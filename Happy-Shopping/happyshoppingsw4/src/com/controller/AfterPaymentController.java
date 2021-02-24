package com.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.model.Bill;
import com.model.BillWithPrice;
import com.model.Customer;
import com.model.Invoice;
import com.model.InvoiceMaster;
import com.model.Item;
import com.service.BillService;
import com.service.BillWithPriceService;
import com.service.ExcelGenerator;
import com.service.InvoiceMasterService;
import com.service.InvoiceService;
import com.service.ItemService;
import com.service.PDFGenerator;



@Controller
public class AfterPaymentController {

	@Autowired
	InvoiceMasterService invoiceMasterService;

	public InvoiceMasterService getInvoiceMasterService() {
		return invoiceMasterService;
	}
	public void setInvoiceMasterService(InvoiceMasterService invoiceMasterService) {
		this.invoiceMasterService = invoiceMasterService;
	}
	
	@Autowired
	BillWithPriceService billWithPriceService;
	
	public BillWithPriceService getBillWithPriceService() {
		return billWithPriceService;
	}
	public void setBillWithPriceService(BillWithPriceService billWithPriceService) {
		this.billWithPriceService = billWithPriceService;
	}
	
	@Autowired
	ItemService itemService;
	public ItemService getItemService() {
		return itemService;
	}
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@Autowired
	InvoiceService invoiceService;
	
	public InvoiceService getInvoiceService() {
		return invoiceService;
	}
	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@Autowired
	BillService billService;
	
	public BillService getBillService() {
		return billService;
	}
	public void setBillService(BillService billService) {
		this.billService = billService;
	}
	
	@RequestMapping(value="/paymentsuccess", method = RequestMethod.GET)
	public ModelAndView storeBill(HttpSession session) {
		System.out.println("Store Bill method called");
		ModelAndView view = new ModelAndView();
		
		Customer customer = (Customer)session.getAttribute("customer");
		String customerId = customer.getCustomerId();
		int gst = (int)session.getAttribute("gst");
		int discount= (int) session.getAttribute("discount");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy '['hh:mm a']'");
	    LocalDateTime now = LocalDateTime.now();
	    String billdate=dtf.format(now);
	    
	    InvoiceMaster invoiceMaster = new InvoiceMaster();
	    invoiceMaster.setBillno(invoiceMasterService.generateBillno());
	    invoiceMaster.setBilldate(billdate);
	    invoiceMaster.setCustomerid(customerId);
	    invoiceMaster.setDiscount(discount);
	    invoiceMaster.setGst(gst);
	    
	    boolean result = invoiceMasterService.insertInvoiceMaster(invoiceMaster);
		BillWithPrice billwithprice = new BillWithPrice();
		billwithprice.setBillno(invoiceMaster.getBillno());
		List<Item> itemss = new ArrayList<>();
	    @SuppressWarnings("unchecked")
		Map<Item, String> items = (HashMap<Item, String>)session.getAttribute("selectedItems");
	    for (Map.Entry<Item,String> item : items.entrySet()) {
	    	itemss.add(itemService.getItem(item.getKey().getItemid()));
	    	Invoice invoice = new Invoice();
	    	invoice.setBillno(invoiceMaster.getBillno());
	    	invoice.setItemid(item.getKey().getItemid());
	    	invoice.setQuantity(item.getValue());
	    	invoiceService.setInvoice(invoice);
	    	}
	    billwithprice.setItems(itemss);
	    boolean result1 = billWithPriceService.insertBillWithPrice(billwithprice);
	    session.setAttribute("billno", invoiceMaster.getBillno());
	    System.out.println("Billno set success  :"+invoiceMaster.getBillno());
		
		view.setViewName("paymentsuccess");
		return view;
	}
	
	@RequestMapping(value="/downloadbill", method = RequestMethod.POST)
	public void sendBill(HttpServletResponse response, HttpServletRequest request) {
		HttpSession session =  request.getSession();
		String billno = (String) session.getAttribute("billno");
		System.out.println("billno : "+billno);
		
		
		
		String genPdf = request.getParameter("genPdf");
		String genExcel = request.getParameter("genExcel");
		
		OutputStream out=null;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Bill bill = billService.getBill(billno);
		
		if (genPdf != null) {
			PDFGenerator pdfGenerator = new PDFGenerator();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.pdf");
			pdfGenerator.generateBill(bill, out);
		}
		if (genExcel != null) {
			ExcelGenerator excelGenerator = new ExcelGenerator();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.xls");
			excelGenerator.generateBill(bill, out);
		}
	}
	@RequestMapping(value="/gotoshop", method = RequestMethod.GET)
	public ModelAndView gotoShop(HttpSession session) {
		ModelAndView view = new ModelAndView();
		List<Item> items = itemService.getAllItems();
		session.setAttribute("items", items);
		view.setViewName("shop");
		return view;
	}
	
}
