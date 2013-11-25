<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
      <title><s:property value="%{getText('page.title.default')}" /></title>
      <script type="text/javascript" src="<s:url value="/js/prototype-js-1.6.0.3/prototype-1.6.0.3.js"/>" encode="false" ></script>
      <script type="text/javascript">
        function countCharsVariable()
        {
          if ($('idDescriptionArea').value.length > 140)
          {
            oldString = $('idDescriptionArea').value.substring(0, 140);
            $('idDescriptionArea').value = oldString;
          }
          else
          {
            $('idCharsVariable').innerHTML = 140 - $('idDescriptionArea').value.length;
          }
        }
      </script>
      <!--### start Google Analitics inclusion ###-->
      <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
      <!--### end Google Analitics inclusion ###-->
    </head>
    <body>

        <div class="wrapper">
            
         <!--### start header ###-->
          <jsp:include page="../jspinc/header.jsp" flush="true" />
         <!--### end header ###-->

         <!--### start leftcolumn ###-->
          <jsp:include page="../jspinc/leftColumn.jsp" flush="true" />
         <!--### end leftcolumn ###-->

            <!--### start centralcolumn ###-->
            <div class="centralCol">

              <!--### start mainContent ###-->
                <div class="mainContent">

                  <h1><u:translation key="label.Preferenze" /></h1>

                  <s:form action="squadPreferences!save" method="post"   >

                    <table>
                      <tr>
                        <td><u:translation key="label.squadPreferenceNomeRosa" />:</td>
                        <td>
                          <s:textfield name="name" size="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                        </td>
                      </tr>
                      <tr>
                        <td><u:translation key="label.squadPreferenceDescrizioneRosa" />:</td>
                        <td>
                          <s:textarea id="idDescriptionArea" name="description" cols="50" rows="3" onkeyup="countCharsVariable()" onkeypress="countCharsVariable()" onkeydown="countCharsVariable()" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                          <em style="color:#808080;"><span id="idCharsVariable">140 </span> <u:translation key="label.caratteri" /></em>
                          <s:fielderror name="errorDescription" fieldName="description" />
                        </td>
                      </tr>
                      <tr>
                        <td><u:translation key="label.squadPreferenceSolitamenteGiochiamo" />:</td>
                        <td>
                            <fieldset>
                              <s:checkbox name="playingWeekdaysMon" />
                              <label for="squad_playingWeekdaysMon"><u:translation key="label.lunediAbbr" /></label>
                              <s:checkbox name="playingWeekdaysTue" />
                              <label for="squad_playingWeekdaysTue"><u:translation key="label.martediAbbr" /></label>
                              <s:checkbox name="playingWeekdaysWed" />
                              <label for="squad_playingWeekdaysWed"><u:translation key="label.mercolediAbbr" /></label>
                              <s:checkbox name="playingWeekdaysThu" />
                              <label for="squad_playingWeekdaysThu"><u:translation key="label.giovediAbbr" /></label>
                              <s:checkbox name="playingWeekdaysFri" />
                              <label for="squad_playingWeekdaysFri"><u:translation key="label.venerdiAbbr" /></label>
                              <s:checkbox name="playingWeekdaysSat" />
                              <label for="squad_playingWeekdaysSat"><u:translation key="label.sabatoAbbr" /></label>
                              <s:checkbox name="playingWeekdaysSun" />
                              <label for="squad_playingWeekdaysSun"><u:translation key="label.domenicaAbbr" /></label>
                              <s:fielderror name="errorPlayingWeekdays" fieldName="playingWeekdays" />
                          </fieldset>
                        </td>
                      </tr>
                      <tr>
                        <td><u:translation key="label.mercato" />:</td>
                        <td>
                            <fieldset>
                            <s:radio name="marketEnabled" list="#{'1':getText('label.Aperto'),'0':getText('label.Chiuso')}" />
                            </fieldset>
                        </td>
                      </tr>
                      <tr>
                        <td><u:translation key="label.squadPreferenceVisualizzazione" />:</td>
                        <td>
                          <s:checkbox name="hiddenEnabled" />
                          <label for="squad_hiddenEnabled"><u:translation key="label.squadPreferenceNascondiRosa" /></label>
                        </td>
                      </tr>
                      <tr>
                        <td></td>
                        <td><u:translation key="label.squadPreferenceWebSiteInfo" /></td>
                      </tr>
                      <tr>
                        <td><u:translation key="label.squadPreferenceWebSite" />:</td>
                        <td>
                          <s:textfield name="webSite" size="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                        </td>
                      </tr>
                      <tr>
                        <td><u:translation key="label.squadPreferenceFoto" />:</td>
                        <td>
                          <s:textfield name="photoSite" size="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                        </td>
                      </tr>
                      <tr>
                        <td><u:translation key="label.squadPreferenceVideo" />:</td>
                        <td>
                          <s:textfield name="videoSite" size="20" cssStyle="border:solid 1px #999999; background:#FFFFFF;" />
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <p>
                            <s:submit cssClass="btn" value="%{getText('btn.save')}" />
                          </p>
                        </td>
                        <td></td>
                      </tr>
                    </table>

                  </s:form>
                  
                </div>
                <!--### end mainContent ###-->
                
            </div>
            <!--### end centralcolumn ###-->

        <!--### start rightcolumn ###-->
          <jsp:include page="../jspinc/rightcolumn.jsp" flush="true" />
        <!--### end rightcolumn ###-->

        <!--### start footer ###-->
          <jsp:include page="../jspinc/footer.jsp" flush="true" />
        <!--### end footer ###-->
            
        </div>
        
    </body>
</html>
