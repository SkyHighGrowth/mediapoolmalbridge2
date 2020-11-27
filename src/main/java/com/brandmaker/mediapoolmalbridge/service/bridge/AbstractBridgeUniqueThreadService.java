package com.brandmaker.mediapoolmalbridge.service.bridge;

import com.brandmaker.mediapoolmalbridge.service.AbstractUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractBridgeUniqueThreadService extends AbstractUniqueThreadService {

    @Autowired
    @Qualifier( "MALTaskExecutorWrapper" )
    protected TaskExecutorWrapper taskExecutorWrapper;

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
