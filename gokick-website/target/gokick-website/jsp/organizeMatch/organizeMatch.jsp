<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
<!--        <base href="<u:GKbase/>"/>-->
        <jsp:include page="../../jspinc/commonHead.jsp" flush="true" >
            <jsp:param name="calendar" value="true" />
        </jsp:include>

        <script type="text/javascript">

            function initializeForm()
            {
                if( $('idTxtMobile').value.length > 0 )
                    $('idClearMobileTxt').show();
                else
                    $('idClearMobileTxt').hide();

                if( $('idSelectMatchType').value > 0 )
                    $('idParticipantsBox').style.display = 'inline';
                else
                    $('idParticipantsBox').hide();

                if( $('idRadioRegOpentrue').checked == true )
                    $('idRegistrationOpenBox').show();
                else
                    $('idRegistrationOpenBox').hide();

                if( $('idSelectDirectlyRegistration').value == 'true' )
                    $('idMaxSquadOutBox').show();
                else
                    $('idMaxSquadOutBox').hide();
                if( $('idSelectRepetitive').value == 'true' )
                {
                    $('idRepetitiveBox').show();
                    $('idSubscriptionsRepetitiveBox').show();
                    $('idSubscriptionsNotRepetitiveBox').hide();
                }
                else
                {
                    $('idRepetitiveBox').hide();
                    $('idSubscriptionsRepetitiveBox').hide();
                    $('idSubscriptionsNotRepetitiveBox').style.display = '';
                }
                if( $('idSelectSubNotRep').value == 3 )
                    $('idSubNotRepDateBox').show();
                else
                    $('idSubNotRepDateBox').hide();
                if( $('idSelectRepetitive').value == 'true' && $('idRadioSubReptrue').checked == true )
                    $('idSubRepDateBox').show();
                else
                    $('idSubRepDateBox').hide();
            }

            function hideShowMatchParticipantsNumber(idMatchType)
            {
                if( $('idSelectMatchType').value > 0 )
                {
                    $('idParticipantsBox').style.display = 'inline';
                    $('idSelectMatchParticipantsNumber').options.length = 1;
                    $('idSelectMaxSquadOut').options.length = 1;

                    var value = idMatchType * 2;
                    var endValue = (idMatchType * 4) - value;
                    for (var j=0; j <= endValue; j++)
                    {
                        $('idSelectMatchParticipantsNumber').options[j] = new Option(value, value);
                        value++;
                    }
                    $('idSelectMatchParticipantsNumber').options[0].selected= true;
                    setSubstitutions();
                    setMaxSquadOutList();
                }
                else
                {
                    $('idParticipantsBox').hide();
                    $('idSelectMatchParticipantsNumber').options.length = 1;
                    $('idSelectMaxSquadOut').options.length = 1;
                }
            }

            function setSubstitutions ()
            {
                var sostituzioni = ($('idSelectMatchParticipantsNumber').value) - ($('idSelectMatchType').value * 2);
                if (sostituzioni<0) sostituzioni=0;
                $('idSostituzioni').innerHTML=sostituzioni +  " <u:translation key='label.substitutions'/>";
            }

            function setMaxSquadOutList()
            {
                $('idSelectMaxSquadOut').options.length = 1;
                for (var j=0; j < $('idSelectMatchParticipantsNumber').value; j++)
                {
                    $('idSelectMaxSquadOut').options[j+1] = new Option(j+1, j+1);
                }
                $('idSelectMaxSquadOut').options[j].selected= true;
                $('idsquadOutNumber').innerHTML=$('idSelectMaxSquadOut').value;
            }

            function hideShowRegistrationOpen()
            {
                if( $('idRadioRegOpentrue').checked == true )
                {
                    $('idSpanMatchType').show();
                    $('idRegistrationOpenBox').show();
                    $('idSelectDirectlyRegistration').options[0].selected= true;
                    $('idSpanMatchTypePrivate').hide();
                    if( $('idSelectDirectlyRegistration').value == 'true' )
                    {
                        $('idMaxSquadOutBox').show();
                        $('idSpanDirectlyRegistration').show();
                        $('idSpanNotDirectlyRegistration').hide();
                    }
                    else
                    {
                        $('idMaxSquadOutBox').hide();
                        $('idSpanDirectlyRegistration').hide();
                        $('idSpanNotDirectlyRegistration').show();
                    }
                }
                else if ($('idRadioRegOpenfalse').checked == true)
                {
                    $('idMaxSquadOutBox').hide();
                    $('idSpanMatchType').hide();
                    $('idRegistrationOpenBox').hide();
                    $('idSpanMatchTypePrivate').show();
                }
                else
                {
                    $('idRegistrationOpenBox').hide();
                    $('idMaxSquadOutBox').hide();
                    $('idSpanMatchType').hide();
                }
            }

            function hideShowMaxSquadOut()
            {
                if( $('idSelectDirectlyRegistration').value == 'true' )
                {
                    $('idMaxSquadOutBox').show();
                    $('idSpanDirectlyRegistration').show();
                    $('idSpanNotDirectlyRegistration').hide();
                }
                else
                {
                    $('idMaxSquadOutBox').hide();
                    $('idSpanDirectlyRegistration').hide();
                    $('idSpanNotDirectlyRegistration').show();
                }
            }

            function hideShowAccepTermination()
            {
                if( $('idRadioAccepTerminationtrue').checked == true )
                {
                    $('idAccepTerminationBox').show();
                    $('idAccepTerminationMaxHours').options[2].selected= true;
                    $('idSpanNocheckout').hide();
                    showAcceptTerminationLimit(24);
                }
                else
                {
                    $('idAccepTerminationBox').hide();
                    $('idSpanNocheckout').show();
                }
            }

            function hideShowRepetitive()
            {
                $('idSpanIscrizioniAperte').hide();
                $('idSpanIscrizioniChiuse').show();
                if( $('idSelectRepetitive').value == 'true' )
                {
                    $('idRepetitiveBox').show();
                    $('idSubscriptionsRepetitiveBox').show();
                    $('idSubscriptionsNotRepetitiveBox').hide();
                    $('idSubNotRepDateBox').hide();
                    if( $('idRadioSubReptrue').checked == true )
                        $('idSubRepDateBox').show();
                    else
                        $('idSubRepDateBox').hide();
                }
                else
                {
                    hideShowSubNotRepDate();
                    $('idRepetitiveBox').hide();
                    $('idSubscriptionsRepetitiveBox').hide();
                    $('idSubscriptionsNotRepetitiveBox').style.display = '';
                    if( $('idSelectSubNotRep').value == 3 )
                        $('idSubNotRepDateBox').show();
                    else
                        $('idSubNotRepDateBox').hide();
                    $('idSubRepDateBox').hide();
                }
            }

            function hideShowSubNotRepDate()
            {
                $('idSpanIscrizioniAperte').show();
                $('idSpanIscrizioniChiuse').hide();
                $('idSubNotRepDateBox').hide();
                $('idShowRegistrationOpenDays').hide();
                if( $('idSelectSubNotRep').value == 3 )
                {
                    $('idSubNotRepDateBox').show();
                    $('idSpanIscrizioniAperte').hide();
                    $('idShowRegistrationOpenDays').show();
                }
                if($('idSelectSubNotRep').value == 1 )
                {
                    $('idSubNotRepDateBox').hide();
                    $('idSpanIscrizioniAperte').hide();
                    $('idSpanIscrizioniChiuse').show();
                }
            }

            function hideShowSubRepDate()
            {
                if( $('idRadioSubReptrue').checked == true )
                    $('idSubRepDateBox').show();
                else
                    $('idSubRepDateBox').hide();
            }

            function showClearAndSharedCell()
            {
                if ( $('idTxtMobile').value.length > 0 )
                {
                    $('idClearMobileTxt').show();
                    $('idSharedCell').show();
                }
                else
                {
                    $('idClearMobileTxt').hide();
                    $('idSharedCell').hide();
                }
            }

            function showCalendarSubNotRepDate(idInputTextBox, showHourMinute,idVisibleTextField,formatOutDate)
            {
                var enabledFromValue = new Date();
                enabledFromValue.setDate(enabledFromValue.getDate()-1);

                var dateto=($('idMatchStartDate').value);

                var date = (dateto.substr(0,2));
                var month = (dateto.substr(3,2));
                var year = (dateto.substr(6,4));
                var hour = (dateto.substr(11,2));
                var min =  (dateto.substr(14,2));

                var enabledToValue = new Date();
                enabledToValue.setDate(date);
                enabledToValue.setMonth(month - 1);
                enabledToValue.setFullYear(year);
                enabledToValue.setHours(hour,min,0,0)

                var options = {
                    showHourMinute: showHourMinute,
                    enabledFrom: enabledFromValue,
                    enabledTo: enabledToValue
                };

                showCalendarOptions(idInputTextBox, options,idVisibleTextField,formatOutDate)
            }

            function showAcceptTerminationLimit(hourLimit)
            {
                new Ajax.Updater('idShowLimit', '<s:url action="showAcceptTerminationLimit" namespace="/ajax"/>',
                {
                    parameters: {
                        dateTo: $('idMatchStartDate').value + ' ' + $('idMatchStartHour').value + ':' + $('idMatchStartMinute').value,
                        hourLimit: hourLimit,
                        dummy: Math.random()
                    }
                });
            }

            function showRegistrationOpenDays()
            {
                if ($('idMatchStartDate').value=='')
                {
                    return;
                }
                new Ajax.Updater('idShowRegistrationOpenDays', '<s:url action="showRegistrationOpenDays" namespace="/ajax"/>',
                {
                    parameters: {
                        registrationOpenDate: $('idSubNotRepDate').value,
                        dummy: Math.random()
                    }
                });
            }

            function showCalendarRepDate(idInputTextBox, showHourMinute,idVisibleTextField,formatOutDate)
            {
                var enabledToValue = new Date();
                enabledToValue.setYear(enabledToValue.getFullYear() + 1);

                var datefrom=($('idMatchStartDate').value + $('idMatchStartHour').value + $('idMatchStartMinute').value);
                var date =   (datefrom.substr(0,2));
                var month =  (datefrom.substr(3,2));
                var year =   (datefrom.substr(6,4));
                var hour =   (datefrom.substr(11,2));
                var min =    (datefrom.substr(14,2));
                var enabledFromValue = new Date();
                enabledFromValue.setDate(date);
                enabledFromValue.setMonth(month - 1);
                enabledFromValue.setFullYear(year);
                enabledFromValue.setHours(hour,min,0,0)

                enabledFromValue.setDate(enabledFromValue.getDate()-1);

                var initDate = $('idMatchStartDate');

                var options = {
                    showHourMinute: showHourMinute,
                    enabledFrom: enabledFromValue,
                    enabledTo: enabledToValue,
                    initialDateFrom: initDate,
                    hideOkbutton: true
                };

                showCalendarOptions(idInputTextBox, options,idVisibleTextField,formatOutDate)
            }

        </script>

        <title><s:property value="%{getText('page.title.default')}" /></title>
  <!--### start Google Analitics inclusion ###-->
  <jsp:include page="../../jspinc/statisticsScript.jsp" flush="true" />
  <!--### end Google Analitics inclusion ###-->
    </head>
    <body onload="initializeForm()">

        <div class="wrapperOrganize">

            <!--### start header ###-->
            <jsp:include page="../../jspinc/header.jsp" flush="true" />
            <!--### end header ###-->

            <!--### start leftcolumn ###-->
            <jsp:include page="../../jspinc/leftColumn.jsp" flush="true">
                <jsp:param name="tab" value="organize"/>
            </jsp:include>
            <!--### end leftcolumn ###-->

            <!--### start centralcolumn ###-->
            <div class="centralCol">
                <div class="topPageWrap">
                    <jsp:include page="../../jspinc/headerTopBar.jsp" flush="true" />
                </div>  



                <!--### start mainContent ###-->
                <div class="mainContentIndent">


                    <strong><s:actionmessage /></strong>

                    <h1><u:translation key="label.organizzaPartita"/></h1>

                    <p class="titleList"><u:translation key="label.organizePrenota"/> [<a href="javascript: void(0);" onclick="openPopupHelp('M003')" >?</a>]</p>


                    <!--### start organize menu ###-->
                    <jsp:include page="../../jspinc/organizeMenu.jsp" flush="true">
                        <jsp:param name="tab" value="organize"/>
                    </jsp:include>
                    <!--### end organize menu ###-->

                    <u:isOrganizer>

                        <s:form action="organizeMatchCreate!insert" method="post"  >
                            <s:hidden name="idMatch" />
                            <table class="organizeMatch">
                                <tr>
                                    <td style="width:18%;" class="subLine">
                                        <u:translation key="label.Data"/>
                                    </td>
                                    <!-- calendario start match -->
                                    <td style="width:38%; vertical-align: bottom; border-bottom:solid 1px #FFFFFF;">
                                        <span id="idShowStartDate">${labelMatchStart}</span>
                                        <s:fielderror name="matchStartDate" fieldName="matchStartDate" />
                                        <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendario" style="cursor:pointer;"
                                             onclick="showCalendar('idMatchStartDate', false,'idShowStartDate','format.date_2');$('idInfoNonModificabili').show();" />
                                        <s:textfield name="matchStartDate" id="idMatchStartDate" cssStyle="visibility:hidden; width:0; height:0;"/>
                                    </td>
                                    <td class="infoReg" style="display: none;" id="idInfoNonModificabili">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:18%;" class="subLine">
                                        <u:translation key="label.Orario"/>
                                    </td>
                                    <td style="width:38%; vertical-align: bottom; border-bottom:solid 1px #FFFFFF;">
                                        <s:select name="matchStartHour" id="idMatchStartHour" list="hoursList" />
                                        :
                                        <s:select name="matchStartMinute" id="idMatchStartMinute" list="minutesList" />
                                    </td>
                                    <td class="infoReg" style="display: none;" >
                                    </td>
                                </tr>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label._Campo" />
                                    </td>
                                    <td style="border-bottom:solid 1px #FFFFFF;">
                                        <span id="jsSportCenterSelectedContainer"style="display: <s:if test="sportCenterInfo.valid">table-cell</s:if><s:else>none</s:else>;">
                                            <s:hidden name="idSportCenter" id="jsSortCenterSelectedId"  />
                                            <span id="jsSportCenterSelectedName"><s:property value="sportCenterInfo.name" /></span><br />

                                            <span id="jsSportCenterSelectedAddress"><s:property value="sportCenterInfo.address" /></span><br />

                                            <span id="jsSportCenterSelectedCity"><s:property value="sportCenterInfo.cityProvinceName" /></span>
                                        </span>
                                        <s:fielderror name="idSportcenter" fieldName="idSportcenter" />
                                        <a href="javascript: void(0);" class="light" onclick="openPopupSportCenterChoose('<s:url action="sportCenter" method="choose" />');$('idInfoPrenota').show();" >
                                            <u:translation key="label._seleziona" />
                                        </a>
                                    </td>
                                    <td class="infoReg" style="display: none;" id="idInfoPrenota">
                                        <u:translation key="label.oraganizeMatchInfoPrenotazione"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.Calcio"/>
                                    </td>
                                    <td style="border-bottom:solid 1px #FFFFFF;">
                                        <s:fielderror name="idMatchType" fieldName="idMatchType" />
                                        <s:fielderror name="matchParticipantsNumber" fieldName="matchParticipantsNumber" />
                                        <s:select name="idMatchType"
                                                  headerKey="0" headerValue="  "
                                                  list="matchTypeInfoList"
                                                  listKey="id"
                                                  listValue="name"
                                                  id="idSelectMatchType"
                                                  onchange="hideShowMatchParticipantsNumber($(this).value)" />
                                        <div style="display: none;" id="idParticipantsBox">
                                            <u:translation key="label.Partecipanti"/>:
                                            <s:select
                                                emptyOption="false"
                                                name="matchParticipantsNumber"
                                                list="matchParticipantsNumberList"
                                                id="idSelectMatchParticipantsNumber"
                                                onchange="setMaxSquadOutList();setSubstitutions();"
                                                style="width:40px;" />
                                        </div>
                                    </td>
                                    <td class="infoReg">
                                        <strong>
                                            <span id="idSostituzioni" ></span>
                                            &nbsp;
                                        </strong>

                                    </td>
                                </tr>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.tuoCell"/> <a  href="javascript: void(0);" onclick="openPopupHelp('M004')" > [?]</a>
                                    </td>
                                    <td>
                                        <s:fielderror name="mobile" fieldName="mobile" />
                                        <s:textfield name="mobile" id="idTxtMobile" maxLength="20" style="width:170px;"  onkeyup="showClearAndSharedCell();"/>
                                        <a href="javascript: void(0);" id="idClearMobileTxt" onclick="$('idTxtMobile').value = '';$(this).hide();showClearAndSharedCell();" class="delete">X</a>
                                    </td>
                                    <td class="infoReg">
                                        <span id="idSharedCell" style="display: none;">
                                            <u:translation key="label.match.cellCondiviso"  />
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.note"/>
                                    </td>
                                    <td colspan="2">
                                        <s:fielderror name="notes" fieldName="notes" />
                                        <s:textarea name="notes" rows="3" cols="40" cssStyle="width:170px;" onkeyup="countStop(this,500);"/>
                                    </td>
                                </tr>
                                <tr><td>&nbsp;</td></tr>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.Partita"/> <a  href="javascript: void(0);" onclick="openPopupHelp('M002')" > [?]</a>
                                    </td>
                                    <td>
                                        <s:fielderror name="registrationOpen" fieldName="registrationOpen" />
                                        <fieldset>
                                            <s:radio name="registrationOpen" list="#{'true':getText('label.Pubblica'),'false':getText('label.Privata')}"
                                                     id="idRadioRegOpen" onclick="hideShowRegistrationOpen()" />
                                        </fieldset>
                                    </td>
                                    <td class="infoReg">
                                        <span style="display:none;">
                                            <u:translation key="label.visibileSoloAmici"/>
                                        </span>

                                        <span id="idSpanMatchType" style="display: none;">
                                            <u:translation key="label.match.visibleAtutti" />
                                        </span>
                                        <span id="idSpanMatchTypePrivate" style="display: none;">
                                            <u:translation key="label.match.visibleSoloAmici" />
                                        </span>
                                    </td>
                                </tr>
                                <tr style="display: none;" id="idRegistrationOpenBox">
                                    <td>&nbsp;</td>
                                    <td>
                                        <small>
                                            <u:translation key="label.organizeMatchAmiciInfo" />
                                        </small>
                                        <p>
                                            <s:select
                                                name="directlyRegistration"
                                                list="#{'true': getText('label.permettiIscrizioniDirette'),'false':getText('label.valutaIscrizioni')}"
                                                id="idSelectDirectlyRegistration"
                                                onchange="hideShowMaxSquadOut()" />
                                        </p>
                                        <br /><br />
                                    </td>
                                    <td class="infoReg">
                                        <span id="idSpanDirectlyRegistration" style="display: none;">
                                            <u:translation key="label.match.registrazioneDiretta" />
                                        </span>
                                        <span id="idSpanNotDirectlyRegistration" style="display: none;">
                                            <u:translation key="label.match.registrazioneConAutorizzazione" />
                                        </span>
                                        <br /><br /><br />
                                    </td>
                                </tr>
                                <!--sostituire la riga commentata con quella che la segue e togliere <tbody> per abilitare fuori Rosa max -->
                                <%--tr style="display: none;" id="idMaxSquadOutBox" --%>
                                <tbody style="display: none;">
                                    <tr style="display: none;visibility:collapse;" id="idMaxSquadOutBox">
                                        <td>&nbsp;</td>
                                        <td>
                                            <s:fielderror name="maxSquadOut" fieldName="maxSquadOut" />
                                            <u:translation key="label.organizeFinoA" />
                                            <s:select
                                                name="maxSquadOut" emptyOption="false"
                                                list="maxSquadOutList" id="idSelectMaxSquadOut"
                                                onchange="$('idsquadOutNumber').innerHTML=$('idSelectMaxSquadOut').value;"/>
                                            <u:translation key="label.posti" />
                                            <br />  <br />
                                        </td>
                                        <td class="infoReg">
                                            <span>
                                                <strong><span id="idsquadOutNumber"> xx</span></strong> <u:translation key="label.match.fuoriRosaMax" />
                                            </span>
                                        </td>
                                    </tr>
                                </tbody>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.disdette" /> <a  href="javascript: void(0);" onclick="openPopupHelp('M005')" > [?]</a>
                                    </td>
                                    <td>
                                        <s:fielderror name="accepTermination" fieldName="accepTermination" />
                                        <fieldset>
                                            <s:radio name="accepTermination" list="#{'false':getText('label.No'),'true':getText('label.Si')}" id="idRadioAccepTermination"
                                                     onclick="hideShowAccepTermination()" />
                                        </fieldset>
                                    </td>
                                    <td class="infoReg">
                                        <span id="idSpanNocheckout" style="display: none;">
                                            <u:translation key="label.match.noDisdette" />
                                        </span>
                                    </td>
                                </tr>
                                <tr style="display: none;" id="idAccepTerminationBox">
                                    <td style="border:none;">&nbsp;</td>
                                    <td>
                                        <u:translation key="label.entro" />&nbsp;
                                        <s:select
                                            id="idAccepTerminationMaxHours"
                                            name="accepTerminationMaxHours" list="#{'6':'6','12':'12','24':'24','36':'36','48':'48'}"
                                            onchange="showAcceptTerminationLimit(this.value);"
                                            />&nbsp;
                                        <u:translation key="label.match.oreDalMatch" />
                                        <br />  <br />
                                    </td>
                                    <td class="infoReg">
                                        <span id="idShowLimit">
                                        </span>
                                        <br /> <br />
                                    </td>
                                </tr>
                                <!--sostituire la riga commentata con quella che la segue e togliere <tbody> per abilitare il ripetitivo -->
                                <%--tr  --%>
                                <tbody style="display: none;">
                                    <tr style="visibility:collapse" >
                                        <td class="subLine" <%--style="visibility:hidden"--%> >
                                            <u:translation key="label.Ripetitivo"/> [<a href="#">?</a>]
                                        </td>
                                        <td>
                                            <s:select
                                                name="repetitive" list="#{'false':getText('label.partitaSingola'),'true':getText('label.partitaSettimanale')}" id="idSelectRepetitive"
                                                onchange="hideShowRepetitive()" />
                                        </td>
                                        <td class="infoReg"></td>
                                    </tr>
                                </tbody>
                                <tbody style="display: none;">
                                    <!--sostituire la riga commentata con quella che la segue e togliere <tbody> per abilitare il ripetitivo -->
                                    <%--tr style="display: none;" id="idRepetitiveBox"--%>
                                    <tr style="display: none;visibility:hidden" id="idRepetitiveBox">
                                        <td>&nbsp;</td>
                                        <!-- calendario ripetitivo -->
                                        <td colspan="2">
                                            <s:fielderror name="endRepetitiveDate" fieldName="endRepetitiveDate" />
                                            <u:translation key="label.organizeFinoA" /> <span id="idShowEndRepetitiveDate">${labelRepDate}</span>
                                            <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendario" style="cursor:pointer;" onclick="showCalendarRepDate('idEndRepetitiveDate', false,'idShowEndRepetitiveDate','format.date_2')" />
                                            <s:textfield name="endRepetitiveDate" id="idEndRepetitiveDate"  cssStyle="visibility:hidden"/>
                                            <br />  <br />
                                        </td>
                                    </tr>
                                </tbody>
                                <tr>
                                    <td style="vertical-align:top;">
                                        <p class="subLine"><u:translation key="label.Iscrizioni" /> <a  href="javascript: void(0);" onclick="openPopupHelp('M006')" > [?]</a></p>
                                    </td>
                                    <td>
                                        <span id="idSubscriptionsNotRepetitiveBox">
                                            <s:select name="subNotRep" id="idSelectSubNotRep" list="#{'3':getText('label.organizeApriInData'),
                                                                                                      '2':getText('label.aperte'),
                                                                                                      '1':getText('label.chiuse')}"
                                                      onchange="hideShowSubNotRepDate()" />
                                        </span>
                                        <span id="idSubNotRepDateBox" style="display:none;">
                                            <!-- calendario apri in data -->
                                            <s:fielderror name="subNotRepDate" fieldName="subNotRepDate" />
                                            <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendario" style="cursor:pointer; vertical-align:bottom;" onclick="showCalendarSubNotRepDate('idSubNotRepDate', true,'idShowNotRepDate','format.date_1')" />
                                            <span  style="display:block; padding:10px 0 0 0;" id="idShowNotRepDate">${labelRegisterDate}</span>
                                            <s:textfield name="subNotRepDate" id="idSubNotRepDate" cssStyle="visibility:hidden"/>
                                        </span>

                                        <div id="idSubscriptionsRepetitiveBox" style="display:none;">
                                            <s:radio name="subRep" id="idRadioSubRep" list="#{'true':getText('label.aperte'),'false':getText('label.chiuse')}"
                                                     onclick="hideShowSubRepDate()" />

                                        </div>

                                    </td>

                                    <td class="infoReg" style="vertical-align: top;">
                                        <span id="idSpanIscrizioniAperte" style="display:none;">
                                            <u:translation key="label.match.iscrizioniAperte"  />
                                        </span>
                                        <span id="idSpanIscrizioniChiuse" style="display:none;">
                                            <u:translation key="label.match.iscrizioniChiuse" />
                                        </span>
                                        <span  class="infoReg" id="idShowRegistrationOpenDays">

                                        </span>
                                    </td>
                                </tr>
                                <%--tr id="idSubNotRepDateBox" style="display:none;">
                                  <td>&nbsp;</td>
                  <!-- calendario apri in data -->
                                  <td>
                                    <s:fielderror name="subNotRepDate" fieldName="subNotRepDate" />
                                    <span id="idShowNotRepDate">${labelRegisterDate}</span>
                                    <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendario" style="cursor:pointer;" onclick="showCalendarSubNotRepDate('idSubNotRepDate', true,'idShowNotRepDate','format.date_1')" />
                                    <s:textfield name="subNotRepDate" id="idSubNotRepDate" cssStyle="visibility:hidden"/>
                                  </td>
                                  <td class="infoReg">&nbsp;</td>
                                </tr--%>

                                <tr id="idSubRepDateBox" style="display:none;">
                                    <td>&nbsp;</td>
                                    <td>
                                        <p>
                                            <s:select name="subRepDateDay" list="numberOfDaysList" /> <u:translation key="label.organizeGiorniPrima"/>
                                        </p>
                                        <p>
                                            <u:translation key="label.alleOre"/>:
                                            <s:select name="subRepDateHours" list="hoursList" /> : <s:select name="subRepDateMinutes" list="minutesList" />
                                        </p>
                                        <br />
                                    </td>
                                    <td class="infoReg">&nbsp;</td>
                                </tr>

                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.tuoRuolo"/>:
                                    </td>
                                    <td>
                                        <s:select name="idPlayerRole"
                                                  list="playerRoleInfoList"
                                                  listKey="id" listValue="name"
                                                  headerKey="0"
                                                  headerValue="%{getText('label.organizzoSenzaGiocare')}"
                                                  />
                                    </td>
                                    <td class="infoReg">&nbsp;</td>
                                </tr>
                            </table>
                            <br />
                            <p>
                              <s:submit cssClass="btn" value="%{getText('label.organizePubblica')}" onclick="JQ('span#idWait').show();" />
                              <span id="idWait" style="display:inline; display:none;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" style="vertical-align: bottom;" /></span>
                            </p>

                        </s:form>
                        <br class="clear" />
                    </u:isOrganizer>

                </div>
                <!--### end mainContent ###-->

                <u:isNotOrganizer>
                    <div class="mainContent">
                        <p align="center">
                            <u:translation key="label.organizeMatchNotEnabled"/>
                        </p>
                    </div>
                </u:isNotOrganizer>

            </div>
            <!--### end centralcolumn ###-->

            <!--### start rightcolumn ###-->
            <jsp:include page="../../jspinc/rightcolumn.jsp" flush="true" />
            <!--### end rightcolumn ###-->

            <!--### start footer ###-->
            <jsp:include page="../../jspinc/footer.jsp" flush="true" />
            <!--### end footer ###-->

        </div>

    </body>
</html>
