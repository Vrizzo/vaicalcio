<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
    <title>Single User</title>
    <script type="text/javascript">

        function numbersonly(myfield, e, dec) {
            var key;
            var keychar;

            if (window.event)
                key = window.event.keyCode;
            else if (e)
                key = e.which;
            else
                return true;
            keychar = String.fromCharCode(key);

            // control keys
            if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13) || (key == 27))
                return true;

            // numbers
            else if ((("0123456789").indexOf(keychar) > -1))
                return true;

            // decimal point jump
            else if (dec && (keychar == ".")) {
                myfield.form.elements[dec].focus();
                return false;
            } else
                return false;
        }

    </script>
</head>

<body>


<div class="wrapper">

<!--### start header ###-->
<jsp:include page="../jspinc/header.jsp" flush="true"/>
<!--### end header ###-->

<div style="padding-left:20px;">


<h1>
    <c:choose>
        <c:when test="${UserContext.idCountryFilter==0}">
            <a href="javascript: openPopupUserDetails('${guiUser.userDetailsUrl}','${userToUpdate.firstName}_${userToUpdate.lastName}');">
                    ${userToUpdate.firstName} ${userToUpdate.lastName}
            </a>
        </c:when>
        <c:otherwise>
            ${userToUpdate.firstName} ${userToUpdate.lastName}
        </c:otherwise>
    </c:choose>
</h1>

<br/><br/>

<s:form id="idUserUpdateForm" action="AdminSingleUser!update" method="post">

<table>
    <tr>
        <td colspan="2">
            <s:url action="viewPictureCard!viewUserPictureCard" id="viewPictureCardURL">
                <s:param name="idUser">${userToUpdate.id}</s:param>
            </s:url>
            <img src="<s:property value="#viewPictureCardURL"/>"/>
        </td>
    </tr>

    <tr>
        <td>Status</td>
        <td>
            <s:select
                    name="userToUpdate.userStatus"
                    list="EnumUserStatusList"
                    />
        </td>
    </tr>

    <tr>
        <td>Organizer</td>
        <td>
            <s:select
                    name="userToUpdate.organizeEnabled"
                    list="#{'false': false , 'true':true}"
                    />

        </td>
    </tr>

    <tr>
        <td>
            ID
        </td>
        <td>
            <s:hidden name="userToUpdate.id"/>
            <s:property value="userToUpdate.id"/>
        </td>
    </tr>

    <tr>
        <td>
            Picture card
        </td>
        <td>
            <s:property value="guiUser.pictureCard.pictureCardStatus"/>
        </td>
    </tr>

    <tr>
        <td>
            Sex
        </td>
        <td>
            <s:property value="userToUpdate.sex"/>
        </td>
    </tr>

    <tr>
        <td>
            E-mail
        </td>
        <td>
            <s:property value="userToUpdate.email"/>
        </td>
    </tr>

    <tr>
        <td>
            Mobile phone
        </td>
        <td>
            <s:property value="userToUpdate.mobile"/>
        </td>
    </tr>

    <tr>
        <td>
            Birth date
        </td>
        <td>
            <s:date name="userToUpdate.birthDay" format="dd/MM/yyyy"/>
        </td>
    </tr>

    <tr>
        <td>
            Last login
        </td>
        <td>
            <s:date name="userToUpdate.lastLogin" format="dd/MM/yyyy"/>
        </td>
    </tr>

    <tr>
        <td>
            Last Ip
        </td>
        <td>
            <s:property value="userToUpdate.lastIP"/>
        </td>
    </tr>

    <tr>
        <td>
            Newsletter
        </td>
        <td>
            <s:property value="userToUpdate.newsletterEnabled"/>
        </td>
    </tr>

    <tr>
        <td>
            Friends
        </td>
        <td>
            <s:property value="guiUser.friendsCount"/>
        </td>
    </tr>

    <tr>
        <td>
            Games played
        </td>
        <td>
            <s:property value="guiUser.allPlayed"/>
        </td>
    </tr>

    <tr>
        <td>
            Games organized
        </td>
        <td>
            <s:property value="guiUser.allOrganized"/> ( ${guiUser.allOrganized} <u:translation key="label.Partite"/>
            + ${guiUser.challangeTot} <u:translation key="label.Sfide"/> )
        </td>
    </tr>

    <tr>
        <td>
            Anonymous
        </td>
        <td>
            <s:property value="userToUpdate.anonymousEnabled"/>
        </td>
    </tr>

    <tr>
        <td>
            Max invitations
        </td>
        <td>
            <s:textfield name="maxInvitations" value="%{userToUpdate.maxInvitations}" onkeypress="return numbersonly(this, event)" maxLength="5"/>
        </td>
    </tr>

    <tr>
        <td>
            Invitations left
        </td>
        <td>
                ${(userToUpdate.maxInvitations)-(inviteUsed)}
        </td>
    </tr>

    <tr>
        <td>
            Invitations used
        </td>
        <td>
            <s:property value="inviteUsed"/>
        </td>
    </tr>
</table>

<br/>

