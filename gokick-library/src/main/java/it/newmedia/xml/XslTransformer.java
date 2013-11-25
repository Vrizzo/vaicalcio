package it.newmedia.xml;

import it.newmedia.io.FileUtil;
import it.newmedia.results.Result;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;

public class XslTransformer
{
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private StreamSource streamSource;
  private String charset;
  private Transformer transformer;
  private boolean xmlValid;
  private boolean xslValid;
  private String lastErrorMessage;
  private Hashtable<String, String> xslParams;
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  public XslTransformer()
  {
    this.xslParams = new Hashtable<String, String>();
    this.lastErrorMessage = "";
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --">
  public void setCharset(String charset)
  {
    this.charset = charset;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  public void addXslParam(String paramName, String paramValue)
  {
    if (paramName != null && paramValue != null && paramName.length() > 0 && paramValue.length() > 0)
      this.xslParams.put(paramName, paramValue);
  }
  public void addAllXslParam(Hashtable<String, String> params)
  {
    this.xslParams.putAll(params);
  }

  public Result<Boolean> loadXmlFromFile(String xmlFilePath)
  {
    Result<String> rFileString = FileUtil.readFile(xmlFilePath);
    if (!rFileString.isSuccessNotNull())
    {
      this.xmlValid = false;
      this.lastErrorMessage = rFileString.getErrorMessage();
      return new Result<Boolean>(this.lastErrorMessage);
    }
    return this.loadXmlFromString(rFileString.getValue());
  }

  public Result<Boolean> loadXmlFromDocument(Document doc)
  {
    try
    {
      return this.loadXmlFromString(XmlUtil.xmlDocumentToString(doc, false, this.charset));
    }
    catch (Exception ex)
    {
      this.xmlValid = false;
      this.lastErrorMessage = ex.getMessage();
      return new Result<Boolean>(ex);
    }
  }

  public Result<Boolean> loadXmlFromString(String xmlString)
  {
    try
    {
      StringReader readerXml = new StringReader(xmlString);
      this.streamSource = new StreamSource(readerXml);
      this.xmlValid = true;
      return new Result<Boolean>(this.xmlValid, true);
    }
    catch (Exception ex)
    {
      this.xmlValid = false;
      this.lastErrorMessage = ex.getMessage();
      return new Result<Boolean>(ex);
    }
  }

  public Result<Boolean> loadXslFromFile(String xslFilePath)
  {
    try
    {
      TransformerFactory factory = TransformerFactory.newInstance();
      Templates templates = factory.newTemplates(new StreamSource(new File(xslFilePath)));
      this.transformer = templates.newTransformer();
      this.xslValid = true;
      return new Result<Boolean>(this.xslValid, true);
    }
    catch (TransformerConfigurationException ex)
    {
      this.xslValid = false;
      this.lastErrorMessage = ex.getMessage();
      return new Result<Boolean>(ex);
    }
  }

  public Result<String> transform()
  {
    if (!this.xmlValid)
      return new Result<String>("Cannot transform! error loading xml: " + this.lastErrorMessage);
    if (!this.xslValid)
      return new Result<String>("Cannot transform! error loading xsl: " + this.lastErrorMessage);
    try
    {
      Writer writer = new StringWriter();
      StreamResult streamResult = new StreamResult(writer);

      if (this.xslParams.size() > 0)
      {
        Enumeration<String> paramNames = this.xslParams.keys();
        while (paramNames.hasMoreElements())
        {
          String paramName = paramNames.nextElement();
          this.transformer.setParameter(paramName, this.xslParams.get(paramName));
        }
      }
      this.transformer.transform(this.streamSource, streamResult);

      String result = writer.toString();
      if (this.charset != null)
      {
        result = new String(result.getBytes(this.charset));
      }
      return new Result<String>(result, true);
    }
    catch (Exception ex)
    {
      return new Result<String>(ex);
    }
  }

  // </editor-fold>
  public static void main(String[] args)
  {
    try
    {
      String xmlFilePath = "e:/temp/test.xml";
      String xslFilePath = "E:/ProjectsJava/Tui/StoreFrontWebSite/web/sites/TUI/xslt/bookingToCustomer_it.xslt";

      Result<Boolean> r;
      System.out.println("Transform xml with xsl...");

      XslTransformer xslTransformer = new XslTransformer();

      System.out.println("Try to load xml file: " + xmlFilePath);
      r = xslTransformer.loadXmlFromFile(xmlFilePath);
      if (r.isSuccessNotNull())
      {
        System.out.println("Loading OK!");
      }
      else
      {
        System.out.println("Loading Fails: " + r.getErrorMessage());
      }

      System.out.println("Try to load xsl file: " + xslFilePath);
      r = xslTransformer.loadXslFromFile(xslFilePath);
      if (r.isSuccessNotNull())
      {
        System.out.println("Loading OK!");
      }
      else
      {
        System.out.println("Loading Fails: " + r.getErrorMessage());
        System.out.println(r.getErrorException());
      }

      xslTransformer.addXslParam("bg_box.gif", "E:/ProjectsJava/Tui/StoreFrontWebSite/web/sites/TUI/images/email/bookingToCustomer/bg_box.gif");
      Result<String> rString = xslTransformer.transform();
      if (rString.isSuccessNotNull())
      {
        System.out.println("Transform OK!");
        System.out.println(rString.getValue());
      }
      else
      {
        System.out.println("Transform fails: " + rString.getErrorMessage());
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
}
