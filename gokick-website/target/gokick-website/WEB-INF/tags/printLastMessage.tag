<%@tag description="Stampa l'ultimo messaggio leggendolo tramite ajax" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="lastMessage" >
</div>
<script type="text/javascript">
Event.observe(window, 'load', function() { showLastMessage(); });
</script>