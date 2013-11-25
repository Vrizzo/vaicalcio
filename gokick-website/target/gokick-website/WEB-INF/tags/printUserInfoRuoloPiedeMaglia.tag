<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@attribute name="userToShow" required="true" type="it.newmedia.gokick.site.infos.UserInfo" %>

<s:if test="userToShow.playerRole!=''" >
    <u:translation key="${userToShow.playerRoleKey}"/>
    <s:if test="userToShow.playerFoot!='' || userToShow.playerShirtNumber!=''" >
        <s:property value="%{getText('label.separatore')}" />
    </s:if>
</s:if>

<s:if test="userToShow.playerFootKeyName!=''" >
    <s:property value="%{getText('label.piede')}" /> <u:translation key="${userToShow.playerFootKeyName}"/>
    <s:if test="userToShow.playerShirtNumber!=''" >
        <s:property value="%{getText('label.separatore')}" />
    </s:if>
</s:if>

<s:if test="userToShow.playerShirtNumber!=''" >
    <s:property value="%{getText('label.numero')}" /> ${userToShow.playerShirtNumber}
</s:if>
