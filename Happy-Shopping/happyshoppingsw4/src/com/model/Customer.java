package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="customer")
@Table(name="customer")
public class Customer {
	@Id()
	private String customerid;
	private String customername, customerphonenumber, customeraddress;

	public String getCustomerId() {
		return customerid;
	}

	public void setCustomerId(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomerName() {
		return customername;
	}

	public void setCustomerName(String customername) {
		this.customername = customername;
	}

	public String getCustomerPhoneNumber() {
		return customerphonenumber;
	}

	public void setCustomerPhoneNumber(String customerphoneNumber) {
		this.customerphonenumber = customerphoneNumber;
	}

	public String getCustomerAddress() {
		return customeraddress;
	}

	public void setCustomerAddress(String customeraddress) {
		this.customeraddress = customeraddress;
	}
}
