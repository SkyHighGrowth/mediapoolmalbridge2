package com.brandmaker.mediapoolmalbridge.service;

import java.time.LocalDate;
import java.util.Map;

/**
 * Service for assets
 */
public interface AssetService {

    /**
     * Get count of all current statuses
     *
     * @param dateFrom Date from
     * @param dateTo   Date to
     * @return map with count of all current status
     */
    Map<String, Long> getMapCurrentAssetStatus(LocalDate dateFrom, LocalDate dateTo);
}
