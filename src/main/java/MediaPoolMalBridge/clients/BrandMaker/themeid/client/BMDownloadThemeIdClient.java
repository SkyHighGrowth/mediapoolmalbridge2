package MediaPoolMalBridge.clients.BrandMaker.themeid.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.model.DownloadThemeIdResponse;
import com.brandmaker.webservices.theme.Theme;
import com.brandmaker.webservices.theme.ThemeName;
import org.springframework.stereotype.Component;

/**
 * Wraps calls to MediapoolWebThemePort.receiveThemeInformationByPath
 */
@Component
public class BMDownloadThemeIdClient extends BrandMakerSoapClient {

    public DownloadThemeIdResponse downloadThemeId(final BMTheme theme) {
        try {
            final ThemeName request = new ThemeName();
            request.setValue(theme.getThemePath());
            final Theme result = getThemeWebServicePort().receiveThemeInformationByPath(request);
            return new DownloadThemeIdResponse(result);
        } catch (final Exception e) {
            reportErrorOnException(String.valueOf(theme.getThemeId()), e);
            return new DownloadThemeIdResponse(false, e.getMessage());
        }
    }
}
