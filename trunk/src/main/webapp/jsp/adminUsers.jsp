<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
    <title>Users</title>
    <script type="text/javascript">

        function checkSelected() {
            var ctrl = false;
            $$('.chkSelectUser').each(function (c) {
                if (c.checked)
                    ctrl = true;
            });

            if (ctrl == false && (!($('chkSendToAll').checked)))
                alert('No selection');
            else
                $('idFormUserList').submit();
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


<h1>${userTot} GoKickers</h1>

<div class="headList">
    <p>
        <s:property value="guiUserList.size"/> GoKickers trovati
    </p>
</div>

<div class="searchBox" id="idBoxSearch">
<s:form action="AdminUsers!searchUsers" method="post">

<table cellpadding="0" cellspacing="0" width="923">
<tr>
    <td width="923" height="10" valign="middle" bgcolor="whitesmoke" colspan="5">
        <p></p>
    </td>
</tr>
<tr>
    <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;"><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              First Name</span></font>
        </p>
    </td>
    <td width="531" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;"><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              <s:textfield name="firstName" size="12" cssStyle="border:solid 1px #999999; background:#FFFFFF;"/>&nbsp;&nbsp;&nbsp;&nbsp;
                              Last Name:&nbsp;
                              <s:textfield name="lastName" size="12" cssStyle="border:solid 1px #999999; background:#FFFFFF;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                              Age:
                              <s:textfield name="minAge" size="2" maxLength="2" cssStyle="border:solid 1px #999999; background:#FFFFFF;"/>
                              </span><span style="font-size:7pt;">age min &nbsp;&nbsp;&nbsp;</span>
                              <span style="font-size:8pt;">
                              <s:textfield name="maxAge" size="2" maxLength="2" cssStyle="border:solid 1px #999999; background:#FFFFFF;"/>
                              </span><span style="font-size:7pt;">age max &nbsp;</span></font>
        </p>
    </td>
    <td width="105" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="line-height:100%; margin:0;">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              Status </span></font><font face="Verdana"><span style="font-size:8pt;">&nbsp;&nbsp;&nbsp;</span></font>
        </p>
    </td>
    <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
        <p align="left" style="line-height:100%; margin:0;"><font face="Verdana"><span style="font-size:8pt;">
                            <s:select
                                    headerKey=""
                                    headerValue="All"
                                    name="userStatus"
                                    list="enumUserStatusList"/>
                            </span></font>
        </p>
    </td>
</tr>
<tr>
    <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              Country</span></font>
        </p>
    </td>
    <td width="531" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax"/>
                              <s:select
                                      name="idCountry"
                                      headerKey="0"
                                      headerValue="All"
                                      list="countyList"
                                      listKey="id"
                                      listValue="name"
                                      id="idSelectCountry"
                                      value="idCountry"
                                      onchange="getProvincesByCountry($(this).value, 1, 0, 0, '%{provincesByCountryURL}')"/>
                              </span></font></p>
    </td>
    <td width="105" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="line-height:100%; margin:0;">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              Permission</span></font>&nbsp;&nbsp;</p>
    </td>
    <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
        <s:select
                name="permissionToSearch"
                list="#{'F':'Profile Pictures (F)',
                                        'C':'Pitches (C)',
                                        'T':'Tournaments (T)',
                                        'L':'Links (L)',
                                        'N':'News (N)',
                                        'B':'Blog (B)'}"
                headerValue="All"
                headerKey=""
                />

    </td>
</tr>
<tr>
    <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;"><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              Province</span></font></p>
    </td>
    <td width="531" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;"><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax"/>
                              <s:select
                                      name="idProvince"
                                      headerKey="0"
                                      headerValue="All"
                                      list="provinceList"
                                      listKey="id"
                                      listValue="name"
                                      id="idSelectProvince"
                                      value="idProvince"
                                      onchange="getCitiesByProvince($(this).value, 1, 0, 0, '%{citiesByProvinceURL}')"/>
                              <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/></span>
                            </span></font></p>
    </td>
    <td width="105" height="24" valign="middle" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">
            <font color="#666666" face="Verdana"><span style="font-size:8pt;">
                              Newsletter</span></font>
        </p>
    </td>
    <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                            <s:checkbox name="chkNewsletter"/></span></font>
        </p>
    </td>
