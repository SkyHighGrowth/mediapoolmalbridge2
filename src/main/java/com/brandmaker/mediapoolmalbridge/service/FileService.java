package com.brandmaker.mediapoolmalbridge.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Service for listing, exporting and calculating size of files.
 */
public interface FileService {
    /**
     * Method that list files from excel directory
     * @return List of files
     */
    public List<String> listFiles();

    /**
     * Method that calculates download folder size
     * @return total size of files
     */
    public long getCurrentDownloadFolderSize();

    /**
     * Method that export excel file
     * @param response {@link HttpServletResponse}
     * @param filename String filename
     */
    public void exportExcelFile(HttpServletResponse response, String filename);
}
