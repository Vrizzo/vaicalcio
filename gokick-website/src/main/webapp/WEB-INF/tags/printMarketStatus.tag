<%@tag description="Market open or closed" pageEncoding="UTF-8"%>
<%@attribute name="marketStatus" required="true"%>

<%
  String marketStatusString = (String)jspContext.getAttribute("marketStatus");
  boolean marketStatus = Boolean.parseBoolean(marketStatusString);
  if(marketStatus)
  {
%>
    Aperto
<%
  } else {
%>
    Chiuso
<%} %>