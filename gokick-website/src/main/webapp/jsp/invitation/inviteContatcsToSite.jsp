<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <jsp:include page="../../jspinc/commonHead.jsp" flush="true" />
        <title><s:property value="%{getText('page.title.default')}" /></title>

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
                    JQ('#idInviteToSiteForm').submit();
                else
                    JQ('#errorMails').show();
            }
      
            function toggleChkBoxes()
            {
                var chk=JQ('#idSelectAll').attr('checked');
                JQ('.chkMail').attr('checked', chk);
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
            <jsp:include page="../../jspinc/leftColumn.jsp" flush="true" />
            <!--### end leftcolumn ###-->

            <!--### start centralcolumn ###-->
            <div class="centralCol">

                <jsp:include page="../../jspinc/topMenu.jsp" flush="true">
                    <jsp:param name="tab" value="inviteToSite"/>
                </jsp:include>

                <p class="topSubMenuLoggedIn">
                    &nbsp;
                </p>


                <!--### start mainContent ###-->
                <div class="mainContentIndent">

                    <h1><u:translation key="label.importContacts.invite"/></h1>

                    <p class="titleList light">
                        <u:translation key="label.inviteToSite.subTitle"/>
                    </p>

                    <p></p>

                    <s:form name="inviteToSiteForm" action="sendInviteToSite" id="idInviteToSiteForm"  method="post" >
                        <table class="frmInviteToSite" style="margin-top:25px;">
                            <tr>
                                <td style="width:95px;">
                                    <u:translation key="label.tuoMessaggio" />
                                </td>
                                <td>
                                    <s:textarea  name="freeText" rows="6" cols="70"  style="width:410px; height:55px;" />
                                </td>
                            </tr>

                            <tr>
                                <th>&nbsp;</th>
                                <td>
                                    <p>
                                        <a href="javascript: checkMailList();" class="btn" >
                                            &nbsp; <u:translation key="label.Invia"/> &nbsp;
                                        </a>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <th>&nbsp;</th>
                                <td>
                                    <!--      segnalazione errore-->
                                    <div class="callUpWarn" style="padding-top:0;">
                                        <div id="errorMails" style="display:none" >
                                            <b><u:translation key="error.importContacts.emptyMailList"/></b>
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

                        <div class="importContactWrap">
                            <table>
                                <tr>
                                    <td style="width:20px;"><input type="checkbox" id="idSelectAll" onclick="toggleChkBoxes();"/></td>
                                    <td><label for="selectAll"><b><u:translation key="label.selezionaTutti"/></b></label></td>
                                </tr>
                            </table>


                            <div class="importContactLst">

                               
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
                        </div>

                    </s:form>      




                    <p class="noSpam" style="padding:10px 0 10px 100px;">
                        <strong >
                            <u:translation key="label.NoSpam"/><br/>
                        </strong>

                        <u:translation key="label.infoNoSpam"/>
                    </p>   

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
