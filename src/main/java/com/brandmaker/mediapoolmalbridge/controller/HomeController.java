package com.brandmaker.mediapoolmalbridge.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Controller
 */
@Controller
public class HomeController {
    private static final String HOME_TEMPLATE = "home";

    private final BuildProperties buildProperties;

    public HomeController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    /**
     * Home mapping
     *
     * @param model {@link Model}
     * @return root url
     */
    @GetMapping("/")
    public String listConfigProperties(Model model) {
        model.addAttribute("version", buildProperties.getVersion());
        return HOME_TEMPLATE;
    }
}
