package com.brandmaker.mediapoolmalbridge.controller;

import com.brandmaker.mediapoolmalbridge.service.AssetService;
import com.brandmaker.mediapoolmalbridge.service.FileService;
import org.springframework.boot.info.BuildProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * Home Controller
 */
@Controller
public class HomeController {
    private static final String HOME_TEMPLATE = "home";

    private final BuildProperties buildProperties;

    private final AssetService assetService;

    private final FileService fileService;

    public HomeController(BuildProperties buildProperties, AssetService assetService, FileService fileService) {
        this.buildProperties = buildProperties;
        this.assetService = assetService;
        this.fileService = fileService;
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
        model.addAttribute("files", fileService.listFiles());
        model.addAttribute("folderSize", String.format("%.2f", fileService.getCurrentDownloadFolderSize()));
        return HOME_TEMPLATE;

    }

    /**
     * Export configuration properties as json file
     *
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("/downloadFile/{filename}")
    public void export(HttpServletResponse response, @PathVariable String filename) {
        fileService.exportExcelFile(response, filename);
    }

}
