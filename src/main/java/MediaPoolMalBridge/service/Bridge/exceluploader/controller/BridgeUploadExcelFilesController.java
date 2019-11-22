package MediaPoolMalBridge.service.Bridge.exceluploader.controller;

import MediaPoolMalBridge.service.Bridge.exceluploader.BridgeUploadExcelFilesSchedulerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BridgeUploadExcelFilesController {

    private final BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService;

    public BridgeUploadExcelFilesController( final BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService )
    {
        this.bridgeUploadExcelFilesSchedulerService = bridgeUploadExcelFilesSchedulerService;
    }

    @GetMapping( "/service/bridge/uploadExcel" )
    public void upload()
    {
        bridgeUploadExcelFilesSchedulerService.upload();
    }
}
