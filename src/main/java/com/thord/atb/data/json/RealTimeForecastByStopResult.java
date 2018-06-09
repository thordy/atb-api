package com.thord.atb.data.json;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.thord.atb.data.json.deserializer.LocalDateTimeDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RealTimeForecastByStopResult {
	@JsonProperty
	private Integer total;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonProperty
	private LocalDateTime timeServer;
	@JsonProperty("InfoNodo")
	private List<BusStop> stops;
	@JsonProperty("Orari")
	private List<Forecast> forecasts;

	@JsonCreator
	public RealTimeForecastByStopResult() {
		// Jackson Creator
	}

	public Integer getTotal() {
		return total;
	}

	public LocalDateTime getTimeServer() {
		return timeServer;
	}

	public BusStop getStop() {
		return stops.get(0);
	}

	public List<BusStop> getStops() {
		return stops;
	}

	public List<Forecast> getForecasts() {
		return forecasts;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("total", total).add("timeServer", timeServer).add("stops", stops)
				.add("forecasts", forecasts).toString();
	}

}
