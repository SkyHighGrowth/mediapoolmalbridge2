package com.brandmaker.mediapoolmalbridge.service.brandmaker;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.AbstractUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.TaskPriorityExecutorWrapper;
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
