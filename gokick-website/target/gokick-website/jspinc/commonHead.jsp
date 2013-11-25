<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="imagetoolbar" content="no"/>
<link href="<s:url value="/images/favicon.ico" encode="false" />" rel="SHORTCUT ICON" type="image/x-icon" />
<c:if test="${param['dataTable'] eq true}">
  <style type="text/css">
  /*margin and padding on body element
    can introduce errfors in determining
    element position and are not recommended;
    we turn them off as a foundation for YUI
    CSS treatments. */
  body {
    margin:0;
    padding:0;
  }
  </style>

  <link rel="stylesheet" href="<s:url value="/css/YUI/paginator.css" encode="false" />" type="text/css" media="all" />
  <link rel="stylesheet" href="<s:url value="/css/YUI/dataTable.css" encode="false" />" type="text/css" media="all" />
  <!--
  <script type="text/javascript" src="<s:url value="/js/YUI/2.7.0/yahoo-dom-event.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.7.0/connection-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.7.0/element-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.7.0/paginator-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.7.0/datasource-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.7.0/datatable-min.js" encode="false" />"></script>
  -->
  <!-- Patch for YUI datatable sorting problem when YUI datatable into a html table  -->
  <!--
  <script type="text/javascript" src="<s:url value="/js/YUI/2.7.0/datatable-sort-patch.js" encode="false" />"></script>
  -->
  
  <script type="text/javascript" src="<s:url value="/js/YUI/2.8/yahoo-dom-event.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.8/connection-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.8/element-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.8/paginator-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.8/datasource-min.js" encode="false" />"></script>
  <script type="text/javascript" src="<s:url value="/js/YUI/2.8/datatable-min.js" encode="false" />"></script>
  <!-- Patch for YUI datatable sorting problem when YUI datatable into a html table -->
  <script type="text/javascript" src="<s:url value="/js/YUI/2.8/datatable-sort-patch.js" encode="false" />"></script>

</c:if>
<link rel="stylesheet" href="<s:url value="/css/" encode="false" />common-${currentCobrandCode}.css" type="text/css" media="all" />
<link rel="stylesheet" href="<s:url value="/css/common.css" encode="false" />" type="text/css" media="all" />
<link rel="stylesheet" href="<s:url value="/css/displaytag.css" encode="false" />" type="text/css" media="all" />

<script type="text/javascript" src="<s:url value="/js/prototype-js-1.6.0.3/prototype-1.6.0.3.js" encode="false" />" charset="UTF-8" ></script>
<%-- <script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/scriptaculous.js?load=effects,controls,dragdrop" encode="false" />" ></script> --%>
<script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/builder.js" encode="false" />" charset="UTF-8" ></script>
<script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/effects.js" encode="false" />" charset="UTF-8" ></script>
<script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/controls.js" encode="false" />" charset="UTF-8" ></script>
<script type="text/javascript" >
  var URL_SHOWLASTMESSAGE = '<s:url action="showLastMessage" namespace="/ajax" />';
  var URL_OPENHELP = '<s:url action="helpInfoPopup" />';
</script>

<!--JQuery-->
<jsp:include page="../jspinc/jQueryInc.jsp" flush="true" />
<!--JQuery-->
<script type="text/javascript">
  JQ(document).ready(function(){
    /*slide Menu*/
    JQ(".slideMenuTitle").click(function(){
      JQ(this).next('.slideMenu').fadeToggle(100);
    });
    
    
    JQ('body').click(function (event) {
      if (!JQ(event.target).closest('.slideMenuWrap').length) {
        JQ('.slideMenu').fadeOut(100);

      };
    });

    //location.href='#';
  });

</script>

<script type="text/javascript" src="<s:url value="/js/common.js" encode="false"/>" charset="UTF-8" ></script>

