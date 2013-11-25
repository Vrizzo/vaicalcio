<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<u:loggedIn>
<table>
  <tr>
    <td style="width: 85px; padding-top:0;">
      <u:translation key="label.Organizza"/>
    </td>

    <td style="padding-top:0;">
      <u:printUserName userInfo="${guiMatchInfo.ownerUserInfo}" linkToDetails="true" showAvatar="false"/>
      <br/>
      <s:if test="guiMatchInfo.matchInfo.mobileUserOwner!=''">
        <small><u:translation key="label.cell"/>: <s:property value="guiMatchInfo.matchInfo.mobileUserOwner"/></small>
      </s:if>
      <s:else>
        <small><u:translation key="label.match.cellNonCondiviso"/></small>
      </s:else>
    </td>
  </tr>
  <tr>
    <td>
      <u:translation key="label.match"/> [<a href="javascript: void(0);" onclick="openPopupHelp('M001')">?</a>]
    </td>
    <td>
      <s:if test="squadOutEnable">
        <u:translation key="label.match.publicDirectRegistration"/>
        <small>
          <s:if test="maxSquadOutPlayers > 0">
            <%-- rimuovere commento in db (label.match.publicMaxSquadOut) per riabilitare info : n° fuori rosa ammessi --%>
            <u:translationArgs key="label.match.publicMaxSquadOut" arg01="${maxSquadOutPlayers}"/>
          </s:if>
          <s:else>
            <u:translationArgs key="label.matchInfoValutazione"/>
          </s:else>
        </small>
      </s:if>
      <s:else>
        <u:translation key="label.match.private"/>
      </s:else>
    </td>
  </tr>
  <tr>
    <td>
      <u:translation key="label.disdette"/>
    </td>
    <td>
      <s:if test="acceptTermination">
        <u:translation key="label.entro"/>&nbsp;${acceptTerminationTime}
      </s:if>
      <s:else>
        <s:if test="guiMatchInfo.ownerUser">
          <u:translation key="label.match.disdetteContattandoti"/>
        </s:if>
        <s:else>
          <u:translation key="label.match.disdetteContattando"/>
        </s:else>
      </s:else>
    </td>
  </tr>
  <tr>
    <td>
      <u:translation key="label.Pagelle"/> [<a href="javascript: void(0);" onclick="openPopupHelp('P001')">?</a>]
    </td>

    <td>
      <s:if test="guiMatchInfo.ownerUser && !guiMatchInfo.matchInfo.canceled">
        <s:select
                name="idPlayerReporter"
                value="idPlayerReporter"
                list="playersRegisteredList"
                listKey="id"
                listValue="userInfo.name + ' ' + userInfo.surname "
                id="idSelectPlayerReporter"
                cssStyle="width:150px;"
                onchange="setReporter($('idSelectPlayerReporter').value );"

                />
        <%--<a id="btnSetReporter"onclick="setReporter($('idSelectPlayerReporter').value );" href="javascript: void(0);"><strong>ok</strong></a>--%>
      </s:if>
      <s:else>
        <u:printUserName userInfo="${userInfoReporter}" linkToDetails="true" showAvatar="false"/>
      </s:else>
    </td>
  </tr>
</table>
</u:loggedIn>



                  





