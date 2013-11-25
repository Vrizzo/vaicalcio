<?xml version="1.0" encoding="utf-8"?>
<%@page contentType="text/xml;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<ResultSet xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:yahoo:lcl" xsi:schemaLocation="urn:yahoo:lcl http://local.yahooapis.com/LocalSearchService/V2/LocalSearchResponse.xsd"
  totalResultsAvailable="<s:property value="dataTable.totalResultsAvailable" />"
  totalResultsReturned="<s:property value="dataTable.totalResultsReturned" />"
  firstResultPosition="1"
  sort="<s:property value="sort" />"
  dir="<s:property value="dir" />"
  >
<s:iterator value="dataTable.results" var="result" >
  <Result>
    <Id><![CDATA[<s:checkbox name="userCheckList" fieldValue="%{#result.userInfo.id}" cssClass="chkSelectUser"/>]]></Id>
    <FirstName><![CDATA[<u:printUserName   printName="true"  printSurname="false" userInfo="${result.userInfo}"  linkToDetails="true" showCurrentUserDetails="true" />]]></FirstName>
    <LastName><![CDATA[ <u:printUserName   printName="false" printSurname="true"  userInfo="${result.userInfo}"  linkToDetails="true" showCurrentUserDetails="true" />]]></LastName>
    <Nationality><![CDATA[<img src="<s:url value="/images/flags/"/>${result.userInfo.idNatCountry}.png"  title="${result.userInfo.natCountry}" onError="this.src='<s:url value="/images/"/>country_flag_0.gif';"/>]]>
    </Nationality>
    <City><![CDATA[${result.userInfo.city}]]></City>
    <Role><![CDATA[<c:set var="ruoloLang" ><u:translation key="${result.userInfo.playerRoleKey}"/></c:set>${fn:substring(ruoloLang, 0, 1)}]]></Role>
    <Age><![CDATA[${result.userInfo.age}]]></Age>
    <Played><![CDATA[${result.allTot}]]></Played>
    <Reliability><![CDATA[${result.reliability}%]]></Reliability>
    <Market><![CDATA[<c:if test="${result.userInfo.marketEnabled}"><img src="<s:url value="/images/gioca.gif" encode="false" />" alt="" /></c:if>]]></Market>
    <Current><![CDATA[${result.userInfo.id==currentUser.id}]]></Current>
  </Result>
</s:iterator>
</ResultSet>
