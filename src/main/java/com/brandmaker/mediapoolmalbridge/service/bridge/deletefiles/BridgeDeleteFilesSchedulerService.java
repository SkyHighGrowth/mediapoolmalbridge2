package com.brandmaker.mediapoolmalbridge.service.bridge.deletefiles;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.UploadedFileEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.FileStateOnDisc;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.UploadedFileRepository;
import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler service which deletes files from {@link UploadedFileEntity} table
 */
@Service
public class BridgeDeleteFilesSchedulerService extends AbstractSchedulerService {

    private final UploadedFileRepository uploadedFileRepository;

    public BridgeDeleteFilesSchedulerService(final UploadedFileRepository uploadedFileRepository) {
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @PostConstruct
    public void init() {
        String bridgeDeleteFileCronExpression = appConfig.getBridgeDeleteFileCronExpression();
        if (bridgeDeleteFileCronExpression != null) {
            jobSchedule(new CronTrigger(bridgeDeleteFileCronExpression));
        }
    }

    @Override
    public void scheduled() {
        logger.info("Deleting files from uploaded file entity table started");
        deleteFiles();
        if (!appConfig.getAppConfigData().isDisableAbsoluteDelete()) {
            listFilesInTempFolder();
        }
        logger.info("Deleting files from uploaded file entity table ended");
    }

    private void listFilesInTempFolder() {
        final File[] files = new File(appConfig.getTempDir()).listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        final LocalDateTime pointInTime = getTodayMidnight().minusDays(appConfig.getAppConfigData().getAssetFileMaximalLivingDaysOnDisc());
        BasicFileAttributes attributes;
        for (final File file : files) {
            if (file.isFile()) {
                try {
                    attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    final LocalDateTime creationTime = Instant.ofEpochSecond(attributes.creationTime()
                            .to(TimeUnit.SECONDS))
                            .atOffset(ZoneOffset.UTC)
                            .toLocalDateTime();
                    if (creationTime.isBefore(pointInTime)) {
                        final UploadedFileEntity uploadedFileEntity = new UploadedFileEntity(file.getName());
                        uploadedFileRepository.save(uploadedFileEntity);
                    }
                } catch (IOException e) {
                    logger.error(String.format("Error deleting file %s caused by exception %s", file.getAbsolutePath(), e));
                }
            }
        }
    }

    private void deleteFiles() {
        try {
            for (int page = 0; true; ++page) {
                final List<UploadedFileEntity> fileEntities = uploadedFileRepository.findByDeletedAndCreatedIsBefore(
                        false, getTodayMidnight().minusDays(appConfig.getAppConfigData().getAssetFileMaximalLivingDaysOnDisc()), PageRequest.of(0, appConfig.getDatabasePageSize()));
                if (fileEntities.isEmpty() || page > 1000) {
                    break;
                }
                deletePage(fileEntities);
            }
        } catch (final Exception e) {
            final String message = String.format("Error deleting files with message [%s]", e.getMessage());
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
        }
    }

    @Transactional
    public void deletePage(final List<UploadedFileEntity> fileEntities) {
        fileEntities.forEach(fileEntity -> {
            final File file = new File(appConfig.getTempDir() + fileEntity.getFilename());
            if (file.exists()) {
                try {
                    if (!Files.deleteIfExists(file.toPath())) {
                        final String message = String.format("Can not delete file [%s]", fileEntity.getFilename());
                        logger.error(message);
                        fileEntity.setFileStateOnDisc(FileStateOnDisc.ERROR);
                        uploadedFileRepository.save(fileEntity);
                        final ReportsEntity reportsEntity = new ReportsEntity(ReportType.WARNING, getClass().getName(), null, message, ReportTo.BM, null, null, null);
                        reportsRepository.save(reportsEntity);
                    } else {
                        fileEntity.setDeleted(true);
                        uploadedFileRepository.save(fileEntity);
                    }
                } catch (IOException e) {
                    logger.error(String.format("Error occurred deleting file: %s", file.getName()), e);
                }
            } else {
                fileEntity.setDeleted(true);
                uploadedFileRepository.save(fileEntity);
            }
        });
    }
}
