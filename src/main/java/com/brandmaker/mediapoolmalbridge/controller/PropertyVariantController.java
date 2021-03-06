package com.brandmaker.mediapoolmalbridge.controller;

import com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants.MALPropertyVariant;
import com.brandmaker.mediapoolmalbridge.service.PropertyVariantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller for managing property variants
 */
@Controller
public class PropertyVariantController {

    private static final String PROPERTY_VARIANTS_PAGE = "propertyVariantsPage";
    private static final String PROPERTY_VARIANTS = "propertyVariants";

    private final PropertyVariantService propertyVariantService;

    public PropertyVariantController(PropertyVariantService propertyVariantService) {
        this.propertyVariantService = propertyVariantService;
    }

    /**
     * List all property variants
     *
     * @param model {@link Model}
     * @return list of all property variants
     */
    @GetMapping("/listPropertyVariants")
    public String listPropertyVariants(Model model) {
        model.addAttribute(PROPERTY_VARIANTS, propertyVariantService.getPropertyVariantsSorted());
        return PROPERTY_VARIANTS_PAGE;
    }

    /**
     * Export property variants file
     *
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("/exportPropertyVariantsFile")
    public void export(HttpServletResponse response) {
        propertyVariantService.exportPropertyVariantsAsFile(response);
    }

    /**
     * Save property variant and list the latest property variants
     *
     * @param malPropertyVariant {@link MALPropertyVariant}
     * @param model              {@link Model}
     * @return list of all property variants
     */
    @PostMapping("/updatePropertyVariants")
    public String updatePropertyVariants(
            @ModelAttribute MALPropertyVariant malPropertyVariant, @RequestParam(required = false) String isDelete, Model model) {
        if (isDelete == null || isDelete.equals("false")) {
            model.addAttribute(PROPERTY_VARIANTS, propertyVariantService.savePropertyVariant(malPropertyVariant, model));
        } else if (isDelete.equals("true")) {
            model.addAttribute(PROPERTY_VARIANTS, propertyVariantService.deletePropertyVariant(malPropertyVariant, model));
        }
        return PROPERTY_VARIANTS_PAGE;
    }

}
