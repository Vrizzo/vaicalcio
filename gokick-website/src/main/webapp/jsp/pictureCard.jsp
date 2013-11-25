<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!--CROP FIGURINA IN REGISTRAZIONE-->

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
          <jsp:include page="../jspinc/leftColumn.jsp" flush="true" />
         <!--### end leftcolumn ###-->

            <!--### start centralcolumn ###-->
            <div class="centralCol">
                <div class="topPageWrap">
                    <h1 class="titleBig"><u:translation key="label.PictureCardCrea"/>!</h1>
                </div>
                
            <!--### start mainContent ###-->
                <div class="mainContent">
                    
                    
                    <%--
                    <p class="stepBar">
                        <span>1 &bull; <u:translation key="label.Spirito"/></span>
                        <span href="#">2 &bull; <u:translation key="label.Account"/></span>
                        <span href="#" class="active">3 &bull; <u:translation key="label.picture"/></span>
                    </p>
                    --%>
                    
                    <%--cpelli
                    <br />
                    <p>
                      <u:translation key="label.pictureCardInfoRidimensiona" />
                      (<a  class="linkUnderline" href="<s:url action="userFinish" />" ><u:translation key="label.pictureCardInfoOppureProcedi" /></a>)
                    </p>
                    --%>
                    
                    
                    
                    <div class="contFullSpace" id="idUploadedImageArea">
                        <img src="<s:url action="userViewUploadedImage" encode="false" />" alt="" id="idUploadedImage" />
                    </div>
                        <div class="rightColPrevCard">
              <p><strong><u:translation key="label.pictureCardAnteprima" /></strong></p>
                <div class="previewPictureCard">
                  <div class="contPictureCard">&nbsp;</div>
                    <s:form action="userSavePictureCard" method="post" enctype="multipart/form-data" >
                    
                        <div id="idPreviewPictureCardArea"></div>
                        
                        <s:hidden name="x1" id="x1" />
                        
                        <s:hidden name="y1" id="y1" />
                        
                        <s:hidden name="x2" id="x2" />
                        
                        <s:hidden name="y2" id="y2" />
                        
                        <s:hidden name="width" id="width" />
                        
                        <s:hidden name="height" id="height" />

                        <p>&nbsp;</p>
                        <p>&nbsp;</p>
                        
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
