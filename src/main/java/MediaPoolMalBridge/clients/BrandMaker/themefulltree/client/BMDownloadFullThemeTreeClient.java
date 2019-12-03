package MediaPoolMalBridge.clients.BrandMaker.themefulltree.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.model.DownloadFullThemeTreeResponse;
import com.brandmaker.webservices.theme.ThemesResult;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MediapoolWebThemeService.getFullThemeTree
 */
@Component
public class BMDownloadFullThemeTreeClient extends BrandMakerSoapClient {

    public DownloadFullThemeTreeResponse downloadFullThemeTree(final BMTheme theme) {
        try {
            final ThemesResult result = getThemeWebServicePort().receiveFullThemeTree(theme.getThemeId());
            return new DownloadFullThemeTreeResponse(result);
        } catch (final Exception e) {
            reportErrorOnException(String.valueOf(theme.getThemeId()), e);
            return new DownloadFullThemeTreeResponse(false, e.getMessage());
        }
    }
}