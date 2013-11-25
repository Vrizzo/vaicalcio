<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>

<%-- A sei iscritto--%>
<s:if test="matchDone">
      <!--non visulizzo nulla caso partita nel passato o annullata-->
</s:if>
<s:else>


<s:if test='blockType=="a"' >  
  <br />
  <h3>
    <u:translation key="messagge.seiIscritto"/>!
    <span>[<a onclick="$('playerStatusDiv').toggle();" href="javascript: void(0);"><s:property value="%{getText('label.modify')}" /></a>]</span>
  </h3>
  <div id="playerStatusDiv" style="display:none;"  >
      <table>
      <tr  id="idSpanErrorRole" style="display :none;">
        <td colspan="2">
          <span >
            <b>
              <font color="red">
                <s:property value="%{getText('error.role.required')}" />
              </font>
            </b>
          </span>
        </td>
      </tr>
      <tr  id="idSpanErrorMobile" style="display :none;">
        <td colspan="2">
          <span >
            <b>
              <font color="red">
                <s:property value="%{getText('error.mobile.invalid')}" />
              </font>
            </b>
          </span>
        </td>
      </tr>
      <tr>
        <td style="width:85px;"><u:translation key="label.tuoRuolo"/>:</td>
        <td>
          <s:select
            id="userRole"
            emptyOption="false"
            name="userToRegisterRole"
            list="#{'1':getText('label.portiere'),'2':getText('label.difensore'),
                    '3':getText('label.centrocampista'),'4':getText('label.attaccante')
            }"
            />
        </td>
      </tr>

      <tr>
        <td>
          <u:translation key="label.tuoCell"/>:
        </td>

        <td>
          <s:textfield id="userMobile" name="userToRegisterMobile" maxLength="20" onkeydown="$('idDeleteMobile').style.visibility='visible'" />
          <a class="delete" id="idDeleteMobile" onclick="$('userMobile').value='';$('idDeleteMobile').style.visibility='hidden'" href="javascript: void(0);" style="visibility:${deleteMobileVisibile? '' :'hidden' }"  >X</a>
        </td>
      </tr>
    </table>
    <p>
      <a class="btn" style="margin-right:10px;" id="btnUpdatePlayer" onclick="updateMatchPlayer(${currentUser.id},document.getElementById('userRole').value, document.getElementById('userMobile').value  );" href="javascript: void(0);"><u:translation key="label.Modifica"/></a>

      <s:if test="removeEnable">
        <a class="btn action2" onclick="removeCurrentPlayer(${idCurrentPlayer});" href="javascript: void(0);"><u:translation key="label.Disdici"/></a>
      </s:if>
    </p>
  </div>
</s:if>


<%-- B gioca questa partita--%>
<s:if test='blockType=="b" && playersToAdd > 0 ' >
  <br />
  <h3>
    <u:translation key="message.giocaPartita"/>:
  </h3>
  <table>
    <tr id="idSpanErrorRole" style="display :none;">
      <td colspan="2" style="padding:0;">
        <span>
          <b>
            <font color="red">
              <s:property value="%{getText('error.role.required')}" />
            </font>
          </b>
        </span>
      </td>
    </tr>
    <tr id="idSpanErrorMobile" style="display :none;">
      <td colspan="2">
        <span>
          <b>
            <font color="red">
              <s:property value="%{getText('error.mobile.invalid')}" />
            </font>
          </b>
        </span>
      </td>
    </tr>
    <tr>
      <td  style="width:85px;"><u:translation key="label.tuoRuolo"/>: </td>

      <td>
        <s:select
          id="idUserRole"
          emptyOption="false"
          headerValue="%{getText('label._seleziona')}"
          headerKey=""
          name="userToRegisterRole"
          list="#{'1':getText('label.portiere'),'2':getText('label.difensore'),
                  '3':getText('label.centrocampista'),'4':getText('label.attaccante')
          }"
          />

      </td>
    </tr>
    <tr>
      <td><u:translation key="label.tuoCell"/>:</td>
      <td>
        <s:textfield id="userMobile" name="userToRegisterMobile" maxLength="20" onkeydown="$('idDeleteMobile').style.visibility='visible'" />
        <a class="delete" id="idDeleteMobile" onclick="$('userMobile').value='';$('idDeleteMobile').style.visibility='hidden'" href="javascript: void(0);" style="visibility:${deleteMobileVisibile? '' :'hidden' }"  >X</a>
      </td>
    </tr>
  </table>
  <p>
    <a class="btn" id="btnRegisterUserToMatch"  onclick="this.disabled=true;registerUserToMatch(${currentUser.id},$('idUserRole').value, $('userMobile').value  );" href="javascript: void(0);"><u:translation key="label.Iscriviti"/></a>
  </p>


</s:if>


