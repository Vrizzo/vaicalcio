<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="FCK" uri="/WEB-INF/taglib/FCKeditor.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<html>
    <head>
        <jsp:include page="../jspinc/commonHead.jsp" flush="true" />

        <title><u:translation key="label.ModificaCommento"/></title>
      <!--### start Google Analitics inclusion ###-->
      <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
      <!--### end Google Analitics inclusion ###-->
    </head>
    <body class="popUpFix">
        <div class="indentCont">
            <br />
            <h1><u:translation key="label.Anteprima"/></h1>

            <br />

            <div  class="picPost">
                <s:url action="viewAvatar" var="viewAvatarURL">
                    <s:param name="idUser">${previewUserInfo.id}</s:param>
                </s:url>
                <img src="${viewAvatarURL}" alt="" />
                <br/>
                ${previewUserInfo.postCount} Post
            </div>
            <div class="infoUserPost">
                <s:url action="userDetails" var="userDetailsURL">
                    <s:param name="idUser">${previewUserInfo.id}</s:param>
                    <s:param name="tab">scheda</s:param>
                </s:url>

                <u:printUserName userInfo="${previewUserInfo}" showAvatar="false" showCurrentUserDetails="true" linkToDetails="true" />
                 <p class="datePost">${previewDate}</p>
            </div>

                <br class="clear" />
              <div class="countPost">
                &nbsp;
              </div>

              <div class="textPost">

                 <span id="commentText"></span>
</div>
                <p>&nbsp;</p>

                <a href="javascript: void(0);" onclick="window.close();" class="btn"><u:translation key="label.chiudi"/></a>

            <script type="text/javascript">
                document.getElementById('commentText').innerHTML = window.opener.getComment();

            </script>
        </div>
    </body>
</html>
