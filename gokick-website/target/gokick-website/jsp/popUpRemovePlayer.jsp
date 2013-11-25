<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>

    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><u:translation key="page.title.default" /></title>
    <script type="text/javascript">

     function removePlayer(missing)
     {
       window.opener.removeRegisteredPlayer(${idPlayerToRemove},${idMatch},missing);
       window.close();
     }

    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class="popUpFix">
  
        <p>
        &nbsp;
        </p>

        <!--### start mainContent ###-->
        <div class="mainContentIndent centred" style="border:none;">

          <p>
            <%--nome: ${namePlayerToRemove} <br/>
            idPlayer to remove: ${idPlayerToRemove} <br/>
            idMatch: ${idMatch}--%>
          </p>

          <s:set name="name" >
            <s:property value="namePlayerToRemove" escapeJavaScript="true" />
          </s:set>

          <p>
            <u:translationArgs key="label.popUpRemoveInfo" arg01="${name}"  />
          </p>
          
          <s:hidden name="idPlayerToRemove"/>
          <s:hidden name="idMatch"/>

          <br/><br/>

         <p>
            <s:url action="userAccount" var="registerFriendToMatchUrl" namespace="" method="update"></s:url>           
            <a href="javascript: void(0);" class="btn" onclick="removePlayer(false)" ><u:translation key="label.buttonRimuoviNoAff"/></a> &nbsp;
            <a href="javascript: void(0);" class="btn" onclick="removePlayer(true)" ><u:translation key="label.buttonRimuoviPerdiAff"/></a> &nbsp;
         </p>
         <br/><br/>
         <p>
            <a href="javascript: void(0);"  onclick="window.close()"  class="btn action2" ><u:translation key="label._Annulla"/></a> &nbsp;
         </p>

        </div>

  </body>
</html>
