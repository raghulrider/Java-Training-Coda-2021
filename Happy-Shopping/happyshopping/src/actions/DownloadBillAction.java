package actions;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.Bill;
import utility.DatabaseMaster;
import utility.ExcelGenerator;
import utility.PDFGenerator;

public class DownloadBillAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		String billno = (String) session.getAttribute("billno");
		System.out.println("billno : "+billno);
		
		Bill bill = DatabaseMaster.getBill(billno);
		if(bill==null) {
			System.out.println("Bill is null");
			return "downloadbill.failure";
		}
		
		String genPdf = request.getParameter("genPdf");
		String genExcel = request.getParameter("genExcel");
		
		OutputStream out;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return "downloadbill.failure";
		}
		
		if (genPdf != null) {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.pdf");
			PDFGenerator.generateBill(bill, out);
			return "downloadbill.success";
		}
		if (genExcel != null) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoice.xls");
			ExcelGenerator.generateBill(bill, out);
			return "downloadbill.success";
		}
		return "downloadbill.failure";
	}

}
