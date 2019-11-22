package MediaPoolMalBridge.clients.MAL.download.client.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MALDownloadSingleAssetController {

    private final MALGetAssetsClient malGetAssetsClient;

    private final MALDownloadAssetClient malDownloadAssetClient;

    private final TaskExecutorWrapper taskExecutorWrapper;

    public MALDownloadSingleAssetController(final MALGetAssetsClient malGetAssetsClient,
                                      final MALDownloadAssetClient malDownloadAssetClient,
                                      final TaskExecutorWrapper taskExecutorWrapper)
    {
        this.malDownloadAssetClient = malDownloadAssetClient;
        this.malGetAssetsClient = malGetAssetsClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    @PostMapping( "/downloadAssets" )
    public void download(@RequestBody final MALGetAssetsRequest malGetAssetsRequest )
    {
        final RestResponse<MALGetAssetsResponse> response = malGetAssetsClient.download( malGetAssetsRequest );
        final MALGetAsset malGetAsset = response.getResponse().getAssets().get( 0 );

        if(StringUtils.isNotBlank( malGetAsset.getXlUrl() ) )
        {
            taskExecutorWrapper.getTaskExecutor().execute( () -> malDownloadAssetClient.download( malGetAsset.getXlUrl(), "xl_file" ) );
        }
        if( StringUtils.isNotBlank( malGetAsset.getLargeUrl() ) )
        {
            taskExecutorWrapper.getTaskExecutor().execute( () -> malDownloadAssetClient.download( malGetAsset.getLargeUrl(), "large_file" ) );
        }
        if( StringUtils.isNotBlank( malGetAsset.getMediumUrl() ) )
        {
            taskExecutorWrapper.getTaskExecutor().execute( () -> malDownloadAssetClient.download( malGetAsset.getMediumUrl(), "medium_file" ) );
        }
        if( StringUtils.isNotBlank( malGetAsset.getThumbnailUrl() ) )
        {
            taskExecutorWrapper.getTaskExecutor().execute( () -> malDownloadAssetClient.download( malGetAsset.getThumbnailUrl(), "thumb_file" ) );
        }
    }
}
