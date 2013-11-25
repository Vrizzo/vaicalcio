package it.newmedia.gokick.data.enums;
/**
 *
 * Enum che definisce lo stato delle figurine.
 */
public enum EnumPictureCardStatus
{
  /**
   *  Enum per figurina senza stato (nessuna figurina)
   */
  Undefined("UNDEFINED"),
  /**
   *  Enum per ufigurina corrente
   */
  Current("CURRENT"),
  /**
   *  Enum per figurina da approvare
   */
  Pending("PENDING"),
  /**
   *  Enum per figurina non approvata
   */
  Refused("REFUSED");

  private String pictureCardStatus;
  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return pictureCardStatus;
  }
  
  EnumPictureCardStatus(String pictureCardStatus)
  {
    this.pictureCardStatus = pictureCardStatus;
  }
  
  /**
   * Restituisce un EnumPictureCardStatus in funzione della stringa passata
   * @param pictureCardStatus Stringa indicante lo stato della figurina
   * @return Un istanza di EnumPictureCardStatus valida. 
   */
  public static EnumPictureCardStatus getEnum( String pictureCardStatus )
  {
    EnumPictureCardStatus enumPictureCardStatus=null;
    for (EnumPictureCardStatus pictureCardStatusEnum : EnumPictureCardStatus.values())
    {
      if( pictureCardStatusEnum.getValue().equals(pictureCardStatus) )
      {
        enumPictureCardStatus=pictureCardStatusEnum;
      }        
    }
    return enumPictureCardStatus;
    
  }

}