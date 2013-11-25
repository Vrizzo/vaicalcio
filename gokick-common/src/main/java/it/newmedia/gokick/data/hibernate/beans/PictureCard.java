package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * Classe che rappresenta l'oggetto PictureCard che fa riferimento alla tabella PICTURE_CARDS.
 *
 * @hibernate.class
 * table="PICTURE_CARDS"
 */
public class PictureCard extends ABean
{

  private User user;
  private String filename;
  private Date created;
  private String pictureCardStatus;

  
  public static final Comparator<PictureCard> CREATED_ORDER =
          new Comparator<PictureCard>()
          {

            public int compare(PictureCard o1, PictureCard o2)
            {
              if (o1.getCreated().before(o2.getCreated()) )
                return -1;
              if (o1.getCreated() == o2.getCreated())
                return 0;
              return 1;
            }

          };

  
  /**
   * @hibernate.id
   * column="ID_PICTURE_CARD"
   * generator-class="native"
   * unsaved-value="null"
   */
  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_USER"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  /**
   * @hibernate.property
   * column="FILENAME"
   */
  public String getFilename()
  {
    return filename;
  }

  public void setFilename(String filename)
  {
    this.filename = filename;
  }

  /**
   * @hibernate.property
   * column="CREATED"
   */
  public Date getCreated()
  {
    return created;
  }

  public void setCreated(Date created)
  {
    this.created = created;
  }

  /**
     * @hibernate.property
     * column="PICTURE_CARD_STATUS"
     */
    public String getPictureCardStatus()
    {
        return pictureCardStatus;
    }

    public void setPictureCardStatus(String pictureCardStatus)
    {
        this.pictureCardStatus = pictureCardStatus;
    }

    public EnumPictureCardStatus getEnumPictureCardStatus()
    {

        if (this.pictureCardStatus == null)
        {
          return EnumPictureCardStatus.Undefined;
        }

        return EnumPictureCardStatus.getEnum(this.pictureCardStatus);
    }

    public void setEnumPictureCardStatus(EnumPictureCardStatus enumPictureCardStatus)
    {
        this.pictureCardStatus = enumPictureCardStatus.getValue();
    }

}