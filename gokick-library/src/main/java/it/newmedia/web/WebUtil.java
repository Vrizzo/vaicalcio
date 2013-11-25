package it.newmedia.web;

import it.newmedia.results.Result;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.newmedia.utils.Convert;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class WebUtil {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String URLENCODE_ENC = "UTF-8";
  private static final String HTTP_HEADER_USER__AGENT = "user-agent";

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --">
  // <editor-fold defaultstate="collapsed" desc="-- Get request parameters --">
  public static Result<String> getRequestParamAsString(String parameterName, ServletRequest request)
  {
    if (request == null)
    {
      return new Result<String>("request is null");
    }

    if (parameterName == null)
    {
      return new Result<String>("parameterName is null");
    }

    String s = request.getParameter(parameterName);

    if (s == null)
    {
      return new Result<String>(String.format("Paramenter %s does not exists", parameterName));
    }

    return new Result<String>(s, true);
  }

  public static Result<Long> getRequestParamAsLong(String parameterName, ServletRequest request)
  {
    Result<String> sr = getRequestParamAsString(parameterName, request);
    if (!sr.isSuccessNotNull())
    {
      return new Result<Long>(sr.getErrorMessage());
    }
    try
    {
      return new Result<Long>(new Long(sr.getValue()), true);
    }
    catch (Exception ex)
    {
      return new Result<Long>(ex);
    }
  }

  public static Result<Integer> getRequestParamAsInteger(String parameterName, ServletRequest request)
  {
    Result<String> sr = getRequestParamAsString(parameterName, request);
    if (!sr.isSuccessNotNull())
    {
      return new Result<Integer>(sr.getErrorMessage());
    }
    try
    {
      return new Result<Integer>(new Integer(sr.getValue()), true);
    }
    catch (Exception ex)
    {
      return new Result<Integer>(ex);
    }
  }

  public static Result<Boolean> getRequestParamAsBoolean(String parameterName, ServletRequest request)
  {
    Result<String> sr = getRequestParamAsString(parameterName, request);
    if (!sr.isSuccess())
    {
      return new Result<Boolean>(sr.getErrorMessage());
    }
    try
    {
      return new Result<Boolean>(new Boolean(sr.getValue()), true);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
  }

  public static Result<Double> getRequestParamAsDouble(String parameterName, ServletRequest request)
  {
    Result<String> sr = getRequestParamAsString(parameterName, request);
    if (!sr.isSuccess())
    {
      return new Result<Double>(sr.getErrorMessage());
    }
    try
    {
      return new Result<Double>(new Double(sr.getValue()), true);
    }
    catch (Exception ex)
    {
      return new Result<Double>(ex);
    }
  }

  public static Result<BigDecimal> getRequestParamAsBigDecimal(String parameterName, ServletRequest request)
  {
    Result<String> sr = getRequestParamAsString(parameterName, request);
    if (!sr.isSuccessNotNull())
    {
      return new Result<BigDecimal>(sr.getErrorMessage());
    }
    try
    {
      return new Result<BigDecimal>(new BigDecimal(sr.getValue()), true);
    }
    catch (Exception ex)
    {
      return new Result<BigDecimal>(ex);
    }
  }

  public static String getRequestParamAsString(String parameterName, ServletRequest request, String defaultValue)
  {
    Result<String> r = getRequestParamAsString(parameterName, request);
    return r.isSuccessNotNull() ? r.getValue() : defaultValue;
  }

  public static long getRequestParamAsLong(String parameterName, ServletRequest request, long defaultValue)
  {
    Result<Long> r = getRequestParamAsLong(parameterName, request);
    return r.isSuccessNotNull() ? r.getValue().longValue() : defaultValue;
  }

  public static int getRequestParamAsInt(String parameterName, ServletRequest request, int defaultValue)
  {
    Result<Integer> r = getRequestParamAsInteger(parameterName, request);
    return r.isSuccessNotNull() ? r.getValue().intValue() : defaultValue;
  }

  public static boolean getRequestParamAsBoolean(String parameterName, ServletRequest request, boolean defaultValue)
  {
    Result<Boolean> r = getRequestParamAsBoolean(parameterName, request);
    return r.isSuccessNotNull() ? r.getValue().booleanValue() : defaultValue;
  }

  public static double getRequestParamAsDouble(String parameterName, ServletRequest request, double defaultValue)
  {
    Result<Double> r = getRequestParamAsDouble(parameterName, request);
    return r.isSuccessNotNull() ? r.getValue().doubleValue() : defaultValue;
  }

  public static BigDecimal getRequestParamAsBigDecimal(String parameterName, ServletRequest request, BigDecimal defaultValue)
  {
    Result<BigDecimal> r = getRequestParamAsBigDecimal(parameterName, request);
    return r.isSuccessNotNull() ? r.getValue() : defaultValue;
  }

  public static Result<String[]> getRequestParamsAsStringArray(String parameterName, ServletRequest request)
  {
    if (request == null)
    {
      return new Result<String[]>("request is null");
    }

    if (parameterName == null)
    {
      return new Result<String[]>("parameterName is null");
    }

    String[] s = request.getParameterValues(parameterName);

    if (s == null)
    {
      return new Result<String[]>(String.format("Paramenter %s does not exists", parameterName));
    }

    return new Result<String[]>(s, true);
  }
  // </editor-fold>

  /**
   * Build and return a string with current server path for web application
   * For example with:
   * http://www.test.it/other/index.do
   * return
   * http://www.test.it/
   * (last / is always append!)
   * @param request A valid HttpServletRequest
   * @return The server path for passed request
   */
  public static String getServerUrl(HttpServletRequest request)
  {
    try
    {
      URL url = new URL(StringUtils.substringBefore(request.getProtocol(), "/"), request.getServerName(), request.getServerPort(), "");
      String serverUrl = url.toString();
      if (!serverUrl.endsWith("/"))
      {
        serverUrl += "/";
      }
      return serverUrl;
    }
    catch (MalformedURLException ex)
    {
      return ex.getMessage();
    }
  }

  /**
   * Build and return a string with current context path for web application
   * For example with:
   * http://www.test.it/other/index.do
   * return
   * http://www.test.it/other/
   * (last / is always append!)
   * @param request A valid HttpServletRequest
   * @return The context path for passed request
   */
  public static String getRequestContext(HttpServletRequest request)
  {
    String requestUrl = request.getRequestURL().toString();
    String servletPath = request.getServletPath();
    String completeUrl = requestUrl.substring(0, requestUrl.lastIndexOf(servletPath) + 1);
    if (!completeUrl.endsWith("/"))
    {
      completeUrl += "/";
    }
    return completeUrl;
  }

  /**
   * Return a string with request url
   * @param request A valid HttpServletRequest
   * @return String with url abuot current request
   */
  public static String getRequestUrl(HttpServletRequest request)
  {
    return WebUtil.getRequestUrl(request, false);
  }

  /**
   * Return a string with request url
   * @param request A valid HttpServletRequest
   * @param withoutQueryString If true query string is never added to request url
   * @return String with url abuot current request
   */
  public static String getRequestUrl(HttpServletRequest request, boolean withoutQueryString)
  {
    StringBuffer completeUrl = request.getRequestURL();
    if (request.getQueryString() != null && !withoutQueryString)
    {
      completeUrl.append("?");
      completeUrl.append(request.getQueryString());
    }
    return completeUrl.toString();
  }

  /**
   * Return user agent string for passed request.
   * Il user agent is not found return an empty string
   * @param request HttpServeltRequest to retrieve user agent
   * @return User agent string or empty string
   */
  public static String getUserAgent(HttpServletRequest request)
  {
    return request.getHeader(WebUtil.HTTP_HEADER_USER__AGENT) != null ? request.getHeader(WebUtil.HTTP_HEADER_USER__AGENT) : "";
  }

  /**
   * Translates a string into <code>x-www-form-urlencoded</code>
   * format. This method uses the platform's default encoding
   * as the encoding scheme to obtain the bytes for unsafe characters.
   * @param strToEncode <code>String</code> to be translated.
   * @return the translated <code>String</code>.
   * @throws java.io.UnsupportedEncodingException If the named encoding is not supported
   */
  public static String encode(String strToEncode) throws UnsupportedEncodingException
  {
    return WebUtil.encode(strToEncode, WebUtil.URLENCODE_ENC);
  }

  /**
   * Translates a string into <code>application/x-www-form-urlencoded</code>
   * format using a specific encoding scheme. This method uses the
   * supplied encoding scheme to obtain the bytes for unsafe
   * characters.
   * <p>
   * <em><strong>Note:</strong> The <a href=
   * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
   * World Wide Web Consortium Recommendation</a> states that
   * UTF-8 should be used. Not doing so may introduce
   * incompatibilites.</em>
   * @param strToEncode <code>String</code> to be translated.
   * @param urlEncodeEnc The name of a supported
   *    <a href="../lang/package-summary.html#charenc">character
   *    encoding</a>.
   * @return the translated <code>String</code>.
   * @exception UnsupportedEncodingException If the named encoding is not supported
   * @see URLDecoder#decode(java.lang.String, java.lang.String)
   * @since 1.4
   */
  public static String encode(String strToEncode, String urlEncodeEnc) throws UnsupportedEncodingException
  {
    if (strToEncode == null)
    {
      return null;
    }
    try
    {
      return URLEncoder.encode(strToEncode, urlEncodeEnc);
    }
    catch (UnsupportedEncodingException ex)
    {
      throw ex;
    }
  }

  /**
   * Return a Result<Cookie> with cookie if found or message about error
   * @param request A valid HttpServletRequest
   * @param name The name of cookie
   * @return String A Result<Cookie>
   */
  public static Result<Cookie> getCookie(HttpServletRequest request, String name)
  {
    if (name == null || name.length() <= 0)
    {
      return new Result<Cookie>("Cannot retrieve a cookie with empty or null name");
    }
    Cookie[] cookies = request.getCookies();
    if (cookies != null)
    {
      for (int i = 0; i < cookies.length; i++)
      {
        if (cookies[i] != null)
        {
          if (cookies[i].getName().equals(name))
          {
            return new Result<Cookie>(cookies[i], true);
          }
        }
      }
    }
    return new Result<Cookie>("Cookie named [" + name + "] not found");
  }

  /**
   * Return value for coockie if found, otherwise defaultValue
   * @param request A valid HttpServletRequest
   * @param name The name of cookie
   * @param defaultValue The default value if cookie not found
   * @return String The cookie value or defaultValue
   */
  public static String getCookieValue(HttpServletRequest request, String name, String defaultValue)
  {
    Result<Cookie> rCookie = WebUtil.getCookie(request, name);
    return rCookie.isSuccessNotNull() ? rCookie.getValue().getValue() : defaultValue;
  }

  /**
   * Return value for coockie if found, otherwise defaultValue
   * @param request A valid HttpServletRequest
   * @param name The name of cookie
   * @param defaultValue The default value if cookie not found or conversion fails
   * @return String The cookie value or defaultValue
   */
  public static int getCookieValue(HttpServletRequest request, String name, int defaultValue)
  {
    Result<Cookie> rCookie = WebUtil.getCookie(request, name);
    return rCookie.isSuccessNotNull() ? Convert.parseInt(rCookie.getValue().getValue(), defaultValue) : defaultValue;
  }
  
  /**
   * Add a cookie to response
   * @param response A valid HttpServletResponse
   * @param name The name of cookie
   * @param value The value of cookie
   * @param maxAge Time for expiration
   */
  public static void setCookie(HttpServletResponse response, String name, String value, int maxAge)
  {
    if (name == null || name.length() <= 0)
    {
      return;
    }
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  /**
   * Add a cookie to response valid only for current session
   * @param response A valid HttpServletResponse
   * @param name The name of cookie
   * @param value The value of cookie
   */
  public static void setCookieSession(HttpServletResponse response, String name, String value)
  {
    if (name == null || name.length() <= 0)
    {
      return;
    }
    Cookie cookie = new Cookie(name, value);
    response.addCookie(cookie);
  }

  /**
   * Remove a cookie setting its maxAge to 0
   * @param response A valid HttpServletResponse
   * @param name The name of cookie
   */
  public static void removeCookie(HttpServletResponse response, String name)
  {
    setCookie(response, name, "", 0);
  }
  // </editor-fold>
}



