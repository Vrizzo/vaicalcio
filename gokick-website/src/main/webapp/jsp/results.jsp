<%@page import="it.newmedia.utils.DateUtil" %>
<%@page import="org.apache.commons.lang.time.DateFormatUtils" %>
<%@page import="org.apache.commons.lang.time.DateUtils" %>
<%@page import="java.util.Date" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax"/>
<s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax"/>
<s:url id="sportCentersByProvinceURL" action="sportCentersByProvince" namespace="/ajax"/>
<s:url id="sportCentersByCityURL" action="sportCentersByCity" namespace="/ajax"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
  <title><s:property value="%{getText('page.title.default')}"/></title>
  <script type="text/javascript">


    function chkAndGetCitiesByProvinces(valore)
    {
      if (valore > 0)
      {
        getCitiesByProvince(valore, 0, 0, 1, '${citiesByProvinceURL}');
        getSportCenterByProvince(valore, '${sportCentersByProvinceURL}');
      }
      else
      {
        $('idSelectCity').options.length = 1;
        $('idSelectSportCenter').options.length = 1;
      }
    }

    function chkAndGetProvincesWithMatches(valore)
    {
      /*$('idSelectSportCenter').options.length = 1;
       $('idSelectSportCenter').size = 1;
       if (valore > 0)
       {
       getProvincesByCountry(valore, 0, 0, 1,'
    ${provincesByCountryURL}');
     }*/

      $('idSelectSportCenter').options.length = 1;

      if (valore > 0)
      {
        var options = {
          startFrom:0,
          callback:'getCitiesByProvince($(\'idSelectProvince\').value, 0, 0, 1, \'${citiesByProvinceURL}\',1);'
        }
        getProvincesByCountry(valore, 0, 0, 1, '${provincesByCountryURL}', options, 1);

      }

    }

  </script>
  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
  <!--### end Google Analitics inclusion ###-->
</head>
<body>
<div class="wrapper">

<!--### start header ###-->
<jsp:include page="../jspinc/header.jsp" flush="true"/>
<!--### end header ###-->

<!--### start leftcolumn ###-->
<jsp:include page="../jspinc/leftColumn.jsp" flush="true">
  <jsp:param name="tab" value="results"/>
</jsp:include>
<!--### end leftcolumn ###-->

<!--### start centralcolumn ###-->
<div class="centralCol">
<div class="topPageWrap">
  <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
</div>


<!--### start mainContent ###-->
<div class="mainContentIndent calCont">

<div class="">

<h1><u:translation key="label.Risultati"/></h1>
<c:set var="partita"><u:translation key="label.p_artita"/></c:set>
<c:set var="partite"><u:translation key="label.udmPartite"/></c:set>
<c:set var="vinta"><u:translation key="label.vinta"/></c:set>
<c:set var="vinte"><u:translation key="label.vinte"/></c:set>
<c:set var="nulla"><u:translation key="label.nulla"/></c:set>
<c:set var="nulle"><u:translation key="label.nulle"/></c:set>
<c:set var="persa"><u:translation key="label.persa"/></c:set>
<c:set var="perse"><u:translation key="label.perse"/></c:set>
<c:set var="organizzata"><u:translation key="label.organizzata"/></c:set>
<c:set var="organizzate"><u:translation key="label.organizzate"/></c:set>

<p class="titleList">
  <u:translation key="label.resultsHaiGiocato_"/>&nbsp;<s:property value="currentUserStatisticInfo.allTot"/>&nbsp;${currentUserStatisticInfo.allTot == 1 ? partita : partite}&nbsp;<u:translation key="label.dicui"/>&nbsp;
  <s:property value="currentUserStatisticInfo.allWin"/>&nbsp;${currentUserStatisticInfo.allWin == 1 ? vinta : vinte}, <s:property value="currentUserStatisticInfo.allDraw"/>&nbsp;${currentUserStatisticInfo.allDraw == 1 ? nulla : nulle}&nbsp;<u:translation key="label.e"/>&nbsp;
  <s:property value="currentUserStatisticInfo.allLose"/>&nbsp;${currentUserStatisticInfo.allLose == 1 ? persa : perse}
