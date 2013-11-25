<%@tag description="Stampa una traduzione troncandola al parametro passato" pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="u" tagdir="/WEB-INF/tags/"
%><%@attribute name="key" required="true"
%><%@attribute name="escapeJavaScript" required="false" type="java.lang.Boolean"
%><%@attribute name="length" required="true" type="java.lang.Integer"
%><c:set var="translation"><u:translation key="${key}" escapeJavaScript="${escapeJavaScript}" /></c:set> ${fn:substring(translation, 0, length)}