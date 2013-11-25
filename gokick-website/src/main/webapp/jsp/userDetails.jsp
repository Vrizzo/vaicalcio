<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>


<s:set value="userToShow" var="varUserToShow" />
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>

    <jsp:include page="../jspinc/commonHead.jsp" flush="true" >
      <jsp:param name="dataTable" value="true" />
    </jsp:include>

    <link rel="stylesheet" href="<s:url value="/css/popup.css" encode="false" />" type="text/css" media="all" />
    <title><s:property value="%{getText('page.title.default')}" /></title>

    <script type="text/javascript">

      function submitFeedbackForm()
      {
        if ($('idSelectTech').value=="")
        {
          alert("<u:translation key='label.valorizzare'/> <u:translation key='label.technical'/>");
          return;
        };
        if ($('idSelectReliability').value=="")
        {
          alert("<u:translation key='label.valorizzare'/> <u:translation key='label.Affidabilita'/>");
          return;
        }
        if ($('idSelectFairplay').value=="")
        {
          alert("<u:translation key='label.valorizzare'/> <u:translation key='label.fairplay'/>");
          return;
        }
        if ($('idtextDetail').value=="" || $('idtextDetail').value=="Inserisci un comm  ento...")
        {
          alert("<u:translation key='label.InserireMessaggio'/>");
          return;
        }
        $('idFeedbackForm').submit();
      }

      function submitPrivateMessageForm()
      {
        if ($('idPrivateMessage').value=="" || $('idPrivateMessage').value=="messaggio priva  to...")
        {
          alert("<u:translation key='error.matchComment.TestoMancante'/>");
          return;
        }
        $('idPrivateMessageForm').submit();
      }

      function submitNotifyAbuseForm()
      {
        $('idDivMessageSent').style.visibility="hidden";
        var text = $('idAbuseText').value.trim();
        if( $('idAbuseList').value==0 ||
          (text=='' || text=='<u:translation key="label.scheda.abuso.Segnalazione.textArea"/>') )
        {
          $('idDivError').style.visibility="visible";
        }
        else
        {
          $('idDivError').style.visibility="hidden";
          $('idFormAbuse').submit();
        }
      }
      
      function clearAbuseText()
      {
        var text = $('idAbuseText').value;
        if (text=='<u:translation key="label.scheda.abuso.Segnalazione.textArea"/>')
        $('idAbuseText').value='';
      }
      
      
    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body class="userDetails yui-skin-gokick">

    <s:if test="close == true">
      <script type="text/javascript">
        window.opener.location.reload();
        window.close();
      </script>
    </s:if>
    <s:if test="userToShow.anonymousEnabled">
      <p class="topMenuLoggedIn"></p>
      <div class="content">
        <u:translation key="label.userDetailsUtenteAnonimo"/>
      </div>
    </s:if>
    <s:elseif test="userDeleted">
      <p class="topMenuLoggedIn"></p>
      <div class="content">
        <u:translation key="label.userDetailsDeleted"/>
      </div>
    </s:elseif>

    <s:else>

      <input type="hidden" value="${tab}" id="idTabHiddenBox" />

      <!-- start top menu-->
      <p class="topMenuLoggedIn">
        <c:choose>
          <c:when test="${tab=='abuse'}">
          </c:when>
          <c:otherwise>
            <s:url action="userDetails" var="schedaUrl">
              <s:param name="idUser" value="idUser"/>
              <s:param name="tab" >scheda</s:param>
            </s:url>
            <a class="${tab=='scheda'?'active':''}" href="${schedaUrl}"><s:property value="%{getText('label.scheda')}" /></a>

            <s:url action="userDetails" var="amiciUrl">
              <s:param name="idUser" value="idUser"/>
              <s:param name="tab" >amici</s:param>
            </s:url>
            <a class="${tab=='amici'?'active':''}" href="${amiciUrl}">${squadInfoToShow.playersTot} <s:property value="%{getText('label.amici')}" /></a>

          </c:otherwise>
        </c:choose>
      </p>
      <!-- end top menu-->

      <div class="content">

        <p class="topInfo">
          <c:choose>
            <c:when test="${tab=='abuse'}">
              <b><u:translation key="label.scheda.abuso.title"/></b>
            </c:when>
            <c:otherwise>
              <s:property value="userToShow.name" /> <s:property value="userToShow.surname" /> <u:printPlayMorePartner playMorePartner="${userToShow.playMorePartner}" link="false" />
              <s:property value="%{getText('label.separatore')}" /> <s:property value="userToShow.playerTitle" />
              <img src="<s:url value="/images/flags/%{userToShow.idNatCountry}.png"/>" title="${userToShow.natCountry}" onError="this.src='<s:url value="/images/country_flag_0.gif"/>';"/>
              <span id="yuiSpinner" style="visibility:${tab=='amici'?'visible':'hidden'}">
                <img alt="" src="<s:url value="/images/spinnerFF.gif" encode="false" />" />
              </span>
            </c:otherwise>
          </c:choose>
        </p>

        <s:if test="tab=='scheda'">
          <div id="idBoxScheda">

            <table class="infoList">
              <tr>
                <td style="width:22%;"><s:property value="%{getText('label.giocaA')}" /></td>
                <td class="valueField"><s:property value="userToShow.city"/> <s:property value="%{getText('label.separatore')}" />
                  <s:property value="userToShow.province"/> <s:property value="%{getText('label.separatore')}" />
                  <s:property value="userToShow.country"/>
                </td>

                <td rowspan="12" class="picCol">
                  <s:url action="pictureCard" id="viewPictureCardURL" method="viewPlayerPictureCard" >
                    <s:param name="idUser" value="userToShow.id"></s:param>
                  </s:url>
                  <img src="<s:property value="#viewPictureCardURL"/>"
                       title="<s:property value="userToShow.name" /> <s:property value="userToShow.surname" />" />
                  <div class="left">
                    <s:property value="%{getText('label.condizione')}" /><br/>
                    <u:printUserPhysicalCondition userToShow="${varUserToShow}" description='true'/>
                  </div>
                  <div class="right">
                    <s:property value="%{getText('label.mercato')}" /><br />
                    <span class="valueField">
                      <s:if test="userToShow.marketEnabled" >
                        <img src="<s:url value="/images/gioca.gif"/>" alt="" /><br />
                        <s:property value="%{getText('label.sulMercato')}" />
                      </s:if>
                      <s:else >
                        <img src="<s:url value="/images/notgioca.gif" />" alt="" style="visibility:hidden;"/><br/>
                        <s:property value="%{getText('label.nonSulMercato')}" />
                      </s:else>
                    </span>
                  </div>

                </td>

              </tr>
              <tr>
                <td>
                  <s:property value="%{getText('label.natoA')}" />
                </td>
                <td class="valueField">
                  <s:if test="userToShow.withBirthdayInfo" >
                    <s:property value="userToShow.birthdayCity"/> <s:property value="%{getText('label.separatore')}" />
                    <s:property value="userToShow.birthdayProvince"/> <s:property value="%{getText('label.separatore')}" />
                    <s:property value="userToShow.birthdayCountry"/>
                  </s:if>
                  <s:else>
                    <s:property value="userToShow.birthdayCountry"/>
                  </s:else>
                </td>
              </tr>
              <tr>
                <td><s:property value="%{getText('label.etaAltPes')}" /></td>
                <td class="valueField">
                  <u:printUserEtaAltezzaPeso userToShow="${varUserToShow}" />
                </td>
              </tr>
              <tr>
                <td><s:property value="%{getText('label.ruoloPiedeMaglia')}" /></td>
                <td class="valueField">
                  <u:printUserInfoRuoloPiedeMaglia userToShow="${varUserToShow}" />
                </td>
              </tr>
              <tr>
                <td><s:property value="%{getText('label.caratteristica')}" /></td>
                <td class="valueField"><s:property value="userToShow.playerMainFeature"/></td>
              </tr>
              <tr>
                <td><p><s:property value="%{getText('label.tifo')}" /></p></td>
                <td class="valueField"><p><s:property value="userToShow.footballTeam"/></p></td>
              </tr>
              <tr>
                <td><s:property value="%{getText('label.idolo')}" /></td>
                <td class="valueField"><s:property value="userToShow.infoFavouritePlayer"/></td>
              </tr>
              <%-- <tr>
                          <td><s:property value="%{getText('label.occupazione')}" /></td>
                          <td class="valueField">Agenzia di comunicazione</td>
              </tr> --%>
              <tr>
                <td><s:property value="%{getText('label.hobby')}" /></td>
                <td class="valueField"><s:property value="userToShow.infoHobby"/></td>
              </tr>
              <tr>
                <td><s:property value="%{getText('label.sogno')}" /></td>
                <td class="valueField"><s:property value="userToShow.infoDream"/></td>
              </tr>
              <tr>
                <td><p><s:property value="%{getText('label.goKickerDal')}" /></p></td>
                <td class="valueField"><p><utl:formatDate date="${userToShow.created}" formatKey="format.date_9" /></p></td>
              </tr>
              <tr>
                <td><s:property value="%{getText('label.giocate')}" /></td>
                <td class="valueField">${statisticInfoTot.allTot > 0 ? statisticInfoTot.allTot : '0' } <u:translation key="label.udmPartite"/> - <u:translation key="label._affidabilita"/> <s:property value="squadInfoToShow.ownerStatisticInfo.reliability" />%</td>
              </tr>
              <tr>
                <td><s:property value="%{getText('label._Organizzate')}" /></td>

                <td class="valueField">${userToShow.recordedMatches > 0 ? userToShow.recordedMatches : '0' } <u:translation key="label.udmPartite"/></td>

              </tr>
              <tr>
                <td style="border:0;">
                  <br/>
                </td>
              </tr>
              <tr class="spot">

                <td><p><s:property value="%{getText('label.annuncio')}" /></p></td>
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

                  <s:include value="../jspinc/userDetails_contact.jsp" />

                </td>
              </tr>
            </table>

          </div>
        </s:if>

        <s:if test="tab=='amici'">
          <div id="idBoxAmici">
            <div class="roseCont">

              <s:if test="squadInfoToShow.squadInfo.valid && squadVisible">

                <%--table class="infoList">
                  <tr>
                    <td style="width:22%;"><s:property value="%{getText('label.friends')}" />:</td>
                    <td class="valueField">
                      <u:printUserSquadInfo squadInfoToShow="${squadInfoToShow}" />
                    </td>
                  </tr>
                  <tr>
                    <td><s:property value="%{getText('label.gamesDay')}" /></td>
                    <td class="valueField">
                      <s:set value="squadInfoToShow.squadInfo.playingWeekdays" var="playingWeekdays" />
                      <u:printPlayingWeekdays playingWeekdays="${playingWeekdays}" />
                    </td>
                  </tr>
                  <tr>
                    <td>
                    <s:property value="%{getText('label.description')}" /></td>
                    <td class="valueField"><s:property value="squadInfoToShow.squadInfo.description" />
                    </td>
                  </tr>
                </table--%>

                <div id="YUIdataTableContainer"   class="yui-skin-gokick noArrow" ></div>

                <!--BEGIN SOURCE CODE DATATABLE =============================== -->

                <script type="text/javascript">
                  YAHOO.util.Event.addListener(window, "load", function() {
                    YAHOO.example.XHR_XML = function() {
                      var myColumnDefs =
                        [
                        {key:"EmptySquad",label:""},
                        {key:"CompleteName",label:"<span title='<u:translation key="label._Amici" />'>         <u:translation key="label._Amici" />              </span>", sortable:true},
                        {key:"Nationality", label:"<span title='<u:translation key="label.country" />'>        <u:translation key="label.countryAbbr" />         </span>", sortable:true},
                        {key:"Role",        label:"<span title='<u:translation key="label.role"/>'>            <u:translation key="label.roleAbbr" />            </span>", sortable:true},
                        {key:"Age",         label:"<span title='<u:translation key="label.Eta" />'>            <u:translation key="label.Eta" />                 </span>", sortable:true},
                        {key:"Condition",   label:"<span title='<u:translation key="label.condizione" />'>     <u:translation key="label.condizioneFisicaAbbr" /></span>", sortable:true},
                        {key:"Played",      label:"<span title='<u:translation key="label.Giocate" />'>        <u:translation key="label.GiocateAbbr" />         </span>", sortable:true},
                        {key:"Frequency",   label:"<span title='<u:translation key="label.GiocateMese" />'>    <u:translation key="label.Fr_giocateAlMeseAbbr" /></span>", sortable:true},
                        {key:"Goal",        label:"<span title='<u:translation key="label.Goal"/>'>            <u:translation key="label.Goal" />                </span>", sortable:true},
                        {key:"AvgGoal",     label:"<span title='<u:translation key="label.mediaGol" />'>       <u:translation key="label.mediaGoalAbbr" />       </span>", sortable:true},
                        {key:"AvgVote",     label:"<span title='<u:translation key="label.reportMediaVoto" />'><u:translation key="label.mediaVotoAbbr" />       </span>", sortable:true},
                        {key:"Reliability", label:"<span title='<u:translation key="label.affidabilita" />'>   <u:translation key="label.affidabilitaAbbr" />    </span>", sortable:true}
                  

                      ];

                      var myDataSource = new YAHOO.util.DataSource("<s:url action="statisticInfoDataTable" namespace="ajax" />?dataTableKey=<s:property value="dataTableKey" />&linkPlayMorePartnerStatus=false");
                      myDataSource.connMethodPost = true;
                      myDataSource.responseType = YAHOO.util.DataSource.TYPE_XML;
                      myDataSource.responseSchema = {
                        resultNode: "Result",
                        fields:["EmptySquad",
                          "CompleteName",
                          "Nationality",
                          "Role",
                          "Age",
                          "Condition",
                          "Played",
                          "Frequency",
                          "Goal",
                          "AvgGoal",
                          "AvgVote",
                          "Reliability",
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
                        rowsPerPage: 14,
                        alwaysVisible : false,
                        firstPageLinkLabel: '<u:translation key="label.paginator.first" escapeJavaScript="true" />',
                        previousPageLinkLabel: '<u:translation key="label.paginator.prev" escapeJavaScript="true" />',
                        nextPageLinkLabel: '<u:translation key="label.paginator.next" escapeJavaScript="true" />',
                        lastPageLinkLabel: '<u:translation key="label.paginator.last" escapeJavaScript="true" />'
                      });

                      var myConfigs = {
                        dynamicData: true,
                        sortedBy : {
                          key: "CompleteName",
                          dir: YAHOO.widget.DataTable.CLASS_ASC // Sets UI initial sort arrow
                        },
                        paginator: myPaginator,
                        formatRow: myRowFormatter,
                        MSG_EMPTY: '<u:translation key="message.NessunRisultato" escapeJavaScript="true" />',
                        MSG_LOADING: '<u:translation key="message.CaricamentoInCorso" escapeJavaScript="true" />',
                        MSG_SORTDESC: '<u:translation key="label.YuiTableOrdina" escapeJavaScript="true" />'
                      };

                      var myDataTable = new YAHOO.widget.DataTable("YUIdataTableContainer", myColumnDefs,
                      myDataSource, myConfigs);

                      // Subscribe to events for row selection
                      //myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow);
                      myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
                      myDataTable.doBeforeSortColumn =function (){$('yuiSpinner').style.visibility='visible';return true;}
                      //myDataTable.subscribe("rowClickEvent", myDataTable.onEventSelectRow);

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
                <p>
                  <u:translation key="message.userDetailsNoRosa" />
                </p>
              </s:else>

              <div class="bottomAction">
                <s:include value="../jspinc/userDetails_contact.jsp" />
              </div>
              <div class="clear">&nbsp;</div>

            </div>
          </div>
        </s:if>

        <s:if test="tab=='statistiche'">
          <div id="idBoxStatistiche" class="userStatistics">

            <s:if test="statisticInfoList.size > 0">
              <div class="contStats">
                <table>
                  <tr>
                    <th><strong><u:translation key="label.Anno" /></strong></th>
                    <th><strong><u:translation key="label.GiocateAbbr" /></strong></th>
                    <th><strong><u:translation key="label.affidabilitaAbbr" /></strong></th>
                    <th><strong><u:translation key="label.giocateAlMeseAbbr" /></strong></th>
                    <th><strong><u:translation key="label.vinteAbbr" /></strong></th>
                    <th><strong><u:translation key="label.pareggiateAbbr" /></strong></th>
                    <th><strong><u:translation key="label.perseAbbr" /></strong></th>
                    <th><strong><u:translation key="label.Goal" /></strong></th>
                    <th class="lblTotGoals"><u:translation key="label.autogoalAbbr" /></th>
                    <th><strong><u:translation key="label.mediaGoalAbbr" /></strong></th>
                    <th><strong><u:translation key="label.mediaVotoAbbr" /></strong></th>
                    <th class="lblMatch"><u:translation key="label.partitaAbbr" /></th>
                    <th class="lblMatch"><u:translation key="label._vinteAbbr" /></th>
                    <th class="lblMatch"><u:translation key="label._pareggiateAbbr" /></th>
                    <th class="lblMatch"><u:translation key="label._perseAbbr" /></th>
                    <th class="lblchallenge"><u:translation key="label.sfideAbbr" /></th>
                    <th class="lblMatch"><u:translation key="label._vinteAbbr" /></th>
                    <th class="lblMatch"><u:translation key="label._pareggiateAbbr" /></th>
                    <th class="lblMatch"><u:translation key="label._perseAbbr" /></th>
                    <th class="lblMatch"><strong><u:translation key="label.partiteArchiviateAbbr" /></strong></th>
                    <th class="lblchallenge"><strong><u:translation key="label.sfideArchiviateAbbr" /></strong></th>
                  </tr>
                  <s:iterator value="statisticInfoList" var="statisticInfo">
                    <tr>
                      <td class="first"><s:property value="#statisticInfo.year" /></td>
                      <td><strong><s:property value="#statisticInfo.allTot" /></strong></td>
                      <td><s:property value="#statisticInfo.reliability" />%</td>
                      <td><s:property value="#statisticInfo.monthPlayed" /></td>
                      <td><s:property value="#statisticInfo.allWin" /></td>
                      <td><s:property value="#statisticInfo.allDraw" /></td>
                      <td><s:property value="#statisticInfo.allLose" /></td>
                      <td><strong><s:property value="#statisticInfo.goalsTot" /></strong></td>
                      <td class="lblTotGoals"><s:property value="#statisticInfo.ownGoalsTot" /></td>
                      <td><s:property value="#statisticInfo.averageGoal" /></td>
                      <td><s:property value="#statisticInfo.averageVote" /></td>
                      <td class="lblMatch"><strong><s:property value="#statisticInfo.matchTot" /></strong></td>
                      <td class="lblMatch"><s:property value="#statisticInfo.matchWin" /></td>
                      <td class="lblMatch"><s:property value="#statisticInfo.matchDraw" /></td>
                      <td class="lblMatch"><s:property value="#statisticInfo.matchLose" /></td>
                      <td class="lblchallenge"><strong><s:property value="#statisticInfo.challangeTot" /></strong></td>
                      <td class="lblchallenge"><s:property value="#statisticInfo.challangeWin" /></td>
                      <td class="lblchallenge"><s:property value="#statisticInfo.challangeDraw" /></td>
                      <td class="lblchallenge"><s:property value="#statisticInfo.challangeLose" /></td>
                      <td class="lblMatch"><s:property value="#statisticInfo.matchStored" />/<s:property value="#statisticInfo.matchOrganized" /></td>
                      <td class="lblchallenge last"><s:property value="#statisticInfo.challangeStored" />/<s:property value="#statisticInfo.challangeStored" /></td>
                    </tr>
                  </s:iterator>
                  <tr>
                    <td class="first"><strong><u:translation key="label.totaleAbbr" /></strong></td>
                    <td><strong><s:property value="statisticInfoTot.allTot" /></strong></td>
                    <td><s:property value="statisticInfoTot.reliability" />%</td>
                    <td><s:property value="statisticInfoTot.monthPlayed" /></td>
                    <td><s:property value="statisticInfoTot.allWin" /></td>
                    <td><s:property value="statisticInfoTot.allDraw" /></td>
                    <td><s:property value="statisticInfoTot.allLose" /></td>
                    <td><strong><s:property value="statisticInfoTot.goalsTot" /></strong></td>
                    <td class="lblTotGoals"><s:property value="statisticInfoTot.ownGoalsTot" /></td>
                    <td><s:property value="statisticInfoTot.averageGoal" /></td>
                    <td><s:property value="statisticInfoTot.averageVote" /></td>
                    <td class="lblMatch"><strong><s:property value="statisticInfoTot.matchTot" /></strong></td>
                    <td class="lblMatch"><s:property value="statisticInfoTot.matchWin" /></td>
                    <td class="lblMatch"><s:property value="statisticInfoTot.matchDraw" /></td>
                    <td class="lblMatch"><s:property value="statisticInfoTot.matchLose" /></td>
                    <td class="lblchallenge"><strong><s:property value="statisticInfoTot.challangeTot" /></strong></td>
                    <td class="lblchallenge"><s:property value="statisticInfoTot.challangeWin" /></td>
                    <td class="lblchallenge"><s:property value="statisticInfoTot.challangeDraw" /></td>
                    <td class="lblchallenge"><s:property value="statisticInfoTot.challangeLose" /></td>
                    <td class="lblMatch"><s:property value="statisticInfoTot.matchStored" />/<s:property value="#statisticInfo.matchOrganized" /></td>
                    <td class="lblchallenge last"><s:property value="statisticInfoTot.challangeStored" />/<s:property value="#statisticInfo.challangeStored" /></td>
                  </tr>
                </table>

              </div>

              <center>
                <p>
                  <strong>
                    <u:translation key="label.userDetailsArchiviaInfo" />
                  </strong>
                </p>
                <p>
                  <u:translation key="label.userDetailsLegendaStat" />
                </p>
              </center>
            </s:if>
            <s:else>
              <u:translation key="message.noStat" />
            </s:else>

          </div>
        </s:if>

        <!--        FEEDBACK NON USATA-->
        <%--
     <s:if test="tab=='feedback'">
       <div id="idBoxFeedback" class="userFeedback">

            <s:if test="feedback.fbSize>0">

              <p>
                <u:printStars vote="${feedback.totalAvg}" />
                <strong><s:property value="%{getText('label.valutazioneMedia')}" /></strong>
                <s:property value="%{getText('label.su')}" /> ${feedback.fbSize}
                <s:property value="%{getText('label.feedback')}" />
              </p>

              <p class="fbDetail">
                <span>
                  <u:printStars vote="${feedback.technicalAvg}" />
                  <s:property value="%{getText('label.technical')}" />
                </span>

                <span>
                  <u:printStars vote="${feedback.reliabilityAvg}" />
                  <s:property value="%{getText('label.Affidabilita')}" />
                </span>

                <span>
                  <u:printStars vote="${feedback.fairPlayAvg}" />
                  <s:property value="%{getText('label.fairplay')}" />
                </span>

              </p>

              <display:table name="feedback.feedbackRowInfoList" id="feedbackRow" requestURI="userDetails.action" pagesize="7"   class="friendList">
                <u:displayTablePaginator />
                <s:set id="titleCommenti" value="%{getText('label.commenti')}" />
                <s:set id="titleDa" value="%{getText('label.da')}" />
                <s:set id="titleData" value="%{getText('label.data')}" />

                <display:column title="${titleCommenti}" >
                  ${feedbackRow.textdetail}
                </display:column>
                <display:column title="${titleDa}" >
                  ${feedbackRow.nameAuthor}
                </display:column>
                <display:column title="${titleData}" >
                  ${feedbackRow.data}
                </display:column>
              </display:table>

            </s:if>



            <s:if test="idUser!=currentUser.id">

              <s:form action="feedbackMessage!insertFeedback" method="post" id="idFeedbackForm" validate="false">

                <s:hidden name="idUser" />
                <s:hidden name="tab" />
                <table>
                  <s:if test="lastFeedback!=null">
                    <tr>
                      <th colspan="3"> ${lastDateFeedback} <u:translation key="message.feedbackLast" />:
                        <p>
                          ${lastFeedback.textDetail}
                        </p>
                      </th>
                    </tr>
                  </s:if>

                  <tr>
                    <td>
                      <s:property value="%{getText('label.technical ')}" />
                    </td>
                    <td>
                      <s:select
                        id="idSelectTech"
                        emptyOption="false"
                        headerKey=""
                        headerValue="%{getText('label.valuta')}"
                        name="idSelectTech"
                        list="#{'5':'5 '+ getText('label.stars'),
                                '4':'4 '+ getText('label.stars'),
                                '3':'3 '+ getText('label.stars'),
                                '2':'2 '+ getText('label.stars'),
                                '1':'1 '+ getText('label.star')
                        }"
                        />                             
                    </td>
                    <td rowspan="3">
                      <s:set name="insertFeedback"><u:translation key='label.inserireCommento'/></s:set>
                      <s:textfield name="textDetail"  id="idtextDetail" size="65" value="%{insertFeedback}" onclick="$('idtextDetail').value=''" ></s:textfield>
                      <p>
                        <small> <s:property value="%{getText('label.infoFeedback ')}" /></small>
                      </p>
                      <p>
                        <a href="javascript: submitFeedbackForm();" class="btn">OK</a>
                      </p>
                    </td>
                  </tr>

                  <tr>
                    <td>
                      <s:property value="%{getText('label.Affidabilita')}" />
                    </td>
                    <td>
                      <s:select
                        id="idSelectReliability"
                        emptyOption="false"
                        headerKey=""
                        headerValue="%{getText('label.valuta')}"
                        name="idSelectReliability"
                        list="#{'5':'5 '+ getText('label.stars'),
                                '4':'4 '+ getText('label.stars'),
                                '3':'3 '+ getText('label.stars'),
                                '2':'2 '+ getText('label.stars'),
                                '1':'1 '+ getText('label.star')
                        }"
                        />
                    </td>
                  </tr>

                  <tr>
                    <td>
                      <s:property value="%{getText('label.fairplay')}" />
                    </td>
                    <td>
                      <s:select
                        id="idSelectFairplay"
                        emptyOption="false"
                        headerKey=""
                        headerValue="%{getText('label.valuta')}"
                        name="idSelectFairplay"
                        list="#{'5':'5 '+ getText('label.stars'),
                                '4':'4 '+ getText('label.stars'),
                                '3':'3 '+ getText('label.stars'),
                                '2':'2 '+ getText('label.stars'),
                                '1':'1 '+ getText('label.star')
                        }"
                        />
                    </td>
                  </tr>

                </table>

              </s:form>
            </s:if>

          </div>
        </s:if>
        --%>
        <!--        FEEDBACK NON USATA-->

        <s:if test="tab=='abuse'">
          <div id="idBoxAbuse">

            <div class="highlightedHead centred" style="margin:15px 0 10px 0;visibility:${abuseMailSent==true?'visible':'hidden'}" id="idDivMessageSent">
              <strong><u:translation key="label.scheda.abuso.topMessage"/></strong>
            </div>

            <s:form action="../sendAbuseNotify.action" id="idFormAbuse"  >
              <s:hidden name="idUser"/>
              <s:hidden name="tab"/>
              <table class="frmAbuseNotify">

                <tr>
                  <td class="right"><u:translation key="label.scheda.abuso.Motivo"/>:</td>
                  <td>
                    <s:select
                      headerKey="0"
                      headerValue="%{getText('label.Seleziona')}..."
                      listKey="id"
                      listValue="translation"
                      list="abuseReasonsList"
                      name="idSelectedAbuseReason"
                      id="idAbuseList"
                      />
                  </td>
                </tr>

                <tr>
                  <td class="right">
                    <u:translation key="label.scheda.abuso.Segnalazione"/>:
                  </td>
                  <td>
                    <s:textarea
                      rows="8"
                      cols="60"
                      value="%{getText('label.scheda.abuso.Segnalazione.textArea')}"
                      name="abuseText"
                      id="idAbuseText"
                      onclick="clearAbuseText()"
                      />
                  </td>
                </tr>

                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <p class="note"><u:translation key="label.scheda.abuso.Segnalazione.info"/></p>
                  </td>
                </tr>

                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <div  class="errorMessage" style="visibility:hidden;" id="idDivError">
                      <u:translation key="label.scheda.abuso.Segnalazione.message"/>
                    </div>
                  </td>
                </tr>
                  
                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <a  href="javascript:submitNotifyAbuseForm()" class="btn action1" >
                      <u:translation key="label.scheda.abuso.Segnala"/>
                    </a>
                    &nbsp;&nbsp;
                    <s:url action="userDetails" var="schedaUrl">
                      <s:param name="idUser" value="idUser"/>
                      <s:param name="tab" >scheda</s:param>
                    </s:url>

                    <a class="btn action2" href="${schedaUrl}">
                      <u:translation key="label.scheda.abuso.toScheda"/>
                    </a>
                  </td>
                </tr>

              </table>
            </s:form>
          </div>
        </s:if>

      </div>

    </s:else>
  </body>
</html>
