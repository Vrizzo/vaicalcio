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

        <!--### start mainContent ###-->
        <div class="mainContentIndent">

          <h1><u:translation key="label.cancellaAccount"/></h1>

          <div class="contTab">
            <p class="left"><u:translation key="label.userCancelAccountSeiSicuro"/></p>


            <a class="active"  style="border-right:none;" href="<s:url action='userPreferences' />" ><u:translation key="label.Preferenze" /></a>
            <a  href="<s:url action='userAccount!input' />" ><u:translation key="label.Account" /></a>

          </div>

          <div class="contCancelAccount">
            <s:form action="userCancelAccount" method="post"  >
              <u:translation key="label.cancelAccountInfo" />

              <u:translation key="label.sicurezzaPwd"/>:
              <s:textfield name="password" />

              <s:fielderror name="password" fieldName="password" />


              <%--u:translation key="label.cancelAccountInfoCancel" /--%>
              <p>
                <s:submit value="%{getText('label.cancellaAccount')}"  cssClass="btn" cssStyle="margin-right:10px;" onclick="return confirm('Cancella irreversibilmente il tuo account?');"/>
                <a href="<s:url action="home" />" class="btn action4"><u:translation key="label._Annulla" /></a>
              </p>
              <p>
                <s:fielderror labelSeparator="<br/>"  name="futureMatchOrganized" fieldName="futureMatchOrganized" />
                <s:fielderror labelSeparator="<br/>"  name="futureMatchRegisteredOrRequest" fieldName="futureMatchRegisteredOrRequest" />
                <s:fielderror labelSeparator="<br/>"  name="pendingRelations" fieldName="pendingRelations" />
                <s:fielderror labelSeparator="<br/>"  name="hasFriends" fieldName="hasFriends" />
              </p>
            </s:form>
          </div>

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