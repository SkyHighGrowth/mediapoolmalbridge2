package MediaPoolMalBridge.service.Bridge.controller;

import MediaPoolMalBridge.service.Bridge.deleteFiles.BridgeDeleteFilesSchedulerService;
import MediaPoolMalBridge.service.Bridge.ktistotheme.BridgeTransferThemeSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALToBMAssetMapTransferSchedulerController {

    private final BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService;

    private final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService;

    public MALToBMAssetMapTransferSchedulerController(final BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService,
                                                      final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService) {
        this.bridgeTransferThemeSchedulerService = bridgeTransferThemeSchedulerService;
        this.bridgeDeleteFilesSchedulerService = bridgeDeleteFilesSchedulerService;
    }

    @GetMapping("/app/kitsToTheme")
    public void transferKits() {
        bridgeTransferThemeSchedulerService.scheduled();
    }

    @GetMapping("/app/deleteFiles")
    public void deleteFile() {
        bridgeDeleteFilesSchedulerService.scheduled();
    }
}
