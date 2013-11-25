package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe che rappresenta l'oggetto le informazioni relative allo stato dell'utente rispetto all'associazione PlayMore
 * Fa riferimaneto alla vista V_PLAYMORE_PARTNERS che a sua volta usa il db PlayMore_APS
 *
 * @hibernate.class table="V_PLAYMORE_PARTNERS"
 */
public class VPlayMorePartner extends ABean
{
  public static final String STATUS_EXPIRED = "expired";
  private Date expireDate;
  private int partnerType;
  private long distance;
  private String status;

  public VPlayMorePartner()
  {
    expireDate = new GregorianCalendar(2000, 0, 1).getTime();
    distance = -9999;
  }

  /**
   * @hibernate.property column="EXPIRE_DATE"
   */
  public Date getExpireDate()
  {
    return expireDate;
  }

  public void setExpireDate(Date expireDate)
  {
    this.expireDate = expireDate;
  }

  /**
   * @hibernate.property column="PARTNER_TYPE"
   */
  public int getPartnerType()
  {
    return partnerType;
  }

  public void setPartnerType(int partnerType)
  {
    this.partnerType = partnerType;
  }

  public boolean isSupporter()
  {
    return partnerType == 1 && !StringUtils.equals(getStatus(), STATUS_EXPIRED);
  }

  public boolean isSuperstar()
  {
    return partnerType == 2 && !StringUtils.equals(getStatus(), STATUS_EXPIRED);
  }

  public long getDistance()
  {
    if (distance == -9999)
    {
      distance = DateUtil.getDiffDays(expireDate, new Date());
      if(distance == 0 && DateUtil.getDiffMinutes(expireDate, new Date())<0)
      {
        distance = -1;
      }
    }
    return distance;
  }

  public String getStatus()
  {
    if (status == null)
    {
      if (getDistance() > 30)
      {
        status = "active";
      }
      else if (getDistance() >= 0 && getDistance() <= 30)
      {
        status = "active-near-expire";
      }
      else if (getDistance() < 0 && getDistance() >= -30)
      {
        status = "active-expired";
      }
      else if (getDistance() < -30)
      {
        status = STATUS_EXPIRED;
      }
    }
    return status;
  }

  /**
   * @hibernate.id column="ID_USER"
   * generator-class="native"
   * unsaved-value="null"
   */
  @Override
  public Integer getId()
  {
    return id;
  }

  @Override
  public void setId(Integer id)
  {
    this.id = id;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
            append("id", getId()).
            append("expireDate", getExpireDate()).
            append("partnerType", getPartnerType()).
            append("distance", getDistance()).
            append("status", getStatus()).
            toString();
  }
}
