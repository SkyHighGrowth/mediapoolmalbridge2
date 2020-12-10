package com.brandmaker.mediapoolmalbridge.service;

import com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants.MALPropertyVariant;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Property variants service for get, save and export of property variants
 */
public interface PropertyVariantService {

    /**
     * Get all property variants sorted by Id number
     *
     * @return map of all property variants
     */
    Map<String, MALPropertyVariant> getPropertyVariantsSorted();

    /**
     * Export property variants as json file
     *
     * @param response {@link HttpServletResponse}
     */
    void exportPropertyVariantsAsFile(HttpServletResponse response);

    /**
     * Save Mal Property Variant
     *
     * @param malPropertyVariant {@link MALPropertyVariant}
     * @return map of all latest version of property variants
     */
    Map<String, MALPropertyVariant> savePropertyVariant(MALPropertyVariant malPropertyVariant);

    /**
     * Delete Mal Property Variant
     *
     * @param malPropertyVariant {@link MALPropertyVariant}
     * @return map of all latest version of property variants without the deleted property variant
     */
    Map<String, MALPropertyVariant> deletePropertyVariant(MALPropertyVariant malPropertyVariant);
}
