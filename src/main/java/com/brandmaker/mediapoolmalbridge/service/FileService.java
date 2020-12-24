package com.brandmaker.mediapoolmalbridge.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Service for listing, exporting and calculating size of files.
 */
public interface FileService {
    /**
     * Method that list files from excel directory
     *
     * @return List of files
     */
    List<String> listFiles();

    /**
     * Method that calculates download folder size
     *
     * @return total size of files
     */
    float getCurrentDownloadFolderSize();

    /**
     * Method that export excel file
     *
     * @param response {@link HttpServletResponse}
     * @param filename String filename
     */
    void exportExcelFile(HttpServletResponse response, String filename);
}
