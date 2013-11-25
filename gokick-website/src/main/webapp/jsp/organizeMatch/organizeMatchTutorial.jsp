<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body>

    <div class="wrapperOrganize">

      <!--### start header ###-->
      <jsp:include page="../../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include  page="../../jspinc/leftColumn.jsp" flush="true">
            <jsp:param name="tab" value="organize"/>
      </jsp:include>
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">

         <div class="topPageWrap">
              <jsp:include page="../../jspinc/headerTopBar.jsp" flush="true" />
          </div>  
        

        <!--### start mainContent ###-->
        <div class="mainContentIndent">
          <h1><u:translation key="label.organizeMatchTutorial.title"/></h1>
          <p class="titleList"><u:translation key="label.organizeTutorial.subtitle"/>
          </p>

          <!--### start organize menu ###-->
          <jsp:include page="../../jspinc/organizeMenu.jsp" flush="true">
            <jsp:param name="tab" value="tutorial"/>
          </jsp:include>
          <!--### end organize menu ###-->

          <u:translation key="label.organizeMatchTutorial.htmlContent"/>

        </div>
        <!--### end mainContent ###-->

      </div>
      <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->
      <jsp:include page="../../jspinc/rightcolumn.jsp" flush="true" />
      <!--### end rightcolumn ###-->

      <!--### start footer ###-->
      <jsp:include page="../../jspinc/footer.jsp" flush="true" />
      <!--### end footer ###-->

    </div>
    <!--### end wrapper ###-->
  </body>
</html>
