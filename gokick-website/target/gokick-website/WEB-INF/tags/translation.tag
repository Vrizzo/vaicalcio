<%@tag description="Stampa un traduzione nella lingua corrente" pageEncoding="UTF-8"
%><%@ taglib prefix="s" uri="/struts-tags"
%><%@attribute name="key" required="true"
       %><%@attribute name="lang" required="false"
%><%@attribute name="escapeJavaScript" required="false" type="java.lang.Boolean"
       %><s:component template="translation.ftl"><s:param name="key">${key}</s:param><s:param name="escapeJavaScript">${escapeJavaScript == null ? 'false' : escapeJavaScript}</s:param><s:param name="lang">${lang == null ? '' : lang}</s:param></s:component>