package utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDAO {
	public final static ArrayList<Item>getAllItems() {
		ArrayList<Item> items = new ArrayList<>();
		Connection con = ConnectionUtility.createConnection();
		System.out.println(con);
		try {
			Statement st = con.createStatement();
			String cmd = "select * from items";
			ResultSet rs = st.executeQuery(cmd);
			while(rs.next()) {
				Item item = new Item();
				item.setItemid(rs.getString(1));
				item.setItemname(rs.getString(2));
				item.setUnit(rs.getString(3));
				item.setPrice(rs.getInt(4));
				item.setImageurl(rs.getString(5));
				item.setShopid(rs.getString(6));
				items.add(item);
			}
			
			ConnectionUtility.closeConnection(null);
		}catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
		}
		return items;
	}
	
	public final static Item getItem(String itemId) {
		Connection con = ConnectionUtility.createConnection();
		Item item=null;
		System.out.println(con);
		try {
			Statement st = con.createStatement();
			String cmd = "select * from items where itemid='"+itemId+"'";
			ResultSet rs = st.executeQuery(cmd);
			while(rs.next()) {
				item = new Item();
				item.setItemid(rs.getString(1));
				item.setItemname(rs.getString(2));
				item.setUnit(rs.getString(3));
				item.setPrice(rs.getInt(4));
				item.setImageurl(rs.getString(5));
				item.setShopid(rs.getString(6));
			}
			
			ConnectionUtility.closeConnection(null);
		}catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
		}
		return item;
	}
}
