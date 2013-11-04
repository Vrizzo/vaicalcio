<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
    <title>Single Sport Centre</title>
    <link type="text/css" rel="stylesheet" href="<s:url value="/css/calendar.css" encode="false"/>" media="screen"></link>
    <script type="text/javascript" src="<s:url value="/js/calendar/dhtmlSuite-common.js" encode="false" />"></script>
    <script type="text/javascript" src="<s:url value="/js/calendar/dhtmlSuite-dragDropSimple.js" encode="false"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/calendar/dhtmlSuite-calendar.js" encode="false"/>"></script>
    <script type="text/javascript">

        //remove week column

        var calendarObj;
        /*var chkImposta=0;*/
        var visibleDate;
        var formatOut;
        /*var hideOkBut;*/

        function showCalendar(idInputTextBox, showHourMinute, idVisibleDate, formatOutDate) {
            /*if (chkImposta==1)
             {
             return;
             }*/

            /*var enabledFromValue = new Date();
             enabledFromValue.setDate(enabledFromValue.getDate()-1);
             enabledFromValue.setHours(enabledFromValue.getHours() +1);

             var enabledToValue = new Date();
             enabledToValue.setYear(enabledToValue.getFullYear() + 1);*/
            if (calendarObj != null && calendarObj.isVisible())
                return;

            var options = {
                showHourMinute: showHourMinute/*,
                 enabledFrom: enabledFromValue,
                 enabledTo: enabledToValue*/
            };
            showCalendarOptions(idInputTextBox, options, idVisibleDate, formatOutDate)
        }


        function showCalendarOptions(idInputTextBox, options, idVisibleDate, formatOutDate) {
            visibleDate = $(idVisibleDate);
            formatOut = formatOutDate;
            /*if (chkImposta==1)
             {
             return;
             }*/
            var date = new Date();
            var hour = (date.getHours()) + 1;
            var calendarModelObj = new DHTMLSuite.calendarModel({initialHour: (hour)});
            /*calendarModelObj.addInvalidDateRange(false,{year: options.enabledFrom.getFullYear(),month:options.enabledFrom.getMonth()+1,day:options.enabledFrom.getDate()});
             if (options.enabledTo!=null)
             {
             calendarModelObj.addInvalidDateRange({year: options.enabledTo.getFullYear(),month:options.enabledTo.getMonth()+1,day:options.enabledTo.getDate()+1},false);
             }*/
            calendarModelObj.setLanguageCode('it');

            var calendarOption =
            {
                /*minuteDropDownInterval:5,
                 numberOfRowsInHourDropDown:10,
                 numberOfRowsInMinuteDropDown:10,*/
                callbackFunctionOnDayClick: 'setCalendarDate',
                /*callbackFunctionOnClose: 'closeCalendar',*/
                displayTodaysDateInNavigationBar: false,
                isDragable: false,
                displayTimeBar: options.showHourMinute,
                displayCloseButton: false
            };

            calendarObj = new DHTMLSuite.calendar(calendarOption);
            calendarObj.setCalendarModelReference(calendarModelObj);
            var inputTextBox = $(idInputTextBox);
            var initDate = options.initialDateFrom;

            var dateTimeFormat = options.showHourMinute ? 'dd/mm/yyyy hh:mm' : 'dd/mm/yyyy';
            calendarObj.setCalendarPositionByHTMLElement(inputTextBox, 0, inputTextBox.offsetHeight + 2);	// Position the calendar right below the form input
            calendarObj.setInitialDateFromInput((initDate != null ? initDate : inputTextBox), dateTimeFormat);	// Specify that the calendar should set it's initial date from the value of the input field.
            calendarObj.addHtmlElementReference('calendarTextBoxField', inputTextBox);	// Adding a reference to this element so that I can pick it up in the getDateFromCalendar below(myInput is a unique key)

            if (calendarObj.isVisible()) {
                calendarObj.hide();
            }
            else {
                calendarObj.resetViewDisplayedMonth();	// This line resets the view back to the inital display, i.e. it displays the inital month and not the month it displayed the last time it was open.
                calendarObj.display();
            }
            /*if (options.hideOkbutton!=true)
             {
             if (chkImposta!=1)
             {
             chkImposta=1;
             var calContainer = $$('.DHTMLSuite_calendar_timeBar').each(function(el)
             {
             Element.insert(el, { top: '<a  id="idOk" href="javascript: void(0);" onclick="closeCalendar();" ><div class="DHTMLSuite_customCloseButton btn action1" >OK</div></a>'});
             });
             }
             }
             else
             {
             hideOkBut=true;
             }*/
        }

        function closeCalendar() {
            var inputArray =
            {
                year: calendarObj.calendarModelReference.displayedYear,
                month: calendarObj.calendarModelReference.displayedMonth.toString().lpad("0", 2),
                day: calendarObj.calendarModelReference.displayedDay.toString().lpad("0", 2),
                hour: calendarObj.calendarModelReference.displayedHour.toString().lpad("0", 2),
                minute: calendarObj.calendarModelReference.displayedMinute.toString().lpad("0", 2)
            };
            var references = calendarObj.getHtmlElementReferences(); // Get back reference to form field.

            references.calendarTextBoxField.value = inputArray.day + '/' + inputArray.month + '/' + inputArray.year;

            /*if(calendarObj.displayTimeBar)
             {
             references.calendarTextBoxField.value += ' ' + inputArray.hour + ':' + inputArray.minute;
             }*/

            showDate(visibleDate, references.calendarTextBoxField.value, /*hideOkBut==true ?*/ 'dd/MM/yyyy' /*: 'dd/MM/yyyy hh:mm'*/);

            calendarObj.hide();

            /*chkImposta=0;*/

        }

        function close() {
            /*chkImposta=0;
             hideOkBut=false;*/
        }

        function showDate(idShowDate, date, formatIn) {
            new Ajax.Updater(idShowDate, '<s:url action="showDate" namespace="/ajax"/>',
                    {
                        parameters: {
                            data: date,
                            formatIn: formatIn,
                            formatOut: formatOut,
                            dummy: Math.random()
                        }
                    });
        }

        function setCalendarDate(inputArray) {

            $$('.DHTMLSuite_calendar_newSelection').each(function (el) {
                el.removeClassName('DHTMLSuite_calendar_newSelection');
            });

            $$('.DHTMLSuite_calendarContent td').each(function (el) {
                if (inputArray.day == el.innerHTML.toString().lpad("0", 2)) {
                    var allClassName = $w(el.className);
                    allClassName.each(function (className) {
                        if (className.endsWith('InThisMonth') || className.endsWith('_currentDate')) {
                            el.addClassName('DHTMLSuite_calendar_newSelection');
                            return;
                        }
                    });
                }
            });

            closeCalendar();
            /*if (hideOkBut)
             {

             hideOkBut=false;
             }*/
        }

    </script>
