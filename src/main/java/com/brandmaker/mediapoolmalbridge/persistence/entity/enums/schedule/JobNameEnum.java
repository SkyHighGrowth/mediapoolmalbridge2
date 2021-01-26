package com.brandmaker.mediapoolmalbridge.persistence.entity.enums.schedule;

public enum JobNameEnum {

    ASSET_METADATA_DOWNLOAD_JOB("MALAssetsSchedulerService", "Asset metadata download job"),
    NEW_BRAND_ASSET_METADATA_DOWNLOAD_JOB("MalNewBrandAssetsSchedulerService", "New brand asset metadata download job"),
    ASSET_FILE_DOWNLOAD_JOB("MALDownloadAssetSchedulerService", "Asset file download job"),
    ASSET_STRUCTURE_JOB("MALGetAssetStructureSchedulerService", "Asset structure job"),
    PROPERTIES_JOB("MALGetPropertiesSchedulerService", "Properties job"),

    CREATE_EXCEL_FILE_JOB("BridgeCreateExcelFileSchedulerService", "Create excel file job"),
    ASSET_DATABASE_RESOLVER("BridgeDatabaseAssetResolverSchedulerService", "Asset resolver job"),
    ON_BOARDING_JOB("BridgeDatabaseUnlockerSchedulerService", "On boarding job"),
    DATABASE_CLEANER_JOB("BridgeDatabaseRowsDeleterSchedulerService", "Database cleaner job"),
    THEME_TRANSFER_JOB("BridgeTransferThemeSchedulerService", "Theme transfer job"),
    SEND_REPORT_EMAIL_JOB("BridgeSendMailSchedulerService", "Send report email job"),
    DELETE_FILES_JOB("BridgeDeleteFilesSchedulerService", "Delete files job"),
    UPLOAD_EXCEL_FILES_JOB("BridgeUploadExcelFilesSchedulerService", "Upload excel files job"),

    METADATA_EXCHANGE_JOB("BMExchangeAssetMetadataSchedulerService", "Metadata exchange job"),
    UPLOAD_ASSET_FILES_JOB("BMUploadAssetSchedulerService", "Upload asset files job");

    private final String className;
    private final String jobName;

    JobNameEnum(String className, String jobName) {
        this.className = className;
        this.jobName = jobName;
    }

    public String getClassName() {
        return className;
    }

    public String getJobName() {
        return jobName;
    }

    public static String getJobName(String simpleName) {
        for (JobNameEnum jobNameEnum : values()) {
            if (jobNameEnum.getClassName().equals(simpleName)) {
                return jobNameEnum.getJobName();
            }
        }
        return null;
    }
}
