package MediaPoolMalBridge.service.Bridge.controller;

import MediaPoolMalBridge.service.Bridge.assetstransfer.BridgeAssetTransferSchedulerService;
import MediaPoolMalBridge.service.Bridge.deleteFiles.BridgeDeleteFilesSchedulerService;
import MediaPoolMalBridge.service.Bridge.ktistotheme.BridgeTransferThemeSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALToBMAssetMapTransferSchedulerController {

    private final BridgeAssetTransferSchedulerService bridgeAssetTransferSchedulerService;

    private final BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService;

    private final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService;

    public MALToBMAssetMapTransferSchedulerController(final BridgeAssetTransferSchedulerService bridgeAssetTransferSchedulerService,
                                                      final BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService,
                                                      final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService) {
        this.bridgeAssetTransferSchedulerService = bridgeAssetTransferSchedulerService;
        this.bridgeTransferThemeSchedulerService = bridgeTransferThemeSchedulerService;
        this.bridgeDeleteFilesSchedulerService = bridgeDeleteFilesSchedulerService;
    }

    @GetMapping("/MALtoBM/transferAssets")
    public void transferAssets() {
        bridgeAssetTransferSchedulerService.scheduled();
    }

    @GetMapping("/MALtoBM/kitsToTheme")
    public void transferKits() {
        bridgeTransferThemeSchedulerService.scheduled();
    }

    @GetMapping("/MALtoBM/deleteFiles")
    public void deleteFile() {
        bridgeDeleteFilesSchedulerService.scheduled();
    }
}
