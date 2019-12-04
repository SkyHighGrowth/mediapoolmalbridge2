package MediaPoolMalBridge.service.BrandMaker;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.AbstractUniqueThreadService;

/**
 * Base class for services that submit task to {@link org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor}
 */
public abstract class AbstractBMUniqueThreadService extends AbstractUniqueThreadService {

    protected void reportErrorOnResponse(final String assetId, final AbstractBMResponse abstractBMResponse) {
        final String message = String.format("Can not perform operation [%s] for asset with id [%s], with error message [%s] and warnings [%s]", getClass().getName(), assetId, abstractBMResponse.getErrorAsString(), abstractBMResponse.getWarningsAsString());
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
        reportsRepository.save( reportsEntity );
    }
}
