<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
<html>
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><u:translation key="page.title.sportCenterChoose"/></title>
    <script type="text/javascript" >
      function changePitchType()
    {
      if( $('idSelectPitchType') == null )
        return;
      // 2 è l'id sul database del tipo di campo a pagamento
      var showRows = $('idSelectPitchType').value == 2;
      $$('.pitchTypeRows').each(function(r){
        if( showRows )
          r.show();
        else
          r.hide();
      });
    }

    function chkAndGetCitiesByProvince(valore)
      {
        
        if (valore > 0)
        {
              getCitiesByProvince(valore, 0, 0, 0, '${citiesByProvinceURL}');
              
        }
         
      }

    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class="popUpFix" onload="changePitchType();">
    <s:if test="sportCenterInfoInserted.valid" >
      <script type="text/javascript">
      Event.observe(window, 'load',
      function() {
        var parentDocument = window.opener.document;
        parentDocument.getElementById('jsSortCenterSelectedId').value = '<s:property value="sportCenterInfoInserted.id" />';
        parentDocument.getElementById('jsSportCenterSelectedName').innerHTML = '<s:property value="sportCenterInfoInserted.name" escapeJavaScript="true" />';
        parentDocument.getElementById('jsSportCenterSelectedAddress').innerHTML = '<s:property value="sportCenterInfoInserted.address" escapeJavaScript="true" />';
        parentDocument.getElementById('jsSportCenterSelectedCity').innerHTML = '<s:property value="sportCenterInfoInserted.cityName" escapeJavaScript="true"/> (<s:property value="sportCenterInfoInserted.provinceAbbreviation" escapeJavaScript="true" />)';
        parentDocument.getElementById('jsSportCenterSelectedContainer').style.display = 'inline';
        window.close();
      }
    );

      </script>
    </s:if>
    <s:else>
      <p class="maxiTab">
        <a href="<s:url action="sportCenter" method="choose" />"><u:translation key="label.ScegliIlCampo" /></a>
        <a class="active" href="#"><u:translation key="label.NuovoCampo" /></a>
        <br class="clear" />
      </p>

      <div class="addSC">
        <p><strong><u:translation key="label.sportCenterInserisciDati"/></strong></p>
        
        <s:form action="sportCenter!insert" method="post" >
            <table style="margin-top:5px;">
            <tr>
              <td class="subLine">
                <u:translation key="label.NomeDelCampo" />:
              </td>
              <td>
                <s:fielderror fieldName="sportCenterToInsert.name" />
                <s:textfield name="sportCenterToInsert.name" size="25" maxLength="40" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:210px;" />
              </td>
            </tr>

            <tr>
              <td class="subLine">
                <u:translation key="label.country"/>:
              </td>
              <td>
                <s:fielderror fieldName="idCountryForInsert" />
                <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
                <s:select
                  name="idCountryForInsert"
                  list="countryListForInsert"
                  listKey="id"
                  listValue="name"
                  id="idSelectCountry"
                  value="idCountryForInsert"
                  onchange="getProvincesByCountry($(this).value, 0, 0, 0, '%{provincesByCountryURL}')" />
              </td>
            </tr>
            <tr>
              <td class="subLine">
                <u:translation key="label.province"/>:
              </td>
              <td>
                <s:fielderror fieldName="idProvinceForInsert" />
                
                <s:select
                  name="idProvinceForInsert"
                  headerKey="0"
                  headerValue="%{getText('label.Seleziona')}"
                  list="provinceListForInsert"
                  listKey="id"
                  listValue="name"
                  value="idProvinceForInsert"
                  id="idSelectProvince"
                  onchange="chkAndGetCitiesByProvince($(this).value)"
                  />

                <%--  onchange="getCitiesByProvince($(this).value, 0, 0, 0, '%{citiesByProvinceURL}')" --%>

                <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
              </td>
            </tr>
            <tr>
              <td class="subLine">
                <u:translation key="label.city"/>:
              </td>
              <td>
                <s:fielderror fieldName="idCityForInsert" />
                <s:select
                  name="idCityForInsert"
                  headerKey="0"
                  headerValue="%{getText('label.Seleziona')}"
                  list="cityListForInsert"
                  listKey="id"
                  listValue="name"
                  value="idCityForInsert"
                  id="idSelectCity" />
                <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
              </td>
            </tr>
            <tr>

              <td class="subLine">
                <u:translation key="label.Indirizzo"/>:
              </td>
              <td>
                <s:fielderror fieldName="sportCenterToInsert.address" />
                <s:textfield name="sportCenterToInsert.address" size="25" maxLength="40" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:210px;" />
              </td>
            </tr>
            <tr>

              <td class="subLine">
                <u:translation key="label.sportCenterTipo"/>:
              </td>
              <td>
                <s:fielderror fieldName="idPitchTypeForInsert" />
                <s:select
                  name="idPitchTypeForInsert"
                  headerKey="0"
                  headerValue="%{getText('label.Seleziona')}"
                  list="pitchTypeList"
                  listKey="id"
                  listValue="name"
                  value="idPitchTypeForInsert"
                  id="idSelectPitchType"
                  onchange="changePitchType();"/>
              </td>
            </tr>

            <tr>
              <td class="subLine">
                <u:translation key="label.Telefono"/>:
              </td>
              <td>
                <s:fielderror fieldName="sportCenterToInsert.telephone" />
                <s:textfield name="sportCenterToInsert.telephone" size="25" maxLength="20" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:210px;" />
              </td>
            </tr>

            <tr>
              <td class="subLine">
                <u:translation key="label.Email"/>:
              </td>

              <td>
                <s:fielderror fieldName="sportCenterToInsert.email" />
                <s:textfield name="sportCenterToInsert.email" size="25" maxLength="40" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:210px;" />
              </td>
            </tr>
            <tr>
              <td class="subLine">
                <u:translation key="label.webSite"/>:
              </td>

              <td>
                <s:fielderror fieldName="sportCenterToInsert.webSite" />
                <s:textfield name="sportCenterToInsert.webSite" size="25" maxLength="40" cssStyle="border:solid 1px #999999; background:#FFFFFF; width:210px;" />
              </td>
            </tr>
            <tr>
              <td>
                &nbsp;
              </td>
              <td>
                <p><input value="<u:translation key='label.sportCenterInserisci'/>" type="submit" class="btn" /></p>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <p class="note">
                  <small><u:translation key="label.sporCenterInsertInfo"/></small> </p>
              </td>
            </tr>

          </table>
        </s:form>

      </div>

    </s:else>
  </body>

</html>
