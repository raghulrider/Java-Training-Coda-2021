<%@page import="utility.ItemDAO"%>
<%@page import="utility.Items"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utility.EmployeeDAO"%>
<%@page import="utility.Employee"%>
<%@page import="utility.InvoiceMasterDAO"%>
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
.container {
	max-width: 80%;
	max-height: 80vh;
	overflow-y: scroll;
}

h5 {
	padding: 2px;
	margin: 2px;
	color: black;
	display: inline-block;
}

h6 {
	font-size: 1.1em;
	padding: 2px;
	margin: 2px;
	display: inline-block;
}

form{
display:inline-block;
margin-right: 8px;
}

.float {
	width: 100px;
	height: 50px;
	background: #1D7CF2;
	display: flex;
	align-items: center;
	right: 15px;
	justify-content: center;
	text-decoration: none;
	border-radius: 6px;
	color: #ffffff;
	font-size: 16px;
	box-shadow: 0 0 10px gray;
	position: fixed;
	bottom: 15px;
	transition: background 0.25s;
}
</style>
</head>
<form method="post" action="downloadbill.do">
	<input type="hidden" name="formid" value="downloadbill"> <input
		type="hidden" name="genPdf" value="yes">
	<button class="btn btn-primary" type="submit">Download Invoice
		as PDF</button>
</form>

<body>
	<%@ include file="header.jsp"%>
	<c:set var="employeeId" scope="session"
		value="${sessionScope.employeeId}" />
	<c:set var="employee"
		value="${EmployeeDAO.getEmployeeByEmployeeId(employeeId)}" />
	<c:set var="employeeName" value="${employee.getName()}" />
	<h6>
		Employee ID :
		<c:out value="${employeeId}" />
	</h6>
	<h6>
		Employee Name :
		<c:out value="${employeeName}" />
	</h6>
	<h2 style="text-align: center; display: block">All Invoices</h2>
	<div class="container">

		<c:set var="minvoices" value="${InvoiceMasterDAO.getAllInvoice()}" />

		<c:forEach items="${minvoices}" var="minvoice">
			<div class="card bg-primary text-white">
				<div class="card-body">
					<h5>Bill No :</h5>
					<h6>${minvoice.getBillno()}</h6>
					<h5>Customer ID :</h5>
					<h6>${minvoice.getCustomerid()}</h6>
					<h5>Bill Date :</h5>
					<h6>${minvoice.getBilldate()}</h6>
					<form method="post" action="downloadbill.do">
						<input type="hidden" name="formid" value="downloadindividualbill"> 
						<input type="hidden" name="genPdf" value="yes">
						<input type="hidden" name="billno" value="${minvoice.getBillno()}">
						<button class="btn btn-danger" type="submit">Download Invoice as PDF</button>
					</form>
					<form method="post" action="downloadbill.do">
						<input type="hidden" name="formid" value="downloadindividualbill">
						<input type="hidden" name="genExcel" value="yes">
						<input type="hidden" name="billno" value="${minvoice.getBillno()}">
						<button class="btn btn-danger" type="submit">Download Invoice as Excel</button>
					</form>
				</div>
			</div>
			<br>
		</c:forEach>
	</div>
</body>
</html>
