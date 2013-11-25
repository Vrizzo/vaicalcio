package it.newmedia.gokick.site.infos;

import it.newmedia.gokick.data.hibernate.beans.UserComment;
import it.newmedia.gokick.site.managers.DateManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe che gestisce le informazioni relative ai feedback quando queste devono essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class FeedbackInfo implements Serializable
{
  
  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >

  private double technicalAvg;
  private double reliabilityAvg;
  private double fairPlayAvg;
  private double totalAvg;
  private List<FeedbackRowInfo> feedbackRowInfoList;
  private int fbSize;
  private FeedbackRowInfo lastFeedback;


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --"  >

  /**
   * @return the technicalAvg
   */
  public double getTechnicalAvg()
  {
    return technicalAvg;
  }

  /**
   * @param technicalAvg the technicalAvg to set
   */
  public void setTechnicalAvg(double technicalAvg)
  {
    this.technicalAvg = technicalAvg;
  }

  /**
   * @return the reliabilityAvg
   */
  public double getReliabilityAvg()
  {
    return reliabilityAvg;
  }

  /**
   * @param reliabilityAvg the reliabilityAvg to set
   */
  public void setReliabilityAvg(double reliabilityAvg)
  {
    this.reliabilityAvg = reliabilityAvg;
  }

  /**
   * @return the fairPlayAvg
   */
  public double getFairPlayAvg()
  {
    return fairPlayAvg;
  }

  /**
   * @param fairPlayAvg the fairPlayAvg to set
   */
  public void setFairPlayAvg(double fairPlayAvg)
  {
    this.fairPlayAvg = fairPlayAvg;
  }

  /**
   * @return the totalAvg
   */
  public double getTotalAvg()
  {
    return totalAvg;
  }

  /**
   * @param totalAvg the totalAvg to set
   */
  public void setTotalAvg(double totalAvg)
  {
    this.totalAvg = totalAvg;
  }

  /**
   * @return the FeedbackRowInfoList
   */
  public List<FeedbackRowInfo> getFeedbackRowInfoList()
  {
    return feedbackRowInfoList;
  }

  /**
   * @param FeedbackRowInfoList the FeedbackRowInfoList to set
   */
  public void setFeedbackRowInfoList(List<FeedbackRowInfo> FeedbackRowInfoList)
  {
    this.feedbackRowInfoList = FeedbackRowInfoList;
  }
   /**
   * @return the fbSize
   */
  public int getFbSize()
  {
    return this.feedbackRowInfoList.size();
  }
  /**
   * @return the lastFeedback
   */
  public FeedbackRowInfo getLastFeedback()
  {
    return this.getFeedbackRowInfoList().get(this.fbSize);
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   *
   * @param userCommentList
   */
  public void load(List<UserComment> userCommentList)
  {
  double technicalTot=0;
  double reliabilityTot=0;
  double fairPlayTot=0;



  this.feedbackRowInfoList= new ArrayList<FeedbackRowInfo>();

  if (userCommentList.size()>0)
  {
     for(UserComment userComment : userCommentList)
      {
        FeedbackRowInfo f = new FeedbackRowInfo();
        f.load(userComment);
        this.feedbackRowInfoList.add(f);

        technicalTot =technicalTot + userComment.getTech();
        reliabilityTot = reliabilityTot+ userComment.getReliability();
        fairPlayTot = fairPlayTot + userComment.getFairplay();
      }

      this.technicalAvg= technicalTot / this.feedbackRowInfoList.size();
      this.reliabilityAvg=reliabilityTot / this.feedbackRowInfoList.size();
      this.fairPlayAvg=fairPlayTot / this.feedbackRowInfoList.size();

      this.totalAvg=(this.technicalAvg + this.reliabilityAvg + this.fairPlayAvg) / 3;
      
      BigDecimal bd = new BigDecimal(this.totalAvg);         //conversione a 1 cifra dopo la virgola
      bd = bd.round(new MathContext(2, RoundingMode.HALF_UP));
      this.totalAvg=bd.doubleValue();

      bd = new BigDecimal(this.technicalAvg);
      bd = bd.round(new MathContext(2, RoundingMode.HALF_UP));
      this.technicalAvg=bd.doubleValue();

      bd = new BigDecimal(this.reliabilityAvg);
      bd = bd.round(new MathContext(2, RoundingMode.HALF_UP));
      this.reliabilityAvg=bd.doubleValue();

      bd = new BigDecimal(this.fairPlayAvg);
      bd = bd.round(new MathContext(2, RoundingMode.HALF_UP));
      this.fairPlayAvg=bd.doubleValue();
  }

}


// </editor-fold>

  /**
  *
  */
  public class FeedbackRowInfo
  {

  // <editor-fold defaultstate="collapsed" desc="-- Members --">

    private int technical;
    private int reliability;
    private int fairPlay;
    private String textdetail;
    private Date date;
    private String data;
    private int idUserAuthor;
    private String nameAuthor;

    // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
    /**
     *
     * @param userComment
     */
    public void load(UserComment userComment)
    {
        this.technical=userComment.getTech();
        this.reliability=userComment.getReliability();
        this.fairPlay=userComment.getFairplay();
        this.textdetail=userComment.getTextDetail();
        this.date=userComment.getCreated();
        this.idUserAuthor=( userComment.getUserAuthor().getId());
        this.nameAuthor=  userComment.getUserAuthor().getFirstName() + " "
                        + userComment.getUserAuthor().getLastName();
        this.data= DateManager.showDate(this.date, DateManager.FORMAT_DATE_4);
      }

// </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --"  >

    /**
     * @return the technical
     */
    public int getTechnical()
    {
      return technical;
    }

    /**
     * @param technical the technical to set
     */
    public void setTechnical(int technical)
    {
      this.technical = technical;
    }

    /**
     * @return the reliabilit
     */
    public int getReliability()
    {
      return reliability;
    }

    /**
     * @param reliability
     */
    public void setReliability(int reliability)
    {
      this.reliability = reliability;
    }

    /**
     * @return the fairPlay
     */
    public int getFairPlay()
    {
      return fairPlay;
    }

    /**
     * @param fairPlay the fairPlay to set
     */
    public void setFairPlay(int fairPlay)
    {
      this.fairPlay = fairPlay;
    }

    /**
     * @return the textdetail
     */
    public String getTextdetail()
    {
      return textdetail;
    }

    /**
     * @param textdetail the textdetail to set
     */
    public void setTextdetail(String textdetail)
    {
      this.textdetail = textdetail;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
      return date;
    }

    /**
     * @return the idUserAuthor
     */
    public int getIdUserAuthor()
    {
      return idUserAuthor;
    }

    /**
     * @return the nameAuthor
     */
    public String getNameAuthor()
    {
      return nameAuthor;
    }

    /**
     * @return the data
     */
    public String getData()
    {
      return data;
    }


    // </editor-fold>

  }


}//end Class
