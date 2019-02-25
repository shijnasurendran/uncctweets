

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
<link rel="stylesheet" href="styles/main.css" type="text/css" />
</head>
<body>
	<%@include file="headerAdmin.jsp"%>
	<section class="container">
		<h1>Delete User</h1>
		<c:forEach items="${usrdelList}" var="u">
			<table>
				<tr>
					<form action="membershipServlet" method="post">
						<input type="hidden" name="action" value="deleteUser"> <input
							type="hidden" name="useremail" value="${u.getEmail()}">
						<td><label>${u.getFullName()}</label></td>
						<td>
							<button type="submit">Delete</button>
						</td>
					</form>
			</table>
			</tr>
		</c:forEach>
	</section>
	<%@include file="footer.jsp"%>
</body>
</html>
