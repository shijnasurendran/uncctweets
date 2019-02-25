<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-blue-grey.css">
        <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
        <link rel="stylesheet" href="styles/home.css" type="text/css"/>
    </head>
   <body class="w3-theme-l5">
       <%@include file="header.jsp" %>
       <script src="includes/main.js"></script>
        <section class="w3-container w3-content">
  <!-- The Grid -->
  <section class="w3-row">
    <!-- Left Column -->
    <section class="w3-col m3">
      <!-- Profile -->
      <section class="w3-card-2 w3-round w3-white">
        <section class="w3-container">
         <h4 class="w3-center">Welcome, ${theUser.fullName}</h4>
         <p class="w3-center">
         
 
<!--    <form method="post" action="Profilepicservlet"
        enctype="multipart/form-data">
         <img src="img/Shijna.jpg" class="w3-circle" style="height:106px;width:106px" alt="Avatar">
        Select file to upload: <input type="file" name="file" size="60" /><br />
        <br /> <input type="submit" value="Upload" />
    </form>-->

<!-- image upload code begins -->
<!--<form method="post" action="Profilepicservlet"
        enctype="multipart/form-data">
                     <img width="240" height="240"/>
  <input type="file" name="file" size="60" /><br />
        <br /> <input type="submit" value="Upload" />
        
                  
             <img src="uploadfiles/${theUser.picfilepath}" width="240" height="240"/>
</form>-->
         <!-- image upload code ends -->
       <!--   <img src="uploadfiles/${theUser.picfilepath}" width="200" height="240"/> -->
       <img src= "images/profile.png" width="200" height="240"/>
        </p>
         <hr>
         <p><i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i>${theUser.fullName}</p>
         <p><i class="fa fa-home fa-fw w3-margin-right w3-text-theme"></i>@ ${theUser.nickName} </p>
         <p><i class="fa fa-birthday-cake fa-fw w3-margin-right w3-text-theme"></i> No of tweets : ${tweetCount}</p>
         <p><i class="fa fa-birthday-cake fa-fw w3-margin-right w3-text-theme"></i> Following Count : ${followingCount}</p>
         <p><i class="fa fa-birthday-cake fa-fw w3-margin-right w3-text-theme"></i> No of followers : ${followersCount}</p>
         
             
        </section>
      </section>
      <br>
        <section class="w3-card-2 w3-round w3-white w3-hide-small">
        <section class="w3-container">
          <p>Trends</p>
          </section>
      </section>
      <br>
      <!-- End Left Column -->
    </section>
    <!-- Middle Column -->
   <section class="w3-col m7">
    
      <section class="w3-row-padding">
        <section class="w3-col m12">
          <section class="w3-card-2 w3-round w3-white">
            <section class="w3-container w3-padding">
              <h6 class="w3-opacity">Good Day!!!!</h6>
              <!--<p contenteditable="true" class="w3-border w3-padding"></p>-->
              <form action="tweetServlet" method="post">
            <input type="hidden" name="action" value="tweet">
            <!--<input type="text" name="tweetText"><br>-->
             <textarea rows="4" cols="60" type="text" maxlength="200" name="tweetText"></textarea><br>
            <!--<input type="submit" value="Tweet">-->
            <input type="submit" value="Tweet" class="w3-btn w3-theme">
            </form>
            </section>
          </section>
        </section>
      </section>
       
        <c:forEach items ="${tweetUserDet}" var ="eachtweet" >
		<c:if test="${theUser.email == eachtweet.email}"> 
            <section class="w3-container w3-card-2 w3-white w3-round w3-margin"><br>
                <p> ${eachtweet.fullName} [@${eachtweet.nickName}]: ${eachtweet.date} </p>
        <p>${eachtweet.text} </p>
            	<form action="tweetServlet" method="post">
            <input type="hidden" name="action" value="deleteTweet">
            <input type="hidden" name="tweetIdStr" value="${eachtweet.tweetid}" >
			<input type="submit" value="Delete">
			 </form>
         </section>
		 </c:if>
    </c:forEach>
        
        <c:forEach items ="${tweetUserDet}" var ="eachtweet" >
            <c:if test="${theUser.email != eachtweet.email}"> 
            <section class="w3-container w3-card-2 w3-white w3-round w3-margin"><br>
                <p> ${eachtweet.fullName} [@${eachtweet.nickName}]: ${eachtweet.date} </p>
        <p>${eachtweet.text} </p>
         </section>
         </c:if>
    </c:forEach>
    </section>
    
    
     <!-- Right Column -->
    <section class="w3-col m2">
      <section class="w3-card-2 w3-round w3-white w3-center">
        <section class="w3-container">
            <p><b>Who to follow</b></p>
          <c:forEach items ="${followers}" var ="eachfollowers" >
        <p>
        <%--<img src="uploadfiles/${eachfollowers.picfilepath}" width="25" height="25"/>  --%>
        ${eachfollowers.fullName} @${eachfollowers.nickName}</p>
        <form action="membershipServlet" method="post">
            <input type="hidden" name="action" value="deleteFollow">
            <input type="hidden" name="followEmail" value="${eachfollowers.email}" >
            <input type="submit" value="UnFollow">
        </form>
        </c:forEach>
         <c:forEach items ="${nonFollowers}" var ="eachnonFollowers" >
        <p>
        <%--<img src="uploadfiles/${eachnonFollowers.picfilepath}" width="25" height="25"/>  --%>
        ${eachnonFollowers.fullName} @${eachnonFollowers.nickName}</p>
        <form action="membershipServlet" method="post">
            <input type="hidden" name="action" value="insertFollow">
            <input type="hidden" name="nonFollowEmail" value="${eachnonFollowers.email}" >
            <input type="submit" value="Follow">
        </form>
        </c:forEach>
        </section>
      </section>
      <br>
       <!-- End Right Column -->
     </section>
     
     <!-- End Page Container -->
 </section>
<br>
         <%@include file="footer.jsp" %>
    </body>
   
   
</html>
