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
                  
                  <h1 class="titleBig"><s:property value="%{getText('label.titolo.cambiaPassword')}" /></h1>

                  <s:set name="hasError" value="errorsPresent"/>

                  <s:if test="#hasError==false">
                  
                    <p>&nbsp;</p>
                    <p><u:translation key="label.changePasswordInserisciNuova"/></p><br />
                    <p>&nbsp;</p>

                    <s:form action="changePasswordSave" method="post"   >
                      <s:hidden name="id" />
                      <s:hidden name="checkPassword" />
                      <table>
                        <tr>
                          <td><u:translation key="label.newPassword"/>:</td>
                          <td><s:password name="newPassword" size="25" maxlength="15" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:200px;" /></td>
                        </tr>
                        <tr>
                          <td><u:translation key="label.repeatPassword"/>:</td>
                          <td><s:password name="repeatPassword" size="25" maxlength="15" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:200px;" /></td>
                        </tr>
                        <tr>
                          <td></td>
                          <td><s:submit value="Ok" cssClass="btn" /></td>
                        </tr>
                      </table>

                     </s:form>
                     
                   </s:if>
                   <p><s:fielderror name="changePasswordError" /></p>
                   <p><s:actionmessage /></p>

                </div>
                <!--### end mainContent ###-->

            </div>
            <!--### end centralcolumn ###-->

        <!--### start rightcolumn ###-->
          <jsp:include page="../jspinc/rightcolumn.jsp" flush="true" />
        <!--### end rightcolumn ###-->

      <!--### start footer ###-->
        <jsp:include page="../jspinc/footerHome.jsp" flush="true" />
      <!--### end footer ###-->

        </div>

    </body>
</html>

