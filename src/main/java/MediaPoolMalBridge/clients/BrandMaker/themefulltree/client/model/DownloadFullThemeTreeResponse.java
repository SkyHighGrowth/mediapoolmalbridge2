package MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
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
