<%@tag description="Display playing weekdays" pageEncoding="UTF-8"%>
<%@attribute name="playingWeekdays" required="true"%>

<%
  String playingWeekdays = (String)jspContext.getAttribute("playingWeekdays");
  String printablePlayingWeekdays = "";
  if( playingWeekdays.equalsIgnoreCase("1111111") )
    printablePlayingWeekdays = "tutti";
  else if  ( playingWeekdays.equalsIgnoreCase("0000000") )
    printablePlayingWeekdays = "-";
  else if  ( playingWeekdays.equalsIgnoreCase("-") )
    printablePlayingWeekdays = "-";
  else
  {
    if( playingWeekdays.charAt(0) == '1')
      printablePlayingWeekdays += "Lun";
    if( playingWeekdays.charAt(1) == '1')
      printablePlayingWeekdays += ", Mar";
    if( playingWeekdays.charAt(2) == '1')
      printablePlayingWeekdays += ", Mer";
    if( playingWeekdays.charAt(3) == '1')
      printablePlayingWeekdays += ", Gio";
    if( playingWeekdays.charAt(4) == '1')
      printablePlayingWeekdays += ", Ven";
    if( playingWeekdays.charAt(5) == '1')
      printablePlayingWeekdays += ", Sab";
    if( playingWeekdays.charAt(6) == '1')
      printablePlayingWeekdays += ", Dom";
    if(printablePlayingWeekdays.charAt(0) == ',')
      printablePlayingWeekdays = printablePlayingWeekdays.substring(2, printablePlayingWeekdays.length());
  }
%>
<%= printablePlayingWeekdays %>