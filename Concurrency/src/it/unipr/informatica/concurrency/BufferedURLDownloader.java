/*
 * BufferedURLDownloader
 *
 * Classe che implementa un servizio di download con buffer.
 * 
*/
package it.unipr.informatica.concurrency;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BufferedURLDownloader implements URLDownloader {
	private static final int BUFFER_SIZE = 1024;

	@Override
	public byte[] download(String url) throws IOException {
		if (url == null)
			throw new IllegalArgumentException("invalid url");

		try (
			InputStream inputStream = new URL(url).openStream(); //se l'apertura dello stream va a buon fine (richiesta-risposta) permette di leggervi il content successivamente
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream); //creo ogg che salva inputStream per leggervi poi il contenuto
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream() //builder che memorizza i dati passati man mano e quando ho finito costruisce un array con dentro i dati
		) {
			//memorizzo 1KB di dati alla volta
			byte[] buffer = new byte[BUFFER_SIZE];

			int read = bufferedInputStream.read(buffer);

			//lo stream finisce quando il numero di byte letti è negativo
			while (read >= 0) { 
				outputStream.write(buffer, 0, read);

				read = bufferedInputStream.read(buffer);
			}

			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw exception;
		}
	}
}
