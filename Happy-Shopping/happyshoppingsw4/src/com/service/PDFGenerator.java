package com.service;

import java.io.OutputStream;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.Bill;
import com.model.Invoice;
import com.model.Item;


public class PDFGenerator{
    public void generateBill (Bill bill, OutputStream out) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            addContent(document, bill);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addContent(Document document, Bill bill) throws DocumentException {
        Paragraph title = new Paragraph("Happy Shopping");
      
        title.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter = new Chapter(title, 1);
        
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        chapter.add(paragraph);
        System.out.println("Bill NO : "+bill.getInvoiceMaster().getBillno());
        chapter.add(new Paragraph("Bill No : "+bill.getInvoiceMaster().getBillno()));
        chapter.add(new Paragraph("Bill Date : "+bill.getInvoiceMaster().getBilldate()));
        chapter.add(new Paragraph("Customer ID :"+bill.getInvoiceMaster().getCustomerid()));
        chapter.add(new Paragraph("Customer Name : "+bill.getCustomer().getCustomerName()));
        chapter.add(new Paragraph("Phone Number : "+bill.getCustomer().getCustomerPhoneNumber()));
        chapter.add(new Paragraph("Address : "+bill.getCustomer().getCustomerAddress()));
        
        paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        chapter.add(paragraph);
       
        int totalAmount = createTable(chapter, bill);
        
        paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        chapter.add(paragraph);
        
        Paragraph para = new Paragraph("Total : Rs."+totalAmount);
        para.setAlignment(Element.ALIGN_RIGHT);
        chapter.add(para);
        
        int discount = bill.getInvoiceMaster().getDiscount();
        discount = (totalAmount/discount);
        para = new Paragraph("Discount : -Rs."+discount);
        para.setAlignment(Element.ALIGN_RIGHT);
        chapter.add(para);
        totalAmount-=discount;
        int gst = bill.getInvoiceMaster().getGst();
        gst = (totalAmount*gst)/100;
        para = new Paragraph("GST : +Rs."+gst);
        para.setAlignment(Element.ALIGN_RIGHT);
        chapter.add(para);
        totalAmount+=gst;
        para = new Paragraph("Final Total : Rs."+(totalAmount));
        para.setAlignment(Element.ALIGN_RIGHT);
        chapter.add(para);
        document.add(chapter);

    }

    public int createTable(Chapter chapter, Bill bill)
            throws BadElementException{
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100.00f);
        PdfPCell c1 = new PdfPCell(new Phrase("S.NO"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Item ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Item Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Qty"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Unit"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Total"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
		List<Invoice> invoices = bill.getInvoices();
		table.setHeaderRows(1);
		int totalAmount = 0;
		int count=0;
		for(Invoice invoice : invoices) {
			String itemId = invoice.getItemid();
			List<Item> items = bill.getItems();
			Item item=null;
			for(Item itemm:items) {
				if(itemm.getItemid().equals(itemId)) {
					item=itemm;
				}
			}
			count++;
			int total = item.getPrice()*Integer.parseInt(invoice.getQuantity());
			totalAmount  += total;
			table.addCell(""+count);
			table.addCell(item.getItemid());
			table.addCell(item.getItemname());
			table.addCell(invoice.getQuantity());
			table.addCell(item.getUnit());
			table.addCell("₹"+item.getPrice());
			table.addCell("₹"+total);
		}

        chapter.add(table);
        return totalAmount;
    }

    public void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}