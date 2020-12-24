package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class that wraps MAL server single asset response structure
 */
@JsonPropertyOrder({ "assetId", "assetTypeId", "associations", "filename",
                    "brandId", "propertyId", "marshaCode", "name", "caption",
                    "description", "limitedRights", "rightsManaged", "usageDescription",
                    "instructions", "collectionId", "destionationId", "subjectId",
                    "colorId", "fileTypeId", "zipped", "status", "dateCreated",
                    "lastModified", "metadata", "thumbnailUrl", "mediumUrl", "highUrl",
                    "largeUrl", "logoJpgUrl", "logoPngUrl", "xlUrl", "offerName",
                    "offerUrl", "width", "height", "isStock", "mimeType" })
public class MALGetAsset {

    /**
     * MAL asset id
     */
    @SerializedName("asset_id")
    private String assetId;

    /**
     * MAL asset type id
     */
    @SerializedName("asset_type_id")
    private String assetTypeId;

    /**
     * MAL associations
     */
    private String association;

    /**
     * MAL file file name that represents asset
     */
    private String filename;

    /**
     * MAL asset brand id
     */
    // will be null if asset is a property asset
    @SerializedName("brand_id")
    private String brandId;

    /**
     * MAL asset property id
     */
    // will be null if asset is a brand asset
    @SerializedName("property_id")
    private String propertyId;

    /**
     * MAL asset marsha code
     */
    // will be null if asset is a brand asset
    @SerializedName("marsha_code")
    private String marshaCode;

    /**
     * MAL asset name
     */
    private String name;

    /**
     * MAL asset caption
     */
    private String caption;

    /**
     * MAL asset description
     */
    private String description;

    /**
     * MAL asset limited rights
     */
    @SerializedName("limited_rights")
    private boolean limitedRights;

    /**
     * MAL asset rights managed
     */
    @SerializedName("rights_managed")
    private boolean rightsManaged;

    /**
     * MAL asset usage description
     */
    @SerializedName("usage_description")
    private String usageDescription;

    /**
     * MAL asset instructions
     */
    private String instructions;

    /**
     * MAL asset collection id
     */
    @SerializedName("collection_id")
    private String collectionId;

    /**
     * MAL asset destination id
     */
    @SerializedName("destination_id")
    private String destionationId;

    /**
     * MAL asset subject id
     */
    @SerializedName("subject_id")
    private String subjectId;

    /**
     * MAL asset color id
     */
    @SerializedName("color_id")
    private String colorId;

    /**
     * MAL asset file type id
     */
    @SerializedName("file_type_id")
    private String fileTypeId;

    /**
     * MAL asset zipped
     */
    private boolean zipped;

    /**
     * MAL asset status
     */
    //valid values: active, inactive, deactivated, report_card
    private String status;

    /**
     * MAL asset date created
     */
    //"2011-02-03 05:14:01",
    @SerializedName("date_created")
    private String dateCreated;

    /**
     * MAL asset date modified
     */
    //"2011-05-25 13:49:31",
    @SerializedName("last_modified")
    private String lastModified;

    /**
     * MAL asset metadata
     */
    private String metadata;

    /**
     * MAL asset thumbnail url
     */
    @SerializedName("thumbnail_url")
    private String thumbnailUrl;

    /**
     * MAL asset medium url
     */
    @SerializedName("medium_url")
    private String mediumUrl;

    /**
     * MAL asset high url
     */
    @SerializedName("high_url")
    private String highUrl;

    /**
     * MAL asset large url
     */
    @SerializedName("large_url")
    private String largeUrl;

    /**
     * MAL asset logo jpg url
     */
    @SerializedName("logo_jpg_url")
    private String logoJpgUrl;

    /**
     * MAL asset logo png url
     */
    @SerializedName("logo_png_url")
    private String logoPngUrl;

    /**
     * MAL asset xl url
     */
    @SerializedName("xl_url")
    private String xlUrl;

    /**
     * MAL asset offer name
     */
    @SerializedName("offer_name")
    private String offerName;

    /**
     * MAL asset offer url
     */
    @SerializedName("offer_url")
    private String offerUrl;

    /**
     * MAL asset width
     */
    private int width;

    /**
     * MAL asset height
     */
    private int height;

    /**
     * MAL asset is stock
     */
    @SerializedName("is_stock")
    private boolean isStock;

    /**
     * MAL asset mime type
     */
    @SerializedName("mime_type")
    private String mimeType;

    @Expose(deserialize = false, serialize = false)
    private boolean hws;

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

    public String getHighUrl() {
        return highUrl;
    }

    public void setHighUrl(String highUrl) {
        this.highUrl = highUrl;
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

    public boolean isHws() {
        return hws;
    }

    public void setHws(boolean hws) {
        this.hws = hws;
    }
}
