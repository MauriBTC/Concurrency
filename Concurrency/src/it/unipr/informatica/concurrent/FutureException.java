/*
 * FutureException
 * 
 * Eccezione lanciata in situazioni anomale dagli oggetti di classe 
 * Future.
 * 
 */
package it.unipr.informatica.concurrent;

public class FutureException extends Exception {
	private static final long serialVersionUID = -4046610017202686920L;

	public FutureException(Throwable cause) {
		super(cause);

		if (cause == null)
			throw new IllegalArgumentException("invalid cause");
	}
}
