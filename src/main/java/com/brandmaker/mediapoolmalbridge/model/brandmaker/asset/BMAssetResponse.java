package com.brandmaker.mediapoolmalbridge.model.brandmaker.asset;

import java.util.List;

/**
 * Class that store response from BrandMaker for getting assets by theme id.
 */
public class BMAssetResponse {

    private String total;
    private List<BMAsset> assets;

    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public List<BMAsset> getAssets() {
        return assets;
    }
    public void setAssets(List<BMAsset> assets) {
        this.assets = assets;
    }
}
