<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>


<s:if test="selfUser==true && tab!='amici'" >
  
    <s:url action="userPlayer" var="userPlayerUrl" method="input" >
      <s:param name="idUser"><s:property value="userToShow.id" /></s:param>
    </s:url>
    <a class="btn" onclick="window.close();window.opener.location.href='${userPlayerUrl}'" href="#" ><u:translation key="label.modify"  /></a>
  
</s:if>

<s:elseif test="invite == true">
  
    <s:url action="inviteUserBox" var="inviteUserBox">
      <s:param name="idUser"><s:property value="userToShow.id" /></s:param>
    </s:url>
    <a class="btn" href="${inviteUserBox}"><s:property value="%{getText('label.richiediAmicizia')}" /></a>
  
</s:elseif>
  
<s:else>
 
    <s:property value="relationshipStatusMessage" />
  
</s:else>

    <s:if test="selfUser==false && tab=='scheda'" >
    <span class="right">
      <s:url action="userDetails" var="abuseUrl">
              <s:param name="idUser" value="idUser"/>
              <s:param name="tab" >abuse</s:param>
      </s:url>
              <a class="light" href="${abuseUrl}"><u:translation key="label.scheda.segnala.abuso"/></a>
    </span>
</s:if>