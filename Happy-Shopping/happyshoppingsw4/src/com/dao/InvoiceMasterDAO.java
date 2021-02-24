package com.dao;

import java.util.List;

import com.model.InvoiceMaster;

public abstract class InvoiceMasterDAO {

	public abstract boolean insertInvoiceMaster(InvoiceMaster invoiceMaster);

	public abstract List<String> getAllBillNos();

	public abstract InvoiceMaster fetchInvoiceMaster(String billno);

	public abstract List<InvoiceMaster> getAllInvoices();

	public abstract List<InvoiceMaster> getInvoiceByCustomerId(String customerId);

	public abstract List<InvoiceMaster> getInvoiceByBillno(String billno);

	public abstract List<InvoiceMaster> getInvoiceByDate(String billdate) ;

}
