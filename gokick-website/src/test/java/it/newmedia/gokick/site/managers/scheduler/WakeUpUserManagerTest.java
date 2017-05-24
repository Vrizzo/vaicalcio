package it.newmedia.gokick.site.managers.scheduler;

import org.junit.Test;


public class WakeUpUserManagerTest
{

  @Test
  public void testRun() throws Exception
  {
    WakeUpUserManager wakeUpUserManager = new WakeUpUserManager();
    wakeUpUserManager.run();
  }
}