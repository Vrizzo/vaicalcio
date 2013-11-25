<%@ page pageEncoding="UTF-8" contentType="text/html"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.mail.*"%>
<%@ page import="javax.mail.internet.*"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.*"%>
<%!//
  //CAMBIARE QUESTI VALORI IN FUNZIONE DELL'APPLICAZIONE DA GESTIRE (tutto ciò che inizia con XXX)
  public static final String SITE_URL = "www.gokick.org";
  public static final String SECURITY = "gokick-org";
  public static final String MAIL_FROM = "noreply@gokick.org";
  public static final String MAIL_SMTP_SERVER = "smtp.gokick.it";
  public static final String MAIL_USERNAME = "noreply/gokick.it";
  public static final String MAIL_PASSWORD = "maradona";
  public static final boolean MAIL_ENABLE_SSL = false;
  public static final boolean MAIL_ENABLE_AUTH = true;
  public static final int MAIL_SMTP_SERVER_PORT = 25 ;
  public static final String MAIL_SUBJECT = "Monitoring " + SITE_URL;
  public static final String MAIL_BODY = "Monitoring " + SITE_URL;
  public static final String JDBC_ALIAS = "jdbc/gokick";
  /**
   * QUERY E ALTRO...
   */
  public static final String QUERY_UPDATE_CHECK = "UPDATE `SITE_MONITOR` SET `LAST_MONITOR`=?,`CURRENT_MONITOR`=? WHERE `CURRENT_MONITOR`=?";
  public static final String QUERY_READ_CHECK = "SELECT CURRENT_MONITOR from SITE_MONITOR";
  public static final String QUERY_CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS `SITE_MONITOR` ( `LAST_MONITOR` datetime default NULL, `CURRENT_MONITOR` datetime default NULL default NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8; ";
  public static final String QUERY_CREATE_TABLE_INSERT_FIRST = "INSERT INTO `SITE_MONITOR` (`LAST_MONITOR`, `CURRENT_MONITOR`) VALUES (CURRENT_DATE(), CURRENT_DATE() );";
  public static final String ENV_CONTEXT = "java:comp/env";
  public static final String APPLICATION_SERVER = "Tomcat 6.0.18";
  public static final String APPLICATION_SERVER_STATUS = "OK";
%>
<%
    String security = request.getParameter("security");

    String databaseRead = "Last monitor was at: 28/09/2009 14:30:23";
    String databaseReadStatus = "OK";
    String databaseUpdate = "Last monitor has been correctly updated";
    String databaseUpdateStatus = "OK";
    String mailSend = "Mail correctly sent to .....";
    String mailSendStatus = "OK";
    String errorFound = "";

    if (SECURITY.equals(security) == false)
    {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
    else
    {
      /**
       * CHECK if SITE_MONITOR table exist
       */
      try
      {
        createMonitorIfNotExist();
      }
      catch (Exception e)
      {
        errorFound += e.getMessage() + "<br/>";
        databaseReadStatus = "KO";
      }
      /**
       * CHECK databaseReadStatus
       */
      try
      {
        databaseRead = readCheck();
      }
      catch (Exception e)
      {
        databaseRead = e.getMessage();
        errorFound += e.getMessage() + "<br/>";
        databaseReadStatus = "KO";
      }


      /**
       * CHECK databaseUpdateStatus
       */
      try
      {
        databaseUpdate = updateCheck(databaseRead);
      }
      catch (Exception e)
      {
        errorFound += e.getMessage() + "<br/>";
        databaseUpdate = e.getMessage();
        databaseUpdateStatus = "KO";
      }
      /**
       * CHECK mailSendStatus
       */
      String[] email = request.getParameterValues("email");
      try
      {
        if (email != null && email.length != 0 && !email[0].equals(""))
        {
          sendMail(email);
          for (int i = 0; i < email.length; i++)
          {
            mailSend = mailSend + " " + email[i];
          }
        }
        else
        {
          mailSend = "No email to send";
        }
        mailSendStatus = "OK";
      }
      catch (Exception e)
      {
        errorFound += e.getMessage() + "<br/>";
        mailSend = e.getMessage();
        mailSendStatus = "KO";
      }
    }

%>
<%!

 public String readCheck() throws Exception
 {
   String foo = "Not Connected";
   Connection conn = null;

     Context initCtx = new InitialContext();
     if (initCtx == null )
     {
       throw new Exception("Boom - No Context");
     }

     Context envCtx = (Context) initCtx.lookup(ENV_CONTEXT);
     DataSource ds = (DataSource) envCtx.lookup(JDBC_ALIAS);

     if (ds != null) {
       conn = ds.getConnection();

       foo += "in fetch : conn is " + conn + "<br>" ;

       if (conn != null)
       {
         foo = "Got Connection "+conn.toString();
         Statement stmt = conn.createStatement();
         ResultSet rst =
         stmt.executeQuery(QUERY_READ_CHECK);
         foo = null;
         if (rst.next())
         {
           foo=rst.getTimestamp("CURRENT_MONITOR").toString();
         }
         try
         {
           rst.close();
         }
         catch (Exception e)
         {
           foo = foo + " " + e.getMessage();
         }

         try
         {
           conn.close();
         }
         catch (Exception e)
         {
           foo = foo + " " + e.getMessage();
         }

         try
         {
           stmt.close();
         }
         catch (Exception e)
         {
           foo = foo + " " + e.getMessage();
         }
       }
     }
     else {
       foo = " null connection";
     }

   return foo;
 }

%>
<%!

 public String updateCheck(String lastCheck) throws Exception
 {
   String foo = "Not Connected";
   Connection conn = null;

     Context initCtx = new InitialContext();
     if (initCtx == null )
     {
       throw new Exception("Boom - No Context");
     }

     Context envCtx = (Context) initCtx.lookup(ENV_CONTEXT);
     DataSource ds = (DataSource) envCtx.lookup(JDBC_ALIAS);

     if (ds != null) {
       conn = ds.getConnection();

       foo += "in fetch : conn is " + conn + "<br>" ;

       if (conn != null)
       {
         foo = "Got Connection "+conn.toString();
         PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE_CHECK);
         Timestamp lastCheckSqlDate =  Timestamp.valueOf(lastCheck);
         Timestamp currentCheckSqlDate =  new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());

         stmt.setTimestamp(1, lastCheckSqlDate);
         stmt.setTimestamp(2, currentCheckSqlDate);
         stmt.setTimestamp(3, lastCheckSqlDate);

         stmt.executeUpdate();
         foo = currentCheckSqlDate.toString();
         try
         {
           conn.close();
         }
         catch (Exception e)
         {
           foo = foo + " " + e.getMessage();
         }

         try
         {
           stmt.close();
         }
         catch (Exception e)
         {
           foo = foo + " " + e.getMessage();
         }
       }
     }
     else {
       foo = " null connection";
     }

   return foo;
 }

