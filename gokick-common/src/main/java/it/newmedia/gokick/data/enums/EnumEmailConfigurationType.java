package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce il tipo di email che verrà inviata dall'applicazione.
 */
public enum EnumEmailConfigurationType
{

  /**
   *  Enum per un tipo indefinito
   */
  Undefined("Undefined"),

  /**
   *  Enum per la notifica della modifica di una partita
   */
  MatchModified("match.modified"),

  /**
   *  Enum per la notifica dell'annullamento di una partita
   */
  MatchCancelled("match.cancelled"),

  /**
   *  Enum per la notifica della pagellazione di una partita
   */
  MatchArchived("match.archived"),

  /**
   *  Enum per la registrazione di un utente
   */
  UserRegistration("user.registration"),
  
  /**
   *  Enum per la richiesta del cambio password di un utente
   */
  UserChangePasswordRequest("user.changePassword"),

  /**
   *  Enum per la notifica di essere stati invitati in una rosa
   */
  UserInviteNotify("user.inviteNotify"),

  /**
   *  Enum per la notifica di richiesta di entrata nella sua rosa
   */ //sembra non sia usato.. ambiguo
  UserRequestNotify("user.requestNotify"),

  /**
   *  Enum per la notifica di inserimento in una rosa
   */
  AcceptUserInSquadNotify("user.acceptInSquad"),

  /**
   *  Enum per la notifica di una partita possibile
   */
  InviteFriendsToMatch("InviteFriendsToMatch"),

  /**
   *  Enum per la notifica di una partita possibile a esterni
   */
  InviteOutersToMatch("InviteOutersToMatch"),

  /**
   *  Enum per la notifica di una partita possibile a Gokickers
   */
  InviteGokickersToMatch("InviteGokickersToMatch"),

  /**
   *  Enum per invio mail a player di un match
   */
  ContactMatchPlayers("ContactMatchPlayers"),

  /**
   *  Enum per invitare esterni ad iscriversi al sito
   */
  InviteFriendsToSite("InviteFriendsToSite"),

  /**
   *  Enum per richiedere degli inviti
   */
  InviteRequest("InviteRequest"),

  /**
   *  Enum per la notifica dell'inserimento di un nuovo campo
   */
  SportCenterInserted("sportCenter.inserted"),
  /**
   *  Enum per la notifica di abusi da parte dell'utente
   */
  NotifyUserAbuse("user.abuseNotify"),
  
  /**
   *  Enum per la notifica di registrazione player in Request nel match
   */
  UserRequestRegisteredToMatchNotify("user.request.registeredToMatch"),
  
   /**
   *  Enum per la notifica di registrazione player nel match
   */
  UserRegisteredToMatchNotify("user.registeredToMatch"),

  /**
   *  Enum per la notifica di registrazione player nel match
   */
  BackofficeNotificationMail("backoffice.notificationMail"),

  /**
   *  Enum per la notifica di apertura iscrizioni match di amici
   */
  MatchRegistrationOpenNotify("match.registrationOpen"),

  /**
   *  Enum per la conferma accettazione richiesta di organizzare partite
   */
  UserNewOrganizerConfirm("user.newOrganizerConfirm"),

  /**
   *  Enum per invio mail verso user segnalazione organizzata terza partita
   */
  UserThirdOrganizedMatch("user.thirdOrganizedMatch"),

  /**
   *  Enum per la notifica verso il backoffice di una nuova richiesta per organizzare partite
   */
  BackofficeNewOrganizer("backoffice.newOrganizer"),

  /**
   * Enum per la mail di notifica scadenza associazione playmore. Nel caso manchino dei gg all scadenza
   */
  PlayMorePartnerNotifyGreaterThanZero("playMore.greaterThanZero"),

  /**
   * Enum per la mail di notifica scadenza associazione playmore. Nel caso manchino dei gg all scadenza
   */
  PlayMorePartnerNotifyEqualZero("playMore.equalZero"),

  /**
   * Enum per la mail di notifica scadenza associazione playmore. Nel caso manchino dei gg all scadenza
   */
  PlayMorePartnerNotifyLessThanZero("playMore.lessThanZero"),



  ;

  private String emailConfigurationType;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return emailConfigurationType;
  }

  EnumEmailConfigurationType(String emailConfigurationType)
  {
    this.emailConfigurationType = emailConfigurationType;
  }

  /**
   * Restituisce un EnumEmailConfigurationType in funzione della stringa passata
   * @param emailConfigurationType Stringa indicante il tipo di email
   * @return Un istanza di EnumEmailConfigurationType valida. Se non è possibile
   * trasformare la stringa viene restituito EnumEmailConfigurationType.Undefined
   */
  public static EnumEmailConfigurationType getEnum( String emailConfigurationType )
  {
    for (EnumEmailConfigurationType enumEmailConfigurationType : EnumEmailConfigurationType.values())
    {
      if( enumEmailConfigurationType.getValue().equals(emailConfigurationType) )
        return enumEmailConfigurationType;
    }
    return EnumEmailConfigurationType.Undefined;
  }

}