package com.thord.atb.data.soap;

import com.fasterxml.jackson.xml.annotate.JacksonXmlProperty;

public class SOAPBody {

	@JacksonXmlProperty(localName = "getUserRealTimeForecastByStopResponse")
	private GetUserRealTimeForecastByStopResponse response;

	public GetUserRealTimeForecastByStopResponse getResponse() {
		return response;
	}

}
