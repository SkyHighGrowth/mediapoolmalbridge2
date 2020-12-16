package com.brandmaker.mediapoolmalbridge.controller;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.AssetService;
import org.springframework.boot.info.BuildProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Home Controller
 */
@Controller
public class HomeController {
    private static final String HOME_TEMPLATE = "home";
    public static final String METADATA = "METADATA";
    public static final String FILE = "FILE";
    public static final String ASSET = "ASSET";

    private final BuildProperties buildProperties;

    private final AssetService assetService;

    public HomeController(BuildProperties buildProperties, AssetService assetService) {
        this.buildProperties = buildProperties;
        this.assetService = assetService;
    }

    /**
     * Home mapping
     *
     * @param model {@link Model}
     * @return root url
     */
    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                       @RequestParam(value = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        model.addAttribute("version", buildProperties.getVersion());
        Map<String, Long> mapCurrentStatus = new HashMap<>();
        mapCurrentStatus.put(METADATA, 0L);
        mapCurrentStatus.put(FILE, 0L);
        mapCurrentStatus.put(ASSET, 0L);
        if (dateFrom != null && dateTo != null) {
            LocalDateTime dateTimeFrom = dateFrom.atStartOfDay();
            LocalDateTime dateTimeTo = dateTo.atStartOfDay();
            for (TransferringAssetStatus status : TransferringAssetStatus.values()) {
                Long value = assetService.countAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatus(dateTimeFrom, dateTimeTo, status);
                putValuesIntoMap(mapCurrentStatus, status, value);
            }
        } else {
            for (TransferringAssetStatus status : TransferringAssetStatus.values()) {
                Long value = assetService.countAllByTransferringAssetStatus(status);
                putValuesIntoMap(mapCurrentStatus, status, value);
            }
        }

        model.addAttribute("mapCurrentStatus", mapCurrentStatus);
        return HOME_TEMPLATE;

    }

    /**
     * Method that put value into the map
     * @param map {@link HashMap}
     * @param status {@link TransferringAssetStatus}
     * @param value value of the status
     */
    private void putValuesIntoMap(Map<String, Long> map, TransferringAssetStatus status, Long value) {
        if (status.toString().startsWith(METADATA)) {
            Long entryValue = map.get(METADATA);
            map.put(METADATA, value + entryValue);
        } else if (status.toString().startsWith(FILE)) {
            Long entryValue = map.get(FILE);
            map.put(FILE, value + entryValue);
        } else if (status.toString().startsWith(ASSET)) {
            Long entryValue = map.get(ASSET);
            map.put(ASSET, value + entryValue);
        } else {
            if (!(status.equals(TransferringAssetStatus.GET_BM_ASSET_ID) || status.equals(TransferringAssetStatus.GETTING_BM_ASSET_ID) || status.equals(TransferringAssetStatus.INVALID))) {
                map.put(status.toString(), value);
            }
        }
    }
}
