package MediaPoolMalBridge.service.Bridge.database.rowsdeleter;

import MediaPoolMalBridge.persistence.repository.Bridge.AssetJsonedValuesRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.schedule.JobRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.schedule.ServiceRepository;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
@DependsOn( "BridgeDatabaseNormalizerService" )
public class BridgeDatabaseRowsDeleterSchedulerService extends AbstractSchedulerService {

    private AssetRepository assetRepository;

    private AssetJsonedValuesRepository assetJsonedValuesRepository;

    private UploadedFileRepository uploadedFileRepository;

    private ReportsRepository reportsRepository;

    private JobRepository jobRepository;

    private ServiceRepository serviceRepository;

    public BridgeDatabaseRowsDeleterSchedulerService( final AssetRepository assetRepository,
                                                      final UploadedFileRepository uploadedFileRepository,
                                                      final ReportsRepository reportsRepository,
                                                      final JobRepository jobRepository,
                                                      final ServiceRepository serviceRepository )
    {
        this.assetRepository = assetRepository;
        this.uploadedFileRepository = uploadedFileRepository;
        this.reportsRepository = reportsRepository;
        this.jobRepository = jobRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBridgeDatabaseRowsDeleterCronExpression()));
    }

    @Override
    public void scheduled() {

        final LocalDateTime dateTime = getTodayMidnight().minusDays( appConfig.getBridgeDatabaseRowsDeleterDays() );

        deleteAssetRows( dateTime );
        deleteFilesToBeDeletedRows( dateTime );
        deleteReportsRows( dateTime );
        deleteScheduledJobsRows( dateTime );
        deleteServiceExecutionRows( dateTime );
    }

    protected void deleteAssetRows( final LocalDateTime dateTime )
    {
        assetRepository.deleteRowsInThePast( dateTime );
    }

    protected void deleteFilesToBeDeletedRows( final LocalDateTime dateTime )
    {
        uploadedFileRepository.deleteRowsInThePast( dateTime );
    }

    protected void deleteReportsRows( final LocalDateTime dateTime )
    {
        reportsRepository.deleteRowsInThePast( dateTime );
    }

    protected void deleteScheduledJobsRows( final LocalDateTime dateTime )
    {
        jobRepository.deleteRowsInThePast( dateTime );
    }

    protected void deleteServiceExecutionRows( final LocalDateTime dateTime )
    {
        serviceRepository.deleteRowsInThePast( dateTime );
    }
}
