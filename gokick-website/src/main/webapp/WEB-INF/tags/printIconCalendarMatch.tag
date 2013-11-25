<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@attribute name="guiCalendarInfo" required="true" type="it.newmedia.gokick.site.guibean.GuiCalendarInfo" %>

<s:set name="freePlace">${guiCalendarInfo.missingPlayers}</s:set>
<s:set name="openInFuture">${guiCalendarInfo.registrationsOpenInFuture}</s:set>
<s:set name="open">${guiCalendarInfo.registrationsOpen}</s:set>
<s:set name="userOwner">${guiCalendarInfo.currentUserOwner}</s:set>
<s:set name="missDay">${guiCalendarInfo.missingDays}</s:set>
<s:set name="missHours">${guiCalendarInfo.missingHours}</s:set>
<s:set name="missMinutes">${guiCalendarInfo.missingMinutes}</s:set>
<s:set name="ctrlPal">false</s:set>

<s:set name="canceled">${guiCalendarInfo.matchInfo.canceled}</s:set>
<s:set name="closed">${guiCalendarInfo.matchInfo.registrationClosed}</s:set>
<s:if test="#canceled">
  <s:set name="path">ann</s:set>
  <s:set name="altText"><u:translation key="label.annullata"/></s:set>
</s:if>
<s:else>
  <s:if test="#freePlace > 0">
    <s:set name="path">omi</s:set>
    <s:set name="altText">${guiCalendarInfo.missingPlayers} <u:translation key="label.calendarPostiDisponibili"/></s:set>
  </s:if>
  <s:else>
    <s:set name="path">pal</s:set>
    <s:set name="altText"><u:translation key="label.calendarIscrizioniComplete"/></s:set>
    <s:set name="ctrlPal" >true</s:set>
  </s:else>
  <s:if test="#openInFuture=='true'">
    <s:if test="#ctrlPal=='false'">
      <s:set name="path">${path}-fad</s:set>
      <s:set name="altText"><u:translation key="label.calendarAperteTra"/></s:set>
      <s:if test="#missDay > 0">
        <s:set name="altText"> ${altText} ${missDay} <u:translation key="label.giorni"/></s:set>
      </s:if>
      <s:elseif test="#missHours > 0">
        <s:set name="altText"> ${altText} ${guiCalendarInfo.missingHours} <u:translation key="label.ore"/></s:set>
      </s:elseif>
      <s:elseif test="#missMinutes > 0">
        <s:set name="altText"> ${altText} ${guiCalendarInfo.missingMinutes} <u:translation key="label.minuti"/></s:set>
      </s:elseif>
    </s:if>
  </s:if>
  <s:elseif test="#closed=='true'">
    <s:if test="#ctrlPal=='false'">
      <s:set name="path">${path}-fad</s:set>
      <s:set name="altText"><u:translation key="label.calendarIscrizioniChiuse"/></s:set>
    </s:if>      
  </s:elseif>
  <s:if test="#userOwner">
    <s:set name="path">${path}-mat</s:set>
  </s:if>
 </s:else><img  src="images/${path}.gif" title="${altText}" alt="${altText}" />



