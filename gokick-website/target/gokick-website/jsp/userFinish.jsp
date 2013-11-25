<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
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
          <jsp:include page="../jspinc/leftColumn.jsp" flush="true" >
            <jsp:param name="tab" value="register"/>
          </jsp:include>
          <!--### end leftcolumn ###-->

            <!--### start centralcolumn ###-->
            <div class="centralCol">

                <div class="topPageWrap">
                    &nbsp;
                </div>

              <!--### start mainContent ###-->
                <div class="mainContent registerFinish" style="text-align:center;">

                   <h1><u:translation key="label.PlayMore" />!</h1>
           
                   <p><u:translation key="label.registerInfoRiceveraiMail" /></p>
                 
                   <s:url action="viewPictureCard" var="imageUrl">
                      <s:param name="idUser"><s:property value="currentUserInfo.id" /></s:param>
                    </s:url>
                   <img src="${imageUrl}" alt="<s:property value="currentUserInfo.firstLastName" />" />
                                      
                   <br />

                   <a class="btn" href="<s:url action="home" />"><u:translation key="label.tornaLogin" /></a>

                </div>
                <!--### end mainContent ###-->

            </div>
            <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->
        <jsp:include page="../jspinc/rightcolumn.jsp" flush="true" />
      <!--### end rightcolumn ###-->

      <!--### start footer ###-->
      <div class="footer">
        <p>
          <u:translation key="label.copyright"/>
        </p>
      </div>
      <u:refresher />
      <!--### end footer ###-->

        </div>
    </body>
</html>
