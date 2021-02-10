package utility;

import java.util.ArrayList;

public class Bill {
	private InvoiceMaster invoiceMaster;
	private ArrayList<Invoice> invoices;
	private Customer customer;
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
	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(ArrayList<Invoice> invoices) {
		this.invoices = invoices;
	}
}
