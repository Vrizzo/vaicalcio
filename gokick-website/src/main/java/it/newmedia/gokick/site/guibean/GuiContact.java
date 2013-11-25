/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.newmedia.gokick.site.guibean;

import java.util.Comparator;

/**
 *
 * @author newmedia
 */
public class GuiContact implements Comparable
{

  private String email;
  private String name;
  private boolean registered;

  

  public GuiContact(String email)
  {
    this.email = email.trim();
    this.name = "";
  }
  
  public GuiContact(String email,String name)
  {
    this.email = email.trim();
    this.name = name.trim();
  }
  
  public GuiContact(String email,String name,boolean registered)
  {
    this.email = email.trim();
    this.name = name.trim();
    this.registered=registered;
  }

  /**
   * @return the email
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public int compareTo(Object o)
  {
    if (o != null && o.getClass().equals(GuiContact.class))
    {
      GuiContact item = (GuiContact) o;
      return this.getEmail().compareTo(item.getEmail());
    }
    return 0;
  }

  public int compareTo(GuiContact another)
  {
    if (another != null)
      return this.getEmail().compareTo(another.getEmail());
    return 0;
  }
  
  public static final Comparator<GuiContact> NAME_ORDER =
          new Comparator<GuiContact>()
          {
            public int compare(GuiContact o1, GuiContact o2)
            {
              return o1.getName().compareToIgnoreCase(o2.getName());
            }

          };        
  
  public static final Comparator<GuiContact> MAIL_ORDER =
          new Comparator<GuiContact>()
          {
            public int compare(GuiContact o1, GuiContact o2)
            {
              return o1.getEmail().compareToIgnoreCase(o2.getEmail());
            }
          };        
                  
                  
           
            
            
            
            
  
  

  /**
   * @return the registered
   */
  public boolean isRegistered()
  {
    return registered;
  }

  /**
   * @param registered the registered to set
   */
  public void setRegistered(boolean registered)
  {
    this.registered = registered;
  }
}
