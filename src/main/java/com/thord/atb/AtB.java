package com.thord.atb;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.thord.atb.data.json.BusStopsListResult;
import com.thord.atb.data.json.RealTimeForecastByStopResult;
import com.thord.atb.exception.AtBException;
import com.thord.atb.http.HTTPBody;
import com.thord.atb.http.HTTPClient;
import com.thord.atb.soap.SOAPClient;
import com.thord.atb.soap.SOAPHelper;

public class AtB {
  private static final Logger logger = LogManager.getLogger(AtB.class);

  private final static String ATB_API_URL = "http://st.atb.no/New/InfoTransit/UserServices.asmx";
  private final static String INFO_TRANSIT_URL = "http://miz.it/infotransit";

  /**
   * XPath expression used to get {@code JSON} value from {@code SOAP} response
   */
  private final static String XPATH_EXPR = "//*[local-name()='%s']";

  private final XPath xpath = XPathFactory.newInstance().newXPath();
  private final ObjectMapper MAPPER = new ObjectMapper();

  private final SOAPClient soapClient;
  private final HTTPClient httpClient;
  /** API Execution mode */
  private final APIMode mode;

  private final Cache<Integer, RealTimeForecastByStopResult> cacheForecast;
  private final Cache<String, BusStopsListResult> cacheStops;

  private final String username;
  private final String password;

  /**
   * Create a new {@code AtB API} instance with default values
   * 
   * @param username - ATB API Username
   * @param password - ATB API Password
   * @throws AtBException If SOAP Client cannot be initialized
   */
  public AtB(String username, String password) throws AtBException {
    this(username, password, APIMode.SOAP);
  }

  /**
   * Create a new {@code AtB API} instance
   * 
   * @param username - ATB API Username
   * @param password - ATB API Password
   * @param mode - Execution mode
   * @throws AtBException If SOAP Client cannot be initialized
   */
  public AtB(String username, String password, APIMode mode) throws AtBException {
    this(username, password, 60, 86400, mode);
  }

  /**
   * Create a new {@code AtB API} instance
   * 
   * @param username - ATB API Username
   * @param password - ATB API Password
   * @param forecastCacheExpiry - Cache time for forecast request
   * @param busStopCacheExpiry - Cache time for busstop requests
   * @param mode - Execution mode
   * @throws AtBException If SOAP Client cannot be initialized
   */
  public AtB(String username, String password, int forecastCacheExpiry, int busStopCacheExpiry,
      APIMode mode) throws AtBException {
    this.username = username;
    this.password = password;

    try {
      this.soapClient = new SOAPClient();
    } catch (SOAPException e) {
      throw new AtBException("Unable to intialize SOAP client ", e);
    }
    this.httpClient = new HTTPClient();
    this.cacheForecast =
        CacheBuilder.newBuilder().expireAfterWrite(forecastCacheExpiry, TimeUnit.SECONDS)
            .build(new CacheLoader<Integer, RealTimeForecastByStopResult>() {
              @Override
              public RealTimeForecastByStopResult load(Integer key) throws AtBException {
                return getUserRealTimeForecastByStop(key);
              }
            });
    this.cacheStops =
        CacheBuilder.newBuilder().maximumSize(1)
            .expireAfterWrite(busStopCacheExpiry, TimeUnit.SECONDS)
            .build(new CacheLoader<String, BusStopsListResult>() {
              @Override
              public BusStopsListResult load(String key) throws AtBException {
                return getBusStopsList();
              }
            });
    this.mode = mode;
  }

  /**
   * Get bus schedule for the given nodeId
   * http://st.atb.no/New/InfoTransit/UserServices.asmx?op=getUserRealTimeForecastByStop
   * 
   * @param nodeId - ID of bus stop node to get forecast for
   * @return {@link RealTimeForecastByStopResult}
   */
  public RealTimeForecastByStopResult getUserRealTimeForecastByStop(int nodeId) throws AtBException {
    RealTimeForecastByStopResult result = cacheForecast.getIfPresent(nodeId);
    if (result == null) {
      String action = "getUserRealTimeForecastByStop";
      try {
        logger.info("Loading forecasts for stop {}", nodeId);

        String response;
        if (mode == APIMode.SIMPLE_HTTP) {
          response =
              httpClient.execute(ATB_API_URL,
                  HTTPBody.getFoecastRequest(nodeId, username, password));
        } else {
          SOAPMessage message = SOAPHelper.createMessage();
          SOAPEnvelope envelope = SOAPHelper.createEnvelope(message);
          SOAPBody body = envelope.getBody();
          body.setPrefix("soap12");

          SOAPElement element = body.addChildElement(action, null, INFO_TRANSIT_URL);
          SOAPHelper.addAuthElement(element, username, password);

          SOAPElement stopElement = element.addChildElement("busStopId");
          stopElement.setValue(String.valueOf(nodeId));
          message.saveChanges();

          response = soapClient.execute(ATB_API_URL, message, INFO_TRANSIT_URL + "/" + action);
        }
        result =
            MAPPER.readValue(getJSON(response, action + "Result"),
                RealTimeForecastByStopResult.class);
        logger.debug("Caching forecast for {}", nodeId);
        cacheForecast.put(nodeId, result);
      } catch (SOAPException | IOException | XPathExpressionException ex) {
        throw new AtBException("Unable to getUserRealTimeForecastByStop", ex);
      }
    } else {
      logger.debug("Got forecasts for {} from cache", nodeId);
    }
    return result;
  }

  /**
   * Get a list of all available bus stops
   * http://st.atb.no/New/InfoTransit/UserServices.asmx?op=GetBusStopsList
   * 
   * @return {@link BusStopsListResult}
   */
  public BusStopsListResult getBusStopsList() throws AtBException {
    BusStopsListResult result = cacheStops.getIfPresent("stops");
    if (result != null) {
      String action = "GetBusStopsList";
      try {
        logger.info("Loading busstops");

        String response;
        if (mode == APIMode.SIMPLE_HTTP) {
          response =
              httpClient.execute(ATB_API_URL, HTTPBody.getBusStopRequest(username, password));
        } else {
          SOAPMessage message = SOAPHelper.createMessage();
          SOAPEnvelope envelope = SOAPHelper.createEnvelope(message);
          SOAPBody body = envelope.getBody();
          body.setPrefix("soap12");

          SOAPElement element = body.addChildElement(action, null, INFO_TRANSIT_URL);
          SOAPHelper.addAuthElement(element, username, password);
          message.saveChanges();

          response = soapClient.execute(ATB_API_URL, message, INFO_TRANSIT_URL + "/" + action);
        }

        result = MAPPER.readValue(getJSON(response, action + "Result"), BusStopsListResult.class);
        logger.debug("Caching busstops");
        cacheStops.put("stops", result);
      } catch (SOAPException | IOException | XPathExpressionException ex) {
        throw new AtBException("Unable to GetBusStopsList", ex);
      }
    }
    return result;
  }

  /**
   * Get {@code JSON} value from the SOAP response
   * 
   * @param xml - SOAP XML
   * @param node - Name of node containing JSON
   * @return JSON value from given node
   * @throws XPathExpressionException If expression cannot be evaluated.
   */
  private String getJSON(String xml, String node) throws XPathExpressionException {
    return xpath.evaluate(String.format(XPATH_EXPR, node), new InputSource(new StringReader(xml)));
  }
}
