

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HashTag Page</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
	<%@include file="header.jsp" %>
        <section class="container">
        <h1>Hash Tags</h1>
  <c:forEach items ="${hashTags}" var ="eachHashTag" >       
                <section class="w3-container w3-card-2 w3-white w3-round w3-margin"><br>
                <p><img src="uploadfiles/${eachHashTag.picfilepath}" width="50" height="50"/> ${eachHashTag.fullName} [@${eachHashTag.nickName}]: ${eachHashTag.date} </p>
        <p>${eachHashTag.text} </p>
         </section>
</c:forEach> 
	</section> 	
		<%@include file="footer.jsp" %> 
    </body>
</html>

