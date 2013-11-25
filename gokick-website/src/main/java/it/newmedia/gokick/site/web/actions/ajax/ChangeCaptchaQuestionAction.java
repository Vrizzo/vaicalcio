package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.web.actions.ABaseActionSupport;

/**
 *
 *Classe contenente le azioni per visualizzare e validare la domanda di sicurezza richiesta in registrazione
 */
public class ChangeCaptchaQuestionAction extends ABaseActionSupport
{

  //private static Logger logger = Logger.getLogger(ChangeCaptchaQuestionAction.class);
  private int captchaValue1;
  private int captchaValue2;
    

  @Override
  public String execute()
  {
    this.captchaValue1 = (int)(Math.random() * 10) + 1;
    this.captchaValue2 = (int)(Math.random() * 10) + 1;
    return SUCCESS;
  }

  public int getCaptchaValue1()
  {
    return captchaValue1;
  }

  public void setCaptchaValue1(int captchaValue1)
  {
    this.captchaValue1 = captchaValue1;
  }

  public int getCaptchaValue2()
  {
    return captchaValue2;
  }

  public void setCaptchaValue2(int captchaValue2)
  {
    this.captchaValue2 = captchaValue2;
  }

}
