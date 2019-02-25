

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout Page</title>
        <link rel="stylesheet" href="styles/logincss.css" type="text/css"/>
        <script src="includes/logoutScript.js"></script>
    </head>
    <body>
	<%@include file="header.jsp" %>
        <h1>Log Out</h1>
        <p>Thanks for log into the system</p>
        <li>Do you want to Log into the system <a href="index.jsp">Login</a></li>
        &nbsp;
        <li>Do you want to create a new account <a href="signup.jsp">Sign Up</a></li>
		<%@include file="footer.jsp" %> 
    </body>
</html>
