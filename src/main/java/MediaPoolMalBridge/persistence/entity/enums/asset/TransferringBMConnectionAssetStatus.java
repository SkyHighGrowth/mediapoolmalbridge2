package MediaPoolMalBridge.persistence.entity.enums.asset;

public enum TransferringBMConnectionAssetStatus {
    INVALID,
    FILE_UPLOADING,
    FILE_UPLOADED,
    FILE_DELETING,
    FILE_DELETED,
    FILE_CREATING,
    FILE_CREATED,
    METADATA_UPLOADING,
    METADATA_UPLOADED,
    METADATA_DOWNLOADING,
    METADATA_DOWNLOADED,
    DONE,
    ERROR
}
