<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <script type="text/javascript">
      function submitForm(validate)
      {
        $('idValidate').value=validate;
        $('idArchiveForm').submit();
      }
          
      function archive(validate)
      {
        $('idRecordedField').value = true;
        submitForm(validate);
      }
         
      function playerAction(idPlayer, actionCode)
      {
        $('idPlayerField').value = idPlayer;
        $('actionCodeField').value = actionCode;
            
        submitForm();
      }

      function removeRegisteredPlayer(idPlayerToRemove,idMatch,missing)
      {
        new Ajax.Updater('playersRegisteredListContainer', '<s:url action="matchRegisteredPlayers" namespace="/ajax" />',
        {
          onComplete:initDataNoRegisteredList,
          parameters:
            {
            idMatch: idMatch,
            idPlayerToRemove:idPlayerToRemove,
            missing: missing,
            dummy: Math.random()
          }
        });
      }
      
      function removeRegisteredPlayer(idPlayer,actionCode,missing)
      {

        $('idPlayerField').value = idPlayer;
        $('actionCodeField').value = 'remove';
        $('idMissingField').value = missing;
        submitForm();
      }
      
      function removeOutPlayer(idPlayer, actionCode)
      {
        if (confirm("<u:translation key='message.archiveMatchRimuoviOut'/>"))
        {
          $('idPlayerField').value = idPlayer;
          $('actionCodeField').value = actionCode;
              
          submitForm();
        }
      }

      function clearTeamName(name)
      {
        if (name.value=='<u:translation key="label.nomeDefaultSquadraUno"/>' || name.value=='<u:translation key="label.nomeDefaultSquadraDue"/>' )
        {
          name.value='';
        }
      }

/*   jQuery ModalBox*/

      /* <![CDATA[ */
      function directCall_Data(mode){
        var iFrameUrl ='<iframe frameborder="no" height="600" width="600" src="registerToMatch.action?idMatch=${idMatch}&team=WHAT_TEAM" ></iframe>';
        if(mode=='teamOne')
          iFrameUrl = iFrameUrl.replace("WHAT_TEAM", "TEAM_ONE");
        else if(mode=='teamTwo')
          iFrameUrl = iFrameUrl.replace("WHAT_TEAM", "TEAM_TWO");
        
        JQ.fn.modalBox(
        {
          /*callFunctionBeforeShow : alert('before'),*/
          usejqueryuidragable : false,
          killModalboxWithCloseButtonOnly : true,
          directCall : {data : iFrameUrl},
          positionTop: 230
        });
      }

      JQ(document).ready(function(){
        JQ("a.directCallDataIscriveOne").click(function(){
          directCall_Data('teamOne');
        });
        JQ("a.directCallDataIscriveTwo").click(function(){
          directCall_Data('teamTwo');
        });
      });




      /*JQ(document).ready(function(){
        JQ("a.directCallContactRegistered").click(function(){
          directCall_Data('contact');
        });
      });*/
      /* ]]> */

