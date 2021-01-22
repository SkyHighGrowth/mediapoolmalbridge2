package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.StructuresEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.StructureRepository;
import com.brandmaker.mediapoolmalbridge.service.StructuresService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class StructuresServiceImpl implements StructuresService {
    private final StructureRepository structureRepository;

    public StructuresServiceImpl(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveStructures(List<StructuresEntity> structures) {
        for (StructuresEntity structure : structures) {
            StructuresEntity structuresEntity = structureRepository.findByStructureIdAndStructureType(structure.getStructureId(), structure.getStructureType());
            if (structuresEntity == null) {
                StructuresEntity entity = new StructuresEntity();
                entity.setStructureId(structure.getStructureId());
                entity.setStructureName(structure.getStructureName());
                entity.setStructureType(structure.getStructureType());
                structureRepository.save(entity);
            } else {
                if (!structuresEntity.getStructureName().equals(structure.getStructureName())) {
                    structuresEntity.setStructureName(structure.getStructureName());
                    structureRepository.save(structuresEntity);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StructuresEntity> getAllStructuresByStructureType(StructureType structureType) {
        return structureRepository.findAllByStructureType(structureType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StructuresEntity getStructureByStructureIdAndStructureType(String structureId, StructureType structureType) {
        return structureRepository.findByStructureIdAndStructureType(structureId, structureType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllStructures() {
        structureRepository.deleteAll();
    }
}