</p>

<div class="headList">
  <p>
    <strong><s:property value="convenedMatchNumber"/>&nbsp;${convenedMatchNumber == 1 ? partita : partite}</strong>&nbsp;<s:property value="searchFilterSummaryText"/>

    [<a class="showHideBoxLink" href="javascript: hideDisplayUserSearchBox('idBoxSearch', 'idBoxSearchHide', 'idBoxSearchDisplay');"><span id="idBoxSearchDisplay" style="display: none;"><u:translation key="label.opzioniRicerca"/></span><span id="idBoxSearchHide"><u:translation key="label.nascondiOpzioni"/></span></a>]
  </p>
</div>

<div class="searchBox" id="idBoxSearch" >
  <s:if test="preformedSearch">
    <script type="text/javascript">
      displayUserSearchBox('idBoxSearch', 'idBoxSearchHide', 'idBoxSearchDisplay');
    </script>
  </s:if>
  <s:form action="results!searchResult" method="post">
    <s:hidden name="preformedSearch" value="%{preformedSearch}" id="idPreformedSearch"/>
    <table class="maximized">
      <tr>
        <td style="width:120px;"><u:translation key="label.country"/>:</td>
        <td colspan="2">
          <s:select
                  name="filterIdCountry"
                  list="countryList"
                  listKey="id"
                  listValue="name"
                  id="idSelectCountry"
                  value="filterIdCountry"
                  onchange="chkAndGetProvincesWithMatches($(this).value)"
                  />
        </td>
      </tr>

      <tr>
        <td><u:translation key="label.province"/>:</td>
        <td colspan="2">
          <s:select
                  name="filterIdProvince"
                  list="provinceList"
                  listKey="id"
                  listValue="name"
                  value="filterIdProvince"
                  id="idSelectProvince"
                  onchange="getCitiesByProvince($(this).value, 0, 0, 1, '%{citiesByProvinceURL}',1); getSportCenterByProvince($(this).value, '%{sportCentersByProvinceURL}');"/>
          <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />"/></span>

        </td>
      </tr>
      <tr>
        <td><u:translation key="label.city"/>:</td>
        <td colspan="2">
          <s:select
                  name="filterIdCity"
                  headerKey="0"
                  headerValue="%{getText('label.Tutte')}"
                  list="cityList"
                  listKey="id"
                  listValue="name"
                  value="filterIdCity"
                  id="idSelectCity"
                  onchange="getSportCenterByCity($(this).value, '%{sportCentersByCityURL}');"/>
          <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />"/></span>
        </td>
      </tr>
      <tr>
        <td><u:translation key="label.campoDiGioco"/>:</td>
        <td>
          <s:select
                  name="filterIdSportCenter"
                  headerKey="0"
                  headerValue="%{getText('label.Tutti')}"
                  list="sportCenterList"
                  listKey="id"
                  listValue="name"
                  value="filterIdSportCenter"
                  id="idSelectSportCenter"/>
          <span id="idWaitSportCenter" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />"/></span>
        </td>
        <td class="right">
          <s:submit value="%{getText('label.Cerca')}" cssClass="btn"/>
        </td>
      </tr>
    </table>

    <script type="text/javascript">
      if ($('idSelectProvince').value == '')
      {
        chkAndGetProvincesWithMatches($('idSelectCountry').value);
      }
    </script>

    <div class="searchMenu">
      <table>
        <tr>
          <td style="width:115px;"><u:translation key="label.visualizzaSolo"/>:</td>
          <td><a href="<s:url action="results!viewPlayedMatch" />"><s:property value="currentUserStatisticInfo.allTot"/>&nbsp;${currentUserStatisticInfo.allTot == 1 ? partita : partite}&nbsp;<u:translation key="label.partitegiocate"/></a></td>
          <td class="centred">.:</td>
          <td><a href="<s:url action="results!viewOrganizedMatch" />"><s:property value="organizedMatchNumber"/>&nbsp;<u:translation key="label.daMe"/>&nbsp;${organizedMatchNumber == 1 ? organizzata : organizzate}</a></td>
        </tr>
      </table>
    </div>
  </s:form>
