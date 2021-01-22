package com.brandmaker.mediapoolmalbridge.service;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.StructuresEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;

import java.util.List;

/**
 * Service with methods for all structures.
 */
public interface StructuresService {
    /**
     * Method that saves list of {@link StructuresEntity} objects in database.
     *
     * @param structures List {@link StructuresEntity}
     */
    void saveStructures(List<StructuresEntity> structures);

    /**
     * Method that returns all structures with a specific {@link StructureType}
     * @param structureType {@link StructureType}
     * @return List {@link StructuresEntity}
     */
    List<StructuresEntity> getAllStructuresByStructureType(StructureType structureType);

    /**
     * Method that return {@link StructuresEntity} object searched by its structure id and structure type.
     * @param structureId String structure id
     * @param structureType {@link StructureType}
     * @return {@link StructuresEntity}
     */
    StructuresEntity getStructureByStructureIdAndStructureType(String structureId, StructureType structureType);

    /**
     * This method deletes all structures from {@link StructuresEntity} table
     */
    void deleteAllStructures();
}
