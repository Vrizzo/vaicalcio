<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />

    <title><u:translation key="page.title.default" /></title>

    <script type="text/javascript">

      function initData()
      {
        reloadRequestList();
        loadMatchPlayerStatus();
        loadMatchDetails();
        reloadRegisteredList();
        showInviteFriends();
      }

      function initDataNoMatchDetails()
      {
        reloadRequestList();
        loadMatchPlayerStatus();
        reloadRegisteredList();
      }

      function initDataNoRegisteredList()
      {
        reloadRequestList();
        loadMatchPlayerStatus();
        loadMatchDetails();
        showInviteFriends();
        
      }

      function initDataNoStatus()
      {
        reloadRequestList();
        loadMatchDetails();
        reloadRegisteredList();
        loadMatchPlayerStatus();
      }

          function loadMatchDetails()
      {
        new Ajax.Updater('detailsContainer', '<s:url action="loadMatchDetails" namespace="/ajax"/>',
        {
          parameters: {
            idMatch: ${guiMatchInfo.matchInfo.id},
            dummy: Math.random()
          }
        });
      }

          function reloadRegisteredList()
      {
       
        new Ajax.Updater('playersRegisteredListContainer', '<s:url action="matchRegisteredPlayers" namespace="/ajax"/>',
        {
              onComplete:showInviteFriends,
          parameters: {
            idMatch: ${guiMatchInfo.matchInfo.id},
            dummy: Math.random()
          }
        });
      }

      function showInviteFriends()
      {
        
        
      }
      
          function loadMatchPlayerStatus()
      {
            if (!(${matchDone}))
        {
              new Ajax.Updater('playersStatusContainer', '<s:url action="matchPlayerStatus" namespace="/ajax" />',
          {
            parameters: {
                  isRegistered: ${isRegistered},
              idMatch: ${guiMatchInfo.matchInfo.id},
              dummy: Math.random()
            }
          });
        }
      }

          function reloadRequestList()
      {
            new Ajax.Updater('playersRequestListContainer', '<s:url action="matchRequestPlayers" namespace="/ajax" />',
        {
          parameters: {
            idMatch: ${guiMatchInfo.matchInfo.id},
            dummy: Math.random()
          }
        });
      }

      function reloadPage()
      {
        window.location.reload(true);
      }

      function removeRegisteredPlayer(idPlayerToRemove,idMatch,missing)
      {
        new Ajax.Updater('playersRegisteredListContainer', '<s:url action="matchRegisteredPlayers" namespace="/ajax" />',
        {
          onComplete:reloadPage,//initDataNoRegisteredList,
          parameters:
            {
            idMatch: idMatch,
            idPlayerToRemove:idPlayerToRemove,
            missing: missing,
            dummy: Math.random()
          }
        });
      }

      function removeRegisteredOutPlayer(idPlayerToRemove)
      {
            if( confirm('<u:translation key="message.viewMatchRemoveRegistered" escapeJavaScript="true"/>') )
        {
          new Ajax.Updater('playersRegisteredListContainer', '<s:url action="matchRegisteredPlayers" namespace="/ajax" />',
          {
                onComplete:reloadPage,
            parameters: {
              idMatch: ${guiMatchInfo.matchInfo.id},
              idPlayerToRemove:idPlayerToRemove,
              dummy: Math.random()
            }
          });
        }
      }

          function removeCurrentPlayer(idCurrentPlayer)
      {
        
         if( confirm('<u:translation key="message.viewMatchRemoveCurrentPlayer" escapeJavaScript="true"/>') )
        {
          new Ajax.Updater('playersStatusContainer', '<s:url action="removeCurrentPlayer" namespace="/ajax" />',
          {
                onComplete:function(){reloadRegisteredList(),showLastMessage(),goOnTop()},
            parameters: {
              idMatch: ${guiMatchInfo.matchInfo.id},
              idCurrentPlayer: idCurrentPlayer,
              dummy: Math.random()
            }
          });
        }
      }

          function removeCurrentRequestPlayer(idCurrentPlayer)
      {
            if( confirm('<u:translation key="message.viewMatchRemoveRequestPlayer" escapeJavaScript="true"/>') )
        {
          new Ajax.Updater('playersStatusContainer', '<s:url action="removeCurrentPlayer" namespace="/ajax" />',
          {
                onComplete:function(){reloadRegisteredList(),showLastMessage(),goOnTop()},
            parameters: {
              idMatch: ${guiMatchInfo.matchInfo.id},
              idCurrentPlayer: idCurrentPlayer,
              dummy: Math.random()
            }
          });
        }
      }

          function registerPlayer(idPlayerToRegister)
      {
            if( confirm('<u:translation key="message.viewMatchRegisterPlayer" escapeJavaScript="true"/>') )
        {
          new Ajax.Updater('playersRequestListContainer', '<s:url action="matchRequestPlayers" namespace="/ajax" />',
          {
                onComplete:initData,
            parameters: {
              idMatch: ${guiMatchInfo.matchInfo.id},
              idPlayerToRegister:idPlayerToRegister,
              dummy: Math.random()
            }
          });
        }
      }

          function registerRequestPlayer(idPlayerToRegister)
      {
            if( confirm('<u:translation key="message.viewMatchRegisterRequestPlayer" escapeJavaScript="true" />') )
        {
          new Ajax.Updater('playersRequestListContainer', '<s:url action="matchRequestPlayers" namespace="/ajax" />',
          {
                onComplete:function(){initData(),showLastMessage(),goOnTop()},
            parameters: {
              idMatch: ${guiMatchInfo.matchInfo.id},
              idPlayerToRegister:idPlayerToRegister,
              dummy: Math.random()
            }
          });
        }
      }

      function registerUserToMatch(idUserToRegister,userToRegisterRole,userToRegisterMobile)
      {
        $('idSpanErrorRole').style.display='none';
         
        if (validateMobile(userToRegisterMobile))
        {
          return;
        }
        if (validateUserRole(userToRegisterRole))
        {
          return;
        }

            if ($('idUserRole').value > 0)
        {
              if( confirm('<u:translation key="message.viewMatchRegisterUserToMatch" escapeJavaScript="true"/>') )
          {
            new Ajax.Updater('playersStatusContainer', '<s:url action="userRegisterToMatch" namespace="/ajax" />',
            {
                  onComplete:function(){initDataNoStatus(),showLastMessage(),goOnTop()},
              parameters:
                {
                idMatch: ${guiMatchInfo.matchInfo.id},
                idUserToRegister:idUserToRegister,
                userToRegisterRole:userToRegisterRole,
                userToRegisterMobile:userToRegisterMobile,
                dummy: Math.random()
              }
            });
          }
        }
        else
        {
          $('idSpanErrorRole').style.display='';
        }
      }

      function requestToplayMatch(idUserToRegister,userToRegisterRole,userToRegisterMobile)
      {
        if (validateMobile(userToRegisterMobile))
        {
          return;
        }
        if (validateUserRole(userToRegisterRole))
        {
              return;
        }
        
            if( confirm('<u:translation key="message.viewMatchRequestToPlayMatch" escapeJavaScript="true"/>') )
        {
          new Ajax.Updater('playersStatusContainer', '<s:url action="requestToplayMatch" namespace="/ajax" />',
          {
                onComplete:function(){initDataNoStatus(),showLastMessage(),goOnTop()},
            parameters: {
              idMatch: ${guiMatchInfo.matchInfo.id},
              idUserToRegister:idUserToRegister,
              userToRegisterRole:userToRegisterRole,
              userToRegisterMobile:userToRegisterMobile,
              dummy: Math.random()
            }
          });
        }
      }

      function validateMobile(userToRegisterMobile)
      {
        $('idSpanErrorMobile').style.display='none';
        var checkOK = "+0123456789";
        var checkStr = userToRegisterMobile;
        var allValid = true;
        for (i = 0; i < checkStr.length; i++)
        {
          ch = checkStr.charAt(i);
          for (j = 0; j < checkOK.length; j++)
            if (ch == checkOK.charAt(j))
              break;
          if (j == checkOK.length)
          {
            allValid = false;
            break;
          }
        }
        if (!allValid)
        {
          $('idSpanErrorMobile').style.display='';
          return true;
        }
      <%--$('idSpanErrorMobile').style.display='none';
      var test=userToRegisterMobile;
      if (isNaN (test))
      {
    
        $('idSpanErrorMobile').style.display='';
        return true;
      }--%>
          }

          function validateUserRole(userToRegisterRole)
          {
            $('idSpanErrorRole').style.display='none';
            if (!userToRegisterRole > 0)
            {
              $('idSpanErrorRole').style.display='';
              return true;
            }
            return false;
          }

          function updateMatchPlayer(idUserToRegister,userToRegisterRole,userToRegisterMobile)
          {
                if (validateMobile(userToRegisterMobile))
            {
              return;
            }

            new Ajax.Updater('playersStatusContainer', '<s:url action="updateMatchPlayer" namespace="/ajax" />',
            {
                  onComplete:initDataNoStatus,
              parameters: {
                idMatch: ${guiMatchInfo.matchInfo.id},
                idUserToRegister:idUserToRegister,
                userToRegisterRole:userToRegisterRole,
                userToRegisterMobile:userToRegisterMobile,
                dummy: Math.random()
              }
            });
        
          }

          function setReporter(idPlayerToReporter)
          {
            /*if (idPlayerToReporter==0)
            {
              alert('Scegliere un Reporter!!!') ;
              exit;
            }*/
          
            new Ajax.Updater('', '<s:url action="setReporter" namespace="/ajax" />',
            {
                  onComplete:function(){showLastMessage(),goOnTop()},
              parameters: {
                idMatch: ${guiMatchInfo.matchInfo.id},
                idPlayerToReporter:idPlayerToReporter
              }
            });
          }

          function goOnTop()
          {
            document.location.hash = 'topPage';
          }

          function showMessages()
          {
            showLastMessage();
            goOnTop();
          }


          /*   jQuery ModalBox*/

          /* <![CDATA[ */
            function directCall_Data(mode){
            var iFrameUrl ='<iframe frameborder="no" height="520" width="610" src="actUrl.action?idMatch=${idMatch}&activePage=callUp""></iframe>';
            if(mode=='callUp')
              iFrameUrl = iFrameUrl.replace("actUrl", "callUpToMatch!viewUsersToCall");
            else if(mode=='iscrive')
              iFrameUrl = iFrameUrl.replace("actUrl", "registerToMatch");
            else if(mode=='contact')
              iFrameUrl = iFrameUrl.replace("actUrl", "contactPlayers!viewRegistered");
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
            JQ(".directCallDataCallup").click(function(){
              directCall_Data('callUp');
            });

            JQ("a.directCallDataIscrive").click(function(){
              directCall_Data('iscrive');
            });

            JQ("a.directCallContactRegistered").click(function(){
              directCall_Data('contact');
            });
            
             JQ('body').click(function (event) {
                if (!JQ(event.target).closest('.btnMenu').length) {
                    JQ('.toggleMenu').hide();

                };
            });
            
          });

          /* ]]> */

          /*  jQuery ModalBox-->*/
    </script>

    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body onload="initData();">
    <a name="topPage" ></a>

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
        <div class="mainContentIndent" style="padding-bottom:24px;">
          <h1><u:translation key="label.Partita" /></h1>

          <div class="clearfix contTab">

            <p class="left" style="width:500px;">
              <utl:formatDate date="${guiMatchInfo.matchInfo.matchStart}" formatKey="format.date_7" /> -
              <s:property value="guiMatchInfo.matchInfo.sportCenterName" />
            </p>

            <s:set name="complete">${guiMatchInfo.matchInfo.complete}</s:set>
            <s:set name="atLeastOnePlayer">${guiMatchInfo.matchInfo.playersRegistered>0}</s:set>
            <s:set name="cancelled">${guiMatchInfo.matchInfo.canceled}</s:set>
            <s:set name="recorded">${guiMatchInfo.matchInfo.recorded}</s:set>
                
            <!-- menu convocazioni-->
            <s:if test="guiMatchInfo.ownerUser">
              <div class="btnMenu">
               

                <s:if test="#complete || matchDone ">
                  <s:set name="callUpClass"></s:set>
                </s:if>
                <s:elseif test="#cancelled">
                  <s:set name="callUpClass"></s:set>
                </s:elseif>
                <s:else>
                  <s:set name="callUpClass">directCallDataCallup</s:set>
                </s:else>

                <%--s:if test="complete=='false">++
                      <s:if test="!matchDone">
                        <s:set name="callUpClass">directCallDataCallup</s:set>
                      </s:if>
                </s:if>
                <s:elseif test="#cancelled">
                  <s:set name="callUpClass"></s:set>
                </s:elseif>
                <s:else>
                  <s:set name="callUpClass"></s:set>
                </s:else--%>

                <div class="cntBtnGk ${callUpClass}">
                  <div class="btnGkTopShadow">&nbsp;</div>
                  <div class="btnGkInner">
                    <s:property value="%{getText('label.Convoca')}" />
                  </div>
                </div>
                  
                <div class="cntBtnGk" onclick="$('idSubMenuBox').toggle();" >
                  <div class="btnGkTopShadow">&nbsp;</div>
                  <div class="btnGkInner pntDwn">
                    apri/chiudi
                  </div>
                </div>

                <!-- subMenu-->
                <div class="toggleMenu" id="idSubMenuBox" style="display: none;"  >
                  <ul>
                    <li>
                      <s:if test="#complete || matchDone ">
                        <span><u:translation key="label.Convoca.giocatori" /></span>
                      </s:if>
                      <s:elseif test="#cancelled">
                        <span><u:translation key="label.Convoca.giocatori" /></span>
                      </s:elseif>
                      <s:else>
                        <a class="directCallDataCallup" href="javascript:void(0);" onclick="$('idSubMenuBox').toggle();" >
                          <u:translation key="label.Convoca.giocatori" />
                        </a>
                      </s:else>
                    </li>

                    <li>
                      <s:if test="#complete || matchDone ">
                        <span><u:translation key="label.Iscrivi.giocatori" /></span>
                      </s:if>
                      <s:elseif test="#cancelled">
                        <span><u:translation key="label.Iscrivi.giocatori" /></span>
                      </s:elseif>
                      <s:else>
                        <a class="directCallDataIscrive" href="javascript:void(0);" onclick="$('idSubMenuBox').toggle();" >
                          <u:translation key="label.Iscrivi.giocatori" />
                        </a>
                      </s:else>
                    </li>

                    <li>
                      <s:if test="#atLeastOnePlayer || !matchDone">
                        <a class="directCallContactRegistered" href="javascript:void(0);" onclick="$('idSubMenuBox').toggle();" >
                          <u:translation key="label.Contatta.giocatori" />
                        </a>
                      </s:if>
                      <s:else>
                        <span><u:translation key="label.Contatta.giocatori" /></span>
                      </s:else>
                    </li>

                    <li>
                      <s:if test="#cancelled">
                        <span><u:translation key="label.Modifica.partita" /></span>
                      </s:if>
                      <s:else>
                        <s:url action="organizeMatchReview" method="input" var="organizeMatchReviewURL" >
                          <s:param name="idMatch">${idMatch}</s:param>
                        </s:url>
                        <a  href="${organizeMatchReviewURL}"><u:translation key="label.Modifica.partita" /></a>
                      </s:else>
                    </li>

                    <li>
                      <s:if test="#cancelled ||  #recorded || matchDone">
                        <span><u:translation key="label.Annulla.partita" /></span>
                      </s:if>
                      <s:else>
                        <s:url action="organizeMatchCancel"  var="organizeMatchCancelURL" >
                          <s:param name="idMatch">${idMatch}</s:param>
                        </s:url>
                        <a href="${organizeMatchCancelURL}" onclick="return confirm('<u:translation key="message.AnnullaLaPartita" escapeJavaScript="true" />');initData();">
                          <u:translation key="label.Annulla.partita" />
                        </a>
                      </s:else>
                    </li>
                  </ul>
                </div>
                <!-- subMenu-->

              </div>
            </s:if>
            <!-- menu convocazioni-->

          </div>

          <!-- matchsubMenu-->
          <jsp:include page="../jspinc/matchMenu.jsp" flush="true">
            <jsp:param name="activePage" value="viewMatch"/>
          </jsp:include>
          <!-- matchsubMenu-->

        </div>

        <div class="mainContent">
          <div class="orgMatch left">
            <!--  messaggio stato iscrizioni  -->
            <h2 align="center">
              <s:if test="#cancelled">
                <font color="red">
                  <strong>
                    <u:translation key="label._ANNULLATA"/>
                  </strong>
                </font>
              </s:if>
              <s:elseif test="registrationClosed">
                <s:if test="registrationDateString!=null">
                  <u:translation key="label.match.iscrizioniAperteNoHtmlTag"/> ${registrationDateString}
                </s:if>
                <s:else>
                  <u:translation key="label.match.iscrizioniChiuse"/>
                </s:else>
              </s:elseif>
              <s:else>
                <u:translation key="label.match.iscrizioniAperte"/>

                <%--s:url action="inviteToMatch" var="inviteToMatchUrl" method="input"  >
                    <s:param name="idMatch">${idMatch}</s:param>
                </s:url>

                                <span id="spanShowInviteFriends" style="display: none;">
                                    <a href="javascript: openPopupInviteFriends('${inviteToMatchUrl}'); ">
                                        <big>&raquo;</big><u:translation key="label.matchInviteFriends"/>
                                    </a>
                                </span--%>
              </s:else>
            </h2>
            <!--  fine messaggio stato iscrizioni  -->

            <%--            <h2 class="centred"><u:translation  key="label.dettagli" />
            <s:if test="guiMatchInfo.ownerUser">
              <s:url action="organizeMatchReview" method="input" var="organizeMatchReviewURL" >
                <s:param name="idMatch">${idMatch}</s:param>
              </s:url>
              <span>
                [<a  href="${organizeMatchReviewURL}"><u:translation key="label.modify" /></a>]
              </span>
            </s:if>
          </h2>--%>

            <div class="gameADV">

              <p>
                <big>
                  <strong><utl:formatDate   date="${guiMatchInfo.matchInfo.matchStart}" formatKey="format.date_4" /></strong>
                </big>
              </p>

              <p>
                <u:translation  key="label.MatchOraInizio" /> <strong><utl:formatDate   date="${guiMatchInfo.matchInfo.matchStart}" formatKey="format.date_5" /></strong> - <u:translation key="label.Calcio"/>  <strong><s:property value="guiMatchInfo.matchInfo.matchTypeName" /></strong>
              </p>

              <p>
                <a  target="blank" href="${guiMatchInfo.matchInfo.googleMapUrl}"  >
                  <s:property value="guiMatchInfo.matchInfo.sportCenterName" />
                </a>
              </p>

              <p>
                <s:property value="guiMatchInfo.matchInfo.sportCenterAddress" />
              </p>
              <p>
                <s:property value="guiMatchInfo.matchInfo.sportCenterCity" />
              </p>
            </div>


            <div class="indent">


              <br/>
              <div id="detailsContainer"></div>

              <table>
                <tr>
                  <td  style="width: 85px;">
                    <u:translation  key="label.note" />:
                  </td>
                  <td >
                    <s:property value="guiMatchInfo.matchInfo.notes"/>
                  </td>
                </tr>
              </table>

              <div id="playersStatusContainer"></div>

              <br />

            </div>
          </div>

          <div class="orgMatch right">

            <div id="playersRegisteredListContainer"></div>

            <s:if test="guiMatchInfo.ownerUser">
              <div id="playersRequestListContainer" ></div>
            </s:if>

          </div>

          <br class="clear" />




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
