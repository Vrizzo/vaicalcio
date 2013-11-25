<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body>
     
    <div class="">
      <s:set var="contentName">${param["content"]}</s:set>
      
      <s:set var="abc">${param["abc"]}</s:set>
      <s:set name="viewCloseButton">${contentName!='termsAndConditions' && contentName!='accettazionePrivacy' }</s:set>
      <u:translation key="text.content.${contentName}" lang="${param['lang']}"/>
   
      <s:if test="#viewCloseButton">
          <p class="sentBtn">
            <a href="javascript: window.close();" class="btn" >
              <u:translation key="label.chiudi"/>
            </a>
          </p>
      </s:if>

   </div>


  </body>
</html>
