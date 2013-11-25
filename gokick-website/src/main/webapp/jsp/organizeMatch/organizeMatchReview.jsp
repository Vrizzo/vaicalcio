<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
<!--        <base href="<u:GKbase/>"/>-->
        <jsp:include page="../../jspinc/commonHead.jsp" flush="true" >
            <jsp:param name="calendar" value="true" />
        </jsp:include>

        <script type="text/javascript">
    
            function setSquadOutNumber() 
            {
                $('idsquadOutNumber').innerHTML=$('idSelectMaxSquadOut').value;
            }

            function initializeForm()
            {
        
                if( $('idTxtMobile').value.length > 0 )
                    $('idClearMobileTxt').show();
                else
                    $('idClearMobileTxt').hide();

                if( $('idSelectMatchType').value > 0 )
                    $('idParticipantsBox').show();
                else
                    $('idParticipantsBox').hide();
        
                if( $('idRadioRegOpentrue').checked == true )
                {
                    $('idRegistrationOpenBox').show();
                    $('idSpanMatchType').show();
                }
                else
                {
                    $('idMaxSquadOutBox').hide();
                    $('idRegistrationOpenBox').hide();
                    $('idSelectDirectlyRegistration').checked = true;
                }

                if ($('idRadioRegOpenfalse').checked == true)
                {
                    $('idSpanMatchTypePrivate').show();
                    $('idMaxSquadOutBox').hide();
                }
           
                if( $('idSelectDirectlyRegistration').value == 'true' )
                    $('idMaxSquadOutBox').show();
                else
                    $('idMaxSquadOutBox').hide();

                if (${directlyRegistration})
                {
                    $('idSelectDirectlyRegistration').options[1].selected== true;

                }
            
                if( $('idSelectSubNotRep').value == 3 )
                    $('idSubNotRepDateBox').show();
                else
                    $('idSubNotRepDateBox').hide();
        
                if( $('idRadioAccepTerminationtrue').checked == true )
                {
                    $('idAccepTerminationBox').style.display = '';
                }
                else
                {
                    $('idAccepTerminationBox').hide();
                    $('idSpanNocheckout').show();
                }
                hideShowSubNotRepDate();
                hideShowRegistrationOpen();
                showAcceptTerminationLimit($('idAccepTerminationMaxHours').value);
                setMaxSquadOutList();
                setSubstitutions();
                setSquadOutNumber();
                showRegistrationOpenDays();
            }

            function hideShowMatchParticipantsNumber(idMatchType)
            {
                var minPlayers = ${currentPlayerCount};
                if( $('idSelectMatchType').value > 0 )
                {
                    $('idParticipantsBox').style.display = 'inline';
                    $('idSelectMatchParticipantsNumber').options.length = 0;
                    $('idSelectMaxSquadOut').options.length = 1;

                    var value = idMatchType * 2;
                    var endValue = (idMatchType * 4) - value;

                    if (endValue<minPlayers)
                    {
                        for (var j=0; j <= endValue; j++)
                        {
                            if (value>=minPlayers)
                            {
                                $('idSelectMatchParticipantsNumber').options[j] = new Option(value, value);
                            }
                            value++;
                        }
              
                        if ($('idSelectMatchParticipantsNumber').options.length == 0)
                        {
                            $('idSelectMatchParticipantsNumber').options[1] = new Option(minPlayers, minPlayers);
                        }

                        $('idSelectMatchParticipantsNumber').options[1].selected= true;
                        setSubstitutions();
                        setMaxSquadOutList();
                    }
                    else
                    {
                        $('idSelectMatchParticipantsNumber').options.length = 1;
                        for (var j=0; j <= endValue; j++)
                        {
                            $('idSelectMatchParticipantsNumber').options[j] = new Option(value, value);
                            value++;
                        }
                        $('idSelectMatchParticipantsNumber').options[0].selected= true;
                        setSubstitutions();
                        setMaxSquadOutList();}
                }
                else
                {
                    $('idParticipantsBox').hide();
                    $('idSelectMatchParticipantsNumber').options.length = 1;
                    $('idSelectMaxSquadOut').options.length = 1;
                }
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

            function hideShowClearMobileBox(mobileText)
            {
                if( mobileText.length > 0 )
                    $('idClearMobileTxt').style.visibility = 'visible';
                else
                    $('idClearMobileTxt').style.visibility = 'hidden';
            }

            <%--function hideShowClearEmailBox(emailText)
            {
              if( emailText.length > 0 )
                $('idClearEmailTxt').style.visibility = 'visible';
              else
                $('idClearEmailTxt').style.visibility = 'hidden';
            }--%>

                function hideShowRegistrationOpen()
                {
                    if( $('idRadioRegOpentrue').checked == true )
                    { 
                        $('idSpanMatchType').show();
                        $('idRegistrationOpenBox').show();
                        $('idSpanMatchTypePrivate').hide();
                        if( $('idSelectDirectlyRegistration').value == 'true' )
                        {
                            $('idSelectDirectlyRegistration').options[0].selected= true;
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
                        $('idMaxSquadOutBox').hide();
                        $('idSpanMatchType').hide();
                        $('idRegistrationOpenBox').hide();
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

                function setSubstitutions ()
                {
                    var sostituzioni = ($('idSelectMatchParticipantsNumber').value) - ($('idSelectMatchType').value * 2);
                    if (sostituzioni<0) sostituzioni=0;
                    $('idSostituzioni').innerHTML=sostituzioni;
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

                    var dateto=($('idMatchStart').value);

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
                            dateTo: $('idMatchStart').value,
                            hourLimit: hourLimit,
                            dummy: Math.random()
                        }
                    });
                }

                function showRegistrationOpenDays()
                {
                    if ($('idMatchStart').value=='')
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
            <jsp:include page="../../jspinc/leftColumn.jsp" flush="true" />
            <!--### end leftcolumn ###-->

            <!--### start centralcolumn ###-->
            <div class="centralCol">

            <div class="topPageWrap">
              <jsp:include page="../../jspinc/headerTopBar.jsp" flush="true" />
            </div>

            <!--### start mainContent ###-->
                <div class="mainContentIndent">

                    <strong><s:actionmessage /></strong>

                    <s:if test="canOrganizeMatch">

                        <h1><u:translation key="label.ModificaDettagli" /></h1>

                        <p class="titleList"><u:translation key="label.organizePrenota"/> <a  href="javascript: void(0);" onclick="openPopupHelp('M003')" > [?]</a></p>

                        <s:form action="organizeMatchReview!update" method="post"  >

                            <s:hidden name="idMatch" value="%{idMatch}" />
                            <s:hidden name="currentPlayerCount" value="%{currentPlayerCount}" />
                            <s:date name="matchStart" var="matchStartId" format="dd/MM/yyyy HH:mm" />
                            <s:hidden name="matchStart" value="%{matchStartId}" id="idMatchStart" />
                            <s:date name="startRegistrationDate" var="startRegistrationDateId" format="dd/MM/yyyy HH:mm"/>

                            <s:fielderror name="unexpectedError"  />
                            <table class="organizeMatch" >
                                <tr>
                                    <td style="width:18%;" class="subLine">
                                        <u:translation key="label.Data" />
                                    </td>
                                    <td style="width:38%;">
                                      <span id="idShowStartDate">${labelMatchStart}</span>
                                      <s:fielderror name="matchStartDate" fieldName="matchStartDate" />
                                        <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendario" style="cursor:pointer; vertical-align:bottom;"
                                             onclick="showCalendar('idMatchStartDate', false,'idShowStartDate','format.date_2');"/>
                                      <s:textfield name="matchStartDate" id="idMatchStartDate" cssStyle="visibility:hidden; width:0; height:0;"/>
                                    </td>
                                    <td class="infoReg" >
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
                                    <td>
                                        <table>
                                            <tr>
                                                <td id="jsSportCenterSelectedContainer"style="display: <s:if test="sportCenterInfo.valid">table-cell</s:if><s:else>none</s:else>;" >

                                                    <s:hidden name="idSportCenter" id="jsSortCenterSelectedId"  />
                                                    <span id="jsSportCenterSelectedName"><s:property value="sportCenterInfo.name" /></span>
                                                    <br/>
                                                    <span id="jsSportCenterSelectedAddress"><s:property value="sportCenterInfo.address" /></span>
                                                    <br/>
                                                    <span id="jsSportCenterSelectedCity"><s:property value="sportCenterInfo.cityProvinceName" /></span>

                                                </td>
                                                <td>
                                                    <s:fielderror name="idSportcenter" fieldName="idSportcenter" />
                                                    <a href="javascript: void(0);" class="light" onclick="openPopupSportCenterChoose('<s:url action="sportCenter" method="choose" />')"><u:translation key="label._seleziona" /> </a>

                                                </td>

                                            </tr>
                                        </table>
                                    </td>
                                    <td class="infoReg">
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
                                                  headerKey="0"
                                                  headerValue="  "
                                                  list="matchTypeInfoList"
                                                  listKey="id"
                                                  listValue="name"
                                                  id="idSelectMatchType"
                                                  onchange="hideShowMatchParticipantsNumber($(this).value)" />
                                        <span style="display: none;" id="idParticipantsBox">
                                            <u:translation key="label.Partecipanti"/>:
                                            <s:select
                                                emptyOption="false"
                                                name="matchParticipantsNumber"
                                                list="matchParticipantsNumberList"
                                                id="idSelectMatchParticipantsNumber"
                                                onchange="setMaxSquadOutList();setSubstitutions();setSquadOutNumber();"
                                                style="width:50px;" />
                                        </span>

                                    </td>
                                    <td class="infoReg">
                                        <strong>
                                            <span id="idSostituzioni" >0</span>
                                            &nbsp;<s:property value="%{getText('label.substitutions')}" />
                                        </strong>

                                    </td>
                                </tr>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.tuoCell"/> <a  href="javascript: void(0);" onclick="openPopupHelp('M004')" > [?]</a>
                                    </td>
                                    <td>
                                        <s:fielderror name="mobile" fieldName="mobile" />
                                        <s:textfield name="mobile" maxLength="20" id="idTxtMobile" cssStyle="width:170px;"  onkeyup="showClearAndSharedCell();"/>
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
                                    <td>
                                        <s:fielderror name="notes" fieldName="notes" />
                                        <s:textarea name="notes" rows="4" cols="20"  cssStyle="width:170px;" onkeyup="countStop(this,500);"/>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr><td>&nbsp;</td></tr>
                                <tr>
                                    <td class="subLine">
                                        <u:translation key="label.Partita"/> <a  href="javascript: void(0);" onclick="openPopupHelp('M002')" > [?]</a>
                                    </td>
                                    <td>
                                        <s:fielderror name="registrationOpen" fieldName="registrationOpen" />
                                        <s:radio name="registrationOpen" list="#{'true':getText('label.Pubblica'),'false':getText('label.Privata')}"
                                                 id="idRadioRegOpen" onclick="hideShowRegistrationOpen()" />
                                    </td>
                                    <td class="infoReg">
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

                                <!--sostituire la riga commentata con quella che la segue e togliere <tbody> per abilitare fuori rosa -->
                                <%--tr style="display: none;" id="idMaxSquadOutBox"--%>
                                <tbody style="display: none;">
                                    <tr style="display: none;visibility:collapse" id="idMaxSquadOutBox">
                                        <td>&nbsp;</td>
                                        <td>
                                            <s:fielderror name="maxSquadOut" fieldName="maxSquadOut" />
                                            <u:translation key="label.organizeFinoA" />
                                            <s:select
                                                name="maxSquadOut" emptyOption="false"
                                                list="maxSquadOutList" id="idSelectMaxSquadOut"
                                                onchange="$('idsquadOutNumber').innerHTML=$('idSelectMaxSquadOut').value;"/>
                                            <u:translation key="label.posti" />
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
                                            <s:radio name="accepTermination" list="#{'false':getText('label.No'),'true':getText('label.Si')}"
                                                     id="idRadioAccepTermination"
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
                                        <br />  <br />
                                    </td>
                                </tr>

                                <tr>
                                    <td style="vertical-align:top;">
                                        <p class="subLine"><u:translation key="label.Iscrizioni"/> <a  href="javascript: void(0);" onclick="openPopupHelp('M006')" > [?]</a></p>
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
                                            <span style="display:block; padding:10px 0 0 0;" id="idShowNotRepDate">${labelRegistrationDate}</span>
                                            <s:textfield name="subNotRepDate" value="%{startRegistrationDateId}" id="idSubNotRepDate" cssStyle="visibility:hidden" />
                                        </span>



                                    </td>
                                    <td class="infoReg"  style="vertical-align: top;">
                                        <span id="idSpanIscrizioniAperte">
                                            <u:translation key="label.match.iscrizioniAperte" />
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
                                        <span id="idShowNotRepDate">${labelRegistrationDate}</span>
                                        <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendario" style="cursor:pointer;" onclick="showCalendarSubNotRepDate('idSubNotRepDate', true,'idShowNotRepDate','format.date_1')" />
                                        <s:textfield name="subNotRepDate" value="%{startRegistrationDateId}" id="idSubNotRepDate" cssStyle="visibility:hidden" />
                                </td>
                                  <td class="infoReg">&nbsp;</td>
                                </tr--%>

                            </table>

                            <br />
                            <p class="left">
                                <s:submit cssClass="btn" value="%{getText('label.SalvaModifiche')}" onclick="JQ('span#idWait').show();" />
                              <span id="idWait" style="display:inline; display:none;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" style="vertical-align: bottom;" /></span>
                            </p>
                            <s:if test="!currentMatch.recorded">
                                <p class="right" style="margin:0 40px 0 0;">
                                    <s:url action="organizeMatchCancel" var="organizeMatchCancelUrl">
                                        <s:param name="idMatch" value="%{idMatch}" />
                                    </s:url>
                                    <a href="${organizeMatchCancelUrl}" onclick="return confirm('<u:translation key="message.AnnullaLaPartita" escapeJavaScript="true" />');" class="btn action2"><u:translation key="label.AnnullaLaPartita" /></a> <a  href="javascript: void(0);" onclick="openPopupHelp('M007')" > [?]</a>
                                </p>
                            </s:if>

                        </s:form>
                        <br class="clear" />
                    </s:if>
                </div>
                <!--### end mainContent ###-->
                <s:else>
                    <div class="mainContent">
                        <p align="center">
                            <u:translation key="label.organizeMatchNotEnabled"/>
                        </p>
                    </div>
                </s:else>


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
