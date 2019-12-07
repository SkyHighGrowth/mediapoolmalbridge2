package MediaPoolMalBridge.service.Bridge.exceluploader.upload;

import MediaPoolMalBridge.clients.Bridge.jscp.client.BridgeJScpClient;
import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import MediaPoolMalBridge.service.Bridge.AbstractBridgeUniqueThreadService;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Service which uploads excel files using sftp protocol
 */
@Service
public class BridgeUploadExcelFilesUniqueThreadService extends AbstractBridgeUniqueThreadService {

    private final BridgeJScpClient bridgeJScpClient;

    private final ReportsRepository reportsRepository;

    private final AppConfig appConfig;

    public BridgeUploadExcelFilesUniqueThreadService(final BridgeJScpClient bridgeJScpClient,
                                                     final ReportsRepository reportsRepository,
                                                     final AppConfig appConfig)
    {
        this.bridgeJScpClient = bridgeJScpClient;
        this.reportsRepository = reportsRepository;
        this.appConfig = appConfig;
    }

    @Override
    protected void run()
    {
        final File[] files = new File(appConfig.getExcelDir() ).listFiles();
        if(files == null || files.length == 0 ) {
            return;
        }
        taskExecutorWrapper.lock();
        try {
            for (final File file : files) {
                if (file.isFile()) {
                    if( taskExecutorWrapper.canAcceptNewTask() ) {
                        taskExecutorWrapper.getTaskExecutor().execute( () -> bridgeJScpClient.uploadFile( file.getAbsolutePath() ) );
                    }
                }
            }
        } catch (final Exception e) {
            final String message = String.format( "Can not transfer file using sftp, with message [%s]", e.getMessage() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null);
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
        } finally {
            taskExecutorWrapper.unlock();
        }
    }
}
