package it.newmedia.xml;

import it.newmedia.results.Result;
import it.newmedia.utils.DateUtil;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

// </editor-fold>
public class XmlUtil
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public final static String FEATURE_URL_VALIDATION = "http://xml.org/sax/features/validation";
  public final static String FEATURE_URL_LOAD_EXT_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
  public final static String FEATURE_NAMESPACES = "http://xml.org/sax/features/namespaces";
  public final static String CHARSET_UTF8 = "UTF-8";
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  public XmlUtil()
  {

  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --">
  // <editor-fold defaultstate="collapsed" desc="-- String to XML --">
  public static Document stringToXmlDocument(String stringValue) throws SAXNotRecognizedException, SAXNotSupportedException, SAXException, IOException
  {
    return XmlUtil.stringToXmlDocument(stringValue, false);
  }

  public static Document stringToXmlDocument(String stringValue, boolean ignoreAllDtdAndValidation) throws SAXNotRecognizedException, SAXNotSupportedException, SAXException, IOException
  {
    return XmlUtil.stringToXmlDocument(stringValue, ignoreAllDtdAndValidation, false);
  }

  public static Document stringToXmlDocument(String stringValue, boolean ignoreAllDtdAndValidation, boolean ignoreNamespaces) throws SAXNotRecognizedException, SAXNotSupportedException, SAXException, IOException
  {
    DOMParser parser = new DOMParser();
    parser.setErrorHandler(new XmlParserErrorHandler());

    if (ignoreAllDtdAndValidation)
    {
      parser.setFeature(FEATURE_URL_VALIDATION, false);
      parser.setFeature(FEATURE_URL_LOAD_EXT_DTD, false);
    }
    if (ignoreNamespaces)
    {
      parser.setFeature(FEATURE_NAMESPACES, false);
    }

    parser.parse(new InputSource(new StringReader(stringValue)));

    return parser.getDocument();
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- XML to String --">
  public static String xmlDocumentToUTF8String(Document document) throws TransformerException, UnsupportedEncodingException
  {
    return XmlUtil.xmlDocumentToString(document, false, XmlUtil.CHARSET_UTF8);
  }

  public static String xmlDocumentToUTF8String(Document document, boolean indentOutput) throws TransformerException, UnsupportedEncodingException
  {
    return XmlUtil.xmlDocumentToString(document, indentOutput, XmlUtil.CHARSET_UTF8);
  }

  public static String xmlDocumentToString(Document document) throws TransformerException, UnsupportedEncodingException
  {
    return XmlUtil.xmlDocumentToString(document, false);
  }

  public static String xmlDocumentToString(Document document, boolean indentOutput) throws TransformerConfigurationException, TransformerException, UnsupportedEncodingException
  {
    return XmlUtil.xmlDocumentToString(document, indentOutput, null);
  }

  public static String xmlDocumentToString(Document document, boolean indentOutput, String charset) throws TransformerConfigurationException, TransformerException, UnsupportedEncodingException
  {
    StringWriter stringWriter = new StringWriter();
    Transformer transformer = TransformerFactory.newInstance().newTransformer();

    if (indentOutput)
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

    String xmlString = stringWriter.toString();
    if (charset != null)
    {
      String xmlStringWithCharset = new String(xmlString.getBytes(charset));
      return xmlStringWithCharset;
    }
    else
    {
      return xmlString;
    }
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Get Attribute --">
  public static Result<String> getAttribute(Node node, String attributeName)
  {
    try
    {
      return new Result<String>(node.getAttributes().getNamedItem(attributeName).getNodeValue(), true);
    }
    catch (Exception exception)
    {
      return new Result<String>(exception);
    }
  }

  public static String getAttribute(Node node, String attributeName, String defaultValue)
  {
    try
    {
      return node.getAttributes().getNamedItem(attributeName).getNodeValue();
    }
    catch (Exception exception)
    {
      return defaultValue;
    }
  }

  public static int getAttribute(Node node, String attributeName, int defaultValue)
  {
    try
    {
      return Integer.parseInt(node.getAttributes().getNamedItem(attributeName).getNodeValue());
    }
    catch (Exception exception)
    {
      return defaultValue;
    }
  }

  public static boolean getAttribute(Node node, String attributeName, boolean defaultValue)
  {
    try
    {
      return Boolean.parseBoolean(node.getAttributes().getNamedItem(attributeName).getNodeValue());
    }
    catch (Exception exception)
    {
      return defaultValue;
    }
  }

  public static Date getAttribute(Node node, String attributeName, Date defaultValue, String format)
  {
    try
    {
      return DateUtil.parseDate(node.getAttributes().getNamedItem(attributeName).getNodeValue(), format);
    }
    catch (Exception exception)
    {
      return defaultValue;
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Set Attribute --">
  public static void setAttribute(Node node, String attributeName, String attributeValue)
  {
    if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
      XmlUtil.setAttribute((Element) node, attributeName, attributeValue);
  }

  public static void setAttribute(Element element, String attributeName, String attributeValue)
  {
    if (attributeName != null && attributeName.length() > 0)
      element.setAttribute(attributeName, attributeValue);
  }

  public static void setAttribute(Element element, String attributeName, int attributeValue)
  {
    XmlUtil.setAttribute(element, attributeName, Integer.toString(attributeValue));
  }
  // </editor-fold>
  public static Node getFirstChildElementByName(Node node, String nodeName)
  {
    NodeList nodes;
    Node newNode;
    int nodesLength;

    nodes = node.getChildNodes();
    nodesLength = nodes.getLength();
    for (int i = 0; i < nodesLength; i++)
    {
      newNode = nodes.item(i);
      if (nodeName.equals(newNode.getNodeName()))
      {
        return newNode;
      }
    }
    return null;
  }


  public static Result<String> getElementValueByTagName(Document doc, String name, int position)
  {
    NodeList nodes = doc.getElementsByTagName(name);
    if (nodes != null)
    {
      if (nodes.item(position) != null)
      {
        return new Result<String>(XmlUtil.getText(nodes.item(position)), true);
      }
      else
      {
        return new Result<String>("Position not found for tag [" + name + "]");
      }
    }
    else
    {
      return new Result<String>("Position not found for tag [" + name + "]");
    }
  }

  public static String getCDATA(Element element)
  {
    StringBuffer buffer = new StringBuffer();
    NodeList children = element.getChildNodes();
    for (int i = 0; i < children.getLength(); i++)
    {
      Node child = children.item(i);
      if (child.getNodeType() == Node.CDATA_SECTION_NODE)
      {
        CDATASection section = (CDATASection) child;
        buffer.append(section.getNodeValue());
      }
    }
    String returnValue = null;
    if (buffer.length() > 0)
    {
      returnValue = buffer.toString();
    }
    return returnValue;
  }

  public static String getText(Node node)
  {
    Node childNode;
    String returnString = "";
    NodeList children = node.getChildNodes();
    if (children != null)
    {
      for (int i = 0; i < children.getLength(); i++)
      {
        childNode = children.item(i);
        if ((childNode.getNodeType() == Node.TEXT_NODE) || (childNode.getNodeType() == Node.CDATA_SECTION_NODE))
        {
          returnString = childNode.getNodeValue();
          break;
        }
      }
    }
    return returnString;
  }

  public static void setText(Node node, String text)
  {
    Node childNode;
    NodeList children = node.getChildNodes();
    if (children != null)
    {
      for (int i = 0; i < children.getLength(); i++)
      {
        childNode = children.item(i);
        if ((childNode.getNodeType() == Node.TEXT_NODE) || (childNode.getNodeType() == Node.CDATA_SECTION_NODE))
        {
          childNode.setNodeValue(text);
          break;
        }
      }
    }
  }
  
  public static int getText(Node node, int defaultValue)
  {
    try
    {
      return Integer.parseInt(XmlUtil.getText(node));
    }
    catch (Exception exception)
    {
      return defaultValue;
    }
  }

  public static NodeList evaluateXPath(String xpathQuery, Node target) throws XPathExpressionException
  {
    XPath xpath = XPathFactory.newInstance().newXPath();
    NodeList nodes = (NodeList) xpath.evaluate(xpathQuery, target, XPathConstants.NODESET);
    return nodes;
  }

  public static Result<Document> getXmlResourceAsDocument(String fileName, Object obj)
  {
    InputStream inputStream = obj.getClass().getResourceAsStream(fileName);
    Document document;

    try
    {
      document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
    }
    catch (Exception exception)
    {
      return new Result<Document>(exception);
    }

    return new Result<Document>(document, true);
  }

  // <editor-fold defaultstate="collapsed" desc="-- Create Element/ElementCDATA --">
  public static Element createElement(Document document, String name) throws Exception
  {
    return createElement(document, name, null, null, null);
  }

  public static Element createElement(Document document, String name, String value) throws Exception
  {
    return createElement(document, name, value, null, null);
  }

  public static Element createElement(Document document, String name, Hashtable attributes) throws Exception
  {
    return createElement(document, name, null, attributes, null);
  }

  public static Element createElement(Document document, String name, String value, Hashtable attributes) throws Exception
  {
    return createElement(document, name, value, attributes, null);
  }

  public static Element createElement(Document document, String name, Element parent) throws Exception
  {
    return createElement(document, name, null, null, parent);
  }

  public static Element createElement(Document document, String name, Date value, Element parent) throws Exception
  {
    return createElement(document, name, DateUtil.formatISO8601Date(value), null, parent);
  }

  public static Element createElement(Document document, String name, BigDecimal value, Element parent) throws Exception
  {
    return createElement(document, name, value.toString(), null, parent);
  }

  public static Element createElement(Document document, String name, BigDecimal value, Hashtable attributes, Element parent, String format) throws Exception
  {
    String valueFormatted = String.format(format, value);
    return createElement(document, name, valueFormatted, attributes, parent);
  }

  public static Element createElement(Document document, String name, int value, Element parent) throws Exception
  {
    return createElement(document, name, Integer.toString(value), null, parent);
  }

  public static Element createElement(Document document, String name, boolean value, Element parent) throws Exception
  {
    return createElement(document, name, Boolean.toString(value), null, parent);
  }

  public static Element createElement(Document document, String name, String value, Element parent) throws Exception
  {
    return createElement(document, name, value, null, parent);
  }

  public static Element createElement(Document document, String name, Hashtable attributes, Element parent) throws Exception
  {
    return createElement(document, name, null, attributes, parent);
  }

  public static Element createElement(Document document, String name, String value, Hashtable attributes, Element parent) throws Exception
  {
    Element element;
    Enumeration enumeration;
    String key;
    String attributeValue;

    if (name == null)
    {
      throw new Exception("it.newmedia.util.XMLUtils.createXmlElement(): parameter name is null");
    }

    element = document.createElement(name);
    if (value != null)
    {
      element.appendChild(document.createTextNode(value));
    }

    if (attributes != null)
    {
      enumeration = attributes.keys();
      while (enumeration.hasMoreElements())
      {
        key = (String) enumeration.nextElement();
        attributeValue = (String) attributes.get(key);
        element.setAttribute(key, attributeValue);
      }
    }

    if (parent != null)
    {
      parent.appendChild(element);
    }

    return element;
  }

  public static Result<Element> createElementResult(Document document, String name, String value, Hashtable attributes, Element parent)
  {
    try
    {
      return new Result<Element>(XmlUtil.createElement(document, name, value, attributes, parent), true);
    }
    catch (Exception ex)
    {
      return new Result<Element>(ex);
    }
  }

  public static Element createElementCDATA(Document document, String name, String value) throws Exception
  {
    return createElementCDATA(document, name, value, null, null);
  }

  public static Element createElementCDATA(Document document, String name, String value, Hashtable attributes) throws Exception
  {
    return createElementCDATA(document, name, value, attributes, null);
  }

  public static Element createElementCDATA(Document document, String name, String value, Element parent) throws Exception
  {
    return createElementCDATA(document, name, value, null, parent);
  }

  public static Element createElementCDATA(Document document, String name, String value, Hashtable attributes, Element parent) throws Exception
  {
    Element element;
    Enumeration enumeration;
    String key;
    String attributeValue;

    if (name == null)
    {
      throw new Exception("it.newmedia.util.XMLUtils.createXmlElement(): parameter name is null");
    }

    element = document.createElement(name);
    if (value != null)
    {
      element.appendChild(document.createCDATASection(value));
    }

    if (attributes != null)
    {
      enumeration = attributes.keys();
      while (enumeration.hasMoreElements())
      {
        key = (String) enumeration.nextElement();
        attributeValue = (String) attributes.get(key);
        element.setAttribute(key, attributeValue);
      }
    }

    if (parent != null)
    {
      parent.appendChild(element);
    }

    return element;
  }

  public static Result<Element> createElementCDATAResult(Document document, String name, String value, Hashtable attributes, Element parent)
  {
    try
    {
      return new Result<Element>(XmlUtil.createElementCDATA(document, name, value, attributes, parent), true);
    }
    catch (Exception ex)
    {
      return new Result<Element>(ex);
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Serialize/Deserialize --">
  public static Result<String> serialize(Object o) throws Exception
  {
    XMLEncoder encoder = null;
    try
    {
      ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
      encoder = new XMLEncoder(out);
      encoder.writeObject(o);
      encoder.close();
      return new Result<String>(out.toString(), true);
    }
    catch (Exception ex)
    {
      if (encoder != null)
        encoder.close();
      return new Result<String>(ex);
    }
  }

  public static Result<Object> deserialize(String string)
  {
    XMLDecoder decoder = null;
    try
    {
      decoder = new XMLDecoder(new ByteArrayInputStream(string.getBytes()));
      Object o = decoder.readObject();
      decoder.close();
      return new Result<Object>(o, true);
    }
    catch (Exception ex)
    {
      if (decoder != null)
        decoder.close();
      return new Result<Object>(ex);
    }
  }
  // </editor-fold>

  // </editor-fold>
}
