package MediaPoolMalBridge.service.BrandMaker.theme.upload;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.client.BMCreateThemeClient;
import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.service.AbstractService;
import MediaPoolMalBridge.tasks.MAL.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

@Service
public class BMUploadThemeService extends AbstractService {

    private final BMCreateThemeClient bmCreateThemeClient;

    private final TaskExecutorWrapper taskExecutorWrapper;

    private final MALKits malKits;

    private final BMThemes bmThemes;

    public BMUploadThemeService(final BMCreateThemeClient bmCreateThemeClient,
                                final TaskExecutorWrapper taskExecutorWrapper,
                                final MALKits malKits,
                                final BMThemes bmThemes )
    {
        this.bmCreateThemeClient = bmCreateThemeClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malKits = malKits;
        this.bmThemes = bmThemes;
    }

    public void uploadTheme()
    {
        bmThemes.keySet().forEach( malKits::remove );
        malKits.keySet().forEach( malKit -> {
            final BMTheme bmTheme = new BMTheme();
            bmTheme.setThemeId( 301 );
            bmTheme.setThemePath( malKit );
            taskExecutorWrapper.getTaskExecutor().execute( () -> bmCreateThemeClient.createTheme( bmTheme ) );
        } );
    }
}
