package com.thord.atb.data.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class BusStopsListResult {

	@JsonProperty("Fermate")
	private List<BusStop> stops;

	@JsonProperty
	private int total;

	@JsonCreator
	public BusStopsListResult() {
		// Jackson Creator
	}

	public List<BusStop> getStops() {
		return stops;
	}

	public int getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("stops", stops).add("total", total).toString();
	}

}
