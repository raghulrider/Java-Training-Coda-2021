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

form {
	display: inline-block;
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

.main {
	width: 70%;
	margin: 50px auto;
}

.has-search .form-control {
	padding-left: 2.375rem;
}

.has-search .form-control-feedback {
	position: absolute;
	z-index: 2;
	display: block;
	width: 2.375rem;
	height: 2.375rem;
	line-height: 2.375rem;
	text-align: center;
	pointer-events: none;
	color: #aaa;
}
</style>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h2 style="text-align: center; display: block">All Invoices</h2>
	<div class="main">
		<form method="post" action="allreport.do" style="display: block">
			<div class="input-group">
				<input type="hidden" name="formid" value="allreport"> <input
					type="text" class="form-control"
					placeholder="Search using following syntax... bxxxx, Cxxxxx, DD-MM-YYY..."
					name="searchKey">
				<div class="input-group-append">
					<button class="btn btn-primary" type="submit">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</div>
			<div class="form-check" style="display:block;text-align:center;">
				<label class="form-check-label"> 
				<input type="checkbox" name="sbd" value="true"> Sort by Date (Descending)
				</label>
			</div>

		</form>
	</div>
	<div class="container">

		<c:set var="minvoices" scope="session"
			value="${sessionScope.minvoices}" />

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
						<input type="hidden" name="genPdf" value="yes"> <input
							type="hidden" name="billno" value="${minvoice.getBillno()}">
						<button class="btn btn-danger" type="submit">Download
							Invoice as PDF</button>
					</form>
					<form method="post" action="downloadbill.do">
						<input type="hidden" name="formid" value="downloadindividualbill">
						<input type="hidden" name="genExcel" value="yes"> <input
							type="hidden" name="billno" value="${minvoice.getBillno()}">
						<button class="btn btn-danger" type="submit">Download
							Invoice as Excel</button>
					</form>
				</div>
			</div>
			<br>
		</c:forEach>
	</div>
</body>
</html>
