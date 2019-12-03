package MediaPoolMalBridge.clients.BrandMaker.themeid.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.theme.Theme;

/**
 * Wraps response from MediapoolWebThemePort.receiveThemeInformationByPath
 */
public class DownloadThemeIdResponse extends AbstractBMResponse {

    private Theme theme;

    public DownloadThemeIdResponse(final Theme theme) {
        this.status = true;
        this.theme = theme;
    }

    public DownloadThemeIdResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
