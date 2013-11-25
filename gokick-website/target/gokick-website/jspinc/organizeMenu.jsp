<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
  <ul class="pageMenu">
    <li><a href="<s:url action="organizeMatchWelcome"/>" class="${param["tab"] eq "welcome" ? "active" : "" }"><u:translation key="label.Benvenuto_escl"/></a>
    <li><a href="<s:url action="organizeMatchCreate" method="input"/>" class="${param["tab"] eq "organize" ? "active" : "" }"><u:translation key="label.Organizza"/></a>
    <li><a href="<s:url action="organizeMatchTutorial"/>" class="${param["tab"] eq "tutorial" ? "active" : "" }"><u:translation key="label.Tutorials"/></a>
	<u:showForCobrands codes="GOKICK">
    <c:set var="urlForum"><u:translation key="label.organizeWelcome.forumLink"/></c:set>
    <li><a class="${param["tab"] eq "forum" ? "active" : "" }" target="_blank" href="${urlForum}"><u:translation key="label.Forum"/></a></li>
	</u:showForCobrands>
  </ul>