</tr>
<tr>
    <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;"><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              City</span></font>
        </p>
    </td>
    <td width="531" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;"><font face="Verdana"><span style="font-size:8pt;">
                              <s:select
                                      name="idCity"
                                      headerKey="0"
                                      headerValue="All"
                                      list="cityList"
                                      listKey="id"
                                      listValue="name"
                                      value="idCity"
                                      id="idSelectCity"/>
                            <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/></span>
                            </span></font></p>
    </td>
    <td width="105" height="24" valign="middle" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">
            <font color="#666666" face="Verdana"><span style="font-size:8pt;">
                              Reports</span></font>
        </p>
    </td>
    <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              <s:checkbox name="chkAlert"/></span></font>
        </p>
    </td>
</tr>
<tr>
    <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="margin-bottom:1;"><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              Position</span></font>
        </p>
    </td>
    <td width="531" height="24" valign="middle" bgcolor="whitesmoke">
        <fieldset>
            <p style="line-height:100%; margin:0;" align="left">
                <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              <s:checkbox id="idChkGK" name="chkGk"/> <label for="idChkGK">Goalkeeper</label>
                              &nbsp;&nbsp;&nbsp;&nbsp;
                              <s:checkbox id="idChkDF" name="chkDf"/> <label for="idChkDF">Defender</label>
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                              <s:checkbox id="idChkCC" name="chkCc"/> <label for="idChkCC">Midfielder</label>
                              &nbsp;&nbsp;&nbsp;&nbsp;
                              <s:checkbox id="idChkAT" name="chkAt"/> <label for="idChkAT">Striker</label>
                              </span></font>
            </p>
        </fieldset>


    </td>
    <td width="105" height="24" valign="middle" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">&nbsp;</p>
    </td>
    <td width="203" height="24" valign="middle" colspan="2" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">&nbsp;</p>
    </td>
</tr>
<tr>
    <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              Market</span></font>
        </p>
    </td>
    <td width="531" height="24" valign="middle" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              <s:checkbox id="idOnlyOpenMarket" name="onlyMarketEnabled"/>
                              <label for="idOnlyOpenMarket">View only GoKickers on the trasfer
                                  market</label></span></font>
        </p>
    </td>
    <td width="105" height="24" valign="middle" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">&nbsp;</p>
    </td>
    <td width="203" height="24" valign="middle" bgcolor="whitesmoke" colspan="2">
        <p style="line-height:100%; margin:0;" align="left">&nbsp;</p>
    </td>
</tr>
<tr>
    <td width="84" height="24" valign="middle" bgcolor="whitesmoke">
        <p style="line-height:100%; margin:0;" align="left">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                              Stats</span></font>
        </p>
    </td>
    <td width="531" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="left" style="line-height:100%; margin:0;">
            <s:select
                    name="statisticPeriod"
                    list="statisticPeriodList"
                    listKey="id"
                    listValue="label"
                    value="statisticPeriod"/>
        </p>
    </td>
    <td width="105" height="24" valign="middle" bgcolor="whitesmoke">
        <p>&nbsp;</p>
    </td>
    <td width="69" height="24" valign="middle" bgcolor="whitesmoke">
        <p>&nbsp;</p>
    </td>
    <td width="134" height="24" valign="middle" bgcolor="whitesmoke">
        <p align="center" style="line-height:100%; margin:0;"><font face="Verdana"><span style="font-size:7pt;">
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


