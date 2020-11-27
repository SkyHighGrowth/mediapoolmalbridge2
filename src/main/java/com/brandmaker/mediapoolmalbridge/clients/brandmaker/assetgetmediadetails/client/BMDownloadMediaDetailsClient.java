package com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetgetmediadetails.client;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.BrandMakerSoapClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.GetMediaDetailsArgument;
import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;
import org.springframework.stereotype.Component;

/**
 * Client that wrapps MediapoolWebService.getMedaiDetails, it wraps expections into
 * {@link DownloadMediaDetailsResponse}
 */
@Component
public class BMDownloadMediaDetailsClient extends BrandMakerSoapClient {

    public DownloadMediaDetailsResponse download(final AssetEntity asset) {
        try {
            final GetMediaDetailsArgument request = new GetMediaDetailsArgument();
            request.setMediaGuid(asset.getBmAssetId());
            final GetMediaDetailsResult result = getMediaPoolPort().getMediaDetails(request);
            return new DownloadMediaDetailsResponse(result);
        } catch (final Exception e) {
            logger.error("Error downloading asset {}", asset, e);
            reportErrorOnException( asset.getBmAssetId(), e );
            return new DownloadMediaDetailsResponse(false, e.getMessage());
        }
    }
}
