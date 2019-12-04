package MediaPoolMalBridge.clients.MAL.asset.client.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * MAL server assets request for the client {@link MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient}
 */
public class MALGetAssetsRequest {

    //(array) asset IDs you want info on.
    private List<String> assetIds = new ArrayList<>();

    //(array) Pass collection IDs to return only assets in given collections.
    private List<String> collectionIds = new ArrayList<>();

    //(array) asset type ids to only return assets of a specific type
    private List<String> assetTypeIds = new ArrayList<>();

    //(array) property type ids to only return assets that are associted with a property of this type
    private List<String> propertyTypeIds = new ArrayList<>();

    //(array) destination ids to only return assets that are associated with the given destinations
    private List<String> destinationIds = new ArrayList<>();

    //(array) property ids to filter assets by. Pass '*' to only return assets associated with any property.
    private List<String> propertyIds = new ArrayList<>();

    //(array) marsha_codes to filter assets by.
    private List<String> marshaCodes = new ArrayList<>();

    //exact name of property to find assets for
    private String propertyName = null;

    //(array) brand ids to filter assets by
    private List<String> brandIds = new ArrayList<>();

    //(array) division ids to filter assets by
    private List<String> divisionIds = new ArrayList<>();

    //(array) country ids to filter assets by
    private List<String> countryIds = new ArrayList<>();

    //(array) state ids to filter assets by
    private List<String> stateIds = new ArrayList<>();

    //(array) province names to filter assets by
    private List<String> provinces = new ArrayList<>();

    //(array) city names to filter assets by
    private List<String> cities = new ArrayList<>();

    //(array) subject ids to filter assets by
    private List<String> subjectIds = new ArrayList<>();

    //(array) color ids to filter assets by
    private List<String> colorIds = new ArrayList<>();

    //(string) words to search by
    private String keywords = null;

    //(string) find assets created on this day or after
    private String dateCreatedStart = null;

    //(string) find assets created on this day or before
    private String dateCreatedEnd = null;

    //(string) find assets modified on this day or after
    private String lastModifiedStart = null;

    //(string) find assets modified on this day or before
    private String lastModifiedEnd = null;

    //(boolean) Only return assets an associated brand.
    private boolean brandOnly = false;

    //(boolean) Return info on the asset’s files size.
    private boolean assetFileData = false;

    //(boolean) Only returns assets that have been processed as an offer.
    private boolean isOffered = false;

    //(boolean) Only returns assets that meet the offer work order criteria.
    private boolean offerable = false;

    //(boolean) find assets that have an uploaded file associated with it
    private boolean hasFile = false;

    //(boolean) find asset that is the primary asset for the property of the selected color or subject
    private boolean primary = false;

    //(integer) The number of assets to return at a time (defaults to 200)
    private int perPage = 0;

    //(integer) The page of results to show (defaults to 1)
    private int page = 0;

    //(integer) The stored search ID to return assets for.
    //NOTE: Passing in a stored_search_id will override all other parameters and only return assets in the specified stored search.
    private int storedSearchId = 0;

    //(string) The name of the Starworks Kit to return assets for.
    //    NOTE: Passing in a kit name will override all other parameters and only return assets in the specified kit.
    private String kit = null;

    //(array) Over time, new datapoints are added to Assets. You can specifiy which fields you would like to include in the response. Current fields are:
    //    is_primary - Whether or not the image is a set as a primary property image on it’s property.
    private List<String> extraFields = new ArrayList<>();

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public List<String> getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(List<String> collectionIds) {
        this.collectionIds = collectionIds;
    }

    public List<String> getAssetTypeIds() {
        return assetTypeIds;
    }

    public void setAssetTypeIds(List<String> assetTypeIds) {
        this.assetTypeIds = assetTypeIds;
    }

    public List<String> getPropertyTypeIds() {
        return propertyTypeIds;
    }

    public void setPropertyTypeIds(List<String> propertyTypeIds) {
        this.propertyTypeIds = propertyTypeIds;
    }

    public List<String> getDestinationIds() {
        return destinationIds;
    }

    public void setDestinationIds(List<String> destinationIds) {
        this.destinationIds = destinationIds;
    }

    public List<String> getPropertyIds() {
        return propertyIds;
    }

    public void setPropertyIds(List<String> propertyIds) {
        this.propertyIds = propertyIds;
    }

    public List<String> getMarshaCodes() {
        return marshaCodes;
    }

