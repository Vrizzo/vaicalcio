<%-- 
    Document   : cancelCacheResponse
    Created on : 24-feb-2011, 16.13.41
    Author     : ggeroldi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>batch Response</title>
        <%
            String result = request.getAttribute("result").toString();
            if (result == "OK")
            {
              response.setStatus(200);
            }
            else
            {
              response.setStatus(404);
            }
        %>

    </head>
    <body>
        
    </body>
</html>
