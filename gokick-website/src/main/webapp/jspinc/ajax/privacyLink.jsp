<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="accettazionePrivacyInfoPopup${lang}.action" var="privacyUrl" />
<a class="linkUnderline" style="color:black;" href="javascript: openPopupPrivacy('${privacyUrl}', '');" >
  <s:property value="%{getText('label.accettazionePrivacy.2')}" />
</a>