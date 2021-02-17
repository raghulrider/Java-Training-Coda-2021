package utility;


import java.util.List;

public class Bill {
	private InvoiceMaster invoiceMaster;
	private List<Invoice> invoices;
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
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
}
