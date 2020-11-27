package com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.MALGetPropertiesClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Request body of {@link MALGetPropertiesClient}
 */
public class MALGetPropertiesRequest {

    //(string) words to search by
    private String keywords = null;

    /*(array) property ids to return specific properties. Format for property_ids is listed below:
            ?property_ids=single_val
            ?property_ids=val1,val2,val3
            ?property_ids[]=val1&property_ids[]=val2
     */
    private List<String> propertyIds = new ArrayList<>();

    /*(array) marsha_codes to return specific properties. Format for marsha_codes is listed below:
            ?marsha_codes=single_val
            ?marsha_codes=val1,val2,val3
            ?marsha_codes[]=val1&property_ids[]=val2
     */
    private List<String> marshaCodes = new ArrayList<>();

    /*(array) brand ids to return properties tied to specific brands. Format for brand_ids is listed below:
            ?brand_ids=single_val
            ?brand_ids=val1,val2,val3
            ?brand_ids[]=val1&brand_ids[]=val2
     */
    private List<String> brandIds = new ArrayList<>();

    /*(array) brand ids to return properties tied to specific brands.
        If the property does not have a parent brand defined, it will search on brand id instead.
        Format for brand_ids is listed below:
            ?parent_brand_ids=single_val
            ?parent_brand_ids=val1,val2,val3
            ?parent_brand_ids[]=val1&parent_brand_ids[]=val2
     */
    private List<String> parentBrandIds = new ArrayList<>();

    /*(boolean) if true, only return properties with associated assets
        ?has_assets=1
     */
    private boolean hasAssets = false;

    /*(string) The locale of the property info you want to return.
    Only properties with the locale set will be returned.
     */
    private String locale = null;

    /*(integer) The number of properties to return at a time (defaults to 50)
     */
    private int perPage = 0;

    /*(integer) The page of results to show (defaults to 1)
     */
    private int page = 0;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public List<String> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(List<String> brandIds) {
        this.brandIds = brandIds;
    }

    public List<String> getParentBrandIds() {
        return parentBrandIds;
    }

    public void setParentBrandIds(List<String> parentBrandIds) {
        this.parentBrandIds = parentBrandIds;
    }

    public boolean isHasAssets() {
        return hasAssets;
    }

    public void setHasAssets(boolean hasAssets) {
        this.hasAssets = hasAssets;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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

    public MultiValueMap<String, String> transformToGetParams() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (keywords != null) {
            params.set("keywords", keywords);
        }
        if (!propertyIds.isEmpty()) {
            params.set("property_ids", transformToString(propertyIds));
        }
        if (!marshaCodes.isEmpty()) {
            params.set("marsha_codes", transformToString(marshaCodes));
        }
        if (!brandIds.isEmpty()) {
            params.set("brand_ids", transformToString(brandIds));
        }
        if (!parentBrandIds.isEmpty()) {
            params.set("parent_brand_ids", transformToString(parentBrandIds));
        }
        if (hasAssets) {
            params.set("has_assets", "1");
        }
        if (StringUtils.isNotBlank(locale)) {
            params.set("locale", locale);
        }
        if (perPage != 0) {
            params.set("per_page", String.valueOf(perPage));
        }
        if (page != 0) {
            params.set("page", String.valueOf(page));
        }
        return params;
    }

    private String transformToString(final List<String> list) {
        return String.join(",", list);
    }
}
