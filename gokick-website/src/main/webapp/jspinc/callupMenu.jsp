<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%-- NON FORMATTARE LA PAGINA, IL LINK DEVE STARE TUTTO SU UNA RIGA --%>
    <ul class="clearfix underTbLnk callupMenu">
  
  <li class="${activePage eq "callUp" ? "active" : "" } menuGK" >
    <a href="callUpToMatch!viewUsersToCall.action?idMatch=${idMatch}&activePage=callUp" ><u:translation key="label.gokickers.di"/> ${titleProvince}</a>
  </li>

  <li class="${activePage eq "friend" ? "active" : "" }" >
      <a href="callUpToMatch!friends.action?idMatch=${idMatch}&activePage=friend" ><u:translation key="label._Amici" /></a>
  </li>

  <li class="${activePage eq "calledUp" ? "active" : "" }" >
     <a href="callUpToMatch!calledUp.action?idMatch=${idMatch}&activePage=calledUp" ><u:translation key="label.Convocati"/></a>
  </li>

  <li class="${activePage eq "invite" ? "active" : "" }" >
     <a href="inviteToMatch!input.action?idMatch=${idMatch}&activePage=invite" ><u:translation key="label.invite" /></a>
  </li>
</ul>



