package utility;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;

public class ItemDAO {
	public final static List<Items>getAllItems() {
		System.out.println("Get all items called");
		Session session=HibernateUtility.getSession();
		Criteria criteria=session.createCriteria(Items.class);
		@SuppressWarnings("unchecked")
		List<Items> items = criteria.list();
//		items.forEach(item->{
//			System.out.println(item.getItemname());
//		});
//		Connection con = ConnectionUtility.createConnection();
//		System.out.println(con);
//		try {
//			Statement st = con.createStatement();
//			String cmd = "select * from items";
//			ResultSet rs = st.executeQuery(cmd);
//			while(rs.next()) {
//				Item item = new Item();
//				item.setItemid(rs.getString(1));
//				item.setItemname(rs.getString(2));
//				item.setUnit(rs.getString(3));
//				item.setPrice(rs.getInt(4));
//				item.setImageurl(rs.getString(5));
//				item.setShopid(rs.getString(6));
//				items.add(item);
//			}
//			
//			ConnectionUtility.closeConnection(null);
//		}catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			e.printStackTrace();
//		}
		HibernateUtility.closeSession(null);
		return items;
	}
	
	public final static Items getItem(String itemId) {
		Session session=HibernateUtility.getSession();
		Criteria criteria=session.createCriteria(Items.class);
		criteria.add(Property.forName("itemid").eq(itemId));
		Items item=(Items)criteria.uniqueResult();
		HibernateUtility.closeSession(null);
		return item;
		
//		Connection con = ConnectionUtility.createConnection();
//		Item item=null;
//		System.out.println(con);
//		try {
//			Statement st = con.createStatement();
//			String cmd = "select * from items where itemid='"+itemId+"'";
//			ResultSet rs = st.executeQuery(cmd);
//			while(rs.next()) {
//				item = new Item();
//				item.setItemid(rs.getString(1));
//				item.setItemname(rs.getString(2));
//				item.setUnit(rs.getString(3));
//				item.setPrice(rs.getInt(4));
//				item.setImageurl(rs.getString(5));
//				item.setShopid(rs.getString(6));
//			}
//			
//			ConnectionUtility.closeConnection(null);
//		}catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			e.printStackTrace();
//		}
//		return item;
	}
}
