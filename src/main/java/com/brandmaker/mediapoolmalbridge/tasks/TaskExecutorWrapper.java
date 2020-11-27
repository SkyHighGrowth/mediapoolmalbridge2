package com.brandmaker.mediapoolmalbridge.tasks;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Wrapper class for ThreadPoolTaskExecutor
 */
public class TaskExecutorWrapper implements DisposableBean {

    private int maximalQueueSize;

    private final ThreadPoolTaskExecutor taskExecutor;

    private final ReentrantLock reentrantLock = new ReentrantLock( true );

    public TaskExecutorWrapper(final ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public int getLockCount() { return reentrantLock.getHoldCount(); }

    public int getQueueSize()
    {
        return taskExecutor.getThreadPoolExecutor().getQueue().size();
    }

    public int getMaximalQueueSize() {
        return maximalQueueSize;
    }

    public void setMaximalQueueSize(int maximalQueueSize) {
        this.maximalQueueSize = maximalQueueSize;
    }

    public boolean canAcceptNewTask() {
        return getQueueSize() < maximalQueueSize;
    }

    @PostConstruct
    public void initialize() {
        taskExecutor.initialize();
    }

    public void shutdown() {
        taskExecutor.shutdown();
    }

    public void lock() { reentrantLock.lock(); }

    public void unlock() { reentrantLock.unlock(); }

    @Override
    public void destroy() {
        taskExecutor.destroy();
    }
}
