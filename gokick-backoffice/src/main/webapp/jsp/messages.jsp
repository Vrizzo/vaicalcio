<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true"/>
    <title>Messages</title>
    <script type="text/javascript">
        function showMessageFields(value) {

            var ctrl;
            if (value == null) {
                ctrl = 0;
            }
            else {
                ctrl = value;
            }

            if (ctrl == 1 || ctrl == 3 || ctrl == 6 || ctrl == 7) {
                $('idDivBoard').show();
                $('idDivMail').hide();
                $('idPwd').show();
                $('idErrorMails').hide();
            }
            else if (ctrl == 2 || ctrl == 4 || ctrl == 5 || ctrl == 8 || ctrl == 9) {
                $('idDivMail').show();
                $('idDivBoard').hide();
                $('idPwd').show();
                $('idErrorMails').hide();
            }
            else {
                $('idDivMail').hide();
                $('idDivBoard').hide();
                $('idPwd').hide();
                $('idErrorMails').hide();
            }
            $('idErrorMails').hide();

        }

        function showMailErrorsMessage() {

            if (${hasErrors}) {
                $('idErrorMails').show();
            }
            else {
                $('idErrorMails').hide();
            }
        }

        function initialize() {
            if (!${messageSent}) {
                showMessageFields(${receiverPlace});
                showMailErrorsMessage();
            }
        }
    </script>

</head>

<body onload="initialize();">


<div class="wrapper">

    <!--### start header ###-->
    <jsp:include page="../jspinc/header.jsp" flush="true"/>
    <!--### end header ###-->

    <div style="padding-left:20px;">

        <s:if test="messageSent">
            <p style="padding-top:50px;">
                <strong>
                    messages successfully sent
                </strong>
            </p>

            <p>
                <s:if test="mailsSent.size > 0">
                    <s:iterator value="mailsSent" var="mail">
                        ${mail} <br/>
                    </s:iterator>
                </s:if>
            </p>
            <p>
            <s:if test="mailsNotSent.size > 0">
                <p>
                    <strong>
                        it was not possible to send your message to the following recipients:
                    </strong>
                </p>

                <p>
                    <s:iterator value="mailsNotSent" var="notMail">
                        ${notMail} <br/>
                    </s:iterator>
                </p>
            </s:if>
            </p>
        </s:if>
        <s:else>
            <s:form action="sendMessage!send">

                <s:hidden name="receiverType"/>
                <table>
                    <tr>
                        <td width="924" height="300" valign="" bgcolor="whitesmoke">
                            <p>Send Message</p>

                            <p>
                                <s:select
                                        name="receiverPlace"
                                        list="receiverMapList"
                                        headerKey="0"
                                        headerValue="select recipient..."
                                        listKey="key"
                                        listValue="value"
                                        onchange="showMessageFields(this.value);"
                                        />
                            </p>


                            <div id="idDivBoard" style="display: none;">
                                <p>&nbsp;</p>
                                <s:textfield name="boardMessage" value="write your message..."/>
                                <s:fielderror name="boardTextError" fieldName="boardTextError"/>
                            </div>

                            <div id="idDivMail" style="display: none;">
                                <p>
                                    <b>Subject</b>
                                </p>

                                <p>
                                    <s:textfield name="mailObject"/>
                                    <s:fielderror name="mailObjError" fieldName="mailObjError"/>
                                </p>

                                <p style="line-height:100%; margin:0;" align="left">
                                    <b>Email</b>
                                </p>

                                <p>
                                    <s:textarea name="mailBody" rows="3" cols="50"/>
                                    <s:fielderror name="mailBodyError" fieldName="mailBodyError"/>
                                </p>
                            </div>

                            <div id="idPwd" style="display: none;">
                                <p>
                                    <b>Password</b>
                                </p>

                                <p style="line-height:100%; margin:0;">
                                    <s:password name="userPwd"/>
                                    <s:fielderror name="boardTextError" fieldName="boardTextError"/>
                                </p>

                                <p>
                                    <s:submit value="Send" cssClass="btn"/>
                                    <s:fielderror name="pwdError" fieldName="pwdError"/>
                                </p>
                            </div>

                        </td>
                    </tr>
                </table>
            </s:form>

        </s:else>

        <div id="idErrorMails" style="display: none;visibility:collapse">
            the following emails have not been sent: <br/>
            <s:actionerror/>
        </div>

    </div>


</div>
<!--### end wrapper ###-->
</body>
</html>
