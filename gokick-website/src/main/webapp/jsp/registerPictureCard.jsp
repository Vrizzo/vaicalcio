<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="errorPopup.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!--SCELTA FIGURINA IN REGISTRAZIONE-->

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
              <h1 class="titleBig"><u:translation key="label.scegliFoto" /></h1>
          </div>

        <!--### start mainContent ###-->
        <div class="mainContent">

          

          <%--p class="stepBar">
            <span>1 &bull; <u:translation key="label.Spirito" /></span>
            <span>2 &bull; <u:translation key="label.Account" /></span>
            <span class="active">3 &bull; <u:translation key="label.picture" /></span>
          </p--%>

          <div class="rightContInt light">
            <p>
              <u:translation key="label.PictureCardInfo1"/>
            </p>
            <p><img src="images/prima.jpg" alt="Crea la tua figurina" /></p>
            <p><u:translation key="label.PictureCardInfo2"/></p>
          </div>

          <br class="clear" />

          <div class="leftContInt">
            <h2 class="subTitleEv"><u:translation key="label.picture" /></h2>
          </div>

          <div class="rightContInt">
            <s:form action="userUploadPictureCard" method="post" enctype="multipart/form-data" >
              <s:file name="pictureCardFile"  />
              <s:submit cssClass="btn" value="%{getText('btn.createPictureCard')}" />
              (max 5 MB)
            </s:form>
            <s:fielderror />
          </div>
          <br class="clear" />
            
          <div class="rightContInt light">
              <s:set name="argInfo"><a class="linkUnderline" class="light" href="<s:url action="userFinish" />"><u:translation key="label.registerConcludiNoFoto" /></a></s:set>
              <u:translationArgs key="label.RegisterPictureCardInfoNota" />
              ${argInfo}
              <u:translationArgs key="label.RegisterPictureCardInfoEvita" />
            <p>&nbsp;</p>
          </div>
          
          <br class="clear" />
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

