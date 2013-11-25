<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>


<s:if test="idCondition==0" >
    <!-- <img src="images/arrow_down.jpg" alt="" /> -->
</s:if>

<s:if test="idCondition==1" >
    <img src="images/infermeria.jpg" alt="" />
</s:if>

<s:if test="idCondition==2" >
    <img src="images/arrow_down.jpg" alt="" />
</s:if>

<s:if test="idCondition==3" >
    <img src="images/arrow_right.jpg" alt="" />
</s:if>

<s:if test="idCondition==4" >
    <img src="images/arrow_up.jpg" alt="" />
</s:if>
