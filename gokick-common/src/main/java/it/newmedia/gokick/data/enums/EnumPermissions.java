package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che i permessi degli user relativi alle attività di BackOffice.
 */
public enum EnumPermissions
{

  /**
   *  Enum per un permesso indefinito
   */
  Undefined("Undefined"),
  /**
   *  Enum permesso figurina
   */
  Figurine("F"),
  /**
   *  Enum permesso Campi
   */
  Campi("C"),
  /**
   *  Enum permesso Tornei
   */
  Tornei("T"),
  /**
   *  Enum permesso Links
   */
  Links("L"),
  /**
   *  Enum permesso News
   */
  News("N"),
  /**
   *  Enum permesso Blog
   */
  Blog("B");
  

  private String permission;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return permission;
  }
  
  EnumPermissions(String permission)
  {
    this.permission = permission;
  }
  
  /**
   * Restituisce un EnunPermission in funzione della stringa passata
   * @param permission Stringa indicante il permesso
   * @return Un istanza di EnumPermissions valida. Se non è possibile
   * trasformare la stringa viene restituito EnumPermissions.Undefined
   */
  public static EnumPermissions getEnum( String permission )
  {
    for (EnumPermissions enumPermission : EnumPermissions.values())
    {
      if( enumPermission.getValue().equals(permission) )
        return enumPermission;
    }
    return EnumPermissions.Undefined;
  }

}