package com.brandmaker.mediapoolmalbridge.persistence.entity.bridge;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;

import javax.persistence.*;

@Entity
@Table(name = "structures", uniqueConstraints =
@UniqueConstraint(columnNames = {"structure_id", "structure_name"}))
public class StructuresEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "structure_id")
    private String structureId;

    @Column(name = "structure_name")
    private String structureName;

    @Column(name = "structure_type")
    @Enumerated( EnumType.STRING )
    private StructureType structureType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public StructureType getStructureType() {
        return structureType;
    }

    public void setStructureType(StructureType structureType) {
        this.structureType = structureType;
    }
}