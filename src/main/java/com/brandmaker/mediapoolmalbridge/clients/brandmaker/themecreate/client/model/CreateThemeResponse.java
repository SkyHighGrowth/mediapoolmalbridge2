package com.brandmaker.mediapoolmalbridge.clients.brandmaker.themecreate.client.model;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.theme.ThemeResult;

/**
 * Wraps response of MediapoolWebServiceTheme.createTheme
 */
public class CreateThemeResponse extends AbstractBMResponse {

    private ThemeResult themeResult;

    public CreateThemeResponse(final ThemeResult themeResult) {
        status = themeResult.isResult();
        this.themeResult = themeResult;
    }

    public CreateThemeResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

    public ThemeResult getThemeResult() {
        return themeResult;
    }

    public void setThemeResult(ThemeResult themeResult) {
        this.themeResult = themeResult;
    }
}
