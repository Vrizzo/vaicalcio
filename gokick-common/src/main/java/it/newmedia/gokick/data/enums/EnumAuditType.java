package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce il l'elenco degli audits che possono essere registrati
 */
public enum EnumAuditType
{

  /**
   *  Login utente
   */
  UserLogin("UserLogin");

  private String type;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return type;
  }

  EnumAuditType(String type)
  {
    this.type = type;
  }
  
  /**
   * Restituisce un EnumCobrandType in funzione della stringa passata
   * @param auditType Stringa indicante il tipo azione
   * @return Un istanza di EnumAuditType valida. Se non è possibile
   * trasformare la stringa viene restituito EnumCobrandType.UserLogin (DEVE esserci qualcosa sempre!)
   */
  public static EnumAuditType getEnum( String auditType )
  {
    for (EnumAuditType enumCobrandType : EnumAuditType.values())
    {
      if( enumCobrandType.getValue().equals(auditType) )
        return enumCobrandType;
    }
    return EnumAuditType.UserLogin;
  }

}