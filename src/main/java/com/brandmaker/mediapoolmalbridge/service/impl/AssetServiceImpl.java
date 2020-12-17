package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.service.AssetService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@inheritDoc}
 */
@Service
public class AssetServiceImpl implements AssetService {

    public static final String METADATA = "METADATA";
    public static final String METADATA_TRANSFER = "Assets with metadata transfer";
    public static final String FILE = "FILE";
    public static final String FILE_TRANSFER = "Assets with file transfer";
    public static final String ASSET = "ASSET";
    public static final String ASSETS_ON_BOARDED = "Assets on boarded";
    public static final String TOTAL_ASSETS = "Total assets";
    public static final String ASSETS_WITH_ERROR = "Assets with error";
    public static final String ASSETS_COMPLETED = "Assets completed";
    public static final String OTHER_STATUSES = "Assets with status Get BM ID/Invalid";

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Long> getMapCurrentAssetStatus(LocalDate dateFrom, LocalDate dateTo) {
        Map<String, Long> mapCurrentStatus = new LinkedHashMap<>();
        mapCurrentStatus.put(ASSETS_ON_BOARDED, 0L);
        mapCurrentStatus.put(FILE_TRANSFER, 0L);
        mapCurrentStatus.put(METADATA_TRANSFER, 0L);
        mapCurrentStatus.put(ASSETS_WITH_ERROR, 0L);
        mapCurrentStatus.put(ASSETS_COMPLETED, 0L);
        mapCurrentStatus.put(TOTAL_ASSETS, 0L);
        if (dateFrom != null && dateTo != null) {
            LocalDateTime dateTimeFrom = dateFrom.atStartOfDay();
            LocalDateTime dateTimeTo = dateTo.atStartOfDay();
            for (TransferringAssetStatus status : TransferringAssetStatus.values()) {
                Long value = assetRepository.countAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatus(dateTimeFrom, dateTimeTo, status);
                putValuesIntoMap(mapCurrentStatus, status, value);
            }
        } else {
            for (TransferringAssetStatus status : TransferringAssetStatus.values()) {
                Long value = assetRepository.countAllByTransferringAssetStatus(status);
                putValuesIntoMap(mapCurrentStatus, status, value);
            }
        }
        return mapCurrentStatus;
    }

    /**
     * Method that put value into the map
     *
     * @param map    {@link HashMap}
     * @param status {@link TransferringAssetStatus}
     * @param value  value of the status
     */
    private void putValuesIntoMap(Map<String, Long> map, TransferringAssetStatus status, Long value) {
        if (status.toString().startsWith(ASSET)) {
            Long entryValue = map.get(ASSETS_ON_BOARDED);
            map.put(ASSETS_ON_BOARDED, value + entryValue);
        } else if (status.toString().startsWith(FILE)) {
            Long entryValue = map.get(FILE_TRANSFER);
            map.put(FILE_TRANSFER, value + entryValue);
        } else if (status.toString().startsWith(METADATA)) {
            Long entryValue = map.get(METADATA_TRANSFER);
            map.put(METADATA_TRANSFER, value + entryValue);
        } else if (status.equals(TransferringAssetStatus.ERROR)) {
            map.put(ASSETS_WITH_ERROR, value);
        } else if (status.equals(TransferringAssetStatus.DONE)) {
            map.put(ASSETS_COMPLETED, value);
        } else if (!(status.equals(TransferringAssetStatus.GET_BM_ASSET_ID)
                || status.equals(TransferringAssetStatus.GETTING_BM_ASSET_ID)
                || status.equals(TransferringAssetStatus.INVALID))) {
            map.put(OTHER_STATUSES, map.get(OTHER_STATUSES) != null ? 0L : map.get(OTHER_STATUSES) + value);
        }
        Long totalValue = map.get(TOTAL_ASSETS);
        map.put(TOTAL_ASSETS, value + totalValue);
    }
}
