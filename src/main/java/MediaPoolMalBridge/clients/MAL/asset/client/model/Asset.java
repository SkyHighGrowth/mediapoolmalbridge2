package MediaPoolMalBridge.clients.MAL.asset.client.model;

import com.google.gson.annotations.SerializedName;

public class Asset {

    @SerializedName( "asset_id" )
    private String assetId;

    @SerializedName( "asset_type_id" )
    private String assetTypeId;

    private String association;

    private String filename;

    // will be null if asset is a property asset
    @SerializedName( "brand_id")
    private String brandId;

    // will be null if asset is a brand asset
    @SerializedName( "property_id" )
    private String propertyId;

    // will be null if asset is a brand asset
    @SerializedName( "marsha_code" )
    private String marshaCode;

    private String name;

    private String caption;

    private String description;

    @SerializedName( "limited_rights" )
    private boolean limitedRights;

    @SerializedName( "rights_managed" )
    private boolean rightsManaged;

    @SerializedName( "usageDescription" )
    private String usageDescription;

    private String instructions;

    @SerializedName( "collection_id" )
    private String collectionId;

    @SerializedName( "destination_id" )
    private String destionationId;

    @SerializedName( "subject_id" )
    private String subjectId;

    @SerializedName( "color_id" )
    private String colorId;

    @SerializedName( "file_type_id" )
    private String fileTypeId;

    private boolean zipped;

    //valid values: active, inactive, deactivated, report_card
    private String status;

    //"2011-02-03 05:14:01",
    @SerializedName( "date_created" )
    private String dateCreated;

    //"2011-05-25 13:49:31",
    @SerializedName( "last_modified" )
    private String lastModified;

    private String metadata;

    @SerializedName( "thumbnail_url" )
    private String thumbnailUrl;

    @SerializedName( "medium_url" )
    private String mediumUrl;

    @SerializedName( "large_url" )
    private String largeUrl;

    @SerializedName( "logo_jpg_url" )
    private String logoJpgUrl;

    @SerializedName( "logo_png_url" )
    private String logoPngUrl;

    @SerializedName( "xl_url" )
    private String xlUrl;

    @SerializedName( "offer_name" )
    private String offerName;

    @SerializedName( "offer_url" )
    private String offerUrl;

    private int width;

    private int height;

    @SerializedName( "is_stock" )
    private boolean isStock;

    @SerializedName( "mime_type")
    private String mimeType;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMarshaCode() {
        return marshaCode;
    }

    public void setMarshaCode(String marshaCode) {
        this.marshaCode = marshaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLimitedRights() {
        return limitedRights;
    }

    public void setLimitedRights(boolean limitedRights) {
        this.limitedRights = limitedRights;
    }

    public boolean isRightsManaged() {
        return rightsManaged;
    }

    public void setRightsManaged(boolean rightsManaged) {
        this.rightsManaged = rightsManaged;
    }

    public String getUsageDescription() {
        return usageDescription;
    }

    public void setUsageDescription(String usageDescription) {
        this.usageDescription = usageDescription;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getDestionationId() {
        return destionationId;
    }

    public void setDestionationId(String destionationId) {
        this.destionationId = destionationId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public boolean isZipped() {
        return zipped;
    }

    public void setZipped(boolean zipped) {
        this.zipped = zipped;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getLogoJpgUrl() {
        return logoJpgUrl;
    }

    public void setLogoJpgUrl(String logoJpgUrl) {
        this.logoJpgUrl = logoJpgUrl;
    }

    public String getLogoPngUrl() {
        return logoPngUrl;
    }

    public void setLogoPngUrl(String logoPngUrl) {
        this.logoPngUrl = logoPngUrl;
    }

    public String getXlUrl() {
        return xlUrl;
    }

    public void setXlUrl(String xlUrl) {
        this.xlUrl = xlUrl;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferUrl() {
        return offerUrl;
    }

    public void setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isStock() {
        return isStock;
    }

    public void setStock(boolean stock) {
        isStock = stock;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
