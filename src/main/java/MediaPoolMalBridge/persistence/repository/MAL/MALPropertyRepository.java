package MediaPoolMalBridge.persistence.repository.MAL;

import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for {@link MALPropertyEntity}
 */
public interface MALPropertyRepository extends PagingAndSortingRepository<MALPropertyEntity, Long> {

    Optional<MALPropertyEntity> findByPropertyIdAndUpdatedIsAfter(final String propertyId, final LocalDateTime updated);

    Optional<MALPropertyEntity> findByPropertyId(final String propertyId);

    List<MALPropertyEntity> findByBrandAndUpdatedIsAfter( final String brand, final LocalDateTime updated );

    List<MALPropertyEntity> findAllByUpdatedIsAfter( final LocalDateTime updated );
}
