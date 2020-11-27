package com.brandmaker.mediapoolmalbridge.clients.brandmaker.themefulltree.client.model;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.theme.ThemesResult;

/**
 * Class that wraps response from MediapoolWebThemePort.downaloadFullThemeTree
 */
public class DownloadFullThemeTreeResponse extends AbstractBMResponse {


    private ThemesResult themesResult;

    public DownloadFullThemeTreeResponse(final ThemesResult themesResult) {
        this.status = true;
        this.themesResult = themesResult;
    }

    public DownloadFullThemeTreeResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

    public ThemesResult getThemesResult() {
        return themesResult;
    }

    public void setThemesResult(ThemesResult themesResult) {
        this.themesResult = themesResult;
    }
}
