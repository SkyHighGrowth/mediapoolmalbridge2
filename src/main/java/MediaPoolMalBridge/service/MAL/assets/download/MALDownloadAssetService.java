package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAsset;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.model.MAL.filetype.MALFileTypes;
import MediaPoolMalBridge.model.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.service.AbstractService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class MALDownloadAssetService extends AbstractService {

    private final MALDownloadAssetClient malDownloadAssetClient;

    private final MALFileTypes malFileTypes;

    public MALDownloadAssetService(final MALDownloadAssetClient malDownloadAssetClient,
                                   final MALFileTypes malFileTypes) {
        this.malDownloadAssetClient = malDownloadAssetClient;
        this.malFileTypes = malFileTypes;
    }

    public void downloadMALAsset(final MALAsset malAsset) {

        malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING);
        final MALGetAsset malGetAsset = malAsset.getMalGetAsset();
        final String fileName = malGetAsset.getFilename();


        if (StringUtils.isBlank(fileName)) {
            logger.error("Empty file name for MAL asset {}", (new Gson()).toJson(malGetAsset));
            return;
        }

        MALDownloadAssetResponse response;
        boolean downloaded = true;
        try {
            if (StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                final String fileExtension = getFileExtension( malGetAsset.getFileTypeId(), malGetAsset.getXlUrl() );
                final String xlFileName = Constants.XL_FILE_PREFIX + fileName + fileExtension;
                response = fire(decode(malGetAsset.getXlUrl()), xlFileName );
                if( response.isSuccess() )
                {
                    malAsset.setXlFileName( xlFileName );
                } else {
                    logger.error( "Can not download logo PNG file for mal asset {}, with message {}", GSON.toJson( malAsset ), response.getMessage() );
                    downloaded = false;
                }
            }

            if (!downloaded && StringUtils.isNotBlank(malGetAsset.getLargeUrl())) {
                final String fileExtension = getFileExtension( malGetAsset.getFileTypeId(), malGetAsset.getLargeUrl() );
                final String xlFileName = Constants.XL_FILE_PREFIX + fileName + fileExtension;
                final String largeFileName = Constants.LARGE_FILE_PREFIX + fileName;
                response = fire(decode(malGetAsset.getLargeUrl()), largeFileName);
                if( response.isSuccess() )
                {
                    malAsset.setLargeFileName( largeFileName );
                } else {
                    logger.error( "Can not download large file for mal asset {} with message {}", GSON.toJson( malAsset ), response.getMessage() );
                    downloaded = false;
                }
            }

            if (!downloaded && StringUtils.isNotBlank(malGetAsset.getMediumUrl())) {
                final String fileExtension = getFileExtension( malGetAsset.getFileTypeId(), malGetAsset.getMediumUrl() );
                final String xlFileName = Constants.XL_FILE_PREFIX + fileName + fileExtension;
                final String mediumFileName = Constants.MEDIUM_FILE_PREFIX + fileName;
                response = fire(decode(malGetAsset.getMediumUrl()), mediumFileName);
                if( response.isSuccess() )
                {
                    malAsset.setMediumFileName( mediumFileName );
                } else {
                    logger.error( "Can not download medium file for mal asset {}", GSON.toJson( malAsset ) );
                    downloaded = false;
                }
            }

            if (!downloaded && StringUtils.isNotBlank(malGetAsset.getThumbnailUrl())) {
                final String fileExtension = getFileExtension( malGetAsset.getFileTypeId(), malGetAsset.getThumbnailUrl() );
                final String xlFileName = Constants.XL_FILE_PREFIX + fileName + fileExtension;
                final String thumbnailFileName = Constants.THUMBNAIL_FILE_PREFIX + fileName;
                response = fire(decode(malGetAsset.getThumbnailUrl()), thumbnailFileName);
                if (response.isSuccess()) {
                    malAsset.setThumbnailFileName(thumbnailFileName);
                } else {
                    logger.error( "Can not download thumbnail file for mal asset {} with message {}", GSON.toJson( malAsset ), response.getMessage() );
                    downloaded = false;
                }
            }

            if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                final String logoJpgFileName = Constants.LOGO_JPG_FILE_PREFIX + fileName + ".jpg";
                response = fire(decode(malGetAsset.getLogoJpgUrl()), logoJpgFileName );
                if( response.isSuccess() )
                {
                    malAsset.setLogoJPGFileName( logoJpgFileName );
                } else {
                    logger.error( "Can not download logo JPG file for mal asset {} with message {}", GSON.toJson( malAsset ), response.getMessage() );
                    downloaded = false;
                }
            }

            if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                final String logoPngFileName = Constants.LOGO_PNG_FILE_PREFIX + fileName + ".png";
                response = fire(decode(malGetAsset.getLogoJpgUrl()), logoPngFileName);
                if( response.isSuccess() )
                {
                    malAsset.setLogoPNGFileName( logoPngFileName );
                } else {
                    logger.error( "Can not download logo PNG file for mal asset {} with message {}", GSON.toJson( malAsset ), response.getMessage() );
                    downloaded = false;
                }
            }

            if( downloaded )
            {
                malAsset.setTransferringMALConnectionAssetStatus( TransferringMALConnectionAssetStatus.DOWNLOADED );
            }
            else
            {
                malAsset.setTransferringMALConnectionAssetStatus( TransferringMALConnectionAssetStatus.DOWNLOADING );
            }
        }
        catch ( final Exception e )
        {
            logger.error( "Can not create job for asset {}", (new Gson()).toJson(malGetAsset), e );
        }
    }

    private MALDownloadAssetResponse fire(final String url, final String fileName) {
        logger.info( "Firing fileName {}, url {}", fileName, url );
        return malDownloadAssetClient.download(url, fileName);
    }

    private String decode( final String encoded )
            throws UnsupportedEncodingException
    {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }

    private String getFileExtension( final String fileTypeId, final String url )
    {
        String fileExtension = malFileTypes.get( fileTypeId );
        if( !StringUtils.isBlank( fileExtension ) &&
        "mixed".equals( fileExtension ) ) {
            return "." + fileExtension;
        }
        if( "none".equals( fileExtension ) )
        {
            return "";
        }
        final int positionOfQuestionMark = url.indexOf("?");
        int i = 1;
        for (; i < 5; ++i) {
            char c = url.charAt(positionOfQuestionMark - i);
            if ('.' == c) {
                break;
            }
        }
        if (i != 5) {
            return "." + url.substring(positionOfQuestionMark - i + 1, positionOfQuestionMark);
        }
        return "";
    }
}
