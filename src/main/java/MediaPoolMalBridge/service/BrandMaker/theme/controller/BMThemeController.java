package MediaPoolMalBridge.service.BrandMaker.theme.controller;

import MediaPoolMalBridge.service.BrandMaker.theme.download.BMDownloadThemeService;
import MediaPoolMalBridge.service.BrandMaker.theme.upload.BMUploadThemeService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class BMThemeController {

    private BMDownloadThemeService bmDownloadThemeService;

    private BMUploadThemeService bmUploadThemeService;

    public BMThemeController(final BMDownloadThemeService bmDownloadThemeService,
                             final BMUploadThemeService bmUploadThemeService) {
        this.bmDownloadThemeService = bmDownloadThemeService;
        this.bmUploadThemeService = bmUploadThemeService;
    }

    @GetMapping("/service/bm/getThemes")
    public void getTheme() {
        bmDownloadThemeService.download();
    }

    @GetMapping("/service/bm/uploadThemes")
    public void uploadTheme() {
        bmUploadThemeService.uploadTheme();
    }
}
