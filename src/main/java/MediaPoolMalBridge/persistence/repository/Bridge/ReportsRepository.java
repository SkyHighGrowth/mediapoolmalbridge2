package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

/**
 * Repository class for {@link ReportsEntity}
 */
public interface ReportsRepository extends PagingAndSortingRepository<ReportsEntity, Long> {

    Slice<ReportsEntity> findAllByReportToAndSentNotAndCreatedIsAfter(final ReportTo reportTo, final boolean sent, final LocalDateTime created, final Pageable page);
}
