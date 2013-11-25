<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
 <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
 <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" >
      <jsp:param name="dataTable" value="true" />
    </jsp:include>
    <title><u:translation key="page.title.sportCenterChoose" /></title>
    <script type="text/javascript">
      function submitForm()
      {
        var selectedSportCenter = YAHOO.util.Dom.getElementBy(function(el){return el.checked;},'input', 'YUIdataTableContainer' );
        if( selectedSportCenter && (isNaN(selectedSportCenter.value) || selectedSportCenter.value <= 0) )
        {
          $('idSpanErrorSportCenter').style.display='';
          <%--alert('<u:translation key="message.SelezionareUnCampo" escapeJavaScript="true" />');--%>
          return;
        }
        $('idSportCenterSelected').value = selectedSportCenter.value;
        $('sportCenter!choose').submit();
       
      }
      /**
       * Verifica che sia selezionata la provincia prima di avviare la ricerca.
       * Se non selezionata mostra un alert js
       */
      function submitSearch()
      {
        /*if( $('idSelectProvince').value <= 0 )
        {
          alert('<u:translation key="message.ricercaSelezionaProvincia" escapeJavaScript="true" />');
          return;
        }*/
        $('sportCenter!choose').submit();
      }

      function chkAndGetCountriesWithSportCenters(valore)
      {
        if (valore > 0)
        {
          var options = {
            startFrom: 0,
            callback: 'getCitiesByProvince($(\'idSelectProvince\').value, 0, 1, 0, \'${citiesByProvinceURL}\');'
          }
          getProvincesByCountry(valore, 0, 1, 0,'${provincesByCountryURL}', options);
        }
      }

    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->

  </head>
  <body class="popUpFix yui-skin-gokick">

    <p class="maxiTab">
      <a class="active" href="#" style="border-left:0; background-color: #fff;"><u:translation key="label.ScegliIlCampo"/><span style="margin:5px 0 0 5px;" id="yuiSpinner"><img alt="" src="<s:url value="/images/spinnerFF.gif"   />" /></span></a>
      <a href="<s:url action="sportCenter" method="input" />"><u:translation key="label.NuovoCampo"/></a>
      <br class="clear" />
    </p>

    <s:if test="sportCenterSelected.valid" >
      <script type="text/javascript">
        Event.observe(window, 'load',
        function() {
          var parentDocument = window.opener.document;
          parentDocument.getElementById('jsSortCenterSelectedId').value = '<s:property value="sportCenterSelected.id"  />';
          parentDocument.getElementById('jsSportCenterSelectedName').innerHTML = '<s:property value="sportCenterSelected.name" escapeJavaScript="true" />';
          parentDocument.getElementById('jsSportCenterSelectedAddress').innerHTML = '<s:property value="sportCenterSelected.address" escapeJavaScript="true"/>';
          parentDocument.getElementById('jsSportCenterSelectedCity').innerHTML = '<s:property value="sportCenterSelected.cityName" escapeJavaScript="true"/> (<s:property value="sportCenterSelected.provinceAbbreviation" escapeJavaScript="true"/>)';
          parentDocument.getElementById('jsSportCenterSelectedContainer').style.display = 'inline';
          window.close();
        }
      );


      </script>
    </s:if>
    <s:else>
      <div class="indentCont">
        <p>
          <s:set name="campo"><u:translation key="label.sportCenter_campo" /></s:set>
          <s:set name="campi"><u:translation key="label.sportCenter_campi" /></s:set>
          <s:set name="convenzionato"><u:translation key="label.convenzionato"/></s:set>
          <s:set name="convenzionati"><u:translation key="label.convenzionati"/></s:set>

          <s:if test="cityInfoName != ''">
            <u:translationArgs key="message.sportCenterListCity" arg01="${countSportCenter}" arg02="${countSportCenter > 1 ? campi : campo}" arg03="${cityInfoName}"  />
          </s:if>
          <s:if test="provinceInfoName != ''">
            <u:translationArgs key="message.sportCenterListProvince" arg01="${countSportCenter}" arg02="${countSportCenter > 1 ? campi : campo}" arg03="${provinceInfoName}"  />
          </s:if>
          <s:if test="countConventionedSportCenter > 0">
            <u:translationArgs key="message.sportCenterListConventioned" arg01="${countConventionedSportCenter}" arg02="${countConventionedSportCenter > 1 ? convenzionati : convenzionato}" />
          </s:if>
          <%--<a class="showHideBoxLink" onclick="Effect.toggle('idBoxSearchSportCenter','slide');" href="javascript: void(0);">
            <u:translation key="label.ApriChiudiRicerca" />
          </a>--%>
          [<a href="javascript: hideDisplayUserSearchBox('idBoxSearchSportCenter', 'idBoxSearchHide', 'idBoxSearchDisplay');"><span id="idBoxSearchDisplay"  style="display: none;"><u:translation key="label.opzioniRicerca"/></span><span id="idBoxSearchHide" ><u:translation key="label.nascondiOpzioni"/></span></a>]
        </p>

        <div class="searchBox" id="idBoxSearchSportCenter" >
        <%--<div class="searchBox" id="idBoxSearchSportCenter" style="display: none;">--%>
          <s:form method="post"  >
            <s:hidden name="idSportCenterSelected" id="idSportCenterSelected" />
            <s:hidden  name="ctrlSearch" />
            <table>
              <tr>
                <td style="width:15%;"><u:translation key="label.name"/>:</td>
                <td><s:textfield name="filterName" size="30" cssStyle="border:solid 1px #999999; background:#FFFFFF;" /></td>
              </tr>
              <tr>
                <td><u:translation key="label.country"/>:</td>
                <td colspan="2">
                 
                  <s:select
                    name="filterIdCountry"
                    list="countryList"
                    listKey="id"
                    listValue="name"
                    id="idSelectCountry"
                    value="filterIdCountry"
                    onchange="chkAndGetCountriesWithSportCenters($(this).value);" />
                   <%-- onchange="getProvincesByCountry($(this).value, 0, 1, 0, '%{provincesByCountryURL}')" /--%>
                </td>
              </tr>
              <tr>
                <td><u:translation key="label.province"/>:</td>
                <td colspan="2">
                  
                  <s:select
                    name="filterIdProvince"
                    list="provinceList"
                    listKey="id"
                    listValue="name"
                    value="filterIdProvince"
                    id="idSelectProvince"
                    onchange="getCitiesByProvince($(this).value, 0, 1, 0, '%{citiesByProvinceURL}')" />
                  <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                </td>
              </tr>
              <tr>
                <td><u:translation key="label.city"/>:</td>
                <td colspan="2">
                  <s:select
                    name="filterIdCity"
                    headerKey="0"
                    headerValue="%{getText('label.Tutte')}"
                    list="cityList"
                    listKey="id"
                    listValue="name"
                    value="filterIdCity"
                    id="idSelectCity" />
                  <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                </td>

              </tr>
              <tr>
                <td><u:translation key="label.TipoCampo" />:</td>
                <td >
                  <s:select
                    name="filterIdMatchType"
                    headerKey="0"
                    headerValue="%{getText('label.Tutti')}"
                    list="matchTypeList"
                    listKey="id"
                    listValue="name"
                    value="filterIdMatchType" />

                </td>
                <td class="right">
                  <a class="btn" href="javascript: submitSearch();" >
                    <u:translation key="label.Cerca" />
                  </a>
                </td>
              </tr>
              <%--tr>
                <td><u:translation key="label.Copertura"/>:</td>
                <td colspan="4">
                  <s:select
                    name="filterIdPitchCover"
                    headerKey="0"
                    headerValue="%{getText('label.Tutte')}"
                    list="pitchCoverList"
                    listKey="id"
                    listValue="name"
                    value="filterIdPitchCover"
                    id="idSelectPitchCover" />

                </td>
              </tr--%>
            </table>
          </s:form>
        </div>
      </div>


      <s:if test="sportCenterInfolist.size > 0 ">

      <div id="YUIdataTableContainer" class="sportCenterChoose yui-skin-gokick noArrow"></div>

      <!--BEGIN SOURCE CODE DATATABLE =============================== -->

      <script type="text/javascript">
        YAHOO.util.Event.addListener(window, "load", function() {
          YAHOO.example.XHR_XML = function() {
            var myColumnDefs =
            [
              {key:"Id", label:""},
              {key:"Name", label:"<u:translation key="label.NomeCampo" />", sortable:true},
              {key:"CityName", label:"<u:translation key="label.Localita" />", sortable:true},
              {key:"Address", label:"<u:translation key="label.Indirizzo" />", sortable:true},
              {key:"MatchTypeAvailable", label:"<u:translation key="label.A" />", sortable:true},
              {key:"Telephone", label:"<u:translation key="label.Telefono" />"}
            ];

            var myDataSource = new YAHOO.util.DataSource("<s:url action="sportCenterDataTableChoose" namespace="ajax" />?dataTableKey=<s:property value="dataTableKey" />");
            myDataSource.connMethodPost = true;
            myDataSource.responseType = YAHOO.util.DataSource.TYPE_XML;
            myDataSource.responseSchema = {
              resultNode: "Result",
              fields: ["Id", "Name","CityName","MatchTypeAvailable","Address","Telephone", "Conventioned"],
              metaNode : "ResultSet", // Name of the node holding meta data
              metaFields : {
                totalResultsAvailable : "totalResultsAvailable"
              }
            };

            // Define a custom row formatter function
            var myRowFormatter = function(elTr, oRecord) {
              if (oRecord.getData('Conventioned')=='true') {
                YAHOO.util.Dom.addClass(elTr, 'mark');
              }
              return true;
            };

            var myPaginator = new YAHOO.widget.Paginator(
            {
              rowsPerPage: 10,
              alwaysVisible : false,
              firstPageLinkLabel: '<u:translation key="label.paginator.first" escapeJavaScript="true" />',
              previousPageLinkLabel: '<u:translation key="label.paginator.prev" escapeJavaScript="true" />',
              nextPageLinkLabel: '<u:translation key="label.paginator.next" escapeJavaScript="true" />',
              lastPageLinkLabel: '<u:translation key="label.paginator.last" escapeJavaScript="true" />'
            });

            var myConfigs = {
              dynamicData: true,
              sortedBy : {
                key: "Name",
                dir: YAHOO.widget.DataTable.CLASS_ASC // Sets UI initial sort arrow
              },
              paginator: myPaginator,
              formatRow: myRowFormatter,
              MSG_EMPTY: '<u:translation key="message.NessunRisultato" escapeJavaScript="true" />'
            };

            var myDataTable = new YAHOO.widget.DataTable("YUIdataTableContainer", myColumnDefs,
            myDataSource, myConfigs);

            // Subscribe to events for row selection
            //myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow);
            myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
            //myDataTable.subscribe("rowClickEvent", myDataTable.onEventSelectRow);
            myDataTable.doBeforeSortColumn =function (){$('yuiSpinner').style.visibility='visible';return true;}
            // Update totalRecords on the fly with value from server
            myDataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
              oPayload.totalRecords = oResponse.meta.totalResultsAvailable;
              $('yuiSpinner').style.visibility='hidden';
              return oPayload;
            };

            return {
              oDS: myDataSource,
              oDT: myDataTable
            };
          }();
        });
      </script>

      <!--END SOURCE CODE DATATABLE =============================== -->

      <div class="bottomAction left">
       <div class="indentCont">
        <a class="btn" href="javascript: submitForm();" >
          <u:translation key="label.Seleziona"/> <u:translation key="label.campo"/>
        </a>&nbsp;
        <span id="idSpanErrorSportCenter" style="display :none;">
            <b>
              <font color="red">
                <s:property value="%{getText('message.SelezionareUnCampo')}" />
              </font>
            </b>
        </span>
              </div>
      </div>
              <br class="clear" />
      </s:if>
      <s:else>
            <div class="indentCont">
              <p>
                <u:translation key="message.NessunRisultato" />
                <script type="text/javascript">
                  $('yuiSpinner').style.visibility='hidden';
                </script>
              </p>
            </div>
      </s:else>

      <p class="indentCont">
        <br />
        <u:translation key="label.sportCenterInfoInserisci" />? <a href="<s:url action="sportCenter" method="input" />"><u:translation key="label.sportCenterInseriscilo" /> >></a>
      </p>
      
    </s:else>

  </body>

</html>
