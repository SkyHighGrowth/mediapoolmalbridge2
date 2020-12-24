package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.service.FileService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.download.MALDownloadAssetService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class FileServiceImpl implements FileService {
    private final AppConfig appConfig;

    private final MALDownloadAssetService malDownloadAssetService;

    public FileServiceImpl(AppConfig appConfig, MALDownloadAssetService malDownloadAssetService) {
        this.appConfig = appConfig;
        this.malDownloadAssetService = malDownloadAssetService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> listFiles() {
        File directory = new File(appConfig.getExcelDir());
        File[] files = directory.listFiles();
        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.exists() && file.getName().endsWith(".xlsx")) {
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getCurrentDownloadFolderSize() {
        float size = malDownloadAssetService.getCurrentDownloadFolderSize();
        return ((size / 1000) / 1000) / 1000;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exportExcelFile(HttpServletResponse response, String filename) {
        try {
            String path = appConfig.getExcelDir();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            try (Workbook workbook = new XSSFWorkbook(path + filename)) {
                workbook.write(response.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
