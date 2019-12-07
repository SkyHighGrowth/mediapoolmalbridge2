package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.FileStateOnDisc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository class for {@link UploadedFileEntity}
 */
public interface UploadedFileRepository extends CrudRepository<UploadedFileEntity, Long> {

    List<UploadedFileEntity> findAllByDeletedAndFileStateOnDiscNot(
            final boolean deleted, final FileStateOnDisc fileStateOnDisc, final Pageable page );
}
