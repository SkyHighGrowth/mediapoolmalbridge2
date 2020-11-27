package com.brandmaker.mediapoolmalbridge.service.brandmaker.theme.upload;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.BMTheme;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themecreate.client.BMCreateThemeClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themecreate.client.model.CreateThemeResponse;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which uploads theme to Mediapool service
 */
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
