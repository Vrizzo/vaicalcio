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

            <!--### start centralcolumn ###-->
            <div class="centralCol">

              <!--### start topmenu ###-->
                <p class="topMenuLoggedOut">&nbsp;</p>
              <!--### end topmenu ###-->

              <!--### start mainContent ###-->
                <div class="mainContent">
                   <h1><u:translation key="error.erroreImprevisto"/></h1>

                 <!--
                   <font color="red">
                    <s:property value="exception" /><br/>
                  </font>
                  <div>
                    <s:property value="exceptionStack" />
                  </div>
                 -->
                 
                </div>
                <!--### end mainContent ###-->

            </div>
            <!--### end centralcolumn ###-->

        </div>
    </body>
</html>
