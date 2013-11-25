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
                <div class="mainContent centred">
                    
                   <p>&nbsp;</p>

                   <p>&nbsp;</p> 
                    
                    <p>&nbsp;</p>

                   
                   <h1><u:translation key="label.registerSeiGokicker" />!</h1>

                   <p>&nbsp;</p>

                   <p><s:actionmessage /></p>

                   <p>&nbsp;</p>

                   <p>&nbsp;</p>

                   <a class="btn" href="<s:url action="home" />"><u:translation key="label.tornaLogin" /></a>

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
