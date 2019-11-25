package MediaPoolMalBridge.service.BrandMaker.theme.upload;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueService;
import org.springframework.stereotype.Service;

@Service
public class BMFireUploadThemeService extends AbstractBMUniqueService {

    private final BMUploadThemeService bmUploadThemeService;

    private final MALKits malKits;

    private final BMThemes bmThemes;

    public BMFireUploadThemeService(final BMUploadThemeService bmUploadThemeService,
                                    final MALKits malKits,
                                    final BMThemes bmThemes) {
        this.bmUploadThemeService = bmUploadThemeService;
        this.malKits = malKits;
        this.bmThemes = bmThemes;
    }

    @Override
    protected void run() {
        bmThemes.keySet().forEach(malKits::remove);
        malKits.keySet().forEach(malKit -> {
            final BMTheme bmTheme = new BMTheme();
            bmTheme.setThemeId(301);
            bmTheme.setThemePath(malKit);
            while( true ) {
                if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                    try {
                        synchronized( bmUploadThemeService ) {
                            bmUploadThemeService.wait(10000);
                        }
                    } catch ( final InterruptedException e ) {
                        throw new RuntimeException();
                    }
                } else {
                    break;
                }
            }
            taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadThemeService.start(bmTheme));
        });
    }
}
