package com.brandmaker.mediapoolmalbridge.service.mal;

import com.brandmaker.mediapoolmalbridge.persistence.repository.mal.MALPropertyRepository;
import com.brandmaker.mediapoolmalbridge.service.AbstractUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Class that implements common fields and properties for MAL server unique thread services
 */
public abstract class AbstractMALUniqueThreadService extends AbstractUniqueThreadService {

    @Autowired
    @Qualifier( "MALTaskExecutorWrapper" )
    private TaskExecutorWrapper taskExecutorWrapper;

    @Autowired
    protected MALPropertyRepository malPropertyRepository;

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
}
