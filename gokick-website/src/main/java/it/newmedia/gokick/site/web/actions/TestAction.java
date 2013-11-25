package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.utils.DateUtil;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * Classe di monitoraggio connessione DB
 */
public class TestAction extends ActionSupport
{

  private String startAt;
  private String endAt;
  private String duration;
  private String data;

  public String getData()
  {
    return data;
  }

  public void setData(String data)
  {
    this.data = data;
  }

  public String getEndAt()
  {
    return endAt;
  }

  public void setEndAt(String endAt)
  {
    this.endAt = endAt;
  }

  public String getStartAt()
  {
    return startAt;
  }

  public void setStartAt(String startAt)
  {
    this.startAt = startAt;
  }

  public String getDuration()
  {
    return duration;
  }

  public void setDuration(String duration)
  {
    this.duration = duration;
  }


  public String provinceList() throws Exception
  {
    StringBuilder sb = new StringBuilder();
    Date start = new Date();
    /////////////////////////////////////////////

    sb.append("Eseguo una query su tutte le province (italia)").append("<br/>");
    List<Province> provinceList = getAllOrdered(1, false, false);
    sb.append("Fatto: ").append(provinceList.size()).append("<br/>");

    sb.append("Leggo 50 oggetti provincia ").append("<br/>");
    for (int i = 50; i < 100; i++)
    {
      Province p = DAOFactory.getInstance().getProvinceDAO().get(i);
      sb.append(" ").append(p.getName());
    }
    sb.append("<br/>").append("Fatto: ").append("<br/>");


    InfoProvider.getUserInfo(126);


    /////////////////////////////////////////////
    Date end = new Date();
    this.duration = DurationFormatUtils.formatDurationHMS(end.getTime() - start.getTime());
    this.data = sb.toString();
    this.startAt = DateUtil.formatDate(start, "HH:mm:sss");
    this.endAt = DateUtil.formatDate(end, "HH:mm:sss");
    return SUCCESS;
  }

  public String sportCenter() throws Exception
  {
    StringBuilder sb = new StringBuilder();
    Date start = new Date();
    /////////////////////////////////////////////

    sb.append("Prendo un campo ").append("<br/>");
    SportCenter s = DAOFactory.getInstance().getSportCenterDAO().get(new Integer(1));
    sb.append("Fatto: ").append("<br/>");

    /////////////////////////////////////////////
    Date end = new Date();
    this.duration = DurationFormatUtils.formatDurationHMS(end.getTime() - start.getTime());
    this.data = sb.toString();
    this.startAt = DateUtil.formatDate(start, "HH:mm:sss");
    this.endAt = DateUtil.formatDate(end, "HH:mm:sss");
    return SUCCESS;
  }


  private List<Province> getAllOrdered(Integer idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters) throws Exception
  {

    Criteria criteria = HibernateSessionHolder.getSessionPerRequestSession().createCriteria(Province.class);
    if (idCountry > 0)
    {
      criteria.add(Restrictions.eq("country.id", idCountry));
    }
    if (onlyWithUsers)
    {
      criteria.createCriteria("userList").add(Restrictions.and(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()), Restrictions.eq("anonymousEnabled", false)));
    }
    if (onlyWithSportCenters)
    {
      criteria.createCriteria("sportCenterList").add(Restrictions.and(Restrictions.eq("enabled", true), Restrictions.eq("approved", true)));
    }
    criteria.addOrder(Order.asc("name"));
    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

    return criteria.list();
  }

  public String thread() throws Exception
  {
    StringBuilder sb = new StringBuilder();
    Date start = new Date();
    /////////////////////////////////////////////

    sb.append("Lancio un thread ").append("<br/>");

    Runnable runnable = new Runnable()
    {
      @Override
      public void run()
      {
        for (int i = 0; i < 10; i++)
        {
          System.out.println(i + " - Sono la thread " + Thread.currentThread().getId());
          try
          {
            Thread.sleep(1000);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }
        }
      }
    };
    new Thread(runnable).start();
    sb.append("Fatto: ").append("<br/>");

    /////////////////////////////////////////////
    Date end = new Date();
    this.duration = DurationFormatUtils.formatDurationHMS(end.getTime() - start.getTime());
    this.data = sb.toString();
    this.startAt = DateUtil.formatDate(start, "HH:mm:sss");
    this.endAt = DateUtil.formatDate(end, "HH:mm:sss");
    return SUCCESS;
  }

}