/*  jQuery ModalBox-->*/





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
        <div class="mainContentIndent">

          <h1><u:translation key="label.match"/></h1>

          <div class="contTab" >
            <p class="left" style="width:500px;">
              <utl:formatDate   date="${currentMatch.matchStart}" formatKey="format.date_7" /> -
              <s:property value="currentMatch.sportCenter.name" />
            </p>
            
          </div>
           <!-- matchsubMenu-->
          <jsp:include page="../jspinc/matchMenu.jsp" flush="true">
            <jsp:param name="activePage" value="archiveMatch"/>
          </jsp:include>
          <!-- matchsubMenu-->
        </div>
          
        <div class="mainContent">
          <s:if test="viewArchivSchema == false">
            <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
            <p align="center">
              ...<u:translation key="label.archiveMatchInAttesa"/> <u:printUserNameBase user="${reporter}" showAvatar="false" displayVertical="false" /> [<a  href="javascript: void(0);" onclick="openPopupHelp('P002')" >?</a>]
            </p>
          </s:if>
          <s:else>
            <s:form action="archiveMatch!archive" method="post" id="idArchiveForm">

              <p>
                <s:actionerror name="archive" />
              </p>
              <s:hidden name="validate" value="false" id="idValidate"/>
              <s:hidden name="idMatch" value="%{idMatch}" />
              <s:hidden name="recorded" value="%{recorded}" id="idRecordedField" />
              <s:hidden name="idPlayer" value="%{idPlayer}" id="idPlayerField" />
              <s:hidden name="actionCode" value="%{actionCode}" id="actionCodeField" />
              <s:hidden name="missing"  id="idMissingField" />

              <div class="teamColumn left">

                <h2><u:translation key="label.squadra"/> 1</h2>
                <div class="teamMainInfo">
                  <table>
                    <s:set name="default" value="%{getText('label.nomeDefaultSquadraUno')}"/>
                    <tr>
                      <td><u:translation key="label.teamName"/>: </td>
                      <td><s:textfield name="teamOneName" maxLength="25" onclick="clearTeamName(this)" /></td>
                      <td><s:select 
                            name="teamOneGoals"
                            list="golNumberList"
                            headerKey=""
                            headerValue="%{getText('label.Goal')}"
                            />
                      </td>
                    </tr>
                    <tr>
                      <td><u:translation key="label.Maglia"/>:</td>
                      <td><s:textfield name="teamOneShirt" maxLength="25" /></td>
                    </tr>
                  </table>
                </div>

                <s:iterator value="teamOnePlayerList" var="teamOnePlayer" status="stat">
                  <s:set value="#stat.count" id="count"/>
                  <s:set value="teamOnePlayerList.size" id="listSize"/>
                  <s:set id="index">${count-1}</s:set>
                  <s:set id="playerStatus">${teamOnePlayer.playerStatus}</s:set>
                  <s:hidden name="teamOnePlayerList[%{index}].id" />
                  <s:hidden name="teamOnePlayerList[%{index}].position" />
                  <s:hidden name="teamOnePlayerList[%{index}].playerType" />
                  <s:if test="#teamOnePlayer.user != null">
                    <s:hidden name="teamOnePlayerList[%{index}].user.id" />
                    <s:hidden name="teamOnePlayerList[%{index}].user.firstName" />
                    <s:hidden name="teamOnePlayerList[%{index}].user.lastName" />
                    <s:hidden name="teamOnePlayerList[%{index}].user.userStatus" />
                    <s:hidden name="teamOnePlayerList[%{index}].user.recordedChallenges" />
                    <s:hidden name="teamOnePlayerList[%{index}].user.recordedMatches" />
                  </s:if>
                  <s:else>
                    <s:hidden name="teamOnePlayerList[%{index}].outFirstName" />
                    <s:hidden name="teamOnePlayerList[%{index}].outLastName" />
                  </s:else>
                  <p class="playerHead">
                    <span class="left">
                      <s:property value="#stat.count" />
                      <s:if test="#teamOnePlayer.user != null">
                        <u:printUserNameBase user="${teamOnePlayer.user}" showAvatar="true" displayVertical="false" />
                      </s:if>
                      <s:else>
                        <u:printSquadOut player="${teamOnePlayer}" showAvatar="true" />
                      </s:else>
                    </span>
                    <span class="right">
                      <s:if test="#index > 0">
                        <a href="javascript: playerAction(${teamOnePlayer.id},'moveup');" title="<u:translation key='label.pagelle.sposta.su'/>"><img src="<s:url value="/images/arrow_up.gif" encode="false" />" alt="<u:translation key='label.pagelle.sposta.su'/>" /></a><br />
                        </s:if>
                        <s:if test="#index <= 0">
                        <br />
                      </s:if>
                      <a href="javascript: playerAction(${teamOnePlayer.id},'moveright');" title="<u:translation key='label.pagelle.sposta'/>"><img src="<s:url value="/images/icon_move_right.gif" encode="false" />" alt="<u:translation key='label.pagelle.sposta'/>" /></a><br />
                        <s:if test="#index < (#listSize -1)">
                        <a href="javascript: playerAction(${teamOnePlayer.id},'movedown');" title="<u:translation key='label.pagelle.sposta.giu'/>"><img src="<s:url value="/images/arrow_down.gif" encode="false" />" alt="<u:translation key='label.pagelle.sposta.giu'/>" /></a>
                        </s:if>
                    </span>
                    <span class="delete right">
                      <s:if test="#playerStatus=='OUT'">
                        <a href="javascript: void(0);" onclick="javascript: removeOutPlayer(${teamOnePlayer.id},'remove');" title="<u:translation key='label.Elimina'/>" class="delete">X</a>
                      </s:if>
                      <s:else>
                        <s:url action="matchRegisteredPlayers" var="openPopUpUrl" namespace="/ajax" method="openPopUp"  >
                          <s:param name="namePlayerToRemove">${teamOnePlayer.user.firstName} ${teamOnePlayer.user.lastName}</s:param>
                          <s:param name="idPlayerToRemove">${teamOnePlayer.id}</s:param>
                          <s:param name="idMatch">${idMatch}</s:param>
                        </s:url>
                        <a onclick="openPopupRemovePlayers('${openPopUpUrl}');" href="javascript: void(0);"  title="<u:translation key='label.Elimina'/>" class="delete">X</a>
                      </s:else>
                    </span>
                  </p>
                  <div class="playerScore">
                    <table>
                      <tr>
                        <td><u:translation key="label.role"/><br /><s:select name="teamOnePlayerList[%{index}].playerRole.id" list="playerRoleInfoList" listKey="id" listValue="shotName" /></td>
                        <td><u:translation key="label.Voto"/><br /><s:select name="teamOnePlayerList[%{index}].vote" list="playerVoteList" listKey="id" listValue="label" /></td>
                        <td><u:translation key="label.Goal"/><br /><s:select name="teamOnePlayerList[%{index}].goals" list="playerGolList"  /></td>
                        <td><u:translation key="label.Autogoal"/><br /><s:select name="teamOnePlayerList[%{index}].ownGoals" list="playerOwnGol"  /></td>
                      </tr>
                    </table>
                        <s:textarea name="teamOnePlayerList[%{index}].review" rows="" cols="" maxlength="500" id="idTeamOnePlayerList[%{index}].review" onkeyup="countStop(this,500);"  />
                  </div>
                </s:iterator>
                
                <p style="padding-top:30px;" class="right">
                  <%--s:url action="registerFriendToMatch" var="registerFriendToMatchUrl" namespace="" method="input">
                    <s:param name="idMatch">${idMatch}</s:param>
                    <s:param name="team">TEAM_ONE</s:param>
                  </s:url>
                  <a class="light" href="javascript: openPopupRegisterFriendToMatch('${registerFriendToMatchUrl}'); "><u:translation key="label.iscriviGokickers"/> <big>&raquo;</big></a>
                  <br/--%>
                  <a class="directCallDataIscriveOne light" href="javascript:void(0);"  >
                    <u:translation key="label.Iscrivi.giocatori" /> <big>&raquo;</big>
                  </a>

                </p>
              </div>

              <div class="teamColumn right">

                <h2><u:translation key="label.squadra"/> 2</h2>

                <div class="teamMainInfo">

                  <table>
                    <tr>
                      <td><u:translation key="label.teamName"/>:</td>
                      <td><s:textfield name="teamTwoName" maxLength="25" onclick="clearTeamName(this)"/></td>
                      <td><s:select name="teamTwoGoals" list="golNumberList"  headerKey="" headerValue="%{getText('label.Goal')}" /></td>
                    </tr>
                    <tr>
                      <td><u:translation key="label.Maglia"/>:</td>
                      <td><s:textfield name="teamTwoShirt" maxLength="25" /></td>
                    </tr>
                  </table>
                </div>


                <s:iterator value="teamTwoPlayerList" var="teamTwoPlayer" status="stat">
                  <s:set value="#stat.count" id="count"/>
                  <s:set value="teamTwoPlayerList.size" id="listSize"/>
                  <s:set id="playerStatus">${teamTwoPlayer.playerStatus}</s:set>
                  <s:set id="index">${count-1}</s:set>
                  <s:hidden name="teamTwoPlayerList[%{index}].id" />
                  <s:hidden name="teamTwoPlayerList[%{index}].position" />
                  <s:hidden name="teamTwoPlayerList[%{index}].playerType" />
                  <s:if test="#teamTwoPlayer.user != null">
                    <s:hidden name="teamTwoPlayerList[%{index}].user.id" />
                    <s:hidden name="teamTwoPlayerList[%{index}].user.firstName" />
                    <s:hidden name="teamTwoPlayerList[%{index}].user.lastName" />
                    <s:hidden name="teamTwoPlayerList[%{index}].user.recordedChallenges" />
                    <s:hidden name="teamTwoPlayerList[%{index}].user.recordedMatches" />
                  </s:if>
                  <s:else>
                    <s:hidden name="teamTwoPlayerList[%{index}].outFirstName" />
                    <s:hidden name="teamTwoPlayerList[%{index}].outLastName" />
                  </s:else>
                  <p class="playerHead">
                    <span class="left">
                      <s:property value="#stat.count" />
                      <s:if test="#teamTwoPlayer.user != null">
                        <u:printUserNameBase user="${teamTwoPlayer.user}" showAvatar="true" displayVertical="false" />
                      </s:if>
                      <s:else>
                        <u:printSquadOut player="${teamTwoPlayer}" showAvatar="true" />
                      </s:else>
                    </span>

                    <span class="right">
                      <s:if test="#index > 0">
                        <a href="javascript: playerAction(${teamTwoPlayer.id},'moveup');" title="<u:translation key='label.pagelle.sposta.su'/>"><img src="<s:url value="/images/arrow_up.gif" encode="false" />" alt="<u:translation key='label.pagelle.sposta.su'/>" /></a><br />
                        </s:if>
                        <s:if test="#index <= 0">
                        <br />
                      </s:if>
                      <a href="javascript: playerAction(${teamTwoPlayer.id},'moveleft');" title="<u:translation key='label.pagelle.sposta'/>"><img src="<s:url value="/images/icon_move_left.gif" encode="false" />" alt="<u:translation key='label.pagelle.sposta'/>" /></a><br />
                        <s:if test="#index < (#listSize -1)">
                        <a href="javascript: playerAction(${teamTwoPlayer.id},'movedown');" title="<u:translation key='label.pagelle.sposta.giu'/>"><img src="<s:url value="/images/arrow_down.gif" encode="false" />" alt="<u:translation key='label.pagelle.sposta.giu'/>" /></a>
                        </s:if>
                    </span>
                    <span class="delete right">
                      <s:if test="#playerStatus=='OUT'">
                        <a href="javascript: void(0);" onclick="javascript: removeOutPlayer(${teamTwoPlayer.id},'remove');" title="<u:translation key='label.Elimina'/>" class="delete">X</a>
                      </s:if>
                      <s:else>
                        <s:url action="matchRegisteredPlayers" var="openPopUpUrl" namespace="/ajax" method="openPopUp"  >
                          <s:param name="namePlayerToRemove">${teamTwoPlayer.user.firstName} ${teamTwoPlayer.user.lastName}</s:param>
                          <s:param name="idPlayerToRemove">${teamTwoPlayer.id}</s:param>
                          <s:param name="idMatch">${idMatch}</s:param>
                        </s:url>
                        <a onclick="openPopupRemovePlayers('${openPopUpUrl}');" href="javascript: void(0);"  title="<u:translation key='label.Elimina'/>" class="delete">X</a>
                      </s:else>
                      <%--<a href="javascript: removePlayer(${teamTwoPlayer.id},'remove');" title="<u:translation key='label.Elimina'/>" class="delete">X</a>--%>
                    </span>
                  </p>
                  <div class="playerScore">
                    <table>
                      <tr>
                        <td><u:translation key="label.role"/><br /><s:select name="teamTwoPlayerList[%{index}].playerRole.id" list="playerRoleInfoList" listKey="id" listValue="shotName" /></td>
                        <td><u:translation key="label.Voto"/><br /><s:select name="teamTwoPlayerList[%{index}].vote" list="playerVoteList" listKey="id" listValue="label" /></td>
                        <td><u:translation key="label.Goal"/><br /><s:select name="teamTwoPlayerList[%{index}].goals" list="playerGolList"   /></td>
                        <td><u:translation key="label.Autogoal"/><br /><s:select name="teamTwoPlayerList[%{index}].ownGoals" list="playerOwnGol"  /></td>
                      </tr>
                    </table>
                    <s:textarea name="teamTwoPlayerList[%{index}].review" rows="2" cols="30" onkeyup="countStop(this,500);" />
                  </div>
                </s:iterator>
                
                <p style="padding-top:30px;" class="right">
                  <%--s:url action="registerFriendToMatch" var="registerFriendToMatchUrl" namespace="" method="input">
                    <s:param name="idMatch">${idMatch}</s:param>
                    <s:param name="team">TEAM_TWO</s:param>
                  </s:url>
                  <a class="light"  href="javascript: openPopupRegisterFriendToMatch('${registerFriendToMatchUrl}'); "><u:translation key="label.iscriviGokickers"/> <big>&raquo;</big></a>
                  <br/--%>
                  <a class="directCallDataIscriveTwo light" href="javascript:void(0);"  >
                    <u:translation key="label.Iscrivi.giocatori" /> <big>&raquo;</big>
                  </a>
                </p>
                  
              </div>

              <br class="clear" />
            
              <p style="padding:30px 10px 0 10px;" class="centred">
                <strong><u:translation key="label.Recensione"/></strong> (<span id="idShowChars">5000</span>)
                <s:textarea name="comment" rows="10" cols="" cssClass="maximized" onkeyup="countStop(this,5000,'idShowChars');"/>
              </p>
              <br />
              <p class="centred">
                <s:if test="recorded == false">
                  <a href="javascript: JQ('span#idWaitOne').css('visibility', 'visible'); submitForm(false);" class="btn action2" ><u:translation key="label.archiveSalvaBozza"/></a>
                  <span id="idWaitOne" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" style="vertical-align: bottom;" /></span>
                </s:if>
                <a href="javascript: JQ('span#idWaitTwo').css('visibility', 'visible'); archive(true); " class="btn action"><u:translation key="label.archiveArchivia"/></a>
                <span id="idWaitTwo" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" style="vertical-align: bottom;" /></span>
              </p>
            </s:form>
          </s:else>

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