<s:form action="AdminUsers!loadReceiverList" method="post" id="idFormUserList">
    <s:if test="advanceSearch == false">
        <s:set name="dtRequestURI">AdminUsers!viewAll.action</s:set>
    </s:if>
    <s:else>
        <s:set name="dtRequestURI">AdminUsers!searchUsers.action</s:set>
    </s:else>

    <display:table name="guiUserList" id="guiUser" requestURI="${dtRequestURI}" pagesize="50" class="usersTable">
        <u:displayTablePaginator/>
        <s:set name="userId"> ${guiUser.user.id}</s:set>
        <s:set name="organizza">${guiUser.user.organizeEnabled}</s:set>

        <display:column title="" class="first" headerClass="first" style="width:5px;">
            <s:checkbox
                    name="userCheckList"
                    fieldValue="%{#userId}"
                    cssClass="chkSelectUser"/>
        </display:column>

        <display:column title="Id" sortable="true" sortProperty="user.id" style="width:5px;">
            ${guiUser.user.id}
        </display:column>

        <display:column title="First Name" sortable="true" sortProperty="user.firstName" style="width:80px;">
            ${guiUser.user.firstName}
        </display:column>

        <display:column title="Last Name" sortable="true" sortProperty="user.lastName" style="width:80px;">
            ${guiUser.user.lastName}
        </display:column>

        <display:column title="City" sortable="true" sortProperty="user.city.name" style="width:80px;">
            ${guiUser.user.city.name}
        </display:column>

        <display:column title="Nat" sortable="true" sortProperty="user.nationalityCountry.name" style="width:30px;">
            <img src="images/flags/${guiUser.user.nationalityCountry.id}.png" onError="this.src='images/country_flag_0.gif';" title="${guiUser.user.nationalityCountry.name}"/>
        </display:column>

        <display:column title="P" sortable="true" sortProperty="user.playerRole.id" style="width:30px;">
            <s:set name="ruoloLang">${fn:toUpperCase(guiUser.user.playerRole.label)}</s:set>
            ${fn:substring(ruoloLang, 0, 1)}
        </display:column>

        <display:column title="Age" sortable="true" sortProperty="userAge" style="width:30px;">
            ${guiUser.userAge}
        </display:column>

        <display:column title="Mkt" style="width:35px;">
            <s:set name="isMarket">${guiUser.user.marketEnabled}</s:set>
            <s:if test="#isMarket">
                <img src="<s:url value="/images/gioca.gif" encode="false" />" alt=""/>
            </s:if>
        </display:column>

        <s:if test="statisticPeriod!='none'">

            <display:column title="P" sortable="true" sortProperty="allPlayed" style="width:30px;">
                ${guiUser.allPlayed}
            </display:column>

            <display:column title="Rel" sortable="true" sortProperty="reliability" style="width:50px;">
                ${guiUser.reliability}%
            </display:column>

            <display:column title="Fr." sortable="true" sortProperty="monthPlayed" style="width:25px;">
                <small>${guiUser.monthPlayed}</small>
            </display:column>

            <display:column title="Org" sortable="true" sortProperty="allOrganized" style="width:25px;">
                ${guiUser.allOrganized}
            </display:column>

            <display:column title="Fri" sortable="true" sortProperty="friendsCount" style="width:25px;">
                ${guiUser.friendsCount}
            </display:column>

        </s:if>

        <display:column title="Reg" sortable="true" sortProperty="user.created" style="width:25px;">
            ${guiUser.dateReg}
        </display:column>

        <display:column title="Lastlog" sortable="true" sortProperty="user.lastLogin" style="width:25px;">
            ${guiUser.dateLastLog}
        </display:column>

        <display:column title="Perm" sortable="true" style="width:60px;">
            <c:forEach items="${guiUser.user.userPermissions}" var="permission">${permission.permission} </c:forEach>
        </display:column>

        <s:if test="statisticPeriod!='nessuna'">
            <display:column title="Pic" sortable="true" style="width:25px;">
                ${fn:substring(guiUser.pictureCard.pictureCardStatus, 0, 4)}
            </display:column>
        </s:if>

        <display:column title="Org" sortable="true" style="width:25px;">
            <s:if test="#organizza">
                <div align="center">O</div>
            </s:if>
        </display:column>

        <display:column title="N" sortable="true" style="width:25px;">
            <s:set name="newsLettEnabled">${guiUser.user.newsletterEnabled}</s:set>
            <s:if test="#newsLettEnabled">
                N
            </s:if>
        </display:column>

        <display:column title="St" sortable="true" style="width:25px;">
            ${fn:substring(guiUser.user.userStatus, 0, 3)}
        </display:column>

        <display:column title="" style="width:25px;">
            <s:url action="AdminSingleUser!input" var="AdminSingleUserUrl">
                <s:param name="idUser">${guiUser.user.id}</s:param>
            </s:url>
            <a href="${AdminSingleUserUrl}"><img src="<s:url value="/images/pencil.gif" />" alt=""/></a>
        </display:column>

    </display:table>


    <s:checkbox id="idChkSelectAll" name="selectAll" onclick="javascript:changeCheckBox('.chkSelectUser', this.checked);"/>
    select all


    <p style="padding-top:30px;">
        <a href="javascript: checkSelected();" class="btn action1">Send Message</a>
        <s:checkbox name="sendToAll" id="chkSendToAll"/>Send to all...
    </p>

</s:form>

</div>

<br/><br/><br/>
</div>
<!--### end wrapper ###-->
</body>
</html>
