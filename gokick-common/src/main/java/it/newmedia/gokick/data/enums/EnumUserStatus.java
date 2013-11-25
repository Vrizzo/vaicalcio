package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce lo status di uno user.
 */
public enum EnumUserStatus
{

  /**
   *  Enum per uno status indefinito
   */
  Undefined("Undefined"),
  /**
   *  Enum per lo status pending
   */
  Pending("PENDING"),
  /**
   *  Enum per lo status enabled
   */
  Enabled("ENABLED"),
  /**
   *  Enum per lo status cancelled
   */
  Cancelled("CANCELLED"),
  /**
   *  Enum per lo status deleted
   */
  Deleted("DELETED");

  private String userStatus;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return userStatus;
  }
  
  EnumUserStatus(String userStatus)
  {
    this.userStatus = userStatus;
  }
  
  /**
   * Restituisce un EnumUserStatus in funzione della stringa passata
   * @param userStatus Stringa indicante lo status
   * @return Un istanza di EnumUserStatus valida. Se non è possibile
   * trasformare la stringa viene restituito EnumUserStatus.Undefined
   */
  public static EnumUserStatus getEnum( String userStatus )
  {
    for (EnumUserStatus enumUserStatus : EnumUserStatus.values())
    {
      if( enumUserStatus.getValue().equals(userStatus) )
        return enumUserStatus;
    }
    return EnumUserStatus.Undefined;
  }

}