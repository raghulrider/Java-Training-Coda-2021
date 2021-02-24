<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Register | Happy Shopping</title>
<%@ include file="styles.jsp"%>
</head>
<body>
<%@ include file="header.jsp"%>
	<div class="back">
		<div class="div-center">
			<div class="content">
				<h3>Customer - Register</h3>
				<hr />
				<form method="post" action="registercustomer">
					<div class="form-group">
						<label for="name">Customer name</label>
						<input type="text"
								class="form-control" 
								id="name"
								name="ncustomerName" 
								placeholder=" name">
					</div>
					<div class="form-group">
					<label for="number">Phone Number</label> 
					<input type="text" 
						class="form-control" 
						id="number" 
						name="ncustomerPhoneNumber"
						placeholder=" phone number">
					</div>
					<div class="form-group">
					<label for="address">Address</label> 
					<textarea 
						class="form-control" 
						id="address" 
						name="ncustomerAddress"
						placeholder="address"></textarea>
					</div>
					<hr>
					<c:set var="errors" scope="session" value="${sessionScope.customerErrors}"/>
						<c:set var="errorSet" value="${errors.getErrors()}"/>
							<c:forEach var="error" items="${errorSet}">
								<div class='card bg-danger text-white'>
									<div class='card-body'> 
									<c:out value="${error.getError()}"/>
									</div>
								</div>
							</c:forEach>
						<c:remove var="customerErrors" scope="session"/>
					<input type="hidden" name="formid" value="registercustomer">
					<button type="submit" class="btn btn-primary">Billing</button>
				</form>
			</div>
			</div>
		</div>
</body>
</html>