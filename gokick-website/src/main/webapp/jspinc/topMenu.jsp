<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<u:loggedIn>
  <div class="columnMenu">
    <a href="<s:url action="home"/>" class="${param["tab"] eq "home" ? "active" : "" }"><u:translation key="label.Home"/></a>
    <a href="<s:url action="squad"/>" class="${param["tab"] eq "squad" ? "active" : "" }"><u:translation key="label._Amici"/></a>
    <u:isOrganizer>
      <a href="<s:url action="organizeMatchCreate" method="input"/>" class="${param["tab"] eq "organize" ? "active" : "" }"><u:translation key="label.Organizza"/></a>
    </u:isOrganizer>
    <u:isNotOrganizer>
      <a href="<s:url action="organizeMatchWelcome" />" class="${param["tab"] eq "organize" ? "active" : "" }"><u:translation key="label.Organizza"/></a>
    </u:isNotOrganizer>
    <a href="<s:url action='calendar!viewCalendar' ><s:param name='defaultSearch' value='true' /></s:url>" class="${param["tab"] eq "play" ? "active" : "" }"><u:translation key="label.Gioca"/></a>
    
    <%-- inizio da visualizzare solo sotto GIOCA e gestire classe attiva "active" --%>
    <p>
          <a href="<s:url action="calendar!viewCalendar" ><s:param name="defaultSearch" value="true" /></s:url>"><u:translation key="label.Calendario"/></a>
          <a href="<s:url action="results!viewResults" />"><u:translation key="label.Risultati"/></a>
        </p>
        <%-- fine da visualizzare solo sotto GIOCA e gestire classe attiva "active" --%>
        
        <br />
    <a href="<s:url action="userPlayer!input"/>" class="${param["tab"] eq "scheda" ? "active" : "" }"><u:translation key="label.scheda"/></a>
    <%--cpelli<span>[<a class="last" href="javascript: void(0);" onclick="openPopupHelp('H001')" >?</a>]</span>--%>
  </div>
</u:loggedIn>
<u:loggedOut>
  <div class="columnMenu">
    <%--a href="<s:url action="userIntro" />" class="${param["tab"] eq "register" ? "active" : "" }"><u:translation key="label.Registrati"/>!</a>
    <a href="<s:url action="tour1" />" class="${param["tab"] eq "tour" ? "active" : "" }"><u:translation key="label.Tour"/></a--%>
    <a href="<s:url action="historyInfo"/>" class="${param["tab"] eq "storia" ? "active" : "" }"><u:translation key="label.PlayMore"/>!</a>
    <%--a href="<s:url action="home" />" class="${param["tab"] eq "home" ? "active" : "" }"><u:translation key="label.Benvenuto"/></a--%>
  </div>
</u:loggedOut>
