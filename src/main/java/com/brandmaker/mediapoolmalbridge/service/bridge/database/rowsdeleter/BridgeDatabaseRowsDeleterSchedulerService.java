package com.brandmaker.mediapoolmalbridge.service.bridge.database.rowsdeleter;

import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.UploadedFileRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.schedule.ServiceRepository;
import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
@DependsOn("BridgeDatabaseNormalizerService")
public class BridgeDatabaseRowsDeleterSchedulerService extends AbstractSchedulerService {

    private final AssetRepository assetRepository;

    private final UploadedFileRepository uploadedFileRepository;

    private final ServiceRepository serviceRepository;

    public BridgeDatabaseRowsDeleterSchedulerService(final AssetRepository assetRepository,
                                                     final UploadedFileRepository uploadedFileRepository,
                                                     final ServiceRepository serviceRepository) {
        this.assetRepository = assetRepository;
        this.uploadedFileRepository = uploadedFileRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostConstruct
    public void init() {
        String bridgeDatabaseRowsDeleterCronExpression = appConfig.getBridgeDatabaseRowsDeleterCronExpression();
        if (bridgeDatabaseRowsDeleterCronExpression != null) {
            jobSchedule(new CronTrigger(bridgeDatabaseRowsDeleterCronExpression));
        }
    }

    @Override
    public void scheduled() {

        final LocalDateTime dateTime = getTodayMidnight().minusDays(appConfig.getBridgeDatabaseRowsDeleterDays());

        deleteAssetRows(dateTime);
        deleteFilesToBeDeletedRows(dateTime);
        deleteReportsRows(dateTime);
        deleteScheduledJobsRows(dateTime);
        deleteServiceExecutionRows(dateTime);
    }

    protected void deleteAssetRows(final LocalDateTime dateTime) {
        assetRepository.deleteRowsInThePast(dateTime);
    }

    protected void deleteFilesToBeDeletedRows(final LocalDateTime dateTime) {
        uploadedFileRepository.deleteRowsInThePast(dateTime);
    }

    protected void deleteReportsRows(final LocalDateTime dateTime) {
        reportsRepository.deleteRowsInThePast(dateTime);
    }

    protected void deleteScheduledJobsRows(final LocalDateTime dateTime) {
        jobRepository.deleteRowsInThePast(dateTime);
    }

    protected void deleteServiceExecutionRows(final LocalDateTime dateTime) {
        serviceRepository.deleteRowsInThePast(dateTime);
    }
}