<c:if test="${param['calendar'] eq true}">
  <link type="text/css" rel="stylesheet" href="<s:url value="/css/calendar.css" encode="false"/>" media="screen"></link>
  <script type="text/javascript" src="<s:url value="/js/calendar/dhtmlSuite-common.js" encode="false" />"></script>
	<script type="text/javascript" src="<s:url value="/js/calendar/dhtmlSuite-dragDropSimple.js" encode="false"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/calendar/dhtmlSuite-calendar.js" encode="false"/>"></script>
  <script type="text/javascript" >

  //remove week column

  var calendarObj;
  var chkImposta=0;
  var visibleDate;
  var formatOut;
  var hideOkBut;
  
	function showCalendar(idInputTextBox, showHourMinute,idVisibleDate,formatOutDate)
	{
    if (chkImposta==1)
    {
      return;
    }
    
    var enabledFromValue = new Date();
    enabledFromValue.setDate(enabledFromValue.getDate()-1);
    enabledFromValue.setHours(enabledFromValue.getHours() +1);

    var enabledToValue = new Date();
    enabledToValue.setYear(enabledToValue.getFullYear() + 1);

    var options = {
      showHourMinute: showHourMinute,
      enabledFrom: enabledFromValue,
      enabledTo: enabledToValue,
      hideOkbutton: !showHourMinute
    };
    
    showCalendarOptions(idInputTextBox, options,idVisibleDate,formatOutDate)

  }


  function showCalendarOptions(idInputTextBox, options,idVisibleDate,formatOutDate)
	{
    visibleDate= $(idVisibleDate);
    formatOut=formatOutDate;
    if (chkImposta==1)
    {
      return;
    }
    var date = new Date();
    var hour=(date.getHours()) + 1;
    var calendarModelObj = new DHTMLSuite.calendarModel({initialHour:(hour)});
    calendarModelObj.addInvalidDateRange(false,{year: options.enabledFrom.getFullYear(),month:options.enabledFrom.getMonth()+1,day:options.enabledFrom.getDate()});
    if (options.enabledTo!=null)
    {
      calendarModelObj.addInvalidDateRange({year: options.enabledTo.getFullYear(),month:options.enabledTo.getMonth()+1,day:options.enabledTo.getDate()+1},false);
    }
     calendarModelObj.setLanguageCode('<s:property value="currentLanguage" />');

    var calendarOption =
    {
    minuteDropDownInterval:5,
    numberOfRowsInHourDropDown:10,
    numberOfRowsInMinuteDropDown:10,
    callbackFunctionOnDayClick:'setCalendarDate',
    callbackFunctionOnClose: 'close',
    displayTodaysDateInNavigationBar: false,
    isDragable:false,
    displayTimeBar:options.showHourMinute,

    displayCloseButton: true
      };

    calendarObj = new DHTMLSuite.calendar(calendarOption);
    calendarObj.setCalendarModelReference(calendarModelObj);
    var inputTextBox = $(idInputTextBox);
    var initDate = options.initialDateFrom;
  
    var dateTimeFormat = options.showHourMinute ? 'dd/mm/yyyy hh:ii' : 'dd/mm/yyyy';
		
    calendarObj.setCalendarPositionByHTMLElement(inputTextBox,0,inputTextBox.offsetHeight+2);	// Position the calendar right below the form input
    calendarObj.setInitialDateFromInput((initDate!=null? initDate : inputTextBox), dateTimeFormat);	// Specify that the calendar should set it's initial date from the value of the input field.
    calendarObj.addHtmlElementReference('calendarTextBoxField',inputTextBox);	// Adding a reference to this element so that I can pick it up in the getDateFromCalendar below(myInput is a unique key)

    if(calendarObj.isVisible())
    {
			calendarObj.hide();
		}
    else
    {
			calendarObj.resetViewDisplayedMonth();	// This line resets the view back to the inital display, i.e. it displays the inital month and not the month it displayed the last time it was open.
			calendarObj.display();
		}
    
    if (options.hideOkbutton!=true)
    {
        if (chkImposta!=1)
        {
          chkImposta=1;
           var calContainer = $$('.DHTMLSuite_calendar_timeBar').each(function(el)
            {
              Element.insert(el, { top: '<div class="DHTMLSuite_customCloseButton" ><a class="btn action1" id="idOk" href="javascript: void(0);" onclick="closeCalendar();" >OK</a></div>'});
            });
        }
      hideOkBut=false;
    }
    else
    {
      hideOkBut=true;
    }
	}

  function closeCalendar()
  {
    
    var inputArray =
    {
      year: calendarObj.calendarModelReference.displayedYear,
      month: calendarObj.calendarModelReference.displayedMonth.toString().lpad("0", 2),
      day: calendarObj.calendarModelReference.displayedDay.toString().lpad("0", 2),
      hour: calendarObj.calendarModelReference.displayedHour.toString().lpad("0", 2),
      minute: calendarObj.calendarModelReference.displayedMinute.toString().lpad("0", 2)
    };
		var references = calendarObj.getHtmlElementReferences(); // Get back reference to form field.

    references.calendarTextBoxField.value = inputArray.day +'/' + inputArray.month + '/' + inputArray.year;

    if(calendarObj.displayTimeBar)
    {
      references.calendarTextBoxField.value += ' ' + inputArray.hour + ':' + inputArray.minute;
    }
    
    showDate(visibleDate,references.calendarTextBoxField.value,hideOkBut==true ? 'dd/MM/yyyy' : 'dd/MM/yyyy HH:mm'  );
    
    calendarObj.hide();
    chkImposta=0;
    showRegistrationOpenDays();
  }

  function close()
  {
    chkImposta=0;
    hideOkBut=false;
  }

  function showDate(idShowDate,date,formatIn)
  {
    new Ajax.Updater(idShowDate, '<s:url action="showDate" namespace="/ajax"/>',
        {
          parameters:
          {
            data: date,
            formatIn: formatIn,
            formatOut: formatOut,
            dummy: Math.random()
          }
        });
  }
  
	function setCalendarDate(inputArray)
	{
    
    $$('.DHTMLSuite_calendar_newSelection').each(function(el){
      el.removeClassName('DHTMLSuite_calendar_newSelection');
    });

    $$('.DHTMLSuite_calendarContent td').each(function(el){
      if( inputArray.day == el.innerHTML.toString().lpad("0", 2))
      {
        var allClassName = $w(el.className);
        allClassName.each(function(className){
          if( className.endsWith('InThisMonth') || className.endsWith('_currentDate') )
            {
              el.addClassName('DHTMLSuite_calendar_newSelection');
              return;
            }
        });
      }
    });
    if (hideOkBut)
    {
      closeCalendar();
      hideOkBut=false;
    }
  }
  
  </script>
</c:if>
