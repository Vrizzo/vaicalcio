<%@ tag import="it.newmedia.gokick.site.infos.UserInfo" %>
<%@ tag import="it.newmedia.gokick.site.providers.InfoProvider" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="user" required="true" type="it.newmedia.gokick.data.hibernate.beans.User" %>
<%@attribute name="linkToDetails" type="java.lang.Boolean" %>
<%@attribute name="showCurrentUserDetails" %>
<%@attribute name="showAvatar" type="java.lang.Boolean" %>
<%@attribute name="justAvatar" type="java.lang.Boolean" %>
<%@attribute name="displayVertical" type="java.lang.Boolean" %>
<%@attribute name="vert" type="java.lang.Boolean" %>
<%
  UserInfo userForPlayMorePartner = InfoProvider.getUserInfo(user.getId());
  jspContext.setAttribute("userForPlayMorePartner", userForPlayMorePartner);
%>
<c:choose>
  <c:when  test="${user.anonymousEnabled}">
    <s:set name="lastName" >${fn:substring(user.lastName, 0, 1)}.</s:set>
  </c:when>
  <c:otherwise>
    <s:set name="lastName" >${user.lastName}</s:set>
  </c:otherwise>
</c:choose>

<s:set name="completeName" >${user.firstName}_${lastName}</s:set>
<s:set name="nameDet" >   
      <s:property value="#completeName" escapeJavaScript="true" />
</s:set>


<c:if test="${user.userStatus eq 'CANCELLED'}">
  <c:set var="linkToDetails" value="false" />
</c:if>

<c:if test="${user.userStatus eq 'DELETED'}">
  <c:set var="linkToDetails" value="false" />
</c:if>

<c:if test="${user.userStatus eq 'ENABLED'}">
  <c:set var="linkToDetails" value="${showCurrentUserDetails == false ? false : linkToDetails==null ? true : linkToDetails}" />
</c:if>



<c:set var="showAvatar" value="${showAvatar==null ? false : showAvatar}" />

<c:if test="${vert eq true }" >
          <s:url action="userDetails" var="userDetailsURL">
            <s:param name="idUser">${user.id}</s:param>
            <s:param name="tab">scheda</s:param>
          </s:url>
          <a  href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');">${user.firstName} <br> ${lastName}</a>
          <c:if test="${user.userStatus != 'CANCELLED' && user.userStatus != 'DELETED'}">
            <c:if test="${user.recordedMatches > 0}">
                <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');" title="${user.recordedMatches}&nbsp;<u:translation key="label.title.partiteOrganizzate"/>"><span class="matchPlay">${user.recordedMatches}</span></a>
              </c:if>
              <c:if test="${user.recordedChallenges > 0 && user.recordedMatches > 0}">
                +
              </c:if>
              <c:if test="${user.recordedChallenges > 0}">
                <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');"><span class="matchOrg">${user.recordedChallenges}</span></a>
              </c:if>
          </c:if>
          <u:printPlayMorePartner playMorePartner="${userForPlayMorePartner.playMorePartner}"  />

</c:if>

<c:if test="${displayVertical eq false }">
  <c:if test="${showAvatar eq true }">
    <s:url action="viewAvatar" var="viewAvatarURL">
      <s:param name="idUser">${user.id}</s:param>
    </s:url>
    <s:url action="userDetails" var="userDetailsURL">
      <s:param name="idUser">${user.id}</s:param>
      <s:param name="tab">scheda</s:param>
    </s:url>
    <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');"><img src="${viewAvatarURL}" alt="" /></a>
  </c:if>

  <c:if test="${linkToDetails eq true }">
    <s:url action="userDetails" var="userDetailsURL">
      <s:param name="idUser">${user.id}</s:param>
      <s:param name="tab">scheda</s:param>
    </s:url>
    <a  href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');">${user.firstName}&nbsp;${lastName}</a>
    <c:if test="${user.recordedMatches > 0}">
      <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');" title="${user.recordedMatches}&nbsp;<u:translation key="label.title.partiteOrganizzate"/>"><span class="matchPlay">${user.recordedMatches}</span></a>
    </c:if>
    <c:if test="${user.recordedChallenges > 0 && user.recordedMatches > 0}">
      +
    </c:if>
    <c:if test="${user.recordedChallenges > 0}">
      <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');"></a><span class="matchOrg">${user.recordedChallenges}</span>
    </c:if>
    <u:printPlayMorePartner playMorePartner="${userForPlayMorePartner.playMorePartner}"  />
  </c:if>

  <c:if test="${linkToDetails eq false}">
    ${user.firstName}&nbsp;${lastName}
    
    <c:if test="${user.userStatus != 'CANCELLED' && user.userStatus != 'DELETED'}">
        <c:if test="${user.recordedMatches > 0}">
          <span class="matchPlay" title="${user.recordedMatches}&nbsp;<u:translation key="label.title.partiteOrganizzate"/>">${user.recordedMatches}</span>
        </c:if>
        <c:if test="${user.recordedChallenges > 0 && user.recordedMatches > 0}">
          +
        </c:if>
        <c:if test="${user.recordedChallenges > 0}">
          <span class="matchOrg">${user.recordedChallenges}</span>
        </c:if>
      <u:printPlayMorePartner playMorePartner="${userForPlayMorePartner.playMorePartner}"  />
    </c:if>
  </c:if>
</c:if>
      
<c:if test="${displayVertical eq true }">
  <c:if test="${justAvatar eq true }">
    <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');"><img src="${viewAvatarURL}" alt="" /></a>
  </c:if>
</c:if>

   