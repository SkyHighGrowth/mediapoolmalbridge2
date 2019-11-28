package MediaPoolMalBridge.service.Bridge.deleteFiles;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;

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
    public void scheduledFileDelete() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBridgeDeleteFileCronExpression()));
    }

    @Override
    public void scheduled() {
        boolean condition = true;
        try {
            for (int page = 0; condition; ++page) {
                final Slice<UploadedFileEntity> fileEntities = uploadedFileRepository.findAllByDeleted(false, PageRequest.of(page, 1000));
                condition = fileEntities.hasNext();
                deletePage(fileEntities);
            }
        } catch( final Exception e ) {
            final String message = String.format( "Error deleting files with message [%s]", e.getMessage() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
            logger.error( message, e );
        }
    }

    @Transactional
    protected void deletePage( final Slice<UploadedFileEntity> fileEntities )
    {
        fileEntities.forEach(fileEntity -> {
            final File file = new File(appConfig.getTempDir() + fileEntity.getFilename());
            if (!file.delete()) {
                final String message = String.format("Can not delete file [%s]", fileEntity.getFilename());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.WARNING, getClass().getName(), message, ReportTo.BM, null, null, null);
                logger.error(message);
            } else {
                fileEntity.setDeleted(true);
                uploadedFileRepository.save(fileEntity);
            }
        });
    }
}
