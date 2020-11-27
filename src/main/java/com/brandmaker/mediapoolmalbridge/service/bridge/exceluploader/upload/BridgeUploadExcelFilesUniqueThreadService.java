package com.brandmaker.mediapoolmalbridge.service.bridge.exceluploader.upload;

import com.brandmaker.mediapoolmalbridge.clients.bridge.jscp.client.BridgeJScpClient;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.bridge.AbstractBridgeUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.tasks.threadpoolexecutor.model.ComparableRunnableWrapper;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Service which uploads excel files using sftp protocol
 */
@Service
public class BridgeUploadExcelFilesUniqueThreadService extends AbstractBridgeUniqueThreadService {

    private final BridgeJScpClient bridgeJScpClient;

    public BridgeUploadExcelFilesUniqueThreadService(final BridgeJScpClient bridgeJScpClient) {
        this.bridgeJScpClient = bridgeJScpClient;
    }

    @Override
    protected void run() {
        final File[] files = new File(appConfig.getExcelDir()).listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        taskExecutorWrapper.lock();
        try {
            for (final File file : files) {
                if (file.isFile() && taskExecutorWrapper.canAcceptNewTask()) {
                    taskExecutorWrapper.getTaskExecutor().execute(new ComparableRunnableWrapper(() -> bridgeJScpClient.uploadFile(file.getAbsolutePath()), 1, "excel file upload"));
                }
            }
        } catch (final Exception e) {
            final String message = String.format("Can not transfer file using sftp, with message [%s]", e.getMessage());
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
        } finally {
            taskExecutorWrapper.unlock();
        }
    }
}
