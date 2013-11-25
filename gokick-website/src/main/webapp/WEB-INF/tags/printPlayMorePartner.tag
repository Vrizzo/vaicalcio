<%@tag description="stampo lo stato dell'utente rispetto all'associazione PlayMore" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@attribute name="playMorePartner" required="true" type="it.newmedia.gokick.data.hibernate.beans.VPlayMorePartner" %>
<%@ attribute name="show" %>
<%@ attribute name="link" %>
<c:set var="showInternal" value="${empty show ? true : show}" />
<c:set var="linkInternal" value="${empty link ? true : link}" />
<c:if test="${showInternal and (playMorePartner.superstar or playMorePartner.supporter)}">
  <c:set var="playMoreStatusKey" value="${playMorePartner.superstar ? 'superstar' : ''}${playMorePartner.supporter ? 'supporter' : ''}-${playMorePartner.status}" />
  <c:set var="titlePlayMoreStatus"><u:translationArgs key="label.title.${playMoreStatusKey}" arg01="${playMorePartner.distance <0 ? 30+playMorePartner.distance : playMorePartner.distance}" /></c:set>
  <c:set var="hrefPlayMoreRenew"><u:translationArgs key="label.playMore.renewLink" /></c:set>
  <c:if test="${linkInternal}">
    <a href="${hrefPlayMoreRenew}" title="${titlePlayMoreStatus}" style="text-decoration: none;">
      <img  src="<s:url value="/images/playmorestatus" encode="false" />/${playMoreStatusKey}.gif" alt="${titlePlayMoreStatus}" style="vertical-align: middle; margin-left: 4px;"/>
    </a>
  </c:if>
  <c:if test="${linkInternal eq false}">
    <img  src="<s:url value="/images/playmorestatus" encode="false" />/${playMoreStatusKey}.gif" alt="${titlePlayMoreStatus}" title="${titlePlayMoreStatus}" style="vertical-align: middle; margin-left: 4px;"/>
  </c:if>

</c:if>
