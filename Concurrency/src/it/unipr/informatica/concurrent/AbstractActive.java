/*
 * AbstractActive
 * 
 * Classe base astratta per la realizzazione di oggetti attivi.
 * 
 */
package it.unipr.informatica.concurrent;

public class AbstractActive implements Active {
	protected ThreadPool pool;

	protected AbstractActive() {
		this(1);
	}

	protected AbstractActive(int poolSize) {
		if (poolSize < 1)
			throw new IllegalArgumentException("invalid poolSize");

		this.pool = new ThreadPool(poolSize);
	}

	@Override
	public void stop() throws InterruptedException {
		pool.stop();
	}

	protected void add(Task task) {
		pool.add(task);
	}
}
