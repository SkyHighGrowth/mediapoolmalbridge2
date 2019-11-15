package MediaPoolMalBridge.clients.MAL.model;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALModifiedPropertyPhotoAsset;
import MediaPoolMalBridge.model.asset.TransferringAsset;

public class MALAsset extends TransferringAsset {

    private String assetId;

    private MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset;

    private MALGetAsset malGetAsset;

    private MALGetUnavailableAsset malGetUnavailableAsset;

    private String mediumPhotoFileName;

    private String jpgPhotoFileName;
    
    private String thumbnailFileName;
    
    private String mediumFileName;
    
    private String largeFileName;
    
    private String logoJPGFileName;
    
    private String logoPNGFileName;

    private String xlFileName;

    public static MALAsset fromMALGetModifiedPropertyPhotos(final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset )
    {
        final MALAsset malAsset = new MALAsset();
        malAsset.setAssetId( malModifiedPropertyPhotoAsset.getAssetId() );
        malAsset.setMalModifiedPropertyPhotoAsset( malModifiedPropertyPhotoAsset );
        return malAsset;
    }

    public static MALAsset fromMALGetAsset( final MALGetAsset malGetAsset )
    {
        final MALAsset malAsset = new MALAsset();
        malAsset.setAssetId( malGetAsset.getAssetId() );
        malAsset.setMalGetAsset( malGetAsset );
        return malAsset;
    }

    public static MALAsset fromMALGetUnavailableAsset(final MALGetUnavailableAsset malGetUnavailableAsset)
    {
        final MALAsset malAsset = new MALAsset();
        malAsset.setAssetId( malGetUnavailableAsset.getAssetId() );
        malAsset.setMalGetUnavailableAsset( malGetUnavailableAsset );
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

    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public void setThumbnailFileName(String thumbnailFileName) {
        this.thumbnailFileName = thumbnailFileName;
    }

    public String getMediumFileName() {
        return mediumFileName;
    }

    public void setMediumFileName(String mediumFileName) {
        this.mediumFileName = mediumFileName;
    }

    public String getLargeFileName() {
        return largeFileName;
    }

    public void setLargeFileName(String largeFileName) {
        this.largeFileName = largeFileName;
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

    public String getXlFileName() {
        return xlFileName;
    }

    public void setXlFileName(String xlFileName) {
        this.xlFileName = xlFileName;
    }

    public String getMALAssetId()
    {
        return assetId;
    }

    public void setAssetId( final String assetId )
    {
        this.assetId = assetId;
    }
}
