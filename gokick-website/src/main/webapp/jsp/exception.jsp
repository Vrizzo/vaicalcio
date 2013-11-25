<%@ page import="org.apache.log4j.Logger" contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="it.newmedia.gokick.site.UserContext" %>
<%@ page import="org.apache.commons.lang.SystemUtils" %>
<%@ page import="java.util.Enumeration" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="it" xmlns="http://www.w3.org/1999/xhtml" lang="it">

  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
  </head>
    
  <body>
    <h1><u:translation key="error.erroreImprevisto"/></h1>
    <%
      try
      {
        StringBuilder sb = new StringBuilder(1000);
        sb.append(SystemUtils.LINE_SEPARATOR);
        sb.append("User: ");
        sb.append(UserContext.getInstance().isLoggedIn() ? UserContext.getInstance().getUser().getId() : "Not logged").append(SystemUtils.LINE_SEPARATOR);
        sb.append("Request:     ").append(request.getAttribute("javax.servlet.forward.request_uri")).append(SystemUtils.LINE_SEPARATOR);
        sb.append("QueryString: ").append(request.getAttribute("javax.servlet.forward.query_string")).append(SystemUtils.LINE_SEPARATOR);
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements())
        {
          String paramName = (String) params.nextElement();
          sb.append("Params: ").append(paramName).append(" = ").append(request.getParameter(paramName)).append(SystemUtils.LINE_SEPARATOR);

        }
        Exception ex = (Exception)request.getAttribute("exception");
        sb.append("Ex message: ").append(ex.getMessage()).append(SystemUtils.LINE_SEPARATOR);
        Logger.getLogger("it.newmedia.gokick").error(sb.toString(), ex);
      }
      catch(Exception e)
      {
        Logger.getLogger("it.newmedia.gokick").error(e, e);
      }
    %>
   <!--
    <font color="red">
      <s:property value="exception" /><br/>
    </font>    
    <div>
      <s:property value="exceptionStack" /> 
    </div>
    -->
  </body>
</html>
