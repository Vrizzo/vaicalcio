<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>

 <s:if test="playersTot==0 && (squadOutEnable==false || directlyRegistration==true)">
 </s:if>
 <s:else>    
      <display:table name="playersRequestList" id="guiPlayer" class="guiPlayer" >
          <u:displayTablePaginator />         
          <s:set name="ownerUser">${guiPlayer.ownerUser}</s:set>
          <s:set name="isCurrentUser">${guiPlayer.currentUser}</s:set>
          <s:set name="playerMobile">${guiPlayer.mobile}</s:set>
          <s:set name="titleInfo"><s:property value="playersTot" /> <u:translation key="${playersTot > 1?'label.viewMatchRichiesteGioco':'label.viewMatchRichiestaGioco' }"/></s:set>
          <s:set name="titleRequest"><u:translation key="label.del"/></s:set>
          <s:set name="titleRole"><u:translation key="label.roleAbbr"/></s:set>

          <display:column title="${titleInfo}" sortable="false">
            <u:printUserName userInfo="${guiPlayer.userInfo}" showAvatar="false"  />
               <s:if test="#playerMobile!=''">
                 <br/>
                 <small class="lblGrey">
                  <u:translation key="label.cell"/>: ${guiPlayer.mobile}
                 </small>         
              </s:if>
          </display:column>

          <display:column title="${titleRequest}" sortable="false">
            <utl:formatDate date="${guiPlayer.requestDate}" formatKey="format.date_6" /> <br/>
            <small class="lblGrey">
            <utl:formatDate date="${guiPlayer.requestDate}" formatKey="format.date_5" />
            </small>
          </display:column>

          <display:column title="${titleRole}" sortable="false">
            <c:if test="${guiPlayer.userInfo!=null}">
              <s:set name="ruoloLang" ><u:translation key="${guiPlayer.userInfo.playerRoleKey}"/></s:set>
              ${fn:substring(ruoloLang, 0, 3)}
            </c:if>
          </display:column>

          <display:column title="" class="">
              <s:if test="playersToAdd > 0 && matchDone!=true">
                <a onclick="registerRequestPlayer(${guiPlayer.id} );" href="javascript: void(0);" >
                      <span class="lblMatch"><u:translation key="label.iscrivi"/></span>
                      <!--<img src="<s:url value="/images/icon_accept.gif" encode="false" />" alt="Accetta" /-->
                </a>             
              </s:if>
          </display:column>

      </display:table>
</s:else>
           

