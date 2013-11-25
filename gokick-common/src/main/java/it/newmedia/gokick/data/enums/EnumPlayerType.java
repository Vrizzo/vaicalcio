package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce il tipo di un Player associato ad un match
 */
public enum EnumPlayerType
{

  /**
   *  Usato quando un utente non si presenta ad una partita
   */
  Missing("MISSING"),
  /**
   *  Appartiene alla squadra ONE di una partita
   */
  TeamOne("TEAM_ONE"),
  /**
   *  Appartiene alla squadra TWO di una partita
   */
  TeamTwo("TEAM_TWO");

  private String playerType;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return playerType;
  }
  
  EnumPlayerType(String playerType)
  {
    this.playerType = playerType;
  }
  
  /**
   * Restituisce un EnumPlayerType in funzione della stringa passata
   * @param playerType Stringa indicante lo stato
   * @return Un istanza di EnumPlayerType valida. Se non è possibile
   * trasformare la stringa viene restituito EnumPlayerType.Missing
   */
  public static EnumPlayerType getEnum( String playerType )
  {
    for (EnumPlayerType enumPlayerType : EnumPlayerType.values())
    {
      if( enumPlayerType.getValue().equals(playerType) )
        return enumPlayerType;
    }
    return EnumPlayerType.Missing;
  }

}