package utility;

import java.util.ArrayList;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class InvoiceDAO {
	public final static List<Invoice> getInvoice(String billno){
		List<Invoice> invoices = new ArrayList<>();
		Session session=HibernateUtility.getSession();
		Query query=session.createQuery("select itemid, quantity from invoice where billno=:b");
		query.setParameter("b", billno);
		List<Object[]> list=query.list();
		for(Object[] i:list) {
			Invoice invoice = new Invoice();
			invoice.setBillno(billno);
			invoice.setItemid((String)i[0]);
			invoice.setQuantity((String)i[1]);
			invoices.add(invoice);
		}
		
//		ArrayList<Invoice> invoices=null;
//		Connection con  =ConnectionUtility.createConnection();
//		try {
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select * from invoice where billno='"+billno+"'");
//			invoices = new ArrayList<>();
//			while(rs.next()) {
//				billno = rs.getString(1);
//				String itemid=rs.getString(2);
//				String quantity=rs.getString(3);
//				Invoice invoice = new Invoice();
//				invoice.setBillno(billno);
//				invoice.setItemid(itemid);
//				invoice.setQuantity(quantity);
//				invoices.add(invoice);
//			}
//			ConnectionUtility.closeConnection(null);
//		}catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			e.printStackTrace();
//		}
		return invoices;
	}
	
	public final static boolean setInvoice (Invoice invoice) {
		Session session = HibernateUtility.getSession();
		if(invoice!=null) {
			try{
				session.save(invoice);
				HibernateUtility.closeSession(null);
				return true;
			}catch(Exception e) {
				HibernateUtility.closeSession(e);
				return false;
			}
		}
		return false;
		
//		Connection con  =ConnectionUtility.createConnection();
//		try {
//			Statement st = con.createStatement();
//			String cmd= String.format("insert into invoice values('%s', '%s', '%s')", invoice.getBillno()
//					, invoice.getItemid(), invoice.getQuantity());
//			st.execute(cmd);
//			ConnectionUtility.closeConnection(null);
//			return true;
//		}catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			e.printStackTrace();
//			return false;
//		}
	}
}
