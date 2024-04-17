/*
 * This file is part of Eleanor project
 *
 * This is proprietary software. See the EULA file distributed with
 * this project for additional information regarding copyright ownership.
 *
 * Copyright (c) 2011-2013, Eleanor Team. All rights reserved.
 */
package com.eleanor.processors;


import com.aionemu.commons.utils.concurrent.AionRejectedExecutionHandler;
import com.aionemu.commons.utils.concurrent.RunnableWrapper;
import com.aionemu.gameserver.configs.main.ThreadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Base game processor component abstraction
 *
 * @author MetaWind
 */
public class AGameProcessor {

    /* Main processors logger*/
    protected static final Logger Log = LoggerFactory.getLogger(AGameProcessor.class);

    /* Maximal delay of scheduled tasks */
    private static final int MAX_DELAY = Integer.MAX_VALUE;

    /* Scheduled tasks pool */
    private ScheduledThreadPoolExecutor _processorPool;

    /**
     * Default constructor
     *
     * @param threadsCount thread pool size for this processor
     */
    protected AGameProcessor(int threadsCount) {

        _processorPool = new ScheduledThreadPoolExecutor(threadsCount);
        _processorPool.setRejectedExecutionHandler(new AionRejectedExecutionHandler());
        _processorPool.prestartAllCoreThreads();

    }

    public void execute(Runnable r) {
        _processorPool.execute(r);
    }

    /**
     * Post task to thread pool
     *
     * @param r     runnable task
     * @param delay execution dellay in milliseconds
     * @return running task
     */
    public ScheduledFuture<?> schedule(Runnable r, long delay) {

        r = new AGameProcessor.RunnableTaskWrapper(r);

        long validated = Math.max(0, Math.min(MAX_DELAY, delay));

        if (validated < delay)
            Log.warn("Determine attempt to post scheduled task with delay {}, but maximal is {}. " +
                            "Delay will be trimmed to maximal"
                    , delay, validated);

        delay = validated;

        return _processorPool.schedule(r, delay, TimeUnit.MILLISECONDS);

    }

    /**
     * Post task to thread pool
     *
     * @param r     runnable task
     * @param delay execution dellay in milliseconds
     * @return running task
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long delay, long period) {

        r = new AGameProcessor.RunnableTaskWrapper(r);

        long validated = Math.max(0, Math.min(MAX_DELAY, delay));

        if (validated < delay)
            Log.warn("Determine attempt to post scheduled task with delay {}, but maximal is {}. " +
                            "Delay will be trimmed to maximal"
                    , delay, validated);

        delay = validated;

        return _processorPool.scheduleAtFixedRate(r, delay, period, TimeUnit.MILLISECONDS);

    }

    /**
     * Post task to thread pool
     *
     * @param r     runnable task
     * @param delay execution delay in milliseconds
     * @return running task
     */
    public boolean schedule(Runnable r, long delay, Task out) {

        r = new AGameProcessor.RunnableTaskWrapper(r);

        long validated = Math.max(0, Math.min(MAX_DELAY, delay));

        if (validated < delay) {
            Log.warn("Determine attempt to post scheduled task with delay {}, but maximal is {}. Action will not be triggered"
                    , delay, validated);

            return false;
        }

        delay = validated;

        out.setTask(_processorPool.schedule(r, delay, TimeUnit.MILLISECONDS));

        return true;

    }

    private static final class RunnableTaskWrapper extends RunnableWrapper {

        private RunnableTaskWrapper(Runnable runnable) {
            super(runnable, ThreadConfig.MAXIMUM_RUNTIME_IN_MILLISEC_WITHOUT_WARNING);
        }
    }

    public static class Task {

        /* Tasks future */
        private ScheduledFuture<?> _task;

        public static Task create() {
            return new Task();
        }

        public ScheduledFuture getTask() {
            return _task;
        }

        /* Private set method */
        private void setTask(ScheduledFuture task) {
            _task = task;
        }
    }
}
