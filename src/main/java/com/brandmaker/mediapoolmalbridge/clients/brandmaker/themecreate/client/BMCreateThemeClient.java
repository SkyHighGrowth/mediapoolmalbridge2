package com.brandmaker.mediapoolmalbridge.clients.brandmaker.themecreate.client;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.BrandMakerSoapClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.BMTheme;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themecreate.client.model.CreateThemeResponse;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themecreate.tranformer.request.BMThemeTransformer;
import com.brandmaker.webservices.theme.ThemeResult;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MediapoolWebThemePort.createTheme
 */
@Component
public class BMCreateThemeClient extends BrandMakerSoapClient {

    /**
     * Tramsformer from BmTheme to com.brandmaker.webservices.theme.Theme
     */
    private final BMThemeTransformer transformer;

    public BMCreateThemeClient(final BMThemeTransformer transformer) {
        this.transformer = transformer;
    }

    public CreateThemeResponse createTheme(final BMTheme theme) {
        try {
            final com.brandmaker.webservices.theme.Theme request = transformer.toBMTheme(theme);
            final ThemeResult result = getThemeWebServicePort().createTheme(request);
            return new CreateThemeResponse(result);
        } catch (final Exception e) {
            reportErrorOnException(String.valueOf(theme.getThemeId()), e);
            return new CreateThemeResponse(false, e.getMessage());
        }
    }
}