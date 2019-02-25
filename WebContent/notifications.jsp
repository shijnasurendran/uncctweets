

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notifications Page</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
	<%@include file="header.jsp" %>
        <section class="container">
        <h1>Notifications</h1>
  <c:forEach items ="${notifications}" var ="eachNotifyRec" >
      
            <c:if test="${eachNotifyRec.notifyType == 'tweet'}" >
                <section class="w3-container w3-card-2 w3-white w3-round w3-margin"><br>
                <p>
                <%--<img src="uploadfiles/${eachNotifyRec.profilePic}" width="50" height="50"/> --%>
                 ${eachNotifyRec.fullName} @${eachNotifyRec.nickName} mentioned you ${eachNotifyRec.date} </p>
                <p>${eachNotifyRec.text} </p>
                </section>
            </c:if>
            <c:if test="${eachNotifyRec.notifyType == 'follow'}" >
                <section class="w3-container w3-card-2 w3-white w3-round w3-margin"><br>
                    <p> ${eachNotifyRec.fullName} @${eachNotifyRec.nickName} followed you ${eachNotifyRec.date}</p>
                   <%-- <p><img src="uploadfiles/${eachNotifyRec.profilePic}" width="50" height="50"/></p> --%>
                </section>   
            </c:if>
</c:forEach> 
	</section> 	
		<%@include file="footer.jsp" %> 
    </body>
</html>
