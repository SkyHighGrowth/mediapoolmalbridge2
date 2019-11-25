package MediaPoolMalBridge.service.BrandMaker.theme.controller;

import MediaPoolMalBridge.service.BrandMaker.theme.download.BMDownloadThemeService;
import MediaPoolMalBridge.service.BrandMaker.theme.upload.BMFireUploadThemeService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class BMThemeController {

    private BMDownloadThemeService bmDownloadThemeService;

    private BMFireUploadThemeService bmFireUploadThemeService;

    public BMThemeController(final BMDownloadThemeService bmDownloadThemeService,
                             final BMFireUploadThemeService bmFireUploadThemeService) {
        this.bmDownloadThemeService = bmDownloadThemeService;
        this.bmFireUploadThemeService = bmFireUploadThemeService;
    }

    @GetMapping("/service/bm/getThemes")
    public void getTheme() {
        bmDownloadThemeService.start();
    }

    @GetMapping("/service/bm/uploadThemes")
    public void uploadTheme() {
        bmFireUploadThemeService.start();
    }
}
