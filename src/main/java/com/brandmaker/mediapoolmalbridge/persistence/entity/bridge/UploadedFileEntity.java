package com.brandmaker.mediapoolmalbridge.persistence.entity.bridge;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.FileStateOnDisc;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Table where files to be deleted are stored
 */
@Entity
@Table( name = "files_to_be_deleted",
        indexes = { @Index( columnList = "deleted" ),
                    @Index( columnList = "file_state_on_disc" ),
                    @Index( columnList = "created" ) } )
public class UploadedFileEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id")
    private long id;

    @Column( name = "filename" )
    private String filename;

    @Column( name = "created" )
    @CreationTimestamp
    private LocalDateTime created;

    @Column( name = "deleted" )
    private boolean deleted;

    @Column( name = "file_state_on_disc" )
    @Enumerated( EnumType.STRING )
    private FileStateOnDisc fileStateOnDisc;

    public UploadedFileEntity() {
        deleted = false;
        fileStateOnDisc = FileStateOnDisc.NO_ERROR;
    }

    public UploadedFileEntity( final String filename )
    {
        this.filename = filename;
        deleted = false;
        fileStateOnDisc = FileStateOnDisc.NO_ERROR;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public FileStateOnDisc getFileStateOnDisc() {
        return fileStateOnDisc;
    }

    public void setFileStateOnDisc(FileStateOnDisc fileStateOnDisc) {
        this.fileStateOnDisc = fileStateOnDisc;
    }
}
