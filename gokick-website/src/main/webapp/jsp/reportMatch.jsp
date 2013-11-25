<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<%@taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="utl" uri="/WEB-INF/taglib/utl.tld" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <jsp:include page="../jspinc/commonHead.jsp" flush="true" />
    <title><s:property value="%{getText('page.title.default')}" /></title>

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
        <div class="topPageWrap">
          <jsp:include page="../jspinc/headerTopBar.jsp" flush="true" />
        </div>

      <!--### start mainContent ###-->
        <div class="mainContentIndent">


          <h1><u:translation key="label.Partita" /></h1>

        <div class="contTab">
            <p class="left" style="width:500px;">
              <utl:formatDate   date="${currentMatch.matchStart}" formatKey="format.date_7" /> -
              <s:property value="currentMatch.sportCenter.name" />
           </p>

        </div>
        
         <!-- matchsubMenu-->
          <jsp:include page="../jspinc/matchMenu.jsp" flush="true">
            <jsp:param name="activePage" value="archiveMatch"/>
          </jsp:include>
          <!-- matchsubMenu-->


          <p class="left" style="padding-top:30px;">
            <u:translation key="label.pagellelACuraDi" /> <u:printUserNameBase user="${reporter}" showAvatar="false" displayVertical="false" />
          </p>
          <p class="right" style="padding-top:30px; padding-right:10px;">
            [<a class="showHideBoxLink" href=" javascript: showHideReportPlayerReview('.playerReview','playerReviewShowLabel','playerReviewHideLabel')"><span id="playerReviewShowLabel" style="display: none;"><u:translation key="label.reportMostraCommenti" /></span><span id="playerReviewHideLabel" ><u:translation key="label.reportNascondiCommenti" /></span></a>]
          </p>

          <div class="clear"></div>



        </div>

        <div class="mainContent">

          <s:hidden name="idMatch" value="%{idMatch}" />



          <div class="teamColumn left">
            <div class="teamInfoReportMatchDetail">

              <h2><span class="left"><s:property value="teamOneName" /></span> <span class="right"><s:property value="teamOneGoals" /></span><br class="clear" /></h2>
              <div class="teamMainInfo">
                <s:if test="teamOneShirt!=''">
                  <u:translation key="label.Maglia" /> <s:property value="teamOneShirt" />
                </s:if>
                <s:else>
                  <br/>
                </s:else>
              </div>


              <s:iterator value="teamOnePlayerList" var="teamOnePlayer" status="stat">
                <s:if test="#teamOnePlayer.user.id == currentUser.id">
                  <s:set name="currentUserClass">reportCurrentUser</s:set>
                </s:if>
                <s:else>
                  <s:set name="currentUserClass"></s:set>
                </s:else>
                <div class="teamInfoPlayerDetail ${currentUserClass} ${stat.count==1?'firstPlayer':''}">
                  <s:set value="#stat.count" id="count"/>
                  <s:set id="index">${count-1}</s:set>
                  <div class="playerHead">
                    <div class="left">
                      <s:property value="#stat.count" />
                    </div>
                    <div class="right">

                      <table class="playerResultInfo">
                        <tr>
                          <s:url action="viewAvatar" var="viewAvatarURL">
                            <s:param name="idUser">${teamOnePlayer.user.id}</s:param>
                          </s:url>
                          <s:if test="#teamOnePlayer.user != null">
                            <td class="pic">
                              <img src="${viewAvatarURL}" alt="" />
                            </td>
                            <td>
                              <u:printUserNameBase user="${teamOnePlayer.user}" vert="true" />
                              <span class="role"><u:translationSubStr key="${teamOnePlayer.playerRole.keyName}" length="3" /></span>
                            </td>
                          </s:if>
                          <s:else>
                            <td class="pic">
                              <img src="${viewAvatarURL}" alt="" />
                            </td>
                            <td>
                              <u:printSquadOut player="${teamOnePlayer}" showAvatar="false" />
                              <span class="role"><u:translationSubStr key="${teamOnePlayer.playerRole.keyName}" length="3" /></span>
                            </td>
                          </s:else>

                          <td class="playerScore">

                            <s:if test="#teamOnePlayer.goals > 0"> <s:property value="#teamOnePlayer.goals" /> <u:translation key="label.Goal"/> <br /> </s:if>
                            <s:if test="#teamOnePlayer.ownGoals > 0"> <s:property value="#teamOnePlayer.ownGoals" /> <u:translation key="label.Autogoal" /> </s:if>

                            <%--p>
                              <s:if test="#teamOnePlayer.user != null">
                                <img src="images/<s:property value="countryFlagImagesPrefix" /><s:property value="#teamOnePlayer.user.country.id" />.gif" />
                              </s:if>
                            </p--%>
                          </td>
                          <td class="playerVote">
                            <s:if test="#teamOnePlayer.vote < 0">
                              <u:translation key="label.SenzaVoto" />
                            </s:if>
                            <s:else>
                              <%--s:property value="#teamOnePlayer.vote" /--%>
                              <s:set var="voteone" value="#teamOnePlayer.vote" />
                              <fmt:formatNumber value="${voteone}" pattern="#.#" />
                            </s:else>
                          </td>
                        </tr>
                      </table>

                    </div>


                    <div style="margin:0; padding:0; height:0;" class="clear">&nbsp;</div>
                  </div>

                  <div class="playerReview">
                    <s:property value="#teamOnePlayer.review" />
                  </div>


                </div>
              </s:iterator>

               <br/><br/>

              <div class="averageRating">
                <table>
                  <tr>
                    <td><u:translation key="label.reportEtaMedia" /> </td><td class="right">
                      <s:set var="oneAVGAge" value="teamOneAVGAge" />
                      <fmt:formatNumber value="${oneAVGAge}" pattern="#.#" />
                    </td>
                  </tr>
                  <tr>
                    <td style="border:0;"><strong><u:translation key="label.reportMediaVoto" /></strong></td> <td class="right" style="border:0;">
                      <strong>
                        <s:set var="oneAVGVote" value="teamOneAVGVote" />
                        <fmt:formatNumber value="${oneAVGVote}" pattern="#.#" />
                      </strong></td>
                  </tr>
                </table>
                <%--small>
                  <s:property value="teamOnePlayerList.size" /> iscritti
                  <u:printPlayersRolesCount playersRolesCount="${teamOneRoles}" all="false" />
                </small--%>
              </div>

            </div>

          </div>

          <div class="teamColumn right">
            <div class="teamInfoReportMatchDetail">
              <h2><span class="left"><s:property value="teamTwoName" /></span> <span class="right"><s:property value="teamTwoGoals" /></span> <br class="clear" /></h2>
              <div class="teamMainInfo">
                <s:if test="teamTwoShirt!=''">
                  <u:translation key="label.Maglia" /> <s:property value="teamTwoShirt" />
                </s:if>
                <s:else>
                  <br/>
                </s:else>
              </div>

              <s:iterator value="teamTwoPlayerList" var="teamTwoPlayer" status="stat">
                <s:if test="#teamTwoPlayer.user.id == currentUser.id">
                  <s:set name="currentUserClass">reportCurrentUser</s:set>
                </s:if>
                <s:else>
                  <s:set name="currentUserClass"></s:set>
                </s:else>
                <div class="teamInfoPlayerDetail ${currentUserClass} ${stat.count==1?'firstPlayer':''}">
                  <s:set value="#stat.count" id="count"/>
                  <s:set id="index">${count-1}</s:set>
                  <div class="playerHead">
                    <div class="left">
                      <s:property value="#stat.count" />
                    </div>

                    <div class="right">
                      <table class="playerResultInfo">
                        <tr>
                          <s:url action="viewAvatar" var="viewAvatarURL">
                            <s:param name="idUser">${teamTwoPlayer.user.id}</s:param>
                          </s:url>
                          <s:if test="#teamTwoPlayer.user != null">
                            <td class="pic">
                              <img src="${viewAvatarURL}" alt="" />
                            </td>
                            <td>
                              <u:printUserNameBase user="${teamTwoPlayer.user}" vert="true" />
                              <span class="role"><u:translationSubStr key="${teamTwoPlayer.playerRole.keyName}" length="3" /></span>
                            </td>
                          </s:if>
                          <s:else>
                            <td class="pic">
                              <img src="${viewAvatarURL}" alt="" />
                            </td>
                            <td>
                              <u:printSquadOut player="${teamTwoPlayer}" showAvatar="false" />
                              <span class="role"><u:translationSubStr key="${teamTwoPlayer.playerRole.keyName}" length="3" /></span>
                            </td>
                          </s:else>

                          <td class="playerScore">
                            <s:if test="#teamTwoPlayer.goals > 0"><s:property value="#teamTwoPlayer.goals" />  <u:translation key="label.Goal"/><br /></s:if>
                            <s:if test="#teamTwoPlayer.ownGoals > 0"><s:property value="#teamTwoPlayer.ownGoals" /> <u:translation key="label.Autogoal" /></s:if>

                            <%--p>
                              <s:if test="#teamTwoPlayer.user != null">
                                <img src="images/<s:property value="countryFlagImagesPrefix" /><s:property value="#teamTwoPlayer.user.country.id" />.gif" />
                              </s:if>
                            </p--%>
                          </td>
                          <td class="playerVote">
                            <s:if test="#teamTwoPlayer.vote < 0">
                              <u:translation key="label.SenzaVoto" />
                            </s:if>
                            <s:else>
                              <%--s:property value="#teamTwoPlayer.vote" /--%>
                              <s:set var="votetwo" value="#teamTwoPlayer.vote" />
                              <fmt:formatNumber value="${votetwo}" pattern="#.#" />
                            </s:else>
                          </td>
                        </tr>

                      </table>
                    </div>

                    <div style="margin:0; padding:0; height:0;" class="clear">&nbsp;</div>
                  </div>

                  <div class="playerReview">
                    <s:property value="#teamTwoPlayer.review" />
                  </div>

                </div>
              </s:iterator>

                  <br/><br/>

              <div class="averageRating">
                <table>
                  <tr>
                    <td><u:translation key="label.reportEtaMedia" />
                    </td>
                    <td class="right">
                      <s:set var="twoAVGAge" value="teamTwoAVGAge" />
                      <fmt:formatNumber value="${twoAVGAge}" pattern="#.#" />
                    </td>
                  </tr>
                  <tr>
                    <td style="border:0;"><strong><u:translation key="label.reportMediaVoto" />
                      </strong> </td><td class="right" style="border:0;">
                      <strong>
                        <s:set var="twoAVGVote" value="teamTwoAVGVote" />
                        <fmt:formatNumber value="${twoAVGVote}" pattern="#.#" />
                      </strong>
                    </td>
                  </tr>
                </table>
                <%--small>
                  <s:property value="teamTwoPlayerList.size" /> iscritti
                  <u:printPlayersRolesCount playersRolesCount="${teamTwoRoles}" all="false" />
                </small--%>


              </div>

            </div>

          </div>

          <br class="clear" />
         
          <div class="boxReview">
            <h3>
              <strong><u:translation key="label.Recensione" /></strong>
            </h3>

            <p>
              <s:property value="comment" />
            </p>
          </div>

          <s:if test="editMatch">
            <p class="centred">
              <span class="light">
              <s:url action="archiveMatch" method="input" var="editUrl">
                <s:param name="idMatch">${idMatch}</s:param>
              </s:url>
              <u:translation key="label.reportModificaTabellino" />
              </span>
              <strong>
              <s:property value="limitEditDateString" />
              </strong>
              [<a class="light" href="${editUrl}" class="showHideBoxLink"><u:translation key="label.modify" /></a>]
            </p>
          </s:if>

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
