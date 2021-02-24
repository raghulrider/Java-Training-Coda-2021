package com.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.InvoiceMasterDAO;
import com.model.InvoiceMaster;

@Service
@Transactional
public class InvoiceMasterServiceImpl implements InvoiceMasterService{
	
	@Autowired
	InvoiceMasterDAO invoiceMasterDAO;
	
	public InvoiceMasterDAO getInvoiceMasterDAO() {
		return invoiceMasterDAO;
	}

	public void setInvoiceMasterDAO(InvoiceMasterDAO invoiceMasterDAO) {
		this.invoiceMasterDAO = invoiceMasterDAO;
	}

	@Override
	public boolean insertInvoiceMaster(InvoiceMaster invoiceMaster) {
		boolean result = invoiceMasterDAO.insertInvoiceMaster(invoiceMaster);
		return false;
	}
	
	@Override
	public String generateBillno() {
		Random rand = new Random();
		int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
		String newBillNo = "b" + String.valueOf(resRandom);
		List<String> bills = getAllBillNos();
		for (String billno : bills) {
			if (billno.equals(newBillNo)) {
				newBillNo = generateBillno();
			}
		}
		return newBillNo;
	}
	
	@Override
	public List<String> getAllBillNos() {
		List<String> bills = invoiceMasterDAO.getAllBillNos();
		return bills;
	}
	
	@Override
	public List<InvoiceMaster> getAllInvoice() {
		List<InvoiceMaster> invoices = invoiceMasterDAO.getAllInvoices();
		return invoices;
	}
	
	@Override
	public List<InvoiceMaster> getInvoiceByCustomerId(String customerId) {
		List<InvoiceMaster> invoices = invoiceMasterDAO.getInvoiceByCustomerId(customerId);
		return invoices;
	}
	
	@Override
	public List<InvoiceMaster> getInvoiceByBillno(String billNo) {
		List<InvoiceMaster> invoices = invoiceMasterDAO.getInvoiceByBillno(billNo);
		return invoices;
	}
	
	@Override
	public List<InvoiceMaster> getInvoiceByDate(String date) {
		List<InvoiceMaster> invoices = invoiceMasterDAO.getInvoiceByDate(date);
		return invoices;
	}
}
