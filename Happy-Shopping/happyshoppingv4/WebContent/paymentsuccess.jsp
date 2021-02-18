<%@page import="utility.ExcelGenerator"%>
<%@page import="utility.PDFGenerator"%>
<%@page import="utility.DatabaseMaster"%>
<%@page import="utility.Bill"%>
<%@page import="utility.CustomerDAO"%>
<%@page import="utility.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
.container{
text-align:center;
padding:30px;
margin:auto;
}
</style>
<meta charset="UTF-8">
<title>Thank You | Happy Shopping</title>
<%@ include file="styles.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
<c:set var="customerId" value="${sessionScope.customerId}" scope="session"/>
<c:set var="customerName" value="${CustomerDAO.getCustomerByCustomerId(customerId).getCustomerName()}"/>
<h2>
Thank you Mr/Ms.<c:out value="${customerName}"/> for shopping with us!
</h2>
<br><br>
<form method="post" action="downloadbill.do">
<input type="hidden" name="formid" value="downloadbill">
<input type="hidden" name="genPdf" value="yes">
<button class="btn btn-primary" type="submit">Download Invoice as PDF</button>
</form>
<br>
<form method="post" action="downloadbill.do">
<input type="hidden" name="formid" value="downloadbill">
<input type="hidden" name="genExcel" value="yes">
<button class="btn btn-primary" type="submit">Download Invoice as Excel</button>
</form>
<br>
<form method="post" action="gotoshop.do">
<input type="hidden" name="formid" value="gotoshop">
<button class="btn btn-success" type="submit">Go to Shop</button>
</form>
</div>
</body>
</html>