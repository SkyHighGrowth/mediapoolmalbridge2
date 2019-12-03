package MediaPoolMalBridge.service.BrandMaker.theme.controller;

import MediaPoolMalBridge.service.BrandMaker.theme.download.BMDownloadThemeUniqueThreadService;
import MediaPoolMalBridge.service.BrandMaker.theme.upload.BMFireUploadThemeUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that triggers {@link BMDownloadThemeUniqueThreadService} and {@link BMFireUploadThemeUniqueThreadService}
 */
@RestController
@Profile("dev")
public class BMThemeController {

    private BMDownloadThemeUniqueThreadService bmDownloadThemeUniqueThreadService;

    private BMFireUploadThemeUniqueThreadService bmFireUploadThemeUniqueThreadService;

    public BMThemeController(final BMDownloadThemeUniqueThreadService bmDownloadThemeUniqueThreadService,
                             final BMFireUploadThemeUniqueThreadService bmFireUploadThemeUniqueThreadService) {
        this.bmDownloadThemeUniqueThreadService = bmDownloadThemeUniqueThreadService;
        this.bmFireUploadThemeUniqueThreadService = bmFireUploadThemeUniqueThreadService;
    }

    /**
     * triggers {@link BMDownloadThemeUniqueThreadService}
     */
    @GetMapping("/service/bm/getThemes")
    public void getTheme() {
        bmDownloadThemeUniqueThreadService.start();
    }

    /**
     * triggers {@link BMFireUploadThemeUniqueThreadService}
     */
    @GetMapping("/service/bm/uploadThemes")
    public void uploadTheme() {
        bmFireUploadThemeUniqueThreadService.start();
    }
}
