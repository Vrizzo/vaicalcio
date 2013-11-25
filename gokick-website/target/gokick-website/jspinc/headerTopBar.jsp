<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<div class="clearfix topBar">
  <u:loggedIn>
    <p class="welcomeBox">
      <s:url action="userDetails" var="openPopDetailsUrl">
        <s:param name="idUser">${currentUserInfo.id}</s:param>
        <s:param name="tab">scheda</s:param>
      </s:url>
      <%-- lasciare la riga sotto tutto su una riga! --%>
      <u:translation key="message.headerTopBarCiao" />&nbsp;<b><a href="javascript: openPopupUserDetails('${openPopDetailsUrl}', '${currentUserInfo.name}');">${currentUserInfo.name}</a></b><u:translation key="message.headerTopBarBenvenuto" />
    </p>

    <div class="slideMenuWrap">

      <span class="slideMenuTitle"><u:translation key="label.Account" /></span>

      <div class="slideMenu">

        <ul>
          <li><a href="<s:url action="inviteToSite!input"/>"><u:translation key="label.invite"/></a></li>
          <li><a  onclick="return openFbLogin();" href="javascript: void(0);">Facebook Connect</a></li>
        </ul>

        <ul>
          <li>
            <a  href="<s:url action="pictureCard" method="newImage"></s:url>" ><u:translation key="btn.createPictureCard"/></a>
          </li>
          <li><a href="<s:url action="userPlayer" method="input" />"><u:translation key="label.scheda" /></a></li>
          <li><a href="<s:url action="userPreferences" method="input" />"><u:translation key="label.Preferenze" /></a></li>
          <li><a href="<s:url action="userAccount" method="input" />"><u:translation key="label.Account" /></a></li>
        </ul>
        <jsp:include page="../jspinc/headerLanguageBar.jsp" flush="true"/>

        <ul>
          <li><a href="<s:url action="logout"/>"><u:translation key="label.Logout"/></a></li>
        </ul>
      </div>
    </div>
  </u:loggedIn>
</div>