</head>

<body>

<c:set var="hideCountry" value="${UserContext.idCountryFilter>0}"/>

<div class="wrapper">

<!--### start header ###-->
<jsp:include page="../jspinc/header.jsp" flush="true"/>
<!--### end header ###-->

<div style="padding-left:20px;">

<h1>${guiSportCenter.name}</h1>

<p>reports TODO</p>

<br/><br/>

<s:form id="idsportCenterForm" action="AdminSingleSportCenter!update" method="post">
<s:hidden name="guiSportCenter.id" value="%{guiSportCenter.id}"/>
<s:hidden name="idSportCenter" value="%{guiSportCenter.id}"/>
<table>
<tr>
    <td>
        ID
    </td>
    <td>
        <s:property value="guiSportCenter.id"/>
    </td>
</tr>

<tr>
    <td>Status</td>
    <td>
        <s:select
                name="guiSportCenter.status"
                list="statusList"
                />
    </td>
</tr>

<tr>
    <td>
        Partner
    </td>
    <td>
        <s:checkbox name="guiSportCenter.conventioned"/>

        <s:if test="guiSportCenter.conventionFrom!=null">
            <s:set name="dateStart"><fmt:formatDate value="${guiSportCenter.conventionFrom}" pattern="dd/MM/yy"/></s:set>
        </s:if>
        <s:if test="guiSportCenter.conventionTo!=null">
            <s:set name="dateEnd"><fmt:formatDate value="${guiSportCenter.conventionTo}" pattern="dd/MM/yy"/></s:set>
        </s:if>


        from: <span id="idSpanConvStartDate">${dateStart}</span>
        <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendario" style="cursor:pointer;"
             onclick="showCalendar('idConvStartDate', false,'idSpanConvStartDate','format.date_9');"/>
        <s:textfield name="convStartDate" id="idConvStartDate" cssStyle="visibility:hidden; width:0; height:0;" value="%{dateStart}"/>


        to: <span id="idSpanConvEndDate">${dateEnd}</span>
        <img src="<s:url value="/images/calendar/icon_calendar.gif" encode="false" />" alt="Calendar" style="cursor:pointer;"
             onclick="showCalendar('idConvEndDate', false,'idSpanConvEndDate','format.date_9');"/>
        <s:textfield name="convEndDate" id="idConvEndDate" cssStyle="visibility:hidden; width:0; height:0;" value="%{dateEnd}"/>

    </td>
