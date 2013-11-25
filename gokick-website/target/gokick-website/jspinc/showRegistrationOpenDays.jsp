<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>

<s:if test="registrationOpenDate!=''">
  <u:translation key="label.calendarAperteTra" /> <strong>${registrationOpenDate}</strong>
</s:if>
