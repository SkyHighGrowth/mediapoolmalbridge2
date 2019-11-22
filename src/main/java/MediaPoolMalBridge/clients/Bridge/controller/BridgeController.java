package MediaPoolMalBridge.clients.Bridge.controller;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.Bridge.jscp.client.BridgeJScpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BridgeController {

    private final BridgeJScpClient bridgeJScpClient;

    private final AppConfig appConfig;

    public BridgeController( final BridgeJScpClient bridgeJScpClient,
                             final AppConfig appConfig )
    {
        this.bridgeJScpClient = bridgeJScpClient;
        this.appConfig = appConfig;
    }

    @GetMapping( "/bridge/uploadFile" )
    public void uploadFile()
    {
        bridgeJScpClient.uploadFile( appConfig.getExcelDir() + "aca.txt" );
    }
}
