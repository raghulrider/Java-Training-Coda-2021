package com.dao;

import java.util.List;

import com.model.Invoice;

public abstract class InvoiceDAO {

	public abstract boolean setInvoice(Invoice invoice);

	public abstract List<Invoice> getInvoice(String billno);
}
