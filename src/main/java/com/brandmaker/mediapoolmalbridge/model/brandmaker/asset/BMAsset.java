package com.brandmaker.mediapoolmalbridge.model.brandmaker.asset;

import java.util.List;

/**
 * Class that represents assets from BrandMaker.
 */
public class BMAsset {
    private String id;
    private List<String> themes;

    private String affiliateNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAffiliateNumber() {
        return affiliateNumber;
    }
    public void setAffiliateNumber(String affiliateNumber) {
        this.affiliateNumber = affiliateNumber;
    }

    public List<String> getThemes() {
        return themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
    }
}
