<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Details | Happy Shopping</title>
<%@ include file="styles.jsp"%>
</head>
<body>
<%@ include file="header.jsp"%>
	<div class="back">
		<div class="div-center">
			<div class="content">
				<h3>Customer Entry</h3>
				<form method="post" action="customerentry">
					<div class="form-group">
					<label for="customerid">Customer ID</label>
					<input type="text"
							class="form-control" id="customerid" name="customerId"
							placeholder=" id">
							<br><span style="margin-left: 45%">or</span><br><br>
							<label for="customerphonenumber">Phone Number</label>
					<input type="text"
							class="form-control" id="customerphonenumber" name="customerPhoneNumber"
							placeholder=" phone number">
					</div>
					<input type="hidden" name="formid" value="customer">
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
					<input type="checkbox" id="newcustomer" name="newCustomer" value="yes">
					<label for="newcustomer">New Customer</label><br>
					<button type="submit" class="btn btn-primary">Billing</button>
					<hr/>
				</form>
			</div>
			</div>
		</div>
</body>
</html>