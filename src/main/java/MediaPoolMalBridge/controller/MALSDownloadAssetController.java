package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient;
import MediaPoolMalBridge.clients.MAL.assettypes.client.MALGetAssetTypesClient;
import MediaPoolMalBridge.clients.MAL.brands.client.MALGetBrandsClient;
import MediaPoolMalBridge.clients.MAL.collections.client.MALGetCollectionsClient;
import MediaPoolMalBridge.clients.MAL.colors.client.MALGetColorsClient;
import MediaPoolMalBridge.clients.MAL.country.client.MALGetCountryClient;
import MediaPoolMalBridge.clients.MAL.destionations.client.MALGetDestinationsClient;
import MediaPoolMalBridge.clients.MAL.divisions.client.MALGetDivisionsClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.MALGetFileTypesClient;
import MediaPoolMalBridge.clients.MAL.kits.client.MALGetKitsClient;
import MediaPoolMalBridge.clients.MAL.model.MALAsset;
import MediaPoolMalBridge.clients.MAL.properties.client.MALGetPropertiesClient;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.MALGetUnavailablePropertiesClient;
import MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.MALGetPropertiesWithRelatedAssetsClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.MALGetPropertyPhotosClient;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.MALGetPropertyTypesClient;
import MediaPoolMalBridge.clients.MAL.state.client.MALGetStatesClient;
import MediaPoolMalBridge.clients.MAL.subjects.client.MALGetSubjectsClient;
import MediaPoolMalBridge.service.MAL.assets.download.MALDownloadAssetService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile( "dev" )
public class MALSDownloadAssetController {

    private static Logger logger = LoggerFactory.getLogger(MALSDownloadAssetController.class);

    private final MALDownloadAssetService malDownloadAssetService;
    private final MALGetAssetsClient getAssetsClient;
    private final MALGetAssetTypesClient getAssetTypesClient;
    private final MALGetBrandsClient getBrandsClient;
    private final MALGetCollectionsClient getCollectionsClient;
    private final MALGetColorsClient getColorsClient;
    private final MALGetCountryClient getCountryClient;
    private final MALGetDestinationsClient getDestinationsClient;
    private final MALGetDivisionsClient getDivisionsClient;
    private final MALGetFileTypesClient getFileTypesClient;
    private final MALGetKitsClient getKitsClient;
    private final MALGetPropertiesClient getPropertiesClient;
    private final MALGetUnavailablePropertiesClient getUnavailablePropertiesClient;
    private final MALGetPropertiesWithRelatedAssetsClient getPropertiesWithRelatedAssetsClient;
    private final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient;
    private final MALGetPropertyPhotosClient getPropertyPhotosClient;
    private final MALGetPropertyTypesClient getPropertyTypesClient;
    private final MALGetStatesClient getStatesClient;
    private final MALGetSubjectsClient getSubjectsClient;
    private final MALGetUnavailableAssetsClient getUnavailableAssetsClient;


    public MALSDownloadAssetController(final MALDownloadAssetService malDownloadAssetService,
                                       final MALGetUnavailableAssetsClient getUnavailableAssetsClient,
                                       final MALGetAssetsClient getAssetsClient,
                                       final MALGetAssetTypesClient getAssetTypesClient,
                                       final MALGetBrandsClient getBrandsClient,
                                       final MALGetCollectionsClient getCollectionsClient,
                                       final MALGetColorsClient getColorsClient,
                                       final MALGetCountryClient getCountryClient,
                                       final MALGetDestinationsClient getDestinationsClient,
                                       final MALGetDivisionsClient getDivisionsClient,
                                       final MALGetFileTypesClient getFileTypesClient,
                                       final MALGetKitsClient getKitsClient,
                                       final MALGetPropertiesClient getPropertiesClient,
                                       final MALGetUnavailablePropertiesClient getUnavailablePropertiesClient,
                                       final MALGetPropertiesWithRelatedAssetsClient getPropertiesWithRelatedAssetsClient,
                                       final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient,
                                       final MALGetPropertyPhotosClient getPropertyPhotosClient,
                                       final MALGetPropertyTypesClient getPropertyTypesClient,
                                       final MALGetStatesClient getStatesClient,
                                       final MALGetSubjectsClient getSubjectsClient) {
        this.malDownloadAssetService = malDownloadAssetService;
        this.getUnavailableAssetsClient = getUnavailableAssetsClient;
        this.getAssetsClient = getAssetsClient;
        this.getAssetTypesClient = getAssetTypesClient;
        this.getBrandsClient = getBrandsClient;
        this.getCollectionsClient = getCollectionsClient;
        this.getColorsClient = getColorsClient;
        this.getCountryClient = getCountryClient;
        this.getDestinationsClient = getDestinationsClient;
        this.getDivisionsClient = getDivisionsClient;
        this.getFileTypesClient = getFileTypesClient;
        this.getKitsClient = getKitsClient;
        this.getPropertiesClient = getPropertiesClient;
        this.getUnavailablePropertiesClient = getUnavailablePropertiesClient;
        this.getPropertiesWithRelatedAssetsClient = getPropertiesWithRelatedAssetsClient;
        this.getModifiedPropertyPhotoClient = getModifiedPropertyPhotoClient;
        this.getPropertyPhotosClient = getPropertyPhotosClient;
        this.getPropertyTypesClient = getPropertyTypesClient;
        this.getStatesClient = getStatesClient;
        this.getSubjectsClient = getSubjectsClient;
    }

    @PostMapping(value = "/downloadFirstAsset", consumes = "application/json")
    public String getAssets(@RequestBody final MALGetAssetsRequest request) {
        final MALGetAssetsResponse response = getAssetsClient.download(request).getResponse();
        if (response == null || response.getAssets() == null || response.getAssets().isEmpty()) {
            logger.error("Invalid Get asset response {}", (new Gson()).toJson(response));
            return (new Gson()).toJson(response);
        }
        for( int i = 0; i < request.getPerPage(); ++i ) {
            logger.debug( "Downloading asset {}", (new Gson()).toJson(response.getAssets().get(i)));
            malDownloadAssetService.downloadMALAsset(MALAsset.fromMALGetAsset( response.getAssets().get(i) ));
        }
        return "Started download of asset " + (new Gson()).toJson(response.getAssets().get(0));
    }
}
