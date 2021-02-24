package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.InvoiceDAO;
import com.model.Invoice;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{
	
	@Autowired
	InvoiceDAO invoiceDAO;

	public InvoiceDAO getInvoiceDAO() {
		return invoiceDAO;
	}

	public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	@Override
	public boolean setInvoice(Invoice invoice) {
		boolean result = invoiceDAO.setInvoice(invoice);
		return result;
	}
}
