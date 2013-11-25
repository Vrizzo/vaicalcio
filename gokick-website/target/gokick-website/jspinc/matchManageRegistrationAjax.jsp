<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>


<display:table   name="guiPlayerInfoList"             id="guiPlayer"  requestURI="registerToMatch.action" class="guiPlayer" >
 
  <u:displayTablePaginator />
  <s:set name="isCurrentUser">${guiPlayer.currentUser}</s:set>
  <s:set name="ownerUser">${guiPlayer.ownerUser}</s:set>
  <s:set name="isValid">${guiPlayer.valid}</s:set>
  <s:set name="playerStatus">${guiPlayer.status}</s:set>
  <s:set name="rowCount">${guiPlayer_rowNum}</s:set>
  <s:set name="idUser">${guiPlayer.userInfo.id}</s:set>
  <s:set name="guiPlayerRecordedMatches">${guiPlayer.userInfo.recordedMatches}</s:set>
  <s:set name="guiPlayerRecordedChallenges">${guiPlayer.userInfo.recordedChallenges}</s:set>
  <s:set name="guiPlayerStatus">${guiPlayer.status}</s:set>
  <s:set name="hasAge">${guiPlayer.userInfo.age>0}</s:set>
  <s:set name="userInList"><s:property value="idUserToAddList.size"/></s:set>
  <s:set name="ctrl">${rowCount==userInList + 1}</s:set>

  <s:set name="classAutocom" value=""/>
  <s:if test="#isValid=='false' && #ctrl=='true'"  >
      <s:set name="classAutocom" >classAutocom</s:set>
  </s:if>

  <display:column title=""  class="${isCurrentUser?'currentUser':''} first centred ${classAutocom}">
    ${rowCount}
  </display:column>

  <display:column title=""  class="${isCurrentUser?'currentUser ':''} ${classAutocom} image" style="width:50px;">
    <s:if test="#isValid"  >
      <s:url action="viewAvatar" var="imageUrl">
        <s:param name="idUser">
          ${guiPlayer.userInfo.id}
        </s:param>
      </s:url>
      <img src="${imageUrl}" alt="" />
      <s:checkbox id="idUserToAddList" name="idUserToAddList" fieldValue="%{#idUser}" cssStyle="display:none;" cssClass="clsCheckUserList" />
    </s:if>
  </display:column>

   
  <display:column title="" class="${isCurrentUser?'currentUser':''} ${classAutocom} ui" style="width:200px; vertical-align:top;">
    <s:if test="#isValid"  >
      <span style="display:block; padding-top:6px;"><u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false" showCurrentUserDetails="${guiPlayer.status=='Out'? 'false':'true'}" linkToDetails="true" linkPlayMorePartnerStatus="false"  /></span>
      
      <small style="color:#999999;">
      <u:translation key="${guiPlayer.userInfo.playerRoleKey}"/>
      <s:if test="#hasAge">
        ${guiPlayer.userInfo.age} <s:property value="%{getText('label.udmEta')}" />
      </s:if><br/>
      ${guiPlayer.userInfo.province}
      </small>
    </s:if>
    <s:else>
      <s:if test="#ctrl">
        <div class="ui-widget" style="margin-top:3px;">
          <input id="searchString" value="<u:translation key="label._cerca"/>..." onclick="this.value=''" type="text" style="width:200px;" />
        </div>
      </s:if>
    </s:else>
  </display:column>

  <%--s:if test="!matchDone"--%>
    <s:if test="#ownerUser && #isValid ">
      <display:column title=""  class="${isCurrentUser?'currentUser':''} delete ${classAutocom} ">
        <a onclick="javascript: removeUserAjax(${idUser});" href="javascript: void(0);">X</a>
      </display:column>
    </s:if>
    <s:else>
      <display:column title=""  class="${isCurrentUser?'currentUser':''} ${classAutocom} " >&nbsp;</display:column>
    </s:else>
  <%--/s:if--%>


</display:table>






