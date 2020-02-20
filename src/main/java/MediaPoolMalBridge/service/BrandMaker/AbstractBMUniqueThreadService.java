package MediaPoolMalBridge.service.BrandMaker;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.AbstractUniqueThreadService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import MediaPoolMalBridge.tasks.TaskPriorityExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Base class for services that submit task to {@link org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor}
 */
public abstract class AbstractBMUniqueThreadService extends AbstractUniqueThreadService {

    @Autowired
    @Qualifier( "BMTaskPriorityExecutorWrapper" )
    protected TaskPriorityExecutorWrapper taskExecutorWrapper;

    @Override
    protected TaskExecutorWrapper getTaskExecutorWrapper() {
        return taskExecutorWrapper;
    }

    @Override
    protected int getTaskExecutorQueueSize() {
        return taskExecutorWrapper.getQueueSize();
    }

    @Override
    protected int getTaskExecutorMaxQueueSize() {
        return taskExecutorWrapper.getMaximalQueueSize();
    }

    protected void reportErrorOnResponse(final String assetId, final AbstractBMResponse abstractBMResponse) {
        final String message = String.format("Can not perform operation [%s] for asset with id [%s], with error message [%s] and warnings [%s]", getClass().getName(), assetId, abstractBMResponse.getErrorAsString(), abstractBMResponse.getWarningsAsString());
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetId, message, ReportTo.BM, null, null, null );
        reportsRepository.save( reportsEntity );
    }
}
