package it.newmedia.gokick.data.enums;

/**
 * 
 * Enum che definisce i messaggi che verranno salvati in funzione delle azioni fatte dagli utenti.
 */
public enum EnumMessage
{

  /**
   *  Enum per un messaggio indefinito
   */
  Undefined("Undefined"),
  /**
   * Enum per il messaggio relativo alla modifica di una partita
   */
  MatchModified("messaggio.bacheca.partita.modificata"),
  /**
   * Enum per il messaggio relativo all'annullamento di una partita
   */
  MatchCancelled("messaggio.bacheca.partita.annullata"),
  /**
   * Enum per il messaggio relativo all'archiviazione di una partita
   */
  MatchArchived("messaggio.bacheca.partita.archiviata"),
  /**
   * Enum per il messaggio relativo ad una richiesta d'amicizia effettuata
   */
  SquadInviteReceived("messaggio.bacheca.amicizia.invitoRicevuto"),
  /**
   * Enum per il messaggio relativo ad una richiesta d'amicizia accettata
   */
  SquadInviteAccepted("messaggio.bacheca.amicizia.invitoAccettato"),
  /**
   * Enum per il messaggio relativo ad una richiesta d'amicizia accettata
   */
  SquadInviteNotAccepted("messaggio.bacheca.amicizia.invitoNonAccettato"),
  /**
   * Enum per il messaggio relativo ad una richiesta d'amicizia ritirata
   */
  SquadRemoveInvitation("messaggio.bacheca.amicizia.invitoNellaRosaRitirato"),
  /**
   * Enum per il messaggio relativo ad un utente che viene rimosso dalla lista degli amici
   */
  SquadRemoveUser("messaggio.bacheca.amicizia.utenteRimosso");


  private String message;
  
  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return message;
  }

  EnumMessage(String message)
  {
    this.message = message;
  }
  
  /**
   * Restituisce un EnumMessage in funzione della stringa passata
   * @param message Stringa indicante la chiave del messaggio
   * @return Un istanza di EnumMessage valida. Se non è possibile
   * trasformare la stringa viene restituito EnumMessage.Undefined
   */
  public static EnumMessage getEnum( String message )
  {
    for (EnumMessage enumMessage : EnumMessage.values())
    {
      if( enumMessage.getValue().equals(message) )
        return enumMessage;
    }
    return EnumMessage.Undefined;
  }

}