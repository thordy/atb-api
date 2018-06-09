package com.thord.atb.data.soap;

import com.fasterxml.jackson.xml.annotate.JacksonXmlProperty;
import com.fasterxml.jackson.xml.annotate.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Envelope")
public class SOAPEnvelope {
	@JacksonXmlProperty(localName = "xmlns:soap", isAttribute = true)
	private String xmlns;

	@JacksonXmlProperty(localName = "xmlns:xsi", isAttribute = true)
	private String xsi;

	@JacksonXmlProperty(localName = "xmlns:xsd", isAttribute = true)
	private String xsd;

	@JacksonXmlProperty(localName = "Body")
	private SOAPBody body;

	public String getXmlns() {
		return xmlns;
	}

	public String getXsd() {
		return xsd;
	}

	public String getXsi() {
		return xsi;
	}

	public SOAPBody getBody() {
		return body;
	}

}
