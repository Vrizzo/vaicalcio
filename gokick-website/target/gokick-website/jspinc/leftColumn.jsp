<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<div class="leftCol">

  <div class="wrapIcon">
    <u:loggedOut>
      <u:translation key="label.boxAltoSinistra"/>
    </u:loggedOut>
    <u:loggedIn>
      <div class="fixIcon">
        <s:url action="viewAvatar" var="imageUrl">
          <s:param name="idUser">${currentUserInfo.id}</s:param>
        </s:url>
        <a href="<s:url action="userPlayer!input"/>"><img class="avatar" src="${imageUrl}" alt=""/></a>
      </div>
    </u:loggedIn>
  </div>

  <u:loggedIn>

    <div class="columnMenu">
      <a href="<s:url action='calendar!viewCalendar' ><s:param name='defaultSearch' value='true' /></s:url>" class="${param["tab"] eq "play" ? "active" : "" }"><u:translation key="label.Gioca"/></a>
      <u:isOrganizer>
        <a href="<s:url action="organizeMatchCreate" method="input"/>" class="${param["tab"] eq "organize" ? "active" : "" }"><u:translation key="label.Organizza"/></a>
      </u:isOrganizer>
      <u:isNotOrganizer>
        <a href="<s:url action="organizeMatchWelcome" />" class="${param["tab"] eq "organize" ? "active" : "" }"><u:translation key="label.Organizza"/></a>
      </u:isNotOrganizer>
      <a href="<s:url action="results!viewResults" />" class="${param["tab"] eq "results" ? "active" : "" }"><u:translation key="label.Risultati"/></a>
    </div>

    <div class="columnMenu">
      <a href="<s:url action="squad"/>" class="${param["tab"] eq "squad" ? "active" : "" }"><u:translation key="label._Amici"/></a>

      <a class="${param["tab"] eq "gokickers" ? "active" : "" }" href="<s:url action="userAll" method="viewAll"/>"><u:translation key="label.GoKickers"/></a>

      <a class="${param["tab"] eq "sportCenters" ? "active" : "" }" href="<s:url action="sportCenterAll!viewAll"/>"><u:translation key="label.sportCenterCampi"/></a>
    </div>

    <u:showForCobrands codes="GOKICK">
      <div class="columnMenu">
        <a class="${param["tab"] eq "home" ? "active" : "" }" href="<s:url action="home"/>"><u:translation key="label.news"/></a>
        <a href="${currentCobrand.siteUrl}/blog?tab=blog"><u:translation key="label.blog"/></a>
        <a href="${currentCobrand.siteUrl}/forum?tab=forum"><u:translation key="label.Forum"/></a>
      </div>

      <div class="columnMenu">
        <a href="<s:url action="playmoreInfo"/>" class="${param["tab"] eq "history" ? "active" : "" }"><u:translation key="label.PlayMore"/>!</a>
        <a target="_blank" href="http://www.flickr.com/photos/gokick/sets/"><u:translation key="label.Foto"/></a>
        <a target="_blank" href="http://www.youtube.com/user/GoKickCommunity"><u:translation key="label.Video"/></a>
      </div>
    </u:showForCobrands>

    <u:showForCobrands codes="METRONEWS">
      <div class="columnMenu">
        <a class="${param["tab"] eq "home" ? "active" : "" }" href="<s:url action="home"/>"><u:translation key="label.news"/></a>
        <a href="<s:url action="playmoreInfo"/>" class="${param["tab"] eq "history" ? "active" : "" }"><u:translation key="label.PlayMore"/>!</a>
        <a target="_blank" href="http://www.flickr.com/photos/gokick/sets/"><u:translation key="label.Foto"/></a>
        <a target="_blank" href="http://www.youtube.com/user/GoKickCommunity"><u:translation key="label.Video"/></a>
      </div>
    </u:showForCobrands>

    <u:showForCobrands codes="PROUSALL">
      <div class="columnMenu">
        <a class="${param["tab"] eq "home" ? "active" : "" }" href="<s:url action="home"/>"><u:translation key="label.news"/></a>
        <a href="<s:url action="playmoreInfo"/>" class="${param["tab"] eq "history" ? "active" : "" }"><u:translation key="label.PlayMore"/>!</a>
        <a target="_blank" href="http://www.flickr.com/photos/gokick/sets/"><u:translation key="label.Foto"/></a>
        <a target="_blank" href="http://www.youtube.com/user/GoKickCommunity"><u:translation key="label.Video"/></a>
      </div>
    </u:showForCobrands>

    <u:showForCobrands codes="MOSCOVA">
      <div class="columnMenu">
        <a class="${param["tab"] eq "home" ? "active" : "" }" href="<s:url action="home"/>"><u:translation key="label.news"/></a>
        <a href="<s:url action="playmoreInfo"/>" class="${param["tab"] eq "history" ? "active" : "" }"><u:translation key="label.PlayMore"/>!</a>
        <a target="_blank" href="http://www.flickr.com/photos/gokick/sets/"><u:translation key="label.Foto"/></a>
        <a target="_blank" href="http://www.youtube.com/user/GoKickCommunity"><u:translation key="label.Video"/></a>
      </div>
    </u:showForCobrands>



  </u:loggedIn>

  <u:loggedOut>
    <u:showForCobrands codes="GOKICK">
      <div class="columnMenu">
        <a href="<s:url action='userIntro' ><s:param name='inviteKey'>HOMEREGISTER</s:param></s:url>" class="${param["tab"] eq "register" ? "active" : "" }"><u:translation key="label.Registrati"/></a>
      </div>
      
      <div class="columnMenu">
        <a href="${currentCobrand.siteUrl}/campi?tab=campi"><u:translation key="label.sportCenterCampi"/></a>
        <a href="${currentCobrand.siteUrl}/blog?tab=blog"><u:translation key="label.blog"/></a>
        <a href="${currentCobrand.siteUrl}/forum?tab=forum"><u:translation key="label.Forum"/></a>
        <a target="_blank" href="http://www.flickr.com/photos/gokick/sets/"><u:translation key="label.Foto"/></a>
        <a target="_blank" href="http://www.youtube.com/user/GoKickCommunity"><u:translation key="label.Video"/></a>
      </div>
      
      <div class="columnMenu">
        <a href="<s:url action="playmoreInfo"/>" class="${param["tab"] eq "history" ? "active" : "" }"><u:translation key="label.PlayMore"/>!</a>
      </div>
    </u:showForCobrands>

    <u:showForCobrands codes="METRONEWS, PROUSALL, MOSCOVA">
      <div class="columnMenu">
        <a href="<s:url action='home' />" class="${param["tab"] eq "home" ? "active" : "" }"><u:translation key="label.Home"/></a>
        <a href="<s:url action='userIntro' ><s:param name='inviteKey'>HOMEREGISTER</s:param></s:url>" class="${param["tab"] eq "register" ? "active" : "" }"><u:translation key="label.Registrati"/></a>
      </div>
      
      <div class="columnMenu">
        <a href="${currentCobrand.siteUrl}/campi?tab=campi"><u:translation key="label.sportCenterCampi"/></a>
        <a target="_blank" href="http://www.flickr.com/photos/gokick/sets/"><u:translation key="label.Foto"/></a>
        <a target="_blank" href="http://www.youtube.com/user/GoKickCommunity"><u:translation key="label.Video"/></a>
      </div>
      
      <div class="columnMenu">
        <a href="<s:url action="playmoreInfo"/>" class="${param["tab"] eq "history" ? "active" : "" }"><u:translation key="label.PlayMore"/>!</a>
      </div>
    </u:showForCobrands>



    


  </u:loggedOut>

</div>