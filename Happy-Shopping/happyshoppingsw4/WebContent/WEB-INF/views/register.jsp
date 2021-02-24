<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Register | Happy Shopping</title>
<%@ include file="styles.jsp"%>
</head>
<body>
	<div class="back">
		<div class="div-center">
			<div class="content">
				<h3>Happy Shoppping - Register</h3>
				<hr />
				<form method="post" action="register">
					<div class="form-group">
						<label for="employeeid">Employee ID</label>
						<input type="text"
								class="form-control" 
								id="employeeid"
								name="employeeId" 
								placeholder=" id">
					</div>
					<div class="form-group">
						<label for="name">Employee name</label>
						<input type="text"
								class="form-control" 
								id="name"
								name="employeeName" 
								placeholder=" name">
					</div>
					<div class="form-group">
					<label for="password">New Password</label> 
					<input type="password" 
						class="form-control" 
						id="password" 
						name="password"
						placeholder="password">
					</div>
					<button type="submit" class="btn btn-primary">Register</button>
					<hr />
					
					<c:set var="errors" scope="session" value="${sessionScope.registrationErrors}"/>
						<c:set var="errorSet" value="${errors.getErrors()}"/>
							<c:forEach var="error" items="${errorSet}">
								<div class='card bg-danger text-white'>
									<div class='card-body'> 
									<c:out value="${error.getError()}"/>
									</div>
								</div>
							</c:forEach>
						<c:remove var="registrationErrors" scope="session"/>		
				</form>
				<form method="get" action="gotologin">
						<button type="submit" class="btn btn-link">Login</button>
					</form>
			</div>
			</div>
			</div>
</body>
</html>