<table class="addressTable">

    <c:choose>
        <c:when test="${UserContext.idCountryFilter==0}">
            <tr>
                <td colspan="4">
                    Permissions
                </td>
            </tr>

            <tr>
                <td class="tdAddress">
                    <s:checkbox name="pictureChk"/>Picture cards
                </td>
                <td colspan="3">
                    Country:
                    <s:select
                            name="idCountryPicturePermission"
                            headerKey="0"
                            headerValue="Select"
                            list="countryList"
                            listKey="id"
                            listValue="name"
                            id="idSelectPictureCountry"
                            value="idCountryPicturePermission"
                            />
                </td>
            </tr>

            <tr>
                <td class="tdAddress">
                    <s:checkbox name="sportCenterChk"/>Sports centre
                </td>
                <td>
                    Country:
                    <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax"/>
                    <s:select
                            name="idCountrySportCenterPermission"
                            headerKey="0"
                            headerValue="Select"
                            list="countryList"
                            listKey="id"
                            listValue="name"
                            id="idSelectSportCenterCountry"
                            value="idCountrySportCenterPermission"
                            onchange="getProvincesByCountryParams($(this).value, 0, 0, 0, '%{provincesByCountryURL}',$('idSportCenterWaitProvinces'),$('idSelectSportCenterProvince'),$('idSelectSportCenterCity'))"
                            />
                </td>
                <td>
                    Province:
                    <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax"/>
                    <s:select
                            name="idProvinceSportCenterPermission"
                            headerKey="0"
                            headerValue="All"
                            list="provinceSportCenterList"
                            listKey="id"
                            listValue="name"
                            id="idSelectSportCenterProvince"
                            value="idProvinceSportCenterPermission"
                            onchange="getCitiesByProvinceParams($(this).value, 0, 0, 0, '%{citiesByProvinceURL}',$('idSportCenterWaitCities'),$('idSelectSportCenterCity'))"
                            />
              <span id="idSportCenterWaitProvinces" style="display:inline; visibility:hidden;">
                <img src="<s:url value="/images/spinner_small.gif" encode="false" />"/>
              </span>
                </td>
                <td>
                    City:
                    <s:select
                            name="idCitySportCenterPermission"
                            headerKey="0"
                            headerValue="All"
                            list="citySportCenterList"
                            listKey="id"
                            listValue="name"
                            value="idCitySportCenterPermission"
                            id="idSelectSportCenterCity"/>
              <span id="idSportCenterWaitCities" style="display:inline; visibility:hidden;">
                <img src="<s:url value="/images/spinner_small.gif" encode="false" />"/>
              </span>

                </td>
            </tr>

            <tr>
                <td class="tdAddress">
                    <s:checkbox name="challengeChk"/>Tournaments
                </td>
                <td>
                    Country:
                    <s:select
                            name="idCountryChallengePermission"
                            headerKey="0"
                            headerValue="Select"
                            list="countryList"
                            listKey="id"
                            listValue="name"
                            id="idSelectChallengeCountry"
                            value="idCountryChallengePermission"
                            onchange="getProvincesByCountryParams($(this).value, 0, 0, 0, '%{provincesByCountryURL}',$('idChallengeWaitProvinces'),$('idSelectChallengeProvince'),$('idSelectChallengeCity'))"
                            />
                </td>
                <td>
                    Province:
                    <s:select
                            name="idProvinceChallengePermission"
                            headerKey="0"
                            headerValue="All"
                            list="provinceChallengeList"
                            listKey="id"
                            listValue="name"
                            id="idSelectChallengeProvince"
                            value="idProvinceChallengePermission"
                            onchange="getCitiesByProvinceParams($(this).value, 0, 0, 0, '%{citiesByProvinceURL}',$('idChallengeWaitCities'),$('idSelectChallengeCity'))"
                            />
              <span id="idChallengeWaitProvinces" style="display:inline; visibility:hidden;">
                <img src="<s:url value="/images/spinner_small.gif" encode="false" />"/>
              </span>
                </td>
                <td>
                    City:
                    <s:select
                            name="idCityChallengePermission"
                            headerKey="0"
                            headerValue="All"
                            list="cityChallengeList"
                            listKey="id"
                            listValue="name"
                            value="idCityChallengePermission"
                            id="idSelectChallengeCity"/>
              <span id="idChallengeWaitCities" style="display:inline; visibility:hidden;">
                <img src="<s:url value="/images/spinner_small.gif" encode="false" />"/>
              </span>
                </td>
            </tr>

            <tr>
                <td class="tdAddress">
                    <s:checkbox name="linksChk"/>Links
                </td>
                <td colspan="3">
                    Country:
                    <s:select
                            name="idCountryLinksPermission"
                            headerKey="0"
                            headerValue="Select"
                            list="countryList"
                            listKey="id"
                            listValue="name"
                            id="idSelectLinksCountry"
                            value="idCountryLinksPermission"
                            />
                </td>
            </tr>

            <tr>
                <td colspan="4" class="tdAddress">
                    <s:checkbox name="newsChk"/>News
                </td>
            </tr>

            <tr>
                <td colspan="4" class="tdAddress">
                    <s:checkbox name="blogChk"/>Blog
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <br/><br/>
                    <a href="javascript: $('idUserUpdateForm').submit();" class="btn action1">Save</a>
                </td>
            </tr>
        </c:when>


        <c:otherwise>
            <tr>
                <td>
                    <br/><br/>
                    <a href="javascript: $('idUserUpdateForm').submit();" class="btn action1">Save</a>
                </td>
            </tr>
        </c:otherwise>
    </c:choose>

</table>

</s:form>

<br/>
<br/>
<br/>


</div>
</div>
</body>
</html>
