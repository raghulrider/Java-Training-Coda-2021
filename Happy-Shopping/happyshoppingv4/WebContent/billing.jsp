<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="utility.Customer"%>
<%@page import="utility.CustomerDAO"%>
<%@page import="utility.Items"%>
<%@page import="utility.ItemDAO"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Billing | Happy Shopping</title>
<%@ include file="styles.jsp"%>
<style>
table {
	width: 100%;
}

.center {
	margin-left: auto;
	margin-right: auto;
}

h6 {
	right: 0;
}

.container {
	margin: auto;
	width: 80%;
	padding: 10px;
}
</style>

</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="container">
		<h2>Invoice</h2>
		<br>
		<br>
		<c:set var="customerId" scope="session"
			value="${sessionScope.customerId}" />
		<c:set var="customer"
			value="${CustomerDAO.getCustomerByCustomerId(customerId)}" />
		<h6>
			Customer ID :
			<c:out value="${customer.getCustomerId()}" />
		</h6>
		<h6>
			Customer Name :
			<c:out value="${customer.getCustomerName()}" />
		</h6>
		<h6>
			Customer Phone Number :
			<c:out value="${customer.getCustomerPhoneNumber()}" />
		</h6>
		<h6>
			Customer Address :
			<c:out value="${customer.getCustomerAddress()}" />
		</h6>
		<table class="center pure-table">
			<thead>
				<tr>
					<th>S.No</th>
					<th>Item ID</th>
					<th>Item Name</th>
					<th>Unit</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Total</th>
				</tr>
			</thead>
			<tbody>

				<c:set var="items" scope="session" value="${sessionScope.items}" />
				<c:set var="total" value="${0}" />
				<c:set var="count" value="${1}" />
				<c:forEach items="${items}" var="item">
					<c:set var="localTotal" value="${0}" />
					<c:set var="itemModel" value="${ItemDAO.getItem(item.key)}" />
					<c:set var="localTotal" value="${itemModel.getPrice()*item.value}" />
					<c:set var="total" value="${total+localTotal}" />

					<tr>
						<td><c:out value="${count}" /></td>
						<td><c:out value="${item.key}" /></td>
						<td><c:out value="${itemModel.getItemname()}" /></td>
						<td><c:out value="${itemModel.getUnit()}" /></td>
						<td>₹<c:out value="${itemModel.getPrice()}" />
						</td>
						<td><c:out value="${item.value}" /></td>
						<td>₹<c:out value="${localTotal}" />
						</td>
					</tr>
					<c:set var="count" value="${count+1}" />
				</c:forEach>

				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>

					<td>Total</td>

					<td>
					₹<c:out value="${total}"/>
					</td>
				</tr>
			</tbody>
		</table>
		<br>
		<br>
		<c:set var="gst" value="${sessionScope.gst}" scope="session"/>
		<c:set var="discount" value="${sessionScope.discount}" scope="session"/>
		<c:set var="discount" value="${total/discount}"/>
		<h6>
			Discount : - ₹<c:out value="${discount}"/>
		</h6>
		<c:set var="total" value="${total-discount}"/>
		<c:set var="gst" value="${(total*gst)/100}"/>
		<h6>
			GST : + ₹<c:out value="${gst}"/>
		</h6>
		<c:set var="total" value="${total+gst}"/>
		<h6>
			Final Total : ₹<c:out value="${total}"/>
		</h6>
		<br>
		<br>
		<form action="storebill.do" method="post">
			<input type="hidden" name="formid" value="storebill">
			<button class="btn btn-default" type="submit">Make Payment</button>

		</form>
	</div>

</body>
</html>