<%@tag description="Stampa il contenuto del tag se l'utente NON è un organizzatore" pageEncoding="UTF-8"
%><%@ tag body-content="scriptless"
%><%@ taglib prefix="s" uri="/struts-tags"
%><s:if test="userContext.organizer == false"><jsp:doBody/></s:if>