<%@tag description="Include il contenuto del file specificato recuperandolo dalla cartella static rispetto al path assoluto dell'applicazione" pageEncoding="UTF-8"
%><%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld"
%><%@ taglib prefix="s" uri="/struts-tags"
%><%@ taglib prefix="u" tagdir="/WEB-INF/tags/"
%><%@attribute name="content" required="true"
%><c:set var="contentUrl"><%= it.newmedia.web.WebUtil.getServerUrl(request) %>static_contents/${content}_<s:property value="currentLanguage" />.html</c:set>
<jsp:scriptlet>
try
{
</jsp:scriptlet>
<c:import url="${contentUrl}" />
<jsp:scriptlet>
}
catch(Exception ex)
{
</jsp:scriptlet>
<u:translation key="error.contenutoNonDisponibile" /> - [${content}_<s:property value="currentLanguage" />.html]
<jsp:scriptlet>
}
</jsp:scriptlet>