package com.brandmaker.mediapoolmalbridge.constants;

/**
 * Contanstants
 */
public class Constants {

    private Constants(){}

    /**
     * prefix appended to downloaded file if thumbnail link is downloaded
     */
    public static final String THUMBNAIL_FILE_PREFIX = "thumbnail_";

    /**
     * prefix appended to download file if medium link is downloaded
     */
    public static final String MEDIUM_FILE_PREFIX = "medium_";

    /**
     * prefix appended to download file if large link is downloaded
     */
    public static final String LARGE_FILE_PREFIX = "large_";

    /**
     * prefix appended to download file if high link is downloaded
     */
    public static final String HIGH_FILE_PREFIX = "high_";

    /**
     * prefix appended to downloaded file if logo JPG link is downloaded
     */
    public static final String LOGO_JPG_FILE_PREFIX = "logoJpg_";

    /**
     * prefix appended to download file if logo png link is downloaded
     */
    public static final String LOGO_PNG_FILE_PREFIX = "logoPng_";

    public static final String SPLITTER = "____";

    /**
     * prefix appended to download file if extra large link is downloaded
     */
    public static final String XL_FILE_PREFIX = "xl_";

    /**
     * name of directory used application in ${user.home} directory
     */
    public static final String APPLICATION_DIR = "marriott";

    /**
     * name of directory used to store downloaded files in ${user.home} directory
     */
    public static final String ASSET_DOWNLOAD_DIR = "temp";

    /**
     * log folder
     */
    public static final String LOGS = "logs";

    /**
     * name of directory used to store generated excel files in ${user.home} directory
     */
    public static final String EXCEL_DIR = "excel";

    /**
     * name of directory used to store application properties in ${user.home} directory
     */
    public static final String APPLICATION_PROPERTIES_JSON = "application.properties.json";

    /**
     * Name of the file used for configuration of property variants in ${user.home} directory
     */
    public static final String PROPERTY_VARIANTS_JSON = "propertyVariants.json";
}
