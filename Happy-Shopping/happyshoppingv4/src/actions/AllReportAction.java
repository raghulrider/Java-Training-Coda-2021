package actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.InvoiceMaster;
import utility.InvoiceMasterDAO;

public class AllReportAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
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
			minvoices = InvoiceMasterDAO.getAllInvoice();
		}
		if(searchKey!=null && searchKey.length()!=0) {
			if(searchKey.toLowerCase().startsWith("c")) {
				System.out.println("Requested search by customer id");
				minvoices = InvoiceMasterDAO.getInvoiceByCustomerId(searchKey);
			}else if(searchKey.toLowerCase().startsWith("b")) {
				System.out.println("Requested search by billno");
				minvoices = InvoiceMasterDAO.getInvoiceByBillno(searchKey);
			}else {
				minvoices = InvoiceMasterDAO.getInvoiceByDate(searchKey);
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
		return "allreport.success";
	}
}
