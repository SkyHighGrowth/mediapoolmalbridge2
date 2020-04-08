package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for {@link ReportsEntity}
 */
public interface ReportsRepository extends PagingAndSortingRepository<ReportsEntity, Long> {

    List<ReportsEntity> findAllByReportToAndSentNotAndCreatedIsAfter(final ReportTo reportTo, final boolean sent, final LocalDateTime created, final Pageable page);

    @Transactional
    @Modifying
    @Query( "delete from ReportsEntity re " +
            "where re.created < :dateTime " )
    void deleteRowsInThePast(final LocalDateTime dateTime );
}
