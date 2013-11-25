<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
<s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" >
      <jsp:param name="dataTable" value="true" />
    </jsp:include>
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <script type="text/javascript">
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
        <body class="yui-skin-gokick" >

    <div class="wrapper">

      <!--### start header ###-->
      <jsp:include page="../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
        <jsp:param name="tab" value="sportCenters"/>
      </jsp:include>
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">
  <div class="topPageWrap">
              <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
          </div>  
        
       

        <!--### start mainContent ###-->
        <div class="mainContent roseCont">

          <div class="indentCont">
            <div style="display:none;" id="idConfirmMessage">
              <p class="confirmMessage"></p>
            </div>

              <h1><u:translation key="label.sportCenterCampi" />
                <span><img id="yuiSpinner" alt="" src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
              </h1>

             

            <p class="titleList">
              <s:set name="campo"><u:translation key="label.sportCenter_campo" /></s:set>
              <s:set name="campi"><u:translation key="label.sportCenter_campi" /></s:set>
              <u:translationArgs key="label.sportCenterCercaTra" arg01="${appContext.sportCenterCount}" arg02="${appContext.sportCenterCount == 1 ? campo : campi}" />
            </p>

            <div class="headList">
              <p>
                <strong>
                  <s:property value="sportCenterInfoList.size" />
                  <s:if test="sportCenterInfoList.size == 1">${campo}</s:if><s:else>${campi}</s:else>
                  online
                </strong>
                <s:if test="sportCenterInfoList.size == 1"><u:translation key="label.trovato"/></s:if><s:else><u:translation key="label.trovati"/></s:else>
                <s:if test="searchAreaFlag == 2">
                  <u:translation key="label.a_"/> <s:property value="searchArea" />
                </s:if>
                <s:elseif test="searchAreaFlag == 1">
                  <u:translation key="label.a_"/> <s:property value="searchArea" /> <u:translation key="label.eProvincia"/>
                </s:elseif>
                <s:elseif test="searchAreaFlag == 0">
                  <u:translation key="label.in"/> <s:property value="searchArea" />
                </s:elseif>

                <s:set name="convenzionato"><u:translation key="label.convenzionato"/></s:set>
                <s:set name="convenzionati"><u:translation key="label.convenzionati"/></s:set>


                <span class="highlightedLine" >
                  <s:if test="conventionedSportCenterCount > 0">
                    <u:translationArgs key="message.sportCenterListConventioned" arg01="${conventionedSportCenterCount}" arg02="${conventionedSportCenterCount > 1 ? convenzionati : convenzionato}" />
                  </s:if>
                </span>


                [<a class="showHideBoxLink" href="javascript: hideDisplayUserSearchBox('idBoxSearch', 'idBoxSearchHide', 'idBoxSearchDisplay');"><span id="idBoxSearchDisplay"  style="display: none;"><u:translation key="label.opzioniRicerca"/></span><span id="idBoxSearchHide"><u:translation key="label.nascondiOpzioni"/></span></a>]
              </p>
            </div>

            <div class="searchBox" id="idBoxSearch" >
              <s:form action="sportCenterAll!searchSportCenter" method="post"  >
                <table>
                  <tr>
                    <td style="width:15%;"><u:translation key="label.firstname"/>:</td>
                    <td colspan="2">
                      <s:textfield name="name" size="30" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.country"/>:</td>
                    <td colspan="2">

                      <s:select
                        name="idCountry"
                        list="countyList"
                        listKey="id"
                        listValue="name"
                        id="idSelectCountry"
                        value="idCountry"
                        onchange="chkAndGetCountriesWithSportCenters($(this).value);"
                        />
                      <!--onchange="getProvincesByCountry($(this).value, 0, 1, 0, '%{provincesByCountryURL}')"-->
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.province"/>:</td>
                    <td colspan="2">
                      <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
                      <s:select
                        name="idProvince"
                        list="provinceList"
                        listKey="id"
                        listValue="name"
                        id="idSelectProvince"
                        value="idProvince"
                        onchange="getCitiesByProvince($(this).value, 0, 1, 0, '%{citiesByProvinceURL}')" />
                      <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.city"/>:</td>
                    <td colspan="2">
                      <s:select
                        name="idCity"
                        headerKey="0"
                        headerValue="%{getText('label.Tutte')}"
                        list="cityList"
                        listKey="id"
                        listValue="name"
                        value="idCity"
                        id="idSelectCity" />
                      <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.TipoCampo"/>:</td>
                    <td colspan="2">
                      <s:select
                        name="idMatchType"
                        headerKey="0"
                        headerValue="%{getText('label.Tutti')}"
                        list="matchTypeList"
                        listKey="id"
                        listValue="name"
                        value="idMatchType"
                        id="idSelectMatchType" />
                    </td>
                    <td class="right">
                      <s:submit value="%{getText('label.Cerca')}" cssClass="btn" />
                    </td>
                  </tr>
                  <%--tr>
                    <td><u:translation key="label.Copertura"/>:</td>
                    <td>
                      <s:select
                        name="idPitchCover"
                        headerKey="0"
                        headerValue="%{getText('label.Tutte')}"
                        list="pitchCoverList"
                        listKey="id"
                        listValue="name"
                        value="idPitchCover"
                        id="idSelectPitchCover" />
                    </td>

                  </tr --%>
                  <tr>
                    <td colspan="6"><s:fielderror name="ageInvalid" /></td>
                  </tr>
                </table>
              </s:form>
            </div>
            <div class="clear">&nbsp;</div>
            <s:if test="advanceSearch == false">
            </s:if>
            <s:else>
              <script type="text/javascript">
                displayUserSearchBox('idBoxSearch', 'idBoxSearchHide', 'idBoxSearchDisplay');
              </script>
            </s:else>

          </div>

          <s:if test="sportCenterInfoList.size > 0 ">

          <div id="YUIdataTableContainer"   class="yui-skin-gokick noArrow"></div>

          <!--BEGIN SOURCE CODE DATATABLE =============================== -->

          <script type="text/javascript">
            YAHOO.util.Event.addListener(window, "load", function() {
              YAHOO.example.XHR_XML = function() {
                var myColumnDefs =
                  [
                  {key:"Empty",                   label:""},
                  {key:"Name",                    label:"<span title='<u:translation key="label.NomeCampo" />'> <u:translation key="label.NomeCampo" /> </span>", sortable:true},
                  {key:"CityName",                label:"<span title='<u:translation key="label.Localita" />'>  <u:translation key="label.Localita" />  </span>", sortable:true},
                  {key:"Address",                 label:"<span title='<u:translation key="label.Indirizzo"/>'>  <u:translation key="label.Indirizzo" /> </span>", sortable:true},
                  {key:"MatchTypeAvailableSCAll", label:"<span title='<u:translation key="label.A" />'>         <u:translation key="label.A" />         </span>", sortable:true},
                  {key:"Telephone",               label:"<span title='<u:translation key="label.Telefono" />'>  <u:translation key="label.Telefono" />  </span>"}
                ];

                var myDataSource = new YAHOO.util.DataSource("<s:url action="sportCenterDataTableChoose" namespace="ajax" />?dataTableKey=<s:property value="dataTableKey" />");
                myDataSource.connMethodPost = true;
                myDataSource.responseType = YAHOO.util.DataSource.TYPE_XML;
                myDataSource.responseSchema = {
                  resultNode: "Result",
                  fields: [ "Empty",
                            "Name",
                            "CityName",
                            "Address",
                            "MatchTypeAvailableSCAll",
                            "Telephone",
                            "Conventioned"],
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
                  rowsPerPage: 50,
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


        </div>
        <!--### end mainContent ###-->

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
