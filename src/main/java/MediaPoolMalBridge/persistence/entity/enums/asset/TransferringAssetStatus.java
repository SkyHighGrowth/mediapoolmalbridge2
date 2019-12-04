package MediaPoolMalBridge.persistence.entity.enums.asset;

/**
 * Enum where current asset status is stored
 */
public enum TransferringAssetStatus {
    INVALID,
    ASSET_OBSERVED,
    FILE_DOWNLOADING,
    FILE_DOWNLOADED,
    GET_BM_ASSET_ID,
    GETTING_BM_ASSET_ID,
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
