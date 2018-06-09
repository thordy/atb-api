package com.thord.atb.soap;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thord.atb.exception.AtBException;

/**
 * Client for executing {@code SOAP} requests
 * 
 * @author thordy
 */
public class SOAPClient {
	private static final Logger logger = LogManager.getLogger(SOAPClient.class);

	private final SOAPConnectionFactory factory;

	public SOAPClient() throws SOAPException {
		this.factory = SOAPConnectionFactory.newInstance();
	}

	/**
	 * Execute a {@code SOAP} request
	 * 
	 * @param url     - SOAP endpoint
	 * @param message - Message to send
	 * @param action  - SOAP action to execute
	 * @return String returned from request
	 * @throws AtBException
	 */
	public String execute(String url, SOAPMessage message, String action) throws AtBException {
		long start = System.currentTimeMillis();
		try {
			SOAPConnection connection = factory.createConnection();

			MimeHeaders headers = message.getMimeHeaders();
			headers.addHeader("SOAPAction", action);

			logger.debug("Sending the following message: {}", serialize(message));
			SOAPMessage soapResponse = connection.call(message, url);
			connection.close();
			logger.info("Execution Time: {}ms", (System.currentTimeMillis() - start));

			String response = serialize(soapResponse);
			logger.debug("Got the following response: {}", response);
			return response;
		} catch (SOAPException ex) {
			throw new AtBException(ex);
		}
	}

	/**
	 * Serialize a {@link SOAPMessage} to string
	 * 
	 * @param message - Message to serialize
	 * @return Serialized message
	 * @throws AtBException If message cannot be serialized
	 */
	private String serialize(SOAPMessage message) throws AtBException {
		try (OutputStream os = new SOAPOutputStream();) {
			message.writeTo(os);
			return os.toString();
		} catch (SOAPException | IOException ex) {
			throw new AtBException("Unable to serialzie message", ex);
		}
	}
}