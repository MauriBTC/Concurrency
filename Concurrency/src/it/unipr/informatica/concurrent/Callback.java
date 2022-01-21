/*
 * Callback
 * 
 * Interfaccia implementata dagli utilizzatori degli oggetti attivi 
 * per ricevere i risultati delle chiamate asincrone.
 * 
 */
package it.unipr.informatica.concurrent;

public interface Callback<T> {
	public void onResult(T result);

	public void onFailure(Throwable throwable);
}
