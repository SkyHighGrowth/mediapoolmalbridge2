package MediaPoolMalBridge.clients.BrandMaker.controller;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
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
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BMClientController {

    private final BMDeleteAssetClient bmDeleteAssetClient;
    private final BMDownloadMediaDetailsClient bmDownloadMediaDetailsClient;
    private final BMUploadAssetClient bmUploadAssetClient;
    private final BMUploadMetadataClient bmUploadMetadataClient;
    private final BMUploadVersionAssetClient bmUploadVersionAssetClient;

    private final BMCreateThemeClient bmCreateThemeClient;
    private final BMDownloadFullThemeTreeClient bmDownloadFullThemeTreeClient;
    private final BMDownloadThemeIdClient bmDownloadThemeIdClient;

    public BMClientController(final BMDeleteAssetClient bmDeleteAssetClient,
                              final BMDownloadMediaDetailsClient bmDownloadMediaDetailsClient,
                              final BMUploadAssetClient bmUploadAssetClient,
                              final BMUploadMetadataClient bmUploadMetadataClient,
                              final BMUploadVersionAssetClient bmUploadVersionAssetClient,
                              final BMCreateThemeClient bmCreateThemeClient,
                              final BMDownloadFullThemeTreeClient bmDownloadFullThemeTreeClient,
                              final BMDownloadThemeIdClient bmDownloadThemeIdClient) {
        this.bmDeleteAssetClient = bmDeleteAssetClient;
        this.bmDownloadMediaDetailsClient = bmDownloadMediaDetailsClient;
        this.bmUploadAssetClient = bmUploadAssetClient;
        this.bmUploadMetadataClient = bmUploadMetadataClient;
        this.bmUploadVersionAssetClient = bmUploadVersionAssetClient;

        this.bmCreateThemeClient = bmCreateThemeClient;
        this.bmDownloadFullThemeTreeClient = bmDownloadFullThemeTreeClient;
        this.bmDownloadThemeIdClient = bmDownloadThemeIdClient;
    }

    @PostMapping("/bm/deleteAsset")
    public DeleteMediaResponse deleteAsset(@RequestBody() final BMAssetEntity bmAsset) {
        return bmDeleteAssetClient.delete(bmAsset);
    }

    @PostMapping("/bm/getMediaDetails")
    public DownloadMediaDetailsResponse getMediaDetails(@RequestBody() final BMAssetEntity bmAsset) {
        return bmDownloadMediaDetailsClient.download(bmAsset);
    }

    @PostMapping("/bm/uploadAsset")
    public UploadStatus uploadAsset(@RequestBody() final BMAssetEntity bmAsset) {
        return bmUploadAssetClient.upload(bmAsset);
    }

    @PostMapping("/bm/uploadMetadata")
    public UploadMetadataStatus uploadMetadata(@RequestBody() final BMAssetEntity bmAsset) {
        return bmUploadMetadataClient.upload(bmAsset);
    }

    @PostMapping("/bm/uploadAssetVersion")
    public UploadStatus uploadAssetVersion(@RequestBody() final BMAssetEntity bmAsset) {
        return bmUploadVersionAssetClient.upload(bmAsset);
    }

    @PostMapping("/bm/createTheme")
    public CreateThemeResponse createTheme(@RequestBody() final BMTheme bmTheme) {
        return bmCreateThemeClient.createTheme(bmTheme);
    }

    @PostMapping("/bm/downloadFullThemeTree")
    public DownloadFullThemeTreeResponse downloadFullThemeTree(@RequestBody() final BMTheme bmTheme) {
        return bmDownloadFullThemeTreeClient.downloadFullThemeTree(bmTheme);
    }

    @PostMapping("/bm/getThemeId")
    public DownloadThemeIdResponse getThemeId(@RequestBody() final BMTheme bmTheme) {
        return bmDownloadThemeIdClient.downloadThemeId(bmTheme);
    }
}
