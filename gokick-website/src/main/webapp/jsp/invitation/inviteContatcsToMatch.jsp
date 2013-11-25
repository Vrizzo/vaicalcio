<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<s:url action="matchComments!viewAll" var="matchUrl" >
    <s:param name="idMatch">${idMatch}</s:param>
</s:url>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <jsp:include page="../../jspinc/commonHead.jsp" flush="true" />
        <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all" />
        <title><u:translation key="page.title.default" /></title>
        <script type="text/javascript">

            function checkMailList()
            {
                var isOneChk=false;
                JQ('.chkMail').each(function() {
                    if (this.checked) 
                    {
                        isOneChk=true;
                    }
                });
    
                if(isOneChk)
                    JQ('#idInviteToMatchForm').submit();
                else
                    JQ('#errorMails').show();
            }
         
            function toggleChkBoxes()
            {
                var chk=JQ('#idSelectAll').attr('checked');
                JQ('.chkMail').attr('checked', chk);
            }
      
            function checkSuccess()
            {
                if(${sendInviteOk})
                {
                    parent.window.document.location='${matchUrl}';
                }
            }
            
          function closeModalBox()
          {
            window.parent.jQuery.fn.modalBox.close();
          }

        </script>
      <!--### start Google Analitics inclusion ###-->
      <jsp:include page="../../jspinc/statisticsScript.jsp" flush="true" />
      <!--### end Google Analitics inclusion ###-->
    </head>
    <body  onload="checkSuccess(); ">

        <h1 class="LBtitle">
            <u:translation key="label.Convoca.giocatori" />
            <a class="LBclose" href="#" onclick="closeModalBox()" >Chiudi</a>
        </h1>


        <s:set name="activePage">invite</s:set>
        <jsp:include page="../../jspinc/callupMenu.jsp" flush="true"/>

        <s:form name="inviteToMatchForm" action="sendInviteToMatch" id="idInviteToMatchForm"  method="post" >
            <s:hidden name="idMatch"/>
            <table class="frmInvite" style="margin-top:40px;">
                <tr>
                    <td style="width:95px;">
                        <u:translation key="label.tuoMessaggio" />
                    </td>
                    <td>
                        <s:textarea  name="freeText" rows="3" cols="70"  style="width:345px; height:55px;" />
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <p style="padding-top:20px;">
                        <a href="javascript: checkMailList();" class="btn" >
                            &nbsp; <u:translation key="label.Convoca"/> &nbsp;
                        </a>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <!--      segnalazione errore-->
                        <div class="callUpWarn">
                            <div id="errorMails" style="display:none" >
                                <b><u:translation key="error.importContacts.emptyMailList"/></b>
                            </div>
                            <a id="errorAnchor"></a>
                        </div>
                        <!--      segnalazione errore-->
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <!--      segnalazione errore-->
                        <s:fielderror name="mailsError" cssErrorClass="error" fieldName="mailsError" />
                        <!--      segnalazione errore-->
                    </td>
                </tr>
            </table>
            <div class="importContactWrap" style="padding-left:105px;">
                <table>
                    <tr>
                        <td style="width:20px;">
                            <input type="checkbox" id="idSelectAll" onclick="toggleChkBoxes();"/>
                        </td>
                        <td>
                            <label for="selectAll"><b><u:translation key="label.selezionaTutti"/></b></label>
                        </td>
                    </tr>
                </table>

                <table class="maximized">
                    <tr>
                        <th style="width:20px;">
                        </th>
                        <th>
                            <u:translation key="label.importContacts.contactName"/>
                        </th>
                        <th>
                            <u:translation key="label.email"/>
                        </th>
                    </tr>
                         <tr>
                                <td>
                               
                                        <img src="<s:url value="/images/omi.gif" encode="false"/>" alt="è già un gokicker"  title="è già un gokicker" />
                                  

                            </td>
                            <td>
                               eqfdgwwx dqedq gtegegetggte
                            </td>
                            <td>
                                 <div class="stringFixWrap">ecfrvteewvt4rvbvrtybsbrybybrwwwwwxeqwdqeff</div>
                            </td>
                        </tr>
                    <c:forEach items="${guiContacts}" var="item" varStatus="status">
                        <s:set name="email">${item.email}</s:set>
                            <tr>
                                <td>
                                <c:choose>
                                    <c:when test="${item.registered}">
                                        <img src="<s:url value="/images/omi.gif" encode="false"/>" alt="è già un gokicker"  title="è già un gokicker" />
                                    </c:when>
                                    <c:otherwise>
                                        <s:checkbox 
                                            cssClass="chkMail"
                                            theme="simple"
                                            name="contacts"
                                            fieldValue="%{#email}"
                                            id="%{#email}"
                                            />
                                    </c:otherwise>
                                </c:choose>

                            </td>
                            <td>
                                ${item.name}
                            </td>
                            <td>
                                 <div class="stringFixWrap">${item.email}</div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </s:form>   

        <p class="noSpam"  style="padding-left:125px;">
            <strong >
                <u:translation key="label.NoSpam"/><br/>
            </strong>
            <u:translation key="label.infoNoSpam"/>
        </p>   

    </body>
</html>
