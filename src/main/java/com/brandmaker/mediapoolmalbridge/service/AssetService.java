package com.brandmaker.mediapoolmalbridge.service;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;

import java.time.LocalDateTime;

/**
 * Service for assets
 */
public interface AssetService {
    /**
     * Count number of assets by their status and date
     * @param from date
     * @param to date
     * @param transferringAssetStatus {@link TransferringAssetStatus}
     * @return number of assets
     */
    long countAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatus(
            final LocalDateTime from, final LocalDateTime to, final TransferringAssetStatus transferringAssetStatus);

    /**
     *
     * @param transferringAssetStatus {@link TransferringAssetStatus}
     * @return number of assets
     */
    long countAllByTransferringAssetStatus(final TransferringAssetStatus transferringAssetStatus);

}
