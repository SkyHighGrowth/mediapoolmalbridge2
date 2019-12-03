package MediaPoolMalBridge.clients.Bridge.controller;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.clients.Bridge.jscp.client.BridgeJScpClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that triggers sftp file upload
 */
@RestController
@Profile("dev")
public class BridgeController {

    /**
     * Client that wraps file upload operation
     */
    private final BridgeJScpClient bridgeJScpClient;

    /**
     * Application configuration
     */
    private final AppConfig appConfig;

    public BridgeController( final BridgeJScpClient bridgeJScpClient,
                             final AppConfig appConfig )
    {
        this.bridgeJScpClient = bridgeJScpClient;
        this.appConfig = appConfig;
    }

    /**
     * triggers upload file in {@link BridgeJScpClient}
     */
    @GetMapping( "/service/app/uploadFile" )
    public void uploadFile()
    {
        bridgeJScpClient.uploadFile( appConfig.getExcelDir() + "aca.txt" );
    }
}
