<%@tag description="stampa i numeri delle partite organizzate e delle sfide" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@attribute name="userInfo" required="true" type="it.newmedia.gokick.site.infos.UserInfo" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<c:if test="${userInfo.recordedChallenges > 0 || userInfo.recordedMatches > 0}">
  <c:if test="${userInfo.recordedMatches > 0}">
    <span class="matchPlay" title="${userInfo.recordedMatches}&nbsp;<u:translation key="label.title.partiteOrganizzate"/>">${userInfo.recordedMatches}</span>
  </c:if>
  <c:if test="${userInfo.recordedChallenges > 0 && userInfo.recordedMatches > 0}">
    +
  </c:if>
  <c:if test="${userInfo.recordedChallenges > 0}">
    <span class="matchOrg">${userInfo.recordedChallenges}</span>
  </c:if>
</c:if>
