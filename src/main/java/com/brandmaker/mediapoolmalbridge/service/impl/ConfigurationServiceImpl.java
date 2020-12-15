package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.brandmaker.mediapoolmalbridge.service.ConfigurationService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.BMExchangeAssetMetadataSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.BMUploadAssetSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.database.assetresolver.BridgeDatabaseAssetResolverSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.database.assetunlocker.BridgeDatabaseUnlockerSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.database.rowsdeleter.BridgeDatabaseRowsDeleterSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.deletefiles.BridgeDeleteFilesSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.excelcreator.BridgeCreateExcelFileSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.exceluploader.BridgeUploadExcelFilesSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.ktistotheme.BridgeTransferThemeSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.sendmail.BridgeSendMailSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.MALAssetsSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.MALDownloadAssetSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.MALGetAssetStructureSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.properties.MALGetPropertiesSchedulerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@inheritDoc}
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    public static final String IS_SUCCESS = "isSuccess";
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final AppConfig appConfig;

    private final MALAssetsSchedulerService malAssetsSchedulerService;
    private final MALDownloadAssetSchedulerService malDownloadAssetSchedulerService;
    private final MALGetAssetStructureSchedulerService malGetAssetStructureSchedulerService;
    private final MALGetPropertiesSchedulerService malGetPropertiesSchedulerService;

    private final BridgeCreateExcelFileSchedulerService bridgeCreateExcelFileSchedulerService;
    private final BridgeDatabaseAssetResolverSchedulerService bridgeDatabaseAssetResolverSchedulerService;
    private final BridgeDatabaseUnlockerSchedulerService bridgeDatabaseUnlockerSchedulerService;
    private final BridgeDatabaseRowsDeleterSchedulerService bridgeDatabaseRowsDeleterSchedulerService;
    private final BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService;
    private final BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService;
    private final BridgeSendMailSchedulerService bridgeSendMailSchedulerService;
    private final BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService;

    private final BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService;
    private final BMUploadAssetSchedulerService bmUploadAssetSchedulerService;

    /**
     * ConfigurationController constructor
     *
     * @param appConfig                                   {@link AppConfig}
     * @param malAssetsSchedulerService                   {@link MALAssetsSchedulerService}
     * @param malDownloadAssetSchedulerService            {@link MALDownloadAssetSchedulerService}
     * @param malGetAssetStructureSchedulerService        {@link MALGetAssetStructureSchedulerService}
     * @param malGetPropertiesSchedulerService            {@link MALGetPropertiesSchedulerService}
     * @param bridgeCreateExcelFileSchedulerService       {@link BridgeCreateExcelFileSchedulerService}
     * @param bridgeDatabaseAssetResolverSchedulerService {@link BridgeDatabaseAssetResolverSchedulerService}
     * @param bridgeDatabaseUnlockerSchedulerService      {@link BridgeDatabaseUnlockerSchedulerService}
     * @param bridgeDatabaseRowsDeleterSchedulerService   {@link BridgeDatabaseRowsDeleterSchedulerService}
     * @param bridgeDeleteFilesSchedulerService           {@link BridgeDeleteFilesSchedulerService}
     * @param bridgeTransferThemeSchedulerService         {@link BridgeTransferThemeSchedulerService}
     * @param bridgeSendMailSchedulerService              {@link BridgeSendMailSchedulerService}
     * @param bridgeUploadExcelFilesSchedulerService      {@link BridgeUploadExcelFilesSchedulerService}
     * @param bmExchangeAssetMetadataSchedulerService     {@link BMExchangeAssetMetadataSchedulerService}
     * @param bmUploadAssetSchedulerService               {@link BMUploadAssetSchedulerService}
     */
    public ConfigurationServiceImpl(AppConfig appConfig,
                                    MALAssetsSchedulerService malAssetsSchedulerService,
                                    MALDownloadAssetSchedulerService malDownloadAssetSchedulerService,
                                    MALGetAssetStructureSchedulerService malGetAssetStructureSchedulerService,
                                    MALGetPropertiesSchedulerService malGetPropertiesSchedulerService,
                                    BridgeCreateExcelFileSchedulerService bridgeCreateExcelFileSchedulerService,
                                    BridgeDatabaseAssetResolverSchedulerService bridgeDatabaseAssetResolverSchedulerService,
                                    BridgeDatabaseUnlockerSchedulerService bridgeDatabaseUnlockerSchedulerService,
                                    BridgeDatabaseRowsDeleterSchedulerService bridgeDatabaseRowsDeleterSchedulerService,
                                    BridgeDeleteFilesSchedulerService bridgeDeleteFilesSchedulerService,
                                    BridgeTransferThemeSchedulerService bridgeTransferThemeSchedulerService,
                                    BridgeSendMailSchedulerService bridgeSendMailSchedulerService,
                                    BridgeUploadExcelFilesSchedulerService bridgeUploadExcelFilesSchedulerService,
                                    BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService,
                                    BMUploadAssetSchedulerService bmUploadAssetSchedulerService) {
        this.appConfig = appConfig;
        this.malAssetsSchedulerService = malAssetsSchedulerService;
        this.malDownloadAssetSchedulerService = malDownloadAssetSchedulerService;
        this.malGetAssetStructureSchedulerService = malGetAssetStructureSchedulerService;
        this.malGetPropertiesSchedulerService = malGetPropertiesSchedulerService;
        this.bridgeCreateExcelFileSchedulerService = bridgeCreateExcelFileSchedulerService;
        this.bridgeDatabaseAssetResolverSchedulerService = bridgeDatabaseAssetResolverSchedulerService;
        this.bridgeDatabaseUnlockerSchedulerService = bridgeDatabaseUnlockerSchedulerService;
        this.bridgeDatabaseRowsDeleterSchedulerService = bridgeDatabaseRowsDeleterSchedulerService;
        this.bridgeDeleteFilesSchedulerService = bridgeDeleteFilesSchedulerService;
        this.bridgeTransferThemeSchedulerService = bridgeTransferThemeSchedulerService;
        this.bridgeSendMailSchedulerService = bridgeSendMailSchedulerService;
        this.bridgeUploadExcelFilesSchedulerService = bridgeUploadExcelFilesSchedulerService;
        this.bmExchangeAssetMetadataSchedulerService = bmExchangeAssetMetadataSchedulerService;
        this.bmUploadAssetSchedulerService = bmUploadAssetSchedulerService;
    }

    /**
     * {@inheritDoc}
     */
    public AppConfigData getAppConfigData() {
        AppConfigData appConfigData = appConfig.getAppConfigData();
        try {
            appConfigData.setMalPrioritiesString(new ObjectMapper().writeValueAsString(appConfigData.getMalPriorities()));
            appConfigData.setIncludedAssetTypesString(new ObjectMapper().writeValueAsString(appConfigData.getIncludedAssetTypes()));
        } catch (JsonProcessingException e) {
            logger.error("Parsing of Mal priorities and Included asset types values failed! ", e);
        }
        return appConfigData;
    }

    /**
     * {@inheritDoc}
     */
    public void exportAppConfigurationProperties(HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String myString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appConfig.getAppConfigData());
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment;filename=application.properties.json");
            ServletOutputStream out = response.getOutputStream();
            out.println(myString);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public AppConfigData saveAppConfigData(AppConfigData appConfigData, Model model) {
        AppConfigData currentConfigData = getAppConfigData();

        ObjectMapper objectMapper = new ObjectMapper();
        String applicationPropertyFile = appConfig.getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.APPLICATION_PROPERTIES_JSON;


        TypeReference<HashMap<String, String>> typeRefIncludedAssetTypes
                = new TypeReference<HashMap<String, String>>() {
        };
        try {
            Map<String, String> includedAssetTypes = objectMapper.readValue(appConfigData.getIncludedAssetTypesString(), typeRefIncludedAssetTypes);
            appConfigData.setIncludedAssetTypes(includedAssetTypes);
        } catch (JsonProcessingException e) {
            logger.error("Parsing of Included asset types values failed! ", e);
            model.addAttribute(IS_SUCCESS, false);
        }


        TypeReference<HashMap<Integer, List<String>>> typeRefMalPriorities
                = new TypeReference<HashMap<Integer, List<String>>>() {
        };
        try {
            Map<Integer, List<String>> malPriorities = objectMapper.readValue(appConfigData.getMalPrioritiesString(), typeRefMalPriorities);
            appConfigData.setMalPriorities(malPriorities);
        } catch (JsonProcessingException e) {
            logger.error("Parsing of Mal priorities values failed! ", e);
            model.addAttribute(IS_SUCCESS, false);
        }

        File propertiesFile = new File(applicationPropertyFile);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(propertiesFile, appConfigData);
        } catch (IOException e) {
            logger.error(String.format("Saving in %s failed!", applicationPropertyFile), e);
            model.addAttribute(IS_SUCCESS, false);
        }


        updateCronjobTriggers(appConfigData);
        if (model != null) {
            Object attribute = model.getAttribute(IS_SUCCESS);
            if (attribute == null || !attribute.equals(false)) {
                appConfig.setAppConfigData(appConfigData);
                model.addAttribute(IS_SUCCESS, true);
            } else {
                //if update failed return previous values
                try {
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(propertiesFile, currentConfigData);
                } catch (IOException e) {
                    logger.error(String.format("Saving in %s failed! (Revert changes)", applicationPropertyFile), e);
                }
            }
        }
        return appConfigData;
    }

    private void updateCronjobTriggers(AppConfigData appConfigData) {
        malAssetsSchedulerService.jobSchedule(new CronTrigger(appConfigData.getMalAssetCronExpression()));
        malDownloadAssetSchedulerService.jobSchedule(new CronTrigger(appConfigData.getMalDownloadAssetCronExpression()));
        malGetAssetStructureSchedulerService.jobSchedule(new CronTrigger(appConfigData.getMalAssetStructureCronExpression()));
        malGetPropertiesSchedulerService.jobSchedule(new CronTrigger(appConfigData.getMalPropertiesCronExpression()));

        bridgeCreateExcelFileSchedulerService.jobScheduleExcel(new CronTrigger(appConfigData.getBridgeExcelFilesCronExpression()));
        bridgeDatabaseAssetResolverSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBridgeAssetResolverCronExpression()));
        bridgeDatabaseUnlockerSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBridgeAssetOnBoardingCronExpression()));
        bridgeDatabaseRowsDeleterSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBridgeDatabaseRowsDeleterCronExpression()));
        bridgeTransferThemeSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBridgeTransferThemeCronExpression()));
        bridgeSendMailSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBridgeSendMailCronExpression()));
        bridgeDeleteFilesSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBridgeDeleteFileCronExpression()));
        bridgeUploadExcelFilesSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBridgeUploadExcelFilesCronExpression()));

        bmExchangeAssetMetadataSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBmExchangeSchedulerCronExpression()));
        bmUploadAssetSchedulerService.jobSchedule(new CronTrigger(appConfigData.getBmUploadSchedulerCronExpression()));
    }
}
