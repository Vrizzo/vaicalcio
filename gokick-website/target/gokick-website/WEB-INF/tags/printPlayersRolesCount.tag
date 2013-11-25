<%@tag description="print players roles count" pageEncoding="UTF-8"%>
<%@tag import="java.util.List"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@attribute name="playersRolesCount" type="java.util.List" required="true"%>
<%@attribute name="all" required="true"%>

<%
  List playersRolesCount = (List)jspContext.getAttribute("playersRolesCount");
  String allString = (String)jspContext.getAttribute("all");
  boolean all = Boolean.parseBoolean(allString);
  if(playersRolesCount.size() == 4) {
    if(all) {
%>

<s:set name="portiere" ><u:translation key="label._Portiere"/></s:set>
<s:set name="portiere" >${fn:substring(portiere, 0, 3)}</s:set>

<s:set name="difensore" ><u:translation key="label._Difensore"/></s:set>
<s:set name="difensore" >${fn:substring(difensore, 0, 3)}</s:set>

<s:set name="centrocampista" ><u:translation key="label._Centrocampista"/></s:set>
<s:set name="centrocampista" >${fn:substring(centrocampista, 0, 3)}</s:set>

<s:set name="attaccante" ><u:translation key="label._Attaccante"/></s:set>
<s:set name="attaccante" >${fn:substring(attaccante, 0, 3)}</s:set>

<%= playersRolesCount.get(0) %> ${portiere}
<%= playersRolesCount.get(1) %> ${difensore}
<%= playersRolesCount.get(2) %> ${centrocampista}
<%= playersRolesCount.get(3) %> ${attaccante}
<% } else { %>
(<%= playersRolesCount.get(0) %> ${portiere},
<%= playersRolesCount.get(1) %> ${difensore},
<%= playersRolesCount.get(2) %> ${centrocampista},
<%= playersRolesCount.get(3) %> ${attaccante})
<% } } %>