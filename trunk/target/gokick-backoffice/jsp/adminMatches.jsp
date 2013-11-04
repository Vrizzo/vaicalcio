<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:url id="sportCentersByCityURL" action="sportCentersByCity" namespace="/ajax"/>
<s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax"/>
<s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
    <title><s:property value="Matches"/></title>
    <script type="text/javascript">

        JQ(document).ready(function () {
            JQ('#idMatchesTable').dataTable({
                iDisplayLength: 25,
                aoColumns: [
                    { bSortable: false },        // 0  <th></th>
                    null,                        // 1  <th>Id</th>
                    { "iDataSort": 3 },          // 2  <th>Date</th>
                    { bVisible: false },        // 3  <th>DataHidden</th>
                    null,                        // 4  <th>Centre</th>
                    null,                        // 5  <th>Province</th>
                    null,                        // 6  <th>City</th>
                    null,                        // 7  <th>vs</th>
                    null,                        // 8  <th>Organizer</th>
                    { bSortable: false },        // 9  <th>R</th>
                    { "iDataSort": 11 },        // 10 <th>missing</th>
                    { bVisible: false },        // 11 <th>missingHidden</th>
                    { "iDataSort": 13 },         // 12 <th>Com</th>
                    { bVisible: false }         // 13 <th>ComHidden</th>
                ]
            });
            JQ('#idDivTable').show();
        });

        function chkAndGetProvincesWithMatches(valore) {

            $('idSelectSportCenter').options.length = 1;
            $('idSelectCity').options.length = 1;
            getProvincesByCountry(valore, 0, 0, 1, '${provincesByCountryURL}');
        }

        function chkAndGetCitiesByProvinces(valore) {
            $('idSelectSportCenter').options.length = 1;
            $('idSelectCity').options.length = 1;

            getCitiesByProvince(valore, 0, 0, 1, '${citiesByProvinceURL}');

        }

        function chkAndGetSportCentersByCities(valore) {
            $('idSelectSportCenter').options.length = 1;
            if (valore > 0) {
                getSportCenterByCity(valore, '${sportCentersByCityURL}');
            }
        }


        function checkSelected() {
            var ctrl = false;
            $$('.chkSelectMatch').each(function (c) {
                if (c.checked)
                    ctrl = true;
            });

            if (ctrl == false && (!($('chkSendToAll').checked)))
                alert('no selection');
            else
                $('idFormMatchList').submit();
        }


    </script>
</head>
<body>

<c:set var="hideCountry" value="${UserContext.idCountryFilter>0}"/>

<div class="wrapper">

<!--### start header ###-->
<jsp:include page="../jspinc/header.jsp" flush="true"/>
<!--### end header ###-->

<div style="padding-left:20px;">

<s:set name="size" value="guiUserList.size"/>
<h1>${matchTot} Matches</h1>

<p>
    ${futureMatches} incoming ${matchTot - futureMatches} past
</p>

<div class="headList">
    <p>
        <s:property value="guiCalendarInfoList.size"/> Matches found
    </p>
</div>

