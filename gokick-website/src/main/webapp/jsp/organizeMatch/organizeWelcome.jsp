<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="../exception.jsp" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <jsp:include page="../../jspinc/commonHead.jsp" flush="true"/>
  <title><s:property value="%{getText('page.title.default')}"/></title>

  <script type="text/javascript">
    function fillTextArea()
    {
      var textToCheck = JQ.trim(JQ('#idTextArea').val());
      if (textToCheck == '')
      {
        if (JQ('#idTextArea').hasClass('readyToWrite'))
        {
          JQ('#idTextArea').removeClass('readyToWrite');
        }
        JQ('#idTextArea').val("<u:translation key='label.organizeWelcome.textArea'/>");
      }
    }
    function clearTextArea()
    {
      var textToCheck = JQ.trim(JQ('#idTextArea').val());
      if (textToCheck == "<u:translation key='label.organizeWelcome.textArea'/>")
      {
        JQ('#idTextArea').val("");
      }
    }
    function setReadyClassToTextArea()
    {
      if (!JQ('#idTextArea').hasClass('readyToWrite'))
      {
        JQ('#idTextArea').addClass('readyToWrite');
      }
    }
    function validateForm()
    {
      var textTocheck = JQ.trim(JQ('#idTextArea').val());
      var defaultText = "<u:translation key='label.organizeWelcome.textArea'/>";
      if (textTocheck == '' || textTocheck == defaultText || (JQ('#idAcceptChkBox').attr('checked') == false))
      {
        JQ('#idDivError').show();
        location.href = "#divError";
        return false;
      }
      else
      {
        return true;
      }
    }
  </script>

  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../../jspinc/statisticsScript.jsp" flush="true"/>
  <!--### end Google Analitics inclusion ###-->
</head>
<body>

<div class="wrapperOrganize">

  <!--### start header ###-->
  <jsp:include page="../../jspinc/header.jsp" flush="true"/>
  <!--### end header ###-->

  <!--### start leftcolumn ###-->
  <jsp:include page="../../jspinc/leftColumn.jsp" flush="true">
    <jsp:param name="tab" value="organize"/>
  </jsp:include>
  <!--### end leftcolumn ###-->

  <!--### start centralcolumn ###-->
  <div class="centralCol">

    <div class="topPageWrap">
      <jsp:include page="../../jspinc/headerTopBar.jsp" flush="true"/>
    </div>


    <!--### start mainContent ###-->
    <div class="mainContent mainContentIndent">
      <h1><u:translation key="label.title.organizeWelcome"/></h1>

      <p class="titleList"><u:translation key="label.subtitle.organizeWelcome"/>
      </p>

      <!--### start organize menu ###-->
      <jsp:include page="../../jspinc/organizeMenu.jsp" flush="true">
        <jsp:param name="tab" value="welcome"/>
      </jsp:include>
      <!--### end organize menu ###-->

      <!-- COLONNA SINISTRA-->
      <div class="organizeCol1">
        <u:translation key="label.organizeWelcome.leftColumn"/>
      </div>
      <!-- COLONNA DESTRA-->
      <div class="organizeCol2">
        <u:translation key="label.organizeWelcome.rightColumn"/>

        <u:isNotOrganizer>
          <c:set var="chkLabel">
            <u:translation key="label.condivido.valori"/>
          </c:set>
          <s:form action="organizerPresentation" method="post" theme="simple" id="idPresentationForm" onsubmit="return validateForm();">
            <s:hidden name="idUser"/>
            <div class="cntFld">
              <s:textarea name="presentationText" id="idTextArea" theme="simple" rows="4" value="%{getText('label.organizeWelcome.textArea')}" onclick="setReadyClassToTextArea();clearTextArea();JQ('#idDivError').hide();" onblur="fillTextArea()"/>
            </div>
            <p class="cntChBx">
              <s:checkbox name="acceptScope" value="false" id="idAcceptChkBox" onclick="JQ('#idDivError').hide();"/>
              <span>${chkLabel}</span>
            </p>

            <p class="cntBtn"><s:submit value="%{getText('label.organizeWelcome.submit')}" cssClass="btn"/></p>
          </s:form>

          <a name="divError"></a>
          <!-- ERRORE IN CASO DI MANCATA VALIDAZIONE FORM-->

          <div class="error" style="display: none" id="idDivError">
            <u:translation key='error.organizeWelcome.presentationForm'/>
          </div>

          <!-- ERRORE IN CASO DI MANCATA VALIDAZIONE FORM-->


        </u:isNotOrganizer>
      </div>
      <br class="clear"/>

      <div class="cntCardLst">
        <h3><u:translationArgs arg01="${totOrganizers}" key="label.organizeWelcome.dreamTeamTitle"/></h3>


        <c:forEach items="${fatherOrganizerList}" var="organizers" varStatus="indexFather">
          <div id="idDivTable_${indexFather.count}" style="display : ${indexFather.count > 1?'none':''}">
            <table>
              <c:forEach items="${organizers}" var="userInfoList">
                <tr>
                  <td>
                    <c:forEach items="${userInfoList}" var="userInfoPic">
                      <s:url action="viewAvatar" var="imageUrl">
                        <s:param name="idUser">
                          ${userInfoPic.id}
                        </s:param>
                      </s:url>
                      <s:url action="userDetails" var="userDetailsURL">
                        <s:param name="idUser">${userInfoPic.id}</s:param>
                        <s:param name="tab">scheda</s:param>
                      </s:url>
                      <s:set name="completeName">${userInfoPic.name}_${userInfoPic.surname}</s:set>
                      <s:set name="nameDet">
                        <s:property value="#completeName" escapeJavaScript="true"/>
                      </s:set>
                      <s:set name="titlePic">${userInfoPic.name}&nbsp;${userInfoPic.surname} - ${userInfoPic.city} - ${userInfoPic.country}</s:set>
                      <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');">
                        <img src="${imageUrl}" alt="" title="${titlePic}"/>
                      </a>
                    </c:forEach>
                  </td>
                </tr>
              </c:forEach>
            </table>

            <c:set var="fatherSize">
              <s:property value="fatherOrganizerList.size"/>
            </c:set>
            <div class="lnkMore" id="idDivLinkMore_${indexFather.count}">
              <c:if test="${fatherSize > indexFather.count}">
                <a href="javascript:void(0);" onclick="JQ('#idDivTable_${indexFather.count+1}').show();JQ('#idDivLinkMore_${indexFather.count}').hide();">
                  <u:translation key="label.show.more"/>
                </a>
              </c:if>
            </div>

          </div>
        </c:forEach>

        <div class="dreamTemInfo"><u:translation key="label.organizeWelcome.DreamTemInfo"/></div>

      </div>

    </div>
    <!--### end mainContent ###-->

  </div>
  <!--### end centralcolumn ###-->

  <!--### start rightcolumn ###-->
  <jsp:include page="../../jspinc/rightcolumn.jsp" flush="true"/>
  <!--### end rightcolumn ###-->

  <!--### start footer ###-->
  <jsp:include page="../../jspinc/footer.jsp" flush="true"/>
  <!--### end footer ###-->

</div>
<!--### end wrapper ###-->
</body>
</html>
