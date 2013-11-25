package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce lo status degli utenti in una rosa.
 */
public enum EnumUserSquadStatus
{

  /**
   *  Enum per uno status indefinito
   */
  Undefined("Undefined"),
  /**
   *  Enum per lo status dell'utente che fa parte della rosa
   */
  Confirmed("CONFIRMED"),
  /**
   *  Enum per lo status dove il proprietario ha invitato un utente ad iscriversi alla propria rosa
   */
  Invited("INVITED"),
  /**
   *  Enum per lo status dell'utente che ha richiesto di far parte di questa rosa
   */
  Request("REQUEST");

  private String userSquadStatus;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return userSquadStatus;
  }
  
  EnumUserSquadStatus(String userSquadStatus)
  {
    this.userSquadStatus = userSquadStatus;
  }
  
  /**
   * Restituisce un EnumUserSquadStatus in funzione della stringa passata
   * @param userSquadStatus Stringa indicante lo status
   * @return Un istanza di EnumUserSquadStatus valida. Se non è possibile
   * trasformare la stringa viene restituito EnumUserSquadStatus.Undefined
   */
  public static EnumUserSquadStatus getEnum( String userSquadStatus )
  {
    for (EnumUserSquadStatus enumUserSquadStatus : EnumUserSquadStatus.values())
    {
      if( enumUserSquadStatus.getValue().equals(userSquadStatus) )
        return enumUserSquadStatus;
    }
    return EnumUserSquadStatus.Undefined;
  }

}