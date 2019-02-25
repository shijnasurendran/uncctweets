
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="styles/logincss.css" type="text/css"/>
    </head>
    <body>
	<%@include file="header.jsp" %>
        <table>
        <tr>
        <td><img ID="HomePageWeb" alt="web" src="images/1.jpg">
        </td>
        
        <td>
        <section class="nav">
        <c:if test="${message != null}">
            <p><i>${message}</i></p>
        </c:if>
        <form action="membershipServlet" method="post">
            
            <input type="hidden" name="action" value="logIn">
			<table>
			<tr><td><label class="pad_top">Email: </label></td>
            <td><input type="email" name="email" class="email" placeholder="Email" ></td></tr>
            <tr><td><label class="pad_top">Password: </label></td>
           <td> <input type="password" class="password" placeholder="password" name="password"></td></tr>
            <!--<input type="submit" value="Login" class="button" ><br>-->
            <tr><td><input type="submit" value="Login" class="button" ></td>
            <td><label>
            <input type="checkbox" name="rememberMe" checked>Remember me &nbsp;
            <a href="forgotpassword.jsp">Forgot password?</a></label></td></tr>
            <tr>
            <td></td>
            <td>New ? &nbsp; <a href="signup.jsp">Sign up now</a></td></tr></table>
        </form>
		</section>
		</td></tr>
		</table>
		<%@include file="footer.jsp" %> 
    </body>
</html>
