<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><u:translation key="page.title.default" /></title>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body>
  <s:set name="contentName">${param["content"]}</s:set>
  <div class="wrapper">

      <!--### start header ###-->
      <jsp:include page="../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
        <jsp:param name="tab" value="${contentName}"/>
      </jsp:include>
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">
        <div class="topPageWrap">
          <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
        </div>

        <!--### start mainContent ###-->
        <div class="mainContent">

          <div class="indentCont">

            <u:translation key="text.content.${contentName}" />

          </div>


        </div>
        <!--### end mainContent ###-->

      </div>
      <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->
      <jsp:include page="../jspinc/rightcolumn.jsp" flush="true" />
      <!--### end rightcolumn ###-->

      <!--### start footer ###-->
      <s:if test="#contentName=='history'">
        <jsp:include page="../jspinc/footerHome.jsp" flush="true" />
      </s:if>
      <s:else>
        <jsp:include page="../jspinc/footer.jsp" flush="true" />
       </s:else>
      <!--### end footer ###-->
     

    </div>

  </body>
</html>
