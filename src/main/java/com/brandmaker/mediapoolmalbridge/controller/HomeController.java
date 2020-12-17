package com.brandmaker.mediapoolmalbridge.controller;

import com.brandmaker.mediapoolmalbridge.service.AssetService;
import org.springframework.boot.info.BuildProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * Home Controller
 */
@Controller
public class HomeController {
    private static final String HOME_TEMPLATE = "home";

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
        model.addAttribute("mapCurrentStatus", assetService.getMapCurrentAssetStatus(dateFrom, dateTo));
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        return HOME_TEMPLATE;

    }
}
