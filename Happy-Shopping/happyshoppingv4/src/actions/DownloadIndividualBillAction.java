package actions;


import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.Bill;
import utility.DatabaseMaster;
import utility.ExcelGenerator;
import utility.PDFGenerator;

public class DownloadIndividualBillAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String billno = (String) request.getParameter("billno");
		System.out.println("billno : "+billno);
		
		Bill bill = DatabaseMaster.getBill(billno);
		if(bill==null) {
			System.out.println("Bill is null");
			return "downloadindividualbill.failure";
		}
		
		String genPdf = request.getParameter("genPdf");
		String genExcel = request.getParameter("genExcel");
		
		OutputStream out;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return "downloadindividualbill.failure";
		}
		
		if (genPdf != null) {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.pdf");
			PDFGenerator.generateBill(bill, out);
			return "downloadindividualbill.success";
		}
		if (genExcel != null) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.xls");
			ExcelGenerator.generateBill(bill, out);
			return "downloadindividualbill.success";
		}
		
		return "downloadindividualbill.failure";
	}
}
