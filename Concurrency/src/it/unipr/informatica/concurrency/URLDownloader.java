/*
 * URLDownloader
 *
 * Interfaccia implementata dai servizi di download.
 * 
*/
package it.unipr.informatica.concurrency;

import java.io.IOException;

public interface URLDownloader {
	public byte[] download(String url) throws IOException;
}