<div class="searchBox" id="idBoxSearch">
    <s:form action="AdminMatches!searchMatches" method="post">
        <table cellpadding="0" cellspacing="0" style="width:880px;">
            <tr>
                <td height="10" valign="middle" bgcolor="whitesmoke" colspan="5">
                    <p></p>
                </td>
            </tr>
            <tr>
                <td width="150" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        Country</span></font>
                    </p>
                </td>
                <td width="400" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        <s:select
                                name="idCountry"
                                headerKey="0"
                                headerValue="All"
                                list="countyList"
                                listKey="id"
                                listValue="name"
                                id="idSelectCountry"
                                value="idCountry"
                                onchange="chkAndGetProvincesWithMatches($(this).value);"
                                />
                        <%-- onchange="getProvincesByCountry($(this).value, 0, 0, 1, '%{provincesByCountryURL}')" --%>
                      </span></font></p>
                </td>
                <td width="200" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font color="#666666" face="Verdana"><span style="font-size:8pt;">
                        Date</span></font>
                    </p>
                </td>
                <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <s:select
                                headerKey="0"
                                headerValue="All"
                                name="time"
                                list="timeList"
                                />
                    </p>
                </td>
            </tr>
            <tr>
                <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        Province</span></font></p>
                </td>
                <td width="400" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        <s:select
                                name="idProvince"
                                headerKey="0"
                                headerValue="All"
                                list="provinceList"
                                listKey="id"
                                listValue="name"
                                id="idSelectProvince"
                                value="idProvince"
                                onchange="chkAndGetCitiesByProvinces($(this).value);"
                                />
                        <%--onchange="getCitiesByProvince($(this).value, 0, 0, 1, '%{citiesByProvinceURL}')" --%>
                        <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/></span>
                      </span></font></p>
                </td>
                <td width="200" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font color="#666666" face="Verdana"><span style="font-size:8pt;">
                        Type (TODO)</span></font>
                    </p>
                </td>
                <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <s:select
                                headerKey="0"
                                headerValue="All"
                                name="type"
                                list="typeList"
                                />
                    </p>
                </td>
            </tr>
            <tr>
                <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        City</span></font>
                    </p>
                </td>
                <td width="400" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;"><font face="Verdana"><span style="font-size:8pt;">
                        <s:select
                                name="idCity"
                                headerKey="0"
                                headerValue="All"
                                list="cityList"
                                listKey="id"
                                listValue="name"
                                value="idCity"
                                id="idSelectCity"
                                onchange="chkAndGetSportCentersByCities($(this).value);"
                                />
                        <%--onchange="getSportCenterByCity($(this).value, '%{sportCentersByCityURL}');" --%>
                        <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/></span>
                      </span></font></p>
                </td>
                <td width="200" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font color="#666666" face="Verdana"><span style="font-size:8pt;">
                        Only canceled</span></font>
                    </p>
                </td>
                <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        <s:checkbox name="onlyCanceled" style="margin:0; padding:0;"/></span></font>
                    </p>
                </td>
            </tr>
            <tr>
                <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        Sports Centre</span></font>
                    </p>
                </td>
                <td>
                    <s:select
                            name="idSportCenter"
                            headerKey="0"
                            headerValue="All"
                            list="sportCenterList"
                            listKey="id"
                            listValue="name"
                            value="idSportCenter"
                            id="idSelectSportCenter"
                            />
                    <span id="idWaitSportCenter" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/></span>
                </td>
                <td width="200" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font color="#666666" face="Verdana"><span style="font-size:8pt;">
                        Reported (TODO)</span></font>
                    </p>
                </td>
                <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                        <input type="checkbox" name="chkAlert" style="margin:0; padding:0;"/></span></font>
                    </p>
                </td>
            </tr>
            <tr>
                <td colspan="3">

                </td>
                <td class="right" width="134" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="line-height:100%; margin:0;">
                        <font face="Verdana"><span style="font-size:7pt;">
                        <s:submit value="Search" cssClass="btn action1" cssStyle="padding:0 10px"/></span></font>
                    </p>
                </td>
            </tr>
            <tr>
                <td colspan="6"><s:fielderror name="ageInvalid"/></td>
            </tr>
            <tr>
                <td width="923" height="10" valign="middle" bgcolor="whitesmoke" colspan="5">
                    <p></p>
                </td>
            </tr>
        </table>
    </s:form>
</div>

<s:if test="advanceSearch == false">
    <s:set name="dtRequestURI">AdminMatches!viewAll.action</s:set>
</s:if>
<s:else>
    <s:set name="dtRequestURI">AdminMatches!searchMatches.action</s:set>
</s:else>

