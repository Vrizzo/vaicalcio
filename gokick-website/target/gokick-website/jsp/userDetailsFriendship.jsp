<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>


<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all" />
    <title><s:property value="%{getText('page.title.default')}" /></title>

    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class="userDetails popUpFix">

    <p class="topMenuLoggedIn">&nbsp;</p>

    <div class="content">

      
      <div class="friendShipBox" >

        <h1><u:translation key="label.richiediAmicizia"/> <u:translation key="label.a_"/> ${nameSurName}?</h1>

      <p class="light"><u:translation key="label.info.richiediAmicizia"/></p>
                        
      <p>
        <s:url action="inviteUser" var="inviteUser">
          <s:param name="idUser">${idUser}</s:param>
        </s:url>
        <a class="btn" id="idInviteLink" href="${inviteUser}"><u:translation key="label.Si"/></a>
        &nbsp;
        <a class="btn action2" href="javascript:history.back()"  ><u:translation key="label.No"/></a>
      </p>
                
     

      </div>

    
    </div>

  </body>
</html>
