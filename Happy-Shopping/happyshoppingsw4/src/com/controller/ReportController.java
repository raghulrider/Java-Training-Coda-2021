package com.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.model.Bill;
import com.model.InvoiceMaster;
import com.service.BillService;
import com.service.ExcelGenerator;
import com.service.InvoiceMasterService;
import com.service.PDFGenerator;





@Controller
public class ReportController {
	@Autowired
	BillService billService;
	public BillService getBillService() {
		return billService;
	}
	public void setBillService(BillService billService) {
		this.billService = billService;
	}
	
	@Autowired
	InvoiceMasterService invoiceMasterService;
	
	
	public InvoiceMasterService getInvoiceMasterService() {
		return invoiceMasterService;
	}
	public void setInvoiceMasterService(InvoiceMasterService invoiceMasterService) {
		this.invoiceMasterService = invoiceMasterService;
	}
	@RequestMapping(value="/allreport", method = RequestMethod.POST)
	public ModelAndView allReport(HttpServletRequest request, HttpServletResponse response) {
		String searchKey = request.getParameter("searchKey");
		String sbd = request.getParameter("sbd");
		boolean sortbydatedes = false;
		if(sbd!=null && sbd.equals("true")) {
			sortbydatedes = true;
		}
		System.out.println("Search key : "+searchKey);
		List<InvoiceMaster> minvoices = new ArrayList<>();
		HttpSession session = request.getSession();
		if(searchKey==null || searchKey.length()==0) {
			System.out.println("Generic search");
			minvoices = invoiceMasterService.getAllInvoice();
		}
		if(searchKey!=null && searchKey.length()!=0) {
			if(searchKey.toLowerCase().startsWith("c")) {
				System.out.println("Requested search by customer id");
				minvoices = invoiceMasterService.getInvoiceByCustomerId(searchKey);
			}else if(searchKey.toLowerCase().startsWith("b")) {
				System.out.println("Requested search by billno");
				minvoices = invoiceMasterService.getInvoiceByBillno(searchKey);
			}else {
				minvoices = invoiceMasterService.getInvoiceByDate(searchKey);
			}
		}
		minvoices.forEach(minvoice->{
			System.out.println("Bill no : "+minvoice.getBillno());
		});
		if(sortbydatedes) {
			//yet to implement
		}
		System.out.println("Result length : "+minvoices.size());
		session.setAttribute("minvoices", minvoices);
		
		ModelAndView view = new ModelAndView();
		view.setViewName("allreport");
		return view;
	}
	
	@RequestMapping(value="/downloadindividualbill", method = RequestMethod.POST)
	public void downloadIndividualBill(HttpServletRequest request, HttpServletResponse response) {
		
		String billno = (String) request.getParameter("billno");
		System.out.println("billno : "+billno);
		
		Bill bill = billService.getBill(billno);
		if(bill==null) {
			System.out.println("Bill is null");
		}
		
		String genPdf = request.getParameter("genPdf");
		String genExcel = request.getParameter("genExcel");
		
		OutputStream out=null;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (genPdf != null) {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.pdf");
			PDFGenerator pdfGenerator = new PDFGenerator();
			pdfGenerator.generateBill(bill, out);
		}
		if (genExcel != null) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.xls");
			ExcelGenerator excelGenerator = new ExcelGenerator();
			excelGenerator.generateBill(bill, out);
		}
	}
}
