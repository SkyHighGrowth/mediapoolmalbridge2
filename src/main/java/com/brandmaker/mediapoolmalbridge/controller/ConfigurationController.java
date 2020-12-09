package com.brandmaker.mediapoolmalbridge.controller;

import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.service.ConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller for get, update and export configuration properties
 */
@Controller
public class ConfigurationController {

    private final ConfigurationService configurationService;

    private static final String HOME_TEMPLATE = "configurationPage";

    /**
     * ConfigurationController constructor
     *
     * @param configurationService {@link ConfigurationService}
     */
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    /**
     * List all configuration properties
     *
     * @param model {@link Model}
     * @return all configuration properties
     */
    @GetMapping("/listConfigProperties")
    public String listConfigProperties(Model model) {
        model.addAttribute("configProperties", configurationService.getAppConfigData());
        return HOME_TEMPLATE;
    }

    /**
     * Export configuration properties as json file
     *
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("/exportAppConfigFile")
    public void export(HttpServletResponse response) {
        configurationService.exportAppConfigurationProperties(response);
    }


    /**
     * Update configuration properties
     *
     * @param appConfigData {@link AppConfigData}
     * @param model         {@link Model}
     * @return list of updated configuration properties
     */
    @PostMapping("/updateConfigurationProperties")
    public String updateConfigurationProperties(
            @ModelAttribute AppConfigData appConfigData, Model model) {
        model.addAttribute("configProperties", configurationService.saveAppConfigData(appConfigData));
        return HOME_TEMPLATE;
    }

}
