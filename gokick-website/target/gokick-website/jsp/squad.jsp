<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" >
      <jsp:param name="dataTable" value="true" />
    </jsp:include>
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <script type="text/javascript">
      function submitChangeStatistic()
      {
        $('statisticPeriodForm').submit();
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
         <jsp:include page="../jspinc/leftColumn.jsp" flush="true">
               <jsp:param name="tab" value="squad"/>
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

            <h1 class="titleFriend">
              <s:set name="amico" ><u:translation key="label.amico"/></s:set>
              <s:set name="amici" ><u:translation key="label.amici"/></s:set>
              <u:translationArgs key="label.squadQuantiAmici"  arg01="${friendsCount}" arg02="${friendsCount == 1 ? amico : amici }" />

              <span><img id="yuiSpinner" alt="" src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
            </h1>

            <p class="titleList"><u:translation key="label.squadAmiciInfo"/> [<a  href="javascript: void(0);" onclick="openPopupHelp('S002')" >?</a>]</p>

            <s:if test="friendRequestsReceivedList.size > 0 || friendRequestsMadeList.size > 0">
              <p class="highlightedHead">

                <s:if test="friendRequestsReceivedList.size > 0">
                  <strong><s:property value="friendRequestsReceivedList.size" /></strong>
                  <s:if test="friendRequestsReceivedList.size == 1">
                    <u:translation key="label.squadRichiestaAmiciziaRicevuta"/>
                  </s:if>
                  <s:else>
                    <u:translation key="label.squadRichiesteAmiciziaRicevute"/>
                  </s:else>
                </s:if>
                <s:if test="friendRequestsReceivedList.size > 0 && friendRequestsMadeList.size > 0">
                  .:
                </s:if>
                <s:if test="friendRequestsMadeList.size > 0">
                  <strong><s:property value="friendRequestsMadeList.size" /></strong>
                  <s:if test="friendRequestsMadeList.size == 1">
                    <u:translation key="label.squadRichiestaAmiciziaEffettuata"/>
                  </s:if>
                  <s:else>
                    <u:translation key="label.squadRichiesteAmiciziaEffettuate"/>
                  </s:else>
                </s:if>


              <s:set name="requestsReceivedListSize"><s:property value="friendRequestsReceivedList.size" /></s:set>
              <s:set name="requestsMadeListSize"><s:property value="friendRequestsMadeList.size" /></s:set>
                [<a class="showHideBoxLink" href="javascript: hideDisplayFriendRequestsBox('boxFriendRequestsUserList','idBoxDisplayRequest','idBoxPHideRequest');"><c:choose><c:when test="${boxFriendRequestsVisible && (requestsReceivedListSize > 0 || requestsMadeListSize > 0 )}"><span id="idBoxDisplayRequest" style="display: none;"><u:translation key="label.squadMostraRichieste"/></span><span id="idBoxPHideRequest" ><u:translation key="label.squadNascondiRichieste"/></span></c:when><c:otherwise><span id="idBoxDisplayRequest"><u:translation key="label.squadMostraRichieste"/></span><span id="idBoxPHideRequest" style="display: none;"><u:translation key="label.squadNascondiRichieste"/></span></c:otherwise></c:choose></a>]
              </p>
            </s:if>

            <div id="boxFriendRequestsUserList" style="display: ${boxFriendRequestsVisible ? 'block' : 'none'};">
            
            <s:if test="friendRequestsReceivedList.size > 0 ">
              <table class="stdList">
                  <tr>
                    <th style="width:205px;">
                      <span ><s:property value="friendRequestsReceivedList.size" /></span>
                      <s:if test="friendRequestsReceivedList.size == 1">
                        <u:translation key="label.squadRichiestaRicevuta" />
                      </s:if>
                      <s:else>
                        <u:translation key="label.squadRichiesteRicevute" />
                      </s:else>
                    </th>
                    <th style="width:150px;"><u:translation key="label.city" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.countryAbbr" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.roleAbbr" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.Eta" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.GiocateAbbr" /></th>
                    <th class="centred" style="width:55px;"><u:translation key="label.affidabilitaAbbr" /></th>
                    <th class="acceptCell"><u:translation key="label.squadAccetta" /></th>
                  </tr>
                  <s:iterator value="friendRequestsReceivedList" var="requestUser">
                    <tr>
                      <td>
                        <u:printUserName userInfo="${invitedUser.userInfo}" showAvatar="false" linkToDetails="true" showCurrentUserDetails="true" />
                      </td>
                      <td><s:property value="#requestUser.userInfo.city" /></td>
                      <td class="centred" >
                        <img src="<s:url value="/images/flags/%{userInfo.idNatCountry}.png"/>" title="<s:property value="%{userInfo.natCountry}"/>" onError="this.src='images/country_flag_0.gif';"/>
                       
                      </td>
                      <td class="centred" >
                        <s:set name="ruoloLang" ><u:translation key="${requestUser.userInfo.playerRoleKey}"/></s:set>
                        ${fn:substring(ruoloLang, 0, 1)}
                        <%--c:set var="playerRole"><s:property value="#requestUser.userInfo.playerRole" /></c:set>
                            ${fn:substring(playerRole, 0, 1)} --%>
                      </td>
                      <td class="centred" ><s:property value="#requestUser.userInfo.age" /></td>
                      <td class="centred" ><s:property value="#requestUser.allTot" /></td>
                      <td class="centred" ><s:property value="#requestUser.reliability" />%</td>
                      <td class="acceptCell">
                        <s:url action="acceptUserInvite" var="acceptInvite">
                          <s:param name="idUser"><s:property value="#requestUser.userInfo.id" /></s:param>
                        </s:url>
                        <s:url action="notAcceptUserInvite" var="notAcceptInvite">
                          <s:param name="idUser"><s:property value="#requestUser.userInfo.id" /></s:param>
                        </s:url>
                        <a href="${acceptInvite}"><u:translation key="label.Si"/></a>
                        &nbsp;
                        <a href="${notAcceptInvite}"><u:translation key="label.No"/></a>
                      </td>
                    </tr>
                  </s:iterator>
                </table>                
            </s:if>
              
           <s:if test="friendRequestsMadeList.size > 0 ">
                <table class="stdList">
                  <tr>
                    <th style="width:205px;">
                      <strong><s:property value="friendRequestsMadeList.size" /></strong>
                      <s:if test="friendRequestsMadeList.size == 1">
                        <u:translation key="label.squadRichiestaEffettuata" />
                      </s:if>
                      <s:else>
                        <u:translation key="label.squadRichiesteEffettuate" />
                      </s:else>
                    </th>
                    <th style="width:150px;"><u:translation key="label.city" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.countryAbbr" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.roleAbbr" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.Eta" /></th>
                    <th class="centred" style="width:30px;"><u:translation key="label.GiocateAbbr"/></th>
                    <th class="centred" style="width:55px;"><u:translation key="label.affidabilitaAbbr" /></th>
                    <th class="acceptCell delete"><u:translation key="label.squadRitira" /></th>
                  </tr>
                  <s:iterator value="friendRequestsMadeList" var="invitedUser">
                    <tr>
                      <td>
                        <u:printUserName userInfo="${invitedUser.userInfo}" showAvatar="false" linkToDetails="true" showCurrentUserDetails="true" />
                      </td>
                      <td><s:property value="#invitedUser.userInfo.city" /></td>
                      <td class="centred" ><img src="<s:url value="/images/flags/%{userInfo.idNatCountry}.png"/>" title="<s:property value="%{userInfo.natCountry}"/>" onError="this.src='images/country_flag_0.gif';"/></td>
                      <td class="centred" >
                        <s:set name="ruoloLang" ><u:translation key="${invitedUser.userInfo.playerRoleKey}"/></s:set>
                        ${fn:substring(ruoloLang, 0, 1)}
                        <%--c:set var="playerRole"><s:property value="#invitedUser.userInfo.playerRole" /></c:set>
                            ${fn:substring(playerRole, 0, 1)}--%>
                      </td>
                      <td class="centred" ><s:property value="#invitedUser.userInfo.age" /></td>
                      <td class="centred" ><s:property value="#invitedUser.allTot" /></td>
                      <td class="centred" ><s:property value="#invitedUser.reliability" />%</td>
                      <td class="acceptCell delete">
                        <s:url action="removeUserInvite" var="removeInvite">
                          <s:param name="idUser">
                            <s:property value="#invitedUser.userInfo.id" />
                          </s:param>
                        </s:url>
                        <a href="${removeInvite}">X</a>
                      </td>
                    </tr>
                  </s:iterator>
                </table>
                  <p>&nbsp;</p>
              </s:if>

            </div>

          </div>

            

          <div id="YUIdataTableContainer"   class="yui-skin-gokick noArrow"  style="margin-top:25px;"></div>

          <!--BEGIN SOURCE CODE DATATABLE =============================== -->

          <script type="text/javascript">
            YAHOO.util.Event.addListener(window, "load", function() {
              YAHOO.example.XHR_XML = function() {
                var myColumnDefs =
                  [
                  {key:"EmptySquad",      label:"" },
                  {key:"Friend",          label:"<span title='<u:translation key="label._Amici" />'>         <u:translation key="label._Amici" />              </span>", sortable:true},
                  {key:"CompleteName",    label:"", sortable:false},
                  {key:"NationalitySquad",label:"<span title='<u:translation key="label.country" />'>        <u:translation key="label.countryAbbr" />         </span>", sortable:true},
                  {key:"RoleSquad",       label:"<span title='<u:translation key="label.role"/>'>            <u:translation key="label.roleAbbr" />            </span>", sortable:true},
                  {key:"AgeSquad",        label:"<span title='<u:translation key="label.Eta" />'>            <u:translation key="label.Eta" />                 </span>", sortable:true},
                  {key:"Condition",       label:"<span title='<u:translation key="label.condizione" />'>     <u:translation key="label.condizioneFisicaAbbr" /></span>", sortable:true},
                  {key:"PlayedSquad",     label:"<span title='<u:translation key="label.Giocate" />'>        <u:translation key="label.GiocateAbbr" />         </span>", sortable:true},
                  {key:"Frequency",       label:"<span title='<u:translation key="label.GiocateMese" />'>    <u:translation key="label.Fr_giocateAlMeseAbbr" /></span>", sortable:true},
                  {key:"Goal",            label:"<span title='<u:translation key="label.Goal"/>'>            <u:translation key="label.Goal" />                </span>", sortable:true},
                  {key:"AvgGoal",         label:"<span title='<u:translation key="label.mediaGol" />'>       <u:translation key="label.mediaGoalAbbr" />       </span>", sortable:true},
                  {key:"AvgVote",         label:"<span title='<u:translation key="label.reportMediaVoto" />'><u:translation key="label.mediaVotoAbbr" />       </span>", sortable:true},
                  {key:"ReliabilitySquad",label:"<span title='<u:translation key="label.affidabilita" />'>   <u:translation key="label.affidabilitaAbbr" />    </span>", sortable:true},
                  {key:"Remove",          label:"" }
                  
              ];

                var myDataSource = new YAHOO.util.DataSource("<s:url action="statisticInfoDataTable" namespace="ajax" />?dataTableKey=<s:property value="dataTableKey" />");
                myDataSource.connMethodPost = true;
                myDataSource.responseType = YAHOO.util.DataSource.TYPE_XML;
                myDataSource.responseSchema = {
                  resultNode: "Result",
                  fields:["EmptySquad",
                          "Friend",
                          "CompleteName",
                          "NationalitySquad",
                          "RoleSquad",
                          "AgeSquad",
                          "Condition",
                          "PlayedSquad",
                          "Frequency",
                          "Goal",
                          "AvgGoal",
                          "AvgVote",
                          "ReliabilitySquad",
                          "Remove",
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
                  rowsPerPage: 20 /*${friendsCount} + 1*/,
                  alwaysVisible : false,
                  firstPageLinkLabel: '<u:translation key="label.paginator.first" escapeJavaScript="true" />',
                  previousPageLinkLabel: '<u:translation key="label.paginator.prev" escapeJavaScript="true" />',
                  nextPageLinkLabel: '<u:translation key="label.paginator.next" escapeJavaScript="true" />',
                  lastPageLinkLabel: '<u:translation key="label.paginator.last" escapeJavaScript="true" />'
                });

                var myConfigs = {
                  dynamicData: true,
                  sortedBy : {
                    key: "EmptySquad",
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

 <div class="indentCont">
<div  class="bottomAction">
                <s:form action="squad" method="post"   id="statisticPeriodForm">
                  <span><u:translation key="label.stat" /></span>
                  <s:select
                    name="statisticPeriod"
                    list="statisticPeriodList"
                    listKey="id"
                    listValue="label"
                    value="statisticPeriod"
                    onchange="javascript: submitChangeStatistic();" />
                </s:form>
              </div>
     <div class="clear">&nbsp;</div>
          <%--  STRUCTURE LAYOUT 2 COLUMN  div class="indentCont">
            <div class="left">

            </div>
            <div class="right" style="padding-right:20px;">
              <p>
               <!--
                [<a class="light" href="<s:url action="squadPreferences" method="input" />"><u:translation key="label.squadModifcaPreferenze" /></a>]
                -->
              </p>
            </div>
            <br class="clear" /--%>

            <p>&nbsp;</p><p>&nbsp;</p>

            <s:if test="userMarketEnabledList.size > 0 ">
              <p>
                <strong><u:translation key="label.squadSuggerimento" />  [<a  href="javascript: void(0);" onclick="openPopupHelp('S001')" >?</a>]</strong>
              </p>
                <display:table name="userMarketEnabledList" id="userMarketEnabled" requestURI="squad.action" class="stdList">
                <u:displayTablePaginator />
                <s:set name="titleRuolo"><u:translation key="label.roleAbbr"/></s:set>
                <s:set name="titleEta"><u:translation key="label.Eta"/></s:set>
                <s:set name="titleNazionalita"><u:translation key="label.countryAbbr"/></s:set>
                <s:set name="titleGiocate"><u:translation key="label.GiocateAbbr"/></s:set>
                <s:set name="titleAffidabilita"><u:translation key="label.affidabilitaAbbr"/></s:set>
                <s:set name="titleLocalita"><u:translation key="label.Localita"/></s:set>

                <display:column title="GoKickers" sortable="true"   sortProperty="userInfo.name" style="width:220px;">
                  <u:printUserName  linkToDetails="true"  userInfo="${userMarketEnabled.userInfo}" />
                </display:column>
                <display:column title="${titleLocalita}" sortable="true" style="width:150px;">
                ${userMarketEnabled.userInfo.city}
                </display:column>
                <display:column title="${titleNazionalita}" sortable="true" class="centred" headerClass="centred" style="width:25px;">
                  <img src="<s:url value="/images/flags/"/>${userMarketEnabled.userInfo.idNatCountry}.png" title="${userMarketEnabled.userInfo.natCountry}" onError="this.src='images/country_flag_0.gif';"/>
                </display:column>
                <display:column title="${titleRuolo}" sortable="true" sortProperty="userInfo.idPlayerRole" class="centred" headerClass="centred" style="width:25px;">
                  <s:set name="ruoloLang" ><u:translation key="${userMarketEnabled.userInfo.playerRoleKey}"/></s:set>
                  ${fn:substring(ruoloLang, 0, 1)}
                </display:column>
                <display:column title="${titleEta}" sortable="true" sortProperty="userInfo.birthday" class="centred" headerClass="centred" style="width:25px;">
                ${userMarketEnabled.userInfo.age}
                </display:column>
                <display:column title="${titleGiocate}" sortable="true" sortProperty="allTot" class="centred" headerClass="centred" style="width:25px;">
                ${userMarketEnabled.allTot}
                </display:column>
                <display:column title="${titleAffidabilita}" sortable="true" sortProperty="reliability" class="centred" headerClass="centred" style="width:65px;">
                ${userMarketEnabled.reliability}%
                </display:column>
                <display:column title="" class="play centred" style="width:25px;">
                  <s:url action="userDetails" var="userDetailsURL">
                    <s:param name="idUser">${userMarketEnabled.userInfo.id}</s:param>
                    <s:param name="tab">scheda</s:param>
                  </s:url>
                  <s:set name="completeName" >${userMarketEnabled.userInfo.name}_${userMarketEnabled.userInfo.surname}</s:set>
                  <s:set name="nameDet" >
                    <s:property value="#completeName" escapeJavaScript="true" />
                  </s:set>
                  <a href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');">
                    <img src="<s:url value="/images/gioca.gif" encode="false" />" alt="Gioca" />
                  </a>
                </display:column>
                  <display:column title="" style="width:5px;" class="spaceCell" headerClass="spaceCell" ></display:column>
              </display:table>
            </s:if>
            <s:else>
              <p>
                <u:translation key="label.squadNoGkMercatoProvincia" />
              </p>
            </s:else>

            

            <div class="right" style="margin-top:10px; padding-right:20px;color:#666666;">
              <p>
                <s:url action="userAll" method="viewAll" var="viewAllUrl" >
                  <s:param name="onlyMarketEnabled" value="true" />
                </s:url>
                <a class="light" href="${viewAllUrl}">
                <u:translation key="label.squadVediTutti" /> <s:property value="currentUser.province.name" /> <u:translation key="label.eProvincia" />
                &raquo;</a>
              </p>
              
              <p style="padding-top:15px;">
                ${currentUser.province.name} <u:translationArgs key="label.squadProvinceNotLuogo" />
                <a class="light" href="<s:url action="userAccount!input" />"><u:translation key="label.modificaloIn" /> <u:translation key="label.Account" /></a>
              </p>
            </div>

            <br class="clear" />

          </div>

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