</tr>

<tr>
    <td width="115" height="24" valign="bottom">
        <p align="left" style="margin-right:5;">
            <font face="Verdana" color="#333333"><span style="font-size:8pt;">
                      Created by</span></font>
        </p>
    </td>
    <td width="809" height="24" valign="bottom">
        <p align="left" style="margin-right:5;">
                  <span style="font-size:8pt;"><font face="Verdana" color="#333333">
                          ${guiSportCenter.userAuthor.firstName} ${guiSportCenter.userAuthor.lastName}
                  </font></span>
            <font face="Verdana" color="#333333"><span style="font-size:8pt;">
                      <fmt:setLocale value="it"/>
                      <fmt:formatDate value="${guiSportCenter.created}" type="date" pattern=" EEE dd/MM/yyyy"/>
                      h <fmt:formatDate value="${guiSportCenter.created}" type="date" pattern="HH:mm:ss"/>
                    </span></font>
        </p>
    </td>
</tr>

<tr>
    <td>
        Sports Centre Name
    </td>
    <td>
        <s:textfield name="guiSportCenter.name"/>
    </td>
</tr>

<tr>
    <td>
        <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
            <font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Country</span></font>
        </p>
    </td>
    <td>
        <p align="left" style="margin-bottom:1;display: ${hideCountry?'none':''}">
            <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax"/>
            <s:select
                    name="guiSportCenter.country.id"
                    headerKey="0"
                    headerValue="All"
                    list="countyList"
                    listKey="id"
                    listValue="name"
                    id="idSelectCountry"
                    value="guiSportCenter.country.id"
                    onchange="getProvincesByCountry($(this).value, 0, 0, 0, '%{provincesByCountryURL}')"/>
        </p>
    </td>
</tr>

<tr>
    <td>
        <p align="left" style="margin-bottom:1;"><font face="Verdana" color="#666666"><span style="font-size:8pt;">
                      Province</span></font>
        </p>
    </td>
    <td>
        <p>
            <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax"/>
            <s:select
                    name="guiSportCenter.province.id"
                    headerKey="0"
                    headerValue="All"
                    list="provinceList"
                    listKey="id"
                    listValue="name"
                    value="guiSportCenter.province.id"
                    id="idSelectProvince"
                    onchange="getCitiesByProvince($(this).value, 0, 0, 0, '%{citiesByProvinceURL}')"/>
            <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/></span></font>
        </p>
    </td>
</tr>

<tr>
    <td>
        <p>
            City
        </p>
    </td>
    <td>
        <p>
            <s:select
                    name="guiSportCenter.city.id"
                    headerKey="0"
                    headerValue="All"
                    list="cityList"
                    listKey="id"
                    listValue="name"
                    value="guiSportCenter.city.id"
                    id="idSelectCity"/>
                  <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinner_small.gif" encode="false" />"/>
                  </span>
        </p>
    </td>
</tr>

<tr>
    <td>
        Address
    </td>
    <td>
        <s:textfield name="guiSportCenter.address"/>
    </td>
</tr>

<tr>
    <td>
        Phone number
    </td>
    <td>
        <s:textfield name="guiSportCenter.telephone"/>
    </td>
</tr>

<tr>
    <td>
        Email
    </td>
    <td>
        <s:textfield name="guiSportCenter.email"/>
    </td>
</tr>

<tr>
    <td>
        Latitude
    </td>
    <td>
        <s:textfield name="guiSportCenter.latitudeStr"/>
        <s:fielderror fieldName="guiSportCenter.latitudeStr"/>
    </td>
</tr>

<tr>
    <td>
        Longitude
    </td>
    <td>
        <s:textfield name="guiSportCenter.longitudeStr"/>
        <s:fielderror fieldName="guiSportCenter.longitudeStr"/>
    </td>
</tr>

<tr>
    <td>
        Website
    </td>
    <td>
        <s:textfield name="guiSportCenter.webSite"/>
    </td>
</tr>


<tr>
    <td>
        <br/><br/>
        <a href="javascript: $('idsportCenterForm').submit();" class="btn action1">Save</a>
    </td>
    <td>

    </td>
</tr>

</table>
</s:form>

</div>
</div>
<br/> <br/> <br/>
</body>
</html>
