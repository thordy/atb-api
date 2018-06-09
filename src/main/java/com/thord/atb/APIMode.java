package com.thord.atb;

/**
 * Enum to specify how requests should be executed
 * 
 * @author thordy
 */
public enum APIMode {
	/** Requests are executed as a simple HTTP POST */
	SIMPLE_HTTP,
	/** Requests are executed using the Java SOAP API */
	SOAP;
}
