package com.brandmaker.mediapoolmalbridge.service;

import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

/**
 * Service for get, update and export application configuration data
 */
public interface ConfigurationService {
    /**
     * Get application configuration data
     *
     * @return list of all configuration properties
     */
    AppConfigData getAppConfigData();

    /**
     * Export configuration propertis as json file
     *
     * @param response {@link HttpServletResponse}
     */
    void exportAppConfigurationProperties(HttpServletResponse response);

    /**
     * Save application configuration data
     *
     * @param appConfigData {@link AppConfigData}
     * @param model
     * @return list of all updated configuration properties
     */
    AppConfigData saveAppConfigData(AppConfigData appConfigData, Model model);
}
