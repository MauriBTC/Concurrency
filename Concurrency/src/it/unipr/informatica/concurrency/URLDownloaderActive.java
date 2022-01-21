/*
 * URLDownloaderActive
 *
 * Classe che implementa un servizio di download mediante un oggetto
 * attivo.
 * 
 */
package it.unipr.informatica.concurrency;

import it.unipr.informatica.concurrent.AbstractActive;
import it.unipr.informatica.concurrent.Callback;
import it.unipr.informatica.concurrent.Future;
import it.unipr.informatica.concurrent.Task;
import it.unipr.informatica.concurrency.BufferedURLDownloader;
import it.unipr.informatica.concurrency.URLDownloader;

public class URLDownloaderActive extends AbstractActive {
	protected URLDownloader downloader;

	public URLDownloaderActive() {
		this(1);
	}

	public URLDownloaderActive(int poolSize) {
		super(poolSize);

		this.downloader = new BufferedURLDownloader();
	}

	public Future<byte[]> download(String url) {
		if (url == null)
			throw new IllegalArgumentException("invalid url");

		Future<byte[]> future = new Future<>();

		//passa come argomento alla add() (di AbstractActive) un new Task() per il quale fornisce l'implementazione della run() trattandosi di un Runnable
		add(new Task() { 
			@Override
			public void run() {
				try {
					byte[] data = downloader.download(url);

					future.setValue(data);
				} catch (Throwable throwable) {
					future.setThrowable(throwable);
				}
			}
		});

		return future;
	}

	public void download(String url, Callback<byte[]> callback) {
		if (url == null)
			throw new IllegalArgumentException("invalid url");

		if (callback == null)
			throw new IllegalArgumentException("invalid callback");

		add(new Task() {
			@Override
			public void run() {
				try {
					byte[] data = downloader.download(url);

					callback.onResult(data);
				} catch (Throwable throwable) {
					callback.onFailure(throwable);
				}
			}
		});
	}
}
