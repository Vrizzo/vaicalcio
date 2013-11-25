<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@attribute name="guiCalendarInfo" required="true" type="it.newmedia.gokick.site.guibean.GuiCalendarInfo" %>
<c:choose>
    <c:when test="${guiCalendarInfo.matchInfo.idReporter==currentUser.id || guiCalendarInfo.currentUserOwner }">
      <c:set var="userReporter">true</c:set>
    </c:when>
    <c:otherwise>
      <c:set var="userReporter">false</c:set>
    </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${guiCalendarInfo.matchInfo.canceled}">
          <c:set var="path">ann</c:set>
          <c:set var="altText"><u:translation key="label.annullata"/></c:set>
   </c:when>
   <c:otherwise>
            <c:set var="altText"><u:translation key="label.pagellelACuraDi"/> ${guiCalendarInfo.matchInfo.reporter}</c:set>
            <c:choose>
                <c:when test="${guiCalendarInfo.matchInfo.recorded}">
                  <c:set var="path">pag</c:set>
                </c:when>
                <c:otherwise>
                  <c:set var="path">att</c:set>
                  <c:set var="altText"><u:translation key="label.archiveMatchInAttesa"/> ${guiCalendarInfo.matchInfo.reporter}</c:set>
                </c:otherwise>
            </c:choose>
            <c:if test="${userReporter && guiCalendarInfo.reportEditable}">
              <c:set var="path">${path}-mat</c:set>
            </c:if>
   </c:otherwise>
</c:choose><img  src="images/${path}.gif" title="${altText}" alt="${altText}"/>