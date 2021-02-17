package utility;

import java.util.List;

public class DatabaseMaster {
	public final static Bill getBill(String billno) {
		Bill bill = new Bill();
		InvoiceMaster invoiceMaster = InvoiceMasterDAO.fetchInvoiceMaster(billno);
		if(invoiceMaster!=null) {
			List<Invoice> invoices = InvoiceDAO.getInvoice(billno);
			Customer customer = CustomerDAO.getCustomerByCustomerId(invoiceMaster.getCustomerid());
			bill.setCustomer(customer);
			bill.setInvoiceMaster(invoiceMaster);
			bill.setInvoices(invoices);
		}
		return bill;
	}
}
