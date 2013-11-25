<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><s:property value="%{getText('page.title.default')}" /></title>
      <!--### start Google Analitics inclusion ###-->
      <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
      <!--### end Google Analitics inclusion ###-->
    </head>
    <body>
        <s:if test="close == true">
          <script>
              window.opener.location.reload();
              window.close();
          </script>
        </s:if>
        
        <div class="">
          <u:translation key="text.accettazionePrivacy"/>
        </div>

    </body>
</html>
