<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
<s:url id="sportCentersByProvinceURL" action="sportCentersByProvince" namespace="/ajax" />
<s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
     <script type="text/javascript">

  function chkAndGetCitiesByProvinces(valore)
  {
    if (valore > 0) 
    {
     getCitiesByProvince(valore, 0, 0, 1,'${citiesByProvinceURL}');
     getSportCenterByProvince(valore,'${sportCentersByProvinceURL}' );
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
     getProvincesByCountry(valore, 0, 0, 1,'${provincesByCountryURL}');
    }*/
    $('idSelectSportCenter').options.length = 1;
    if (valore > 0)
        {
          var options = {
            startFrom: 0,
            callback: 'getCitiesByProvince($(\'idSelectProvince\').value, 0, 0, 1, \'${citiesByProvinceURL}\');'
          }
          getProvincesByCountry(valore, 0, 0, 1,'${provincesByCountryURL}', options);

        }

  }
  
  function chkProvince()
  {
    if ($('idSelectProvince').value > 0)
    {
      $('idSearchForm').submit();
    }
    else
      alert('<u:translation key="label.selezionaProvincia"/>');
  }
  
  
     </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body>

    <div class="wrapper">
      <!--### start header ###-->
      <jsp:include page="../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
           <jsp:param name="tab" value="play"/>
      </jsp:include>
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">
  <div class="topPageWrap">
              <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
          </div>  

        <!--### start mainContent ###-->
        <div class="mainContentIndent calCont">

            <h1><u:translation key="label.Calendario"/></h1>

            <s:set name="partita"><u:translation key="label.p_artita" /></s:set>
            <s:set name="partite"><u:translation key="label.udmPartite" /></s:set>
            <s:set name="suggerita"><u:translation key="label.suggerita" /></s:set>
            <s:set name="suggerite"><u:translation key="label.suggerite" /></s:set>
            <s:set name="organizzata"><u:translation key="label.organizzata" /></s:set>
            <s:set name="organizzate"><u:translation key="label.organizzate" /></s:set>

            <p class="titleList">
              <u:translationArgs key="label.calendarioPartiteGiocabili" arg01="${allPlayableMatchNumber}" arg02="${allPlayableMatchNumber == 1 ? partita : partite}" />
            </p>


            <div class="headList" >
              <p>
                <strong><s:property value="convenedMatchNumber" />&nbsp;${convenedMatchNumber == 1 ? partita : partite}</strong>
                <s:if test="preformedSearch == false">
                  &nbsp;${convenedMatchNumber == 1 ? suggerita : suggerite}
                </s:if>
                <s:else>
                    <s:property value="searchFilterSummaryText" />
                </s:else>
                
                [<a class="showHideBoxLink" href="javascript: hideDisplayUserSearchBox('idBoxSearch', 'idBoxSearchHide', 'idBoxSearchDisplay');"><span id="idBoxSearchDisplay" style="display: none;"><u:translation key="label.opzioniRicerca" /></span><span id="idBoxSearchHide" ><u:translation key="label.nascondiOpzioni" /></span></a>]
                
              </p>
            </div>

            <div class="searchBox" id="idBoxSearch" >
              <s:if test="preformedSearch">
                  <script type="text/javascript">
                      displayUserSearchBox('idBoxSearch', 'idBoxSearchHide', 'idBoxSearchDisplay');
                  </script>
              </s:if>
              
              <s:form action="calendar!viewCalendar" method="post" id="idSearchForm"  >
                <s:hidden name="preformedSearch" value="%{preformedSearch}" id="idPreformedSearch" />
                <table class="maximized">
                  <tr>
                    <td style="width:120px;">
                      <u:translation key="label.country"/>:
                    </td>
                    <td colspan="2">
                      
                      <s:select
                        name="filterIdCountry"
                        list="countryList"
                        listKey="id"
                        listValue="name"
                        id="idSelectCountry"
                        value="filterIdCountry"
                        onchange="chkAndGetProvincesWithMatches($(this).value);"
                        />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <u:translation key="label.province" />:
                    </td>
                    <td colspan="2">
                      
                      <s:select
                        name="filterIdProvince"
                        list="provinceList"
                        listKey="id"
                        listValue="name"
                        value="filterIdProvince"
                        id="idSelectProvince"
                        onchange="getCitiesByProvince($(this).value, 0, 0, 1, '%{citiesByProvinceURL}'); getSportCenterByProvince($(this).value, '%{sportCentersByProvinceURL}');" />
                        <%--headerKey="0"
                        headerValue="%{getText('label.selezionaProvincia')}"--%>
                         <%--onchange="chkAndGetCitiesByProvinces($(this).value);" />--%>
                      <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.city"/>:</td>
                    <td colspan="2">
                      <s:url id="sportCentersByCityURL" action="sportCentersByCity" namespace="/ajax" />
                      <s:select
                        name="filterIdCity"
                        headerKey="0"
                        headerValue="%{getText('label.Tutte')}"
                        list="cityList"
                        listKey="id"
                        listValue="name"
                        value="filterIdCity"
                        id="idSelectCity"
                        onchange="getSportCenterByCity($(this).value, '%{sportCentersByCityURL}');" />
                      <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
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
                        id="idSelectSportCenter" />
                      <span id="idWaitSportCenter" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                    </td>
                    <td class="right">
                      
                      <s:submit value="%{getText('label.Cerca')}" cssClass="btn"  />
                      <%--<input type="button" value="<u:translation key="label.Cerca"/>" class="btn" onclick="chkProvince();" />--%>
                    </td>
                  </tr>
                </table>

                <div class="searchMenu">
                  <table>
                    <tr>
                      <td style="width:115px;"><u:translation key="label.visualizzaSolo"/>:</td>
                      <td><a href="<s:url action="calendar!viewFriendsMatch" />"><s:property value="friendsMatchNumber" />&nbsp;${friendsMatchNumber == 1 ? partita : partite}&nbsp;<u:translation key="label.deiMieiAmici"/></a></td>
                      <td class="centred">.:</td>
                      <td><a href="<s:url action="calendar!viewRegisteredMatch" />"><s:property value="registeredMatchNumber" />&nbsp;<u:translation key="label.partiteiscritto"/></a></td>
                      <td class="centred">.:</td>
                      <td><a href="<s:url action="calendar!viewOrganizedMatch" />"><s:property value="organizedMatchNumber" />&nbsp;<u:translation key="label.daMe"/>&nbsp;${organizedMatchNumber == 1 ? organizzata : organizzate}</a></td>
                    </tr>
                  </table>
                </div>
              </s:form>
            </div>

            <div class="clear">&nbsp;</div>

            <s:if test="guiCalendarDayInfoNotRecordedList.size > 0">

              <display:table name="guiCalendarDayInfoNotRecordedList" id="guiCalendarDayInfoNotRecordedListItem" class="fatherTable oldEvent">
                <u:displayTablePaginator />
                <display:column >
                  <s:set name="itemListsize">${guiCalendarDayInfoNotRecordedListItem.listSize}</s:set>
                  <s:set name="rowCount" value="0"></s:set>
                  <display:table name="${guiCalendarDayInfoNotRecordedListItem.guiCalendarInfoList}" id="guiCalendarDayInfoNotRecordedListItemMatch" >                                        
                    <u:displayTablePaginator />
                    <s:set name="currentUserRegistered">${guiCalendarDayInfoNotRecordedListItemMatch.currentUserRegistered}</s:set>
                    <s:set name="currentUserRequest">${guiCalendarDayInfoNotRecordedListItemMatch.currentUserRequest}</s:set>
                    <s:if test="#currentUserRegistered == 'true'">
                      <s:set name="currentUserRegisteredClass">calendarHighlightedLine</s:set>
                    </s:if>
                    <s:elseif test="#currentUserRequest== 'true'">
                      <s:set name="currentUserRegisteredClass">calendarHighlightedLine2</s:set>
                    </s:elseif>
                    <s:else>
                      <s:set name="currentUserRegisteredClass"></s:set>
                    </s:else>
                    <s:set name="titleOra"><u:translation key="label.Ora" /></s:set>
                    <s:set name="titlePr"><u:translation key="label.ProvinciaAbbreviata" /></s:set>
                    <s:set name="titleA"><u:translation key="label.a_"/></s:set>
                    <s:set name="titleOrganizzatore"><u:translation key="label.Organizzatore"/></s:set>
                    <s:set name="titleDay"><utl:formatDate date='${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.matchStart}' formatKey='format.date_8'  /></s:set>
                    <s:set name="titleTipo"><u:translation key="label.Tipo"/></s:set>
                    <s:set name="rowCount"  value="#rowCount =#rowCount + 1" />
                    <s:set name="idUserOwner">${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.idUserOwner}</s:set>
                    <s:set name="recordedMatchesUserOwner">${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.recordedMatchesUserOwner}</s:set>
                    <s:set name="recordedChallengesUserOwner">${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.recordedChallengesUserOwner}</s:set>
                    <s:set name="hasComments">${guiCalendarDayInfoNotRecordedListItemMatch.hasComments}</s:set>
                    <s:set name="hasNewComments">${guiCalendarDayInfoNotRecordedListItemMatch.commentsToRead}</s:set>
                    <s:url action="archiveMatch" method="input" var="archiveMatchUrl">
                      <s:param name="idMatch">${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.id}</s:param>
                    </s:url>
                    <s:url action="matchComments!viewAll" var="viewMatchCommentsUrl">
                      <s:param name="idMatch">${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.id}</s:param>
                    </s:url>

                    <display:column title="${titleDay}" style="width:185px;${rowCount==itemListsize ? 'border:none;' : ''} " class="${currentUserRegisteredClass}">
                      <a  target="blank" href="${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.googleMapUrl}"  >
                        ${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.sportCenterName}
                      </a>
                    </display:column>

                    <display:column title="${(guiCalendarDayInfoNotRecordedListItem_rowNum == 1) ? titleOra : ''}" style="width:40px;${rowCount==itemListsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass}">
                      ${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.matchStartHour}
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoNotRecordedListItem_rowNum == 1) ? titlePr : ''}" style="width:25px;${rowCount==itemListsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass}">
                      ${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.sportCenterProvinceAbbreviation}
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoNotRecordedListItem_rowNum == 1) ? titleTipo : ''}" style="width:40px;${rowCount==itemListsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass} lblMatch">
                      <u:translation key="label.partita"/>
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoNotRecordedListItem_rowNum == 1) ? titleA : ''}" style="width:25px;${rowCount==itemListsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass} lblMatch">
                      ${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.matchTypeTotPlayers}
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoNotRecordedListItem_rowNum == 1) ? titleOrganizzatore : ''}" style="width:190px;${rowCount==itemListsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass} lblMatch">
                      <u:printUserName userInfo="${guiCalendarDayInfoNotRecordedListItemMatch.userOwnerInfo}" linkToDetails="true" showAvatar="false" showCurrentUserDetails="true" />
                    </display:column>
                    <display:column title="" style="${rowCount==itemListsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass} ratings">
                      <table>
                        <tr>
                          <td>
                            <a href="${archiveMatchUrl}" ><img src="<s:url value="/images/att-mat.gif" encode="false" />" title="<u:translation key="label.archiveMatchInAttesa"/> ${guiCalendarDayInfoNotRecordedListItemMatch.matchInfo.reporter}" /></a>
                          </td>
                          <td>&nbsp;</td>
                          <td>
                            <s:if test="#hasComments == 'true'">
                              <a href="${viewMatchCommentsUrl}#startComments">
                                <s:if test="#hasNewComments == 'true'">
                                  <img src="<s:url value="/images/comment3.gif" encode="false" />" title="<u:translation key="label.postPresent"/>" />
                                </s:if>
                                <s:else>
                                  <img src="<s:url value="/images/comment3_fade.gif" encode="false" />" title="<u:translation key="label.NoPost"/>" />
                                </s:else>
                              </a>
                            </s:if>
                          </td>
                        </tr>
                      </table>
                    </display:column>
                  </display:table>

                </display:column>
              </display:table>

            </s:if>

            <s:if test="preformedSearch == false">
              <s:set name="dtRequestURI">calendar.action</s:set>
            </s:if>
            <s:else>
              <s:set name="dtRequestURI">searchMatch.action</s:set>
              <!--script type="text/javascript">
                displayMatchSearchBox('idSearchBox', 'idSearchBoxHide', 'idSearchBoxDisplay');
              </script-->
            </s:else>
            <s:if test="guiCalendarInfoDayList.size <= 0">
              <strong><u:translation key="message.calendarNoPartite"/></strong>
            </s:if>
            <s:else>
              
              <c:set var="pageSize" value="14" />
              <display:table name="guiCalendarDayInfoList" id="guiCalendarDayInfoListItem" class="fatherTable nextEvent" pagesize="${pageSize}" requestURI="calendar!viewCalendar.action" >
                <u:displayTablePaginator />
                <%--u:displayTablePaginator /--%>
                <display:column>
                  <s:set name="listsize">${guiCalendarDayInfoListItem.listSize}</s:set>
                  <s:set name="rCount" value="0"></s:set>

                  <display:table name="${guiCalendarDayInfoListItem.guiCalendarInfoList}" id="guiCalendarDayInfoListItemMatch" >
                    <u:displayTablePaginator />
                    <%-- Inizio label intestazione tabela singolo giorno --%>
                    <s:set name="rCount"  value="#rCount =#rCount + 1" />
                    <s:set name="labelPr"><u:translation key="label.ProvinciaAbbreviata" /></s:set>
                    <s:set name="labelOra"><u:translation key="label.Ora" /></s:set>
                    <s:set name="titleIscrizioni"><u:translation key="label.calendar.Iscrizioni" /></s:set>

                    <%-- Fine label intestazione tabela singolo giorno --%>
                    <s:set name="titleA"><u:translation key="label.a_"/></s:set>
                    <s:set name="titleOrganizzatore"><u:translation key="label.Organizzatore"/></s:set>
                    <s:set name="idUserOwner">${guiCalendarDayInfoListItemMatch.matchInfo.idUserOwner}</s:set>
                    <s:set name="recordedMatchesUserOwner">${guiCalendarDayInfoListItemMatch.matchInfo.recordedMatchesUserOwner}</s:set>
                    <s:set name="recordedChallengesUserOwner">${guiCalendarDayInfoListItemMatch.matchInfo.recordedChallengesUserOwner}</s:set>
                    <s:set name="currentUserOwner">${guiCalendarDayInfoListItemMatch.currentUserOwner}</s:set>
                    <s:set name="currentUserFriendOwner">${guiCalendarDayInfoListItemMatch.currentUserFriendOwner}</s:set>
                    <s:set name="currentUserNotFriendOwner">${guiCalendarDayInfoListItemMatch.currentUserNotFriendOwner}</s:set>
                    <s:set name="currentUserRegistered">${guiCalendarDayInfoListItemMatch.currentUserRegistered}</s:set>
                    <s:set name="currentUserRequest">${guiCalendarDayInfoListItemMatch.currentUserRequest}</s:set>
                    <s:set name="registrationsOpen">${guiCalendarDayInfoListItemMatch.registrationsOpen}</s:set>
                    <s:set name="openInFuture">${guiCalendarInfo.registrationsOpenInFuture}</s:set>
                    <s:set name="missingDays">${guiCalendarDayInfoListItemMatch.missingDays}</s:set>
                    <s:set name="missingHours">${guiCalendarDayInfoListItemMatch.missingHours}</s:set>
                    <s:set name="missingMinutes">${guiCalendarDayInfoListItemMatch.missingMinutes}</s:set>
                    <s:set name="hasComments">${guiCalendarDayInfoListItemMatch.hasComments}</s:set>
                    <s:set name="hasNewComments">${guiCalendarDayInfoListItemMatch.commentsToRead}</s:set>
                    <s:set name="missingPlayers">${guiCalendarDayInfoListItemMatch.missingPlayers}</s:set>
                    <s:set name="titleTipo"><u:translation key="label.Tipo"/></s:set>
                    <s:if test="#currentUserRegistered == 'true'">
                      <s:set name="currentUserRegisteredClass">calendarHighlightedLine</s:set>
                    </s:if>
                    <s:elseif test="#currentUserRequest== 'true'">
                      <s:set name="currentUserRegisteredClass">calendarHighlightedLine2</s:set>
                    </s:elseif>
                    <s:else>
                      <s:set name="currentUserRegisteredClass"></s:set>
                    </s:else>
                    <%--s:if test="#currentUserFriendOwner == 'true'">
                      <s:set name="userOwnerClass">calendarFriendMatch</s:set>
                    </s:if--%>
                    <s:elseif test="#currentUserNotFriendOwner == 'true'">
                      <s:set name="userOwnerClass">calendarNotFriendMatch</s:set>
                    </s:elseif>
                    <s:else>
                      <s:set name="userOwnerClass"></s:set>
                    </s:else>
                    <s:url action="matchComments!viewAll" var="viewMatchUrl">
                      <s:param name="idMatch">${guiCalendarDayInfoListItemMatch.matchInfo.id}</s:param>
                    </s:url>
                    <s:url action="organizeMatchReview!input" var="organizeMatchReviewUrl">
                      <s:param name="idMatch">${guiCalendarDayInfoListItemMatch.matchInfo.id}</s:param>
                    </s:url>
                    <s:url action="matchComments!viewAll" var="viewMatchCommentsUrl">
                      <s:param name="idMatch">${guiCalendarDayInfoListItemMatch.matchInfo.id}</s:param>
                    </s:url>
                    
                    <display:column  title="${guiCalendarDayInfoListItem.correspondingDate}" style="width:185px;${rCount==listsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass}" headerClass="nextEvent">
                      <!--${rCount}- ${listsize}-->
                      <a  target="blank" href="${guiCalendarDayInfoListItemMatch.matchInfo.googleMapUrl}"  >
                        ${guiCalendarDayInfoListItemMatch.matchInfo.sportCenterName}
                      </a>
                    </display:column>
                    
                    <display:column   title="${(guiCalendarDayInfoListItem_rowNum mod pageSize eq 1) ? labelOra : ''}" style="width:40px;${rCount==listsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass}" headerClass="nextEvent nextEventTitles">
                      <utl:formatDate date="${guiCalendarDayInfoListItemMatch.matchInfo.matchStart}" formatKey="format.date_5" />
                    </display:column>
                    
                    <display:column title="${(guiCalendarDayInfoListItem_rowNum mod pageSize eq 1) ? labelPr : ''}" style="width:25px ;${rCount==listsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass}" headerClass="nextEvent nextEventTitles">
                      ${guiCalendarDayInfoListItemMatch.matchInfo.sportCenterProvinceAbbreviation}
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoListItem_rowNum mod pageSize eq 1) ? titleTipo : ''}" style="width:40px;${rCount==listsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass} lblMatch" headerClass="nextEvent nextEventTitles">
                      <u:translation key="label.Partita"/>
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoListItem_rowNum mod pageSize eq 1) ? titleA : ''}" style="width:25px;${rCount==listsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass}" headerClass="nextEvent nextEventTitles">
                      ${guiCalendarDayInfoListItemMatch.matchInfo.matchTypeTotPlayers}
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoListItem_rowNum mod pageSize eq 1) ? titleOrganizzatore : ''}" style="width:190px;${rCount==listsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass}" headerClass="nextEvent nextEventTitles">
                      <span class="${userOwnerClass}">
                        <u:printUserName userInfo="${guiCalendarDayInfoListItemMatch.userOwnerInfo}" linkToDetails="true" showAvatar="false" showCurrentUserDetails="true" />
                      </span>
                    </display:column>
                    <display:column title="${(guiCalendarDayInfoListItem_rowNum mod pageSize eq 1) ? titleIscrizioni : ''}" style="${rCount==listsize ? 'border:none;' : ''}" class="${currentUserRegisteredClass} ratings" headerClass="nextEvent nextEventTitles ">
                      
                      <s:set name="canceled">${guiCalendarDayInfoListItemMatch.canceled}</s:set>
                      <table>
                            <tr>
                               <td>
                                    <a href="${viewMatchUrl}">
                                      <u:printIconCalendarMatch guiCalendarInfo='${guiCalendarDayInfoListItemMatch}'/>
                                    </a>
                              </td>
                              <td>
                                <s:if test="#missingPlayers > 0 && #canceled=='false'">
                                  <small class="${(guiCalendarDayInfoListItemMatch.registrationsOpenInFuture || guiCalendarDayInfoListItemMatch.matchInfo.registrationClosed) ? 'disable' : '' }">
                                      - ${guiCalendarDayInfoListItemMatch.missingPlayers} 
                                  </small>
                                </s:if>
                              </td>
                              <td>
                                <s:if test="#hasComments == 'true'">
                                  <a href="${viewMatchCommentsUrl}#startComments">
                                      <s:if test="#hasNewComments == 'true'">
                                        <img src="<s:url value="/images/comment3.gif" encode="false" />" title="<u:translation key="label.postPresent"/>"  />
                                      </s:if>
                                      <s:else>
                                        <img src="<s:url value="/images/comment3_fade.gif" encode="false" />" title="<u:translation key="label.NoPost"/>" />
                                      </s:else>
                                  </a>
                                </s:if>
                              </td>
                            </tr>
                      </table>
                       
                    </display:column>
                  </display:table>

                </display:column>
              </display:table>

            </s:else>
            
                      
                      <div class="cntLblFill">
                          <u:translation key="label.calendarFill"/>
                      </div>
        

        </div>
        <!--### end mainContent ###-->

      </div>
      <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->
      <jsp:include page="../jspinc/rightcolumn.jsp" flush="true" />
      <!--### end rightcolumn ###-->

      <!--### start footer ###-->
      <jsp:include page="../jspinc/footer.jsp" flush="true" />
      <!--### end footer ###-->

    </div>

  </body>
</html>
