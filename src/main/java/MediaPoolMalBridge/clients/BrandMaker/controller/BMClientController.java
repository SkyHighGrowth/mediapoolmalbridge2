package MediaPoolMalBridge.clients.BrandMaker.controller;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.clients.BrandMaker.assetid.client.BMGetAssetIdFromHashClient;
import MediaPoolMalBridge.clients.BrandMaker.assetid.client.model.GetAssetIdFromMediaHashResponse;
import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.BMUploadMetadataClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.BMUploadVersionAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.client.BMCreateThemeClient;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.client.model.CreateThemeResponse;
import MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.BMDownloadFullThemeTreeClient;
import MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.model.DownloadFullThemeTreeResponse;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.BMDownloadThemeIdClient;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.model.DownloadThemeIdResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.*;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.BindingProvider;
import java.util.Map;

/**
 * Controller that exposes calls to clients directly skipping services, note that calls to this
 * end points will not have an effect on assets in database
 */
@RestController
@Profile("enable controllers")
public class BMClientController {

    private final BMDeleteAssetClient bmDeleteAssetClient;
    private final BMGetAssetIdFromHashClient bmGetAssetIdFromHashClient;
    private final BMDownloadMediaDetailsClient bmDownloadMediaDetailsClient;
    private final BMUploadAssetClient bmUploadAssetClient;
    private final BMUploadMetadataClient bmUploadMetadataClient;
    private final BMUploadVersionAssetClient bmUploadVersionAssetClient;

    private final BMCreateThemeClient bmCreateThemeClient;
    private final BMDownloadFullThemeTreeClient bmDownloadFullThemeTreeClient;
    private final BMDownloadThemeIdClient bmDownloadThemeIdClient;

    public BMClientController(final BMDeleteAssetClient bmDeleteAssetClient,
                              final BMGetAssetIdFromHashClient bmGetAssetIdFromHashClient,
                              final BMDownloadMediaDetailsClient bmDownloadMediaDetailsClient,
                              final BMUploadAssetClient bmUploadAssetClient,
                              final BMUploadMetadataClient bmUploadMetadataClient,
                              final BMUploadVersionAssetClient bmUploadVersionAssetClient,
                              final BMCreateThemeClient bmCreateThemeClient,
                              final BMDownloadFullThemeTreeClient bmDownloadFullThemeTreeClient,
                              final BMDownloadThemeIdClient bmDownloadThemeIdClient) {
        this.bmDeleteAssetClient = bmDeleteAssetClient;
        this.bmGetAssetIdFromHashClient = bmGetAssetIdFromHashClient;
        this.bmDownloadMediaDetailsClient = bmDownloadMediaDetailsClient;
        this.bmUploadAssetClient = bmUploadAssetClient;
        this.bmUploadMetadataClient = bmUploadMetadataClient;
        this.bmUploadVersionAssetClient = bmUploadVersionAssetClient;

        this.bmCreateThemeClient = bmCreateThemeClient;
        this.bmDownloadFullThemeTreeClient = bmDownloadFullThemeTreeClient;
        this.bmDownloadThemeIdClient = bmDownloadThemeIdClient;
    }

    /**
     * deletes asset from Mediapool
     * @param asset
     * @return
     */
    @PostMapping("/client/bm/deleteAsset")
    public DeleteMediaResponse deleteAsset(@RequestBody() final AssetEntity asset) {
        return bmDeleteAssetClient.delete(asset);
    }

    /**
     * obtains asset id from the Mediapool server
     * @param asset
     * @return
     */
    @PostMapping("/client/bm/assetIdFromHash")
    public GetAssetIdFromMediaHashResponse getAssetIdFromHash(@RequestBody() final AssetEntity asset) {
        return bmGetAssetIdFromHashClient.getAssetId(asset);
    }

    /**
     * gets media detail from Mediapool server
     * @param asset
     * @return
     */
    @PostMapping("/client/bm/getMediaDetails")
    public DownloadMediaDetailsResponse getMediaDetails(@RequestBody() final AssetEntity asset) {
        return bmDownloadMediaDetailsClient.download(asset);
    }

    /**
     * creates asset on Mediapool server
     * @param asset
     * @return
     */
    @PostMapping("/client/bm/uploadAsset")
    public UploadStatus uploadAsset(@RequestBody() final AssetEntity asset) {
        return bmUploadAssetClient.upload(asset);
    }

    /**
     * uploads metadata to Mediapool server
     * @param asset
     * @return
     */
    @PostMapping("/client/bm/uploadMetadata")
    public UploadMetadataStatus uploadMetadata(@RequestBody() final AssetEntity asset) {
        return bmUploadMetadataClient.upload(asset);
    }

    @PostMapping("/client/soap/bm/uploadMetadata")
    public UploadMetadataResult uploadMetadata(@RequestBody() final UploadMetadataArgument uploadMetadataArgument) {
        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, "sal_middle_ware_2" );
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, "Changeme2020!" );
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://qamarriott.brandmakerinc.com/webservices/MediaPool/");
        return port.uploadMetaData(uploadMetadataArgument);
    }

    @PostMapping("/client/soap/bm/downloadMetadata")
    public GetMediaDetailsResult downloadMetadata(@RequestBody() final GetMediaDetailsArgument uploadMetadataArgument) {
        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, "sal_middle_ware_2" );
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, "Changeme2020!" );
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://qamarriott.brandmakerinc.com/webservices/MediaPool/");
        return port.getMediaDetails(uploadMetadataArgument);
    }

    /**
     * uploads asset version to Mediapool server
     * @param asset
     * @return
     */
    @PostMapping("/client/bm/uploadAssetVersion")
    public UploadStatus uploadAssetVersion(@RequestBody() final AssetEntity asset) {
        return bmUploadVersionAssetClient.upload(asset);
    }

    /**
     * creates theme on Mediapool server
     * @param bmTheme
     * @return
     */
    @PostMapping("/client/bm/createTheme")
    public CreateThemeResponse createTheme(@RequestBody() final BMTheme bmTheme) {
        return bmCreateThemeClient.createTheme(bmTheme);
    }

    /**
     * downloads full theme tree form Mediapool server
     * @param bmTheme
     * @return
     */
    @PostMapping("/client/bm/downloadFullThemeTree")
    public DownloadFullThemeTreeResponse downloadFullThemeTree(@RequestBody() final BMTheme bmTheme) {
        return bmDownloadFullThemeTreeClient.downloadFullThemeTree(bmTheme);
    }

    /**
     * downloads theme id by path
     * @param bmTheme
     * @return
     */
    @PostMapping("/client/bm/getThemeId")
    public DownloadThemeIdResponse getThemeId(@RequestBody() final BMTheme bmTheme) {
        return bmDownloadThemeIdClient.downloadThemeId(bmTheme);
    }
}