%>
<%!

public void createMonitorIfNotExist() throws Exception
{
    Connection conn = null;

    Context initCtx = new InitialContext();
    if (initCtx == null )
    {
      throw new Exception("Boom - No Context");
    }
    Context envCtx = (Context) initCtx.lookup(ENV_CONTEXT);
    DataSource ds = (DataSource) envCtx.lookup(JDBC_ALIAS);

    if (ds != null)
    {
      conn = ds.getConnection();
    }

    if (conn != null)
    {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(QUERY_CREATE_TABLE);
      stmt.executeUpdate(QUERY_CREATE_TABLE_INSERT_FIRST);
      try
      {
        stmt.close();
      }
      catch (Exception e)
      {
        throw e;
      }
      try
      {
        conn.close();
      }
      catch (Exception e)
      {
       throw e;
      }
    }
  }

%>
<%!
  public void sendMail(String recipients[]) throws MessagingException
  {

    Properties props = new Properties();
    Session session;

      //Set the host smtp address
      props.put("mail.smtp.host", MAIL_SMTP_SERVER);
      props.put("mail.smtp.port", MAIL_SMTP_SERVER_PORT);
      if(MAIL_ENABLE_SSL)
      {
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        props.put("mail.smtp.starttls.enable","true");
      }

      if(MAIL_ENABLE_AUTH)
      {
        props.put("mail.smtp.auth", "true");
        Authenticator auth = new SMTPAuthenticator();
        session = Session.getInstance(props, auth);
      }
      else
      {
        session = Session.getInstance(props, null);
      }

      props.put("mail.transport.protocol", "smtp");
      props.put("mail.debug", "true");

      // create a message
      Message msg = new MimeMessage(session);

      // set the from and to address
      InternetAddress addressFrom = new InternetAddress(MAIL_FROM);
      msg.setFrom(addressFrom);

      InternetAddress[] addressTo = new InternetAddress[recipients.length];
      for (int i = 0; i < recipients.length; i++) {
          addressTo[i] = new InternetAddress(recipients[i]);
      }
      msg.setRecipients(Message.RecipientType.TO, addressTo);

      // Setting the Subject and Content Type
      msg.setSubject(MAIL_SUBJECT);
      msg.setContent(MAIL_BODY, "text/plain");
      Transport.send(msg);
  }

  /**
   * SimpleAuthenticator is used to do simple authentication when the SMTP
   * server requires it.
   */
  private class SMTPAuthenticator extends javax.mail.Authenticator {

      public PasswordAuthentication getPasswordAuthentication() {
          String username = MAIL_USERNAME;
          String password = MAIL_PASSWORD;
          return new PasswordAuthentication(username, password);
      }
  }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html  xmlns="http://www.w3.org/1999/xhtml" xml:lang="IT" lang="IT">
  <head>
    <title><%= SITE_URL%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="robots" content="noindex,nofollow" />
    <meta name="author" content="NewMedia Solutions" />
    <style type="text/css">
      body{
        margin:0; padding:0;
        background:#FFFFFF;
        font: normal 11px Arial;
        color:#404040;
      }
      a, a:visited{color:#404040;}
      a:hover{color:#808080;}
      table{width:100%; border-collapse: collapse;}
      td, th{ text-align: left; padding:10px 10px 5px 10px;}

      th{background:#799ecb; color:#FFFFFF; border:none; border-bottom: solid 1px #eaeff8;  font-size:900;}
      td, th{border-bottom: solid 1px #eaeff8;}


      tr:hover td{background:#eaeff8;}
      h1{margin:0; padding:0; float: left; font: normal 20px Arial; color: #799ecb;}
      h2{margin:0; padding:5px 0 0 0; float: left; font: normal 15px Arial; color:#9ebbd9; float: right;}
      h2 a, h2 a:visited{color:#9ebbd9;}
      h2 a:hover{color:#808080;}
      big{font-size:13px; font-weight:900;}
      .clear{clear: both; margin:0; padding:0; font-size:0;}
      .wrapper{ margin:0 auto; width:980px; }
      .header{height:35px; padding:0 10px; margin:20px auto 0 auto; border-bottom: solid 10px #799ecb; }
      .service{font-weight:900;}
      .ok{color:#008000;}
      .ko{color:#FF1000;}
      .footer{border-top: solid 5px #9ebbd9; padding:0 10px; color:#808080;}
      .footer a, .footer a:visited{color:#808080;}
      .footer p{line-height:16px;}
    </style>
  </head>
  <body>


    <div class="wrapper">

      <div class="header">
        <h1><%= SITE_URL%></h1>
        <h2><a href="http://<%= SITE_URL%>" target="_blank"><%= SITE_URL%></a></h2>
      </div>

      <table>

        <tr>
          <th>Service</th>
          <th>Status</th>
          <th>Notes</th>
        </tr>

        <tr>
          <td class="service">Application server</td>
          <td><big class="<%= APPLICATION_SERVER_STATUS%>"><%= APPLICATION_SERVER_STATUS%></big></td>
          <td>
            <%= APPLICATION_SERVER%> -
            Processori
            <big><%= Runtime.getRuntime().availableProcessors()%> |</big>
            Memoria totale VM:
            <big><%= String.format("%,d KB", Runtime.getRuntime().totalMemory() / 1024)%> |</big>
            Memoria libera VM:
            <big><%= String.format("%,d KB", Runtime.getRuntime().freeMemory() / 1024)%> |</big>
            Memoria max che la VM cercherà di usare:
            <big><%= String.format("%,d KB", Runtime.getRuntime().maxMemory() / 1024)%> |</big>
          </td>
        </tr>
        <tr>
          <td class="service">Database-Read</td>
          <td><big class="<%= databaseReadStatus%>"> <%= databaseReadStatus%></big></td>
          <td><%= databaseRead%></td>
        </tr>
        <tr>
          <td class="service">Database-Update</td>
          <td><big class="<%= databaseUpdateStatus%>"> <%= databaseUpdateStatus%></big></td>
          <td><%= databaseUpdate%></td>
        </tr>
        <tr>
          <td class="service">Mail-Send</td>
          <td><big class="<%= mailSendStatus%>"> <%= mailSendStatus%></big></td>
          <td><%= mailSend%></td>
        </tr>
        <tr>
          <td class="service">ErrorMessage</td>
          <td colspan="2"><%=errorFound%></td>
        </tr>
      </table>
      <div class="footer">
        <p>
          <strong>NewMedia Solutions</strong><br />
          <a href="http://www.newmediasolutions.it" target="_blank">www.newmediasolutions.it</a>
        </p>
      </div>
    </div>


  </body>
</html>