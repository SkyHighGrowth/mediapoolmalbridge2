package com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetid.client;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.BrandMakerSoapClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetid.client.model.GetAssetIdFromMediaHashResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import org.springframework.stereotype.Component;

/**
 * Client which wraps MediapoolWebServicePort.getMediaByGuidByHash
 */
@Component
public class BMGetAssetIdFromHashClient extends BrandMakerSoapClient {

    public GetAssetIdFromMediaHashResponse getAssetId(final AssetEntity asset) {
        try {
            final String result = getMediaPoolPort().getMediaGuidByHash(asset.getBmMd5Hash());
            return new GetAssetIdFromMediaHashResponse(result);
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new GetAssetIdFromMediaHashResponse(false, e.getMessage());
        }
    }
}
