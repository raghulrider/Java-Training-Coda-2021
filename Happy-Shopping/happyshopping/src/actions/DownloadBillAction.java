package actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
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

		ServletContext context = request.getServletContext();

		String genPdf = request.getParameter("genPdf");
		String genExcel = request.getParameter("genExcel");

		if (genPdf != null) {
			String pdfPath = context.getRealPath("/invoices/" + billno + "_invoice.pdf");
			System.out.println(bill+" : "+pdfPath+" : "+billno);
			PDFGenerator.generateBill(bill, pdfPath);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoicecopy.pdf");
			File file =new File(pdfPath);
			try (
					FileInputStream in = new FileInputStream(file);
					OutputStream out = response.getOutputStream()) {
				byte[] buffer = new byte[1024];
				int numBytesRead=0;
				while ((numBytesRead = in.read(buffer)) !=-1) {
					out.write(buffer, 0, numBytesRead);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "downloadbill.failure";
			}
			return "downloadbill.success";
		}
		if (genExcel != null) {
			String excelPath = context.getRealPath("invoices/" + billno + "_invoice.xls");
			ExcelGenerator.generateBill(bill, excelPath);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + billno + "_invoicecopy.xls");
			File file =new File(excelPath);
			try (
					FileInputStream in = new FileInputStream(file);
					OutputStream out = response.getOutputStream()) {
				byte[] buffer = new byte[1024];
				int numBytesRead=0;
				while ((numBytesRead = in.read(buffer)) !=-1) {
					out.write(buffer, 0, numBytesRead);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "downloadbill.failure";
			}
			return "downloadbill.success";
		}
		return "downloadbill.success";
	}

}
