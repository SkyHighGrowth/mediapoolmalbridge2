package com.brandmaker.mediapoolmalbridge.clients.brandmaker.themecreate.tranformer.request;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.BMTheme;
import com.brandmaker.webservices.theme.Theme;
import com.brandmaker.webservices.theme.ThemeName;
import org.springframework.stereotype.Component;

/**
 * Transform {@link BMTheme} to com.brandmaker.webservices.theme.Theme
 */
@Component
public class BMThemeTransformer {

    public Theme toBMTheme(final BMTheme theme) {
        final Theme theme1 = new Theme();
        theme1.setId(theme.getThemeId());
        theme1.setParentId(301);
        Theme.Names names = new Theme.Names();
        ThemeName themeName = new ThemeName();
        themeName.setValue(theme.getThemePath());
        themeName.setLanguageKey("EN");
        names.getName().add(themeName);
        theme1.setNames(names);
        return theme1;
    }
}
