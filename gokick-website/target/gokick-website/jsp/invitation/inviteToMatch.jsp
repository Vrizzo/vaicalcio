<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="../exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<s:url action="matchComments!viewAll" var="matchUrl" >
  <s:param name="idMatch">${idMatch}</s:param>
</s:url>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../../jspinc/commonHead.jsp" flush="true" />
    <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all" />
    <title><u:translation key="page.title.default" /></title>
    <script type="text/javascript">

      function importContacts()
      {
        JQ('#idSpanSpinner').show();
        JQ('#idFormImportContacts').submit();
      }
      
      function checkMailList()
      {
        var mails = jQuery.trim(JQ('[name=addresses]').val());
        if(mails=='')
        {
          JQ('#errorMails').show();
          location.href="#errorAnchor";
          return;
        }
        else
        {
          JQ('#errorMails').hide();
        }
        JQ('#inviteToMatchForm').submit();
      }
      
      function closeModalBox()
      {
        window.parent.jQuery.fn.modalBox.close();
      }
      
      function checkSuccess()
      {
        if(${sendInviteOk})
        {
          parent.window.document.location='${matchUrl}';
        }
      }

    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body onload="checkSuccess();">

    <h1 class="LBtitle">
      <u:translation key="label.Convoca.giocatori" />
      <a class="LBclose" href="#" onclick="closeModalBox()" >Chiudi</a>
    </h1>


    <s:set name="activePage">invite</s:set>
    <jsp:include page="../../jspinc/callupMenu.jsp" flush="true"/>
    
        
        <h2 class="frmInvite">
          <u:translation key="label.inviteToMatch.importTitle"/>
        </h2>
        
        <s:form name="inviteToMatch!importContacts" action="inviteToMatch!importContacts" id="idFormImportContacts"  method="post">
          <s:hidden name="idMatch" id="idIdMatch"/>
          <table class="frmInvite">
             <tr>
                  <th>
                    <s:label><u:translation key='label.importContacts.yourProvider'/></s:label> 
                  </th>
                  <td>   
                    <select name="provider"  >
                      <option label="" value=""><u:translation key="label.seleziona"/></option>
                      <option label="" value='gmail'>GMail</option>
                      <option label="" value='yahoo'>Yahoo!</option>
                      <option label="" value='hotmail'>Hotmail / Msn / Live</option>
<!--                      <option label="" value='libero'>Libero</option>-->
                      <option label="" value='virgilio'>Virgilio</option>
<!--                      <option label="" value='gmx_net'>GMX.net</option>-->
                      <option label="" value="aol">AOL</option>
<!--                      <option label="" value='msn'>MSN</option>-->
                    </select>
                  </td>
                </tr>
                <tr>
                  <th>
                    <s:label><u:translation key='label.importContacts.yourMail'/></s:label> 
                  </th>
                  <td>
                    <s:textfield name="account" style="width:140px;"/> 
                  </td>
                </tr>
                <tr>
                  <th>
                    <s:label><u:translation key='label.password'/></s:label> 
                  </th>
                  <td>
                    <s:password name="password" style="width:140px;" />
                  </td>
                  <td style="vertical-align:middle;padding-left:7px;text-align:right;">
                    <a href="javascript: importContacts();" class="btn" >&nbsp; <u:translation key='label.importContacts.findContacts'/> &nbsp;</a> 
                    <span id="idSpanSpinner" style="display: none"><img id="yuiSpinner" alt="" src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                  </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                  <td colspan="4"><s:fielderror name="errorImport" cssErrorClass="error" fieldName="errorImport" /></td>
                </tr>
          </table>
        </s:form>
    
      <p class="titleList" style="margin:0 35px;"></p>
      
      <h2 class="frmInvite"><u:translation key='label.importContacts.individualAddresses'/></h2>

      <s:form name="inviteToMatchForm" action="inviteToMatch!execute" id="inviteToMatchForm"  method="post" >
        <s:hidden name="idMatch" />
        <table class="frmInvite">
            <tr id="idTrMails">
                <th>
                  <u:translation key="label.EmailInvitati" />
                </th>
                <td>
                  <s:textarea name="addresses" rows="2" cols="70" id="idSendAddresses"/>
                  <small><u:translation key="label.mail.infoDestinatari" /> </small>
                </td>
              </tr>
                 <tr style="height:10px;"><td></td></tr>
              <tr>
                <th>
                  <u:translation key="label.tuoMessaggio" />
                </th>
                <td>
                  <s:textarea  name="freeText" rows="2" cols="70" />
                </td>
              </tr>
                 <tr><td></td></tr>
              <tr>
                <th>&nbsp;</th>
                <td>
                    <p>
                    <a href="javascript: checkMailList();" class="btn" >
                    <u:translation key="label.Invia"/>
                  </a>
                  </p>
                </td>
              </tr>
              <tr>
                <th>&nbsp;</th>
                <td>
                  <!--      segnalazione errore-->
                  <div class="callUpWarn">
                    <div id="errorMails" style="display:none" >
                      <b><u:translation key="error.convoca.noMail"/></b>
                    </div>
                    <a id="errorAnchor"></a>
                  </div>
                  <!--      segnalazione errore-->
                   <!--      segnalazione errore-->
                  <s:fielderror name="mailsError" cssErrorClass="error" fieldName="mailsError" />
                  <!--      segnalazione errore-->
                </td>
              </tr>
             
              <tr>
            <th>&nbsp;</th>
            <td  >
              <p class="noSpam">
                <strong >
                  <u:translation key="label.NoSpam"/><br/>
                </strong>

                <u:translation key="label.infoNoSpam"/>
              </p>
            </td>
          </tr>    
        </table>
      </s:form>
</body>
</html>
