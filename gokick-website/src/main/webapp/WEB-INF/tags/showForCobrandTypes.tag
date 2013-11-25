<%@tag description="Stampa il contenuto del tag se il tipo del cobrand è contenuto nei tipi passati, separati da ;" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@attribute name="types" required="true" %>
<c:if test="${fn:contains(types, currentCobrand.type)}"><jsp:doBody/></c:if>
