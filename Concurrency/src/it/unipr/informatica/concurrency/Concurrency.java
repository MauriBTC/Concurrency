/*
 * Concurrency
 * 
 * Esempio d'uso un servizio di download realizzato mediante un oggetto attivo.
 * 
 */
package it.unipr.informatica.concurrency;

import it.unipr.informatica.concurrent.Callback;
import it.unipr.informatica.concurrent.Future;
import it.unipr.informatica.concurrent.FutureException;

public class Concurrency {
	public static void main(String[] args) {
		URLDownloaderActive activeDownloader = new URLDownloaderActive(10);

		try {
			activeDownloader.download("https://www.google.com", new URLDownloaderCallback());

			activeDownloader.download("https://www.youtube.com", new URLDownloaderCallback());

			activeDownloader.download("https://www.ailab5.unipr.it", new URLDownloaderCallback());

			Future<byte[]> future1 = activeDownloader.download("https://www.ailab.unipr.it");

			Future<byte[]> future2 = activeDownloader.download("https://www.google.com");

			byte[] data = future1.get();

			System.out.println("Received future1 " + data.length + " bytes");

			data = future2.get();

			System.out.println("Received future2 " + data.length + " bytes");

			activeDownloader.stop();
		} catch (FutureException exception) {
			Throwable cause = exception.getCause();

			System.out.println("Failure: " + cause.getMessage());
		} catch (InterruptedException ignored) {
			// Blank
		}
	}

	private static class URLDownloaderCallback implements Callback<byte[]> { //statica perchè la chiamo direttamente da main
		@Override
		public void onResult(byte[] result) {
			System.out.println("Received " + result.length + " bytes");
		}

		@Override
		public void onFailure(Throwable throwable) {
			System.out.println("Failure: " + throwable.getMessage());
		}
	}
}
