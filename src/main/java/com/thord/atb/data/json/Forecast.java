package com.thord.atb.data.json;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.thord.atb.data.json.deserializer.LocalDateTimeDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
	@JsonProperty("codAzLinea")
	private String lineID;
	@JsonProperty("descrizioneLinea")
	private String lineDescription;
	@JsonProperty("orario")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime departureTimeRegistered;
	@JsonProperty("orarioSched")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime departureTimeScheduled;
	@JsonProperty("statoPrevisione")
	private String statoPrevisione;
	@JsonProperty("capDest")
	private String destination;
	@JsonProperty("turnoMacchina")
	private String turnoMacchina;
	@JsonProperty("descrizionePercorso")
	private String descrizionePercorso;

	@JsonCreator
	public Forecast() {
		// Jackson Creator
	}

	public String getLineID() {
		return lineID;
	}

	public String getLineDescription() {
		return lineDescription;
	}

	public LocalDateTime getDepartureTimeRegistered() {
		return departureTimeRegistered;
	}

	public LocalDateTime getDepartureTimeScheduled() {
		return departureTimeScheduled;
	}

	public String getStatoPrevisione() {
		return statoPrevisione;
	}

	public String getDestination() {
		return destination;
	}

	public String getTurnoMacchina() {
		return turnoMacchina;
	}

	public String getDescrizionePercorso() {
		return descrizionePercorso;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("lineID", lineID).add("lineDescription", lineDescription)
				.add("departureTimeRegistered", departureTimeRegistered)
				.add("departureTimeScheduled", departureTimeScheduled).add("statoPrevisione", statoPrevisione)
				.add("destination", destination).add("turnoMacchina", turnoMacchina)
				.add("descrizionePercorso", descrizionePercorso).toString();
	}

}
