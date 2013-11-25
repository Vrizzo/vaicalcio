<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<!--CROP FIGURINA DA SCHEDA-->
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
      <title><s:property value="%{getText('page.title.default')}" /></title>
      <script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/dragdrop.js" encode="false" />" ></script>
      <script type="text/javascript" src="<s:url value="/js/cropper-js-1.2.0/cropper.js"/>" encode="false" ></script>
      <script type="text/javascript" src="<s:url value="/js/cropper-js-1.2.0/customcropper.js"/>" encode="false" ></script>
      <link rel="stylesheet" href="<s:url value="/css/cropper.css"/>" type="text/css" media="all" />
      <!--### start Google Analitics inclusion ###-->
      <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
      <!--### end Google Analitics inclusion ###-->
    </head>
    <body>
        
        <div class="wrapper wideWrapper">
            
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
                    
                     <%--cpelli
                    <p class="centred light">
                      <u:translation key="label.pictureCardInfoRidimensiona"/>
                    </p>--%>
                    
                    <div class="contFullSpace" id="idUploadedImageArea">
                      <img  src="<s:url action="userViewUploadedImage" encode="false" />" alt="" id="idUploadedImage" />
                    </div>
                      
                    
                    
                      <div class="rightColPrevCard">
              <p><strong><u:translation key="label.pictureCardAnteprima" /></strong></p>
                <div class="previewPictureCard">
                  <div class="contPictureCard">&nbsp;</div>
                    <s:form action="pictureCard!saveImage" method="post" enctype="multipart/form-data" >
                    
                        <div id="idPreviewPictureCardArea"></div>
                        
                        <s:hidden name="x1" id="x1" />
                        
                        <s:hidden name="y1" id="y1" />
                        
                        <s:hidden name="x2" id="x2" />
                        
                        <s:hidden name="y2" id="y2" />
                        
                        <s:hidden name="width" id="width" />
                        
                        <s:hidden name="height" id="height" />

                        <p>&nbsp;</p><p>&nbsp;</p>
                        
                        <p style="text-align: center;">
                            <s:submit cssClass="btn" value="%{getText('btn.save')}" />
                        </p>

                    </s:form>     
              </div>
           </div> 
                    
                    
                    <br class="clear" />
                    
                </div>
                <!--### end mainContent ###-->
                
            </div>
            <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->

          
            <!--### end rightcolumn ###-->

      <!--### start footer ###-->
        <jsp:include page="../jspinc/footer.jsp" flush="true" />
      <!--### end footer ###-->

        </div>
    </body>
</html>