<s:form action="AdminMatches!loadReceiverList" method="post" id="idFormMatchList">

    <s:textarea name="matchFoundList" cssStyle="visibility:hidden;" rows="0"/>

    <div id="idDivTable" style="display:none">
        <table cellpadding="0" cellspacing="0" border="0" class="usersTable left" id="idMatchesTable" style="width:880px;">
            <thead>
            <tr>
                <th></th>
                <th>Id</th>
                <th>Date</th>
                <th>DataHidden</th>
                <th>Centre</th>
                <th>Province</th>
                <th>City</th>
                <th>vs</th>
                <th>Organizer</th>
                <th>Rep</th>
                <th></th>
                <th>missingHidden</th>
                <th>Com</th>
                <th>ComHidden</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${guiCalendarInfoList}" var="guiCalendar" varStatus="status">
                <s:set name="idGame">${guiCalendar.idMatch}</s:set>
                <tr>
                    <td>
                        <s:checkbox
                                name="matchCheckList"
                                fieldValue="%{#idGame}"
                                cssClass="chkSelectMatch"/>
                    </td>
                    <td>
                            ${idGame}
                    </td>
                    <td>
                            ${guiCalendar.matchDate}
                    </td>
                    <td>
                            ${guiCalendar.dateMatch}
                    </td>
                    <td>
                        <a href="${guiCalendar.googleMapUrlUrl}" target="_blank">
                                ${guiCalendar.sportCenterName}
                        </a>
                    </td>
                    <td>
                            ${guiCalendar.sportCenterProvince}
                    </td>
                    <td>
                            ${guiCalendar.sportCenterCity}
                    </td>
                    <td>
                            ${guiCalendar.playerForTeam}
                    </td>
                    <td>
                            ${guiCalendar.userOwnerName}
                    </td>
                    <td>
                        <small>todo</small>
                    </td>
                    <td>
                        <c:set var="pastMatch">${guiCalendar.past}</c:set>
                        <c:choose>
                            <c:when test="${pastMatch}">
                                <c:set var="recorded">${guiCalendar.recorded}</c:set>
                                <c:choose>
                                    <c:when test="${recorded}">
                                        <c:choose>
                                            <c:when test="${UserContext.idCountryFilter==0}">
                                                <a href="${guiCalendar.reportMatchUrl}" target="_blank">
                                                    <u:printIconReportCalendarMatch guiCalendarInfo="${guiCalendar}"/>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <u:printIconReportCalendarMatch guiCalendarInfo="${guiCalendar}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${UserContext.idCountryFilter==0}">
                                                <a href="${guiCalendar.archiveMatchUrl}" target="_blank">
                                                    <u:printIconReportCalendarMatch guiCalendarInfo="${guiCalendar}"/>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <u:printIconReportCalendarMatch guiCalendarInfo="${guiCalendar}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${UserContext.idCountryFilter==0}">
                                        <a href="${guiCalendar.viewMatchUrl}" target="_blank">
                                            <u:printIconCalendarMatch guiCalendarInfo="${guiCalendar}"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <u:printIconCalendarMatch guiCalendarInfo="${guiCalendar}"/>
                                    </c:otherwise>
                                </c:choose>
                                <c:set var="missingPlayers">${guiCalendar.missingPlayers}</c:set>
                                <c:if test="${missingPlayers > 0}">
                                    <small class="${(guiCalendar.registrationsOpenInFuture || guiCalendar.registrationsClosed) ? 'disable' : '' }">
                                        - ${guiCalendar.missingPlayers}</small>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${!pastMatch && missingPlayers > 0}">
                            ${guiCalendar.missingPlayers}
                        </c:if>
                    </td>
                    <td>
                        <c:set var="comments">${guiCalendar.commentsNumber}</c:set>
                        <c:if test="${comments > 0}">
                            <c:choose>
                                <c:when test="${UserContext.idCountryFilter==0}">
                                    <a href="${guiCalendar.viewCommentsUrl}" target="_blank">
                                        <img src="<s:url value="/images/comment3.gif" encode="false" />" title="post"/>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <img src="<s:url value="/images/comment3.gif" encode="false" />" title="post"/>
                                </c:otherwise>
                            </c:choose>
                            ${comments}
                        </c:if>
                    </td>
                    <td>
                            ${comments}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <br class="clear"/>

    <div style="clear:both; margin-top: 15px;">
        <s:checkbox id="idChkSelectAll" name="selectAll" onclick="javascript:changeCheckBox('.chkSelectMatch', this.checked);"/>
        select all
    </div>

    <p style="padding-top:20px;">
        <!--s:submit value="Send Message" cssClass="btn" onclick="idFormMatchList();"/-->
        <a href="javascript: checkSelected();" class="btn action1">Send Message</a>
        <s:checkbox name="sendToAll" id="chkSendToAll"/>Send to all...
    </p>
</s:form>

<br/><br/>

</div>


</div>
<!--### end wrapper ###-->
</body>
</html>
