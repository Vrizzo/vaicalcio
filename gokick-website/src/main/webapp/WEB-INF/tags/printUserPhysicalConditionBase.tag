<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@attribute name="user" required="true" type="it.newmedia.gokick.data.hibernate.beans.User" %>

<%--<s:set value="%{getText(userToShow.physicalConditionKey)}" var="descrizione"  />--%>
<s:set name="idContition">${user.physicalCondition.id}</s:set>

<s:if test="#idContition==1" >
    <img src="images/infermeria.jpg" alt="${descrizione}" /><br />
</s:if>

<s:elseif test="#idContition==2" >
    <img src="images/arrow_down.jpg" alt="${descrizione}" /><br />
</s:elseif>

<s:elseif  test="#idContition==3" >
    <img src="images/arrow_right.jpg" alt="" /><br />
</s:elseif>

<s:elseif  test="#idContition==4" >
    <img src="images/arrow_up.jpg" alt="${descrizione}" /><br />
</s:elseif>

<s:else>
    <img src="images/arrow_right.jpg" alt="" /><br />
</s:else>


<s:if test="( #idContition > 0 )" >
  <c:if test="${description}" >
    <span class="valueField">
      ${descrizione}
    </span>
  </c:if>
</s:if>