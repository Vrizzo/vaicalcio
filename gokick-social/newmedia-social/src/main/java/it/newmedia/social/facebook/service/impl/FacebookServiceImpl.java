package it.newmedia.social.facebook.service.impl;

import it.newmedia.net.HttpConnection;
import it.newmedia.social.facebook.dto.*;
import it.newmedia.social.facebook.service.FacebookService;
import it.newmedia.social.facebook.utils.Convert;
import it.newmedia.social.service.SecurityService;
import it.newmedia.social.utils.JSONUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.springframework.util.SystemPropertyUtils;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class FacebookServiceImpl implements FacebookService
{

  private static final Logger logger = Logger.getLogger(FacebookServiceImpl.class);

  private String urlOpenGraphPosts;

  public void setUrlOpenGraphPosts(String urlOpenGraphPosts)
  {
    this.urlOpenGraphPosts = urlOpenGraphPosts;
  }

  private String urlOpenGraphFeed;

  public void setUrlOpenGraphFeed(String urlOpenGraphFeed)
  {
    this.urlOpenGraphFeed = urlOpenGraphFeed;
  }

  private int readDataTimeout;

  public void setReadDataTimeout(int readDataTimeout)
  {
    this.readDataTimeout = readDataTimeout;
  }

  private SecurityService securityService;

  public void setSecurityService(SecurityService securityService)
  {
    this.securityService = securityService;
  }

  @Override
  public ReadPostResponse readLastPosts(ReadPostRequest readPostRequest)
  {
    try
    {
      if (readPostRequest == null)
      {
        throw new IllegalArgumentException("Param [readPostRequest] cannot be null");
      }
      if (!securityService.login(readPostRequest.getLogin()
                                                .getUsername(), readPostRequest.getLogin()
                                                                               .getPassword()))
      {
        return new ReadPostResponse("Not authorized");
      }
      if (StringUtils.isBlank(readPostRequest.getIdUser()))
      {
        throw new IllegalArgumentException("Param [request->idUser] cannot be empty, null or blank");
      }
      if (StringUtils.isBlank(readPostRequest.getAccessToken()))
      {
        throw new IllegalArgumentException("Param [request->accessToken] cannot be empty, null or blank");
      }
      HttpConnection httpConnection = buildHttpConnection(this.urlOpenGraphPosts, readPostRequest.getIdUser(), readPostRequest.getAccessToken());
      httpConnection.doGet();
      if (httpConnection.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        String errorMessage = checkIfErrorResponse(httpConnection.getResponse());
        if (StringUtils.isEmpty(errorMessage))
        {
          throw new Exception("Invalid HHTP response code: " + httpConnection.getResponseCode());
        }
        return new ReadPostResponse(errorMessage);
      }
      String data = httpConnection.getResponse();

      List<ReadPostResponseData> posts = Convert.jsonStringToDtoReadPostResponseDataList(data);
      ReadPostResponse readPostResponse = new ReadPostResponse();
      readPostResponse.setSuccess(true);
      readPostResponse.setPosts(posts);
      return readPostResponse;
    }
    catch (UnknownHostException uhex)
    {
      logger.error("Error reading posts", uhex);
      return new ReadPostResponse("Cannot connect to: " + uhex.getMessage());
    }
    catch (Exception ex)
    {
      logger.error("Error reading posts", ex);
      return new ReadPostResponse(ex.getMessage());
    }
  }

  @Override
  public WritePostResponse writePosts(WritePostRequest writePostRequest)
  {
    try
    {
      if (writePostRequest == null)
      {
        throw new IllegalArgumentException("Param [writePostRequest] cannot be null");
      }
      if (!securityService.login(writePostRequest.getLogin()
                                                 .getUsername(), writePostRequest.getLogin()
                                                                                 .getPassword()))
      {
        return new WritePostResponse("Not authorized");
      }
      if (CollectionUtils.isEmpty(writePostRequest.getPosts()))
      {
        throw new IllegalArgumentException("Number of posts to write is 0");
      }
      logger.debug("Found " + writePostRequest.getPosts()
                                              .size() + " to write..");
      WritePostResponse writePostResponse = new WritePostResponse();
      int writeError = 0;
      for (int i = 0; i < writePostRequest.getPosts()
                                          .size(); i++)
      {
        try
        {
          WritePostRequestData writePostRequestData = writePostRequest.getPosts()
                                                                      .get(i);

          HttpConnection httpConnection = buildHttpConnection(this.urlOpenGraphFeed, writePostRequestData.getIdUser());
          Map<String, String> params = Convert.dtoWritePostRequestDataToMapParams(writePostRequestData);
          httpConnection.doPost(params);
          if (httpConnection.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
          {
            String errorMessage = checkIfErrorResponse(httpConnection.getResponse());
            if (StringUtils.isEmpty(errorMessage))
            {
              writePostResponse.getPosts()
                               .add(new WritePostResponseData("Invalid HHTP response code: " + httpConnection.getResponseCode()));
              continue;
            }
            writePostResponse.getPosts()
                             .add(new WritePostResponseData(errorMessage));
            continue;
          }
          String dataReceived = httpConnection.getResponse();


          WritePostResponseData writePostResponseData = new WritePostResponseData(JSONUtil.getProperty(dataReceived, "id"),
                                                                                  writePostRequestData.getIdUser());
          writePostResponse.getPosts()
                           .add(writePostResponseData);
        }
        catch (UnknownHostException uhex)
        {
          logger.error("Error reading posts", uhex);
          writeError++;
          writePostResponse.getPosts()
                           .add(new WritePostResponseData("Cannot connect to: " + uhex.getMessage()));
        }
        catch (Exception ex)
        {
          logger.error("Error writing posts", ex);
          writeError++;
          writePostResponse.getPosts()
                           .add(new WritePostResponseData(ex.getMessage()));
        }
      }
      if (writeError == 0)
      {
        writePostResponse.setSuccess(true);
      }
      else
      {
        writePostResponse.setSuccess(false);
        writePostResponse.setErrorMessage(String.format("Write fails for %s post on %s, see error message in each post response data", writeError,
                                                        writePostResponse.getPosts()
                                                                         .size()));
      }
      return writePostResponse;
    }
    catch (Exception ex)
    {
      logger.error("Error writing posts", ex);
      return new WritePostResponse(ex.getMessage());
    }

  }


  private HttpConnection buildHttpConnection(String url, Object... params)
  {
    url = String.format(url, params);
    HttpConnection httpConnection = new HttpConnection(url, this.readDataTimeout);
    httpConnection.setFollowRedirects(false);
    httpConnection.setUserAgent(" ");
    return httpConnection;
  }

  private String checkIfErrorResponse(String response)
  {
    try
    {
      JSONObject jsonObject = JSONObject.fromObject(response);
      JSONObject jsonError = jsonObject.getJSONObject("error");
      return String.format("Error->[Type: %s, Message: %s]", JSONUtil.getSafeProperty(jsonError, "type"),
                           JSONUtil.getSafeProperty(jsonError, "message"));
    }
    catch (Exception ex)
    {
      return null;
    }
  }

  public static void main(String[] args)
  {
    System.out
            .println("Inizio ##################################");


    System.out
            .println("Fine ####################################");
  }
}