package MediaPoolMalBridge.service.Bridge.exceluploader.upload;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.Bridge.jscp.client.BridgeJScpClient;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import MediaPoolMalBridge.service.AbstractService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class BridgeUploadExcelFilesService extends AbstractService {

    private final BridgeJScpClient bridgeJScpClient;

    private final ReportsRepository reportsRepository;

    private final AppConfig appConfig;

    private final TaskExecutorWrapper taskExecutorWrapper;

    public BridgeUploadExcelFilesService(final BridgeJScpClient bridgeJScpClient,
                                         final ReportsRepository reportsRepository,
                                         final AppConfig appConfig,
                                         final TaskExecutorWrapper taskExecutorWrapper)
    {
        this.bridgeJScpClient = bridgeJScpClient;
        this.reportsRepository = reportsRepository;
        this.appConfig = appConfig;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void upload()
    {
        try {
            final File[] files = new File(appConfig.getExcelDir() ).listFiles();
            for (final File file : files) {
                if (file.isFile()) {
                    taskExecutorWrapper.getTaskExecutor().execute( () -> bridgeJScpClient.uploadFile( file.getAbsolutePath() ) );
                }
            }
        } catch (final Exception e) {
            final String message = "Can not get file names in excel directory";
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null);
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
        }
    }
}
