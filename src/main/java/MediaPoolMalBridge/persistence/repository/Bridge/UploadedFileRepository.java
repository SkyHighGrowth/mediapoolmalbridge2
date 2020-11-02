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

    @Query( "select ufe "+
            "from UploadedFileEntity ufe " +
            "left join AssetEntity as a on a.fileNameOnDisc = ufe.filename " +
            "where (ufe.deleted = :deleted  and ufe.created < :created) or (a.malStatesRepetitions = :repetitions and ufe.deleted = :deleted)")
    List<UploadedFileEntity> findByDeletedAndCreatedIsBefore(
            final boolean deleted, final LocalDateTime created, final Integer repetitions, final Pageable page );

    @Transactional
    @Modifying
    @Query( "delete from UploadedFileEntity ufe " +
            "where ufe.created < :dateTime ")
    void deleteRowsInThePast(final LocalDateTime dateTime );
}