    public void setMarshaCodes(List<String> marshaCodes) {
        this.marshaCodes = marshaCodes;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public List<String> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(List<String> brandIds) {
        this.brandIds = brandIds;
    }

    public List<String> getDivisionIds() {
        return divisionIds;
    }

    public void setDivisionIds(List<String> divisionIds) {
        this.divisionIds = divisionIds;
    }

    public List<String> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(List<String> countryIds) {
        this.countryIds = countryIds;
    }

    public List<String> getStateIds() {
        return stateIds;
    }

    public void setStateIds(List<String> stateIds) {
        this.stateIds = stateIds;
    }

    public List<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<String> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public List<String> getColorIds() {
        return colorIds;
    }

    public void setColorIds(List<String> colorIds) {
        this.colorIds = colorIds;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDateCreatedStart() {
        return dateCreatedStart;
    }

    public void setDateCreatedStart(String dateCreatedStart) {
        this.dateCreatedStart = dateCreatedStart;
    }

    public String getDateCreatedEnd() {
        return dateCreatedEnd;
    }

    public void setDateCreatedEnd(String dateCreatedEnd) {
        this.dateCreatedEnd = dateCreatedEnd;
    }

    public String getLastModifiedStart() {
        return lastModifiedStart;
    }

    public void setLastModifiedStart(String lastModifiedStart) {
        this.lastModifiedStart = lastModifiedStart;
    }

    public String getLastModifiedEnd() {
        return lastModifiedEnd;
    }

    public void setLastModifiedEnd(String lastModifiedEnd) {
        this.lastModifiedEnd = lastModifiedEnd;
    }

    public boolean isBrandOnly() {
        return brandOnly;
    }

    public void setBrandOnly(boolean brandOnly) {
        this.brandOnly = brandOnly;
    }

    public boolean isAssetFileData() {
        return assetFileData;
    }

    public void setAssetFileData(boolean assetFileData) {
        this.assetFileData = assetFileData;
    }

    public boolean isOffered() {
        return isOffered;
    }

    public void setOffered(boolean offered) {
        isOffered = offered;
    }

    public boolean isOfferable() {
        return offerable;
    }

    public void setOfferable(boolean offerable) {
        this.offerable = offerable;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStoredSearchId() {
        return storedSearchId;
    }

    public void setStoredSearchId(int storedSearchId) {
        this.storedSearchId = storedSearchId;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public List<String> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(List<String> extraFields) {
        this.extraFields = extraFields;
    }

    public MultiValueMap<String, String> trnasformToGetParams() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (!assetIds.isEmpty()) {
            params.set("asset_ids", transfromToString(assetIds));
        }
        if (!collectionIds.isEmpty()) {
            params.set("collection_ids", transfromToString(collectionIds));
        }
        if (!assetTypeIds.isEmpty()) {
            params.set("asset_type_ids", transfromToString(assetTypeIds));
        }
        if (!propertyTypeIds.isEmpty()) {
            params.set("property_type_id", transfromToString(propertyTypeIds));
        }
        if (!destinationIds.isEmpty()) {
            params.set("destination_ids", transfromToString(destinationIds));
        }
        if (!propertyIds.isEmpty()) {
            params.set("property_ids", transfromToString(propertyIds));
        }
        if (!marshaCodes.isEmpty()) {
            params.set("marsha_codes", transfromToString(marshaCodes));
        }
        if (StringUtils.isNotBlank(propertyName)) {
            params.set("property_name", propertyName);
        }
        if (!brandIds.isEmpty()) {
            params.set("brand_ids", transfromToString(brandIds));
        }
        if (!divisionIds.isEmpty()) {
            params.set("division_ids", transfromToString(divisionIds));
        }
        if (!countryIds.isEmpty()) {
            params.set("country_ids", transfromToString(countryIds));
        }
        if (!stateIds.isEmpty()) {
            params.set("state_ids", transfromToString(stateIds));
        }
        if (!provinces.isEmpty()) {
            params.set("provinces", transfromToString(provinces));
        }
        if (!cities.isEmpty()) {
            params.set("cities", transfromToString(cities));
        }
        if (!subjectIds.isEmpty()) {
            params.set("subject_ids", transfromToString(subjectIds));
        }
        if (!colorIds.isEmpty()) {
            params.set("color_ids", transfromToString(colorIds));
        }
        if (StringUtils.isNotBlank(keywords)) {
            params.set("keywords", keywords);
        }
        if (StringUtils.isNotBlank(dateCreatedStart)) {
            params.set("date_created_start", dateCreatedStart);
        }
        if (StringUtils.isNotBlank(dateCreatedEnd)) {
            params.set("date_created_end", dateCreatedEnd);
        }
        if (StringUtils.isNotBlank(lastModifiedStart)) {
            params.set("last_modified_start", lastModifiedStart);
        }
        if (StringUtils.isNotBlank(lastModifiedEnd)) {
            params.set("last_modified_end", lastModifiedEnd);
        }
        if (brandOnly) {
            params.set("brand_only", "1");
        }
        if (assetFileData) {
            params.set("asset_file_data", "1");
        }
        if (isOffered) {
            params.set("is_offered", "1");
        }
        if (offerable) {
            params.set("offerable", "1");
        }
        if (hasFile) {
            params.set("has_file", "1");
        }
        if (primary) {
            params.set("primary", String.valueOf(primary));
        }
        if (perPage != 0) {
            params.set("per_page", String.valueOf(perPage));
        }
        if (page != 0) {
            params.set("page", String.valueOf(page));
        }
        if (storedSearchId != 0) {
            params.set("stored_search_id", String.valueOf(storedSearchId));
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(kit)) {
            params.set("kit", kit);
        }
        if (!extraFields.isEmpty()) {
            params.set("extra_fields", transfromToString(extraFields));
        }
        return params;
    }

    private String transfromToString(final List<String> list) {
        return String.join(",", list);
    }
}
