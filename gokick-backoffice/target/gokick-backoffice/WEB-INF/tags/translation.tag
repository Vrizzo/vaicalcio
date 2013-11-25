<%@tag description="Stampa un traduzione nella lingua corrente" pageEncoding="UTF-8"
        %>
<%@ taglib prefix="s" uri="/struts-tags"
        %>
<%@attribute name="key" required="true"
        %>
<%@attribute name="escapeJavaScript" required="false" type="java.lang.Boolean"
        %>
<s:set name="keyName">${key}</s:set>

<s:property value="%{getText(#keyName)}"/>
