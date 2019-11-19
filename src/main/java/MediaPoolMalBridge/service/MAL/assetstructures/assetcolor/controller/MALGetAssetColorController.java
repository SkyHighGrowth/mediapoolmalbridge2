package MediaPoolMalBridge.service.MAL.assetstructures.assetcolor.controller;

import MediaPoolMalBridge.clients.MAL.colors.client.MALGetColorsClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetColorController {

    private final MALGetColorsClient getColorsClient;

    public MALGetAssetColorController(final MALGetColorsClient getColorsClient) {
        this.getColorsClient = getColorsClient;
    }

    @GetMapping("/service/mal/getCollors")
    public void getColors() {
        getColorsClient.download();
    }
}
