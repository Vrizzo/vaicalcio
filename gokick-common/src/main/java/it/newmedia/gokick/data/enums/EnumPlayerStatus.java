package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce lo status dei players in una partita.
 */
public enum EnumPlayerStatus
{

  Undefined("Undefined"),
  /**
   *  l'utente si è iscritto alla partita
   */
  UserRegistered("USER_REGISTERED"),
  /**
   *  l'utente è stato convocato alla partita
   */
  UserCalled("USER_CALLED"),
  /**
   *  l'utente è stato iscritto dall'organizzatore della partita
   */
  OwnerRegistered("OWNER_REGISTERED"), 
  /**
   *  un utente (amici e non) ha richiesto di giocare
   */
  UserRequest("USER_REQUEST"),
  /**
   *  l'organizzatore ha accettato un utente fuori rosa
   */
  UserRequestRegistered("USER_REQUEST_REGISTERED"),
  /**
   *  è un giocatore che non è iscritto al sito
   */
  Out("OUT");

  private String playerStatus;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return playerStatus;
  }

  EnumPlayerStatus(String playerStatus)
  {
    this.playerStatus = playerStatus;
  }

  /**
   * Restituisce un EnumPlayerStatus in funzione della stringa passata
   * @param playerMatchStatus Stringa indicante lo status
   * @return Un istanza di EnumPlayerStatus valida. Se non è possibile
   * trasformare la stringa viene restituito EnumPlayerStatus.Undefined
   */
  public static EnumPlayerStatus getEnum(String playerStatus)
  {
    for (EnumPlayerStatus enumPlayerStatus : EnumPlayerStatus.values())
    {
      if (enumPlayerStatus.getValue().equals(playerStatus))
      {
        return enumPlayerStatus;
      }
    }
    return EnumPlayerStatus.Undefined;
  }

}