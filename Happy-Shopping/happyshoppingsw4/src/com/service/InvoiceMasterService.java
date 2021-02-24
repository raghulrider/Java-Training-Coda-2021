package com.service;

import java.util.List;

import com.model.InvoiceMaster;

public interface InvoiceMasterService {

	public boolean insertInvoiceMaster(InvoiceMaster invoiceMaster);
	public String generateBillno();
	public List<String> getAllBillNos();
	public List<InvoiceMaster> getAllInvoice();
	public List<InvoiceMaster> getInvoiceByCustomerId(String customerId);
	public List<InvoiceMaster> getInvoiceByBillno(String billNo);
	public List<InvoiceMaster> getInvoiceByDate(String date);

}
