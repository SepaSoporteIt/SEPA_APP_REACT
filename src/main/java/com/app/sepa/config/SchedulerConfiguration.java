package com.app.sepa.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class SchedulerConfiguration implements SchedulingConfigurer {
    private final int POOL_SIZE = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler newThreadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        newThreadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        newThreadPoolTaskScheduler.setThreadNamePrefix("multi-pool scheduler: ");
        newThreadPoolTaskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(newThreadPoolTaskScheduler);
    }
}
