<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
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

      function highlightAndCheckChks(value)
      {
        if (value)
        {
          $$('#guiPlayer td').each(function(c)
                                {
                                  c.removeClassName("selectedUser");
                                  c.addClassName("selectedUser");
                                });
        }
        else
        {
          $$('#guiPlayer td').each(function(c)
                                {
                                  c.removeClassName("selectedUser");
                                });
        }
        $$('#guiPlayer input:checkbox').each(function(c)
                                         {
                                           c.checked = value;
                                         });
      }

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

      function changeActiveClass()
      {

        if (JQ(".menuGK").hasClass('active'))
        {
          JQ(".menuGK").removeClass('active');
          JQ(".menuGK" ).addClass('activeDis');
        }
        else
        {
          JQ(".menuGK").removeClass('activeDis');
          JQ(".menuGK" ).addClass('active');
        }
      }
    
      function showHideSearchBox()
      {
        if( JQ('.searchBox').is(':visible') )
        {
          JQ('.searchBox').hide();
          JQ('.searchLink').html('<u:translation key="label.ricerca"/>');
          JQ('.searchLink').removeClass('LBsearchLinkOn');
        }
        else
        {
          JQ('.searchBox').show();
          JQ('.searchLink').html('<strong><u:translation key="label.ricerca"/></strong>');
          JQ('.searchLink').addClass('LBsearchLinkOn');
        }
        changeActiveClass();
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
          return;
        }
        else
        {
          JQ('#errorDiv').hide();
        }
        if(freeText=='')
        {
          JQ('#errorText').show();
          //location.href="#errorAnchor";
          return
        }
        else
        {
          JQ('#errorText').hide();
        }
        JQ('#idCallUpUsersForm').submit();

      }

      function searchUser()
      {
        JQ('[name=advancedSearch]').val(true);
        JQ('#idSearchForm').submit();
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
        parent.document.location=url;
      }

      function closeModalBox()
      {
        window.parent.jQuery.fn.modalBox.close();
      }
    </script>

    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class=""  onload="checkSuccess(); ">
        
    <h1 class="LBtitle"><u:translation key="label.Convoca.giocatori" />
      <span><img id="yuiSpinner" alt="" src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
      <a class="LBclose" href="#" onclick="closeModalBox()">Chiudi</a>
    </h1>
      
    <jsp:include page="../jspinc/callupMenu.jsp" flush="true"/>

    <s:set name="activePage">${activePage}</s:set>

    <s:if test="privateMatch"> <%-- =='true' && #activePage='callUp'--%>
        <div class="frmInvite">
            <p style="padding-top:80px;color:#666;">
              <s:url action="callUpToMatch!friends.action" var="callUpFriendsUrl">
                <s:param name="idMatch">${idMatch}</s:param>
                <s:param name="activePage">friend</s:param>
              </s:url>
              <u:translationArgs key="label.convoca.privata" arg01="${callUpFriendsUrl}"/>

            <%--a href="callUpToMatch!friends.action?idMatch=${idMatch}&activePage=friend" >
              <u:translation key="label.convoca.privata"/>
            </a--%>

          </p>
           <s:url action="organizeMatchReview" var="reviewUrl" method="input" >
            <s:param name="idMatch">${idMatch}</s:param>
          </s:url>
          <%--a href="organizeMatchReview!input.action?idMatch=${idMatch}" --%>
          <p style="padding-top:15px;color:#666;">
              <u:translationArgs key="label.convoca.rendiPubblica" arg01="${reviewUrl}"/>
          </p>
          <%--p style="padding-top:15px;color:#666;">
            <a href="#"  onclick="change_parent_url('${reviewUrl}');">
              <u:translation key="label.convoca.rendiPubblica"/>
            </a>
          </p--%>
          <script type="text/javascript">
            $('yuiSpinner').style.visibility='hidden';
          </script>
        </div>
    </s:if>

    <s:else>

      <s:if test="activePage=='callUp'">

        <div class="clearfix">
          <!-- start form ricerca-->
          <a href="#" onclick="showHideSearchBox()" class="searchLink LBsearchLink ${advancedSearch==true?'LBsearchLinkOn':''}">
            <u:translation key="label.ricerca"/>
          </a>
          <div class="searchBox LBsearchBox" id="idBoxSearch" style="display:${advancedSearch==true?'':'none'}">
            
            <s:url action="callUpToMatch" method="viewUsersToCall"  var="viewUsersURL">
              <s:param name="advancedSearch" value="true"/>
            </s:url>
            <s:form action="searchToCallUp" method="post" id="idSearchForm"  >
              <span style="display:none;"><s:text name="advancedSearch" /></span>
              <s:hidden name="advancedSearch"/>
              <s:hidden name="idMatch"/>
              <s:hidden name="activePage"/>

              <table>
                <tr>
                  <td><u:translation key="label.firstname" />:</td>
                  <td><s:textfield name="firstName" size="12" cssStyle="border:solid 1px #999999; background:#FFFFFF;" /></td>
                  <td class="right"><u:translation key="label.lastName" />:</td>
                  <td colspan="2"><s:textfield name="lastName" size="12" cssStyle="border:solid 1px #999999; background:#FFFFFF;" /></td>
                </tr>
                <tr>
                  <td><u:translation key="label.role" />:</td>
                  <td colspan="5">
                    <fieldset>
                      <s:checkbox id="idChkGK" name="chkGk" /> <label for="idChkGK"><u:translation key="label._Portiere" /></label>
                      <s:checkbox id="idChkDF" name="chkDf" /> <label for="idChkDF"><u:translation key="label._Difensore" /></label>
                      <s:checkbox id="idChkCC" name="chkCc" /> <label for="idChkCC"><u:translation key="label._Centrocampista" /></label>
                      <s:checkbox id="idChkAT" name="chkAt" /> <label for="idChkAT"><u:translation key="label._Attaccante" /></label>
                    </fieldset>
                  </td>
                </tr>
                <tr>
                  <td><u:translation key="label.country" />:</td>
                  <td colspan="4">
                    <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
                    <s:select
                      name="idCountry"
                      list="countyList"
                      listKey="id"
                      listValue="name"
                      id="idSelectCountry"
                      value="idCountry"
                      onchange="getProvincesByCountry($(this).value, 1, 0, 0, '%{provincesByCountryURL}', null,0,1)" />
                  </td>
                </tr>
                <tr>
                  <td><u:translation key="label.province" />:</td>
                  <td colspan="4">
                    <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
                    <s:select
                      name="idProvince"
                      headerKey="0"
                      headerValue="%{getText('label.Tutte')}"
                      list="provinceList"
                      listKey="id"
                      listValue="name"
                      id="idSelectProvince"
                      value="idProvince"
                      onchange="getCitiesByProvince($(this).value, 1, 0, 0, '%{citiesByProvinceURL}',0,1)" />
                    <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                  </td>
                </tr>
                <tr>
                  <td><u:translation key="label.city" />:</td>
                  <td colspan="4">
                    <s:select
                      name="idCity"
                      headerKey="0"
                      headerValue="%{getText('label.Tutte')}"
                      list="cityList"
                      listKey="id"
                      listValue="name"
                      value="idCity"
                      id="idSelectCity" />
                    <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                  </td>
                </tr>
              </table>
                   <p class="clearfix cntBtn">
                    <%--s:submit value="%{getText('label.Cerca')}" cssClass="btn action1" /--%>
                    <a href="javascript: searchUser();" class="btn right" >
                      <u:translation key="label.Cerca"/>
                    </a>
                  </p>
            </s:form>
          </div>
        </div>
        <!-- end form ricerca-->
      </s:if>
         <s:form id="idCallUpUsersForm"  name="callUpUsersForm"  action="callUpToMatch!callUpUsers"  method="post" >
      <div class="clearfix">

        <div class="LBsplitLeft">

          <div class="roseCont">
            <s:if test="guiPlayerInfoList.size > 0 ">
             
                <s:hidden name="idMatch"></s:hidden>
                <s:hidden name="playedMatch"></s:hidden>

                <strong class="tlPlayerToCallUp">
                  <s:if test="#activePage=='friend'">
                    <u:translation key="label.friendsOnMarket" />
                  </s:if>
                  <s:elseif  test="#activePage=='callUp'">
                    <u:translation key="label.gksOnMarket" />
                  </s:elseif>
                  <s:elseif  test="#activePage=='calledUp'">
                    <u:translation key="label.gksConvocati" />
                  </s:elseif>
                </strong>
                <img src="<s:url value="/images/gioca.gif" encode="false" />" alt="<u:translation key="label.altText.market" />" title="<u:translation key="label.altText.market" />" />

                <!--      start tabella utenti-->
                <s:if test="advancedSearch">
                   <s:url action="callUpToMatch" method="viewUsersToCall"  var="displayTagURL">
                    <s:param name="advancedSearch" value="true"/>
                  </s:url>
                </s:if>
                <s:else >
                  <s:url action="callUpToMatch" method="viewUsersToCall"  var="displayTagURL">
                    <s:param name="advancedSearch" value="false"/>
                  </s:url>
                </s:else>
                <s:if test="activePage=='friend'">
                  <s:url action="callUpToMatch" var="callUpFriendsUrl" method="friends"/>
                  <s:set name="displayTagURL">${callUpFriendsUrl}</s:set>
                </s:if>
                <s:if test="activePage=='calledUp'">
                  <s:url action="callUpToMatch" var="calledUpUrl" method="calledUp"/>
                  <s:set name="displayTagURL">${calledUpUrl}</s:set>
                </s:if>
                <display:table   pagesize="${ activePage eq 'callUp' ? 100 : 0}"   name="guiPlayerInfoList" id="guiPlayer" requestURI="${displayTagURL}"  class="guiPlayer" >
                  <u:displayTablePaginator />
                  <s:set name="isCurrentUser">${guiPlayer.currentUser}</s:set>
                  <s:set name="isOwnerUser">${guiPlayer.ownerUser}</s:set>
                  <s:set name="playerStatus">${guiPlayer.status}</s:set>
                  <s:set name="hasAge">${guiPlayer.userInfo.age>0}</s:set>
                  <s:set name="playerType">${guiPlayer.type}</s:set>
                  <s:set name="row">${guiPlayer_rowNum}</s:set>
                  <s:set name="idUser">${guiPlayer.userInfo.id}</s:set>
                  <s:set name="emailUser">${guiPlayer.userInfo.email}</s:set>

                  <display:column  title="" sortable="false" class="${isCurrentUser?'':''} ${guiPlayer.userInfo.id}" headerClass="first">
                    <s:if test="#playerStatus=='Undefined' || #playerStatus=='UserRequest' || #playerType=='Missing'">
                      <s:checkbox id="userMailList" name="userMailList" fieldValue="%{#emailUser}"
                                  onclick="changeTdClass(%{#idUser});"
                                  cssClass="playersToAddCheckBox"/>
                    </s:if>
                    <s:elseif test="#playerStatus=='UserRegistered' || #playerStatus=='OwnerRegistered' || #playerType=='UserRequestRegistered'">
                      <img src="images/checkGreen.png"  onError="this.src='images/country_flag_0.gif';" title="<u:translation key='label.GKiscritto'/>" />
                    </s:elseif>
                    <s:elseif test="#playerStatus=='UserCalled'">
                      <img src="images/called.png"      onError="this.src='images/country_flag_0.gif';" title="<u:translation key='label.GKConvocato'/>" />
                    </s:elseif>
                  </display:column>

                  <display:column title=""  class="${isCurrentUser?'currentUser pic':'pic'} ${guiPlayer.userInfo.id}">
                    <s:set name="guiPlayerId">${guiPlayer.userInfo.id}</s:set>
                    <s:set name="guiPlayerRecordedMatches">${guiPlayer.userInfo.recordedMatches}</s:set>
                    <s:set name="guiPlayerRecordedChallenges">${guiPlayer.userInfo.recordedChallenges}</s:set>
                    <s:set name="guiPlayerStatus">${guiPlayer.status}</s:set>
                    <%--s:url action="viewAvatar" var="imageUrl">
                        <s:param name="idUser">
                          ${guiPlayer.userInfo.id}
                        </s:param>
                     </s:url>
                     <img src="${imageUrl}" alt="" /--%>
                    <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="true" showCurrentUserDetails="true" linkToDetails="true"  printName="false" printSurname="false" linkPlayMorePartnerStatus="false"/>

                  </display:column>

                  <display:column title="" class="${isCurrentUser?'currentUser':''} ${guiPlayer.userInfo.id}" >
                    <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false" showCurrentUserDetails="${guiPlayer.status=='Out'? 'false':'true'}" linkToDetails="true"  linkPlayMorePartnerStatus="false" />
                  
                    <small class="additionalInfo">
                    <u:translation key="${guiPlayer.userInfo.playerRoleKey}"/>
                    <s:if test="#hasAge">
                      ${guiPlayer.userInfo.age} <s:property value="%{getText('label.udmEta')}" />
                    </s:if>
                    </small>      
                    <%--${guiPlayer.userInfo.playedMatches}++${guiPlayer.userInfo.created}--%>
                  </display:column>

                  <display:column title="" sortable="false" class="${isCurrentUser?'currentUser right':'right'} ${guiPlayer.userInfo.id}" headerClass="centred" style="width:28px;">
                    <img src="images/flags/${guiPlayer.userInfo.idNatCountry}.png" onError="this.src='images/country_flag_0.gif';" title="${guiPlayer.userInfo.natCountry}"/>
                  </display:column>

                </display:table>
                <!--      end tabella utenti-->
                <s:if test="#activePage=='friend'">
                  <s:checkbox id="idChkSelectAll" name="selectAll" onclick="highlightAndCheckChks(this.checked);"  />
                  <u:translation key="label.Tutti"/>
                </s:if>

                <script type="text/javascript">
                  $('yuiSpinner').style.visibility='hidden';
                </script>

              </div>
            </div>


            <div class="LBsplitRight">

              <s:if test="activePage!='calledUp'">
                <s:if test="activePage=='callUp'">
                  <div class="spiritBox">
                    <span class="tl"><u:translation key="label.spirito.GK"/></span>
                    <img src="images/smilePositive.png" onError="this.src='images/country_flag_0.gif';" title="<u:translation key="label.aiutaci.promuovere"/>" />

                    <p><em><u:translation key="label.spirito.GK.description"/></em></p>
                  </div>
                </s:if>

                <div class="callUpMsgBox">
                  <strong class="tl"><u:translation key="label.messaggio.convocazione"/></strong>
                  <div class="msg txtAreaFx">
                    <s:textarea  name="freeText" id="idFreeText" rows="6" cols="30" cssClass="maximized"  />
                  </div>

                  <p class="note"><u:translationArgs arg01="${emailOwner}" key="label.convocati.Riceveranno"  /></p>
                  <a id="errorAnchor"></a>
                  <p class="contBtn">
                    <a href="javascript: checkMailList();" class="btn" >
                      <u:translation key="label.Convoca"/>
                    </a>
                  </p>

                  <s:fielderror  name="noGkSelected"/>
                </div>
              </s:if>

            

            <div class="callUpWarn">
              <div id="errorDiv" style="display:none" >
                <b><u:translation key="error.convoca.noGk"/></b>
              </div>

              <div id="errorText" style="display:none" >
                <b><u:translation key="error.convoca.noTesto"/></b>
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

        </div>
      </div>
</s:form>
    </s:else>
  </body>
</html>
