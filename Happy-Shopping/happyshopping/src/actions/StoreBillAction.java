package actions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.Invoice;
import utility.InvoiceDAO;
import utility.InvoiceMaster;
import utility.InvoiceMasterDAO;

public class StoreBillAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String customerId = (String)session.getAttribute("customerId");
		int gst = (int)session.getAttribute("gst");
		int discount= (int)session.getAttribute("discount");
		
		String billno = InvoiceMasterDAO.generateBillNo();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    LocalDateTime now = LocalDateTime.now();
	    String billdate=dtf.format(now);
	    
	    InvoiceMaster invoiceMaster = new InvoiceMaster();
	    invoiceMaster.setBillno(billno);
	    invoiceMaster.setBilldate(billdate);
	    invoiceMaster.setCustomerid(customerId);
	    invoiceMaster.setDiscount(discount);
	    invoiceMaster.setGst(gst);
	    
	    InvoiceMasterDAO.insertInvoiceMaster(invoiceMaster);
		
	    @SuppressWarnings("unchecked")
		Map<String, String> items = (HashMap<String, String>)session.getAttribute("items");
	    for (Map.Entry<String,String> item : items.entrySet()) { 
	    	Invoice invoice = new Invoice();
	    	invoice.setBillno(billno);
	    	invoice.setItemid(item.getKey());
	    	invoice.setQuantity(item.getValue());
	    	InvoiceDAO.setInvoice(invoice);
	    	}
	    session.setAttribute("billno", billno);
	    System.out.println("Billno set success  :"+billno);
		return "storebill.success";
	}

}
