package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assettypes.client.MALGetAssetTypesClient;
import MediaPoolMalBridge.clients.MAL.brands.client.MALGetBrandsClient;
import MediaPoolMalBridge.clients.MAL.collections.client.MALGetCollectionsClient;
import MediaPoolMalBridge.clients.MAL.colors.client.MALGetColorsClient;
import MediaPoolMalBridge.clients.MAL.country.client.MALGetCountryClient;
import MediaPoolMalBridge.clients.MAL.destionations.client.MALGetDestinationsClient;
import MediaPoolMalBridge.clients.MAL.divisions.client.MALGetDivisionsClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.MALGetFileTypesClient;
import MediaPoolMalBridge.clients.MAL.kits.client.MALGetKitsClient;
import MediaPoolMalBridge.clients.MAL.properties.client.MALGetPropertiesClient;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesRequest;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.MALGetUnavailablePropertiesClient;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailablePropertiesRequest;
import MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.MALGetPropertiesWithRelatedAssetsClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.MALGetPropertyPhotosClient;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosRequest;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.MALGetPropertyTypesClient;
import MediaPoolMalBridge.clients.MAL.state.client.MALGetStatesClient;
import MediaPoolMalBridge.clients.MAL.subjects.client.MALGetSubjectsClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

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


    public HelloController(final MALGetUnavailableAssetsClient getUnavailableAssetsClient,
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
                           final MALGetSubjectsClient getSubjectsClient ) {
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

    @RequestMapping(value = "/getUnavailableAssets", method = RequestMethod.POST)
    public String getUnavailableAssets(@RequestBody final MALGetUnavailableAssetsRequest request) {
        return (new Gson()).toJson(getUnavailableAssetsClient.download(request).getResponse());
    }

    @RequestMapping( value = "/getAssets", method = RequestMethod.POST )
    public String getAssets( @RequestBody final MALGetAssetsRequest request ) {
        return (new Gson()).toJson(getAssetsClient.download(request).getResponse());
    }

    @RequestMapping("/getAssetTypes")
    public String getAssetTypes() {
        return (new Gson()).toJson(getAssetTypesClient.download().getResponse());
    }

    @RequestMapping("/getBrands")
    public String getBrands() {
        return (new Gson()).toJson(getBrandsClient.download().getResponse());
    }

    @RequestMapping("/getCollections")
    public String getCollections() {
        return (new Gson()).toJson(getCollectionsClient.download().getResponse());
    }

    @RequestMapping("/getColors")
    public String getColors() {
        return (new Gson()).toJson(getColorsClient.download().getResponse());
    }

    @RequestMapping("/getCountry")
    public String getCountry() {
        return (new Gson()).toJson(getCountryClient.download().getResponse());
    }

    @RequestMapping("/getDestinations")
    public String getDestinations() {
        return (new Gson()).toJson(getDestinationsClient.download().getResponse());
    }

    @RequestMapping("/getDivisions")
    public String getDivisions() {
        return (new Gson()).toJson(getDivisionsClient.download().getResponse());
    }

    @RequestMapping("/getFileTypes")
    public String getFileTypes() {
        return (new Gson()).toJson(getFileTypesClient.download().getResponse());
    }

    @RequestMapping("/getKits")
    public String getKits() {
        return (new Gson()).toJson(getKitsClient.download().getResponse());
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.POST)
    public String getProperties( @RequestBody final MALGetPropertiesRequest request) {
        return (new Gson()).toJson(getPropertiesClient.download(request).getResponse());
    }

    @RequestMapping(value = "/getUnavailableProperties", method = RequestMethod.POST)
    public String getUnavailableProperties(@RequestBody final MALGetUnavailablePropertiesRequest request) {
        return (new Gson()).toJson(getUnavailablePropertiesClient.download(request).getResponse());
    }

    @RequestMapping("/getPropertiesWithRelatedAssets")
    public String getPropertiesWithUnavailableAssets() {
        return (new Gson()).toJson(getPropertiesWithRelatedAssetsClient.download().getResponse());
    }

    @RequestMapping(value = "/getModifiedPropertiesPhotos", method = RequestMethod.POST)
    public String getModifiedPropertyPhotos(@RequestBody final MALGetModifiedPropertyPhotoRequest request) {
        return (new Gson()).toJson(getModifiedPropertyPhotoClient.download(request).getResponse());
    }

    @RequestMapping(value = "/getPropertiesPhotos", method = RequestMethod.POST)
    public String getPropertyPhotos(@RequestBody final MALGetPropertyPhotosRequest request) {
        return (new Gson()).toJson(getPropertyPhotosClient.download(request).getResponse());
    }

    @RequestMapping("/getPropertiesTypes")
    public String getPropertyTypes() {
        return (new Gson()).toJson(getPropertyTypesClient.download().getResponse());
    }

    @RequestMapping("/getStates")
    public String getStates() {
        return (new Gson()).toJson(getStatesClient.download().getResponse());
    }

    @RequestMapping("/getSubjects")
    public String getSubjects() {
        return (new Gson()).toJson(getSubjectsClient.download().getResponse());
    }
}
