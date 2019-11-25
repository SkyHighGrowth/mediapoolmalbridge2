package MediaPoolMalBridge.persistence.repository;

import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReportsRepository extends PagingAndSortingRepository<ReportsEntity, Long> {

    Slice<ReportsEntity> findAllByReportToAndCreatedIsAfter(final ReportTo reportTo, final LocalDateTime created, final Pageable page);
}
