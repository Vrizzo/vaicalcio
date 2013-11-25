<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
    <title>Sports Centres</title>
    <script type="text/javascript">

        function checkSelected() {
            var ctrl = false;
            $$('.chkSelectSportCenter').each(function (c) {
                if (c.checked)
                    ctrl = true;
            });

            if (ctrl == false && (!($('chkSendToAll').checked)))
                alert('No selection');
            else
                $('idFormSportCenterList').submit();
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

<p align="left" style="line-height:100%; margin:0;"><font face="Verdana" color="#333333"><span style="font-size:16pt;">
            ${sportCenterTot}  Sports centres</span></font>
</p>

<p style="line-height:100%; margin:0;" align="left">
    <font face="Verdana" color="#999999"><span style="font-size:8pt;">
            ${sportCenterOffLine} offline</span></font>
    &nbsp;&nbsp;
    <font face="Verdana" color="#333333"><span style="font-size:8pt; background-color:rgb(255,254,190);">
            ${sportCenterConventioned} partners</span></font>
    &nbsp;&nbsp;
    <font face="Verdana" color="red"><span style="font-size:8pt;">
            (TODO) reported</span></font>
</p>

<div class="headList">
    <p>
        <s:property value="guiSportCenterList.size"/> found
    </p>
</div>

<div class="searchBox" id="idBoxSearch">

    <s:form action="AdminSportCenters!searchSportCenters" method="post">

        <table cellpadding="0" cellspacing="0" width="923">
            <tr>
                <td width="923" height="10" valign="middle" bgcolor="whitesmoke" colspan="4">
                    <p></p>
                </td>
            </tr>
            <tr>
                <td width="87" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Name</span></font>
                    </p>
                </td>
                <td width="604" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      <s:textfield name="name" size="30" cssStyle="border:solid 1px #999999; background:#FFFFFF;"/>
                      &nbsp;&nbsp;</span></font>
                    </p>
                </td>
                <td width="120" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Status</span></font>
                    </p>
                </td>
                <td width="112" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <s:select
                                name="status"
                                headerKey="tutti"
                                headerValue="All"
                                list="statusList"
                                />
                    </p>
                </td>
            </tr>
            <tr>

                <td width="87" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Country</span></font>
                    </p>
                </td>
                <td width="604" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
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
                                onchange="getProvincesByCountry($(this).value, 0, 1, 0, '%{provincesByCountryURL}')"/>
                    </p>
                </td>
                <td width="120" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Only partners</span></font>
                    </p>
                </td>
                <td width="112" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                    <s:checkbox name="chkConventioned"/></span></font>
                    </p>
                </td>
            </tr>
            <tr>
                <td width="87" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Province</span></font>
                    </p>
                </td>
                <td width="604" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
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
                              onchange="getCitiesByProvince($(this).value, 0, 1, 0, '%{citiesByProvinceURL}')"/>
                    </span>
                            <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/></span></font>
                    </p>
                </td>
                <td width="120" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font color="#666666" face="Verdana"><span style="font-size:8pt;">Reported</span></font></p>
                </td>
                <td width="112" height="24" valign="middle" bgcolor="whitesmoke">
                    <p><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      <s:checkbox name="chkReported"/></span></font>
                    </p>
                </td>
            </tr>
            <tr>
                <td width="87" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      City</span></font>
                    </p>
                </td>
                <td width="604" height="24" valign="middle" bgcolor="whitesmoke">
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
                <td width="120" height="24" valign="middle" bgcolor="whitesmoke">
                    <p>&nbsp;</p>
                </td>
                <td width="112" height="24" valign="middle" bgcolor="whitesmoke">
                    <p>&nbsp;</p>
                </td>
            </tr>
            <tr>
                <td width="87" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="left" style="margin-bottom:1;">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Pitch type</span></font>
                    </p>
                </td>
                <td width="604" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <s:select
                                name="idMatchType"
                                headerKey="0"
                                headerValue="All"
                                list="matchTypeList"
                                listKey="id"
                                listValue="id +' vs '+ id"
                                value="idMatchType"
                                id="idSelectMatchType"/>
                    </p>
                </td>
                <td width="120" height="24" valign="middle" bgcolor="whitesmoke">
                    <p>&nbsp;</p>
                </td>
                <td width="112" height="24" valign="middle" bgcolor="whitesmoke">
                    <p>&nbsp;</p>
                </td>
            </tr>
            <tr>
                <td width="87" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Covered</span></font>
                    </p>
                </td>
                <td width="604" height="24" valign="middle" bgcolor="whitesmoke">
                    <p style="line-height:100%; margin:0;" align="left">
                        <s:select
                                name="idPitchCover"
                                headerKey="0"
                                headerValue="All"
                                list="pitchCoverList"
                                listKey="id"
                                listValue="%{getText(keyName)}"
                                value="idPitchCover"
                                id="idSelectPitchCover"/>
                    </p>
                </td>
                <td width="120" height="24" valign="middle" bgcolor="whitesmoke">
                    <p>&nbsp;</p>
                </td>
                <td width="112" height="24" valign="middle" bgcolor="whitesmoke">
                    <p align="center" style="line-height:100%; margin:0;">
                        <font face="Verdana"><span style="font-size:7pt;">
                              <s:submit value="Search" cssClass="btn action1" cssStyle="padding:0 10px"/></span></font>
                    </p>
                </td>
            </tr>
            <tr>
                <td width="923" height="10" valign="middle" bgcolor="whitesmoke" colspan="4">
                    <p></p>
                </td>
            </tr>
        </table>
    </s:form>

</div>

<s:if test="advanceSearch == false">
    <s:set name="dtRequestURI">AdminSportCenters!viewAll.action</s:set>
</s:if>
<s:else>
    <s:set name="dtRequestURI">AdminSportCenters!searchSportCenters.action</s:set>
</s:else>

<s:form action="AdminSportCenters!loadReceiverList" method="post" id="idFormSportCenterList">
    <display:table name="guiSportCenterList" id="guiSportCenter" requestURI="${dtRequestURI}" pagesize="20" class="usersTable">
        <u:displayTablePaginator/>
        <s:set name="sportCenterId">${guiSportCenter.id}</s:set>

        <s:set name="isConventioned">${guiSportCenter.conventioned}</s:set>
        <s:if test="#isConventioned == 'true'">
            <s:set name="currentSportCenterClass">sportCenterAllHighlightedLine</s:set>
        </s:if>
        <s:else>
            <s:set name="currentSportCenterClass"></s:set>
        </s:else>

        <display:column title="" headerClass="first" style="width:5px;" class="${currentSportCenterClass} first ">
            <s:checkbox
                    name="sportCenterCheckList"
                    fieldValue="%{#sportCenterId}"
                    cssClass="chkSelectSportCenter"/>
        </display:column>

        <display:column title="Id" style="width:30px;" sortable="true" sortProperty="id" class="${currentSportCenterClass}">
            ${guiSportCenter.id}
        </display:column>

        <display:column title="Centre's Name" style="width:220px;" sortable="true" sortProperty="name" class="${currentSportCenterClass}">
            ${guiSportCenter.name}
        </display:column>

        <display:column title="City" sortable="true" sortProperty="cityName" style="width:120px;" class="${currentSportCenterClass}">
            ${guiSportCenter.cityName}
        </display:column>

        <display:column title="Address" style="width:120px;" sortable="true" sortProperty="address" class="${currentSportCenterClass}">
            ${guiSportCenter.address}
        </display:column>

        <display:column title="vs" sortable="true" sortProperty="matchTypeAvailable" style="width:30px;" class="${currentSportCenterClass}">
            ${guiSportCenter.matchTypeAvailable}
        </display:column>

        <display:column title="Phone" sortable="true" sortProperty="telephone" style="width:90px;" class="${currentSportCenterClass}">
            ${guiSportCenter.telephone}
        </display:column>

        <display:column title="Created" sortable="true" sortProperty="sportCenter.created" style="width:30px;" class="${currentSportCenterClass}">
            <fmt:formatDate value="${guiSportCenter.sportCenter.created}" pattern="dd/MM/yy"/>
        </display:column>

        <display:column title="by" sortable="true" sortProperty="sportCenter.userAuthor.firstName" style="width:110px;" class="${currentSportCenterClass}">
            ${guiSportCenter.sportCenter.userAuthor.firstName} ${guiSportCenter.sportCenter.userAuthor.lastName}
        </display:column>

        <display:column title="Status" sortable="true" sortProperty="status" style="width:30px;" class="${currentSportCenterClass}">
            ${guiSportCenter.status}
        </display:column>

        <display:column title="Rep." style="width:50px;" class="${currentSportCenterClass}">
            TODO
        </display:column>

        <display:column title="" style="width:35px;" class="${currentSportCenterClass}">
            <s:url action="AdminSingleSportCenter!input" var="AdminSingleSportCenterUrl">
                <s:param name="idSportCenter">${guiSportCenter.id}</s:param>
            </s:url>
            <a href="${AdminSingleSportCenterUrl}"><img src="<s:url value="/images/pencil.gif" />" alt=""/></a>
        </display:column>

    </display:table>

    <p class="left">
        <s:checkbox id="idChkSelectAll" name="selectAll" onclick="javascript:changeCheckBox('.chkSelectSportCenter', this.checked);"/>
        select all
    </p>
    <br/>
    <br/>

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
