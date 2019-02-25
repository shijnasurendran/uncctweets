<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HeaderAdmin</title>
        <link rel="stylesheet" href="styles/headercss.css" type="text/css"/>
        <script src="includes/headerjs.js"></script>
    </head>
    <body>
       <ul class="topnav" id="myTopnav">
            <li><a target="_blank" title="follow me on mysocio" href="http://www.twitter.com/"><img alt="follow me on twitter" src="images/new-twitter-bird.png" width=25px, height=25px border=0></a></li>
  			<li><a href="<c:url value='/membershipServlet?action=logout'/>" >Logout </a></li> 
			<li><a href="javascript:void(0);" onclick="myFunction()">&#9776;</a></li>
        </ul>       
    </body>
</html>
