<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
      <title><s:property value="%{getText('page.title.default')}" /></title>
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
          <jsp:include page="../jspinc/leftColumn.jsp" flush="true" />
         <!--### end leftcolumn ###-->

            <!--### start centralcolumn ###-->
            <div class="centralCol">
              <div class="topPageWrap">
                <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
              </div>

              <!--### start mainContent ###-->
                <div class="mainContent">

                  <s:set name="message" value="info"/>
                 
                  <h1> 
                    <s:if test="#message=='permissionError'">
                      ${message}
                    </s:if>
                    <s:elseif test="#message=='idMatchCommentError'">
                      <s:property value="%{getText('message.title.impossibileVisualizzareCommentiPartita')}" />
                    </s:elseif>
                    <s:elseif test="#message=='idMatchError'">
                      <s:property value="%{getText('message.title.impossibileVisualizzareDatiPartita')}" />
                    </s:elseif>
                    <s:elseif test="#message=='matchExpired'">
                      <s:property value="" />
                    </s:elseif>
                    <s:elseif test="#message=='accessDenied'">
                      <s:property value="%{getText('message.title.info.accessDenied')}" />
                    </s:elseif>
                    <s:elseif test="#message=='changePasswordRequest'">
                      <span class="titleBig">
                        <s:property value="%{getText('message.title.info.changePasswordRequest')}" />
                      </span>
                    </s:elseif>
                    <s:elseif test="#message=='changePassword'">
                      <span class="titleBig">
                        <s:property value="%{getText('message.title.info.changePassword')}" />
                      </span>
                    </s:elseif>
                    <s:elseif test="#message=='matchError'">
                      <s:property value="%{getText('message.title.impossibileVisualizzareDatiPartita')}" />
                    </s:elseif>
                    <s:elseif test="#message=='archiveError'">
                      <s:property value="%{getText('message.title.impossibileArchiviareDatiPartita')}" />
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorInvalid'">
                      <u:translation key="message.title.invitoNonValido" />
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorUsed'">
                      <u:translation key="message.title.invitoUtilizzato" />
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorNotAvailable'">
                      <u:translation key="message.title.invitoNonDisponibile" />
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorMandatory'">
                      <u:translation key="message.title.invitoObbligatorio" />
                    </s:elseif>
                    <s:else>
                    </s:else>
                  </h1>

                  <p>&nbsp;</p>
                 
                  <p>
                    <s:if test="#message=='permissionError'">
                      <s:property value="%{getText('message.impossibileVisualizzareDatiPartita')}" />
                    </s:if>
                     <s:elseif test="#message=='idMatchError'">
                      <s:property value="%{getText('message.impossibileVisualizzareDatiPartita')}" />
                    </s:elseif>
                    <s:elseif test="#message=='matchExpiredError'">
                      <s:property value="" />
                    </s:elseif>
                    <s:elseif test="#message=='accessDenied'">
                      <s:property value="%{getText('message.info.accessDenied')}" />
                    </s:elseif>
                    <s:elseif test="#message=='changePasswordRequest'">
                      <s:property value="%{getText('message.info.changePasswordRequest')}" />
                    </s:elseif>
                    <s:elseif test="#message=='changePassword'">
                      <s:property value="%{getText('message.info.changePassword')}" />
                    </s:elseif>
                    <s:elseif test="#message=='matchError'">
                      <s:property value="%{getText('message.impossibileVisualizzareDatiPartita')}" />
                    </s:elseif>
                    <s:elseif test="#message=='archiveError'">
                      <s:property value="%{getText('message.impossibileArchiviareDatiPartita')}" />
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorInvalid'">
                      <u:translation key="message.info.invitoNonValido" />
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorUsed'">
                      <u:translation key="message.info.invitoUtilizzato" />
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorNotAvailable'">
                      
                    </s:elseif>
                    <s:elseif test="#message=='invitationErrorMandatory'">
                      <u:translation key="message.info.invitoObbligatorio" />
                    </s:elseif>
                    <s:else>
                      <s:property value="%{getText('error.info.missingParam')}" />
                    </s:else>
                  </p>

                  <p>&nbsp;</p>

                  
                  <br/>

                  <s:if test="#message=='permissionError' || #message=='matchError' || #message=='matchExpired' ">
                        <input type="button" class="btn"  value="Torna Indietro" onclick="history.back();" />
                  </s:if>
                  <s:elseif test="#message=='idMatchError' ">
                    <a class="btn" href="<s:url action="home" />"><u:translation key="label.tornaLogin"/></a>
                  </s:elseif>
                  <s:else>
                    <a class="btn" href="<s:url action="home" />"><u:translation key="label.tornaHome"/></a>
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
