package com.brandmaker.mediapoolmalbridge.config;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MalIncludedAssetTypes {

    private final Set<String> includeAssetTypes;

    public MalIncludedAssetTypes(final AppConfig appConfig ) {
        includeAssetTypes = appConfig.getAppConfigData().getIncludedAssetTypes().keySet();
    }

    public boolean isIncludedAssetType( final String assetType ) {
        return includeAssetTypes.contains( assetType );
    }
}

