package MediaPoolMalBridge.service.Bridge.exceluploader.controller;

import MediaPoolMalBridge.service.Bridge.exceluploader.BridgeUploadExcelFilesSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class BridgeUploadExcelFilesController {

    private final BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService;

    public BridgeUploadExcelFilesController( final BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService )
    {
        this.bridgeUploadExcelFilesSchedulerService = bridgeUploadExcelFilesSchedulerService;
    }

    @GetMapping( "/service/bridge/uploadExcel" )
    public void upload()
    {
        bridgeUploadExcelFilesSchedulerService.scheduled();
    }
}
