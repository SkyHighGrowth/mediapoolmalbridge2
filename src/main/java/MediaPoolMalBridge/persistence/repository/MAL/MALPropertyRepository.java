package MediaPoolMalBridge.persistence.repository.MAL;

import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.persistence.entity.enums.property.MALPropertyStatus;
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

    List<MALPropertyEntity> findByBrand( final String brand );

    List<MALPropertyEntity> findByBrandAndMalPropertyStatus( final String brand, final MALPropertyStatus malPropertyStatus );

    List<MALPropertyEntity> findAllByUpdatedIsAfter( final LocalDateTime updated );
}
