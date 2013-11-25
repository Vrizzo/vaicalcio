<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title><s:property value="%{getText('page.title.default')}"/></title>
  <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../jspinc/statisticsScript.jsp" flush="true"/>
  <!--### end Google Analitics inclusion ###-->
</head>
<body>
<div class="wrapper">

  <!--### start header ###-->
  <jsp:include page="../jspinc/header.jsp" flush="true"/>
  <!--### end header ###-->

  <!--### start leftcolumn ###-->
  <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
    <jsp:param name="tab" value="register"/>
  </jsp:include>
  <!--### end leftcolumn ###-->

  <!--### start centralcolumn ###-->
  <div class="centralCol">

    <div class="topPageWrap">
      <h1 class="titleBig"><u:translation key="label.RegistratiGratis"/></h1>
    </div>

    <!--### start mainContent ###-->
    <div class="mainContent introUserWrap">


      <%--p class="stepBar">
        <span class="active">1 &bull; <u:translation key="label.Spirito" /></span>
        <span>2 &bull; <u:translation key="label.Account" /></span>
        <span>3 &bull; <u:translation key="label.picture" /></span>
      </p--%>

      <u:translation key="label.userIntroInfo"/>

      <br/><br/>

      <p class="centred">
        <a href="<s:url action="user!input" />" class="btn"><u:translation key="label.registerAccettoValori"/></a>
      </p>

      <br/>


    </div>
    <!--### end mainContent ###-->

  </div>
  <!--### end centralcolumn ###-->

  <!--### start rightcolumn ###-->
  <jsp:include page="../jspinc/rightcolumn.jsp" flush="true"/>
  <!--### end rightcolumn ###-->

  <!--### start footer ###-->
  <div class="footer">
    <p>
      <u:translation key="label.copyright"/>
    </p>
  </div>
  <u:refresher/>
  <!--### end footer ###-->

</div>
</body>
</html>
