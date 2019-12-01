package MediaPoolMalBridge.persistence.entity.Bridge;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "files_to_be_deleted",
        indexes = { @Index( columnList = "deleted" ) } )
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

    public UploadedFileEntity() { }

    public UploadedFileEntity( final String filename )
    {
        this.filename = filename;
        deleted = false;
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
}
