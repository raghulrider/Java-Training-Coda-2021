<%@page import="utility.ItemDAO"%>
<%@page import="utility.Items"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utility.EmployeeDAO"%>
<%@page import="utility.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>Happy Shopping</title>
<%@ include file="styles.jsp"%>
<style>
.float{
	width : 100px;
	height:50px;
	background:#1D7CF2;
	display:flex;
	align-items:center;
	right:15px;
	justify-content:center;
	text-decoration:none;
	border-radius:6px;
	color:#ffffff;
	font-size:16px;
	box-shadow: 0 0 10px gray;
	position:fixed;
	bottom:15px;
	transition: background 0.25s;
}
</style>
</head>
<body>
	<%@ include file="header.jsp"%>
	<c:set var="employeeId" scope="session"
		value="${sessionScope.employeeId}" />
	<c:set var="employee"
		value="${EmployeeDAO.getEmployeeByEmployeeId(employeeId)}"/>
		<c:set var="employeeName" value="${employee.getName()}"/>
	<h6>
		Employee ID :
		<c:out value="${employeeId}" />
	</h6>
	<h6>
		Employee Name :
		<c:out value="${employeeName}" />
	</h6>
	<c:set var="items" value="${ItemDAO.getAllItems()}" />
	<form action="shopping.do" method="post">
		<input type="hidden" name="formid" value="shopping">
		<div class="main">
			<ul class="cards">
				<c:forEach items="${items}" var="item">
					<li class='cards_item'>
						<div class='card'>
							<div class='card_image'>
								<img src='assets/${item.getImageurl()}' />
							</div>
							<div class='card_content'>
								<h1 class='card_title'>
									<c:out value="${item.getItemname()}" />
								</h1>
								<h2 class='card_text'>
									₹<c:out value="${item.getPrice()}" />
									/
									<c:out value="${item.getUnit()}" />
								</h2>
								<input class='quantity' type='number' name='${item.getItemid()}'
									min='0' value='0'>
							</div>
						</div>
					</li>
				</c:forEach>

			</ul>
		</div>
		<button class="float" type="submit">Customer Entry</button>
	</form>
</body>
</html>
