package com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.MALGetPropertiesClient;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Response of {@link MALGetPropertiesClient}
 */
public class MALGetPropertiesResponse extends MALAbstractResponse {

    private String page;

    @SerializedName("per_page")
    private String perPage;

    @SerializedName("total_properties")
    private String totalProperties;

    @SerializedName("total_pages")
    private String totalPages;


    /**
     * List of {@link MALProperty} objects
     */
    private List<MALProperty> properties;

    public MALGetPropertiesResponse() {

    }

    public MALGetPropertiesResponse(final String result, final String message) {
        setResult(result);
        setMessage(message);
        this.properties = new ArrayList<>();
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    public String getTotalProperties() {
        return totalProperties;
    }

    public void setTotalProperties(String totalProperties) {
        this.totalProperties = totalProperties;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<MALProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<MALProperty> properties) {
        this.properties = properties;
    }
}
