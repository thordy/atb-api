package com.thord.atb.soap;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SOAPHelper {
  private static final String XML_NS_XSL = "http://www.w3.org/2001/XMLSchema-instance";
  private static final String XML_NS_XSD = "http://www.w3.org/2001/XMLSchema";
  private static final String XML_NS_SOAP12 = "http://www.w3.org/2003/05/soap-envelope";

  public static SOAPMessage createMessage() throws SOAPException {
    MessageFactory factory = MessageFactory.newInstance();

    SOAPMessage message = factory.createMessage();
    message.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
    return message;
  }

  public static SOAPEnvelope createEnvelope(SOAPMessage message) throws SOAPException {
    SOAPPart part = message.getSOAPPart();

    SOAPEnvelope envelope = part.getEnvelope();
    envelope.removeNamespaceDeclaration("SOAP-ENV");
    envelope.addNamespaceDeclaration("xsl", XML_NS_XSL).addNamespaceDeclaration("xsd", XML_NS_XSD)
        .addNamespaceDeclaration("soap12", XML_NS_SOAP12).setPrefix("soap12");
    envelope.removeChild(envelope.getHeader());
    return envelope;
  }

  public static void addAuthElement(SOAPElement parent, String username, String password)
      throws SOAPException {
    SOAPElement auth = parent.addChildElement("auth");

    SOAPElement user = auth.addChildElement("user");
    user.setValue(username);

    SOAPElement pwd = auth.addChildElement("password");
    pwd.setValue(password);
  }
}
