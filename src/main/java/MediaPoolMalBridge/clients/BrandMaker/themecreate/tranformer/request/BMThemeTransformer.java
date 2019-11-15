package MediaPoolMalBridge.clients.BrandMaker.themecreate.tranformer.request;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import com.brandmaker.webservices.theme.ThemeName;
import org.springframework.stereotype.Component;

import com.brandmaker.webservices.theme.Theme;

@Component
public class BMThemeTransformer {

    public Theme toBMTheme(final BMTheme theme) {
        final Theme theme1 = new Theme();
        theme1.setId( theme.getThemeId() );
        Theme.Names names = new Theme.Names();
        ThemeName themeName = new ThemeName();
        themeName.setValue( theme.getThemePath() );
        themeName.setLanguageKey( "EN" );
        names.getName().add( themeName );
        theme1.setNames( names );
        return theme1;
    }
}
