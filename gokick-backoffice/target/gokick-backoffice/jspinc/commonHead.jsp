<%@page import="it.newmedia.gokick.backOffice.UserContext" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="imagetoolbar" content="no"/>

<link href="<s:url value="/images/favicon.ico" encode="false" />" rel="SHORTCUT ICON" type="image/x-icon"/>
<link rel="stylesheet" href="<s:url value="/css/common.css" encode="false" />" type="text/css" media="all"/>
<link rel="stylesheet" href="<s:url value="/css/displaytag.css" encode="false" />" type="text/css" media="all"/>

<!--JQuery-->
<jsp:include page="jQueryInc.jsp" flush="true"/>
<!--JQuery-->

<script type="text/javascript" src="<s:url value="/js/prototype-js-1.6.0.3/prototype-1.6.0.3.js" encode="false" />" charset="UTF-8"></script>
<%-- <script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/scriptaculous.js?load=effects,controls,dragdrop" encode="false" />" ></script> --%>
<script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/builder.js" encode="false" />" charset="UTF-8"></script>
<script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/effects.js" encode="false" />" charset="UTF-8"></script>
<script type="text/javascript" src="<s:url value="/js/scriptaculous-js-1.8.2/controls.js" encode="false" />" charset="UTF-8"></script>


<script type="text/javascript" src="<s:url value="/js/common.js" encode="false"/>" charset="UTF-8"></script>

<script type="text/javascript">
    JQ(document).ready(function () {
        /*slide Menu*/
        JQ(".slideMenuTitle").click(function () {
            JQ(this).next('.slideMenu').fadeToggle(100);
        });


        JQ('body').click(function (event) {
            if (!JQ(event.target).closest('.slideMenuWrap').length) {
                JQ('.slideMenu').fadeOut(100);

            }
            ;
        });
    });

</script>
