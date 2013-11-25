<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <script type="text/javascript">


      function checkCountryforConditionsContent()
      {
        var value=($('idSelectCountry').value);
        
        if(value=='75')
        {
          
          new Ajax.Updater('idSpanTermsAndConditions', '<s:url action="showTermsAndConditionByLang" namespace="/ajax"/>',
          {
            parameters: {lang: value}
          });
          new Ajax.Updater('idSpanPrivacy', '<s:url action="showPrivacyByLang" namespace="/ajax"/>',
          {
            parameters: {lang: value}
          });
        }
        else
        {
          new Ajax.Updater('idSpanTermsAndConditions', '<s:url action="showTermsAndConditionByLang" namespace="/ajax"/>',
          {
            parameters: {}
          }); 
          new Ajax.Updater('idSpanPrivacy', '<s:url action="showPrivacyByLang" namespace="/ajax"/>',
          {
            parameters: {}
          }); 
        }
      }

        function checkUniqueEmail(email)
        {
          var options = {
            method: 'GET',
            parameters: { 
              email: email,
              dummy: Math.random()
            },
            onSuccess: function(t) {
              var response = t.responseJSON;
              if(response.unique == false )
                $('emailError').show();
              else
                $('emailError').hide();
            }
          };
          new Ajax.Request('<s:url action="checkUniqueEmail" namespace="/ajax" />', options);
        }

        function checkUniqueForumNickname(forumNickname)
        {
          var options = {
            method: 'GET',
            parameters: { 
                forumNickname: forumNickname,
                dummy: Math.random()
              },
            onSuccess: function(t) {
              var response = t.responseJSON;
              if(response.unique == false )
                $('forumNicknameError').style.display = 'block';
              else
                $('forumNicknameError').style.display = 'none';
            }
          };
          new Ajax.Request('<s:url action="checkUniqueForumNickname" namespace="/ajax" />', options);
        }

        function changeCaptchaQuestion()
        {
          var options = {
            method: 'GET',
            onSuccess: function(t) {
              var response = t.responseJSON;
              $('idCaptchaValue1').value = response.captchaValue1;
              $('idCaptchaValue2').value = response.captchaValue2;
              $('idLblCaptchaValues').innerHTML = response.captchaValue1 + " + " + response.captchaValue2;
            },
            parameters: { dummy: Math.random()}
          };
          new Ajax.Request('<s:url action="changeCaptchaQuestion" namespace="/ajax" />', options);
        }

    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
        <body onload="checkCountryforConditionsContent()">

    <div class="wrapper">

      <!--### start header ###-->
      <jsp:include page="../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true" >
        <jsp:param name="tab" value="register"/>
      </jsp:include>
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">

          <div class="topPageWrap">
              <h1 class="titleBig"><u:translation key="label.registerCreaAccount" /></h1>
          </div>

        <!--### start mainContent ###-->
        <div class="mainContent">

          

          <%--p class="stepBar">
            <span>1 &bull; <u:translation key="label.Spirito" /></span>
            <span class="active" href="#">2 &bull; <u:translation key="label.Account" /></span>
            <span>3 &bull; <u:translation key="label.picture" /></span>
          </p--%>
          <s:form action="user!insert" method="post"  >

            <s:hidden name="captchaValue1" id="idCaptchaValue1" />
            <s:hidden name="captchaValue2" id="idCaptchaValue2" />
            <s:set name="hasError" value="errorsPresent"/>

            <s:if test="#hasError==true">
              <p class="topAlert">
                <strong>
                <u:translation key="error.correggiRosso" />
                </strong>
              </p>
            </s:if>
           
            <table class="registration">
              <tr>
                <th style="width:120px;">&nbsp;</th><th><h2><u:translation key="label.DatiGiocatore" /></h2></th>
                <td class="infoReg" style="vertical-align:bottom;">
                  <p>
                    <u:translation key="label.tuttiCampiObbligatori" />
                  </p>
                </td>
              </tr>
              <tr>
                <td class="label"><s:property value="%{getText('label.firstname')}" /></td>
                <td>
                  <!--NOME-->
                  <s:textfield name="userToInsert.firstName" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:150px;" />
                  <s:fielderror name="errorFirstName" fieldName="userToInsert.firstName" />
                </td>
                <td class="infoReg" rowspan="4">
                  <u:translation key="label.registerInfoNomecognome" />
                </td>
              </tr>
              <tr>
                <td class="label"><s:property value="%{getText('label.lastName')}" /></td>
                <td>
                  <!--COGNOME-->
                  <s:textfield name="userToInsert.lastName" size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:150px;" />
                  <s:fielderror name="errorLastName" fieldName="userToInsert.lastName" />
                </td>
              </tr>
              <tr>
                <!--RUOLO-->
                <td class="label"><s:property value="%{getText('label.role')}" /></td>
                <td>
                  <s:select
                    name="userToInsert.playerRole.id"
                    list="playerRoleList"
                    headerKey="0"
                    headerValue="%{getText('label.seleziona')}"
                    listKey="id"
                    listValue="name"
                    />
                  <s:fielderror name="errorSex" fieldName="userToInsert.playerRole" />
                </td>
              </tr>
              <tr>
                <!--SESSO-->
                <td class="label"><s:property value="%{getText('label.sex')}" /></td>
                <td>
                  <s:select
                    name="userToInsert.sex"
                    list="#{'M':getText('label.sex.male'),'F':getText('label.sex.female')}" />
                  <s:fielderror name="errorSex" fieldName="userToInsert.sex" />
                </td>
              </tr>

              <tr>
                <!--CAP-->
                <td class="label"><s:property value="%{getText('label.residenceCap')}" /></td>
                <td>
                  <s:textfield name="userToInsert.cap" size="10" maxLength="10" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                  <s:fielderror name="errorCap" fieldName="userToInsert.cap" />
                </td>
              </tr>

              <%--tr> //decommentare per abilitare forum, anche userAction ValidateInsert
                <td class="label">
                  <s:property value="%{getText('label.forumNickname')}" />:
                </td>
                <td>
                  <s:textfield name="userToInsert.forumNickname" size="25" maxLength="15" onblur="checkUniqueForumNickname($(this).value)" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:150px;" />
                  <s:fielderror name="errorForumNickname" fieldName="userToInsert.forumNickname" />
                  <div style="display:none;" id="forumNicknameError"><ul class="errorMessage"><li><s:property value="%{getText('error.forumNickname.notunique')}" /></li></ul></div>
                </td>
                <td class="infoReg">
                  <u:translation key="label.accountInfoNick" />
                </td>
              </tr--%>
              
              <tr><th>&nbsp;</th><th><h2><u:translation key="label.registerDoveGiochi" />?</h2></th></tr>

              <tr>
                <td class="label"><s:property value="%{getText('label.country')}" /></td>
                <td>
                  <!--NAZIONE-->
                  <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
                  <s:select
                    name="idCountry"
                    headerKey="0"
                    headerValue="%{getText('label.select.country')}"
                    list="countyList"
                    listKey="id"
                    listValue="name"
                    id="idSelectCountry"
                    value="idCountry"
                    onchange="getProvincesByCountry($(this).value, 0, 0, 0, '%{provincesByCountryURL}');checkCountryforConditionsContent();"
                    cssStyle="width:150px;" />
                  <s:fielderror name="errorCountry" fieldName="userToInsert.country" />
                </td>
                <td class="infoReg" rowspan="3">
                  <strong><u:translation key="label.accountInfoLuogo" /></strong><br />
                  <u:translation key="label.accountInfoGiocatoriCampi" />
                </td>
              </tr>

              <tr>
                <td class="label"><s:property value="%{getText('label.province')}" /></td>
                <td>
                  <!--PROVINCIA-->
                  <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
                  <s:select
                    name="idProvince"
                    headerKey="0"
                    headerValue="%{getText('label.select.province')}"
                    list="provinceList"
                    listKey="id"
                    listValue="name"
                    id="idSelectProvince"
                    value="idProvince"
                    onchange="getCitiesByProvince($(this).value, 0, 0, 0, '%{citiesByProvinceURL}')"
                    cssStyle="width:150px;" />
                  <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                  <s:fielderror name="errorProvince" fieldName="userToInsert.province" />
                </td>
              </tr>

              <tr>
                <td class="label"><s:property value="%{getText('label.city')}" /></td>
                <td>
                  <!--CITTA'-->
                  <s:select
                    name="idCity"
                    headerKey="0"
                    headerValue="%{getText('label.select.city')}"
                    list="cityList"
                    listKey="id"
                    listValue="name"
                    value="idCity"
                    id="idSelectCity"
                    cssStyle="width:150px;" />
                  <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                  <s:fielderror name="errorCity" fieldName="userToInsert.city" />
                </td>
              </tr>

              <tr><th>&nbsp;</th><th><h2><u:translation key="label.DatiLogin" /></h2></th></tr>

              <tr>
                <td class="label">
                  <s:property value="%{getText('label.email')}" />
                </td>
                <td>
                  <s:textfield name="userToInsert.email" size="25" onblur="checkUniqueEmail($(this).value)" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:150px;" />
                  <s:fielderror name="errorEmail" fieldName="userToInsert.email" />
                  <div style="display:none;" id="emailError"><ul class="errorMessage"><li><s:property value="%{getText('error.email.notunique')}" /></li></ul></div>
                </td>
                <td class="infoReg" rowspan="3">
                  <strong><u:translation key="label.NoSpam" /></strong>
                  <u:translation key="label.accountInfoSpam" /> <u:translation key="label.registerRiceveraiMaill"  />
                </td>
              </tr>

              <tr>
                <td class="label"><s:property value="%{getText('label.password')}" /></td>
                <td>
                  <s:password name="userToInsert.password" size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:150px;" />
                  <s:fielderror name="errorPassword" fieldName="userToInsert.password" />
                </td>
              </tr>

              <tr>
                <td class="label"><s:property value="%{getText('label.repeatPassword')}" /></td>
                <td>
                  <s:password name="repeatPassword" size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:150px;" />
                  <s:fielderror name="errorRepeatPassword" fieldName="repeatPassword" />
                </td>
              </tr>

              </table>

              <table class="registration">

              <tr>
                <th style="width:120px;">&nbsp;</th>
                <th style="padding-left:15px;"><h2><u:translation key="label.CGU" />, <u:translation key="label.Privacy" />, <u:translation key="label.newsletter.2" /></h2></th>
              </tr>

              <tr>
                <td>&nbsp;</td>
                <td colspan="2">
                  <table>
                    <tr>
                      <td>
                        <s:checkbox name="userToInsert.generalTermsAccepted" />
                      </td>
                      <td>
                        <s:property value="%{getText('label.accettazioneCondizioniUtilizzo.1')}" />
                        <span id="idSpanTermsAndConditions">
                        <a  class="linkUnderline" style="color:black; " href="javascript: openPopupTermsAndConditions('<s:url action="termsAndConditionsInfoPopup.action"/>', '');">
                          <s:property value="%{getText('label.accettazioneCondizioniUtilizzo.2')}" />
                        </a>
                        </span>
                        <s:fielderror name="errorGeneralTermsAccepted" fieldName="userToInsert.generalTermsAccepted" />
                      </td>
                    </tr>

                    <tr>
                      <td>
                        <s:checkbox name="userToInsert.privacyAccepted" />
                      </td>
                      <td>
                        <s:property value="%{getText('label.accettazionePrivacy.1')}" />
                        <span id="idSpanPrivacy">
                          <a class="linkUnderline" style="color:black;" href="javascript: openPopupPrivacy('<s:url action="accettazionePrivacyInfoPopup.action"/>', '');" >
                            <s:property value="%{getText('label.accettazionePrivacy.2')}" />
                          </a>
                        </span>
                        <s:property value="%{getText('label.accettazionePrivacy.3')}" />
                        <s:fielderror name="errorPrivacyAccepted" fieldName="userToInsert.privacyAccepted" />
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <s:checkbox name="userToInsert.newsletterEnabled" />
                      </td>
                      <td>
                        <s:property value="%{getText('label.newsletter.1')}" />
                        <a class="linkUnderline"  style="color:black;" href="javascript: openPopupNewsletter('<s:url action="newsletter.action"/>', '');"><s:property value="%{getText('label.newsletter.2')}" /></a>
                        <s:property value="%{getText('label.newsletter.3')}" />
                      </td>

                    </tr>
                    
                    <tr>
                      <td>&nbsp;</td>
                      <td>
                        <br />
                        <s:property value="%{getText('label.captcha.info')}" />

                        <p>
                          <strong>
                            <a class="siteColor" href="javascript:void(0);" onclick="changeCaptchaQuestion()" >
                              <s:property value="%{getText('label.captcha.newquestion')}" /> &raquo;
                            </a>
                          </strong>
                          &nbsp;<s:property value="%{getText('label.captcha.sum')}" />
                          
                          <span id="idLblCaptchaValues"><s:property value="captchaValue1" /> + <s:property value="captchaValue2" /></span>

                          <s:property value="%{getText('label.captcha.sum.is')}" />:
                          <s:textfield name="captchaValueSum" size="10" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                        </p>

                        <s:fielderror name="errorCaptchaValueSum" fieldName="captchaValueSum" />
                        
                        <br />
                        <p>
                          <s:submit cssClass="btn" value="%{getText('btn.continue')}" />
                        </p>
                        <br />
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>


          </s:form>

        </div>
        <!--### end mainContent ###-->

      </div>
      <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->
      <jsp:include page="../jspinc/rightcolumn.jsp" flush="true" />
      <!--### end rightcolumn ###-->

      <!--### start footer ###-->
      <div class="footer">
        <p>
          <u:translation key="label.copyright"/>
        </p>
      </div>
      <u:refresher />
      <!--### end footer ###-->

    </div>

  </body>
</html>
