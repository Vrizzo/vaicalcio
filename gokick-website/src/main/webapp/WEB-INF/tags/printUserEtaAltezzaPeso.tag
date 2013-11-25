
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@attribute name="userToShow" required="true" type="it.newmedia.gokick.site.infos.UserInfo" %>

<s:if test="userToShow.age!=''" >
    ${userToShow.age} <s:property value="%{getText('label.udmEta')}" /> 
    <s:if test="userToShow.playerHeight!='' || userToShow.playerWeight!=''" >
        <s:property value="%{getText('label.separatore')}" />
    </s:if>
</s:if>

<s:if test="userToShow.playerHeight!=''" >
    ${userToShow.playerHeight} <s:property value="%{getText('label.udmAltezza')}" /> 
    <s:if test="userToShow.playerWeight!=''" >
        <s:property value="%{getText('label.separatore')}" />
    </s:if>
</s:if>

<s:if test="userToShow.playerWeight!=''" >
    ${userToShow.playerWeight} <s:property value="%{getText('label.udmPeso')}" />
</s:if>
