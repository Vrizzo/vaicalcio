<%@tag description="put the tag description here" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:url action="refresh" namespace="service" var="urlRefresh"/>
<script type="text/javascript">
    new Ajax.PeriodicalUpdater('idRefresher', '${urlRefresh}', {method: 'get', frequency: 30, decay: 1 });
</script>
<div id="idRefresher" style="display:none;"></div>
