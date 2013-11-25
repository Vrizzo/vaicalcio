<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all" />
    <title><s:property value="%{getText('page.title.default')}" /></title>

    <script type="text/javascript">

      var maxPlayersToAdd = ${(team != null? 1000 : playersToAdd) };

      var selectPlayersToAddArray = new Array (maxPlayersToAdd);


      function checkPlayersToaddroles()
      {
        $$('.playersRoleErrorsContainer').each(function(c){c.hide();})
        $$('.playersNameErrorsContainer').each(function(c){c.hide();})
        if ($('idOutPlayersSelect').value > 0)
        {
          for(i=0; i < ($('idOutPlayersSelect').value); i++ )
          {
            var ctrl = "<u:translation key='label.esterno'/>" + " " + [i+1];
            var nameOut = $$('.selectNamePlayers')[i].value;
            nameOut=nameOut.strip();
                
            if (ctrl==nameOut)
            {
              $('idSpanErrorName').show();
              $$('.selectNamePlayers')[i].value="";
              return;
            }
             
            if ($$('.selectNamePlayers')[i].value=='')
            {
              $('idSpanErrorName').show();
              return;
            }
            if ($$('.selectRolePlayers')[i].value==0)
            {
              $('idSpanErrorRole').show();
              return;
            }
                
          }
          $('idRegisterFriendForm').submit();
        }
        else
          $('idRegisterFriendForm').submit();
      }

      function changePlayerToAdd(select)
      {
        var remainingPlayersToAdd = maxPlayersToAdd - calculatePlayersCheckBox();
        if( select.value > remainingPlayersToAdd )
        {
          alert("<u:translation key='error.registerFriendsSelezioneMax'/> " + remainingPlayersToAdd + " " +  (remainingPlayersToAdd > 1 ? "<u:translation key='label.giocatori'/>" : "<u:translation key='label.giocatore'/>") );
          select.options[remainingPlayersToAdd].selected = true;
          changePlayerToAdd(select);
          return;
        }
        $$('.playersToAddContainer').each(function(c){c.hide();})
        for(i=0; i < select.value; i++ )
        {
          $('jsIdPlayersToAddName_' + i).show();

        }
        $$('.playersRoleToAddContainer').each(function(c){c.hide();})
        for(i=0; i < select.value; i++ )
        {
          $('jsIdPlayersRoleToAdd_' + i).show();

        }
      }

      function calculatePlayersCheckBox()
      {
        var currentPlayersAdded = 0;
        $$('.playersToAddCheckBox').each(function(c){
          if(c.checked)
            currentPlayersAdded++;
        });
        return currentPlayersAdded;
      }

      function calculatePlayersSelect()
      {
        var currentPlayersAdded = 0;
        $$('.playersToAddContainer').each(function(c)
        {
          if(c.visible())
            currentPlayersAdded++;
        });
        return currentPlayersAdded;
      }

      function calculateRemainingPlayersAll()
      {
        return maxPlayersToAdd - (calculatePlayersSelect() + calculatePlayersCheckBox());
      }

      function checkRemainingPlayers(currentCheckBox)
      {
        if( currentCheckBox.checked == false )
          return;
        var remainingPlayersToAdd = calculateRemainingPlayersAll();
        if( remainingPlayersToAdd < 0 )
        {
          currentCheckBox.checked = false;
          alert("<u:translation key='error.registerFriendsTroppiGiocatoriSel'/>");
        }
      }

      function clearOuterName(name)
      {
        var nome = name.value;
        var cerca = '<u:translation key="label.esterno"/>';
          
        if(nome.match(cerca)!=null)
        {
          name.value='';
        }
      }
    </script>

    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class="registerFriendToMatch">


    <s:if test="friendRegistered > 0"   >
      <script type="text/javascript">
        window.opener.location.reload(true);
        window.close();
      </script>
    </s:if>
    <s:elseif test="friendRegistered == -1"   >
      <script type="text/javascript">
        window.opener.location.reload(true);
        window.close();
      </script>
    </s:elseif>

    <s:else>

      <h1><u:translation key="label.iscriviGokickers" /></h1>
      <div class="roseCont">
        <s:form id="idRegisterFriendForm"  name="registerFriendForm"  action="registerFriendToMatch"  method="post"   >
          <s:hidden name="idMatch"></s:hidden>
          <s:hidden name="playersToAdd"></s:hidden>
          <s:hidden name="playedMatch"></s:hidden>
          <s:hidden name="team"></s:hidden>

          <!-- friends-->
          <display:table name="guiPlayerInfoList" id="guiPlayer" requestURI="registerFriendToMatch.action?noValidate=true" class="friendList maximized" >
            <u:displayTablePaginator />
            <s:set name="idUser">${guiPlayer.userInfo.id}</s:set>
            <s:set name="isCurrentUser">${guiPlayer.currentUser}</s:set>
            <s:set name="titleAmici"><u:translation key="label._Amici"/></s:set>
            <s:set name="titleNazionalita"><u:translation key="label.NazionalitaIniziale"/></s:set>
            <s:set name="titleRuolo"><u:translation key="label.roleAbbr"/></s:set>
            <s:set name="titleEta"><u:translation key="label.Eta"/></s:set>
            <s:set name="titleCondizione"><u:translation key="label.condizioneFisicaAbbr"/></s:set>

            <display:column title="" sortable="false" class="${isCurrentUser?'currentUser':''} first" headerClass="first">
              <s:set name="playerStatus">${guiPlayer.status}</s:set>
              <s:set name="playerType">${guiPlayer.type}</s:set>
              <s:if test="#playerStatus=='Undefined' || #playerStatus=='UserRequest' || #playerType=='Missing' || #playerStatus=='UserCalled'">
                <s:checkbox id="userCheckList" name="userCheckList" fieldValue="%{#idUser}"
                            onclick="checkRemainingPlayers(this);"
                            cssClass="playersToAddCheckBox"/>
                <s:checkbox id="userCheckList" name="userCheckList" cssStyle="display:none;"/>
                <!-- aggiunta altrimenti restiuisce false per la userCheckList
                (caso con una sola chk selezionabile) e va in errore se si aggiungono solo esterni -->
              </s:if>
            </display:column>

            <display:column title="${titleAmici}" sortable="true"  sortProperty="userInfo.name" class="${isCurrentUser?'currentUser':''}">
              <s:set name="guiPlayerId">${guiPlayer.userInfo.id}</s:set>
              <s:set name="guiPlayerRecordedMatches">${guiPlayer.userInfo.recordedMatches}</s:set>
              <s:set name="guiPlayerRecordedChallenges">${guiPlayer.userInfo.recordedChallenges}</s:set>
              <s:url action="viewAvatar" var="imageUrl">
                <s:param name="idUser">
                  ${guiPlayer.userInfo.id}
                </s:param>
              </s:url>
              <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false" showCurrentUserDetails="true" linkToDetails="true" />
            </display:column>

            <display:column title="${titleNazionalita}" sortable="true" class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:28px;">
              <img src="images/flags/${guiPlayer.userInfo.idNatCountry}.png" onError="this.src='images/country_flag_0.gif';" />
            </display:column>

            <display:column title="${titleRuolo}" sortable="true" sortProperty="userInfo.idPlayerRole"  class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:20px;">
              <s:set name="ruoloLang" ><u:translation key="${guiPlayer.userInfo.playerRoleKey}"/></s:set>
              ${fn:substring(ruoloLang, 0, 1)}
            </display:column>

            <display:column title="${titleEta}" sortable="true" class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:28px;">
              ${guiPlayer.userInfo.age}
            </display:column>

            <display:column title="${titleCondizione}" sortable="true" class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:30px;">
              <u:printUserPhysicalCondition userToShow="${guiPlayer.userInfo}"/>
            </display:column>

          </display:table>
          <!-- friends-->

          <s:if test="playedMatch && guiPlayerRequestInfoList!=null">
            <s:set value="guiPlayerRequestInfoList.size" id="listSize"/>
            <!-- Fuori Rosa -->
            <display:table name="guiPlayerRequestInfoList" id="guiPlayer" requestURI="registerFriendToMatch.action" class="friendList">
              <u:displayTablePaginator />
              <s:set name="idUser">${guiPlayer.userInfo.id}</s:set>
              <s:set name="isCurrentUser">${guiPlayer.currentUser}</s:set>
              <s:set name="titleRichieste">${listSize} <u:translation key="label.matchRichiesteGioco"/></s:set>

              <display:column title="" sortable="false" class="${isCurrentUser?'currentUser':''} first" headerClass="first">
                <s:set name="playerStatus">${guiPlayer.status}</s:set>
                <s:set name="playerType">${guiPlayer.type}</s:set>
                <s:if test="#playerStatus=='UserRequest' || #playerType=='Missing' || #playerStatus=='UserCalled'">
                  <s:checkbox id="userRequestCheckList" name="userRequestCheckList" fieldValue="%{#idUser}"
                              onclick="checkRemainingPlayers(this);"
                              cssClass="playersToAddCheckBox"/>
                  <s:checkbox id="userRequestCheckList" name="userRequestCheckList" cssStyle="display:none;"/>
                  <!-- aggiunta altrimenti restiuisce false per la userCheckList
                  (caso con una sola chk selezionabile) e va in errore se si aggiungono solo esterni -->
                </s:if>
              </display:column>

              <display:column title="${titleRichieste}" sortable="true" sortProperty="userInfo.name" class="${isCurrentUser?'currentUser':''}">
                <s:set name="guiPlayerId">${guiPlayer.userInfo.id}</s:set>
                <s:set name="guiPlayerRecordedMatches">${guiPlayer.userInfo.recordedMatches}</s:set>
                <s:set name="guiPlayerRecordedChallenges">${guiPlayer.userInfo.recordedChallenges}</s:set>
                <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false" showCurrentUserDetails="true" linkToDetails="true" />
              </display:column>

              <display:column title="${titleNazionalita}" sortable="true" class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:28px;">
                <img src="images/flags/${guiPlayer.userInfo.idNatCountry}.png" alt="" onError="this.src='images/country_flag_0.gif';"/>
              </display:column>

              <display:column title="${titleRuolo}" sortable="true" sortProperty="userInfo.idPlayerRole" class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:20px;">
                <s:set name="ruoloLang" ><u:translation key="${guiPlayer.userInfo.playerRoleKey}"/></s:set>
                ${fn:substring(ruoloLang, 0, 1)}
              </display:column>

              <display:column title="${titleEta}" sortable="true" class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:28px;">
                ${guiPlayer.userInfo.age}
              </display:column>

              <display:column title="${titleCondizione}" sortable="true" class="${isCurrentUser?'currentUser centred':'centred'}" headerClass="centred" style="width:30px;">
                <u:printUserPhysicalCondition userToShow="${guiPlayer.userInfo}" />
              </display:column>

            </display:table>
            <!-- Fuori Rosa -->
          </s:if>

          <h2><u:translation key="label.Esterni" /> <small>(<u:translation key="label.registerFriendMatchInfoEsterni" />)</small></h2>

          <table class="extGokickersList">
            <tr>
              <td colspan="2">
                <u:translation key="label.quanti" />? :
                <s:select
                  name="selectedNumberPlayersToAdd"
                  list="playersToAddList"
                  headerKey="0"
                  headerValue="-"
                  id="idOutPlayersSelect"
                  onchange="changePlayerToAdd(this);"/>
              </td>
            </tr>

            <tr>
              <td>               
                <s:iterator value="playersToAddNameList" var="playersToAddName"  status="stat">
                  <div id="jsIdPlayersToAddName_<s:property value="%{#stat.count-1}" />" class="playersToAddContainer" style="display:none;" >
                    <%--esterno <s:property value="%{#stat.count}" />:--%>
                    <p><s:textfield  onclick="clearOuterName(this);"    name="playersToAddNameList[%{#stat.count-1}]" maxLength="25" value="%{getText('label.esterno')} %{#stat.count} " cssClass="selectNamePlayers"/></p>
                  </div>
                </s:iterator>
              </td>
              <td>
                <s:iterator value="playersRoleToAddList" var="playersRoleToAdd"  status="stat">
                  <div id="jsIdPlayersRoleToAdd_<s:property value="%{#stat.count-1}" />" class="playersRoleToAddContainer" style="display:none;" >
                    <p>
                      <s:select
                        cssClass="selectRolePlayers"
                        id="userRole"
                        emptyOption="false"
                        headerValue="%{getText('label.role')}"
                        headerKey=""
                        name="playersRoleToAddList[%{#stat.count-1}]"
                        list="#{'1':getText('label.portiere'),'2':getText('label.difensore'),
                                '3':getText('label.centrocampista'),'4':getText('label.attaccante')
                        }"
                        />
                    </p>
                  </div>
                </s:iterator>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <span id="idSpanErrorRole" style="display :none;" class="playersRoleErrorsContainer">
                  <br/>
                  <font color="red">
                    <s:property value="%{getText('error.role.required')}" />
                  </font>
                </span>
                <span id="idSpanErrorName" style="display :none;" class="playersNameErrorsContainer">
                  <br/>
                  <font color="red">
                    <s:property value="%{getText('error.nomeCognome.required')}" />
                  </font>
                </span>
              </td>
            </tr>
            <%--<tr>
              <td>
                <span id="idSpanErrorRole_<s:property value="%{#stat.count-1}" />" style="display :none;" class="playersRoleErrorsContainer">
                      <br/>
                        <font color="red">
                          <s:property value="%{getText('error.role.required')}" />
                        </font>
                      </span>
                      <span id="idSpanErrorName_<s:property value="%{#stat.count-1}" />" style="display :none;" class="playersNameErrorsContainer">
                      <br/>
                        <font color="red">
                          <s:property value="%{getText('error.nomeCognome.required')}" />
                        </font>
                      </span>
              </td>
            </tr>--%>
            <tr>
              <td colspan="2">
                <s:actionerror /><br />
                <s:set value="%{getText('label.iscriviGokickers')}" var="btnLabel" />

                <p>
                  <%--<s:submit  onclick="checkPlayersToaddroles();" cssClass="btn action" value="%{getText('label.iscriviGokickers')}" ></s:submit>--%>
                  <input type="button" onclick="checkPlayersToaddroles();" value="<u:translation key="label.iscrivi"/>" class="btn action" />
                </p>
              </td>
            </tr>
          </table>

        </s:form>
      </s:else>

    </div>

  </body>
</html>
