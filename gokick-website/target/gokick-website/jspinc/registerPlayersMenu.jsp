<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<%-- NON FORMATTARE LA PAGINA, IL LINK DEVE STARE TUTTO SU UNA RIGA --%>

    <ul class="clearfix underTbLnk callupMenu">

  <li class="${activePage eq "gokickers" ? "active" : "" }" >
    <a href="registerToMatch.action?idMatch=${idMatch}&activePage=gokickers&team=${team}" ><b>GoKickers</b></a>
  </li>

  <li class="${activePage eq "outers" ? "active" : "" }" >
      <a href="registerToMatch!outers.action?idMatch=${idMatch}&activePage=outers&team=${team}" ><u:translation key="label.Esterni" /></a>
  </li>


</ul>


