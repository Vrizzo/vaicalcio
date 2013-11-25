<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>

<s:url action="matchComments!viewAll"  var="openerUrl" escapeAmp="yes">
  <s:param name="idMatch">${idMatch}</s:param>
  <s:param name="idSavedMessage">${idSavedMessage}</s:param>
</s:url>

<html>
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><u:translation key="label.ModificaCommento"/></title>
    <script type="text/javascript" src="<s:url value="/Ckeditor/ckeditor.js" encode="false" />"></script>
    <script type="text/javascript">

      window.onload = function()
      {
        CKEDITOR.replace( 'comment',
        {
          customConfig : 'custom_config.js',
          filebrowserUploadUrl : 'commentUpload.action?idUser=${userContext.user.id}&idMatch=${idMatch}'
        });
      }

    </script>
    <style type="text/css">input[type='text']{border:none; background:none;}</style>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class="popUpFix">
    
  <div class="indentCont" style="margin-right:20px;">
    <s:if test="close == true">
      <script type="text/javascript">
        window.opener.location.href = '${openerUrl}';
        //window.opener.location.reload();
        window.close();
      </script>
    </s:if>
    <br />
    <h1><u:translation key="label.ModificaCommento"/></h1>

    <p><s:actionerror /></p>

    <s:form action="matchComments!update" method="post">

      <span style="display: none;visibility:collapse">
        <s:hidden name="idMatchComment" />
        <s:hidden name="idSavedMessage">${idSavedMessage}</s:hidden>
        <s:hidden name="idMatch">${idMatch}</s:hidden>
      </span>

      <s:textarea name="comment" height="250" width="100%" value="%{comment}"></s:textarea>

      <p align="right" style="padding-right:20px;">
        <s:submit cssClass="btn" value="%{getText('label.Aggiorna')}" />
      </p>

    </s:form>
  </div>
  </body>
</html>
