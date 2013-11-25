<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<s:url action="matchComments!viewAll" var="matchUrl" >
  <s:param name="idMatch">${idMatch}</s:param>
</s:url>

<s:url action="archiveMatch" var="matchArchivedUrl" method="input" >
  <s:param name="idMatch">${idMatch}</s:param>
</s:url>

<s:url action="inviteToMatch!input" var="urlInvite" >
  <s:param name="idMatch">${idMatch}</s:param>
  <s:param name="activePage" value="invite"></s:param>
</s:url>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
 
    <script type="text/javascript">

      var team='${team}';
      
      function checkMailList()
      {
        var freeText = jQuery.trim(JQ('[name=freeText]').val());
        freeText=freeText.replace(/&nbsp;/gi, '');
        freeText=freeText.replace(/[<]br[^>]*[>]/gi, '');
        freeText=JQ.trim(freeText);
        if (team=='')
        {
          if(freeText=='')
          {
            JQ('#errorText').show();
            location.href="#errorAnchor";
            return false;
          }
          else
          {
            JQ('#errorText').hide();
          }
        }
        
        if((JQ('input[name=idUserToAddList]').is(':checked'))==false)
        {
          JQ('#errorDiv').show();
          if (team=='')
          {
            location.href="#errorAnchor";
          }
          return false;
        }
        else
        {
          JQ('#errorDiv').hide();
        }
        return true;
      }

      function registerNotAjax()
      {
        if(checkMailList())
        {
          JQ('[name=idUserToAddList]').val(createUsersList());
          JQ('[name=userRegistration]').val(true);
          JQ('#idRegisterToMatch').submit();
        }
      }

      function checkSuccess()
      {
        if(${registerOk} && team=="")
        {
          parent.window.document.location='${matchUrl}';
        }
        else if(${registerOk} && (team=="TEAM_ONE" ||team=="TEAM_TWO") )
        {
          parent.window.document.location='${matchArchivedUrl}';
        }
        else
          initList();
      }

      function initList()
      {
        addUserAjax(0);
      }

      function reloadParent()
      {
        parent.window.document.location='${matchUrl}';
      }

      function closeModalBox()
      {
        window.parent.jQuery.fn.modalBox.close();
      }

      function changePlayerToAdd(select)
      {
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

      function clearOuterName(name)
      {
        var nome = name.value;
        var cerca = '<u:translation key="label.esterno"/>';

        if(nome.match(cerca)!=null)
        {
          name.value='';
        }
      }

      function checkPlayersThenSubmit()
      {
        
        $$('.playersRoleErrorsContainer').each(function(c){c.hide();})
        $$('.playersNameErrorsContainer').each(function(c){c.hide();})
        $$('.outersSelectedErrorsContainer').each(function(c){c.hide();})

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
          JQ('[name=userRegistration]').val(true);
          $('idFormRegisterOuter').submit();
        }
        else
          $('idSpanErrorSelection').show();
      }

      function addUserAjax(idUser)
      {
        var idUserList=createUsersList();

        new Ajax.Updater('playersRegisteredListContainer', '<s:url action="fillUserToRegisterList" namespace="/ajax"/>',
        {onComplete:loadAutocom,
          parameters: {
            idMatch: ${idMatch},
            idUserToAdd: idUser,
            idUserToAddList: idUserList ,
            team: team ,
            dummy: Math.random()

          }
        });
      }

      function removeUserAjax(idUser)
      {
        var idUserList=createUsersList();

        new Ajax.Updater('playersRegisteredListContainer', '<s:url action="fillUserToRegisterList" namespace="/ajax"/>',
        {onComplete:loadAutocom,
          parameters: {
            idMatch: ${idMatch},
            idPlayerToRemove: idUser,
            idUserToAddList: idUserList ,
            team: team ,
            dummy: Math.random()

          }
        });
      }

      //MAYBE UNUSED
      function registerToMatchAjax()
      {
        if (checkMailList()==false)
          return;

        var freeText = jQuery.trim(JQ('[name=freeText]').val());
        freeText=freeText.replace(/&nbsp;/gi, '');
        freeText=freeText.replace(/[<]br[^>]*[>]/gi, '');
        freeText=JQ.trim(freeText);
        
        var idUserList=createUsersList();

        new Ajax.Updater('playersRegisteredListContainer', '<s:url action="fillUserToRegisterList" namespace="/ajax"/>',
        {
          parameters: {
            idMatch: ${idMatch},
            idUserToAddList: idUserList ,
            userRegistration: true,
            freeText: freeText,
            team: team ,
            dummy: Math.random()

          }
        });
      }

      function boldCompleteName(n,s, t)
      {
        var completeName=n +" " +s ;
        
        var splittedString =t.split(" ");
        if(splittedString.length > 1 )
        {
          var i = 0
          for(i=0;i<splittedString.length;i++)
          {
            if (splittedString[i]!="")
              completeName= completeName.replace(new RegExp(splittedString[i], 'i'),"<b>"+splittedString[i]+"</b>");
              
          }
        }
        else
        {
            completeName= completeName.replace(new RegExp(t, 'gi'),"<b>"+t+"</b>");
        }
          
        
        return completeName;
      }

      function loadAutocom(){
        
          JQ(".classAutocom.image").remove();
          JQ(".classAutocom.ui").attr("colspan", "2");

          JQ( "#searchString" ).autocomplete({
            select: function(event,ui) {addUserAjax(ui.item.id)},

            source: function( request, response ) {
              JQ.ajax({
                url: "<s:url action="usersAutocom" namespace="/ajax"/>",
                dataType: "json",
                data: {
                  searchString: request.term,
                  idMatch: ${idMatch}
                },

                success: function( data ) {
                  response( JQ.map( data.userInfoList, function( item ) {
                    return {
                      name: item.name,
                      surName: item.surname,
                      completename: boldCompleteName(item.name,item.surname,data.searchString),
                      anonimous: item.anonymousEnabled,
                      value: "",
                      id: item.id,
                      role: item.playerRole,
                      age: item.age,
                      province: item.province,
                      searchedChars: data.searchString,
                      searchedLength: data.searchLength
                    }
                  }));
                }
                
              });
            },
            minLength: 2
          })
          .data( "autocomplete" )._renderItem = function( ul, item ) {
            return JQ( "<li></li>" )
            .data( "item.autocomplete", item )
            .append("<a><table>\n\
                     <tr>\n\
                        <td>\n\
                             <img src='viewAvatar.action?idUser=" + item.id +"' alt='' />\n\
                        </td>\n\
                        <td style='text-transform: capitalize;'>" + item.completename + "<br>\n\
                            <small>" + item.role + ", " +  printAge(item.age) + " <br/>\n\
                            " + item.province + "   </small>\n\
                        </td>\n\
                     </tr>\n\
                 </table>\n\
               </a>")
            .appendTo( ul );
          };
      }

      function printAge(age)
      {
        var anni = "<s:property value="%{getText('label.udmEta')}" />";
        if(age!="")
          return age + " " + anni;
        else
          return "";
      }

      function createUsersList()
      {

        var idUserToAdds=[];
        var  chkUsersList = JQ('.clsCheckUserList');
        JQ.each(chkUsersList, function(key, value)
        {
          idUserToAdds.push(value.value);
        });
        return idUserToAdds;

      }

    </script>

    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class=""   onload="checkSuccess();">

    <h1 class="LBtitle"><u:translation key="label.Iscrivi.giocatori" />
      <a class="LBclose" href="#" onclick="closeModalBox()">Chiudi</a>
    </h1>

    <jsp:include page="../jspinc/registerPlayersMenu.jsp" flush="true"/>

    <s:if test="activePage=='gokickers'">
      <s:if test="guiPlayerInfoList.size > 0">
        <div class="clearfix">
          <div class="LBsplitLeft">
            <s:form action="registerToMatch" id="idRegisterToMatch" >
              <span style="display:none;"><s:text name="idUserToAdd" /></span>
              <span style="display:none;"><s:text name="idPlayerToRemove" /></span>
              <span style="display:none;"><s:text name="userRegistration" /></span>
              <s:hidden name="idMatch"/>
              <s:hidden name="idPlayerToRemove"/>
              <s:hidden name="idUserToAdd"/>
              <s:hidden name="userRegistration"/>
              <s:hidden name="team"/>

              <strong class="tlPlayerToCallUp"><u:translation key="label.iscrivi.gks" /></strong>
              <!--      start tabella utenti-->
              <div id="playersRegisteredListContainer">
                <%--jsp:include page="../jspinc/matchManageRegistration.jsp" flush="true" /--%>
              </div>
              <!--      start tabella utenti-->
            </div>
            <div class="LBsplitRight">
              <s:set name="team">${team}</s:set>
              <s:if test="team==null || team==''">
                <div class="callUpMsgBox">
                  <strong class="tl"><u:translation key="label.Messaggio"/></strong>

                  <div class="msg txtAreaFx">
                    <s:textarea  name="freeText" id="idFreeText" rows="6" cols="30" cssClass="maximized"  />
                  </div>
                  
                  <p class="note"><u:translationArgs arg01="${userContext.user.email}" key="label.iscritti.Riceveranno"  /></p>

                  <p class="contBtn">
                    <a href="javascript: registerNotAjax();" class="btn" >
                      <u:translation key="label.Iscrivi"/>
                    </a>
                  </p>

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
              </s:if>
              <s:else>
                <div class="callUpMsgBox" style="text-align:center; padding-top:28px;">

                  <a href="javascript:void(0);" class="btn contBtn"  onclick="registerNotAjax();">
                    <u:translation key="label.Iscrivi"/>
                  </a>

                  <div class="callUpWarn" >
                    <div id="errorDiv" style="display:none" >
                      <b><u:translation key="error.convoca.noGk"/></b>
                    </div>
                    <a id="errorAnchor"></a>
                  </div>
                </div>


              </s:else>

            </s:form>
          </div>

        </div>
      </s:if>
      <s:else>
        <u:translation key="label.limite.gokickers.raggiunto"/>
      </s:else>
    </s:if>


    <s:elseif test="activePage=='outers'">
      <%-- ESTERNI --%>
      <s:set name="sizeList"><s:property value="playersToAddList.size"/></s:set>

      <s:if test="#sizeList > 0">

        <s:form action="registerToMatch!outers" id="idFormRegisterOuter" >
          <div class="clearfix">
            <div class="LBsplitLeft">
              <span style="display:none;"><s:text name="userRegistration" /></span>
              <s:hidden name="userRegistration"/>
              <s:hidden name="idMatch"/>
              <s:hidden name="team"/>

              <strong class="tlPlayerToCallUp">
                <u:translation key="label._Iscrivi"/>
                <s:select
                  name="selectedNumberPlayersToAdd"
                  list="playersToAddList"
                  headerKey="0"
                  headerValue="-"
                  id="idOutPlayersSelect"
                  onchange="changePlayerToAdd(this);"/>
                <u:translation key="label.giocatori.esterni"/>
              </strong>

              <table class="extGokickersList">

                <tr>
                  <td>

                    <s:iterator value="playersToAddNameList" var="playersToAddName"  status="stat">
                      <div id="jsIdPlayersToAddName_<s:property value="%{#stat.count-1}" />" class="playersToAddContainer" style="display:none;" >
                        <p class="callupExt">
                          <s:textfield  onclick="clearOuterName(this);"
                                        name="playersToAddNameList[%{#stat.count-1}]"
                                        maxLength="25"
                                        value="%{getText('label.esterno')} %{#stat.count} "
                                        cssClass="selectNamePlayers"/>
                        </p>
                      </div>
                    </s:iterator>

                  </td>
                  <td>
                    <s:iterator value="playersRoleToAddList" var="playersRoleToAdd"  status="stat">
                      <div id="jsIdPlayersRoleToAdd_<s:property value="%{#stat.count-1}" />" class="playersRoleToAddContainer" style="display:none;" >
                        <p class="callupExt">
                          <s:select
                            cssClass="selectRolePlayers"
                            id="userRole"
                            emptyOption="false"
                            headerValue="%{getText('label.role')}"
                            headerKey=""
                            name="playersRoleToAddList[%{#stat.count-1}]"
                            list="#{'1':getText('label.portiere'),'2':getText('label.difensore'),'3':getText('label.centrocampista'),'4':getText('label.attaccante')}"/>
                        </p>
                      </div>
                    </s:iterator>
                  </td>
                </tr>
              </table>
            </div>


            <div class="LBsplitRight">
              <div class="spiritBox" style="padding-bottom:0;">

                <span class="tl"><u:translation key="label.spirito.GK"/></span>
                <img src="images/smilePositive.png" onError="this.src='images/country_flag_0.gif';" title="<u:translation key="label.aiutaci.promuovere"/>" />
                <p><em><u:translationArgs arg01="${urlInvite}" key="label.spirito.register.outers.description"/></em></p>

              </div>
              <div class="callUpMsgBox">
                <p class="contBtn">
                  <a href="#" class="btn" onclick="checkPlayersThenSubmit();" >
                    <u:translation key="label.Iscrivi" />
                  </a>
                </p>
              </div>
              <div class="callUpWarn">
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
                <span id="idSpanErrorSelection" style="display :none;" class="outersSelectedErrorsContainer">
                  <br/>
                  <font color="red">
                    <s:property value="%{getText('error.outers.noSelected')}" />
                  </font>
                </span>
              </div>
            </div>
          </div>
        </s:form>

      </s:if>
      <s:else>

        <br/>
        <br/>
        <p class="frmInvite"><u:translation key="label.limite.outers.raggiunto"/></p>


      </s:else>

      <%-- fine ESTERNI --%>
    </s:elseif>

  </body>
</html>
