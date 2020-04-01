package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.FileStateOnDisc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for {@link UploadedFileEntity}
 */
public interface UploadedFileRepository extends CrudRepository<UploadedFileEntity, Long> {

    List<UploadedFileEntity> findByDeletedAndFileStateOnDisc(
            final boolean deleted, final FileStateOnDisc fileStateOnDisc, final Pageable page );

    List<UploadedFileEntity> findByDeletedAndFileStateOnDiscAndCreatedIsBefore(
            final boolean deleted, final FileStateOnDisc fileStateOnDisc, final LocalDateTime created, final Pageable page );

    @Transactional
    @Modifying
    @Query( "delete from UploadedFileEntity ufe " +
            "where ufe.created < :dateTime ")
    void deleteRowsInThePast(final LocalDateTime dateTime );
}
