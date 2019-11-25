package MediaPoolMalBridge.service.Bridge.deleteFiles;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetRepository;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Service
public class BridgeDeleteFilesSchedulerService extends AbstractSchedulerService {

    private final BMAssetRepository bmAssetRepository;

    private final AppConfig appConfig;

    public BridgeDeleteFilesSchedulerService(final BMAssetRepository bmAssetRepository,
                                             final AppConfig appConfig ) {
        this.bmAssetRepository = bmAssetRepository;
        this.appConfig = appConfig;
    }

    @PostConstruct
    public void scheduledFileDelete() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
    }

    @Override
    public void scheduled() {
        final List<BMAssetEntity> bmAssetEntities = bmAssetRepository.findFilUploadedOrMetadataUploadingOrMetadataUploaded( TransferringBMConnectionAssetStatus.FILE_UPLOADED,
                TransferringBMConnectionAssetStatus.METADATA_UPLOADING, TransferringBMConnectionAssetStatus.METADATA_UPLOADED );
        bmAssetEntities.forEach(bmAssetEntity -> {
            final File file = new File(appConfig.getTempDir() + bmAssetEntity.getFileName());
            if (!file.delete()) {
                final String message = String.format("Can not delete file [%s]", bmAssetEntity.getFileName());
                final ReportsEntity reportsEntity = new ReportsEntity( ReportType.WARNING, getClass().getName(), message, ReportTo.BM, null, null, null );
                logger.error(message);
            }
        });
    }
}
