package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.gokick.data.enums.EnumAuditType;
import it.newmedia.gokick.data.enums.EnumCobrandType;
import org.apache.commons.lang.StringUtils;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.util.Date;

/**
 * Classe Cobrand che rappresenta le configurazioni relative ai possibili Cobrand di gokick
 * Fa riferimento alla tabella COBRANDS
 *
 * @hibernate.class table="AUDITS"
 * mutable="false"
 * @hibernate.cache usage="read-only"
 */
public class Audit extends ABean
{
// ------------------------------ FIELDS ------------------------------

  private String type;
  private Integer idUser;
  private String cobrandCode;
  private String ipAddress;
  private String infos;
  private Date refDateTime;

  public Audit()
  {
    refDateTime = new Date();
  }

  // --------------------- GETTER / SETTER METHODS ---------------------


  /**
   * @hibernate.id column="ID_AUDIT"
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
   * @hibernate.property column="TYPE"
   */
  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  /**
   * @hibernate.property column="ID_USER"
   */
  public Integer getIdUser()
  {
    return idUser;
  }

  public void setIdUser(Integer idUser)
  {
    this.idUser = idUser;
  }

  /**
   * @hibernate.property column="COBRAND_CODE"
   */
  public String getCobrandCode()
  {
    return cobrandCode;
  }

  public void setCobrandCode(String cobrandCode)
  {
    this.cobrandCode = cobrandCode;
  }

  /**
   * @hibernate.property column="IP_ADDRESS"
   */
  public String getIpAddress()
  {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress)
  {
    this.ipAddress = ipAddress;
  }

  /**
   * @hibernate.property column="INFOS"
   */
  public String getInfos()
  {
    return infos;
  }

  public void setInfos(String infos)
  {
    this.infos = infos;
  }

  /**
   * @hibernate.property column="REF_DATETIME"
   */
  public Date getRefDateTime()
  {
    return refDateTime;
  }

  public void setRefDateTime(Date refDateTime)
  {
    this.refDateTime = refDateTime;
  }

  // -------------------------- OTHER METHODS --------------------------

  public EnumAuditType getEnumAuditType()
  {
    if (this.type == null)
    {
      return EnumAuditType.UserLogin;
    }
    return EnumAuditType.getEnum(this.type);
  }

  public void setEnumAuditType(EnumAuditType enumAuditType)
  {
    this.type = enumAuditType.getValue();
  }
}
