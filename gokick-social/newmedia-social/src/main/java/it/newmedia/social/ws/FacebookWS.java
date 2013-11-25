package it.newmedia.social.ws;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import it.newmedia.social.facebook.dto.ReadPostRequest;
import it.newmedia.social.facebook.dto.WritePostResponse;
import it.newmedia.social.facebook.service.FacebookService;
import it.newmedia.social.facebook.dto.WritePostRequest;
import it.newmedia.social.facebook.dto.ReadPostResponse;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlType;

@WebService
public class FacebookWS
{
  private static final Logger logger = Logger.getLogger(FacebookWS.class);

  @Autowired
  FacebookService facebookService;

  @WebMethod(exclude = true)
  public void setFacebookService(FacebookService facebookService)
  {
    this.facebookService = facebookService;
  }

  @WebMethod(operationName = "writePosts")
  @WebResult(name = "response")
  public WritePostResponse writePosts(@WebParam(name = "request") WritePostRequest request)
  {
    StringBuilder sbDebug = new StringBuilder(1000);
    try
    {
      sbDebug.append(SystemUtils.LINE_SEPARATOR)
             .append("##### Facebook->writePosts start...")
             .append(SystemUtils.LINE_SEPARATOR)
             .append(request)
             .append(SystemUtils.LINE_SEPARATOR);

      WritePostResponse response = facebookService.writePosts(request);

      sbDebug.append(response)
             .append(SystemUtils.LINE_SEPARATOR);

      return response;
    }
    finally
    {
      sbDebug.append("##### Facebook->writePosts end")
             .append(SystemUtils.LINE_SEPARATOR);
      logger.debug(sbDebug.toString());
    }
  }

  @WebMethod(operationName = "readLastPosts")
  @WebResult(name = "response")
  public ReadPostResponse readLastPosts(@WebParam(name = "request") ReadPostRequest request)
  {
    StringBuilder sbDebug = new StringBuilder(1000);
    try
    {
      sbDebug.append(SystemUtils.LINE_SEPARATOR)
             .append("##### Facebook->readLastPosts start...")
             .append(SystemUtils.LINE_SEPARATOR)
             .append(request)
             .append(SystemUtils.LINE_SEPARATOR);

      ReadPostResponse response = facebookService.readLastPosts(request);

      sbDebug.append(response)
             .append(SystemUtils.LINE_SEPARATOR);

      return response;
    }
    finally
    {
      sbDebug.append("##### Facebook->readLastPosts end")
             .append(SystemUtils.LINE_SEPARATOR);
      logger.debug(sbDebug.toString());
    }
  }

}