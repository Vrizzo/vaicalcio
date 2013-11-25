<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<!--SCELTA FIGURINA DA SCHEDA-->
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <script type="text/javascript">
  JQ(document).ready(function(){
       JQ('form .btn').click( function(){
           JQ('form').append('<img src="images/spinnerFF.gif" class="picSpinner" alt="wait" />');
       });
  });

</script>
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
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
          <jsp:param name="tab" value="scheda"/>
      </jsp:include>
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">

          <div class="topPageWrap">
              <u:loggedIn>
              <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
              </u:loggedIn>
              <u:loggedOut>
                  <h1 class="titleBig"><u:translation key="label.scegliFoto" /></h1>
              </u:loggedOut>
          </div>  
         

        <!--### start mainContent ###-->
        <div class="mainContent">


          <div class="rightContInt light">
              <u:loggedIn>
              <h1 class="titleBig"><u:translation key="label.scegliFoto" /></h1>
</u:loggedIn>
            <p>
              <u:translation key="label.PictureCardInfo1"/>
            </p>
            <p><img src="images/prima.jpg" alt="Crea la tua figurina" /></p>
            <p><u:translation key="label.PictureCardInfo2"/></p>
          </div>

          <br class="clear" />
        
          <div class="leftContInt">
            <h2 class="subTitleEv" style="margin-top:40px;"><u:translation key="label.picture" /></h2>
          </div>
          
          <div class="rightContInt">
            <s:form action="pictureCard!chooseImage" method="post" enctype="multipart/form-data" >
              <p>
              <s:file name="pictureCardFile" />
              <s:submit cssClass="btn" value="%{getText('btn.createPictureCard')}" />
              <span class="light">(max 5 MB)</span>
              </p>
            </s:form>
            <s:fielderror />

          </div>
          <br class="clear" />
        
          <div class="rightContInt light">
              <u:translationArgs key="label.RegisterPictureCardInfo" arg01=""/>
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
      <jsp:include page="../jspinc/footer.jsp" flush="true" />
      <!--### end footer ###-->

    </div>
  </body>
</html>
