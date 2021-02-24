package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BillWithPriceDAO;
import com.dao.CustomerDAO;
import com.dao.InvoiceDAO;
import com.dao.InvoiceMasterDAO;
import com.model.Bill;
import com.model.BillWithPrice;
import com.model.Customer;
import com.model.Invoice;
import com.model.InvoiceMaster;
import com.model.Item;



@Service
@Transactional
public class BilllServiceImpl implements BillService{
	@Autowired
	InvoiceMasterDAO invoiceMasterDAO;
	
	public InvoiceMasterDAO getInvoiceMasterDAO() {
		return invoiceMasterDAO;
	}

	public void setInvoiceMasterDAO(InvoiceMasterDAO invoiceMasterDAO) {
		this.invoiceMasterDAO = invoiceMasterDAO;
	}
	
	@Autowired
	BillWithPriceDAO billWithPriceDAO;
	
	public BillWithPriceDAO getBillWithPrice() {
		return billWithPriceDAO;
	}

	public void setBillWithPrice(BillWithPriceDAO billWithPriceDAO) {
		this.billWithPriceDAO = billWithPriceDAO;
	}
	
	@Autowired
	InvoiceDAO invoiceDAO;

	public InvoiceDAO getInvoiceDAO() {
		return invoiceDAO;
	}

	public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}
	
	@Autowired
	CustomerDAO customerDAO;

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
	@Override
	public Bill getBill(String billno) {
		Bill bill =null;
		InvoiceMaster invoiceMaster = invoiceMasterDAO.fetchInvoiceMaster(billno);
		BillWithPrice bwp = billWithPriceDAO.getBillWithPrice(billno);
		List<Item> items = bwp.getItems();
		if(invoiceMaster!=null) {
			bill = new Bill();
			List<Invoice> invoices = invoiceDAO.getInvoice(billno);
			Customer customer = customerDAO.getCustomerByCustomerId(invoiceMaster.getCustomerid());
			bill.setCustomer(customer);
			bill.setInvoiceMaster(invoiceMaster);
			bill.setInvoices(invoices);
			bill.setItems(items);
		}
		return bill;
	}
}
