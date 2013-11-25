<%@tag description="put the tag description here" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@attribute name="guiCalendarInfo" required="true" type="it.newmedia.gokick.backOffice.guibean.GuiCalendarInfo" %>

<c:set var="userOwner">true</c:set>
<%--<s:set name="recorded">${guiCalendarInfo.match.recorded}</s:set>
<s:set name="canceled">${guiCalendarInfo.match.canceled}</s:set>--%>
<c:set var="recorded">${guiCalendarInfo.recorded}</c:set>
<c:set var="canceled">${guiCalendarInfo.canceled}</c:set>
<c:set var="editable">${guiCalendarInfo.reportEditable}</c:set>
<c:set var="userReporter">true</c:set>

<c:choose>
    <c:when test="${canceled}">
        <c:set var="path">ann</c:set>
        <c:set var="altText">annullata</c:set>
    </c:when>
    <c:otherwise>
        <c:set var="altText">pagelle a cura di ${guiCalendarInfo.reporter}</c:set>
        <c:choose>
            <c:when test="${recorded}">
                <c:set var="path">pag</c:set>
            </c:when>
            <c:otherwise>
                <c:set var="path">att</c:set>
                <c:set var="altText">in attese delle pagelle di ${guiCalendarInfo.reporter}</c:set>
            </c:otherwise>
        </c:choose>
        <c:if test="${userReporter && editable}">
            <c:set var="path">${path}-mat</c:set>
        </c:if>
    </c:otherwise>
</c:choose>

<img src="images/${path}.gif" title="${altText}" alt="${altText}"/>


