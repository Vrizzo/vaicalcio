<%@tag description="Stampa il contenuto del tag se l'utente è loggato" pageEncoding="UTF-8"
%><%@ tag body-content="scriptless"
%><%@ taglib prefix="s" uri="/struts-tags"
%><s:if test="userContext.loggedIn == true"><jsp:doBody/></s:if>