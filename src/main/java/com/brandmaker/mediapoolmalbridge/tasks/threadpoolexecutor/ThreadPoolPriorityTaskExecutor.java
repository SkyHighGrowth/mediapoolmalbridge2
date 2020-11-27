package com.brandmaker.mediapoolmalbridge.tasks.threadpoolexecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class ThreadPoolPriorityTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
        return new PriorityBlockingQueue<>(queueCapacity);
    }
}
