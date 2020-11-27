package com.brandmaker.mediapoolmalbridge.service.bridge.excelcreator.excelfilesserver6_5;

import com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants.MALPropertyVariant;
import com.brandmaker.mediapoolmalbridge.persistence.entity.mal.MALPropertyEntity;

public class MALPropertyPair {
    private final MALPropertyEntity malPropertyEntity;
    private final MALPropertyVariant malPropertyVariant;

    public MALPropertyPair(MALPropertyEntity malPropertyEntity, MALPropertyVariant malPropertyVariant) {
        this.malPropertyEntity = malPropertyEntity;
        this.malPropertyVariant = malPropertyVariant;
    }

    public MALPropertyEntity getMalPropertyEntity() {
        return malPropertyEntity;
    }

    public MALPropertyVariant getMalPropertyVariant() {
        return malPropertyVariant;
    }
}
