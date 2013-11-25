<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<html>
  <head>

  </head>
</html>
<body>
<b><%=new java.util.Date().toString()%></b>
<br/>
<br/>
<h2 style="color: red;">
<s:actionerror />
</h2>

<h2>Caricamento Province</h2>
<s:form action="test!provinceList" >
  <s:submit/>
</s:form>

<hr />

<h2>Prendo sportCenter 1</h2>
<s:form action="test!sportCenter" >
  <s:submit/>
</s:form>

<hr />

<s:property value="startAt" />
<br/>
<s:property value="endAt" />
<br/>
<s:property value="duration" />
<br/>


<s:property value="data" escape="false" />

</body>







