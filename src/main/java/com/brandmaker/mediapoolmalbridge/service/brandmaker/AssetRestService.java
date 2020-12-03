package com.brandmaker.mediapoolmalbridge.service.brandmaker;

/**
 * Rest service for fetching all assets ids by theme id and property id
 */
public interface AssetRestService {

    /**
     * Method that returns assets from BM by BM theme id and property id
     *
     * @param bmColorThemeId String color theme id
     * @param propertyId     String property id
     * @return String asset id
     */
    String getAssetIdsByThemeIdAndPropertyId(String bmColorThemeId, String propertyId);
}
