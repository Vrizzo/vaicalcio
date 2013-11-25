<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>

<ul class="underTbLnk">

  <s:set name="activePage">${param["activePage"]}</s:set>
  <s:url action="matchComments!viewAll" var="viewMatchUrl">
    <s:param name="idMatch">${idMatch}</s:param>
  </s:url>


  <li class="${param["activePage"] eq "viewMatch" ? "active" : "" }" ><a href="${viewMatchUrl}"><u:translation  key="label.Iscritti" /></a></li>

  <li class="${param["activePage"] eq "archiveMatch" ? "active" : "" }" >
  <s:if test="#activePage == 'viewMatch'">
    <s:if test="guiMatchInfo.matchInfo.canceled == true">
      <a href="#" style="cursor: default;"><u:translation key="label.Pagelle" /></a>
    </s:if>
    <s:elseif test="guiMatchInfo.matchInfo.recorded == true">
      <s:url action="archiveMatch!report" var="viewReportUrl">
        <s:param name="idMatch">${guiMatchInfo.matchInfo.id}</s:param>
      </s:url>
      <a href="${viewReportUrl}" ><u:translation key="label.Pagelle" /></a>
    </s:elseif>
    <s:elseif test="matchDone">
      <s:url action="archiveMatch" method="input" var="archiveMatchUrl">
        <s:param name="idMatch">${guiMatchInfo.matchInfo.id}</s:param>
      </s:url>
      <a href="${archiveMatchUrl}" ><u:translation key="label.Pagelle" /></a>
    </s:elseif>
    <s:else>
      <s:url action="archiveMatch" method="input" var="archiveMatchUrl">
        <s:param name="idMatch">${guiMatchInfo.matchInfo.id}</s:param>
      </s:url>
      <a href="${archiveMatchUrl}" ><u:translation key="label.Pagelle" /></a>
    </s:else>
  </s:if>
  

  <s:elseif test="#activePage == 'archiveMatch'">
    <a class="active" href="javascript: void(0);"><u:translation key="label.Pagelle"/></a>
  </s:elseif>
    
  <s:elseif test="#activePage == 'viewComments'">
    <s:if test="currentMatch.canceled == true">
      <a href="#" style="cursor: default;"><u:translation key="label.Pagelle" /></a>
    </s:if>
    <s:elseif test="currentMatch.recorded == true">
      <s:url action="archiveMatch!report" var="viewReportUrl">
        <s:param name="idMatch">${currentMatch.id}</s:param>
      </s:url>
      <a href="${viewReportUrl}" ><u:translation key="label.Pagelle" /></a>
    </s:elseif>
    <s:else>
      <s:url action="archiveMatch" method="input" var="archiveMatchUrl">
        <s:param name="idMatch">${currentMatch.id}</s:param>
      </s:url>
      <a href="${archiveMatchUrl}" ><u:translation key="label.Pagelle" /></a>
    </s:else>
  </s:elseif>
</li>
  <c:if test="${activePage eq 'viewMatch'}">
    <li ><a href="#startComments">${countPost} Post</a></li>
  </c:if>
  <c:if test="${activePage ne 'viewMatch'}">
    <li ><a href="${viewMatchUrl}#startComments">${countPost} Post</a></li>
  </c:if>

</ul>



