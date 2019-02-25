<%@ page import="java.util.GregorianCalendar, java.util.Calendar" %>
<%  
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
    int currentMonth = currentDate.get(Calendar.MONTH) + 1;
    int currentYear = currentDate.get(Calendar.YEAR);
%>
<html>
<head><link rel="stylesheet" href="styles/footercss.css" type="text/css"/></head>
<body>
    <section class="demo"></section>
    <section class="footer">
      	<p><img alt="UNCC" src="images/unc-charlotte-logo.gif" align="left" width=100px;>
      	Product of UNC Charlotte</p>
    	<a target="_blank" title="follow me on twitter" href="http://www.twitter.com/"><img alt="follow me on twitter" src="//login.create.net/images/icons/user/twitter_30x30.png" border=0> </a>
    
    </section>
</body>
</html>