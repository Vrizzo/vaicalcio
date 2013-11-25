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
                <div class="mainContent">
                  
                   <h1 class="titleBig"><u:translation key="label.CambioPassword"/></h1>

                 
                   
                   <p><u:translation key="label.changePasswordRequestInfo"/></p>
          
                   <p>&nbsp;</p>

                   <s:form action="changePasswordRequest" method="post"   >
                       <br />

                    <p>
                      <s:textfield name="email" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:200px;" />

                      <s:submit value="%{getText('label._Invia')}" cssClass="btn" />
                    </p>
                    <p><s:fielderror name="changePasswordError" value="changePasswordError" /></p>

                   </s:form>
                   
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
        <!--### end wrapper ###-->
    </body>
</html>
