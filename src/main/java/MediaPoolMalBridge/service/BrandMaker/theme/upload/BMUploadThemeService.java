package MediaPoolMalBridge.service.BrandMaker.theme.upload;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.client.BMCreateThemeClient;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.client.model.CreateThemeResponse;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMUploadThemeService extends AbstractBMNonUniqueThreadService<BMTheme> {

    private final BMCreateThemeClient bmCreateThemeClient;

    public BMUploadThemeService(final BMCreateThemeClient bmCreateThemeClient) {
        this.bmCreateThemeClient = bmCreateThemeClient;
    }

    @Override
    protected void run(BMTheme bmTheme) {
        final CreateThemeResponse response = bmCreateThemeClient.createTheme( bmTheme );
        if (!response.isStatus()) {
            reportErrorOnResponse(String.valueOf(bmTheme.getThemeId()), response);
        }
    }
}
