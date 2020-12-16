package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.service.AssetService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 */
@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatus(LocalDateTime from, LocalDateTime to, TransferringAssetStatus transferringAssetStatus) {
        return assetRepository.countAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatus(from, to, transferringAssetStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAllByTransferringAssetStatus(TransferringAssetStatus transferringAssetStatus) {
        return assetRepository.countAllByTransferringAssetStatus(transferringAssetStatus);
    }
}
