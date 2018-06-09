package com.thord.atb.http;

public class HTTPBody {
	// @formatter:off
	private static final String TEMPLATER_FORECAST = "" +
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" + 
			"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" + 
			"  <soap12:Body>" + 
			"    <getUserRealTimeForecastByStop xmlns=\"http://miz.it/infotransit\">" + 
			"      <auth>" + 
			"        <user>%s</user>" + 
			"        <password>%s</password>" + 
			"      </auth>" + 
			"      <busStopId>%d</busStopId>" + 				
			"    </getUserRealTimeForecastByStop>" + 
			"  </soap12:Body>" + 
			"</soap12:Envelope>";
	// @formatter:on

	// @formatter:off
	private static final String TEMPLATE_BUSSTOPS = "" +
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" + 
			"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" + 
			"  <soap12:Body>" + 
			"    <GetBusStopsList xmlns=\"http://miz.it/infotransit\">" + 
			"      <auth>" + 
			"        <user>%s</user>" + 
			"        <password>%s</password>" + 
			"      </auth>" + 
			"    </GetBusStopsList>" + 
			"  </soap12:Body>" + 
			"</soap12:Envelope>";
	// @formatter:on

	/**
	 * Get {@code HTTP} body for {@code getUserRealTimeForecastByStop} request
	 * 
	 * @param nodeId   - Node to get foecasts for
	 * @param username - ATB API Username
	 * @param password - ATB API Password
	 * @return HTTP Body
	 */
	public static String getFoecastRequest(int nodeId, String username, String password) {
		return String.format(TEMPLATER_FORECAST, username, password, nodeId);
	}

	/**
	 * Get {@code HTTP} body for {@code GetBusStopsList} request
	 * 
	 * @param username - ATB API Username
	 * @param password - ATB API Password
	 * @return HTTP Body
	 */
	public static String getBusStopRequest(String username, String password) {
		return String.format(TEMPLATE_BUSSTOPS, username, password);
	}
}
