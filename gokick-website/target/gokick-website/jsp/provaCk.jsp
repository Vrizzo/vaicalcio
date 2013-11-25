<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>

<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <link rel="stylesheet" href="<s:url value="/css/editor.css" encode="false" />" type="text/css" media="all" />
   
    <script type="text/javascript" src="<s:url value="/Ckeditor/ckeditor.js" encode="false" />"></script>
    <script type="text/javascript">

      window.onload = function()
      {
        CKEDITOR.replace( 'comment',
        {
          customConfig : 'custom_config.js',
          filebrowserUploadUrl : 'commentUpload.action'
        });
        

      }

    </script>
  </head>
  <body>
  
              <br/><br/><br/>
              <s:textarea cssClass="noInput" name="comment" height="250" width="100%" value=""></s:textarea>
              <br/><br/><br/>                                     
             


  </body>
</html>
