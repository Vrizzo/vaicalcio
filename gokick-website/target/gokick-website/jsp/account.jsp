<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include  page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
    
    <script type="text/javascript">

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
              $('emailError').style.display = 'block';
            else
              $('emailError').style.display = 'none';
          }
        };
      <s:url id="checkUniqueEmailURL" action="checkUniqueEmail" namespace="/ajax" />
          new Ajax.Request('<s:property value="#checkUniqueEmailURL"/>', options);
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
      <s:url id="checkUniqueForumNicknameURL" action="checkUniqueForumNickname" namespace="/ajax" />
          new Ajax.Request('<s:property value="#checkUniqueForumNicknameURL"/>', options);
        }
        
    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body>
      
    <div class="wrapper">

      <!--### start header ###-->
      <jsp:include page="../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true" />
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">

         <div class="topPageWrap">
              <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
          </div>    

      

        <!--### start mainContent ###-->
        <div class="mainContentIndent account">

          <h1><u:translation key="label.Account"/></h1>

          <div class="clearfix contTab contTabFix">
            <p class="left"><u:translation key="label.accountModificaDati"/></p>


            <a href="<s:url action="userPreferences!input" />"><u:translation key="label.Preferenze" /></a>
            <a class="active" href="<s:url action="userAccount!input" />" class="active"><u:translation key="label.Account"/></a>

          </div>

          <s:form id="userAccountForm" action="userAccount!update" method="post" validate="save">

            <s:set name="hasError" value="errorsPresent"/>

            <s:if test="#hasError==true">
              <p class="topAlert"><strong><u:translation key="error.correggiRosso"/></strong></p>
            </s:if>

            <s:hidden name="userToUpdate.id"/>

            <table>
              <tr>
                <td style="width:110px;">&nbsp;</td>
                <th colspan="2">
                  <p><u:translation key="label.accountDatiGokicker"/></p>
                </th>

              </tr>

              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.firstname')}" /></td>
                <td>
                  <s:fielderror name="errorFirstName" fieldName="userToUpdate.firstName" />
                  <s:textfield name="userToUpdate.firstName" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:140px;" />
                  
                </td>
                <td class="infoReg" rowspan="4">
                  <u:translation key="label.accountInfoNomeCognome"/>
                </td>
              </tr>
              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.lastName')}" /></td>
                <td>
                  <s:fielderror name="errorLastName" fieldName="userToUpdate.lastName" />
                  <s:textfield name="userToUpdate.lastName" size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:140px;" />
                  </td>
              </tr>
              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.sex')}" /></td>
                <td>
                  <s:fielderror name="errorSex" fieldName="userToUpdate.sex" />
                  <s:select
                    name="userToUpdate.sex"
                    list="#{'M':getText('label.sex.male'),'F':getText('label.sex.female')}" />
                </td>
              </tr>

              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.residenceCap')}" /></td>
                <td>
                  <s:fielderror name="errorCap" fieldName="userToUpdate.cap" />
                  <s:textfield name="userToUpdate.cap" size="10" maxLength="10" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                </td>
              </tr>

              <%--tr>     //decommentare per abilitare forum, anche userAction ValidateUpdate
                <td class="lblFrmUndLn">
                  <s:property value="%{getText('label.forumNickname')}" />:
                </td>
                <td>
                  <s:textfield name="userToUpdate.forumNickname" size="25" maxLength="15" onblur="checkUniqueForumNickname($(this).value)" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:140px;" />
                  <s:fielderror name="errorForumNickname" fieldName="userToUpdate.forumNickname" />
                  <div style="display:none;" id="forumNicknameError"><ul class="errorMessage"><li><s:property value="%{getText('error.forumNickname.notunique')}" /></li></ul></div>
                </td>
                <td class="infoReg">
                  <u:translation key="label.accountInfoNick"/>
                </td>
              </tr--%>

              <tr><td>&nbsp;</td><th colspan="2"><p><u:translation key="label.accounInfoCampo"/></p></th></tr>

              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.country')}" /></td>
                <td>

                  <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
                  <s:fielderror name="errorCountry" fieldName="userToUpdate.country" />
                  <s:select
                    name="idCountry"
                    headerKey="0"
                    headerValue="%{getText('label.select.country')}"
                    list="countyList"
                    listKey="id"
                    listValue="name"
                    id="idSelectCountry"
                    value="idCountry"
                    style="width:150px;"
                    onchange="getProvincesByCountry($(this).value, 0, 0, 0, '%{provincesByCountryURL}')"
                    />
                </td>
                <td class="infoReg" rowspan="3">
                  <strong><u:translation key="label.accountInfoLuogo"/></strong>
                  <br />
                  <u:translation key="label.accountInfoGiocatoriCampi"/>
                </td>
              </tr>

              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.province')}" /></td>
                <td>
                  <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
                  <s:fielderror name="errorProvince" fieldName="userToUpdate.province" />
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
                    style="width:150px;" />
                  <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                </td>
              </tr>

              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.city')}" /></td>
                <td>
                  <s:fielderror name="errorCity" fieldName="userToUpdate.city" />
                  <s:select
                    name="idCity"
                    headerKey="0"
                    headerValue="%{getText('label.select.city')}"
                    list="cityList"
                    listKey="id"
                    listValue="name"
                    value="idCity"
                    id="idSelectCity"
                    style="width:150px;" />
                  <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                </td>
              </tr>

              <tr><td>&nbsp;</td><th colspan="2"><p><u:translation key="label.DatiLogin"/></p></th></tr>

              <tr>
                <td class="lblFrmUndLn">
                  <s:property value="%{getText('label.email')}" />
                </td>
                <td>
                  <s:fielderror name="errorEmail" fieldName="userToUpdate.email" />
                  <s:textfield name="userToUpdate.email" size="25" onblur="checkUniqueEmail($(this).value)" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:140px;" />                  
                  <div style="display:none;" id="emailError"><ul class="errorMessage"><li><s:property value="%{getText('error.email.notunique')}" /></li></ul></div>
                </td>
                <td class="infoReg" rowspan="3">
                  <strong><u:translation key="label.NoSpam"/></strong>
                <u:translation key="label.accountInfoSpam"/></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.oldPassword')}" /></td>
                <td>
                  <s:fielderror name="errorPassword" fieldName="oldPassword" />
                  <s:password name="oldPassword"  size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:140px;" />                  
                </td>
              </tr>

              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.newPassword')}" /></td>
                <td>
                  <s:fielderror name="errorNewPassword" fieldName="newPassword" />
                  <s:password name="newPassword" size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:140px;" />              
                </td>
              </tr>

              <tr>
                <td class="lblFrmUndLn"><s:property value="%{getText('label.repeatPassword')}" /></td>
                <td>
                  <s:fielderror name="errorRepeatPassword" fieldName="repeatPassword" />
                  <s:password name="repeatPassword" size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;width:140px;" />                
                </td>
              </tr>

              <tr><td>&nbsp;</td><th colspan="2"><p><u:translation key="label.Contatti"/></p></th></tr>

              <tr style="height:10px;">
                <td class="lblFrmUndLn">
                  <s:property value="%{getText('label.mobile')}" />
                </td>
                <td>
                  <%--  <s:select
                      name="mobilePre"
                      list="#{'+39':getText('label.mobile.ita'),
                              '+44':getText('label.mobile.uk'),
                              '+01':getText('label.mobile.usa')}" /> --%>
                  <s:fielderror name="mobile" fieldName="mobile" />
                  <s:textfield   name="userToUpdate.mobile" size="16"  maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                </td>
                <td class="infoReg" rowspan="3">
                  <u:translation key="label.accountInfoCell"/>
                </td>

              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </table>

            <br/><br/>

            <p style="margin:0;" align="center">
              <s:url action="userAccount" var="registerFriendToMatchUrl" namespace="" method="update"></s:url>
              <a  href="javascript: $('userAccountForm').submit();" class="btn action1" >
                <u:translation key="btn.save"/>
              </a>
              &nbsp;&nbsp;
              <a href="<s:url action="home"/>" class="btn action2" ><u:translation key="label.annulla"/></a>
            </p>

          </s:form>

        </div>
        <!--### end maincontent ###-->



      </div>
      <!--### end centralcolumn ###-->

      <!--### start rightcolumn ###-->
      <jsp:include page="../jspinc/rightcolumn.jsp" flush="true" />
      <!--### end rightcolumn ###-->

      <!--### start footer ###-->
      <jsp:include page="../jspinc/footer.jsp" flush="true" />
      <!--### end footer ###-->

    </div>

  </body>
</html>
