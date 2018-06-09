package com.thord.atb.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import com.thord.atb.exception.AtBException;

/**
 * Client for executing a {@code HTTP POST} request, returning response as
 * {@code String}
 * 
 * @author thordy
 */
public class HTTPClient {
	private static final Logger logger = LogManager.getLogger(HTTPClient.class);

	private static final String SOAP_XML = "application/soap+xml";

	/**
	 * Execute a {@code HTTP POST} request
	 * 
	 * @param url  - URL endpoint
	 * @param body - POST Body
	 * @return String response from request
	 * @throws AtBException If request cannot be executed
	 */
	public String execute(String url, String body) throws AtBException {
		long start = System.currentTimeMillis();
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, SOAP_XML);
			connection.setDoOutput(true);

			logger.debug("Sending the following message: {}", body);
			try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), Charsets.UTF_8);) {
				writer.write(body);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));) {
				logger.info("Execution Time: {}ms", (System.currentTimeMillis() - start));
				return CharStreams.toString(br);
			}
		} catch (IOException ex) {
			throw new AtBException(ex);
		}
	}
}
