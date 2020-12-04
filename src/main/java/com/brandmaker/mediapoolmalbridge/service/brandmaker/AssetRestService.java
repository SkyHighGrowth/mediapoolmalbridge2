package com.brandmaker.mediapoolmalbridge.service.brandmaker;

import com.brandmaker.mediapoolmalbridge.model.brandmaker.asset.BMAsset;

import java.util.List;

/**
 * Rest service for fetching all assets ids by theme id and property id
 */
public interface AssetRestService {

    /**
     * Method that returns assets from BM by BM theme id and property id
     *
     * @param bmColorThemeId String color theme id
     * @return String asset id
     */
    List<BMAsset> getAssetIdsByThemeIdAndPropertyId(String bmColorThemeId);
}
