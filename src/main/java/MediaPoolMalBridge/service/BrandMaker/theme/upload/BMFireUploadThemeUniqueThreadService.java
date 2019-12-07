package MediaPoolMalBridge.service.BrandMaker.theme.upload;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service executes {@link BMUploadThemeService} for specific theme
 */
@Service
public class BMFireUploadThemeUniqueThreadService extends AbstractBMUniqueThreadService {

    private final BMUploadThemeService bmUploadThemeService;

    private final MALKits malKits;

    private final BMThemes bmThemes;

    public BMFireUploadThemeUniqueThreadService(final BMUploadThemeService bmUploadThemeService,
                                                final MALKits malKits,
                                                final BMThemes bmThemes) {
        this.bmUploadThemeService = bmUploadThemeService;
        this.malKits = malKits;
        this.bmThemes = bmThemes;
    }

    @Override
    protected void run() {
        bmThemes.keySet().forEach(malKits::remove);
        taskExecutorWrapper.lock();
        try {
            malKits.keySet().forEach(malKit -> {
                final BMTheme bmTheme = new BMTheme();
                bmTheme.setThemeId(301);
                bmTheme.setThemePath(malKit);

                    if ( taskExecutorWrapper.canAcceptNewTask() ) {
                        taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadThemeService.start(bmTheme));
                    }

            });
        } catch( final Exception e ) {
            logger.error( "Exception occurred during putting load on task executor in {}", getClass().getName(), e );
        } finally {
            taskExecutorWrapper.unlock();
        }
    }
}
