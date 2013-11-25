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
    <title>Pictures</title>
    <script type="text/javascript">
        function checkSelected() {
            var ctrl = false;
            $$('.chkSelectPicure').each(function (c) {
                if (c.checked)
                    ctrl = true;
            });
            if (ctrl == false)
                alert('No selection');
            else
                $('idAdminPicturesUpdateForm').submit();
        }

    </script>
</head>

<body>


<div class="wrapper">

    <!--### start header ###-->
    <jsp:include page="../jspinc/header.jsp" flush="true"/>
    <!--### end header ###-->

    <div style="padding-left:20px;">

        <h1>${picTot} Picture Cards</h1>

        <p style="color:red">
            <strong>
                ${picTot - picPendingTot - picRefusedTot} CURRENT
                &nbsp;
                ${picPendingTot} PENDING
                &nbsp;
                ${picRefusedTot} REFUSED
            </strong>
        </p>

        <br/><br/>

        <form action="AdminPictures!viewAll.action" method="post" id="idAdminPicturesSearchForm">
            <s:select
                    name="pictureStatus"
                    list="enumPictureStatusList"/>

            <a href="javascript: $('idAdminPicturesSearchForm').submit();" class="btn action1">Search</a>
        </form>

        <s:if test="picSelectedTot > 0">
            <form action="AdminPictures!update.action" method="post" id="idAdminPicturesUpdateForm">

                <display:table id="pictureList" name="pictureCardFatherList" pagesize="4" requestURI="AdminPictures!viewAll.action">
                    <display:column>
                        <table>
                            <tr>
                                <c:forEach items="${pictureList}" var="picture">

                                    <s:set var="created">${picture.pictureCard.created}</s:set>
                                    <s:set var="idPicture">${picture.pictureCard.id}</s:set>
                                    <s:set var="idUser">${picture.user.id}</s:set>
                                    <c:set var="country">${picture.user.country.name}</c:set>
                                    <td>
                                        <a href="javascript: openPopupUserDetails('${picture.detailsUrl}','${picture.user.firstName}_${picture.user.lastName}');">
                                            id:${idUser} fig:${idPicture}
                                            up:<fmt:formatDate value="${picture.pictureCard.created}" pattern="dd/MM/yyyy"/>
                                            <br/>
                                        </a>
                                        country: ${country}
                                        <br/>

                                        <s:url action="viewPictureCard!viewUserPictureCardById" id="viewPictureCardURL">
                                            <s:param name="idPicture">${idPicture}</s:param>
                                        </s:url>
                                        <img src="<s:property value="#viewPictureCardURL"/>" onclick="$('idPictureChk_'+${idPicture}).checked = !($('idPictureChk_'+${idPicture}).checked)"/>

                                        <p align="center">
                                                <%--${picture.pictureCardStatus}--%>
                                            <s:checkbox
                                                    name="pictureCheckList"
                                                    fieldValue="%{#idPicture}"
                                                    cssClass="chkSelectPicure"
                                                    id="idPictureChk_%{#idPicture}"
                                                    onchange="$('idUserChk_%{#idUser}').checked=this.checked;"

                                                    />
                      <span style="display: none;visibility:collapse">
                      <s:checkbox
                              name="userCheckList"
                              fieldValue="%{#idUser}"
                              cssClass="chkSelectPicure"
                              id="idUserChk_%{#idUser}"
                              />
                      </span>
                                        </p>
                                    </td>
                                </c:forEach>
                            </tr>
                        </table>

                    </display:column>
                </display:table>


                <p>
                    <s:checkbox id="idChkSelectAll" name="selectAll" onclick="javascript:changeCheckBox('.chkSelectPicure', this.checked);"/>
                    Select all
                </p>
                <br/><br/>

                <strong>Evaluate picture cards</strong>
                <br/> <br/>

                <s:radio name="newPictureStatus" list="enumPictureStatusList" value="pictureStatus"/>
                <br/> <br/>
                <a href="javascript: checkSelected();" class="btn action1">Save</a>
                &nbsp;
                <s:checkbox name="sendMessage"/>send message after saving

            </form>

        </s:if>
        <s:else>

            <br/><br/>
            No ${pictureStatus}
        </s:else>
        <s:actionerror/>

        <br/><br/><br/>
    </div>
</div>
<!--### end wrapper ###-->
</body>
</html>
