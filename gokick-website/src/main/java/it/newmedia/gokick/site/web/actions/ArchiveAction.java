package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiPlayerVote;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.PlayerRoleInfo;
import it.newmedia.gokick.site.managers.*;
import it.newmedia.utils.MathUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * Classe contenente le azioni per archiviazione (da parte dell'organizzatore/pagellatore) e la visualizzazione di partite e sfide da parte degli utenti secondo i permessi
 */
public class ArchiveAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public final static String ACTION_CODE__MOVE_UP = "moveup";

  public final static String ACTION_CODE__MOVE_DOWN = "movedown";

  public final static String ACTION_CODE__MOVE_RIGHT = "moveright";

  public final static String ACTION_CODE__MOVE_LEFT = "moveleft";

  public final static String ACTION_CODE__REMOVE = "remove";

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idMatch;

  private boolean recorded;

  private int idPlayer;

  private String actionCode;

  private boolean missing;

  private boolean saveDraft;

  private boolean editMatch;

  private boolean viewArchivSchema;

  private int countPost;

  private String countryFlagImagesPrefix;

  private int playersTeamOneToAdd;

  private String limitEditDateString;

  private Match currentMatch;

  private String teamOneName;

  private String teamOneGoals;

  private String teamOneShirt;

  private String teamTwoName;

  private String teamTwoGoals;

  private String teamTwoShirt;

  private List<Player> teamOnePlayerList;

  private List<Player> teamTwoPlayerList;

  private String comment;

  private List golNumberList;

  private List<PlayerRoleInfo> playerRoleInfoList;

  private List<GuiPlayerVote> playerVoteList;

  private List playerGolList;

  private List playerOwnGol;

  private BigDecimal teamOneAVGVote;

  private BigDecimal teamTwoAVGVote;

  private BigDecimal teamOneAVGAge;

  private BigDecimal teamTwoAVGAge;

  private List teamOneRoles;

  private List teamTwoRoles;

  private User reporter;

  private boolean validate;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public void prepare()
  {
    NumberFormat nf = new DecimalFormat("#.#");
    this.golNumberList = new ArrayList();
    for (int i = 0; i <= Constants.MAX_GOALS; i++)
    {
      this.golNumberList.add(i);
    }
    this.playerRoleInfoList = AppContext.getInstance().getAllPlayerRoleInfo(getCurrentObjLanguage(), getCurrentCobrand());
    this.playerVoteList = new ArrayList();
    this.playerVoteList.add(new GuiPlayerVote(GuiPlayerVote.ID_VOTE_NOT_ASSIGNED, ""));
    BigDecimal vote = new BigDecimal(10.5);
    while (vote.compareTo(new BigDecimal(1.0)) != 0)
    {
      vote = vote.subtract(BigDecimal.valueOf(0.5));
      this.playerVoteList.add(new GuiPlayerVote(vote.toString(), nf.format(vote.doubleValue())));
    }
    this.playerVoteList.add(new GuiPlayerVote(GuiPlayerVote.ID_WITHOUT_VOTE, getText("label.player.withoutVote")));

    this.playerGolList = new ArrayList();
    for (int i = 0; i <= Constants.MAX_GOALS; i++)
    {
      this.playerGolList.add(i);
    }

    this.playerOwnGol = new ArrayList();
    for (int i = 0; i <= Constants.MAX_GOALS; i++)
    {
      this.playerOwnGol.add(i);
    }
  }

  @Override
  public String input()
  {
    if (!editPermission())
    {
      return Constants.STRUTS_RESULT_NAME__INFO;
    }

    this.countPost = GuiManager.countCommentsByMatch(this.idMatch);
    
    if (!viewArchivSchema)
      return INPUT;

    if (this.currentMatch.getTeamNameOne() == null || this.currentMatch.getTeamNameOne().equals(""))
      this.teamOneName = getText("label.nomeDefaultSquadraUno");
    else
      this.teamOneName = this.currentMatch.getTeamNameOne();

    //if (StringUtils.isBlank(this.teamOneGoals))
      this.teamOneGoals = this.currentMatch.getTeamGoalsOne();

    this.teamOneShirt = this.currentMatch.getTeamShirtOne();
    if (this.currentMatch.getTeamNameTwo() == null || this.currentMatch.getTeamNameTwo().equals(""))
      this.teamTwoName = getText("label.nomeDefaultSquadraDue");
    else
      this.teamTwoName = this.currentMatch.getTeamNameTwo();

    //if (StringUtils.isBlank(this.teamTwoGoals))
      this.teamTwoGoals = this.currentMatch.getTeamGoalsTwo();

    this.teamTwoShirt = this.currentMatch.getTeamShirtTwo();
    this.comment = this.currentMatch.getComment();

    this.teamOnePlayerList = new ArrayList<Player>();
    this.teamTwoPlayerList = new ArrayList<Player>();
    int countListOne = 0;
    int countListTwo = 0;
    for (Player player : this.currentMatch.getPlayerList())
    {
      if (player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequest) ||
             player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserCalled)  )  //se è in userRequest o convocato non va visualizzato
      {
        continue;
      }

      if (player.getVote() != null)
      {
        player.setVote(MathUtil.divide(player.getVote(), 1, RoundingMode.HALF_UP, 1));
      }

      if (player.getEnumPlayerType().equals(EnumPlayerType.TeamOne))
      {
        if (player.getPosition() <= 0)
        {
          player.setPosition(countListOne);
        }
        countListOne++;
        this.teamOnePlayerList.add(player);
      }
      else if (player.getEnumPlayerType().equals(EnumPlayerType.TeamTwo))
      {
        if (player.getPosition() <= 0)
        {
          player.setPosition(countListTwo);
        }
        countListTwo++;
        this.teamTwoPlayerList.add(player);
      }
    }
    this.playersTeamOneToAdd = this.currentMatch.getMaxPlayers() - (this.teamOnePlayerList.size() + this.teamTwoPlayerList.size());

    //Ordino length due liste per posizione
    Collections.sort(this.teamOnePlayerList, Player.POSITION_ORDER);
    Collections.sort(this.teamTwoPlayerList, Player.POSITION_ORDER);

    this.recorded = this.currentMatch.getRecorded();

    return INPUT;
  }

  public void validateArchive()
  {
    if (!editPermission())
    {
      return;
    }

    if (validate){

    if (this.actionCode.equals(Constants.STRING_EMPTY))
    {
      if (this.teamOneName.equalsIgnoreCase(Constants.STRING_EMPTY))
      {
        addActionError(getText("error.teamOneName.required"));
      }
      if (this.teamTwoName.equalsIgnoreCase(Constants.STRING_EMPTY))
      {
        addActionError(getText("error.teamTwoName.required"));
      }
      if (this.teamOneGoals.equalsIgnoreCase(Constants.STRING_EMPTY))
      {
        addActionError(getText("error.teamOneGoals.required"));
      }
      if (this.teamTwoGoals.equalsIgnoreCase(Constants.STRING_EMPTY))
      {
        addActionError(getText("error.teamTwoGoals.required"));
      }
    }
    else
    {
      return;
    }

    if (this.teamOnePlayerList != null && this.teamOnePlayerList.size() > 0)
    {
      for (Player player : this.teamOnePlayerList)
      {
        if (player.getVote().toString().equals(GuiPlayerVote.ID_VOTE_NOT_ASSIGNED))
        {
          if (player.getUser() != null)
          {
            addActionError(String.format(getText("error.player.vote.required"), player.getUser().getFirstName() + " " + player.getUser().getLastName()));
          }
          else
          {
            addActionError(String.format(getText("error.player.vote.required"), player.getOutFirstName() + " " + player.getOutLastName()));
          }
        }
      }
    }

    if (this.teamTwoPlayerList != null && this.teamTwoPlayerList.size() > 0)
    {
      for (Player player : this.teamTwoPlayerList)
      {
        if (player.getVote().toString().equals(GuiPlayerVote.ID_VOTE_NOT_ASSIGNED))
        {
          if (player.getUser() != null)
          {
            addActionError(String.format(getText("error.player.vote.required"), player.getUser().getFirstName() + " " + player.getUser().getLastName()));
          }
          else
          {
            addActionError(String.format(getText("error.player.vote.required"), player.getOutFirstName() + " " + player.getOutLastName()));
          }
        }
      }
    }

    this.countPost = GuiManager.countCommentsByMatch(this.idMatch);
    if (this.hasErrors())
    {
      if (!this.currentMatch.getRecorded()) this.recorded=false;
    }
    }
  }

  public String archive()
  {
    if (!editPermission())
    {
      return Constants.STRUTS_RESULT_NAME__INFO;
    }
    boolean sendAdvice = false;
    if (this.recorded)
    {
      if (!this.currentMatch.getRecorded())
      {
        this.currentMatch.setRecorded(true);
        this.currentMatch.setRecordedDate(new Date());
        sendAdvice = true;
      }
      this.saveDraft = false;
    }
    else
    {
      this.saveDraft = true;
      sendAdvice = false;
    }

    this.countPost = GuiManager.countCommentsByMatch(this.idMatch);
    this.playersTeamOneToAdd = this.currentMatch.getMaxPlayers() - (this.teamOnePlayerList == null ? 0 : this.teamOnePlayerList.size()) + (this.teamTwoPlayerList == null ? 0 : this.teamTwoPlayerList.size());

    this.currentMatch.setTeamNameOne(this.teamOneName);
    //if (!this.saveDraft)
      this.currentMatch.setTeamGoalsOne(this.teamOneGoals.equals("") ? "0" : this.teamOneGoals);
    this.currentMatch.setTeamShirtOne(this.teamOneShirt);

    this.currentMatch.setTeamNameTwo(this.teamTwoName);
    //if (!this.saveDraft)
      this.currentMatch.setTeamGoalsTwo(this.teamTwoGoals.equals("") ? "0" : this.teamTwoGoals);
    this.currentMatch.setTeamShirtTwo(this.teamTwoShirt);

    this.currentMatch.setComment(StringUtils.left(this.comment, 5000));

    updatePlayers();

    boolean success = MatchManager.update(this.currentMatch, false, sendAdvice, getCurrentObjLanguage(), getCurrentCobrand());
    if (!success)
    {
      return ERROR;
    }

    if (this.saveDraft)
    {
      if (this.actionCode.isEmpty())
      {
        String message = getText("message.ArchiveBozzaSalvata");
        UserContext.getInstance().setLastMessage(message);
      }
      return Constants.STRUTS_RESULT_NAME__SAVE_DRAFT;
    }
    else
    {
      boolean calculate = StatisticManager.calculateStatistic(this.currentMatch.getId(), true, false, getCurrentCobrand());
      if (!calculate)
      {
        return ERROR;
      }
      String message = getText("message.ArchivePartitaArchiviata");
      UserContext.getInstance().setLastMessage(message);
      //eseguo i post su facebook solo la prima volta
      if( sendAdvice )
      {
        FacebookManager.postOnMatchRecorder(this.currentMatch.getId());
      }
      return Constants.STRUTS_RESULT_NAME__ARCHIVE_MATCH;
    }
  }
  
  public String report()
  {
    if (!viewPermission())
    {
      return Constants.STRUTS_RESULT_NAME__INFO;
    }

    
    Player reporterPlayer = MatchManager.getReporter(this.idMatch);
    if (reporterPlayer == null)
    {
      reporter = this.currentMatch.getUserOwner();
    }
    else
    {
      reporter = reporterPlayer.getUser();
    }

    this.countPost = GuiManager.countCommentsByMatch(this.idMatch);
    this.countryFlagImagesPrefix = Constants.COUNTRY_FLAG_IMAGE_PREFIX;

    this.editMatch = false;
    if ((this.currentMatch.getUserOwner().getId().equals(UserContext.getInstance().getUser().getId())) ||
            (MatchManager.isUserReporter(UserContext.getInstance().getUser().getId(), this.idMatch)))
    {
      Date limitEditDate = DateUtils.addDays(this.currentMatch.getRecordedDate(), AppContext.getInstance().getArchiveMatchEditableNumbersOfDays());
      if (limitEditDate.after(new Date()))
      {
        this.editMatch = true;
        this.limitEditDateString = DateManager.showDate(limitEditDate, DateManager.FORMAT_DATE_10);
      }
    }

    this.teamOneName = this.currentMatch.getTeamNameOne();
    this.teamOneGoals = this.currentMatch.getTeamGoalsOne();
    this.teamOneShirt = this.currentMatch.getTeamShirtOne();
    this.teamTwoName = this.currentMatch.getTeamNameTwo();
    this.teamTwoGoals = this.currentMatch.getTeamGoalsTwo();
    this.teamTwoShirt = this.currentMatch.getTeamShirtTwo();
    this.comment = this.currentMatch.getComment();

    this.teamOnePlayerList = new ArrayList<Player>();
    this.teamTwoPlayerList = new ArrayList<Player>();
    int countListOne = 0;
    int countListTwo = 0;
    int countListOneAge = 0;
    int countListTwoAge = 0;
    BigDecimal tmpTeamOneTotVote = new BigDecimal(0);
    BigDecimal tmpTeamTwoTotVote = new BigDecimal(0);
    BigDecimal tmpTeamOneTotAge = new BigDecimal(0);
    BigDecimal tmpTeamTwoTotAge = new BigDecimal(0);

    for (Player player : this.currentMatch.getPlayerList())
    {

      if (player.getEnumPlayerStatus() != EnumPlayerStatus.UserRequest &&
              player.getEnumPlayerStatus() != EnumPlayerStatus.UserCalled)
      {
        player.setVote(MathUtil.divide(player.getVote(), 1, RoundingMode.HALF_UP, 1));

        if (player.getEnumPlayerType().equals(EnumPlayerType.TeamOne))
        {
          if (player.getPosition() <= 0)
          {
            player.setPosition(countListOne);
          }

          if  (player.getVote().doubleValue()>0)
          {
            countListOne++;
            tmpTeamOneTotVote = tmpTeamOneTotVote.add(player.getVote());
          }

          if (player.getUser() != null && player.getUser().getBirthDay() != null)
          {
            tmpTeamOneTotAge = tmpTeamOneTotAge.add(BigDecimal.valueOf(calculateAge(player.getUser().getBirthDay())));
            countListOneAge++;
          }
          this.teamOnePlayerList.add(player);
        }
        else if (player.getEnumPlayerType().equals(EnumPlayerType.TeamTwo))
        {
          if (player.getPosition() <= 0)
          {
            player.setPosition(countListTwo);
          }

          if  (player.getVote().doubleValue()>0)
          {
            countListTwo++;
            tmpTeamTwoTotVote = tmpTeamTwoTotVote.add(player.getVote());
          }
          if (player.getUser() != null && player.getUser().getBirthDay() != null)
          {
            tmpTeamTwoTotAge = tmpTeamTwoTotAge.add(BigDecimal.valueOf(calculateAge(player.getUser().getBirthDay())));
            countListTwoAge++;
          }
          this.teamTwoPlayerList.add(player);
        }
      }
    }

    //Ordino lengthdue liste per posizione
    Collections.sort(this.teamOnePlayerList, Player.POSITION_ORDER);
    Collections.sort(this.teamTwoPlayerList, Player.POSITION_ORDER);

    if (countListOne <= 0)
    {
      this.teamOneAVGVote = new BigDecimal(0);
    }
    else
    {
      this.teamOneAVGVote = MathUtil.divide(tmpTeamOneTotVote, countListOne, RoundingMode.HALF_UP, 1);
    }
    if (countListOneAge <= 0)
    {
      this.teamOneAVGAge = new BigDecimal(0);
    }
    else
    {
      this.teamOneAVGAge = MathUtil.divide(tmpTeamOneTotAge, countListOneAge, RoundingMode.HALF_UP, 1);
    }

    if (countListTwo <= 0)
    {
      this.teamTwoAVGVote = new BigDecimal(0);
    }
    else
    {
      this.teamTwoAVGVote = MathUtil.divide(tmpTeamTwoTotVote, countListTwo, RoundingMode.HALF_UP, 1);
    }
    if (countListTwoAge <= 0)
    {
      this.teamTwoAVGAge = new BigDecimal(0);
    }
    else
    {
      this.teamTwoAVGAge = MathUtil.divide(tmpTeamTwoTotAge, countListTwoAge, RoundingMode.HALF_UP, 1);
    }

    this.teamOneRoles = countPlayersByRoles(this.teamOnePlayerList);
    this.teamTwoRoles = countPlayersByRoles(this.teamTwoPlayerList);

    return Constants.STRUTS_RESULT_NAME__REPORT_MATCH;
  }

  private void managePlayerInTeam(List<Player> formPlayerList, List<Player> teamList)
  {
    for (int i = 0; i < teamList.size(); i++)
    {
      Player currentPlayer = teamList.get(i);
      
      if (currentPlayer.getId().equals(this.idPlayer))
      {
        this.saveDraft = true;
        if (this.actionCode.equals(ACTION_CODE__MOVE_DOWN))
        {
          Player nextPlayer = teamList.get(i + 1);
          int currentPosition = currentPlayer.getPosition();
          currentPlayer.setPosition(nextPlayer.getPosition());
          nextPlayer.setPosition(currentPosition);
        }
        else if (this.actionCode.equals(ACTION_CODE__MOVE_UP))
        {
          currentPlayer.setPosition(currentPlayer.getPosition() - 1);
          teamList.get(i - 1).setPosition(teamList.get(i - 1).getPosition() + 1);
        }
        else if (this.actionCode.equals(ACTION_CODE__MOVE_RIGHT))
        {
          currentPlayer.setPlayerType(EnumPlayerType.TeamTwo.getValue());
          currentPlayer.setPosition(100);//(this.teamTwoPlayerList != null ? this.teamTwoPlayerList.size() + 1 : 1);
        }
        else if (this.actionCode.equals(ACTION_CODE__MOVE_LEFT))
        {
          currentPlayer.setPlayerType(EnumPlayerType.TeamOne.getValue());
          currentPlayer.setPosition(100);//(this.teamOnePlayerList != null ? this.teamOnePlayerList.size() + 1 : 1);
        }
        else if (this.actionCode.equals(ACTION_CODE__REMOVE))
        {
         if (missing)
            currentPlayer.setPlayerType(EnumPlayerType.Missing.getValue());
        }
      }
     
        formPlayerList.add(currentPlayer);
    }
  }

  private boolean viewPermission()
  {
    if (this.idMatch <= 0)
    {
      return false;
    }
    try
    {
      this.currentMatch = DAOFactory.getInstance().getMatchDAO().get(this.idMatch);
    }
    catch (Exception e)
    {
      return false;
    }
    if (this.currentMatch == null)
    {
      return false;
    }
    if (this.currentMatch.getCanceled())
    {
      return false;
    }
    if (!this.currentMatch.getRecorded())
    {
      return false;
    }
    return true;
  }

  private boolean editPermission()
  {
    if (this.idMatch <= 0)
    {
      return false;
    }
    this.currentMatch = MatchManager.getById(idMatch);
    if (this.currentMatch == null)
    {
      return false;
    }
    if (this.currentMatch.getCanceled())
    {
      return false;
    }
    if ((!this.currentMatch.getUserOwner().getId().equals(UserContext.getInstance().getUser().getId())) &&
            (!MatchManager.isUserReporter(UserContext.getInstance().getUser().getId(), this.idMatch)))
    {
      ArrayList<Integer> idAccreditedList = new ArrayList<Integer>();
      idAccreditedList.add(this.currentMatch.getUserOwner().getId());
      for (Player player : this.currentMatch.getPlayerList())
      {
        if (player.getUser() != null)
        {
          idAccreditedList.add(player.getUser().getId());
        }
      }
      Squad ownerSquad = SquadManager.getUserFirstSquad(this.currentMatch.getUserOwner().getId());
      if (ownerSquad != null)
      {
        idAccreditedList.addAll(SquadManager.getAllConfirmedUserByIdSquad(ownerSquad.getId()));
      }

      if (this.currentMatch.getRecorded())
      {
        return false;
      }
      else
      {
        this.viewArchivSchema = false;
        Player reporterPlayer = MatchManager.getReporter(this.idMatch);
        if (reporterPlayer == null)
        {
          reporter = this.currentMatch.getUserOwner();
        }
        else
        {
          reporter = reporterPlayer.getUser();
        }
      }
    }
    else
    {
      if (this.currentMatch.getRecorded())
      {
        GregorianCalendar limitEditDate = new GregorianCalendar();
        limitEditDate.setTime(this.currentMatch.getRecordedDate());
        limitEditDate.add(Calendar.DAY_OF_MONTH, AppContext.getInstance().getArchiveMatchEditableNumbersOfDays());
        if (limitEditDate.before(new GregorianCalendar()))
        {
          return false;
        }
      }
      this.viewArchivSchema = true;
    }
    if (this.currentMatch.getMatchStart().after(new Date()))
    {
      this.viewArchivSchema = false;
      Player reporterPlayer = MatchManager.getReporter(this.idMatch);
        if (reporterPlayer == null)
        {
          reporter = this.currentMatch.getUserOwner();
        }
        else
        {
          reporter = reporterPlayer.getUser();
        }
    }


    return true;
  }

  private boolean updatePlayers()
  {
    boolean success = true;
    List<Player> formPlayerList = new ArrayList<Player>();
    List<Player> tempPlayerList = new ArrayList<Player>();

    boolean delete=false;
    if (this.actionCode.equals(ACTION_CODE__REMOVE) &&  this.missing==false)
    {
      MatchManager.deletePlayerFromMatch(idMatch, idPlayer);
      delete=true;
    }

    for (Player player : this.currentMatch.getPlayerList())
    {
      if (player.getId()==this.idPlayer && delete)
      {
        logger.debug("deleted player " + idPlayer);
      }
      else
      {     
        tempPlayerList.add(player);
      }
    }

    // <editor-fold defaultstate="collapsed" desc="-- Player team One --">
    if (this.teamOnePlayerList != null)
    {
      managePlayerInTeam(formPlayerList, this.teamOnePlayerList);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Player team Two --">
    if (this.teamTwoPlayerList != null)
    {
      managePlayerInTeam(formPlayerList, this.teamTwoPlayerList);
    }
    // </editor-fold>
    
    JXPathContext jxpathContext = JXPathContext.newContext(formPlayerList);
    for (int i = 0; i < tempPlayerList.size(); i++)
    {
      Player tmpPlayer = tempPlayerList.get(i);
      Player formPlayer = null;
      try
      {
        formPlayer = (Player) jxpathContext.getValue(".[@id = " + tmpPlayer.getId() + "]");
      }
      catch (Exception e)
      {
      }
      if (formPlayer != null)
      {
        tmpPlayer.setGoals(formPlayer.getGoals());
        tmpPlayer.setVote(formPlayer.getVote());
        tmpPlayer.setPlayerRole(formPlayer.getPlayerRole());
        tmpPlayer.setPlayerType(formPlayer.getPlayerType());
        tmpPlayer.setOwnGoals(formPlayer.getOwnGoals());
        tmpPlayer.setReview(StringUtils.left(formPlayer.getReview(), 500));
        tmpPlayer.setPosition(formPlayer.getPosition());
      }
    }
    
    Collections.sort(tempPlayerList, Player.TEAM_POSITION_ORDER);
    int newPosition = 1;
    for (Player p : tempPlayerList)
    {
      p.setPosition(newPosition);
      newPosition++;
    }

    this.currentMatch.getPlayerList().clear();

    this.currentMatch.getPlayerList().addAll(tempPlayerList);

    return success;
  }

  private long calculateAge(Date birthDate)
  {
    if (birthDate == null)
    {
      return 0;
    }
    else
    {
      GregorianCalendar userBirthDate = new GregorianCalendar();
      userBirthDate.setTime(birthDate);
      GregorianCalendar currentDate = new GregorianCalendar();

      long tmpAge = currentDate.get(Calendar.YEAR) - userBirthDate.get(Calendar.YEAR);
      if (userBirthDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH))
      {
        tmpAge--;
      }
      else if (userBirthDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH))
      {
        if (userBirthDate.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH))
        {
          tmpAge--;
        }

      }
      return tmpAge;
    }

  }

  private List countPlayersByRoles(List<Player> playerList)
  {
    ArrayList countPlayersByRolesList = new ArrayList();

    List<PlayerRoleInfo> playerRoleInfoList = AppContext.getInstance().getAllPlayerRoleInfo(getCurrentObjLanguage(), getCurrentCobrand());
    if (playerRoleInfoList == null || playerRoleInfoList.size() <= 0)
    {
      return countPlayersByRolesList;
    }

    for (PlayerRoleInfo playerRoleInfo : playerRoleInfoList)
    {
      int count = 0;
      for (int i = 0; i <
              playerList.size(); i++)
      {
        if (playerList.get(i).getPlayerRole()!=null && playerList.get(i).getPlayerRole().getId() == playerRoleInfo.getId())
        {
          count++;
        }

      }
      countPlayersByRolesList.add(count);
    }

    return countPlayersByRolesList;
  }

