<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
  <title><s:property value="%{getText('page.title.default')}"/></title>
  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
  <!--### end Google Analitics inclusion ###-->
</head>
<body>
<div class="wrapper">

<!--### start header ###-->
<jsp:include page="../jspinc/header.jsp" flush="true"/>
<!--### end header ###-->

<!--### start leftcolumn ###-->
<jsp:include page="../jspinc/leftColumn.jsp" flush="true"/>
<!--### end leftcolumn ###-->

<!--### start centralcolumn ###-->
<div class="centralCol">

 <div class="topPageWrap">
              <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
          </div>  

 


  <!--### start mainContent ###-->
  <div class="mainContentIndent">

    <h1><u:translation key="label.Preferenze"/></h1>

    <div class="clearfix contTab contTabFix">
      <p class="left"><u:translation key="label.preferenzeModifica"/></p>

      <a class="active" style="border-right:none;" href="<s:url action='userPreferences!input' />"><u:translation
              key="label.Preferenze"/></a>
      <a href="<s:url action='userAccount!input' />"><u:translation key="label.Account"/></a>

    </div>

    <s:form id="idPreferencesForms" action="userPreferences!update" method="post">
      <table class="preferences">

        <!--              Avvisi Email-->
        <tr>
          <th valign="middle" width="15px">
            <img src="<s:url value="/images/icon_mail.jpg" encode="false" />"/>
          </th>
          <th>
            <u:translation key="label.preferencesAvvisiEmail"/>:
          </th>
          <th></th>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesAperturaIscrizioni"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.alertOnRegistrationStart"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesPartitaAnnullata"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.alertOnChange"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesIscrizioneRichiesta"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.alertOnSquadOutAccepted"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesRichiestaAmicizia"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.alertOnSquadRequest"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesRichiestaAmiciziaAccettata"/>
          </td>
          <td class="right" style="border-bottom:solid 1px #FFFFFF;">
            <s:checkbox name="userToUpdate.alertOnSquadInsert"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesPubblicazionePagelle"/>
          </td>
          <td class="right" style="border-bottom:solid 1px #FFFFFF;">
            <s:checkbox name="userToUpdate.alertOnReportCreated"/>
          </td>
        </tr>

        <!-- Condivisione facebook -->
        <tr>
          <th valign="middle" width="15px">
            <img src="<s:url value="/images/icon_facebook.gif" encode="false" />"/>
          </th>
          <th>
            <u:translation key="label.preferenzeCondivisioneFacebook"/>
          </th>
          <th></th>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesPostFacebookPartita"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.facebookPostOnMatchCreation"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesPostFacebookIscrizione"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.facebookPostOnMatchRegistration"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesPostFacebookPagelle"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.facebookPostOnMatchRecorded"/>
          </td>
        </tr>


        <!--               Riservatezza -->
        <tr>
          <th valign="middle" width="15px">
            <img src="<s:url value="/images/icon_privacy.jpg" encode="false" />"/>
          </th>
          <th>
            <strong><u:translation key="label.Riservatezza"/></strong>
          </th>
          <th></th>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesAnonimo"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.anonymousEnabled"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesNascondiAmici"/>
          </td>
          <td class="right">
            <s:checkbox name="squad.hiddenEnabled"/>
          </td>
        </tr>


        <!--               NewsLetter -->
        <tr>
          <th valign="middle" width="15px">
            <img src="<s:url value="/images/icon_tazza.jpg" encode="false" />"/>
          </th>
          <th>
            <strong> <u:translation key="label.newsletter.2"/></strong>
          </th>
          <th></th>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesRiceviNewsletter"/>
          </td>
          <td class="right">
            <s:checkbox name="userToUpdate.newsletterEnabled"/>
          </td>
        </tr>


        <!--               Cancellazione account -->
        <tr>
          <th valign="middle" width="15px">
            <img src="<s:url value="/images/icon_noentry.jpg" encode="false" />"/>
          </th>
          <th>
            <strong><u:translation key="label.CancelAccount"/></strong>
          </th>
          <th></th>
        </tr>

        <tr>
          <td colspan="2">
            <u:translation key="label.preferencesCancelAccountInfo"/>
          </td>
          <td class="right">
            <s:url action="userCancelAccount!input" var="deleteUrl"/>
            <a href="${deleteUrl}">&gt;&gt;</a>
          </td>
        </tr>

      </table>

      <p class="centred">
        <s:submit cssClass="btn" value="%{getText('label.salva')}"/>
      </p>

    </s:form>

  </div>
  <!--### end maincontent ###-->


</div>
<!--### end centralcolumn ###-->

<!--### start rightcolumn ###-->
<jsp:include page="../jspinc/rightcolumn.jsp" flush="true"/>
<!--### end rightcolumn ###-->

<!--### start footer ###-->
<jsp:include page="../jspinc/footer.jsp" flush="true"/>
<!--### end footer ###-->

</div>

</body>
</html>
