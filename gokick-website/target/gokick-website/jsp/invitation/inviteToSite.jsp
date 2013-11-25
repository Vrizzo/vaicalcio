<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>

    <script type="text/javascript">

      function checkMailList()
      {
        var mails = jQuery.trim(JQ('[name=addresses]').val());
        if(mails=='')
        {
          JQ('#errorMails').show();
          location.href="#errorAnchor";
          return
        }
        else
        {
          JQ('#errorMails').hide();
        }
        JQ('#idInviteToSiteForm').submit();
      }
      function importContacts()
      {
        JQ('#idSpanSpinner').show();
        JQ('#idFormImportContacts').submit();
      }
    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body>
    <div class="wrapper">

      <!--### start header ###-->
      <jsp:include page="../../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include page="../../jspinc/leftColumn.jsp" flush="true">
           <jsp:param name="tab" value="inviteToSite"/>
        </jsp:include>
      
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">

       
          <div class="topPageWrap">
              <jsp:include page="../../jspinc/headerTopBar.jsp" flush="true" />
          </div>  



        <!--### start mainContent ###-->
        <div class="mainContentIndent">

          <h1><u:translation key="label.importContacts.invite"/></h1>

          <p class="titleList light">
            <u:translation key="label.inviteToSite.subTitle"/>
          </p>

          <h2 style="padding-top:30px;font-size:8pt;"><u:translation key="label.inviteToSite.importTitle"/></h2>
          
          <div id="idDivImport">
            <s:form name="importContactsForm" action="inviteToSite!importContacts" id="idFormImportContacts"  method="post">
              <table class="frmInviteToSite">
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
          </div>

          

          <h2 style="padding-top:30px; font-size:8pt;"><u:translation key='label.importContacts.individualAddresses'/></h2>

          <s:form name="inviteToSiteForm" action="inviteToSite!execute" id="idInviteToSiteForm"  method="post" >
            <table class="frmInviteToSite">
              <tr id="idTrMails">
                <th>
                  <u:translation key="label.EmailInvitati" />
                </th>
                <td>
                  <s:textarea name="addresses" rows="3" cols="70"  style="height:40px;" id="idSendAddresses"/>
                  <small><u:translation key="label.mail.infoDestinatari" /> </small>
                </td>
              </tr>
                <tr><td></td></tr>
              <tr>
                <th>
                  <u:translation key="label.tuoMessaggio" />
                </th>
                <td>
                  <s:textarea  name="freeText" rows="6" cols="70"  style="height:55px;" />
                </td>
              </tr>
                 <tr><td></td></tr>
              <tr>
                <th>&nbsp;</th>
                <td>
                  <a href="javascript: checkMailList();" class="btn" >
                    <u:translation key="label.Invia"/>
                  </a>
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
             
            </table>

            <p class="noSpam" style="padding:30px 0 20px 100px;">
              <strong >
                <u:translation key="label.NoSpam"/><br/>
              </strong>

              <u:translation key="label.infoNoSpam"/>
            </p>   

          </s:form>
        </div>
        <!--### end maincontent ###-->



      </div>
      <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->
      <jsp:include page="../../jspinc/rightcolumn.jsp" flush="true" />
      <!--### end rightcolumn ###-->

      <!--### start footer ###-->
      <jsp:include page="../../jspinc/footer.jsp" flush="true" />
      <!--### end footer ###-->

    </div>

  </body>
</html>
