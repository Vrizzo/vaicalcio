<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<ul class="langBar">
  <s:iterator value="languageList">
    <s:if test="userContext.language.language == language">
      <li>
        <strong>${label}</strong>
        <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
      </li>
    </s:if>
    <s:else>
      <li>
        <a href="<s:url includeParams="all" includeContext="true" ><s:param name="request_locale" value="locale" /></s:url>">
            ${label}&nbsp;<img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
        </a>
      </li>
    </s:else>
  </s:iterator>
</ul>
