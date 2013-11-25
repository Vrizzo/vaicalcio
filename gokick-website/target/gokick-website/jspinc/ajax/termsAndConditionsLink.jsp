<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="termsAndConditionsInfoPopup${lang}.action" var="termsUrl" />
<a  class="linkUnderline" style="color:black; " href="javascript: openPopupTermsAndConditions('${termsUrl}','');">
  <s:property value="%{getText('label.accettazioneCondizioniUtilizzo.2')}" />
</a>