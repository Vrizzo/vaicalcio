<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<s:url action="matchComments!viewAll" var="matchUrl" >
  <s:param name="idMatch">${idMatch}</s:param>
</s:url>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all" />
    <title><s:property value="%{getText('page.title.default')}" /></title>

    <script type="text/javascript">
         
      
      function changeTdClass(value)
      {      
        if (JQ("." + value).hasClass('selectedUser'))
          JQ("." + value).removeClass('selectedUser');
        else
        {
          if (JQ("." + value).hasClass('currentUser'))
          {
            JQ("." + value).removeClass('currentUser');
            JQ("." + value).addClass('selectedUser');
            JQ("." + value).addClass('currentUser');
          }
          else
            JQ("." + value).addClass('selectedUser');
        }
      }
      
      function checkMailList()
      {
        var freeText = jQuery.trim(JQ('[name=freeText]').val());
        freeText=freeText.replace(/&nbsp;/gi, '');
        freeText=freeText.replace(/[<]br[^>]*[>]/gi, '');
        freeText=JQ.trim(freeText);
        if((JQ('input[name=userMailList]').is(':checked'))==false)
        {
          JQ('#errorDiv').show();
          location.href="#errorAnchor";
          return;
        }
        else
        {
          JQ('#errorDiv').hide();
        }
        if(freeText=='')
        {
          JQ('#errorText').show();
          location.href="#errorAnchor";
          return
        }
        else
        {
          JQ('#errorText').hide();
        }
        JQ('#idContactPlayers').submit();

      }

          function checkSuccess()
      {
            if(${callUpOk})
        {
          parent.window.document.location='${matchUrl}';
        }
      }

      function change_parent_url(url)
      {
        closeModalBox()
        window.parent.document.location=url;
      }

      function closeModalBox()
      {
        window.parent.jQuery.fn.modalBox.close();
      }

      function highlightAndCheckChks(value)
      {
        if (value)
        {
          $$('.displayTD').each(function(c)
          {
            c.removeClassName("selectedUser");
            c.addClassName("selectedUser");
          });
        }
        else
        {
          $$('.displayTD').each(function(c)
          {
            c.removeClassName("selectedUser");
          });
        }
        $$('.playersToAddCheckBox').each(function(c)
        {
          c.checked = value;
        });
      }

    </script>

    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class=""   onload="checkSuccess(); ">
    <s:form id="idContactPlayers"  name="ContactPlayers"  action="contactPlayers!contactRegistered"  method="post"   >
    <script type="text/javascript">
      checkSuccess()
    </script>
    <h1 class="LBtitle"><u:translation key="label.contact.players.title" />
      <span><img id="yuiSpinner" alt="" src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
      <a class="LBclose" href="#" onclick="closeModalBox()">Chiudi</a>
    </h1>
    <ul class="clearfix underTbLnk callupMenu"><li class="">&nbsp;</li></ul>

    <div class="clearfix">

      <div class="LBsplitLeft">

        <div class="roseCont">
          <s:if test="guiPlayerInfoList.size > 0 ">
          
              <s:hidden name="idMatch"></s:hidden>
              <s:hidden name="playedMatch"></s:hidden>

              <strong class="tlPlayerToCallUp"><u:translation key="label.contact.player.iscritti" /></strong>

              <!--      start tabella utenti-->
              <display:table   name="guiPlayerInfoList" id="guiPlayer" requestURI="callUpToMatch!callUp.action" class="guiPlayer" >
                <u:displayTablePaginator />
                <s:set name="isCurrentUser">${guiPlayer.currentUser}</s:set>
                <s:set name="isOwnerUser">${guiPlayer.ownerUser}</s:set>
                <s:set name="playerStatus">${guiPlayer.status}</s:set>
                <s:set name="hasAge">${guiPlayer.userInfo.age>0}</s:set>
                <s:set name="playerType">${guiPlayer.type}</s:set>
                <s:set name="row">${guiPlayer_rowNum}</s:set>
                <s:set name="idUser">${guiPlayer.userInfo.id}</s:set>
                <s:set name="emailUser">${guiPlayer.userInfo.email}</s:set>

                <display:column  title="" sortable="false" class="displayTD ${isCurrentUser?'currentUser':''} ${guiPlayer.userInfo.id}" headerClass="first">
                  <s:checkbox id="userMailList" name="userMailList" fieldValue="%{#emailUser}"
                              onclick="changeTdClass(%{#idUser});"
                              cssClass="playersToAddCheckBox"/>
                </display:column>

                <display:column title=""  class="displayTD ${isCurrentUser?'currentUser first':'first'} ${guiPlayer.userInfo.id}">
                  <s:set name="guiPlayerId">${guiPlayer.userInfo.id}</s:set>
                  <s:set name="guiPlayerRecordedMatches">${guiPlayer.userInfo.recordedMatches}</s:set>
                  <s:set name="guiPlayerRecordedChallenges">${guiPlayer.userInfo.recordedChallenges}</s:set>
                  <s:set name="guiPlayerStatus">${guiPlayer.status}</s:set>
                  <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="true" showCurrentUserDetails="true" linkToDetails="true"  printName="false" printSurname="false" linkPlayMorePartnerStatus="false"/>
                </display:column>

                <display:column title="" class="displayTD ${isCurrentUser?'currentUser':''} ${guiPlayer.userInfo.id}" >
                  <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false" showCurrentUserDetails="${guiPlayer.status=='Out'? 'false':'true'}" linkToDetails="true" linkPlayMorePartnerStatus="false" />
                  <small class="additionalInfo">
                    <u:translation key="${guiPlayer.userInfo.playerRoleKey}"/>
                    <s:if test="#hasAge">
                      ${guiPlayer.userInfo.age} <s:property value="%{getText('label.udmEta')}" />
                    </s:if>
                  </small>
                  <s:if test="!matchDone">
                    <%--???--%>
                  </s:if>
                </display:column>

                <display:column title="" sortable="true" class="displayTD ${isCurrentUser?'currentUser centred':'centred'} ${guiPlayer.userInfo.id}" headerClass="centred" style="width:28px;">
                  <img src="images/flags/${guiPlayer.userInfo.idNatCountry}.png" onError="this.src='images/country_flag_0.gif';" title="${guiPlayer.userInfo.natCountry}"/>
                </display:column>

              </display:table>
              <!--      end tabella utenti-->

              <%-- s:checkbox id="idChkSelectAll" name="selectAll" onclick="javascript:changeCheckBox('.playersToAddCheckBox', this.checked);" onmouseup="highlightAllchk(this.value)" /--%>
              <s:checkbox id="idChkSelectAll" name="selectAll" onclick="highlightAndCheckChks(this.checked);"  />
              <u:translation key="label.Tutti"/>

              <script type="text/javascript">
                $('yuiSpinner').style.visibility='hidden';
              </script>

            </div>

          </div>

          <div class="LBsplitRight">
            <div class="spiritBox">
              <span class="tl"><u:translation key="label.spirito.GK"/></span>
              <img src="images/smilePositive.png" onError="this.src='images/country_flag_0.gif';" title="<u:translation key="label.aiutaci.promuovere"/>" />
              <s:url action="matchComments!viewAll" var="urlComments">
                <s:param name="idMatch">${idMatch}</s:param>
              </s:url>
              <p><em>
                  <u:translationArgs arg01="${urlComments}" key="label.spirito.GK.usoMail"/>
                </em>
              </p>

            </div>

            <div class="callUpMsgBox">
              <strong class="tl"><u:translation key="label.Messaggio"/></strong>
              <div class="msg txtAreaFx">
                <s:textarea  name="freeText" id="idFreeText"   rows="6" cols="50" cssClass="maximized" />
              </div>
              <p class="note"><u:translationArgs arg01="${userContext.user.email}" key="label.convocati.Riceveranno"  /></p>
              <p class="contBtn">
                <a href="javascript: checkMailList();" class="btn" >
                  <u:translation key="label.Invia"/>
                </a>
              </p>

            </div>


         
          <div class="callUpWarn">
            <div id="errorDiv" style="display:none" >
              <b><u:translation key="error.convoca.noGk"/></b>
            </div>
            <a id="errorAnchor"></a>
            <div id="errorText" style="display:none" >
              <b><u:translation key="error.convoca.noTesto"/></b>
            </div>
          </div>
        </div>
      </div>
    </s:if>

    <s:else>
      <div class="indentCont">
        <p>
          <u:translation key="message.nessunGkTrovato" />
          <script type="text/javascript">
            $('yuiSpinner').style.visibility='hidden';
          </script>
        </p>
      </div>
    </s:else>
 </s:form>
  </body>
</html>