// </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public boolean isViewArchivSchema()
  {
    return viewArchivSchema;
  }

  public void setViewArchivSchema(boolean viewArchivSchema)
  {
    this.viewArchivSchema = viewArchivSchema;
  }

  public List getTeamOneRoles()
  {
    return teamOneRoles;
  }

  public void setTeamOneRoles(List teamOneRoles)
  {
    this.teamOneRoles = teamOneRoles;
  }

  public List getTeamTwoRoles()
  {
    return teamTwoRoles;
  }

  public void setTeamTwoRoles(List teamTwoRoles)
  {
    this.teamTwoRoles = teamTwoRoles;
  }

  public BigDecimal getTeamOneAVGAge()
  {
    return teamOneAVGAge;
  }

  public void setTeamOneAVGAge(BigDecimal teamOneAVGAge)
  {
    this.teamOneAVGAge = teamOneAVGAge;
  }

  public BigDecimal getTeamOneAVGVote()
  {
    return teamOneAVGVote;
  }

  public void setTeamOneAVGVote(BigDecimal teamOneAVGVote)
  {
    this.teamOneAVGVote = teamOneAVGVote;
  }

  public BigDecimal getTeamTwoAVGAge()
  {
    return teamTwoAVGAge;
  }

  public void setTeamTwoAVGAge(BigDecimal teamTwoAVGAge)
  {
    this.teamTwoAVGAge = teamTwoAVGAge;
  }

  public BigDecimal getTeamTwoAVGVote()
  {
    return teamTwoAVGVote;
  }

  public void setTeamTwoAVGVote(BigDecimal teamTwoAVGVote)
  {
    this.teamTwoAVGVote = teamTwoAVGVote;
  }

  public String getCountryFlagImagesPrefix()
  {
    return countryFlagImagesPrefix;
  }

  public void setCountryFlagImagesPrefix(String countryFlagImagesPrefix)
  {
    this.countryFlagImagesPrefix = countryFlagImagesPrefix;
  }

  public int getIdMatch()
  {
    return idMatch;
  }

  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  public Match getCurrentMatch()
  {
    return currentMatch;
  }

  public void setCurrentMatch(Match currentMatch)
  {
    this.currentMatch = currentMatch;
  }

  public List getGolNumberList()
  {
    return golNumberList;
  }

  public void setGolNumberList(List golNumberList)
  {
    this.golNumberList = golNumberList;
  }

  public String getTeamOneGoals()
  {
    return teamOneGoals;
  }

  public void setTeamOneGoals(String teamOneGoals)
  {
    this.teamOneGoals = teamOneGoals;
  }

  public String getTeamOneName()
  {
    return teamOneName;
  }

  public void setTeamOneName(String teamOneName)
  {
    this.teamOneName = teamOneName;
  }

  public String getTeamOneShirt()
  {
    return teamOneShirt;
  }

  public void setTeamOneShirt(String teamOneShirt)
  {
    this.teamOneShirt = teamOneShirt;
  }

  public String getTeamTwoGoals()
  {
    return teamTwoGoals;
  }

  public void setTeamTwoGoals(String teamTwoGoals)
  {
    this.teamTwoGoals = teamTwoGoals;
  }

  public String getTeamTwoName()
  {
    return teamTwoName;
  }

  public void setTeamTwoName(String teamTwoName)
  {
    this.teamTwoName = teamTwoName;
  }

  public String getTeamTwoShirt()
  {
    return teamTwoShirt;
  }

  public void setTeamTwoShirt(String teamTwoShirt)
  {
    this.teamTwoShirt = teamTwoShirt;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public List<Player> getTeamOnePlayerList()
  {
    return teamOnePlayerList;
  }

  public void setTeamOnePlayerList(List<Player> teamOnePlayerList)
  {
    this.teamOnePlayerList = teamOnePlayerList;
  }

  public List<Player> getTeamTwoPlayerList()
  {
    return teamTwoPlayerList;
  }

  public void setTeamTwoPlayerList(List<Player> teamTwoPlayerList)
  {
    this.teamTwoPlayerList = teamTwoPlayerList;
  }

  public List getPlayerGolList()
  {
    return playerGolList;
  }

  public void setPlayerGolList(List playerGolList)
  {
    this.playerGolList = playerGolList;
  }

  public List getPlayerOwnGol()
  {
    return playerOwnGol;
  }

  public void setPlayerOwnGol(List playerOwnGol)
  {
    this.playerOwnGol = playerOwnGol;
  }

  public List<PlayerRoleInfo> getPlayerRoleInfoList()
  {
    return playerRoleInfoList;
  }

  public void setPlayerRoleInfoList(List<PlayerRoleInfo> playerRoleInfoList)
  {
    this.playerRoleInfoList = playerRoleInfoList;
  }

  public List<GuiPlayerVote> getPlayerVoteList()
  {
    return playerVoteList;
  }

  public void setPlayerVoteList(List<GuiPlayerVote> playerVoteList)
  {
    this.playerVoteList = playerVoteList;
  }

  public String getActionCode()
  {
    return actionCode;
  }

  public void setActionCode(String actionCode)
  {
    this.actionCode = actionCode;
  }

  public int getIdPlayer()
  {
    return idPlayer;
  }

  public void setIdPlayer(int idPlayer)
  {
    this.idPlayer = idPlayer;
  }

  public boolean isRecorded()
  {
    return recorded;
  }

  public void setRecorded(boolean recorded)
  {
    this.recorded = recorded;
  }

  public boolean isSaveDraft()
  {
    return saveDraft;
  }

  public void setSaveDraft(boolean saveDraft)
  {
    this.saveDraft = saveDraft;
  }

  public boolean isEditMatch()
  {
    return editMatch;
  }

  public void setEditMatch(boolean editMatch)
  {
    this.editMatch = editMatch;
  }

  public String getLimitEditDateString()
  {
    return limitEditDateString;
  }

  public void setLimitEditDateString(String limitEditDateString)
  {
    this.limitEditDateString = limitEditDateString;
  }

  public int getCountPost()
  {
    return countPost;
  }

  public void setCountPost(int countPost)
  {
    this.countPost = countPost;
  }

  public int getPlayersTeamOneToAdd()
  {
    return playersTeamOneToAdd;
  }

  public void setPlayersTeamOneToAdd(int playersTeamOneToAdd)
  {
    this.playersTeamOneToAdd = playersTeamOneToAdd;
  }

  /**
   * @return the reporter
   */
  public User getReporter()
  {
    return reporter;
  }
  /**
   * @param missing the missing to set
   */
  public void setMissing(boolean missing)
  {
    this.missing = missing;
  }
  /**
   * @param validate the validate to set
   */
  public void setValidate(boolean validate)
  {
    this.validate = validate;
  }
// </editor-fold>
}
