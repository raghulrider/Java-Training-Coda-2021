package utility;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelGenerator {
    public static final boolean generateBill(Bill bill, OutputStream out) {
    	WritableWorkbook myFirstWbook = null;
    	InvoiceMaster invoiceMaster = bill.getInvoiceMaster();
    	Customer customer = bill.getCustomer();
    	List<Invoice> invoices = bill.getInvoices();
    	
    	String billNo = invoiceMaster.getBillno();
    	String billDate = invoiceMaster.getBilldate();
    	String customerId = invoiceMaster.getCustomerid();
    	String customerName = customer.getCustomerName();
    	String customerPhoneNumber = customer.getCustomerPhoneNumber();
    	String customerAddress = customer.getCustomerAddress();
    	
        try {
            myFirstWbook = Workbook.createWorkbook(out);
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
            
            Label label = new Label(0, 0, "Bill No");
            excelSheet.addCell(label);

            label = new Label(1, 0, billNo);
            excelSheet.addCell(label);
            
            label = new Label(0, 1, "Bill Date");
            excelSheet.addCell(label);

            label = new Label(1, 1, billDate);
            excelSheet.addCell(label);
            
            label = new Label(0, 2, "Customer ID");
            excelSheet.addCell(label);
            
            label = new Label(1, 2, customerId);
            excelSheet.addCell(label);
            
            label = new Label(0, 3, "Customer Name");
            excelSheet.addCell(label);
            
            label = new Label(1, 3, customerName);
            excelSheet.addCell(label);
            
            label = new Label(0, 4, "Customer Phone Number");
            excelSheet.addCell(label);
            
            label = new Label(1, 4, customerPhoneNumber);
            excelSheet.addCell(label);
            
            label = new Label(0, 5, "Customer Address");
            excelSheet.addCell(label);
            
            label = new Label(1, 5, customerAddress);
            excelSheet.addCell(label);

            label = new Label(0, 7, "S.NO");
            excelSheet.addCell(label);
            
            label = new Label(1, 7, "Item ID");
            excelSheet.addCell(label);
            
            label = new Label(2, 7, "Item Name");
            excelSheet.addCell(label);
            
            label = new Label(3, 7, "Qty");
            excelSheet.addCell(label);
            
            label = new Label(4, 7, "Unit");
            excelSheet.addCell(label);
            
            label = new Label(5, 7, "Price");
            excelSheet.addCell(label);
            
            label = new Label(6, 7, "Total");
            excelSheet.addCell(label);
            int count=0;
            int totalAmount=0;
            int row=8;
            for(Invoice invoice : invoices) {
            	count++;
            	String itemId = invoice.getItemid();
            	String qty = invoice.getQuantity();
 
    			List<Items> items = bill.getItems();
    			Items item=null;
    			for(Items itemm:items) {
    				if(itemm.getItemid().equals(itemId)) {
    					item=itemm;
    				}
    			}
            	int price = item.getPrice();
            	int total = price*(Integer.parseInt(qty));
            	totalAmount += total;
            	String Data[] = new String[] {String.valueOf(count), itemId, item.getItemname(), qty, item.getUnit(), String.valueOf(price), String.valueOf(total)};
            	for(int i=0;i<7;i++) {
            		label = new Label(i, row, Data[i]);
                    excelSheet.addCell(label);
            	}
            	row++;
            }
            
            label = new Label(5, row, "Total");
            excelSheet.addCell(label);
            
            label = new Label(6, row++, String.valueOf(totalAmount));
            excelSheet.addCell(label);
            
            int discount = invoiceMaster.getDiscount();
            
            discount = totalAmount/discount;
            label = new Label(5, row, "Discount");
            excelSheet.addCell(label);
            
            label = new Label(6, row++, String.valueOf(discount));
            excelSheet.addCell(label);
            totalAmount -= discount;
            
            int gst = invoiceMaster.getGst();
            gst = (totalAmount*gst)/100;
            label = new Label(5, row, "GST");
            excelSheet.addCell(label);
            
            label = new Label(6, row++, String.valueOf(gst));
            excelSheet.addCell(label);
            totalAmount += gst;
            label = new Label(5, row, "Final Total");
            excelSheet.addCell(label);
            
            label = new Label(6, row++, String.valueOf(totalAmount));
            excelSheet.addCell(label);
            
           
            
            myFirstWbook.write();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }


        }
        return true;
    }
}