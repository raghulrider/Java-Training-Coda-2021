package com.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;


public class Bill {

	private InvoiceMaster invoiceMaster;
	private List<Invoice> invoices;
	private Customer customer;
	private List<Item> items;
	
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public InvoiceMaster getInvoiceMaster() {
		return invoiceMaster;
	}
	public void setInvoiceMaster(InvoiceMaster invoiceMaster) {
		this.invoiceMaster = invoiceMaster;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
}
