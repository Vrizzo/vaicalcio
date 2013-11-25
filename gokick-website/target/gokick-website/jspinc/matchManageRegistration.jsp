<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"  %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>

    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />

<script type="text/javascript">
  JQ(function() {
    JQ( "#searchString" ).autocomplete({
      select: function(event,ui) {addUserAjax(ui.item.id)},

      source: function( request, response ) {
        JQ.ajax({
          url: "<s:url action="usersAutocom" namespace="/ajax"/>",
          dataType: "json",
          data: {
            searchString: request.term,
            idMatch: ${idMatch}
          },

          success: function( data ) {
            response( JQ.map( data.userInfoList, function( item ) {
              return {
                label: item.name + " "  +item.surname ,
                value: "",
                id: item.id,
                role: item.playerRole,
                age: item.age,
                province: item.province
              }
            }));
          }
        });
      },
      minLength: 2
    })
    .data( "autocomplete" )._renderItem = function( ul, item ) {
      return JQ( "<li></li>" )
      .data( "item.autocomplete", item )
      .append("<a><table>\n\
                     <tr>\n\
                        <td>\n\
                             <img src='viewAvatar.action?idUser=" + item.id +"' alt='' />\n\
                        </td>\n\
                        <td>" + item.label + "<br>\n\
                            <small>" + item.role + ", " +  item.age + " <s:property value="%{getText('label.udmEta')}" />  <br/>\n\
                            " + item.province + " </small>\n\
                        </td>\n\
                     </tr>\n\
                 </table>\n\
               </a>")
      .appendTo( ul );
    };
  });

  function removeUser(idUser)
  {
    
    JQ('[name=idPlayerToRemove]').val(idUser);
    JQ('#idRegisterToMatch').submit();
  }

  function registerUsers()
  {

    JQ('[name=userRegistration]').val(true);
    JQ('#idRegisterToMatch').submit();
  }

  function addUser(idUser)
  {
    JQ('[name=idUserToAdd]').val(idUser);
    JQ('#idRegisterToMatch').submit();
  }

  function addUserAjax(idUser)
  {
    new Ajax.Updater('playersRegisteredListContainer', '<s:url action="fillUserToRegisterList" namespace="/ajax"/>',
        {
          parameters: {
            idMatch: ${idMatch},
            idUserToAdd: idUser,
            idUserToAddList: ${idUserToAddList} ,//to-SESSIONE
            dummy: Math.random()

          }
        });
  }

</script>


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

  <display:column title=""  class="${isCurrentUser?'currentUser':''} first centred">
    ${rowCount}
  </display:column>

  <display:column title=""  class="${isCurrentUser?'currentUser ':''}" style="width:0px;">
    <s:if test="#isValid"  >
      <s:url action="viewAvatar" var="imageUrl">
        <s:param name="idUser">
          ${guiPlayer.userInfo.id}
        </s:param>
      </s:url>
      <img src="${imageUrl}" alt="" />
      <s:checkbox id="idUserToAddList" name="idUserToAddList" fieldValue="%{#idUser}" cssStyle="display:none;"  />
    </s:if>
  </display:column>

  <display:column title="" class="${isCurrentUser?'currentUser':''}">
    <s:if test="#isValid"  >
      <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false" showCurrentUserDetails="${guiPlayer.status=='Out'? 'false':'true'}" linkToDetails="true"  />
      <br/>
      <small style="color:#999999;">
      <u:translation key="${guiPlayer.userInfo.playerRoleKey}"/>
      <s:if test="#hasAge">
        ${guiPlayer.userInfo.age} <s:property value="%{getText('label.udmEta')}" />
      </s:if><br/>
      ${guiPlayer.userInfo.province}
      </small>
    </s:if>
    <s:else>
      <s:set name="ctrl">${rowCount==userInList + 1}</s:set>
      <s:if test="#ctrl">
        <div class="ui-widget">
          <input id="searchString" value="<u:translation key="label._cerca"/>..." onclick="this.value=''" type="text" class="maximized" />
        </div>
      </s:if>
    </s:else>
  </display:column>

  <s:if test="!matchDone">
    <s:if test="#ownerUser && #isValid ">
      <display:column title=""  class="${isCurrentUser?'currentUser':''} delete">
        <a onclick="javascript: removeUser(${idUser});" href="javascript: void(0);">X</a>
      </display:column>
    </s:if>
    <s:else>
      <display:column title=""  class="${isCurrentUser?'currentUser':''}" >&nbsp;</display:column>
    </s:else>
  </s:if>

</display:table>
     





