<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" >
      <jsp:param name="dataTable" value="true" />
    </jsp:include>
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <script type="text/javascript">

      function posTop()
      {
        window.scroll(0, 0)
      }

      function inviteSelected(chkListName)
      {
        arrayChk = document.getElementsByName(chkListName);
        idUserToInviteList = new Array();
        y = 0;
        for(i=0; i<arrayChk.length; i++)
        {
          if(arrayChk[i].checked)
          {
            idUserToInviteList[y] = arrayChk[i].value;
            y++
          }
        }

        var options = {
          method: 'GET',
          parameters: {
            idUserToInviteList: idUserToInviteList,
            dummy: Math.random()
          },
          onSuccess: function(t) {
            var response = t.responseJSON;
            if(response.confirmMessage == "")
            {
              $('idErrorMessage').show();
            }
            else
            {
              showLastMessage();
              $('idErrorMessage').hide();
              posTop();
            }
            changeCheckBox('.chkSelectUser', false);
            $('idChkSelectAll').checked = false;

          }
        };
        new Ajax.Request('<s:url action="inviteUsers" namespace="/ajax" />', options);
          
      }
    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class="yui-skin-gokick">

    <div class="wrapper">

      <!--### start header ###-->
      <jsp:include page="../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->

      <!--### start leftcolumn ###-->
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true" >
        <jsp:param name="tab" value="gokickers"/>
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
            <h1>GoKickers
              <span><img id="yuiSpinner" alt="" src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
            </h1>

            <p class="titleList">
              <u:translationArgs key="label.userAllCercaTra" arg01="${appContext.goKickerCount}"/>
            </p>

            <div class="headList">
              <p>
                <c:set var="listSize"><s:property value="statisticInfoList.size" /></c:set>
                <u:translationArgs key="label.gokickersTrovati" arg01="${listSize}"  />
                <s:if test="searchAreaFlag == 2">
                  <u:translation key="label.a_" /> <s:property value="searchArea" />
                </s:if>
                <s:elseif test="searchAreaFlag == 1">
                  <u:translation key="label.a_" /> <s:property value="searchArea" /> <u:translation key="label.eProvincia" />
                </s:elseif>
                <s:elseif test="searchAreaFlag == 0">
                  <u:translation key="label.in" /> <s:property value="searchArea" />
                </s:elseif>
                <s:elseif test="searchAreaFlag == -1">
                  <u:translation key="label.nellaCommunity" />
                </s:elseif>
                - <u:printPlayersRolesCount playersRolesCount="${countPlayersRoles}" all="true" />

                <!--deve rimanere tutto su una riga per evitare gli spazi tra parentesi quadre e parole-->
                [<a class="showHideBoxLink" href="javascript: hideDisplayUserSearchBox('idBoxSearch', 'idBoxSearchHide', 'idBoxSearchDisplay');"><span id="idBoxSearchDisplay" style="display: none;"><u:translation key="label.opzioniRicerca" /></span><span id="idBoxSearchHide" ><u:translation key="label.nascondiOpzioni" /></span></a>]
              </p>
            </div>
            <div class="searchBox" id="idBoxSearch" >

              <s:form action="userAll!searchUser" method="post"  >
                <table>
                  <tr>
                    <td><u:translation key="label.firstname" />:</td>
                    <td><s:textfield name="firstName" size="12" cssStyle="border:solid 1px #999999; background:#FFFFFF;" /></td>
                    <td class="right"><u:translation key="label.lastName" />:</td>
                    <td><s:textfield name="lastName" size="12" cssStyle="border:solid 1px #999999; background:#FFFFFF;" /></td>
                    <td class="right"><u:translation key="label.Eta" />:</td>
                    <td><s:textfield name="minAge" size="2" maxLength="2" cssStyle="border:solid 1px #999999; background:#FFFFFF;" /> <small><u:translation key="label.minima"/></small></td>
                    <td><s:textfield name="maxAge" size="2" maxLength="2" cssStyle="border:solid 1px #999999; background:#FFFFFF;" /> <small><u:translation key="label.massima"/></small></td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.country" />:</td>
                    <td colspan="6">
                      <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
                      <s:select
                        name="idCountry"
                        list="countyList"
                        listKey="id"
                        listValue="name"
                        id="idSelectCountry"
                        value="idCountry"
                        onchange="getProvincesByCountry($(this).value, 1, 0, 0, '%{provincesByCountryURL}')" />
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.province" />:</td>
                    <td colspan="6">
                      <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
                      <s:select
                        name="idProvince"
                        headerKey="0"
                        headerValue="%{getText('label.Tutte')}"
                        list="provinceList"
                        listKey="id"
                        listValue="name"
                        id="idSelectProvince"
                        value="idProvince"
                        onchange="getCitiesByProvince($(this).value, 1, 0, 0, '%{citiesByProvinceURL}')" />
                      <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.city" />:</td>
                    <td colspan="6">
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
                    <td><u:translation key="label.role" />:</td>
                    <td colspan="6">
                      <fieldset>
                        <s:checkbox id="idChkGK" name="chkGk" /> <label for="idChkGK"><u:translation key="label._Portiere" /></label>
                        <s:checkbox id="idChkDF" name="chkDf" /> <label for="idChkDF"><u:translation key="label._Difensore" /></label>
                        <s:checkbox id="idChkCC" name="chkCc" /> <label for="idChkCC"><u:translation key="label._Centrocampista" /></label>
                        <s:checkbox id="idChkAT" name="chkAt" /> <label for="idChkAT"><u:translation key="label._Attaccante" /></label>
                      </fieldset>
                    </td>
                  </tr>
                  <tr>
                    <td><u:translation key="label.mercato" />:</td>
                    <td colspan="6">
                      <s:checkbox id="idOnlyOpenMarket" name="onlyMarketEnabled" /> <label for="idOnlyOpenMarket"><u:translation key="label.visualizzaSolo" /> <u:translation key="label.GoKickers" /> <u:translation key="label.sulMercato" /></label>
                    </td>
                  </tr>
                  <tr>
                    <td   ><u:translation key="label.stat" />:</td>
                    <td  colspan="5">
                      <s:select
                        name="statisticPeriod"
                        list="statisticPeriodList"
                        listKey="id"
                        listValue="label"
                        value="statisticPeriod" />
                    </td>
                    <td class="right">
                      <s:submit value="%{getText('label.Cerca')}" cssClass="btn action1" />
                    </td>
                  </tr>
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


          <s:if test="statisticInfoList.size > 0 ">

            <s:url value="images/gioca.gif" encode="false" var="imgPlay" />
            <c:set var="titleName" value="%{getText('label.name')}" />
            <c:set var="titleSurname" value="%{getText('label.surname')}" />
            <c:set var="titleLocalita" value="%{getText('label.localita')}" />

            <div id="YUIdataTableContainer"   class="yui-skin-gokick noArrow"  > </div>

            <!--BEGIN SOURCE CODE DATATABLE =============================== -->

            <script type="text/javascript">
              YAHOO.util.Event.addListener(window, "load", function() {
                YAHOO.example.XHR_XML = function() {
                  var myColumnDefs =
                    [
                    {key:"Id", label:"" },
                    {key:"FirstName",  label:"<span title='<u:translation key="label.firstName" />'>      <u:translation key="label.firstName" />           </span>", sortable:true},
                    {key:"LastName",   label:"<span title='<u:translation key="label.lastName" />'>       <u:translation key="label.lastName" />            </span>", sortable:true},
                    {key:"City",       label:"<span title='<u:translation key="label.localita" />'>       <u:translation key="label.localita" />            </span>", sortable:true},
                    {key:"Nationality",label:"<span title='<u:translation key="label.country" />'>        <u:translation key="label.countryAbbr" />         </span>", sortable:true},
                    {key:"Role",       label:"<span title='<u:translation key="label.role"/>'>            <u:translation key="label.roleAbbr" />            </span>", sortable:true},
                    {key:"Age",        label:"<span title='<u:translation key="label.Eta" />'>            <u:translation key="label.Eta" />                 </span>", sortable:true},
                    {key:"Played",     label:"<span title='<u:translation key="label.Giocate" />'>        <u:translation key="label.GiocateAbbr" />         </span>", sortable:true},
                    {key:"Reliability",label:"<span title='<u:translation key="label.affidabilita" />'>   <u:translation key="label.affidabilitaAbbr" />    </span>", sortable:true},
                    {key:"Market",     label:"<span title='<u:translation key="label.mercato" />'>        <u:translation key="label.mercatoAbbr" /> " }

                  ];

                  var myDataSource = new YAHOO.util.DataSource("<s:url action="statisticInfoDataTableUserAll" namespace="ajax" />?dataTableKey=<s:property value="dataTableKey" />");
                  myDataSource.connMethodPost = true;
                  myDataSource.responseType = YAHOO.util.DataSource.TYPE_XML;
                  myDataSource.responseSchema = {
                    resultNode: "Result",
                    fields:["Id",
                      "FirstName",
                      "LastName",
                      "City",
                      "Nationality",
                      "Role",
                      "Age",
                      "Played",
                      "Reliability",
                      "Market",
                      "Current"],
                    metaNode : "ResultSet", // Name of the node holding meta data
                    metaFields : {
                      totalResultsAvailable : "totalResultsAvailable"
                    }
                  };

                  // Define a custom row formatter function
                  var myRowFormatter = function(elTr, oRecord) {
                    if (oRecord.getData('Current')=='true') {
                      YAHOO.util.Dom.addClass(elTr, 'markUser');
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
                  
                  var search = '${advanceSearch}';
                  var orderPar;
                  var orderDir;
                  var orderDirDesc  = YAHOO.widget.DataTable.CLASS_DESC;
                  var orderDirAsc  = YAHOO.widget.DataTable.CLASS_ASC;
                  
                  if(search=='true')
                  {
                    orderPar='FirstName';
                    orderDir=orderDirAsc;
                  }
                  else
                  {
                    orderPar='Id'; 
                    orderDir=orderDirDesc;
                  }
                    
                  
                  var myConfigs = {
                    dynamicData: true,
                    sortedBy : {
                      key: orderPar,
                      dir: orderDir // Sets UI initial sort arrow
                    },
                    paginator: myPaginator,
                    formatRow: myRowFormatter
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

            <div class="bottomAction" style="padding-left:7px; " >
              <s:checkbox id="idChkSelectAll" name="selectAll" onclick="javascript:changeCheckBox('.chkSelectUser', this.checked);" />
              <label for="idChkSelectAll"><u:translation key="label.selezionaTutti" /></label>
            </div>

            <div class="clear">&nbsp;</div>
            <br />

            <p class="centred">
              <a class="btn" href="javascript: void(0);" onclick="inviteSelected('userCheckList');"><u:translation key="label.richiediAmicizia" /></a>
            </p>

            <div style="display:none;" id="idErrorMessage">
              <p class="errorMessage"><s:property value="%{getText('error.selectGoKickers')}" /></p>
            </div>

          </s:if>

          <s:else>
            <div class="indentCont">
              <p>
                <u:translation key="message.nessunGkTrovato" />
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
