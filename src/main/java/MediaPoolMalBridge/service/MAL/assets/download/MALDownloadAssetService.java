package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class MALDownloadAssetService extends AbstractMALService {

    private final MALDownloadAssetClient malDownloadAssetClient;

    private final MALAssetStructures malAssetStructures;

    public MALDownloadAssetService(final MALDownloadAssetClient malDownloadAssetClient,
                                   final MALAssetStructures malAssetStructures) {
        this.malDownloadAssetClient = malDownloadAssetClient;
        this.malAssetStructures = malAssetStructures;
    }

    public void downloadMALAsset(final MALAssetEntity malAsset) {

        malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING);
        final MALGetAsset malGetAsset = malAsset.getMALGetAsset();

        MALDownloadAssetResponse response;
        try {
            switch (malAsset.getAssetType()) {
                case FILE:
                    String downloadUrl = "";
                    String fileNameDecorated = "";
                    if (StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                        downloadUrl = malGetAsset.getXlUrl();
                        fileNameDecorated = Constants.XL_FILE_PREFIX + getFileName(downloadUrl, malGetAsset);
                    } else if (StringUtils.isNotBlank(malGetAsset.getLargeUrl())) {
                        downloadUrl = malGetAsset.getLargeUrl();
                        fileNameDecorated = Constants.LARGE_FILE_PREFIX + getFileName(downloadUrl, malGetAsset);
                    } else if (StringUtils.isNotBlank(malGetAsset.getMediumUrl())) {
                        downloadUrl = malGetAsset.getMediumUrl();
                        fileNameDecorated = Constants.MEDIUM_FILE_PREFIX + getFileName(downloadUrl, malGetAsset);
                    } else if (StringUtils.isNotBlank(malGetAsset.getThumbnailUrl())) {
                        downloadUrl = malGetAsset.getThumbnailUrl();
                        fileNameDecorated = Constants.THUMBNAIL_FILE_PREFIX + getFileName(downloadUrl, malGetAsset);
                    }

                    if (StringUtils.isNotBlank(downloadUrl) && StringUtils.isNotBlank(fileNameDecorated)) {
                        response = fire(decode(downloadUrl), fileNameDecorated);
                        if (response.isSuccess()) {
                            malAsset.setFileName(fileNameDecorated);
                            malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADED);
                            malAssetRepository.save(malAsset);
                            return;
                        }
                    } else {
                        final String message = String.format("Can not determine download url for asset id [%s]", malGetAsset.getAssetId());
                        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
                        reportsRepository.save( reportsEntity );
                        logger.error(message);
                    }
                    malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING);
                    break;
                case JPG_LOGO:
                    if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                        final String logoJpgFileName = Constants.LOGO_JPG_FILE_PREFIX +
                                getFileNameWithExtension(malGetAsset.getLogoJpgUrl(), malGetAsset, ".jpg");
                        response = fire(decode(malGetAsset.getLogoJpgUrl()), logoJpgFileName);
                        if (response.isSuccess()) {
                            malAsset.setLogoJPGFileName(logoJpgFileName);
                            malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADED);
                            malAssetRepository.save(malAsset);
                            return;
                        }
                    }
                    malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING);
                    break;
                case PNG_LOGO:
                    if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                        final String logoPngFileName = Constants.LOGO_PNG_FILE_PREFIX +
                                getFileNameWithExtension(malGetAsset.getLogoPngUrl(), malGetAsset, ".png");
                        response = fire(decode(malGetAsset.getLogoPngUrl()), logoPngFileName);
                        if (response.isSuccess()) {
                            malAsset.setLogoPNGFileName(logoPngFileName);
                            malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADED);
                            malAssetRepository.save(malAsset);
                            return;
                        }
                    }
                    malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING);
                    break;
            }
        } catch (final Exception e) {
            logger.error("Problem downloading for asset {}", (new Gson()).toJson(malGetAsset), e);
            malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING);
        }
        malAssetRepository.save(malAsset);
    }

    private MALDownloadAssetResponse fire(final String url, final String fileName) {
        logger.info("Firing fileName {}, url {}", fileName, url);
        return malDownloadAssetClient.download(url, fileName);
    }

    private String decode(final String encoded)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }

    private String getFileNameWithExtension(final String url, final MALGetAsset malGetAsset, final String extension) {
        final String fileName = StringUtils.substringBefore(url, "?");
        if (StringUtils.isNotBlank(fileName)) {
            final String[] parts = fileName.split("/");
            return parts[parts.length - 1];
        }
        if (StringUtils.isNotBlank(malGetAsset.getFilename())) {
            return malGetAsset.getFilename() + extension;
        }
        return RandomStringUtils.randomAlphabetic(30) + extension;
    }

    private String getFileName(final String url, final MALGetAsset malGetAsset) {
        final String fileName = StringUtils.substringBefore(url, "?");
        if (StringUtils.isNotBlank(fileName)) {
            final String[] parts = fileName.split("/");
            return parts[parts.length - 1];
        }
        if (StringUtils.isNotBlank(malGetAsset.getFilename())) {
            return malGetAsset.getFilename() + getFileExtension(malGetAsset.getFileTypeId());
        }
        return malGetAsset.getAssetId() + getFileExtension(malGetAsset.getFileTypeId());
    }

    private String getFileExtension(final String fileTypeId) {
        String fileExtension = malAssetStructures.getFileTypes().get(fileTypeId);
        if (!StringUtils.isBlank(fileExtension) &&
                !"mixed".equals(fileExtension) &&
                !"none".equals(fileExtension)) {
            return "." + fileExtension;
        }
        return "";
    }
}
