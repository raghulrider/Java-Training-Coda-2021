package utility;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Property;

public class InvoiceMasterDAO {
	public final static InvoiceMaster fetchInvoiceMaster(String billno) {
		Session session = HibernateUtility.getSession();
		Criteria criteria = session.createCriteria(InvoiceMaster.class);
		criteria.add(Property.forName("billno").eq(billno));
		InvoiceMaster invoiceMaster = (InvoiceMaster) criteria.uniqueResult();

//		InvoiceMaster invoiceMaster = null;
//		Connection con = ConnectionUtility.createConnection();
//		try {
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select * from invoicemaster where billno='"+billno+"'");
//			while(rs.next()) {
//				billno = rs.getString(1);
//				String billdate = rs.getString(2);
//				String customerid = rs.getString(3);
//				int discount = rs.getInt(4);
//				int gst = rs.getInt(5);
//				invoiceMaster = new InvoiceMaster();
//				invoiceMaster.setBillno(billno);
//				invoiceMaster.setBilldate(billdate);
//				invoiceMaster.setCustomerid(customerid);
//				invoiceMaster.setDiscount(discount);
//				invoiceMaster.setGst(gst);
//			}
//			ConnectionUtility.closeConnection(null);
//		}catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			e.printStackTrace();
//		}
		return invoiceMaster;
	}

	public final static boolean insertInvoiceMaster(InvoiceMaster invoiceMaster) {
		Session session = HibernateUtility.getSession();
		if (invoiceMaster != null) {
			try {
				session.save(invoiceMaster);
				HibernateUtility.closeSession(null);
				return true;
			} catch (Exception e) {
				HibernateUtility.closeSession(e);
				return false;
			}
		}
		return false;

//		Connection con = ConnectionUtility.createConnection();
//		try {
//			Statement st = con.createStatement();
//			String cmd = String.format("insert into invoicemaster values('%s', '%s', '%s', %d, %d)",
//					invoiceMaster.getBillno(),invoiceMaster.getBilldate(),
//					invoiceMaster.getCustomerid(), invoiceMaster.getDiscount(),
//					invoiceMaster.getGst()
//					);
//			st.execute(cmd);
//			ConnectionUtility.closeConnection(null);
//			return true;
//		}catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			e.printStackTrace();
//			return false;
//		}
	}

	public final static String generateBillNo() {
		Random rand = new Random();
		int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
		String newBillNo = "b" + String.valueOf(resRandom);
		List<String> bills = getAllBillNos();
		for (String billno : bills) {
			if (billno.equals(newBillNo)) {
				newBillNo = generateBillNo();
			}
		}
		return newBillNo;
	}

	public final static List<String> getAllBillNos() {
		Session session = HibernateUtility.getSession();
		Query query = session.createQuery("select billno from invoicemaster");
		List<String> bills = query.list();

//		ArrayList<String> bills=new ArrayList<>();
//		Connection con = ConnectionUtility.createConnection();
//		try {
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select billno from invoicemaster");
//			while(rs.next()) {
//				bills.add(rs.getString("billno"));
//			}
//			ConnectionUtility.closeConnection(null);
//			
//		}catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			e.printStackTrace();
//		}
		return bills;
	}
}
