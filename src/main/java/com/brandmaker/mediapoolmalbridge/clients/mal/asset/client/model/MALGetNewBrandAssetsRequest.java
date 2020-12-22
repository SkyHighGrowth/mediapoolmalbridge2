package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * MAL server assets request for the client {@link com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.MALGetNewBrandAssetsClient}
 */
public class MALGetNewBrandAssetsRequest {

    //set to true for property only assets
    private boolean property;
    // set to true for brand only assets. this will override property
    private boolean brand;
    //an array of MAL asset id's.
    private List<Integer> ids;
    //an array of MDAM asset id's.
    private List<Integer> mdam_ids;
    //set true to include assets set as "Hotel Website Standard"
    private boolean hws;
    //the page you wish to pull from. Default is 1
    private int page;
    //the number of results you wish to be returned. Default is 100
    private int limit;

    public boolean isProperty() {
        return property;
    }

    public void setProperty(boolean property) {
        this.property = property;
    }

    public boolean isBrand() {
        return brand;
    }

    public void setBrand(boolean brand) {
        this.brand = brand;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getMdam_ids() {
        return mdam_ids;
    }

    public void setMdam_ids(List<Integer> mdam_ids) {
        this.mdam_ids = mdam_ids;
    }

    public boolean isHws() {
        return hws;
    }

    public void setHws(boolean hws) {
        this.hws = hws;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public MultiValueMap<String, String> transformToGetParams() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("hws", "true");
        params.set("property", "true");
        params.set("page", String.valueOf(getPage()));
        params.set("limit", String.valueOf(getLimit()));
        return params;
    }

}
