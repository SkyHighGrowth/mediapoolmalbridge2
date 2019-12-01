package MediaPoolMalBridge.persistence.entity.enums.asset;

public enum TransferringAssetStatus {
    INVALID,
    ASSET_OBSERVED,
    FILE_DOWNLOADING,
    FILE_DOWNLOADED,
    FILE_UPLOADING,
    FILE_UPLOADED,
    FILE_DELETING,
    FILE_DELETED,
    METADATA_UPLOADING,
    METADATA_UPLOADED,
    METADATA_DOWNLOADING,
    METADATA_DOWNLOADED,
    DONE,
    ERROR
}
