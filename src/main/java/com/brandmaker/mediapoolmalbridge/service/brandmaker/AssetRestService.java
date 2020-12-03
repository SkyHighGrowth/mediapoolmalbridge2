package com.brandmaker.mediapoolmalbridge.service.brandmaker;

public interface AssetRestService {
    /**
     * Method that gets assets from BM by theme id.
     * @param themeId String theme id
     * @param propertyId String property id
     * @return String asset id
     */
    String getAssetsByThemeId(String themeId, String propertyId);
}
