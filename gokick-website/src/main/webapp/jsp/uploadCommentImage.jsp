<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>

    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body>
    <script type="text/javascript">
      
      window.parent.CKEDITOR.tools.callFunction(2, '<s:property value="commentImageVirtualPath" />','<s:property value="errorMessage" />');
      window.parent.CKEDITOR.tools.callFunction(1, '<s:property value="commentImageVirtualPath" />','<s:property value="errorMessage" />');
    
      
    </script>
  </body>

</html>

