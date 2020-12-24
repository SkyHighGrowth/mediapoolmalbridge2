package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.App;
import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.controller.HomeController;
import com.brandmaker.mediapoolmalbridge.service.FileService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * {@inheritDoc}
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private final AppConfig appConfig;

    public FileServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> listFiles() {
        File directory = new File(appConfig.getExcelDir());
        File[] files = directory.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File f : files) {
            if (f.isFile() && f.exists() && f.getName().endsWith(".xlsx")) {
                fileNames.add(f.getName());
            }
        }
        return fileNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCurrentDownloadFolderSize() {
        File tempDir = new File(appConfig.getTempDir());
        long size = 0;
        try (Stream<Path> walkStream = Files.walk(tempDir.toPath())) {
            size = walkStream
                    .filter(p -> p.toFile().isFile())
                    .mapToLong(p -> p.toFile().length())
                    .sum();
        } catch (IOException e) {
            logger.error(String.format("Could not calculate %s size", tempDir.getName()), e);
        }
        return ((size/1000)/1000)/1000;
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
