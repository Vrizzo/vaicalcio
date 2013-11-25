<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@attribute name="player" required="true" type="it.newmedia.gokick.data.hibernate.beans.Player" %>
<%@attribute name="showAvatar" type="java.lang.Boolean" %>
<c:set var="showAvatar" value="${showAvatar==null ? false : showAvatar}" />
<c:if test="${showAvatar eq true }">
  <s:url action="viewAvatar" var="viewAvatarURL">
    <s:param name="idUser">-1</s:param>
  </s:url>
  <img src="${viewAvatarURL}" alt="" />
</c:if>
${player.outFirstName} ${player.outLastName}