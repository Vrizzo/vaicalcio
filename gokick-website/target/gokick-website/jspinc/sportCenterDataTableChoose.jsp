<%@page contentType="text/xml;charset=UTF-8" %><%@ taglib prefix="s" uri="/struts-tags" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><?xml version="1.0" encoding="utf-8"?>
<ResultSet xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:yahoo:lcl" xsi:schemaLocation="urn:yahoo:lcl http://local.yahooapis.com/LocalSearchService/V2/LocalSearchResponse.xsd"
  totalResultsAvailable="<s:property value="dataTable.totalResultsAvailable" />"
  totalResultsReturned="<s:property value="dataTable.totalResultsReturned" />"
  firstResultPosition="1" 
  sort="<s:property value="sort" />"
  dir="<s:property value="dir" />"
  >
<s:iterator value="dataTable.results" var="result" >
  <Result>
    <Id><![CDATA[<input type="radio" name="sportCenterSelected" value="<s:property value="#result.id" />" />]]></Id>
    <Empty><![CDATA[ &nbsp; ]]></Empty>
    <Name><![CDATA[<a  target="blank" href="<s:property value='#result.googleMapsUrl' />"><s:property value="#result.name" /></a>]]></Name>
    <Address><![CDATA[<s:property value="#result.address" />]]></Address>
    <CityName><![CDATA[<s:property value="#result.cityName" />]]></CityName>
    <ProvinceName><![CDATA[<s:property value="#result.provinceName" />]]></ProvinceName>
    <MatchTypeAvailable><![CDATA[<s:property value="#result.matchTypeAvailable" />]]></MatchTypeAvailable>
    <MatchTypeAvailableSCAll><![CDATA[<s:property value="#result.matchTypeAvailable" />]]></MatchTypeAvailableSCAll>
    <Telephone><![CDATA[<s:property value="#result.telephone" />]]></Telephone>
    <Conventioned><![CDATA[<s:property value="#result.conventioned" />]]></Conventioned>
  </Result>
</s:iterator>
</ResultSet>
