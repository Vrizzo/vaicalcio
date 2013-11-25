<%@tag description="Stampa un traduzione nella lingua corrente" pageEncoding="UTF-8"
%><%@ taglib prefix="s" uri="/struts-tags"
%><%@attribute name="key" required="true"
%><%@attribute name="arg01"
%><%@attribute name="arg02"
%><%@attribute name="arg03"
%><%@attribute name="arg04"
%><%@attribute name="escapeJavaScript" required="false" type="java.lang.Boolean"
%><s:component template="translationArgs.ftl"
  ><s:param name="key">${key}</s:param
  ><s:param name="arg01">${arg01 == null ? '<!null>' : arg01}</s:param
  ><s:param name="arg02">${arg02 == null ? '<!null>' : arg02}</s:param
  ><s:param name="arg03">${arg03 == null ? '<!null>' : arg03}</s:param
  ><s:param name="arg04">${arg04 == null ? '<!null>' : arg04}</s:param
  ><s:param name="escapeJavaScript">${escapeJavaScript == null ? 'false' : escapeJavaScript}</s:param
></s:component>