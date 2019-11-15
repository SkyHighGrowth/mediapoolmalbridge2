package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.MAL.assettypes.client.MALGetAssetTypesClient;
import MediaPoolMalBridge.clients.MAL.assettypes.client.model.MALGetAssetTypesResponse;
import MediaPoolMalBridge.clients.MAL.brands.client.MALGetBrandsClient;
import MediaPoolMalBridge.clients.MAL.brands.client.model.MALGetBrandsResponse;
import MediaPoolMalBridge.clients.MAL.collections.client.MALGetCollectionsClient;
import MediaPoolMalBridge.clients.MAL.collections.client.model.MALGetCollectionsResponse;
import MediaPoolMalBridge.clients.MAL.colors.client.MALGetColorsClient;
import MediaPoolMalBridge.clients.MAL.colors.client.model.MALGetColorsResponse;
import MediaPoolMalBridge.clients.MAL.country.client.MALGetCountryClient;
import MediaPoolMalBridge.clients.MAL.country.client.model.MALGetCountryResponse;
import MediaPoolMalBridge.clients.MAL.destionations.client.MALGetDestinationsClient;
import MediaPoolMalBridge.clients.MAL.destionations.client.model.MALGetDestinationsResponse;
import MediaPoolMalBridge.clients.MAL.divisions.client.MALGetDivisionsClient;
import MediaPoolMalBridge.clients.MAL.divisions.client.model.MALGetDivisionsResponse;
import MediaPoolMalBridge.clients.MAL.filetypes.client.MALGetFileTypesClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.MALGetFileTypesResponse;
import MediaPoolMalBridge.clients.MAL.kits.client.MALGetKitsClient;
import MediaPoolMalBridge.clients.MAL.kits.client.model.MALGetKitsResponse;
import MediaPoolMalBridge.clients.MAL.properties.client.MALGetPropertiesClient;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesRequest;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesResponse;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.MALGetUnavailablePropertiesClient;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailabelPropertiesResponse;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailablePropertiesRequest;
import MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.MALGetPropertiesWithRelatedAssetsClient;
import MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.model.MALGetPropertiesWithRelatedAssetsResponse;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoResponse;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.MALGetPropertyPhotosClient;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosResponse;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.MALGetPropertyTypesClient;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.model.MALGetPropertyTypesResponse;
import MediaPoolMalBridge.clients.MAL.state.client.MALGetStatesClient;
import MediaPoolMalBridge.clients.MAL.state.client.model.MALGetStatesResponse;
import MediaPoolMalBridge.clients.MAL.subjects.client.MALGetSubjectsClient;
import MediaPoolMalBridge.clients.MAL.subjects.client.model.MALGetSubjectsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile( "dev" )
public class MALClientController {

    private static Logger logger = LoggerFactory.getLogger(MALClientController.class);

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


    public MALClientController(final MALGetUnavailableAssetsClient getUnavailableAssetsClient,
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

    @PostMapping(value = "/mal/getUnavailableAssets", consumes = "application/json")
    public MALGetUnavailableAssetsResponse getUnavailableAssets(@RequestBody final MALGetUnavailableAssetsRequest request) {
        return getUnavailableAssetsClient.download(request).getResponse();
    }

    @PostMapping(value = "/mal/getAssets", consumes = "application/json")
    public MALGetAssetsResponse getAssets(@RequestBody final MALGetAssetsRequest request) {
        return getAssetsClient.download(request).getResponse();
    }

    @GetMapping("/mal/getAssetTypes")
    public MALGetAssetTypesResponse getAssetTypes() {
        return getAssetTypesClient.download().getResponse();
    }

    @GetMapping("/mal/getBrands")
    public MALGetBrandsResponse getBrands() {
        return getBrandsClient.download().getResponse();
    }

    @GetMapping("/mal/getCollections")
    public MALGetCollectionsResponse getCollections() {
        return getCollectionsClient.download().getResponse();
    }

    @GetMapping("/mal/getColors")
    public MALGetColorsResponse getColors() {
        return getColorsClient.download().getResponse();
    }

    @GetMapping("/getCountry")
    public MALGetCountryResponse getCountry() {
        return getCountryClient.download().getResponse();
    }

    @GetMapping("/mal/getDestinations")
    public MALGetDestinationsResponse getDestinations() {
        return getDestinationsClient.download().getResponse();
    }

    @GetMapping("/mal/getDivisions")
    public MALGetDivisionsResponse getDivisions() {
        return getDivisionsClient.download().getResponse();
    }

    @GetMapping("/mal/getFileTypes")
    public MALGetFileTypesResponse getFileTypes() {
        return getFileTypesClient.download().getResponse();
    }

    @GetMapping("/mal/getKits")
    public MALGetKitsResponse getKits() {
        return getKitsClient.download().getResponse();
    }

    @PostMapping(value = "/mal/getProperties", consumes = "application/json")
    public MALGetPropertiesResponse getProperties(@RequestBody final MALGetPropertiesRequest request) {
        return getPropertiesClient.download(request).getResponse();
    }

    @PostMapping(value = "/mal/getUnavailableProperties", consumes = "application/json")
    public MALGetUnavailabelPropertiesResponse getUnavailableProperties(@RequestBody final MALGetUnavailablePropertiesRequest request) {
        return getUnavailablePropertiesClient.download(request).getResponse();
    }

    @GetMapping("/mal/getPropertiesWithRelatedAssets")
    public MALGetPropertiesWithRelatedAssetsResponse getPropertiesWithUnavailableAssets() {
        return getPropertiesWithRelatedAssetsClient.download().getResponse();
    }

    @PostMapping(value = "/mal/getModifiedPropertiesPhotos", consumes = "application/json")
    public MALGetModifiedPropertyPhotoResponse getModifiedPropertyPhotos(@RequestBody final MALGetModifiedPropertyPhotoRequest request) {
        return getModifiedPropertyPhotoClient.download(request).getResponse();
    }

    @PostMapping(value = "/mal/getPropertiesPhotos", consumes = "application/json")
    public MALGetPropertyPhotosResponse getPropertyPhotos(@RequestBody final MALGetPropertyPhotosRequest request) {
        return getPropertyPhotosClient.download(request).getResponse();
    }

    @GetMapping("/mal/getPropertiesTypes")
    public MALGetPropertyTypesResponse getPropertyTypes() {
        return getPropertyTypesClient.download().getResponse();
    }

    @GetMapping("/mal/getStates")
    public MALGetStatesResponse getStates() {
        return getStatesClient.download().getResponse();
    }

    @GetMapping("/mal/getSubjects")
    public MALGetSubjectsResponse getSubjects() {
        return getSubjectsClient.download().getResponse();
    }
}
