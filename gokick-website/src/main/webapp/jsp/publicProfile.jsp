<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>


<s:set value="userToShow" var="varUserToShow"/>
<html>
<head>

  <jsp:include page="../jspinc/commonHead.jsp" flush="true">
    <jsp:param name="dataTable" value="true"/>
  </jsp:include>

  <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all"/>
  <title><s:property value="%{getText('page.title.default')}"/></title>

  <script type="text/javascript">

    function submitFeedbackForm() {
      if ($('idSelectTech').value == "") {
        alert("<u:translation key='label.valorizzare'/> <u:translation key='label.technical'/>");
        return;
      }
      ;
      if ($('idSelectReliability').value == "") {
        alert("<u:translation key='label.valorizzare'/> <u:translation key='label.Affidabilita'/>");
        return;
      }
      if ($('idSelectFairplay').value == "") {
        alert("<u:translation key='label.valorizzare'/> <u:translation key='label.fairplay'/>");
        return;
      }
      if ($('idtextDetail').value == "" || $('idtextDetail').value == "Inserisci un comm  ento...") {
        alert("<u:translation key='label.InserireMessaggio'/>");
        return;
      }
      $('idFeedbackForm').submit();
    }

    function submitPrivateMessageForm() {
      if ($('idPrivateMessage').value == "" || $('idPrivateMessage').value == "messaggio priva  to...") {
        alert("<u:translation key='error.matchComment.TestoMancante'/>");
        return;
      }
      $('idPrivateMessageForm').submit();
    }

    function submitNotifyAbuseForm() {
      $('idDivMessageSent').style.visibility = "hidden";
      var text = $('idAbuseText').value.trim();
      if ($('idAbuseList').value == 0 ||
        (text == '' || text == '<u:translation key="label.scheda.abuso.Segnalazione.textArea"/>')) {
        $('idDivError').style.visibility = "visible";
      }
      else {
        $('idDivError').style.visibility = "hidden";
        $('idFormAbuse').submit();
      }
    }

    function clearAbuseText() {
      var text = $('idAbuseText').value;
      if (text == '<u:translation key="label.scheda.abuso.Segnalazione.textArea"/>')
        $('idAbuseText').value = '';
    }


  </script>
  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../jspinc/statisticsScript.jsp" flush="true"/>
  <!--### end Google Analitics inclusion ###-->
</head>
<body>

