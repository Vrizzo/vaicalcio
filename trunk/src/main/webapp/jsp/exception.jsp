<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="it" xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h1>An unexpected error occurred</h1>
<font color="red">
    <s:property value="exception"/><br/>
</font>

<div>
    <s:property value="exceptionStack"/>
</div>
</body>
</html>
