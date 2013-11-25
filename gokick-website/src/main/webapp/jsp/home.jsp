<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
  <title><s:property value="%{getText('page.title.home')}"/></title>
  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
  <!--### end Google Analitics inclusion ###-->
</head>
<body>

<div class="wrapperHP">

  <!--### start header ###-->
  <jsp:include page="../jspinc/header.jsp" flush="true"/>
  <!--### end header ###-->

  <!--### start leftcolumn ###-->
  <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
    <jsp:param name="tab" value="home"/>
  </jsp:include>
  <!--### end leftcolumn ###-->

  <!--### start centralcolumn ###-->
  <div class="centralCol">

    <div class="topPageWrap">

      <s:form action="login" method="post">
        <table class="loginForm">
          <tr>
            <td class="">
              <s:property value="%{getText('label.email')}"/>
            </td>
            <td>
              <s:textfield value="" name="email" size="20" cssStyle="border:solid 1px #cccccc; background:#FFFFFF; width:155px;"/>
            </td>
            <td rowspan="2">&nbsp;</td>
            <td class="">
              <s:property value="%{getText('label.password')}"/>
            </td>
            <td>
              <s:password value="" name="password" size="19" cssStyle="border:solid 1px #cccccc; background:#FFFFFF; width:85px;"/>
                <%--<s:textfield  value="Password"   size="19" cssStyle="border:solid 1px #999999; background:#FFFFFF;" onclick="showHidePwd" display:none; /--%>
            </td>
            <td rowspan="2">&nbsp;</td>
            <td>
              <s:submit value="Login" cssClass="btn"></s:submit>
              <a onclick="return openFbLogin();" href="javascript: void(0);"><img src="<s:url value="/images/btn_fb_connect.png" encode="false" />" alt="Connect on Facebook"/></a>
                <%--
                <u:showForCobrands codes="GOKICK">
                  <a href="/facebookLogin.action"><img src="<s:url value="/images/btn_fb_connect.png" encode="false" />" alt="Connect on Facebook"/></a>
                </u:showForCobrands>
                --%>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td style="padding-bottom:0px;">
              <small><s:checkbox name="rememberMe"/><label for="login_rememberMe"><u:translation key="label.homeRicordaLogin"/></label></small>
            </td>
            <td>&nbsp;</td>
            <td colspan="3" style="padding-bottom:0px;">
              <small>
                <a href="<s:url action="changePasswordRequest!input" />"><u:translation key="label.passwordDimenticata"/></a>
              </small>
            </td>
          </tr>
        </table>
        <br/>
      </s:form>
      <div class="errorPanelHP">
        <small><s:fielderror name="loginError" value="loginError"/></small>
      </div>
    </div>

    <!--### start mainContent ###-->
    <div class="mainContent mainHP">


      <u:translation key="label.homeNotLogCentral"/>

    </div>
    <!--### end mainContent ###-->


  </div>
  <!--### end centralcolumn ###-->

  <!--### start rightcolumn ###-->
  <u:showForCobrandTypes types="Complete">
    <div class="rightColHPnotLog">
      <u:translation key="label.homeNotLogExtRight"/>
    </div>
  </u:showForCobrandTypes>
  <!--### end rightcolumn ###-->

  <!--### start footer ###-->
  <jsp:include page="../jspinc/footerHome.jsp" flush="true"/>
  <!--### end footer ###-->

</div>

</body>
</html>
