package MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_5;

import MediaPoolMalBridge.model.MAL.propertyvariants.MALPropertyVariant;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;

public class MALPropertyPair {
    private MALPropertyEntity malPropertyEntity;
    private MALPropertyVariant malPropertyVariant;

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
