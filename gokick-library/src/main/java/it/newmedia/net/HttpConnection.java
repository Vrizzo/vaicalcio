package it.newmedia.net;

import it.newmedia.io.FileUtil;
import it.newmedia.xml.XmlUtil;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * La classe <code>HttpConnection</code> rappresenta una connessione http.
 */
public class HttpConnection
{
// ------------------------------ FIELDS ------------------------------

  public static final int HTTP_RESPONSECODE_200_OK = 200;

  public static final int HTTP_RESPONSECODE_400_BAD_REQUEST = 400;

  public static final int HTTP_RESPONSECODE_401_UNAUTHORIZED = 401;

  public static final int HTTP_RESPONSECODE_402_PAYMENT_REQUIRED = 402;

  public static final int HTTP_RESPONSECODE_403_FORBIDDEN = 403;

  public static final int HTTP_RESPONSECODE_404_NOT_FOUND = 404;

  public static final int HTTP_RESPONSECODE_500_INTERNAL_SERVER_ERROR = 500;

  public static final String HTTP_VERSION_0_9 = "HTTP/0.9";

  public static final String HTTP_VERSION_1_0 = "HTTP/1.0";

  public static final String HTTP_VERSION_1_1 = "HTTP/1.1";

  public static final String HTTP_CONTENT_CHARSET_UTF8 = "UTF-8";

  public static final String DEFAULT_HTTP_CONTENT_CHARSET = HTTP_CONTENT_CHARSET_UTF8;

  private static final Map<HostConfiguration, Integer> DEFAULT_MAX_HOST_CONNECTIONS = new HashMap<HostConfiguration, Integer>();

  private static final Integer DEFAULT_MAX_TOTAL_CONNECTIONS = new Integer(1500);

  private static final Integer DEFAULT_CONNECTION_TIMEOUT = new Integer(1000 * 10);

  private static final Integer DEFAULT_SO_TIMEOUT = new Integer(1000 * 60 * 6);

  private static final HttpVersion DEFAULT_PROTOCOL_VERSION = HttpVersion.HTTP_1_1;

  private static final boolean DEFAULT_USE_EXPECT_CONTINUE = true;

  private static final String DEFAULT_USER_AGENT = "";

  private static MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager;

  private static HttpClient httpClient;

  private Header locationHeader;

  private String uri;

  private Integer httpSocketTimeout;

  private HttpVersion httpVersion;

  private String charset = DEFAULT_HTTP_CONTENT_CHARSET;

  private StringBuilder response;

  private int responseCode;

  private String userAgent;

  private Boolean followRedirect;
  
  private UsernamePasswordCredentials credentials = null;
  
  private Hashtable<String, String> extraRequestHeader;

// -------------------------- STATIC METHODS --------------------------

  static
  {
    DEFAULT_MAX_HOST_CONNECTIONS.put(HostConfiguration.ANY_HOST_CONFIGURATION, new Integer(250));
    multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
    multiThreadedHttpConnectionManager.getParams()
                                      .setParameter(HttpConnectionManagerParams.MAX_HOST_CONNECTIONS, DEFAULT_MAX_HOST_CONNECTIONS);
    multiThreadedHttpConnectionManager.getParams()
                                      .setParameter(HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS, DEFAULT_MAX_TOTAL_CONNECTIONS);
    httpClient = new HttpClient(multiThreadedHttpConnectionManager);
    httpClient.getParams()
              .setParameter(HttpClientParams.PROTOCOL_VERSION, DEFAULT_PROTOCOL_VERSION);
    //httpClient.getParams().setParameter(HttpClientParams.SO_TIMEOUT, DEFAULT_SO_TIMEOUT);
    //httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
    httpClient.getParams()
              .setParameter(HttpClientParams.HTTP_CONTENT_CHARSET, DEFAULT_HTTP_CONTENT_CHARSET);
    httpClient.getState()
              .setCredentials(AuthScope.ANY, null);
  }

// --------------------------- CONSTRUCTORS ---------------------------

  public HttpConnection(String uri)
  {
    this(uri, null);
  }

  public HttpConnection(String uri, Integer httpSocketTimeout)
  {
    this(uri, httpSocketTimeout, null);
  }

