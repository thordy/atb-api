package com.thord;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.thord.atb.APIMode;
import com.thord.atb.AtB;
import com.thord.atb.data.json.BusStop;
import com.thord.atb.data.json.BusStopsListResult;
import com.thord.atb.data.json.Forecast;
import com.thord.atb.data.json.RealTimeForecastByStopResult;

public class App {
  private static final Logger logger = LogManager.getLogger(App.class);

  public static void main(String[] args) throws Exception {
    // Initialize a new instance of the API
    AtB api = new AtB("<username>", "<password>", APIMode.SOAP);

    // Get a list of all bus stops
    // BusStopsListResult stops = api.getBusStopsList();
    // Get bus schedule for a stop matching the given id
    // RealTimeForecastByStopResult result = api.getUserRealTimeForecastByStop(<bus_stop_id>);

    RealTimeForecastByStopResult result = api.getUserRealTimeForecastByStop(16010402);
    printSchedule(result.getStop(), result.getForecasts());
  }

  private static void printSchedule(BusStop stop, List<Forecast> forecasts) {
    // @formatter:off
		String TIMETABLE = ""
				+ "%s | Registered       | Scheduled \n" 
				+ "---------------------|------------------|-----------------\n" 
				+ "%s %s         | %s | %s\n" 
				+ "%s %s         | %s | %s\n" 
				+ "%s %s         | %s | %s\n" 
				+ "%s %s         | %s | %s\n" 
				+ "%s %s         | %s | %s";
		// @formatter:on

    String timetable =
        stop.getDescriptionNormalized() + " | " + "Registered       | Scheduled        \n";
    int pad = stop.getDescriptionNormalized().length();
    timetable += StringUtils.leftPad("", pad, "-") + "-|------------------|-----------------\n";

    for (Forecast f : forecasts) {
      timetable +=
          StringUtils.rightPad(StringUtils.rightPad(f.getLineID(), 2) + " " + f.getDestination(),
              pad)
              + " | "
              + f.getDepartureTimeRegistered()
              + " | "
              + f.getDepartureTimeScheduled()
              + "\n";
    }
    logger.info("\n{}", timetable);
  }
}
