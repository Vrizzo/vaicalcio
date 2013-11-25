<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
  <title><s:property value="%{getText('page.title.default')}"/></title>
  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
  <!--### end Google Analitics inclusion ###-->
</head>
<body>

<div class="wrapperHP">

<!--### start header ###-->
<jsp:include page="../jspinc/header.jsp" flush="true"/>
<!--### end header ###-->

<!--### start leftcolumn ###-->
<jsp:include page="../jspinc/leftColumn.jsp" flush="true">
  <jsp:param name="tab" value="home"/>
</jsp:include>
<!--### end leftcolumn ###-->

<!--### start centralcolumn ###-->
<div class="centralCol">
<div class="topPageWrap">
  <jsp:include page="../jspinc/headerTopBar.jsp" flush="true"/>
</div>


<!--### start mainContent ###-->
<div class="mainContent mainHP">

  <div class="messageArea">
    <s:set name="mess"><u:translation key="message.homeManutenzione"/></s:set>
    <s:if test="#mess!=''">
      <p><u:translation key="message.homeManutenzione"/></p>
    </s:if>
  </div>

  <div class="contHPlogged">

    <div class="leftHP">
      <h2><u:translation key="label.homeLeftBoxTitle"/></h2>
      <u:customContent position="center-slider" width="285" height="209"/>
    </div>

    <div class="rightHP">
      <h2>
        <u:printUserName userInfo="${userInfo}" linkToDetails="true" showCurrentUserDetails="true"/>
      </h2>

      <s:url action="viewAvatar" var="imageUrl">
        <s:param name="idUser">
          ${userInfo.id}
        </s:param>
      </s:url>
      <a href="<s:url action="userPlayer!input"/>"><img class="avatar" src="${imageUrl}" alt=""/></a>

      <p>
        <a href="<s:url action="userPlayer!input"/>"><u:translation key="${userInfo.playerRoleKey}"/><s:if test="userInfo.age > 0">&nbsp;${userInfo.age}&nbsp;<u:translation key="label.udmEta"/></s:if></a>
        <u:printUserPhysicalCondition userToShow="${userInfo}" description='false'/>
        <a href="<s:url action="userAccount!input"/>">
          <u:translation key="label.homeGiochiA"/>&nbsp;${userInfo.city}
        </a>
      </p>

      <table class="maximized">
        <tr>
          <td class="subLine">
            <u:translation key="label.homePuoiGiocare"/>
          </td>
          <td>
            <s:url action="calendar!viewCalendar" var="calendarUrl">
              <s:param name="defaultSearch">true</s:param>
            </s:url>
            <a href="${calendarUrl}">
              ${allPlayableMatchNumber}&nbsp;<u:translation key="label._Partite"/></a>
            [<a href="javascript: void(0);" onclick="openPopupHelp('M008')">?</a>]
          </td>
        </tr>
        <tr>
          <td class="subLine">
            <s:url action="matchComments!viewAll" var="viewMatchUrl">
              <s:param name="idMatch">${calendarInfo.matchInfo.id}</s:param>
            </s:url>
            <s:set name="missingPlayers">${calendarInfo.missingPlayers}</s:set>
            <u:translation key="label.homeGiochiProx"/>
          </td>
          <td>
            <s:if test="calendarInfo.matchInfo.id > 0">
              <a href="${viewMatchUrl}">${nextMatchDate}</a>
              <a href="${viewMatchUrl}"><u:printIconCalendarMatch guiCalendarInfo='${calendarInfo}'/></a>
              <s:if test="#missingPlayers > 0">
                <small class="${(calendarInfo.registrationsOpenInFuture || calendarInfo.matchInfo.registrationClosed) ? 'disable' : '' }"> - ${calendarInfo.missingPlayers}</small>
              </s:if>
            </s:if>
            <s:else>
              <u:translation key="label.homeNoIscrizione"/>
            </s:else>
          </td>
        </tr>
        <tr>
          <td class="subLine">
            <u:translation key="label.homeLastResult"/>
          </td>
          <td>
            <s:if test="calendarResultInfo.matchInfo.id > 0">
              <s:url action="archiveMatch!report" var="viewReportUrl">
                <s:param name="idMatch">${calendarResultInfo.matchInfo.id}</s:param>
              </s:url>
              <a href="${viewReportUrl}">${calendarResultInfo.currentUserResultText}&nbsp;${calendarResultInfo.matchInfo.resultGoal}</a>

              <a href="${viewReportUrl}"><u:printIconReportCalendarMatch guiCalendarInfo="${calendarResultInfo}"/></a>
            </s:if>
            <s:else>
              <u:translation key="label.homeMaiGiocato"/>
            </s:else>
          </td>
        </tr>
        <tr>
          <td class="subLine"><u:translation key="label.resultsHaiGiocato_"/></td>
          <td>
            <a href="<s:url action="results!viewPlayedMatch"/>">${allPlayedNumber}&nbsp;<u:translation key="label._Partite"/></a>
          </td>
        </tr>
        <tr>
          <td class="subLine">
            <s:url action="matchComments!viewAll" var="viewMatchOrgUrl">
              <s:param name="idMatch">${calendarOrganizingInfo.matchInfo.id}</s:param>
            </s:url>
            <s:set name="missingPlayers">${calendarOrganizingInfo.missingPlayers}</s:set>
            <u:translation key="label.homeStaiOrganizzando"/>
          </td>
          <td>
            <s:if test="organizingListSize==1">
              <a href="${viewMatchOrgUrl}">${nextMatchOrganizingDate}</a>
              <a href="${viewMatchOrgUrl}">
                <u:printIconCalendarMatch guiCalendarInfo='${calendarOrganizingInfo}'/>
              </a>
              <s:if test="#missingPlayers > 0">
                <small class="${(calendarOrganizingInfo.registrationsOpenInFuture || calendarOrganizingInfo.matchInfo.registrationClosed) ? 'disable' : '' }">
                  - ${calendarOrganizingInfo.missingPlayers}
                </small>
              </s:if>
            </s:if>
            <s:else>
              <s:url action="calendar!viewOrganizedMatch" var="calendarOrganizeUrl">
                <s:param name="defaultSearch">true</s:param>
              </s:url>
              <a href="${calendarOrganizeUrl}">
                  ${organizingListSize}&nbsp;<u:translation key="label._Partite"/>
              </a>
            </s:else>
          </td>
        </tr>
        <tr>
          <td class="subLine"><u:translation key="label.homeHaiRicevuto"/></td>
          <td>
            <s:url action="squad" var="squadUrl">
              <s:param name="boxFriendRequestsVisible">true</s:param>
            </s:url>
            <a href="${squadUrl}">
              ${friendRequestsReceivedList}&nbsp;<u:translation key="label.homeRichiesteAmicizia"/>
            </a>
          </td>
        </tr>
        <tr>
          <td class="subLine"><u:translation key="label.homeSei"/></td>
          <td>

            <s:if test="userInfo.marketEnabled">
              <a href="<s:url action="userPlayer!input"/>"><s:property value="%{getText('label.Sul_Mercato')}"/></a> <img src="<s:url value="/images/gioca.gif"/>" alt=""/>
            </s:if>
            <s:else>
              <a href="<s:url action="userPlayer!input"/>"><s:property value="%{getText('label.nonSulMercato')}"/></a>
            </s:else>
            [<a class="last" href="javascript: void(0);" onclick="openPopupHelp('U007')">?</a>]
          </td>
        </tr>
      </table>
    </div>
    <div class="clear">&nbsp;</div>
    <h2><u:translation key="label.homeBenvenuti"/></h2>
    <p class="clearfix usersStripe">
      <s:iterator value="userInfoWithPic" id="userInfoPic">
        <s:url action="viewAvatar" var="imageUrl">
          <s:param name="idUser">
            ${userInfoPic.id}
          </s:param>
        </s:url>

        <s:url action="userDetails" var="userDetailsURL">
          <s:param name="idUser">${userInfoPic.id}</s:param>
          <s:param name="tab">scheda</s:param>
        </s:url>
        <s:set name="completeName">${userInfoPic.name}_${userInfoPic.surname}</s:set>
        <s:set name="nameDet">
          <s:property value="#completeName" escapeJavaScript="true"/>
        </s:set>
        <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');">
          <img src="${imageUrl}" alt=""/>
        </a>
      </s:iterator>
    </p>
  </div>
</div>
<!--### end mainContent ###-->
</div>
<!--### end centralcolumn ###-->
<!--### start rightcolumn ###-->
<u:showForCobrandTypes types="Complete">
  <div class="rightColHP">
  <%--<u:translation key="label.homeBoxEsterno" />--%>
  <u:customContent position="right-sidebar" width="250" height="800"/>
  </div>
</u:showForCobrandTypes>
<!--### end rightcolumn ###-->
<!--### start footer ###-->
<jsp:include page="../jspinc/footer.jsp" flush="true"/>
<!--### end footer ###-->
</div>
</body>
</html>
