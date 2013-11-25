<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>
    <script type="text/javascript">
      
 
  function inizializeForm() 
  {
    showMarketIcon();
    if($('idTitoloScheda').value=='')
      $('idTitoloScheda').value='<u:translation key="label.userPlayerEsTitlescheda"/>';
    if($('idCaratteristica').value=='')
      $('idCaratteristica').value='<u:translation key="label.userPlayerEsCaratteristica"/>';
    countStop($('idUserPlayerToUpdate'),240,'idShowChars');
  }

  function showMarketIcon()
      {
        if ($('idRadioMarkettrue').checked)
        {
          $('idDivMercato').show();
        }
        else
        {
          $('idDivMercato').hide();
        }     
      }

      function showCondition(idCondition)
      {
        new Ajax.Updater('idSpanCondition', '<s:url action="showCondition" namespace="/ajax"/>',
        {
          parameters: {
            idCondition: idCondition,
            dummy: Math.random()
          }
        });
      }

    function removePicture()
    {alert('${userRemovePictureUrl}');
      if (confirm('asd')) 
        $('idRemovePicLink').href='${userRemovePictureUrl}';
      else
        $('idRemovePicLink').href="##";
    }

    function clearText(idThis, type)
    {
      
        switch (type)
        {
          case 'title':
              
              if (idThis.value=='<u:translation key="label.userPlayerEsTitlescheda"/>')
              {
                idThis.value='';
              }
              break 
          case 'caratteristica': 
              if (idThis.value=='<u:translation key="label.userPlayerEsCaratteristica"/>')
              {
                idThis.value='';
              }
              break
         default: 
              return;
        }
     }
    </script>
    <!--### start Google Analitics inclusion ###-->
    <jsp:include page="../jspinc/statisticsScript.jsp" flush="true" />
    <!--### end Google Analitics inclusion ###-->
  </head>
  <body onload="inizializeForm();">
    <div class="wrapper">

      <!--### start header ###-->
      <jsp:include page="../jspinc/header.jsp" flush="true" />
      <!--### end header ###-->
      
      <!--### start leftcolumn ###-->
      <jsp:include page="../jspinc/leftColumn.jsp" flush="true" >
         <jsp:param name="tab" value="scheda"/>
      </jsp:include>
      <!--### end leftcolumn ###-->

      <!--### start centralcolumn ###-->
      <div class="centralCol">

        <div class="topPageWrap">
              <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
          </div>  
        


        <!--### start mainContent ###-->
        <div class="mainContentIndent">

          <h1><u:translation key="label.userPlayerSchedaTecnica"/></h1>
          <p class="titleList">
            <u:translation key="label.userPlayerInfoModifica"/>
            <s:url action="userDetails" var="userDetailsURL">
                <s:param name="idUser">${userPlayerToUpdate.id}</s:param>
                <s:param name="tab">scheda</s:param>
            </s:url>
            <s:set name="nameDet" >
              <s:property value="userPlayerToUpdate.firstName" escapeJavaScript="true" />_<s:property value="userPlayerToUpdate.lastName" escapeJavaScript="true" />
            </s:set>
               <a  href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');"><u:translation key="label.userPlayerComeAppare"/> &raquo;</a>
          </p>
          <s:form id="userPlayerForm" action="userPlayer!update" method="post" >

            <s:hidden name="userPlayerToUpdate.id"/>

            <div class="schedaInfo">
              <table >
                <tr>
                  <td class="lblFrmUndLn" style="width:120px;">
                    <u:translation key="label.homeGiochiA"/> [<a  href="javascript: void(0);" onclick="openPopupHelp('U001')" >?</a>]
                  </td>
                  <td>
                    <strong><s:property value="userPlayerToUpdate.city.name" />, <s:property   value="userPlayerToUpdate.province.name" />,  <s:property value="userPlayerToUpdate.country.name" /></strong>
                    [<a class="light" href="userAccount!input.action"><u:translation key="label.modify"/></a>]
                  </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                  <td style="padding-top:5px; vertical-align:top;">
                    <p style="padding-top:0;" class="lblFrmUndLn" ><u:translation key="label.mercato"/> [<a href="javascript: void(0);" onclick="openPopupHelp('U002')" >?</a>]</p>
                  </td>
                  <td style="padding-top:0;">

                    <p class="left" style="padding-top:0;">
                      <s:radio  id="idRadioMarket" name="userPlayerToUpdate.marketEnabled" list="#{'true':getText('label.Sul_Mercato'),'false':getText('label.nonSulMercato')}"
                               template="radiomapVert"  onclick="showMarketIcon()" />
                    </p>

                    <div id="idDivMercato" class="left" style="margin:5px 0 0 -5px;">
                      <span>&nbsp;&nbsp;<img src="images/gioca.gif" alt="" /></span>
                    </div>


                  </td>
                </tr>
                <tr><td>&nbsp;</td></tr>

                 <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.condizione"/>
                  </td>
                  <td>

                    <s:select
                      name="userPlayerToUpdate.physicalCondition.id"
                      list="#{'1':getText('label.condizioneFisica.infortunato'),
                              '2':getText('label.condizioneFisica.scarsa'),
                              '3':getText('label.condizioneFisica.media'),
                              '4':getText('label.condizioneFisica.buona')}"
                      onchange="showCondition(this.value)"
                      id="idSelectCondition"
                      />

                    <s:fielderror name="errorPhysicalCondition" fieldName="userPlayerToUpdate.physicalCondition" />
                    <span id="idSpanCondition">
                      <u:printUserPhysicalConditionBase user="${userPlayerToUpdate}" />
                    </span>

                  </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.titleScheda"/> [<a  href="javascript: void(0);" onclick="openPopupHelp('U003')" >?</a>]
                  </td>
                  <td>
                    <s:textfield name="userPlayerToUpdate.playerTitle" maxLength="50" id='idTitoloScheda' onclick="clearText(this, 'title');" />
                    <s:fielderror name="errorFirstName" fieldName="userPlayerToUpdate.playerTitle" />
                  </td>
                </tr>
              
                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.nationality"/>
                  </td>
                  <%--<td>
                    <s:textfield name="userPlayerToUpdate.birthdayCountry.name" maxLength="20"  />
                    <s:textfield name="userPlayerToUpdate.birthdayCountry.id" maxLength="20" size="3"  />
                    <img src="images/country_flag_1.gif" alt="" />
                    <s:fielderror name="errorFirstName" fieldName="userPlayerToUpdate.birthdayCountry" />

                  </td>--%>
                  <td colspan="2">
                    <s:select
                      name="idNationalityCountry"
                      headerKey="0"
                      headerValue="%{getText('label.select.country')}"
                      list="natCountryList"
                      listKey="id"
                      listValue="name"
                      id="idSelectNatCountry"
                      value="idNationalityCountry"
                      style="width:200px;"
                      onchange="$('idCountryFlag').src = $('idCountryFlag').src.replace(/[0-9]+.png/,this.value +'.png');"/> <!--showFlag(this.value)-->
                     <img id="idCountryFlag" src="images/flags/${idNationalityCountry}.png" alt="" onError="this.src='images/country_flag_0.gif';" />
                  </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.NatoIn"/>
                  </td>
                  <td>
                    <s:url id="provincesByCountryURL" action="provincesByCountry" namespace="/ajax" />
                    <s:select
                      name="idBirthdayCountry"
                      headerKey="0"
                      headerValue="%{getText('label.select.country')}"
                      list="natCountryList"
                      listKey="id"
                      listValue="name"
                      id="idSelectCountry"
                      value="idBirthdayCountry"
                      onchange="getProvincesByCountry($(this).value, 0, 0, 0, '%{provincesByCountryURL}')"
                      style="width:200px;" />
                    <s:fielderror name="errorCountry" fieldName="userPlayerToUpdate.birthdaycountry" />
                  </td>

                </tr>

                <tr>
                  <td style="padding-left:15px; padding-top:5px;">
                    <u:translation key="label.province"/>:
                  </td>
                  <td style="padding-top:5px;">
                    <s:url id="citiesByProvinceURL" action="citiesByProvince" namespace="/ajax" />
                    <s:select
                      name="idBirthdayProvince"
                      headerKey="0"
                      headerValue="%{getText('label.select.province')}"
                      list="provinceList"
                      listKey="id"
                      listValue="name"
                      id="idSelectProvince"
                      value="idBirthdayProvince"
                      onchange="getCitiesByProvince($(this).value, 0, 0, 0, '%{citiesByProvinceURL}')"
                      style="width:200px;" />
                    <span id="idWaitProvinces" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                      <s:fielderror name="errorProvince" fieldName="userPlayerToUpdate.province" />
                  </td>
                </tr>

                <tr>
                  <td style="padding-left:15px; padding-top:5px;">
                    <u:translation key="label.city"/>
                  </td>
                  <td style="padding-top:5px;">
                    <s:select
                      name="idBirthdayCity"
                      headerKey="0"
                      headerValue="%{getText('label.select.city')}"
                      list="cityList"
                      listKey="id"
                      listValue="name"
                      value="idBirthdayCity"
                      id="idSelectCity"
                      style="width:200px;" />
                    <span id="idWaitCities" style="display:inline; visibility:hidden;"><img src="<s:url value="/images/spinnerFF.gif" encode="false" />" /></span>
                      <s:fielderror name="errorCity" fieldName="userPlayerToUpdate.city" />
                  </td>
                </tr>


                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.bornDate"/>
                  </td>
                  <td>
                    <s:select
                      name="bornDay"
                      headerKey="-1"
                      headerValue=""
                      list="bornDayList"
                      id="idSelectBornDay"/>
                    <s:fielderror name="errorBornDay" fieldName="userPlayerToUpdate.bornDay" />

                    <s:select
                      name="bornMonth"
                      headerKey="-1"
                      headerValue=""
                      list="#{'0':getText('label.Gennaio'),
                              '1':getText('label.Febbario'),
                              '2':getText('label.Marzo'),
                              '3':getText('label.Aprile'),
                              '4':getText('label.Maggio'),
                              '5':getText('label.Giugno'),
                              '6':getText('label.Luglio'),
                              '7':getText('label.Agosto'),
                              '8':getText('label.Settembre'),
                              '9':getText('label.Ottobre'),
                              '10':getText('label.Novembre'),
                              '11':getText('label.Dicembre')
                      }"
                      id="idSelectBornMonth"/>
                    <s:fielderror name="errorBornMonth" fieldName="userPlayerToUpdate.bornMonth" />

                    <s:select
                      name="bornYear"
                      headerKey="-1"
                      headerValue=""
                      list="bornYearList"
                      id="idSelectBornYear"/>
                    <s:fielderror name="errorBornYear" fieldName="userPlayerToUpdate.bornYear" />

                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><small class="light"><u:translation key="label.userPlayerInfoEta"/></small></td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.heightWeight"/>
                  </td>
                  <td>
                    <s:select
                      headerKey="0"
                      headerValue=""
                      name="userPlayerToUpdate.playerHeight"
                      list="heightList"/>
                    <span class="light"><u:translation key="label.udmAltezza"/></span>
                    <s:fielderror name="errorHeight" fieldName="userPlayerToUpdate.height" />

                    <s:select
                      headerKey="0"
                      headerValue=""
                      name="userPlayerToUpdate.playerWeight"
                      list="weightList"/>
                    <span class="light"><u:translation key="label.udmPeso"/></span>
                    <s:fielderror name="errorWeight" fieldName="userPlayerToUpdate.weight" />
                  </td>
                </tr>

                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.roleShirt"/>
                  </td>
                  <td>
                    <s:select               
                      name="userPlayerToUpdate.playerRole.id"
                      list="#{'1':getText('label.portiere'),'2':getText('label.difensore'),
                              '3':getText('label.centrocampista'),'4':getText('label.attaccante')
                      }"
                      />
                    <s:fielderror name="errorRole" fieldName="userPlayerToUpdate.playerRole" />

                    <span class="light"><u:translation key="label.numero"/> <u:translation key="label.shirt"/></span>
                    <s:select
                      headerKey="0"
                      headerValue=""
                      name="userPlayerToUpdate.playerShirtNumber"
                      list="shirtNumberList"
                      />
                    <s:fielderror name="errorShirt" fieldName="userPlayerToUpdate.playerShirtNumber" />
                  </td>
                </tr>

                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.Piede"/>
                  </td>
                  <td>

                    <s:select
                      headerKey="0"
                      headerValue=""
                      name="userPlayerToUpdate.playerFoot.id"
                      list="#{'1':getText('label.piede.destro'),
                              '2':getText('label.piede.sinistro'),
                              '3':getText('label.piede.ambidestro')
                      }"
                      />
                    <s:fielderror name="errorFoot" fieldName="userPlayerToUpdate.playerFoot" />
                  </td>
                </tr>

                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.caratteristica"/>
                  </td>
                  <td>
                    <s:textfield name="userPlayerToUpdate.playerMainFeature" maxLength="35" cssStyle="width:200px;" id='idCaratteristica' onclick="clearText(this, 'caratteristica');"/>
                    <s:fielderror name="errorMainFeature" fieldName="userPlayerToUpdate.playerMainFeature" />
                  </td>
                </tr>

                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.tifo"/>
                  </td>
                  <td>
                    <s:select
                      headerKey="0"
                      headerValue=""
                      listKey="id"
                      listValue="name"
                      name="userPlayerToUpdate.footballTeam.id"
                      list="footballTeamList"
                      style="width:203px;"/>
                  </td>
                </tr>

                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.idolo"/>
                  </td>
                  <td>
                    <s:textfield name="userPlayerToUpdate.infoFavouritePlayer" maxLength="35" cssStyle="width:200px;" />
                    <s:fielderror name="errorFavouritePlayer" fieldName="userPlayerToUpdate.infoFavouritePlayer" />
                  </td>
                </tr>

                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.hobby"/>
                  </td>
                  <td>
                    <s:textfield name="userPlayerToUpdate.infoHobby" maxLength="35" cssStyle="width:200px;" />
                    <s:fielderror name="errorHobby" fieldName="userPlayerToUpdate.infoHobby" />
                  </td>
                </tr>

                <tr>
                  <td class="lblFrmUndLn">
                    <u:translation key="label.sogno"/>
                  </td>
                  <td>
                    <s:textfield name="userPlayerToUpdate.infoDream" maxLength="35" cssStyle="width:200px;" />
                    <s:fielderror name="errorDream" fieldName="userPlayerToUpdate.infoDream" />

                  </td>
                </tr>



                <tr>
                  <td>
                    <u:translation key="label.annuncio"/>
                  </td>
                  <td>
                    <s:textarea name="userPlayerToUpdate.infoAnnounce" maxLength="240"  rows="6" cols=""  cssStyle="width:200px;" onkeyup="countStop(this,240,'idShowChars');" id="idUserPlayerToUpdate"/>
                    <s:fielderror name="errorAnnounce" fieldName="userPlayerToUpdate.infoAnnounce" />
                    <span id="idShowChars" class="light" >240</span>
                    <br />
                     <small class="light">
                       <u:translation key="label.userPlayerInfoAnnuncio"/>
                   </small>

                  </td>
                </tr>
               
 
                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <p style="padding-top:20px;">
                      <a  href="javascript: void(0);" onclick="$('userPlayerForm').submit(); return false;" class="btn action1" >
                        <u:translation key="btn.save"/>
                      </a>

                      &nbsp;&nbsp;

                      <%--a href="#<s:url action="home"/>" class="btn action2" ><u:translation key="label.Anteprima" /></a--%>
                    
                    </p>
                  </td>
                </tr>
              </table>
            </div>

            <div class="schedaPic">

              <s:url action="pictureCard!viewUserPlayerPictureCard" id="viewPictureCardURL" >
                <s:param name="idUser" value="userPlayerToUpdate.id"></s:param>
              </s:url>
              <a  href="javascript: openPopupUserDetails('<s:property value="#userDetailsURL" escape="false"/>','${nameDet}');"><img  src="<s:property value="#viewPictureCardURL"/>" /></a>
              <p>
                <s:property value="labelPicStatus" />
              </p>

              <p>
                <s:url action="pictureCard" var="userUploadPictureUrl" namespace="" method="newImage"></s:url>
                <a  href="${userUploadPictureUrl}"  ><u:translation key="label.newPicture"/></a>

                <s:if test="picStatus!='UNDEFINED'" >

                  <s:url action="pictureCard" var="userRemovePictureUrl" namespace="" method="remove"></s:url>
                  <a  href="${userRemovePictureUrl}" onclick="return confirm('<u:translation key="message.removePicture"/>');" ><u:translation key="label.removePicture"/></a>


                </s:if>
              </p>

            </div>
            <br class="clear" />  <br class="clear" />

          </s:form>

        </div>
        <!--### end maincontent ###-->



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
