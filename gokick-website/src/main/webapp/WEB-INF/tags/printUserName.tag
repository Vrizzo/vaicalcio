<%@tag description="put the tag description here" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@attribute name="userInfo" required="true" type="it.newmedia.gokick.site.infos.UserInfo" %>
<%@attribute name="linkToDetails" %>
<%@attribute name="showCurrentUserDetails" description="Se è true mette il link anche nel caso in cui l'utente stampato sia l'utente corrente (loggato)" %>
<%@attribute name="showAvatar" %>
<%@attribute name="printName" %>
<%@attribute name="printSurname" %>
<%@attribute name="printPlayMorePartnerStatus" %>
<%@attribute name="linkPlayMorePartnerStatus" %>

<c:if test="${userInfo.status eq 'Cancelled'}">
  <c:set var="linkToDetailsInternal" value="false" />
</c:if>

<c:if test="${userInfo.status eq 'Deleted'}">
  <c:set var="linkToDetailsInternal" value="false" />
  <c:set var="linkToDetails" value="false" />
</c:if>

<c:if test="${userInfo.status eq 'Enabled' || userInfo.status eq null}">
  <c:set var="linkToDetailsInternal" value="${showCurrentUserDetails == 'false' ? 'false' : (linkToDetails=='false' ? 'false' : 'true')}" />
</c:if>

<c:set var="showAvatar" value="${showAvatar==null ? false : showAvatar}" />
<c:set var="printNameInternal" value="${printName == 'false' ? 'false' : (printName=='true' ? 'true' : 'true')}" />
<c:set var="printSurnameInternal" value="${printSurname == 'false' ? 'false' : (printSurname=='true' ? 'true' : 'true')}" />

<c:if test="${showAvatar eq 'true' }">
  <s:url action="viewAvatar" var="viewAvatarURL">
    <s:param name="idUser">${userInfo.id}</s:param>
  </s:url>
  <img src="${viewAvatarURL}" alt="" />
</c:if>

<c:if test="${linkToDetailsInternal eq 'true' }">
  <s:url action="userDetails" var="userDetailsURL">
    <s:param name="idUser">${userInfo.id}</s:param>
    <s:param name="tab">scheda</s:param>
  </s:url>
    <s:set name="completeName" >${userInfo.name}_${userInfo.surname}</s:set>
    <s:set name="nameDet" >
      <s:property value="#completeName" escapeJavaScript="true" />
    </s:set>
  <a  href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');">
    <c:if test="${printNameInternal eq 'true'}">${userInfo.name}</c:if>
    <c:if test="${printNameInternal eq 'true' && printSurnameInternal eq 'true'}">&nbsp;</c:if>
    <c:if test="${printSurnameInternal eq 'true'}">${userInfo.surname}</c:if></a>
    <c:if test="${printSurnameInternal eq 'true'}">
      <u:printMatchesChallenges userInfo="${userInfo}" />
      <u:printPlayMorePartner playMorePartner="${userInfo.playMorePartner}" show="${printPlayMorePartnerStatus}" link="${linkPlayMorePartnerStatus}" />
    </c:if>
</c:if>

<c:if test="${linkToDetailsInternal eq 'false'}">
  <c:if test="${printNameInternal eq 'true'}">${userInfo.name}</c:if>
  <c:if test="${printNameInternal eq 'true' && printSurnameInternal eq 'true'}">&nbsp;</c:if>
  <c:if test="${printSurnameInternal eq 'true'}">${userInfo.surname}</c:if>
    
  <c:if test="${userInfo.status != 'Cancelled' && userInfo.status != 'Deleted'}">
    <u:printMatchesChallenges userInfo="${userInfo}" />
    <u:printPlayMorePartner playMorePartner="${userInfo.playMorePartner}" show="${printPlayMorePartnerStatus}" link="${linkPlayMorePartnerStatus}" />
  </c:if>
          
</c:if>

         