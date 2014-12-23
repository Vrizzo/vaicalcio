package it.newmedia.gokick.site.managers.scheduler;

import org.junit.Test;

import static org.junit.Assert.*;

public class WakeUpUserManagerTest
{

  @Test
  public void testRun() throws Exception
  {
    WakeUpUserManager wakeUpUserManager = new WakeUpUserManager();
    wakeUpUserManager.run();
  }
}