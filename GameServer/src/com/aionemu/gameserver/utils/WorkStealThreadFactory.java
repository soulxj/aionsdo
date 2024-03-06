package com.aionemu.gameserver.utils;

import com.aionemu.commons.utils.concurrent.PriorityThreadFactory;
import com.aionemu.commons.utils.internal.chmv8.ForkJoinPool;
import com.aionemu.commons.utils.internal.chmv8.ForkJoinPool.ForkJoinWorkerThreadFactory;
import com.aionemu.commons.utils.internal.chmv8.ForkJoinWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkStealThreadFactory extends PriorityThreadFactory implements ForkJoinWorkerThreadFactory
{
	public WorkStealThreadFactory(String namePrefix) {
		super(namePrefix, Thread.NORM_PRIORITY);
	}
	
	public void setDefaultPool(ForkJoinPool pool) {
		if (pool == null)
			pool = ForkJoinPool.commonPool();
		super.setDefaultPool(pool);
	}
	
	@Override
	public ForkJoinPool getDefaultPool() {
		return (ForkJoinPool) super.getDefaultPool();
	}
	
	@Override
	public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
		return new WorkStealThread(pool);
	}
	
	private static class WorkStealThread extends ForkJoinWorkerThread {
		private static final Logger log = LoggerFactory.getLogger(WorkStealThread.class);
		public WorkStealThread(ForkJoinPool pool) {
			super(pool);
		}
		@Override
		protected void onStart() {
			super.onStart();
		}
		@Override
		protected void onTermination(Throwable exception) {
			if (exception != null)
				log.error("Error - Thread: " + this.getName() + " terminated abnormaly: " + exception);
			super.onTermination(exception);
		}
	}
}