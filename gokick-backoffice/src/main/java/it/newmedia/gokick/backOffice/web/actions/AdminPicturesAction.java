package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.guibean.GuiPictureCard;
import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.backOffice.manager.PictureCardManager;
import it.newmedia.gokick.backOffice.manager.UserManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di utenti
 */
public class AdminPicturesAction extends  ABaseActionSupport  implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public final static int NUMBER_PICTURES_FOR_ROW = 5;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private List<List<GuiPictureCard>> pictureCardFatherList;
  private List<String> enumPictureStatusList;
  private String pictureStatus;
  private String newPictureStatus;
  private int picTot;
  private int picRefusedTot;
  private int picPendingTot;
  private int picSelectedTot;
  private List<Integer> pictureCheckList;
  private List<Integer> userCheckList;
  private boolean sendMessage;
  private String receiverType;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  @Override
  public void prepare()
  {
    int idCountryFilter=UserContext.getInstance().getIdCountryFilter();
    this.enumPictureStatusList= new ArrayList<String>();
    this.getEnumPictureStatusList().add(EnumPictureCardStatus.Pending.getValue());
    this.getEnumPictureStatusList().add(EnumPictureCardStatus.Current.getValue());
    this.getEnumPictureStatusList().add(EnumPictureCardStatus.Refused.getValue());

    this.picTot=PictureCardManager.countPictures(idCountryFilter,EnumPictureCardStatus.Pending,EnumPictureCardStatus.Current,EnumPictureCardStatus.Refused);
    this.picPendingTot=PictureCardManager.countPictures(idCountryFilter,EnumPictureCardStatus.Pending);
    this.picRefusedTot=PictureCardManager.countPictures(idCountryFilter,EnumPictureCardStatus.Refused);

  }

  public String viewAll()
  {
    List<PictureCard> pictureCardList=new ArrayList<PictureCard>();
    pictureCardList=PictureCardManager.getAllByStatusValue(this.pictureStatus,UserContext.getInstance().getIdCountryFilter());
    this.picSelectedTot=pictureCardList.size();

    this.pictureCardFatherList = new ArrayList<List<GuiPictureCard>>();
    List<GuiPictureCard> tempList = null;

    int count = 0;
    int indexCount = 0;
    for (PictureCard pictureCard : pictureCardList)
    {
      indexCount++;
      count = count + 1;
      if (count <= NUMBER_PICTURES_FOR_ROW )
      {
        if (tempList == null)
          tempList = new ArrayList<GuiPictureCard>();

        GuiPictureCard guiPictureCard=new GuiPictureCard(pictureCard);
        tempList.add(guiPictureCard);
      }

      if (count == NUMBER_PICTURES_FOR_ROW || indexCount== pictureCardList.size() )
      {
        getPictureCardFatherList().add(tempList);
        tempList = null;
        count = 0;
      }

    }
    return SUCCESS;
  }

  public String input()
  {
    return SUCCESS;
  }

  @Override
  public void validate()
  {
    if (this.sendMessage && userCheckList==null)
    {
      addActionError("seleziona destinatari");
    }
  }

  public String update()
  {
    int updated=PictureCardManager.updateMultiplyPicturesStatus(this.pictureCheckList, EnumPictureCardStatus.getEnum(newPictureStatus));
    if (this.sendMessage)
    {
      UserContext.getInstance().setUserReceiverList(UserManager.getByIdUserList(userCheckList));
      this.receiverType="gokickers";
      return Constants.STRUTS_RESULT_NAME__SENDMESSAGES;
    }

    return Constants.STRUTS_RESULT_NAME__UPDATED;
  }

   // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   * @return the pictureCardFatherList
   */
  public List<List<GuiPictureCard>> getPictureCardFatherList()
  {
    return pictureCardFatherList;
  }

  /**
   * @return the EnumUserStatusList
   */
  public List<String> getEnumPictureStatusList()
  {
    return enumPictureStatusList;
  }

  /**
   * @return the pictureStatus
   */
  public String getPictureStatus()
  {
    return pictureStatus;
  }

  /**
   * @param pictureStatus the pictureStatus to set
   */
  public void setPictureStatus(String pictureStatus)
  {
    this.pictureStatus = pictureStatus;
  }

  /**
   * @return the picTot
   */
  public int getPicTot()
  {
    return picTot;
  }

  /**
   * @return the picRefusedTot
   */
  public int getPicRefusedTot()
  {
    return picRefusedTot;
  }

  /**
   * @return the picPendingTot
   */
  public int getPicPendingTot()
  {
    return picPendingTot;
  }

  /**
   * @return the picSelectedTot
   */
  public int getPicSelectedTot()
  {
    return picSelectedTot;
  }

  /**
   * @return the newPictureStatus
   */
  public String getNewPictureStatus()
  {
    return newPictureStatus;
  }

  /**
   * @param newPictureStatus the newPictureStatus to set
   */
  public void setNewPictureStatus(String newPictureStatus)
  {
    this.newPictureStatus = newPictureStatus;
  }

  /**
   * @return the pictureCheckList
   */
  public List<Integer> getPictureCheckList()
  {
    return pictureCheckList;
  }

  /**
   * @param pictureCheckList the pictureCheckList to set
   */
  public void setPictureCheckList(List<Integer> pictureCheckList)
  {
    this.pictureCheckList = pictureCheckList;
  }

  /**
   * @return the sendMessage
   */
  public boolean isSendMessage()
  {
    return sendMessage;
  }

  /**
   * @param sendMessage the sendMessage to set
   */
  public void setSendMessage(boolean sendMessage)
  {
    this.sendMessage = sendMessage;
  }

  /**
   * @return the userCheckList
   */
  public List<Integer> getUserCheckList()
  {
    return userCheckList;
  }

  /**
   * @param userCheckList the userCheckList to set
   */
  public void setUserCheckList(List<Integer> userCheckList)
  {
    this.userCheckList = userCheckList;
  }

  /**
   * @return the receiverType
   */
  public String getReceiverType()
  {
    return receiverType;
  }

  /**
   * @param receiverType the receiverType to set
   */
  public void setReceiverType(String receiverType)
  {
    this.receiverType = receiverType;
  }



  // </editor-fold>
}
