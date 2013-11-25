
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@attribute name="vote" required="true" type="java.lang.Double"%>


<%
    for (double var=0; var<5; var++)
    {
      if (var<vote) 
      {
        if (vote-var >0 && vote-var<0.6)
        {
          %> <img src="images/icon_star_half.gif" alt="*" /> <%;
        }
        else
        {
          %> <img src="images/icon_star.gif" alt="*" /> <%;
        }
      }   
      if (var>=vote) %> <img src="images/icon_star_empty.gif" alt="(*)" /> <%;
   }
%> ${vote}


 

   