<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
    <title>Site Manager</title>
</head>

<body>

<!--### start wrapper ###-->
<div class="wrapper">

    <!--### start header ###-->
    <jsp:include page="../jspinc/header.jsp" flush="true"/>
    <!--### end header ###-->


    <div style="padding-left:40px;padding-top:50px;">

        <s:url action="adminSite!cancelCache" var="cancelCacheUrl"></s:url>
        <a href="${cancelCacheUrl}" class="btn action1">Delete Cache</a>
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <s:property value="cancelCacheResponse"/>

    </div>


</div>
<!--### end wrapper ###-->
</body>
</html>
