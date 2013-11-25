package it.newmedia.social.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;


public class SecurityService
{
  private static final String SECURITY_PREFIX = "security.";
  private Properties securityProperties;

  @Autowired
  public void setSecurityProperties(final Properties securityProperties)
  {
    this.securityProperties = securityProperties;
  }

  public boolean login(String inputUsername, String inputPassword)
  {
    String password = securityProperties.getProperty(SECURITY_PREFIX + inputUsername, "");
    return !StringUtils.isEmpty(password) && password.equalsIgnoreCase(inputPassword);
  }
}
