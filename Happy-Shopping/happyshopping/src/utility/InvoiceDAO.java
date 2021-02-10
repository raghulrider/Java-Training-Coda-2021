package utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class InvoiceDAO {
	public final static ArrayList<Invoice> getInvoice(String billno){
		ArrayList<Invoice> invoices=null;
		Connection con  =ConnectionUtility.createConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from invoice where billno='"+billno+"'");
			invoices = new ArrayList<>();
			while(rs.next()) {
				billno = rs.getString(1);
				String itemid=rs.getString(2);
				String quantity=rs.getString(3);
				Invoice invoice = new Invoice();
				invoice.setBillno(billno);
				invoice.setItemid(itemid);
				invoice.setQuantity(quantity);
				invoices.add(invoice);
			}
			ConnectionUtility.closeConnection(null);
		}catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
		}
		return invoices;
	}
	
	public final static boolean setInvoice (Invoice invoice) {
		Connection con  =ConnectionUtility.createConnection();
		try {
			Statement st = con.createStatement();
			String cmd= String.format("insert into invoice values('%s', '%s', '%s')", invoice.getBillno()
					, invoice.getItemid(), invoice.getQuantity());
			st.execute(cmd);
			ConnectionUtility.closeConnection(null);
			return true;
		}catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
			return false;
		}
	}
}
