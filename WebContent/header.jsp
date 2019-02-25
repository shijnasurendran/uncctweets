<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
        <link rel="stylesheet" href="styles/headercss.css" type="text/css"/>
        <script src="includes/headerjs.js"></script>
    </head>
    <body>
       <ul class="topnav" id="myTopnav">
            <li><a target="_blank" title="follow me on UNCC TWITTER" href="http://www.twitter.com/"><img alt="follow me on twitter" src="images/new-twitter-bird.png" width=50px, height=50px border=0></a></li>
          
            <c:if test="${theUser != null}">
                <li><a href="home.jsp">Home</a></li>
                <li><a href="notifications.jsp">Notifications</a></li>
                <li><a href="signup.jsp">Profile</a></li>
            </c:if>
            <c:if test="${theUser != null}">
            <li class="floatRight"><a href="#">${theUser.fullName}</a></li>
            <li class="floatRight"><a href="#">${theUser.email}</a></li>
            <li class="floatRight"><a href="<c:url value='/membershipServlet?action=logout'/>">Logout</a></li>
            <li class="icon">
            </c:if>
<!--            <c:if test="${theUser != null}">
                <li><a href="<c:url value='/membershipServlet?action=logout'/>" >Logout </a></li> 
            </c:if> -->
            <a href="javascript:void(0);" onclick="myFunction()">&#9776;</a>
            </li>
        </ul>
        
    </body>
</html>
