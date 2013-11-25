package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce il l'aspetto del cobrand
 */
public enum EnumCobrandType
{

  /**
   *  Sito completo, si vede header, footer e tutte le pagine complete
   */
  Complete("Complete"),
  /**
   *  Sito senza colonne di sx
   */
  Small("Small");

  private String type;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return type;
  }

  EnumCobrandType(String type)
  {
    this.type = type;
  }
  
  /**
   * Restituisce un EnumCobrandType in funzione della stringa passata
   * @param cobrandType Stringa indicante il tipo di cobrand
   * @return Un istanza di EnumCobrandType valida. Se non è possibile
   * trasformare la stringa viene restituito EnumCobrandType.Complete
   */
  public static EnumCobrandType getEnum( String cobrandType )
  {
    for (EnumCobrandType enumCobrandType : EnumCobrandType.values())
    {
      if( enumCobrandType.getValue().equals(cobrandType) )
        return enumCobrandType;
    }
    return EnumCobrandType.Complete;
  }

}