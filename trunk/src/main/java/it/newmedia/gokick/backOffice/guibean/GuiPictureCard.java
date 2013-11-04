package it.newmedia.gokick.backOffice.guibean;

import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.security.encryption.MD5;
import it.newmedia.web.WebUtil;
import java.io.UnsupportedEncodingException;

public class GuiPictureCard extends AGuiBean
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private PictureCard pictureCard;
  private User user;
  private String detailsUrl;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  public GuiPictureCard(PictureCard pictureCard)
  {
    this.pictureCard = pictureCard;
    this.user=pictureCard.getUser();
    this.detailsUrl=getUserDetailsUrl();
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public String getUserDetailsUrl()
  {
    try
    {
      return String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",
              AppContext.getInstance().getSiteUrl(),
              this.pictureCard.getUser().getId(),
              this.getUserPasswordMD5(),
              WebUtil.encode( "userDetails.action?idUser=" + this.pictureCard.getUser().getId() + "&tab=scheda","UTF-8")
              );
    }
    catch (UnsupportedEncodingException unsupportedEncodingException)
    {
      return "Error creating UserDetailsUrl";
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  /**
   * @return the pictureCard
   */
  public PictureCard getPictureCard()
  {
    return pictureCard;
  }

  public String getUserPasswordMD5()
  {
    return MD5.getHashString(this.pictureCard.getUser().getPassword());
  }

  /**
   * @return the user
   */
  public User getUser()
  {
    return user;
  }
  /**
   * @return the detailsUrl
   */
  public String getDetailsUrl()
  {
    return detailsUrl;
  }

  

  // </editor-fold>
}
