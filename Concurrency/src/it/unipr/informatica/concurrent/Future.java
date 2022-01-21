/*
 * Future
 * 
 * Classe usata dagli utilizzatori degli oggetti attivi per leggere i
 * risultati delle chiamate asincrone.
 * 
 */
package it.unipr.informatica.concurrent;

public class Future<T> {
	private T value;

	private Throwable throwable;

	public Future() {
		this.value = null;

		this.throwable = null;
	}

	public synchronized void setValue(T value) {
		if (value == null)
			throw new IllegalArgumentException("invalid value");

		this.value = value;

		notifyAll(); //perchè una get() fatta troppo presto addormenta il thread in cui viene chiamata
	}

	public synchronized void setThrowable(Throwable throwable) {
		if (throwable == null)
			throw new IllegalArgumentException("invalid throwable");

		this.throwable = throwable;

		notifyAll();
	}

	public synchronized T get() throws FutureException, InterruptedException {
		while (value == null && throwable == null)
			wait();

		//se throwable diverso da null significa che è stata lanciata un'ecc con la setThrowable, perciò viene lanciata una FutureException dalla get()
		if (throwable != null) 
			throw new FutureException(throwable);

		return value;
	}
}
