package MediaPoolMalBridge.service.Bridge.deleteFiles;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.FileStateOnDisc;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.AbstractSchedulerService;
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

    private final AppConfig appConfig;

    public BridgeDeleteFilesSchedulerService(final UploadedFileRepository uploadedFileRepository,
                                             final AppConfig appConfig ) {
        this.uploadedFileRepository = uploadedFileRepository;
        this.appConfig = appConfig;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBridgeDeleteFileCronExpression()));
    }

    @Override
    public void scheduled() {
        deleteFiles();
        if( !appConfig.isDisableAbsoluteDelete() ) {
            listFilesInTempFolder();
        }
    }

    private void listFilesInTempFolder() {
        final File[] files = new File(appConfig.getTempDir() ).listFiles();
        if(files == null || files.length == 0 ) {
            return;
        }
        final LocalDateTime pointInTime = getTodayMidnight().minusDays( appConfig.getAssetFileMaximalLivingDaysOnDisc() );
        BasicFileAttributes attributes = null;
        for (final File file : files) {
            if (file.isFile()) {
                try
                {
                    attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    final LocalDateTime creationTime = Instant.ofEpochSecond( attributes.creationTime()
                            .to(TimeUnit.SECONDS ) )
                            .atOffset( ZoneOffset.UTC )
                            .toLocalDateTime();
                    if( creationTime.isBefore( pointInTime ) ) {
                        final UploadedFileEntity uploadedFileEntity = new UploadedFileEntity( file.getName() );
                        uploadedFileRepository.save( uploadedFileEntity );
                    }
                } catch (IOException e) {
                    logger.error("Error deleting file " + file.getAbsolutePath() + " caused by exception " + e.getMessage());
                }
            }
        }
    }

    private void deleteFiles() {
        boolean condition = true;
        try {
            for (int page = 0; condition; ++page) {
                final List<UploadedFileEntity> fileEntities = uploadedFileRepository.findByDeletedAndFileStateOnDiscAndCreatedIsBefore(
                        false, FileStateOnDisc.NO_ERROR, getTodayMidnight().minusDays( appConfig.getBridgeLookInThePastDays() + appConfig.getBridgeResolverWindow() + 1 ), PageRequest.of(0, appConfig.getDatabasePageSize()));
                if( fileEntities.isEmpty() || page > 1000 ) {
                    break;
                }
                deletePage(fileEntities);
            }
        } catch( final Exception e ) {
            final String message = String.format( "Error deleting files with message [%s]", e.getMessage() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
        }
    }

    @Transactional
    protected void deletePage( final List<UploadedFileEntity> fileEntities )
    {
        fileEntities.forEach(fileEntity -> {
            final File file = new File(appConfig.getTempDir() + fileEntity.getFilename());
            if (file.exists()) {
                if (!file.delete()) {
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
            } else {
                fileEntity.setDeleted(true);
                uploadedFileRepository.save(fileEntity);
            }
        });
    }
}
