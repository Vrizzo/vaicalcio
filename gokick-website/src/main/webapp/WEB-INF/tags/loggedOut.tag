<%@tag description="Stampa il contenuto del tag se l'utente NON è loggato" pageEncoding="UTF-8"
%><%@ tag body-content="scriptless"
%><%@ taglib prefix="s" uri="/struts-tags"
%><s:if test="userContext.loggedIn == false"><jsp:doBody/></s:if>