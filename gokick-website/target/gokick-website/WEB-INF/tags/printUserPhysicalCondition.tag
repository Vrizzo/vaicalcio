<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@attribute name="userToShow" required="true" type="it.newmedia.gokick.site.infos.UserInfo" %>
<%@attribute name="description" required="false"    type="Boolean" %>



<s:set name="condition">${userToShow.idPhysicalCondition}</s:set>
<s:set name="descrizione"><u:translation key="${userToShow.physicalConditionKey}"/></s:set>
<s:if test="#condition==''" >
    <!-- <img src="images/arrow_down.jpg" alt="" /> --><br />
</s:if>

<s:if test="#condition==1" >
  <img src="<s:url value="/images/infermeria.jpg"/>" title="${descrizione}" /><br />
</s:if>

<s:if test="#condition==2" >
    <img src="<s:url value="/images/arrow_down.jpg"/>" title="${descrizione}" /><br />
</s:if>

<s:if test="#condition==3" >
    <img src="<s:url value="/images/arrow_right.jpg"/>" title="${descrizione}" /><br />
</s:if>

<s:if test="#condition==4" >
    <img src="<s:url value="/images/arrow_up.jpg"/>" title="${descrizione}" /><br />
</s:if>


<s:if test="( #condition > 0 )" >
  <c:if test="${description}" >
    <span class="valueField">
      ${descrizione}
    </span>
  </c:if>
</s:if>