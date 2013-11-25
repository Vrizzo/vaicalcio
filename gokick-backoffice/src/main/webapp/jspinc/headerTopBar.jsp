<%@page import="it.newmedia.gokick.backOffice.UserContext" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<div class="topBar">

    <p class="left">
        <a href="<s:url action="AdminUsers!viewAll"/>">GoKickers</a>
        &nbsp;
        <s:url action="AdminPictures!viewAll" var="AdminPicturesUrl">
            <s:param name="pictureStatus">PENDING</s:param>
        </s:url>
        <a href="${AdminPicturesUrl}">Picture Cards</a>
        &nbsp;
        <a href="<s:url action="AdminSportCenters!viewAll"/>">Sports Centres</a>
        &nbsp;
        <a href="<s:url action="AdminMatches!viewAll"/>">Matches</a>
        <!--      <a href="javascript:void(0);">Matches(OFF)</a>-->
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <c:set var="ctrlUserRole">
            <%= UserContext.getInstance().getIdCountryFilter()%>
        </c:set>
        <c:if test="${ctrlUserRole==0}">
            <s:url action="adminSite!input" var="AdminSiteUrl"/>
            <a href="${AdminSiteUrl}">Site Manager</a>
        </c:if>

    </p>


</div>
