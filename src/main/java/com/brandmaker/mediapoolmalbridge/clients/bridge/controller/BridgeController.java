package com.brandmaker.mediapoolmalbridge.clients.bridge.controller;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.clients.bridge.jscp.client.BridgeJScpClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that triggers sftp file upload
 */
@RestController
@Profile("enable controllers")
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
    @GetMapping( "/service/app/uploadFile/{fileName}" )
    public void uploadFile(@PathVariable String fileName)
    {
        bridgeJScpClient.uploadFile( appConfig.getExcelDir() + fileName );
    }
}