  public HttpConnection(String uri, Integer httpSocketTimeout, String httpVer)
  {
    this.uri = uri;
    this.httpSocketTimeout = httpSocketTimeout;
    if (httpVer != null && httpVer.length() > 0)
    {
      try
      {
        this.httpVersion = HttpVersion.parse(httpVer);
      }
      catch (ProtocolException protocolException)
      {
        this.httpVersion = null;
      }
    }
    else
    {
      this.httpVersion = null;
    }
  }

// --------------------- GETTER / SETTER METHODS ---------------------

  public String getCharset()
  {
    return charset;
  }

  public void setCharset(String charset)
  {
    this.charset = charset;
  }

  /**
   * Get HTTP status code for the last request (get or post)
   *
   * @return int value corresponding to HTTP status code
   */
  public int getResponseCode()
  {
    return this.responseCode;
  }

  public String getUserAgent()
  {
    if (userAgent == null)
    {
      userAgent = DEFAULT_USER_AGENT;
    }
    return userAgent;
  }

  public void setUserAgent(String userAgent)
  {
    this.userAgent = userAgent;
  }

// -------------------------- OTHER METHODS --------------------------

  private void doPost(String data, Map<String, String> parameters, String soapAction, boolean useExpectContinue) throws IOException
  {
    int charRead;
    char[] charArray;
    BufferedReader bufferedReader = null;
    PostMethod postMethod;
    RequestEntity requestEntity;

    postMethod = new PostMethod(uri);

    //setting followRedirect
    if (this.followRedirect != null)
    {
      postMethod.setFollowRedirects(this.followRedirect);
    }

    /** Read time out */
    if (httpSocketTimeout != null)
    {
      postMethod.getParams()
                .setParameter(HttpMethodParams.SO_TIMEOUT, httpSocketTimeout);
    }
    else
    {
      postMethod.getParams()
                .setParameter(HttpMethodParams.SO_TIMEOUT, DEFAULT_SO_TIMEOUT);
    }
    /** Http version */
    if (httpVersion != null)
    {
      postMethod.getParams()
                .setParameter(HttpMethodParams.PROTOCOL_VERSION, httpVersion);
    }

    /** Use Expect Continue */
    postMethod.getParams()
              .setParameter(HttpMethodParams.USE_EXPECT_CONTINUE, useExpectContinue);

    /** User agent */
    postMethod.getParams()
              .setParameter(HttpMethodParams.USER_AGENT, this.getUserAgent());


    if (this.charset == null || this.charset
            .length() == 0)
    {
      this.charset = DEFAULT_HTTP_CONTENT_CHARSET;
    }
    /** Xml */
    if (data != null && data.length() > 0)
    {
      requestEntity = new ByteArrayRequestEntity(data.getBytes(), "text/xml; charset=" + this.charset);
      postMethod.setRequestEntity(requestEntity);
    }

    /** SoapAction */
    if (soapAction != null && soapAction.length() > 0)
    {
      postMethod.setRequestHeader("SOAPAction", soapAction);
    }

    /** Parameters */
    if (parameters != null && parameters.size() > 0)
    {
      for (String key : parameters.keySet())
      {
        postMethod.addParameter(key, parameters.get(key));
      }
    }

    /** Accept-Encoding: deflate, gzip, x-gzip, compress, x-compress */
    postMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
    if( this.extraRequestHeader != null)
    {
      for (Map.Entry<String, String> entry : extraRequestHeader.entrySet())
      {
        postMethod.addRequestHeader(entry.getKey(), entry.getValue());
      }
    }
    try
    {
      //System.out.println("httpclient " + httpClient.getParams().getSoTimeout());
      //System.out.println("postmethod " + postMethod.getParams().getSoTimeout());
      responseCode = httpClient.executeMethod(postMethod);

      Header contentEncoding = postMethod.getResponseHeader("Content-Encoding");

      if (contentEncoding == null || contentEncoding.getValue()
                                                    .length() == 0)
      {
        bufferedReader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), Charset.forName(this.charset)));
      }
      else if (contentEncoding.getValue()
                              .equals("gzip"))
      {
        bufferedReader = new BufferedReader(
                new InputStreamReader(new GZIPInputStream(postMethod.getResponseBodyAsStream()), Charset.forName(this.charset)));
      }
      else if (contentEncoding.getValue()
                              .equals("deflate"))
      {
        bufferedReader = new BufferedReader(
                new InputStreamReader(new InflaterInputStream(postMethod.getResponseBodyAsStream()), Charset.forName(this.charset)));
      }
      else
      {
        bufferedReader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), Charset.forName(this.charset)));
      }

      response = new StringBuilder();
      charArray = new char[8196];
      while ((charRead = bufferedReader.read(charArray, 0, charArray.length)) != -1)
      {
        response.append(charArray, 0, charRead);
      }

      //      System.out.println("REQUEST ***********************");
      //      for (int i=0 ; i<postMethod.getRequestHeaders().length ; i++)
      //        System.out.println(postMethod.getRequestHeaders()[i]);
      //
      //      System.out.println("RESPONSE ***********************");
      //      for (int i=0 ; i<postMethod.getResponseHeaders().length ; i++)
      //        System.out.println(postMethod.getResponseHeaders()[i]);
      this.locationHeader = postMethod.getResponseHeader("location");
    }
    finally
    {
      if (bufferedReader != null)
      {
        bufferedReader.close();
      }
      postMethod.releaseConnection();
    }
  }
  
  public void addExtraRequestHeader(String headerName, String headerValue)
  {
    if( this.extraRequestHeader == null)
    {
      this.extraRequestHeader = new Hashtable<String, String>();
    }
    this.extraRequestHeader.put(headerName, headerValue);
  }

  public void doGet() throws IOException
  {
    this.doGet(null);
  }

  public void doGet(Map<String, String> parameters) throws IOException
  {
    StringBuilder queryString;
    int charRead;
    char[] charArray;
    BufferedReader bufferedReader = null;
    GetMethod getMethod;

    getMethod = new GetMethod(uri);


    //setting followRedirect
    if (this.followRedirect != null)
    {
      getMethod.setFollowRedirects(this.followRedirect);
    }

    /** Read time out */
    if (httpSocketTimeout != null)
    {
      getMethod.getParams()
               .setParameter(HttpMethodParams.SO_TIMEOUT, httpSocketTimeout);
    }
    else
    {
      getMethod.getParams()
               .setParameter(HttpMethodParams.SO_TIMEOUT, DEFAULT_SO_TIMEOUT);
    }
    /** Http version */
    if (httpVersion != null)
    {
      getMethod.getParams()
               .setParameter(HttpMethodParams.PROTOCOL_VERSION, httpVersion);
    }

    if (credentials != null)
    {
      getMethod.setDoAuthentication(true);
    }
    getMethod.getParams()
             .setParameter(HttpMethodParams.USE_EXPECT_CONTINUE, Boolean.TRUE);
    /** User agent */
    getMethod.getParams()
             .setParameter(HttpMethodParams.USER_AGENT, this.getUserAgent());
    /** Parameters */
    if (parameters != null && parameters.size() > 0)
    {
      NameValuePair[] nameValuePair = new NameValuePair[parameters.size()];
      int index = 0;
      for (String key : parameters.keySet())
      {
        nameValuePair[index] = new NameValuePair(key, parameters.get(key));
        index++;
      }
      getMethod.setQueryString(nameValuePair);
    }

    if (credentials != null)
    {
      getMethod.setDoAuthentication(true);
    }

    /** Accept-Encoding: deflate, gzip, x-gzip, compress, x-compress */
    getMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
    if( this.extraRequestHeader != null)
    {
      for (Map.Entry<String, String> entry : extraRequestHeader.entrySet())
      {
        getMethod.addRequestHeader(entry.getKey(), entry.getValue());
      }
    }
    try
    {
      responseCode = httpClient.executeMethod(getMethod);

      Header contentEncoding = getMethod.getResponseHeader("Content-Encoding");

      if (contentEncoding == null || contentEncoding.getValue()
                                                    .length() == 0)
      {
        bufferedReader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));
      }
      else if (contentEncoding.getValue()
                              .equals("gzip"))
      {
        bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(getMethod.getResponseBodyAsStream())));
      }
      else if (contentEncoding.getValue()
                              .equals("deflate"))
      {
        bufferedReader = new BufferedReader(new InputStreamReader(new InflaterInputStream(getMethod.getResponseBodyAsStream())));
      }
      else
      {
        bufferedReader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));
      }

      response = new StringBuilder();
      charArray = new char[8196];
      while ((charRead = bufferedReader.read(charArray, 0, charArray.length)) != -1)
      {
        response.append(charArray, 0, charRead);
      }

      //      System.out.println("REQUEST ***********************");
      //      for (int i=0 ; i<getMethod.getRequestHeaders().length ; i++)
      //        System.out.println(getMethod.getRequestHeaders()[i]);
      //
      //      System.out.println("RESPONSE ***********************");
      //      for (int i=0 ; i<getMethod.getResponseHeaders().length ; i++)
      //        System.out.println(getMethod.getResponseHeaders()[i]);
    }
    finally
    {
      if (bufferedReader != null)
      {
        bufferedReader.close();
      }
      getMethod.releaseConnection();
    }
  }

  public void doPost(Map<String, String> parameters) throws IOException
  {
    doPost(null, parameters, null, DEFAULT_USE_EXPECT_CONTINUE);
  }

  public void doPost(Document xmlDocument) throws TransformerConfigurationException, TransformerException, IOException
  {
    doPost(XmlUtil.xmlDocumentToString(xmlDocument), null, null, DEFAULT_USE_EXPECT_CONTINUE);
  }

  public void doPost(String data) throws IOException
  {
    doPost(data, null, null, DEFAULT_USE_EXPECT_CONTINUE);
  }

  public void doPost(String data, Map<String, String> parameters) throws IOException
  {
    doPost(data, parameters, null, DEFAULT_USE_EXPECT_CONTINUE);
  }

  public void doPostNoUseExpectContinue(String data) throws IOException
  {
    doPost(data, null, null, false);
  }

  public void doPostSoap(String soapAction, String data) throws IOException
  {
    doPost(data, null, soapAction, DEFAULT_USE_EXPECT_CONTINUE);
  }

  public void doPostSoap(String soapAction, Map<String, String> parameters) throws IOException
  {
    doPost(null, parameters, soapAction, DEFAULT_USE_EXPECT_CONTINUE);
  }

  public void doPostSoap(String soapAction, Document xmlDocument) throws TransformerConfigurationException, TransformerException, IOException
  {
    doPost(XmlUtil.xmlDocumentToString(xmlDocument), null, soapAction, DEFAULT_USE_EXPECT_CONTINUE);
  }

  public void doPostSoap(String soapAction, String data, Map<String, String> parameters) throws IOException
  {
    doPost(data, parameters, soapAction, DEFAULT_USE_EXPECT_CONTINUE);
  }

  /**
   * Returns true if the HTTP method should automatically follow
   * HTTP redirects (status code 302, etc.), false otherwise.
   *
   * @return true if the method will automatically follow HTTP redirects, false otherwise
   */
  public boolean getFollowRedirects()
  {
    return this.followRedirect
            .booleanValue();
  }

  public String getLocationRedirect()
  {
    return locationHeader.getValue();
  }

  public String getResponse()
  {
    if (response != null)
    {
      return response.toString();
    }
    return null;
  }

  public Document getResponseAsXml() throws ParserConfigurationException, SAXException, IOException
  {
    return this.getResponseAsXml(false);
  }

  public Document getResponseAsXml(boolean ignoreAllDtdAndValidation) throws ParserConfigurationException, SAXException, IOException
  {
    return XmlUtil.stringToXmlDocument(response.toString(), ignoreAllDtdAndValidation);
  }

  public Document getResponseAsXml(boolean ignoreAllDtdAndValidation, boolean ignoreNamespaces) throws ParserConfigurationException, SAXException,
                                                                                                       IOException
  {
    return XmlUtil.stringToXmlDocument(response.toString(), ignoreAllDtdAndValidation, ignoreNamespaces);
  }

  public void resetExtraRequestHeader()
  {
    this.extraRequestHeader = null;
  }

  public void saveResponse(String path) throws IOException
  {
    FileUtil.writeFile(response.toString(), path);
  }

  public void setCredentials(String username, String password)
  {
    credentials = new UsernamePasswordCredentials(username, password);
    httpClient.getState()
              .setCredentials(AuthScope.ANY, credentials);
  }

  /**
   * Sets whether or not the HTTP method should automatically follow
   * HTTP redirects (status code 302, etc.)
   *
   * @param followRedirect - true if the method will automatically follow redirects, false otherwise.
   */
  public void setFollowRedirects(boolean followRedirect)
  {
    this.followRedirect = new Boolean(followRedirect);
  }

// --------------------------- main() method ---------------------------

  public static void main(String[] args)
  {
    HttpConnection con;
    HttpConnection con1;

    try
    {
      con = new HttpConnection("http://www.showmyip.it", null);
      //con.setFollowRedirects(false);
      con.addExtraRequestHeader("Accept", "application/json");
      con.addExtraRequestHeader("Content-Type", "application/json");

      con.doPost("aa");
      System.out
              .println("con getResponseCode is:" + con.getResponseCode());
      System.out
              .println("con getResponseData is:" + con.getResponse());
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
}
