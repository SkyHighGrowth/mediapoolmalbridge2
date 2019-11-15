package MediaPoolMalBridge.service.BrandMaker.theme.download;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.BMDownloadFullThemeTreeClient;
import MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.model.DownloadFullThemeTreeResponse;
import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class BMDownloadThemeService extends AbstractService {

    private final BMDownloadFullThemeTreeClient downloadThemeIdClient;

    private final BMThemes bmThemes;

    public BMDownloadThemeService( final BMDownloadFullThemeTreeClient downloadThemeIdClient,
                                   final BMThemes bmThemes )
    {
        this.downloadThemeIdClient = downloadThemeIdClient;
        this.bmThemes = bmThemes;
    }

    public void download()
    {
        final BMTheme bmTheme = new BMTheme();
        bmTheme.setThemeId( 301 );
        final DownloadFullThemeTreeResponse response = downloadThemeIdClient.downloadFullThemeTree( bmTheme );
        if( !response.isStatus() ||
            response.getThemesResult() == null )
        {
            logger.error( "Can not download themes with response {}", GSON.toJson( response ) );
            return;
        }

        bmThemes.update( response.getThemesResult() );
    }
}
