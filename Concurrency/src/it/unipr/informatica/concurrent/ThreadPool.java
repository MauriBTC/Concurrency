package it.unipr.informatica.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
	private Thread[] workers;

	private List<Runnable> queue;

	private boolean stopped; //indica la volontà di chiudersi della pool

	private int activeWorkers;

	public ThreadPool(int size) {
		if (size < 1)
			throw new IllegalArgumentException("invalid size");

		this.stopped = false;

		this.activeWorkers = 0;

		this.queue = new LinkedList<>();

		this.workers = new Worker[size];

		for (int i = 0; i < size; ++i) {
			Worker worker = new Worker();

			this.workers[i] = worker;

			worker.start();
		}
	}

	public synchronized void stop() throws InterruptedException {
		while (activeWorkers != 0)
			wait();

		stopped = true;

		notifyAll();
	}

	public synchronized void add(Runnable runnable) {
		queue.add(runnable);

		notifyAll(); //dopo aver fatto una add sveglio i worker che potrebbero essersi addormentati avendo cercato di fare get con la coda vuota
	}

	private synchronized Runnable get() throws InterruptedException {
		while (!stopped && queue.isEmpty())
			wait();

		if (stopped)
			throw new InterruptedException("gracefully stopped");

		Runnable runnable = queue.remove(0); //FIFO

		notifyAll(); //perchè notifico tutti se la get() viene fatta da un worker??

		return runnable;
	}

	private synchronized void increaseActiveWorkers() {
		activeWorkers++;
	}

	private synchronized void decreaseActiveWorkers() {
		activeWorkers--;

		if (activeWorkers == 0)
			notifyAll();
	}

	private class Worker extends Thread {
		@Override
		public void run() {
			for (;;) {
				try {
					Runnable runnable = get();

					increaseActiveWorkers();

					runnable.run();
				} catch (InterruptedException ignored) {
					decreaseActiveWorkers();

					return;
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}

				decreaseActiveWorkers();
			}
		}
	}
}
