package com.brandmaker.mediapoolmalbridge.service.bridge.controller;

import com.brandmaker.mediapoolmalbridge.service.bridge.database.assetresolver.BridgeDatabaseAssetResolverSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.database.assetunlocker.BridgeDatabaseUnlockerSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.deletefiles.BridgeDeleteFilesSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.excelcreator.BridgeCreateExcelFileSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.exceluploader.BridgeUploadExcelFilesSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.ktistotheme.BridgeTransferThemeSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.sendmail.BridgeSendMailSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that exposes end points to trigger {@link MALToBMAssetMapTransferSchedulerController}
 */
@RestController
@Profile("enable controllers")
public class MALToBMAssetMapTransferSchedulerController {

    private final BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService;

    private final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService;

    private final BridgeCreateExcelFileSchedulerService bridgeCreateExcelFileSchedulerService;

    private final BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService;

    private final BridgeSendMailSchedulerService bridgeSendMailSchedulerService;

    private final BridgeDatabaseUnlockerSchedulerService bridgeDatabaseUnlockerSchedulerService;

    private final BridgeDatabaseAssetResolverSchedulerService bridgeDatabaseAssetResolverSchedulerService;

    public MALToBMAssetMapTransferSchedulerController(final BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService,
                                                      final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService,
                                                      final BridgeCreateExcelFileSchedulerService bridgeCreateExcelFileSchedulerService,
                                                      final BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService,
                                                      final BridgeSendMailSchedulerService bridgeSendMailSchedulerService,
                                                      final BridgeDatabaseUnlockerSchedulerService bridgeDatabaseUnlockerSchedulerService,
                                                      final BridgeDatabaseAssetResolverSchedulerService bridgeDatabaseAssetResolverSchedulerService) {
        this.bridgeTransferThemeSchedulerService = bridgeTransferThemeSchedulerService;
        this.bridgeDeleteFilesSchedulerService = bridgeDeleteFilesSchedulerService;
        this.bridgeCreateExcelFileSchedulerService = bridgeCreateExcelFileSchedulerService;
        this.bridgeUploadExcelFilesSchedulerService = bridgeUploadExcelFilesSchedulerService;
        this.bridgeSendMailSchedulerService = bridgeSendMailSchedulerService;
        this.bridgeDatabaseUnlockerSchedulerService = bridgeDatabaseUnlockerSchedulerService;
        this.bridgeDatabaseAssetResolverSchedulerService = bridgeDatabaseAssetResolverSchedulerService;
    }

    /**
     * triggers execution of {@link BridgeTransferThemeSchedulerService}
     */
    @GetMapping("/service/app/kitsToTheme")
    public void transferKits() {
        bridgeTransferThemeSchedulerService.scheduled();
    }

    /**
     * triggers execution of {@link BridgeDeleteFilesSchedulerService}
     */
    @GetMapping("/service/app/deleteFiles")
    public void deleteFile() {
        bridgeDeleteFilesSchedulerService.scheduled();
    }

    /**
     * triggers execution of {@link BridgeCreateExcelFileSchedulerService}
     */
    @GetMapping( "/service/app/createExcel" )
    public void createExcelFiles()
    {
        bridgeCreateExcelFileSchedulerService.scheduled();
    }

    /**
     * triggers execution of {@link BridgeUploadExcelFilesSchedulerService}
     */
    @GetMapping( "/service/app/uploadExcel" )
    public void uploadExcelFiles()
    {
        bridgeUploadExcelFilesSchedulerService.scheduled();
    }

    /**
     * triggers execution of {@link BridgeSendMailSchedulerService}
     */
    @GetMapping( "/service/app/sendMail" )
    public void sendMail()
    {
        bridgeSendMailSchedulerService.scheduled();
    }

    @GetMapping( "/service/app/assetOnBoarding" )
    public void assetOnBoarding()
    {
        bridgeDatabaseUnlockerSchedulerService.scheduled();
    }

    @GetMapping( "/service/app/assetResolve" )
    public void assetResolve()
    {
        bridgeDatabaseAssetResolverSchedulerService.scheduled();
    }
}
