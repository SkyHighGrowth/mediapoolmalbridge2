package MediaPoolMalBridge.service.Bridge.controller;

import MediaPoolMalBridge.service.Bridge.MALToBMAssetMapTransferSchedulerService;
import MediaPoolMalBridge.service.Bridge.deleteFiles.BridgeDeleteFilesSchedulerService;
import MediaPoolMalBridge.service.Bridge.ktistotheme.MALKitsToBMThemeSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALToBMAssetMapTransferSchedulerController {

    private final MALToBMAssetMapTransferSchedulerService malToBMAssetMapTransferSchedulerService;

    private final MALKitsToBMThemeSchedulerService malKitsToBMThemeSchedulerService;

    private final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService;

    public MALToBMAssetMapTransferSchedulerController(final MALToBMAssetMapTransferSchedulerService malToBMAssetMapTransferSchedulerService,
                                                      final MALKitsToBMThemeSchedulerService malKitsToBMThemeSchedulerService,
                                                      final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService) {
        this.malToBMAssetMapTransferSchedulerService = malToBMAssetMapTransferSchedulerService;
        this.malKitsToBMThemeSchedulerService = malKitsToBMThemeSchedulerService;
        this.bridgeDeleteFilesSchedulerService = bridgeDeleteFilesSchedulerService;
    }

    @GetMapping("/MALtoBM/transferAssets")
    public void transferAssets() {
        malToBMAssetMapTransferSchedulerService.transferAssets();
    }

    @GetMapping("/MALtoBM/kitsToTheme")
    public void transferKits() {
        malKitsToBMThemeSchedulerService.exchange();
    }

    @GetMapping("/MALtoBM/deleteFiles")
    public void deleteFile() {
        bridgeDeleteFilesSchedulerService.deleteFiles();
    }
}
