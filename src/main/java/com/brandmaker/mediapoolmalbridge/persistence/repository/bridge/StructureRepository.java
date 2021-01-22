package com.brandmaker.mediapoolmalbridge.persistence.repository.bridge;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.StructuresEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository class for {@link StructuresEntity}
 */
public interface StructureRepository extends JpaRepository<StructuresEntity, Long> {
    /**
     * This method return {@link StructuresEntity} searched by structure id.
     *
     * @param structureId String structure id
     * @return {@link StructuresEntity}
     */
    StructuresEntity findByStructureIdAndStructureType(String structureId, StructureType structureType);

    /**
     * Method that returns all structures with a specific {@link StructureType}
     *
     * @param structureType {@link StructureType}
     * @return List {@link StructuresEntity}
     */
    List<StructuresEntity> findAllByStructureType(StructureType structureType);
}
