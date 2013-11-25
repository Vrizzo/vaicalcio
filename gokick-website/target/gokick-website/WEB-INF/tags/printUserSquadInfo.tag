<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@attribute name="squadInfoToShow" required="true" type="it.newmedia.gokick.site.guibean.GuiSquadInfo" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<s:if test="squadInfoToShow.squadInfo.playersTot!='-'" >
    <s:property value="squadInfoToShow.squadInfo.playersTot" /> GoKickers
    <s:if test="squadInfoToShow.ownerStatisticInfo!=null || squadInfoToShow.squadInfo.market" >
        <s:property value="%{getText('label.separatore')}" />
    </s:if>
</s:if>

<s:if test="squadInfoToShow.ownerStatisticInfo!=null" >
    <s:property value="squadInfoToShow.ownerStatisticInfo.matchOrganized" />
    <s:property value="%{getText('label.matchOrganized')}" />
    <%--s:if test="squadInfoToShow.squadInfo.market!=''" >
        <s:property value="%{getText('label.separatore')}" />
    </s:if--%>
</s:if>

<%--s:if test="squadInfoToShow.squadInfo.market" >
    <s:property value="%{getText('label.mercato')}" />
    <s:set value="squadInfoToShow.squadInfo.market" var="market" />
    <u:printMarketStatus marketStatus="${market}" />
    <img src="images/rosa.gif" alt="" />
</s:if--%>




                        