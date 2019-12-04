package MediaPoolMalBridge.service.BrandMaker.theme.themeid.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class BMThemePathToId extends ConcurrentHashMap<String, String> {

    public synchronized void addThemePath( final String themePath ) {
        final String id = get( themePath );
        if( StringUtils.isBlank( id ) ) {
            put( themePath, "" );
        }
    }
}
