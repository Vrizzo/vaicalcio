package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.SportCenterInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.providers.TranslationProvider;
import it.newmedia.social.facebook.client.FacebookWsClient;
import it.newmedia.social.ws.*;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class FacebookManager
{
  private static Logger logger = Logger.getLogger(UserCommentManager.class);

  public static void postOnMatchCreation(int idMatch)
  {
    try
    {
      MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
      UserInfo userInfoOwner = InfoProvider.getUserInfo(matchInfo.getIdUserOwner());
      //se l'owner non è un utente facebook posso terminare
      if (!userInfoOwner.isFacebookUser())
      {
        return;
      }
      //se l'owner non ha dato il consenso per questo post esco
      if (!userInfoOwner.isFacebookPostOnMatchCreation())
      {
        return;
      }
      Language currentLanguage = UserContext.getInstance().getLanguage();
      Cobrand currentCobrand = UserContext.getInstance().getCurrentCobrand();
      SportCenterInfo sportCenterInfo = InfoProvider.getSportCenterInfo(matchInfo.getIdSportCenter());

      WritePostRequestData post = buildCommonPost(matchInfo, userInfoOwner, currentLanguage, currentCobrand);
      post.setLink(currentCobrand.getGatewayUrl() + "/matchComments!viewAll.action?idMatch=" + matchInfo.getId());
      post.setMessage(TranslationProvider.getTranslationValue("facebook.postOnMatchCreation.message_calcio", currentLanguage, currentCobrand));


      execWritePosts(post);
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
  }

  public static void postOnMatchRegistration(int idMatch, int idUser)
  {
    try
    {
      MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
      UserInfo userInfo = InfoProvider.getUserInfo(idUser);
      //se l'owner non è un utente facebook posso terminare
      if (!userInfo.isFacebookUser())
      {
        return;
      }
      //se l'utente non ha dato il consenso per questo post esco
      if (!userInfo.isFacebookPostOnMatchRegistration())
      {
        return;
      }
      Language currentLanguage = UserContext.getInstance().getLanguage();
      Cobrand currentCobrand = UserContext.getInstance().getCurrentCobrand();

      WritePostRequestData post = buildCommonPost(matchInfo, userInfo, currentLanguage, currentCobrand);
      post.setLink(currentCobrand.getGatewayUrl() + "/viewMatch.action?idMatch=" + matchInfo.getId());
      post.setMessage(TranslationProvider.getTranslationValue("facebook.postOnMatchRegistration.message_calcio", currentLanguage, currentCobrand));


      execWritePosts(post);
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
  }

  public static void postOnMatchRecorder(int idMatch)
  {
    try
    {
      MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
      Language currentLanguage = UserContext.getInstance().getLanguage();
      Cobrand currentCobrand = UserContext.getInstance().getCurrentCobrand();

      //creo la lista dei post
      List<WritePostRequestData> postList = new ArrayList<WritePostRequestData>();

      for (Player player : matchInfo.getPlayerList())
      {
        switch (player.getEnumPlayerStatus())
        {
          case Out:
          case Undefined:
          case UserRequest:
          case UserCalled:
            //Non iscritti alla partita o non utenti gokick
            continue;
          case OwnerRegistered:
          case UserRegistered:
          case UserRequestRegistered:
            break;
        }
        //se l'utente non è un utente facebook posso terminare
        if (!player.getUser().isFacebookUser())
        {
          continue;
        }
        //se l'utente non ha dato il consenso per questo post esco
        if (!player.getUser().isFacebookPostOnMatchRecorded())
        {
          continue;
        }
        UserInfo userInfo = InfoProvider.getUserInfo(player.getUser().getId());
        WritePostRequestData post = buildCommonPost(matchInfo, userInfo, currentLanguage, currentCobrand);
        post.setLink(currentCobrand.getGatewayUrl() + "/archiveMatch!report.action?idMatch=" + matchInfo.getId());
        post.setMessage(TranslationProvider.getTranslationValue("facebook.postOnMatchRecorded.message_calcio", currentLanguage, currentCobrand));
        StringBuilder sbDescription = new StringBuilder(500);

        String vote = TranslationProvider.getTranslationValue("label.SenzaVoto", currentLanguage, currentCobrand);
        if(player.getVote().compareTo(BigDecimal.ZERO) > 0)
        {
          NumberFormat nf = new DecimalFormat("#.#");
          vote = nf.format(player.getVote().doubleValue());
        }
        sbDescription.append("[ ")
                     .append(TranslationProvider.getTranslationValue("label.Voto", currentLanguage, currentCobrand))
                     .append(": ")
                     .append(vote)
                     .append(" - ")
                     .append(TranslationProvider.getTranslationValue("label.Goal", currentLanguage, currentCobrand))
                     .append(": ")
                     .append(player.getGoals())
                     .append(" ] ")
                     .append(player.getReview());
        post.setDescription(sbDescription.toString());
        postList.add(post);
      }
      execWritePosts(postList);
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
  }

  private static WritePostRequestData buildCommonPost(MatchInfo matchInfo, UserInfo userInfo, Language currentLanguage, Cobrand currentCobrand)
  {
    SportCenterInfo sportCenterInfo = InfoProvider.getSportCenterInfo(matchInfo.getIdSportCenter());

    WritePostRequestData post = new WritePostRequestData();
    //setto i dati di accesso per poter scrivere
    post.setIdUser(userInfo.getFacebookIdUser());
    post.setAccessToken(userInfo.getFacebookAccessToken());

    post.setName(String.format("%s %s - %s", "Calcio", matchInfo.getMatchTypeName(), TranslationProvider.getTranslationValue("facebook.postSiteName",currentLanguage, currentCobrand)));
    post.setCaption(String.format("%s - %s - %s", sportCenterInfo.getCityName(), DateManager.showDate(matchInfo.getMatchStart(), "format.date_20"), matchInfo.getSportCenterName()));
    post.setDescription(matchInfo.getNotes());
    post.setPicture(TranslationProvider.getTranslationValue("facebook.postOnMatchCreation.picture_" + "calcio", currentLanguage, currentCobrand));

    return post;
  }

  private static void execWritePosts(WritePostRequestData post)
  {
    List<WritePostRequestData> postList = new ArrayList<WritePostRequestData>();
    postList.add(post);
    execWritePosts(postList);
  }

  private static void execWritePosts(List<WritePostRequestData> posts)
  {
    final String webServiceUrlFinal = AppContext.getInstance().getFacebookWebServiceUrl();
    final int webServiceTimeoutFinal = AppContext.getInstance().getFacebookWebServiceTimeout();
    final String webServiceUsernameFinal = AppContext.getInstance().getFacebookWebServiceUsername();
    final String webServicePasswordFinal = AppContext.getInstance().getFacebookWebServicePassword();
    final List<WritePostRequestData> postsFinal = posts;
    Runnable runnable = new Runnable()
    {
      @Override
      public void run()
      {
        //Creo la richiesta per il ws
        WritePostRequest writePostRequest = FacebookWsClient.buildWritePostRequest(webServiceUsernameFinal, webServicePasswordFinal);
        for (WritePostRequestData post : postsFinal)
        {
          writePostRequest.getPosts().add(post);
        }
        //Creo il client ws
        FacebookWsClient wsClient = new FacebookWsClient(webServiceUrlFinal, webServiceTimeoutFinal);
        //scrivo il post
        WritePostResponse writePostResponse = wsClient.writePosts(writePostRequest);
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("Facebook writePosts result: ").append(writePostResponse.isSuccess()).append(SystemUtils.LINE_SEPARATOR);
        for (WritePostResponseData data : writePostResponse.getPosts())
        {
          stringBuilder.append("  idUser: ").append(data.getIdUser()).append("; result: ").append(data.isSuccess()).append(SystemUtils.LINE_SEPARATOR);
          if (data.isSuccess())
          {
            stringBuilder.append("     idPost: ").append(data.getIdPost()).append(SystemUtils.LINE_SEPARATOR);
          }
          else
          {
            stringBuilder.append("     ErrorMessage: ").append(data.getErrorMessage()).append(SystemUtils.LINE_SEPARATOR);
          }
        }
        logger.info(stringBuilder.toString());
      }
    };
    runnable.run();
  }
}
