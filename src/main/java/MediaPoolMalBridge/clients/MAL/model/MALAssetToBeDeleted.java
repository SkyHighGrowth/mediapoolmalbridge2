package MediaPoolMalBridge.clients.MAL.model;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALModifiedPropertyPhotoAsset;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAsset;
import org.apache.commons.lang3.StringUtils;

public class MALAssetToBeDeleted extends TransferringAsset {

    private String assetIdDecorated;

    private String bmAssetId;

    private MALAssetType assetType;

    private MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset;

    private MALGetAsset malGetAsset;

    private MALGetUnavailableAsset malGetUnavailableAsset;

    private String mediumPhotoFileName;

    private String jpgPhotoFileName;

    private String fileName;

    private String logoJPGFileName;

    private String logoPNGFileName;

    public static MALAssetToBeDeleted fromMALGetModifiedPropertyPhotos(final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset, final MALAssetType malAssetType) {
        final MALAssetToBeDeleted malAsset = new MALAssetToBeDeleted();
        malAsset.setAssetIdDecorated(MALAssetToBeDeleted.createAssetIdDecorated(malModifiedPropertyPhotoAsset.getAssetId(), malAssetType));
        malAsset.setMalModifiedPropertyPhotoAsset(malModifiedPropertyPhotoAsset);
        return malAsset;
    }

    public static MALAssetToBeDeleted fromMALGetAsset(final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        final MALAssetToBeDeleted malAsset = new MALAssetToBeDeleted();
        malAsset.setAssetType(malAssetType);
        malAsset.setAssetIdDecorated(MALAssetToBeDeleted.createAssetIdDecorated(malGetAsset.getAssetId(), malAssetType));
        malAsset.setMalGetAsset(malGetAsset);
        return malAsset;
    }

    public static MALAssetToBeDeleted fromMALGetUnavailableAsset(final MALGetUnavailableAsset malGetUnavailableAsset, final MALAssetType malAssetType) {
        final MALAssetToBeDeleted malAsset = new MALAssetToBeDeleted();
        malAsset.setAssetIdDecorated(MALAssetToBeDeleted.createAssetIdDecorated(malGetUnavailableAsset.getAssetId(), malAssetType));
        malAsset.setMalGetUnavailableAsset(malGetUnavailableAsset);
        return malAsset;
    }

    public MALModifiedPropertyPhotoAsset getMalModifiedPropertyPhotoAsset() {
        return malModifiedPropertyPhotoAsset;
    }

    public void setMalModifiedPropertyPhotoAsset(MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset) {
        this.malModifiedPropertyPhotoAsset = malModifiedPropertyPhotoAsset;
    }

    public MALGetAsset getMalGetAsset() {
        return malGetAsset;
    }

    public void setMalGetAsset(MALGetAsset malGetAsset) {
        this.malGetAsset = malGetAsset;
    }

    public MALGetUnavailableAsset getMalGetUnavailableAsset() {
        return malGetUnavailableAsset;
    }

    public void setMalGetUnavailableAsset(MALGetUnavailableAsset malGetUnavailableAsset) {
        this.malGetUnavailableAsset = malGetUnavailableAsset;
    }

    public String getMediumPhotoFileName() {
        return mediumPhotoFileName;
    }

    public void setMediumPhotoFileName(String mediumPhotoFileName) {
        this.mediumPhotoFileName = mediumPhotoFileName;
    }

    public String getJpgPhotoFileName() {
        return jpgPhotoFileName;
    }

    public void setJpgPhotoFileName(String jpgPhotoFileName) {
        this.jpgPhotoFileName = jpgPhotoFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLogoJPGFileName() {
        return logoJPGFileName;
    }

    public void setLogoJPGFileName(String logoJPGFileName) {
        this.logoJPGFileName = logoJPGFileName;
    }

    public String getLogoPNGFileName() {
        return logoPNGFileName;
    }

    public void setLogoPNGFileName(String logoPNGFileName) {
        this.logoPNGFileName = logoPNGFileName;
    }

    /*public String getMALAssetId()
    {
        return assetId;
    }*/

    public String getAssetIdDecorated() {
        return assetIdDecorated;
    }

    public void setAssetIdDecorated(final String assetIdDecorated) {
        this.assetIdDecorated = assetIdDecorated;
    }

    public String getBmAssetId() {
        return bmAssetId;
    }

    public void setBmAssetId(String bmAssetId) {
        this.bmAssetId = bmAssetId;
    }

    public MALAssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(MALAssetType assetType) {
        this.assetType = assetType;
    }

    public static String createAssetIdDecorated(final String assetId, final MALAssetType malAssetType) {
        return assetId + "-" + malAssetType.name();
    }

    public static String getAssetIdFromDecorated(final String assetIdDecorated) {
        return StringUtils.substringBefore(assetIdDecorated, "-");
    }

    public static MALAssetType getAssetTypeFromDecorated(final String assetIdDecorated) {
        return MALAssetType.valueOf(StringUtils.substringAfter(assetIdDecorated, "-"));
    }
}
