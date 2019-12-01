package MediaPoolMalBridge.persistence.repository.MAL;

import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MALPropertyRepository extends PagingAndSortingRepository<MALPropertyEntity, Long> {

    public Optional<MALPropertyEntity> findByPropertyIdAndUpdatedIsAfter(final String propertyId, final LocalDateTime updated);

    public Optional<MALPropertyEntity> findByPropertyId(final String propertyId);

    public List<MALPropertyEntity> findByBrandAndUpdatedIsAfter( final String brand, final LocalDateTime updated );

    public List<MALPropertyEntity> findAllByUpdatedIsAfter( final LocalDateTime updated );
}
