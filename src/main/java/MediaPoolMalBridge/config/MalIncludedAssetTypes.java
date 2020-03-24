package MediaPoolMalBridge.config;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MalIncludedAssetTypes {

    private Set<String> includeAssetTypes;

    public MalIncludedAssetTypes(final AppConfig appConfig ) {
        includeAssetTypes = appConfig.getIncludedAssetTypes().keySet();
    }

    public boolean isIncludedAssetType( final String assetType ) {
        return includeAssetTypes.contains( assetType );
    }
}

