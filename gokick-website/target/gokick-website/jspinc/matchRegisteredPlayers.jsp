<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>

<s:set name="rowCount" value="0"></s:set>





<display:table   name="requestScope.playerRegisteredList" id="guiPlayer"  class="guiPlayer">

   <u:displayTablePaginator />
      
  <s:set name="isCurrentUser">${guiPlayer.currentUser}</s:set>
  <s:set name="isOwnerUser">${guiPlayer.ownerUser}</s:set>
  <s:set name="isValid">${guiPlayer.valid}</s:set>
  <s:set name="mobile">${guiPlayer.mobile}</s:set>
  <s:set name="ownerMobile">${userOwnerMobile}</s:set>
  <s:set name="playerStatus">${guiPlayer.status}</s:set>
  <s:set name="titleIscritti"><u:translation key="label.Iscritti"/></s:set>
  <s:set name="titleRole"><u:translation key="label.roleAbbr"/></s:set>
  <s:set name="titleEta"><u:translation key="label.Eta"/></s:set>
  
  <display:caption >
      <thead>
          <th colspan="" class="caption"><strong>&nbsp;</strong></th>
          <th colspan="2" class="caption"><strong>${titleIscritti}</strong></th>
          <th colspan="" class="caption"><strong>${titleRole}</strong></th>
          <th colspan="2" class="caption"><strong>${titleEta}</strong></th>
      </thead>
  </display:caption>
 
  

  <display:column title=""  class="${isCurrentUser?'currentUser':''} first centred">
    <s:property  value="#rowCount =#rowCount + 1" />
  </display:column>

  <display:column title=""  class="${isCurrentUser?'currentUser':''}">
    <s:set name="guiPlayerId">${guiPlayer.userInfo.id}</s:set>
    <s:set name="guiPlayerRecordedMatches">${guiPlayer.userInfo.recordedMatches}</s:set>
    <s:set name="guiPlayerRecordedChallenges">${guiPlayer.userInfo.recordedChallenges}</s:set>
    <s:set name="guiPlayerStatus">${guiPlayer.status}</s:set>
    <s:if test="#isValid"  >
      <s:url action="viewAvatar" var="imageUrl">
        <s:param name="idUser">
          ${guiPlayer.userInfo.id}
        </s:param>
      </s:url>
      <img src="${imageUrl}" alt="" />
    </s:if>
  </display:column>

  <display:column title="" class="${isCurrentUser?'currentUser':''}">
    <s:if test="#isValid"  >
      <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false" showCurrentUserDetails="${guiPlayer.status=='Out'? 'false':'true'}" linkToDetails="true"  />
      <br/>
      <s:if test="#isOwnerUser">
        <s:if test="#ownerMobile!='' ">
          <small class="light">cell. ${userOwnerMobile}</small>
        </s:if>
      </s:if>
      <s:elseif test="#mobile!=''">
        <s:if test="userOwner">
          <small class="light">cell. ${guiPlayer.mobile}</small>
        </s:if>
        <s:elseif  test="#isCurrentUser">
          <small class="light">cell. ${guiPlayer.mobile}</small>
        </s:elseif>
      </s:elseif>
    </s:if>

    <s:if test="!matchDone">
      <%--s:if test="#rowCount == (maxPlayers + 1) - playersToAdd">
        <s:url action="registerFriendToMatch" var="registerFriendToMatchUrl" namespace="" method="input">
          <s:param name="idMatch">${idMatch}</s:param>
        </s:url>
        <s:if test="userOwner && playersToAdd > 0">
          <a class="light" id="idLinkIscrivi" href="javascript: openPopupRegisterFriendToMatch('${registerFriendToMatchUrl}'); "><u:translation key="label.iscriviGokickers"/> &raquo;</a>
        </s:if>
      </s:if--%>
    </s:if>
  </display:column>

  <display:column title=""  class="${isCurrentUser?'currentUser':''}" style="width:20px;">
        <c:if test="${guiPlayer.userInfo!=null}">
          <s:set name="ruoloLang" >${guiPlayer.role}</s:set>
          ${fn:substring(ruoloLang, 0, 3)}
        </c:if>
  </display:column>

  <display:column title=""  class="${isCurrentUser?'currentUser':''} centred" headerClass="centred" style="width:20px;">
        ${guiPlayer.userInfo.age}  
  </display:column>

   
  <s:if test="!matchDone">
    <s:if test="userOwner && #isValid ">    
        <display:column title=""  class="${isCurrentUser?'currentUser':''} delete">

        <s:if test="#playerStatus=='Out'">
          <a onclick="removeRegisteredOutPlayer(${guiPlayer.id});" href="javascript: void(0);">X</a>
        </s:if>
        <s:else>
          <s:url action="matchRegisteredPlayers" var="openPopUpUrl" namespace="/ajax" method="openPopUp"  >
            <s:param name="namePlayerToRemove">${guiPlayer.userInfo.name} ${guiPlayer.userInfo.surname}</s:param>
            <s:param name="idPlayerToRemove">${guiPlayer.id}</s:param>
            <s:param name="idMatch">${idMatch}</s:param>
          </s:url>
          <a onclick="openPopupRemovePlayers('${openPopUpUrl}');" href="javascript: void(0);">X</a>
          <%--a onclick="removeRegisteredPlayer(${guiPlayer.id},'${guiPlayer.status}' );" href="javascript: void(0);">X</a--%>
        </s:else>
      </display:column>
    </s:if>
    <s:else>
            <display:column title=""  class="${isCurrentUser?'currentUser':''}" >&nbsp;</display:column>
   </s:else>
  </s:if>

</display:table>

<br />
<s:url action="registerFriendToMatch" var="registerFriendToMatchUrl" namespace="" method="input">
  <s:param name="idMatch">${idMatch}</s:param>
</s:url>

<p class="placesLeft"><u:translation key="label.math.postiRimasti" />: <s:property value="playersToAdd" /></p>
<%--
<s:if test="userOwner && playersToAdd > 0">
    <a href="javascript: openPopupRegisterFriendToMatch('${registerFriendToMatchUrl}'); " class="btn"><u:translation key="label.iscriviGokickers"/></a>
</s:if>
--%>









