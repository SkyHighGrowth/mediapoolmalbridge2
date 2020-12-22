
package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for fetching asset data from api/syncs
 */
public class MALGetData {

    @SerializedName("asset_id")
    public String assetId;
    @SerializedName("asset_thumbnail_url")
    public String assetThumbnailUrl;
    @SerializedName("asset_type_id")
    public String assetTypeId;
    @SerializedName("association_name")
    public String associationName;
    @SerializedName("association")
    public String association;
    @SerializedName("brand_id")
    public String brandId;
    @SerializedName("caption")
    public String caption;
    @SerializedName("color_id")
    public String colorId;
    @SerializedName("date_activated")
    public String dateActivated;
    @SerializedName("date_created")
    public String dateCreated;
    @SerializedName("date_shot")
    public String dateShot;
    @SerializedName("deactivation_date")
    public String deactivationDate;
    @SerializedName("default_collection_id")
    public String defaultCollectionId;
    @SerializedName("description")
    public String description;
    @SerializedName("destination_id")
    public String destinationId;
    @SerializedName("extension")
    public String extension;
    @SerializedName("file_type_id")
    public String fileTypeId;
    @SerializedName("filename")
    public String filename;
    @SerializedName("instructions")
    public String instructions;
    @SerializedName("is_offer")
    public Boolean isOffer;
    @SerializedName("is_stock")
    public Boolean isStock;
    @SerializedName("last_modified")
    public String lastModified;
    @SerializedName("limited_rights")
    public Boolean limitedRights;
    @SerializedName("marketing_caption")
    public String marketingCaption;
    @SerializedName("mdam_id")
    public String mdamId;
    @SerializedName("metadata")
    public String metadata;
    @SerializedName("name")
    public String name;
    @SerializedName("old_filename")
    public String oldFilename;
    @SerializedName("property_id")
    public String propertyId;
    @SerializedName("rights_managed")
    public Boolean rightsManaged;
    @SerializedName("status")
    public String status;
    @SerializedName("subject_id")
    public String subjectId;
    @SerializedName("uploaded")
    public Boolean uploaded;
    @SerializedName("usage_description")
    public String usageDescription;
    @SerializedName("zipped")
    public Boolean zipped;
    @SerializedName("rights-documents")
    public MALRightsDocuments rightsDocuments;
    @SerializedName("collections")
    public MALCollections collections;
    @SerializedName("files")
    public MALFiles files;
    @SerializedName("property")
    public MALProperty property;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetThumbnailUrl() {
        return assetThumbnailUrl;
    }

    public void setAssetThumbnailUrl(String assetThumbnailUrl) {
        this.assetThumbnailUrl = assetThumbnailUrl;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(String dateActivated) {
        this.dateActivated = dateActivated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateShot() {
        return dateShot;
    }

    public void setDateShot(String dateShot) {
        this.dateShot = dateShot;
    }

    public String getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(String deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public String getDefaultCollectionId() {
        return defaultCollectionId;
    }

    public void setDefaultCollectionId(String defaultCollectionId) {
        this.defaultCollectionId = defaultCollectionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Boolean getOffer() {
        return isOffer;
    }

    public void setOffer(Boolean offer) {
        isOffer = offer;
    }

    public Boolean getStock() {
        return isStock;
    }

    public void setStock(Boolean stock) {
        isStock = stock;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Boolean getLimitedRights() {
        return limitedRights;
    }

    public void setLimitedRights(Boolean limitedRights) {
        this.limitedRights = limitedRights;
    }

    public String getMarketingCaption() {
        return marketingCaption;
    }

    public void setMarketingCaption(String marketingCaption) {
        this.marketingCaption = marketingCaption;
    }

    public String getMdamId() {
        return mdamId;
    }

    public void setMdamId(String mdamId) {
        this.mdamId = mdamId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldFilename() {
        return oldFilename;
    }

    public void setOldFilename(String oldFilename) {
        this.oldFilename = oldFilename;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public Boolean getRightsManaged() {
        return rightsManaged;
    }

    public void setRightsManaged(Boolean rightsManaged) {
        this.rightsManaged = rightsManaged;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getUsageDescription() {
        return usageDescription;
    }

    public void setUsageDescription(String usageDescription) {
        this.usageDescription = usageDescription;
    }

    public Boolean getZipped() {
        return zipped;
    }

    public void setZipped(Boolean zipped) {
        this.zipped = zipped;
    }

    public MALRightsDocuments getRightsDocuments() {
        return rightsDocuments;
    }

    public void setRightsDocuments(MALRightsDocuments rightsDocuments) {
        this.rightsDocuments = rightsDocuments;
    }

    public MALCollections getCollections() {
        return collections;
    }

    public void setCollections(MALCollections collections) {
        this.collections = collections;
    }

    public MALFiles getFiles() {
        return files;
    }

    public void setFiles(MALFiles files) {
        this.files = files;
    }

    public MALProperty getProperty() {
        return property;
    }

    public void setProperty(MALProperty property) {
        this.property = property;
    }
}
