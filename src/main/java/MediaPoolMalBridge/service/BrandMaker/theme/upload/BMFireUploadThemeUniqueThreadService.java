package MediaPoolMalBridge.service.BrandMaker.theme.upload;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import MediaPoolMalBridge.tasks.threadpoolexecutor.model.ComparableRunnableWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

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
            for (Map.Entry<String, String> malKitEntry : malKits.entrySet()) {
                final BMTheme bmTheme = new BMTheme();
                bmTheme.setThemeId(Integer.parseInt(malKitEntry.getValue()));
                bmTheme.setThemePath(malKitEntry.getKey());

                if (taskExecutorWrapper.canAcceptNewTask()) {
                    taskExecutorWrapper.getTaskExecutor().execute(new ComparableRunnableWrapper(() -> bmUploadThemeService.start(bmTheme), 1, "kits to theme"));
                }
            }
        } catch( final Exception e ) {
            logger.error( "Exception occurred during putting load on task executor in {}", getClass().getName(), e );
        } finally {
            taskExecutorWrapper.unlock();
        }
    }
}
