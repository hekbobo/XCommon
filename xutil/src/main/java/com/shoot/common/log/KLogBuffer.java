package com.shoot.common.log;

import java.util.concurrent.ArrayBlockingQueue;

public class KLogBuffer {
	private static final int QUEUE_SIZE = 200;
	private ArrayBlockingQueue<KLogBean> queue;

	public KLogBuffer() {
	}

	public void put(KLogBean bean) throws InterruptedException {
		if (queue == null) {
			return;
		}
		queue.put(bean);
	}

	public KLogBean take() throws InterruptedException {
		if (queue == null) {
			return null;
		}
		KLogBean bean =  queue.take();
		return bean;
	}

	public void enable() {
		queue = new ArrayBlockingQueue<KLogBean>(QUEUE_SIZE, true);
	}

	public void disable() {
		if (queue == null) {
			return;
		}
		queue.clear();
		queue = null;
	}
}
