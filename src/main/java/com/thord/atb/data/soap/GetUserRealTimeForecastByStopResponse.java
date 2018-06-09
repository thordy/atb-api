package com.thord.atb.data.soap;

import com.fasterxml.jackson.xml.annotate.JacksonXmlProperty;

public class GetUserRealTimeForecastByStopResponse {
	@JacksonXmlProperty(localName = "xmlns:soap", isAttribute = true)
	private String xmlns;

	@JacksonXmlProperty(localName = "getUserRealTimeForecastByStopResult")
	private GetUserRealTimeForecastByStopResult result;

	public String getXmlns() {
		return xmlns;
	}

	public GetUserRealTimeForecastByStopResult getResult() {
		return result;
	}
}