</div>

<div class="clear">&nbsp;</div>

<s:if test="preformedSearch == false">
  <c:set var="dtRequestURI">results!viewResults.action</c:set>
</s:if>
<s:else>
  <c:set var="dtRequestURI">results!searchResult.action</c:set>
</s:else>
<s:if test="playedSearch == true">
  <c:set var="dtRequestURI">results!viewPlayedMatch.action</c:set>
</s:if>
<s:if test="organizedSearch == true">
  <c:set var="dtRequestURI">results!viewOrganizedMatch.action</c:set>
</s:if>

<s:if test="calendarInfoList.size <= 0">
  <strong><u:translation key="message.resultsNoPartite"/></strong>
</s:if>
<s:else>

<c:set var="titleData"><u:translation key="label.data"/></c:set>
<c:set var="titleCampo"><u:translation key="label._Campo"/></c:set>
<c:set var="titleTipo"><u:translation key="label.Tipo"/></c:set>
<c:set var="titleA"><u:translation key="label.A"/></c:set>
<c:set var="titleRisultato"><u:translation key="label.Risultato"/></c:set>
<c:set var="titleVoto"><u:translation key="label.Voto"/></c:set>
<c:set var="titlePagelle"><u:translation key="label.Pagelle"/></c:set>
<c:set var="titlePr"><u:translation key="label.ProvinciaAbbreviata"/></c:set>
<c:set var="titleGoal"><u:translation key="label.Goal"/></c:set>
<c:set var="matchType"><u:translation key="label.match"/></c:set>
<c:set var="titleNoPost"><u:translation key="label.NoPost"/></c:set>
<c:set var="titlePost"><u:translation key="label.postPresent"/></c:set>

  <display:table name="calendarInfoList" id="calendarInfo" requestURI="${dtRequestURI}" class="oldEvent resultList" pagesize="10">
  <u:displayTablePaginator/>
  <c:set var="dateStart"><utl:formatDate date="${calendarInfo.matchInfo.matchStart}" formatKey="format.date_12"/></c:set>

  <c:choose>
    <c:when test="${calendarInfo.currentUserRegistered}">
      <c:set var="currentUserRegisteredClass">calendarHighlightedLine</c:set>
    </c:when>
    <c:when test="${calendarInfo.currentUserRequest}">
      <c:set var="currentUserRegisteredClass">calendarHighlightedLine2</c:set>
    </c:when>
    <c:otherwise>
      <c:set var="currentUserRegisteredClass"></c:set>
    </c:otherwise>
  </c:choose>

  <s:url action="archiveMatch!report" var="viewReportUrl">
    <s:param name="idMatch">${calendarInfo.matchInfo.id}</s:param>
  </s:url>
  <s:url action="archiveMatch" method="input" var="viewMatchUrl">
    <s:param name="idMatch">${calendarInfo.matchInfo.id}</s:param>
  </s:url>
  <s:url action="matchComments!viewAll" var="viewMatchCommentsUrl">
    <s:param name="idMatch">${calendarInfo.matchInfo.id}</s:param>
  </s:url>

  <display:column title="${titleData}" class="${currentUserRegisteredClass}" style="width: 20px;">
    ${fn:substring(dateStart, 0, 3)}
  </display:column>

  <display:column title="" class="${currentUserRegisteredClass}" style="width: 75px;">
    ${fn:substring(dateStart, 4, 13)}
  </display:column>

  <display:column title="${titlePr}" class="${currentUserRegisteredClass}" style="width:25px;">
    ${calendarInfo.matchInfo.sportCenterProvinceAbbreviation}
  </display:column>

  <display:column title='${titleCampo}' class="${currentUserRegisteredClass}" style="width:175px;">
    <a target="blank" href="${calendarInfo.matchInfo.googleMapUrl}">
        ${calendarInfo.matchInfo.sportCenterName}
    </a>
  </display:column>

  <display:column title="${titleTipo}" class="${currentUserRegisteredClass} lblMatch" style="width:40px;">
    ${matchType}
  </display:column>

  <display:column title="${titleA}" class="${currentUserRegisteredClass}" style="width:30px;">
    ${calendarInfo.matchInfo.matchTypeTotPlayers}
  </display:column>

  <display:column title="${titleRisultato}" class="${currentUserRegisteredClass}" style="width:85px;">
    <c:if test="${calendarInfo.matchInfo.canceled!='true'}">
      ${calendarInfo.matchInfo.resultGoal}&nbsp;
      <c:if test="${calendarInfo.currentUserRequest!='true' && calendarInfo.currentUserRegistered=='true' }">
        ${calendarInfo.currentUserResultText}
      </c:if>
    </c:if>
  </display:column>

  <display:column title="${titleVoto}" class="${currentUserRegisteredClass}" style="width:35px;">
    <c:if test="${calendarInfo.currentUserRequest!='true' && calendarInfo.matchInfo.canceled!='true' && calendarInfo.currentUserRegistered=='true' }">
      <c:if test="${calendarInfo.currentUserVote!=''}">
        <fmt:formatNumber value="${calendarInfo.currentUserVote}" pattern="#.#"/>
      </c:if>
    </c:if>
  </display:column>

  <display:column title="${titleGoal}" class="${currentUserRegisteredClass}" style="width:35px;">
    <c:if test="${calendarInfo.currentUserRequest!='true' && calendarInfo.matchInfo.canceled!='true' && calendarInfo.currentUserGoals>0 && calendarInfo.currentUserRegistered=='true' }">
      ${calendarInfo.currentUserGoals}
    </c:if>
  </display:column>

  <display:column title="${titlePagelle}" class="${currentUserRegisteredClass} ratings">

    <c:choose>
      <c:when test="${calendarInfo.matchInfo.canceled}">
        <s:url action="matchComments!viewAll" var="viewMatchCanceledUrl">
          <s:param name="idMatch">${calendarInfo.matchInfo.id}</s:param>
        </s:url>
        <a href="${viewMatchCanceledUrl}">
          <u:printIconReportCalendarMatch guiCalendarInfo="${calendarInfo}"/>
        </a>
      </c:when>
      <c:when test="${calendarInfo.matchInfo.recorded}">
        <a href="${viewReportUrl}">
          <u:printIconReportCalendarMatch guiCalendarInfo="${calendarInfo}"/>
        </a>
      </c:when>
      <c:otherwise>
        <a href="${viewMatchUrl}">
          <u:printIconReportCalendarMatch guiCalendarInfo="${calendarInfo}"/>
        </a>
      </c:otherwise>
    </c:choose>

    &nbsp;

    <c:if test="${calendarInfo.hasComments}">
      <a href="${viewMatchCommentsUrl}">
        <c:choose>
          <c:when test="${calendarInfo.commentsToRead}">
            <img src="<s:url value="/images/comment3.gif" encode="false" />" title="${titlePost}"/>
          </c:when>
          <c:otherwise>
            <img src="<s:url value="/images/comment3_fade.gif" encode="false" />" title="${titleNoPost}"/>
          </c:otherwise>
        </c:choose>
      </a>
    </c:if>

  </display:column>
</display:table>
<%-- Date end = new Date();
    Long diff = DateUtil.getDiffMillis(end, start);
%>
<%=diff%--%>

</s:else>

</div>

</div>
<!--### end mainContent ###-->

</div>
<!--### end centralcolumn ###-->

<!--### start rightcolumn ###-->
<jsp:include page="../jspinc/rightcolumn.jsp" flush="true"/>
<!--### end rightcolumn ###-->

<!--### start footer ###-->
<jsp:include page="../jspinc/footer.jsp" flush="true"/>
<!--### end footer ###-->

</div>

</body>
</html>