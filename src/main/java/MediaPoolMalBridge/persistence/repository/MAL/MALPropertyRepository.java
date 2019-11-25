package MediaPoolMalBridge.persistence.repository.MAL;

import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface MALPropertyRepository extends PagingAndSortingRepository<MALPropertyEntity, Long> {

    public Optional<MALPropertyEntity> findByPropertyId(final String propertyId);

    public List<MALPropertyEntity> findByBrand( final String brand );
}
