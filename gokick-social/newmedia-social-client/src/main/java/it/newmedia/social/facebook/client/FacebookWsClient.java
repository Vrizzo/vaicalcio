package it.newmedia.social.facebook.client;

import it.newmedia.social.ws.*;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.soap.SOAPHandler;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

public class FacebookWsClient
{
  private static final String REQUEST_TIMEOUT = "com.sun.xml.internal.ws.request.timeout";

  private static final String CONNECT_TIMEOUT = "com.sun.xml.internal.ws.connect.timeout";

  private static final String WSDL_CLASSPATH = "/wsdl/newmedia-social-facebook.wsdl";

  private static String WS_LOCAL_PART_NAME = "FacebookWSService";
  private static String WS_NAMESPACE_URI_NAME = "http://ws.social.newmedia.it/";

  private String wsdlEndPointUrl;
  private int wsdlEndPointTimeout;
  
  private static Hashtable<String, FacebookWsClient> clients = new Hashtable<String, FacebookWsClient>();

  private FacebookWSService service;

  private FacebookWS port;

  private FacebookWsClient(String wsdlEndPointUrl,
                           String namespaceuri,
                           String localpart,
                           Integer connectTimeout,
                           Integer requestTimeout)
  {
    QName qName = new QName(namespaceuri, localpart);

    URL url = FacebookWSService.class.getResource(WSDL_CLASSPATH);

    service = new FacebookWSService(url, qName);

    port = service.getFacebookWSPort();

    // Impostazione url WSDL.
    ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlEndPointUrl);
    // Impostazione read timeout
    ((BindingProvider) port).getRequestContext().put(REQUEST_TIMEOUT, connectTimeout);
    // Impostazione connection timeout
    ((BindingProvider) port).getRequestContext().put(CONNECT_TIMEOUT, requestTimeout);
  }

  private FacebookWsClient getInstance()
  {
    String clientKey = String.format("%s_%s", this.wsdlEndPointUrl, this.wsdlEndPointTimeout);
    FacebookWsClient client = clients.get(clientKey);
    if (client == null)
    {
      client = new FacebookWsClient(wsdlEndPointUrl, WS_NAMESPACE_URI_NAME, WS_LOCAL_PART_NAME, wsdlEndPointTimeout, wsdlEndPointTimeout);
      clients.put(clientKey, client);
    }
    return client;
  }

  public FacebookWsClient(String wsdlEndPointUrl, int wsdlEndPointTimeout)
  {
    this.wsdlEndPointUrl = wsdlEndPointUrl;
    this.wsdlEndPointTimeout = wsdlEndPointTimeout;
  }

  private FacebookWS getPort()
  {
    return port;
  }

  public static WritePostRequest buildWritePostRequest(String username, String password)
  {
    WritePostRequest writePostRequest = new WritePostRequest();
    Login login = new Login();
    login.setUsername(username);
    login.setPassword(password);
    writePostRequest.setLogin(login);
    return writePostRequest;
  }

  public WritePostResponse writePosts(WritePostRequest writePostRequest)
  {
    return this.getInstance().getPort().writePosts(writePostRequest);
  }

  public ReadPostResponse readLastPosts(ReadPostRequest readPostRequest)
  {
    return this.getInstance().getPort().readLastPosts(readPostRequest);
  }

}
