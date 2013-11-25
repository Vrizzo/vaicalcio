<%@ page contentType="text/html" pageEncoding="UTF-8"  %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<p class="${lastMessage!=''?'confirmMessage':'confirmMessageOff'}">
 <s:property value="lastMessage" />
</p>