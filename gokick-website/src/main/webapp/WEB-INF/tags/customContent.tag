<%@tag description="Stampa il contenuto personalizzato (da WordPress) in un iFrame" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@attribute name="position" description="ubicazione del contenuto nella pagina" required="true" %>
<%@attribute name="width" description="specifica larghezza iFrame" required="false" %>
<%@attribute name="height" description="specifica altezza iFrame" required="false" %>
<%@attribute name="URL" description="url da chiamare" required="false" %>
<%--
  url di test /service/custmContentTest.action
  url di prod /blog/service
  --%>
  <c:if test="${URL==null}">
    <c:set var="URL" >${currentCobrand.siteUrl}/blog/service</c:set>
  </c:if>
    <c:url value="${URL}"   var="iFrameUrl" >
      <c:param name="section"   >${currentServletPath}</c:param>
      <c:param name="position"  >${position}</c:param>
      <c:param name="language"  >${userContext.language.language}</c:param>
      <c:if test="${userContext.user.id ne null}" >
        <c:param name="userId"    >${userContext.userInfo.id}</c:param>
        <c:param name="countryId" >${userContext.userInfo.idCountry}</c:param>
        <c:param name="provinceId">${userContext.userInfo.idProvince}</c:param>
        <c:param name="cityId"    >${userContext.userInfo.idCity}</c:param>
      </c:if>
      <c:param name="cobrand"  >${currentCobrandCode}</c:param>
    </c:url>
<c:if test="${width!=null}">
  <c:set var="width">${width}px</c:set>
</c:if>
<c:if test="${height!=null}">
  <c:set var="height">${height}px</c:set>
</c:if>
<iframe id="idFramecContent" width="${width}" height="${height}"  src="${iFrameUrl}" style="border: 0px" frameborder="0" ></iframe>
