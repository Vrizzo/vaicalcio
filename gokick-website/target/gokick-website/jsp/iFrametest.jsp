<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <body style="background:pink;">
    
      TEST CUSTOM CONTENT

      <ul>
          <li>user ID  :  <%= request.getParameter("userId") %></li>
          <li>language :  <%= request.getParameter("language") %></li>
          <li>position :  <%= request.getParameter("position") %></li>
          <li>section  :  <%= request.getParameter("section") %></li>
          <li>country  :  <%= request.getParameter("countryId") %></li>
          <li>province :  <%= request.getParameter("provinceId") %></li>
          <li>city     :  <%= request.getParameter("cityId") %></li>
      </ul>

      
    </body>
</html>