<div class="wrapperHP">

  <!--### start header ###-->
  <jsp:include page="../jspinc/header.jsp" flush="true"/>
  <!--### end header ###-->

  <!--### start leftcolumn ###-->
  <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
    <jsp:param name="tab" value="home"/>
  </jsp:include>
  <!--### end leftcolumn ###-->

  <!--### start centralcolumn ###-->
  <div class="centralCol">
    <div class="topPageWrap">
      <jsp:include page="../jspinc/headerTopBar.jsp" flush="true"/>
    </div>


    <!--### start mainContent ###-->
    <div class="mainContent mainHP">
      <u:idBoxScheda/>
      <div id="idBoxScheda">

        <table class="infoList">
          <tr>
            <td style="width:22%;"><s:property value="%{getText('label.giocaA')}"/></td>
            <td class="valueField"><s:property value="userToShow.city"/> <s:property
              value="%{getText('label.separatore')}"/>
              <s:property value="userToShow.province"/> <s:property value="%{getText('label.separatore')}"/>
              <s:property value="userToShow.country"/>
            </td>

            <td rowspan="12" class="picCol">
              <s:url action="pictureCard" id="viewPictureCardURL" method="viewPlayerPictureCard">
                <s:param name="idUser" value="userToShow.id"></s:param>
              </s:url>
              <img src="<s:property value="#viewPictureCardURL"/>"
                   title="<s:property value="userToShow.name" /> <s:property value="userToShow.surname" />"/>

              <div class="left">
                <s:property value="%{getText('label.condizione')}"/><br/>
                <u:printUserPhysicalCondition userToShow="${varUserToShow}" description='true'/>
              </div>
              <div class="right">
                <s:property value="%{getText('label.mercato')}"/><br/>
                    <span class="valueField">
                      <s:if test="userToShow.marketEnabled">
                        <img src="<s:url value="/images/gioca.gif"/>" alt=""/><br/>
                        <s:property value="%{getText('label.sulMercato')}"/>
                      </s:if>
                      <s:else>
                        <img src="<s:url value="/images/notgioca.gif" />" alt="" style="visibility:hidden;"/><br/>
                        <s:property value="%{getText('label.nonSulMercato')}"/>
                      </s:else>
                    </span>
              </div>

            </td>

          </tr>
          <tr>
            <td>
              <s:property value="%{getText('label.natoA')}"/>
            </td>
            <td class="valueField">
              <s:if test="userToShow.withBirthdayInfo">
                <s:property value="userToShow.birthdayCity"/> <s:property value="%{getText('label.separatore')}"/>
                <s:property value="userToShow.birthdayProvince"/> <s:property value="%{getText('label.separatore')}"/>
                <s:property value="userToShow.birthdayCountry"/>
              </s:if>
              <s:else>
                <s:property value="userToShow.birthdayCountry"/>
              </s:else>
            </td>
          </tr>
          <tr>
            <td><s:property value="%{getText('label.etaAltPes')}"/></td>
            <td class="valueField">
              <u:printUserEtaAltezzaPeso userToShow="${varUserToShow}"/>
            </td>
          </tr>
          <tr>
            <td><s:property value="%{getText('label.ruoloPiedeMaglia')}"/></td>
            <td class="valueField">
              <u:printUserInfoRuoloPiedeMaglia userToShow="${varUserToShow}"/>
            </td>
          </tr>
          <tr>
            <td><s:property value="%{getText('label.caratteristica')}"/></td>
            <td class="valueField"><s:property value="userToShow.playerMainFeature"/></td>
          </tr>
          <tr>
            <td><p><s:property value="%{getText('label.tifo')}"/></p></td>
            <td class="valueField"><p><s:property value="userToShow.footballTeam"/></p></td>
          </tr>
          <tr>
            <td><s:property value="%{getText('label.idolo')}"/></td>
            <td class="valueField"><s:property value="userToShow.infoFavouritePlayer"/></td>
          </tr>
          <%-- <tr>
                      <td><s:property value="%{getText('label.occupazione')}" /></td>
                      <td class="valueField">Agenzia di comunicazione</td>
          </tr> --%>
          <tr>
            <td><s:property value="%{getText('label.hobby')}"/></td>
            <td class="valueField"><s:property value="userToShow.infoHobby"/></td>
          </tr>
          <tr>
            <td><s:property value="%{getText('label.sogno')}"/></td>
            <td class="valueField"><s:property value="userToShow.infoDream"/></td>
          </tr>
          <tr>
            <td><p><s:property value="%{getText('label.goKickerDal')}"/></p></td>
            <td class="valueField"><p><utl:formatDate date="${userToShow.created}" formatKey="format.date_9"/></p></td>
          </tr>
          <tr>
            <td><s:property value="%{getText('label.giocate')}"/></td>
            <td class="valueField">${statisticInfoTot.allTot > 0 ? statisticInfoTot.allTot : '0' } <u:translation
              key="label.udmPartite"/> - <u:translation key="label._affidabilita"/> <s:property
              value="squadInfoToShow.ownerStatisticInfo.reliability"/>%
            </td>
          </tr>
          <tr>
            <td><s:property value="%{getText('label._Organizzate')}"/></td>

            <td class="valueField">${userToShow.recordedMatches > 0 ? userToShow.recordedMatches : '0' } <u:translation
              key="label.udmPartite"/></td>

          </tr>
          <tr>
            <td style="border:0;">
              <br/>
            </td>
          </tr>
          <tr class="spot">

            <td><p><s:property value="%{getText('label.annuncio')}"/></p></td>
            <td class="valueField" colspan="2">
              <p><s:property value="userToShow.infoAnnounce"/></p>
            </td>
          </tr>
          <tr class="spot">
            <td>&nbsp;</td>
            <td class="valueField" colspan="2">
              <p></p>
            </td>
          </tr>
          <tr class="spot">
            <td>&nbsp;</td>
            <td class="valueField" style="padding-right:0;" colspan="2">

              <s:include value="../jspinc/userDetails_contact.jsp"/>

            </td>
          </tr>
        </table>

      </div>
    </div>
    <!--### end centralcolumn ###-->
    <!--### start rightcolumn ###-->
    <u:showForCobrandTypes types="Complete">
      <div class="rightColHP">
          <%--<u:translation key="label.homeBoxEsterno" />--%>
        <u:customContent position="right-sidebar" width="250" height="800"/>
      </div>
    </u:showForCobrandTypes>
    <!--### end rightcolumn ###-->
    <!--### start footer ###-->
    <jsp:include page="../jspinc/footer.jsp" flush="true"/>
    <!--### end footer ###-->

  </div>
</div>
</body>
</html>
