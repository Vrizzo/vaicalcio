<%@tag description="Stampa il contenuto del tag se il codice del cobrand è contenuto nei codici passati, separati da ;" pageEncoding="UTF-8"
%><%@ tag body-content="scriptless"
%><%@ taglib prefix="c"       uri="/WEB-INF/taglib/c.tld"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"
%><%@attribute name="codes" required="true"
%>
<c:if test="${fn:contains(codes, currentCobrandCode)}"><jsp:doBody/></c:if>
