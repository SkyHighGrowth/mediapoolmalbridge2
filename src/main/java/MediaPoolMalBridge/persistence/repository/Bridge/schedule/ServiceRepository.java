package MediaPoolMalBridge.persistence.repository.Bridge.schedule;

import MediaPoolMalBridge.persistence.entity.Bridge.schedule.ServiceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Repository class for {@link ServiceEntity}
 */
public interface ServiceRepository extends PagingAndSortingRepository<ServiceEntity, Long> {

    @Transactional
    @Modifying
    @Query( "delete from ServiceEntity se " +
            "where se.created < :dateTime ")
    void deleteRowsInThePast( final LocalDateTime dateTime );
}