<%-- C richiedi di giocare--%>
<s:if test='blockType=="c"' >
   <br />
  <h3><u:translation key="messagge.richiediGiocare"/></h3>

  <table>
    <tr>
      <td colspan="2" style="padding:0;">
        <span    id="idSpanErrorRole" style="display :none;">
          <b>
            <font color="red">
              <s:property value="%{getText('error.role.required')}" />
            </font>
          </b>
        </span>
      </td>
    </tr>
    <tr>
      <td colspan="2" style="padding:0;">
        <span    id="idSpanErrorMobile" style="display :none;">
          <b>
            <font color="red">
              <s:property value="%{getText('error.mobile.invalid')}" />
            </font>
          </b>
        </span>
      </td>
    </tr>

    <tr>
      <td  style="width:85px;"><u:translation key="label.tuoRuolo"/>:</td>
      <td>
        <s:select
          id="idUserRoleReq"
          emptyOption="false"
          headerValue="%{getText('label._seleziona')}"
          headerKey=""
          name="userToRegisterRole"
          list="#{'1':getText('label.portiere'),'2':getText('label.difensore'),
                  '3':getText('label.centrocampista'),'4':getText('label.attaccante')
          }"
          />
      </td>
    </tr>
    <tr>
      <td><u:translation key="label.tuoCell"/>:</td>
      <td>
        <s:textfield id="userMobile" name="userToRegisterMobile" maxLength="20" onkeydown="$('idDeleteMobile').style.visibility='visible'" />
        <a class="delete" id="idDeleteMobile" onclick="$('userMobile').value='';$('idDeleteMobile').style.visibility='hidden'" href="javascript: void(0);" style="visibility:${deleteMobileVisibile? '' :'hidden' }"  >X</a>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <small>
          <u:translation key="label.match.mailRichiedi" /> <s:property value="email" />
        </small>
      </td>
    </tr>
  </table>

  <p>
    <a class="btn" id="btnRequestToplayMatch"  onclick="requestToplayMatch (${currentUser.id},document.getElementById('idUserRoleReq').value, document.getElementById('userMobile').value  );" href="javascript: void(0);"><u:translation key="label.Richiedi"/></a>
    <span    id="idSpanErrorRoleReq" style="display :none;">
      <b>
        <font color="red">
          <s:property value="%{getText('error.role.required')}" />
        </font>
      </b>
    </span>
  </p>
</s:if>


<%-- D richiesta inviata--%>
<s:if test='blockType=="d"' > 
 <br />
  <h3><u:translation key="message.richiestaInviata"/>
     <span>[<a onclick="$('playerStatusDiv').toggle();" href="javascript: void(0);"><s:property value="%{getText('label.modify')}" /></a>]</span>
  </h3>

  <div id="playerStatusDiv" style="display:none;" >
    <table>
      <tr>
        <td colspan="2" style="padding:0;">
          <span    id="idSpanErrorRole" style="display :none;">
            <b>
              <font color="red">
                <s:property value="%{getText('error.role.required')}" />
              </font>
            </b>
          </span>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="padding:0;">
          <span    id="idSpanErrorMobile" style="display :none;">
            <b>
              <font color="red">
                <s:property value="%{getText('error.mobile.invalid')}" />
              </font>
            </b>
          </span>
        </td>
      </tr>
      <tr>
        <td  style="width:85px;">
          <u:translation key="label.tuoRuolo"/>:
        </td>

        <td>
          <s:select
            id="userRole"
            emptyOption="true"
            name="userToRegisterRole"
            list="#{'1':getText('label.portiere'),'2':getText('label.difensore'),
                    '3':getText('label.centrocampista'),'4':getText('label.attaccante')
            }"
            />
        </td>
      </tr>
      <tr>
        <td><u:translation key="label.tuoCell"/>:</td>
        <td>
          <s:textfield id="userMobile" name="userToRegisterMobile" maxLength="20" onkeydown="$('idDeleteMobile').style.visibility='visible'" />
          <a class="delete" id="idDeleteMobile" onclick="$('userMobile').value='';$('idDeleteMobile').style.visibility='hidden'" href="javascript: void(0);" style="visibility:${deleteMobileVisibile? '' :'hidden' }"  >X</a>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <small>
            <u:translation key="label.richiestaInivataIl"/> <s:date name="requestDate" format="dd/MM/yy" /> <u:translation key="label.alleOre"/> <s:date name="requestDate" format="HH:mm" />
            <br/>
            <u:translation key="label.match.mailRichiedi" /> <s:property value="email" />
          </small>
        </td>
      </tr>
    </table>
    <p>
      <a class="btn" style="margin-right:10px;" id="btnUpdatePlayer" onclick="updateMatchPlayer(${currentUser.id},document.getElementById('userRole').value, document.getElementById('userMobile').value  );" href="javascript: void(0);"><u:translation key="label.Modifica"/></a>

      <a class="btn action2" onclick="removeCurrentRequestPlayer(${idCurrentPlayer});" href="javascript: void(0);"><s:property value="%{getText('btn.request.revert')}" /></a>

    </p>
  </div>
</s:if>

<%-- E raggiunto numero max di nonAmici--%>
<s:if test='blockType=="e"' >

  <h2><u:translation key="message.matchPlayerStatusLimiteFuoriRosa"/> </h2>
</s:if>


</s:else>

