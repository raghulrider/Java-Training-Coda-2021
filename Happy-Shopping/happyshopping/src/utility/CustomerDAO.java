package utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerDAO {

	public final static Customer getCustomerByCustomerId(String customerId) {
		Customer customer=null;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from customer where customerid='"+customerId+"'");
			System.out.println("Got value using customer id");
			if(rs.next()) {
				customerId = rs.getString(1);
				String customerName = rs.getString(2);
				String customerPhoneNumber = rs.getString(3);
				String customerAddress = rs.getString(4);
				customer=new Customer();
				customer.setCustomerId(customerId);
				customer.setCustomerName(customerName);
				customer.setCustomerPhoneNumber(customerPhoneNumber);
				customer.setCustomerAddress(customerAddress);
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
		}
		return customer;
	}
	
	public final static Customer getCustomerByCustomerPhoneNumber(String customerPhoneNumber) {
		Customer customer=null;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from customer where customerphonenumber='"+customerPhoneNumber+"'");
			System.out.println("Got value using customer id");
			if(rs.next()) {
				String customerId = rs.getString(1);
				String customerName = rs.getString(2);
				customerPhoneNumber = rs.getString(3);
				String customerAddress = rs.getString(4);
				customer=new Customer();
				customer.setCustomerId(customerId);
				customer.setCustomerName(customerName);
				customer.setCustomerPhoneNumber(customerPhoneNumber);
				customer.setCustomerAddress(customerAddress);
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
		}
		return customer;
	}

	public final static boolean insertCustomer(String customerId, String customerName, String customerPhoneNumber,
			String customerAddress) {
		boolean result=false;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			String cmd=String.format("insert into customer values ('%s', '%s', '%s', '%s')", customerId, customerName, customerPhoneNumber, customerAddress);
			int val = st.executeUpdate(cmd);
			if(val==1) {
				result=true;
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
		}
		return result;
	}
	
	public final static boolean updateCustomer(Customer customer) {
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			st.execute("update customer set customername='"+customer.getCustomerName()+"'");
			st.execute("update customer set cutomerphonenumber='"+customer.getCustomerPhoneNumber()+"'");
			st.execute("update customer set customeraddress='"+customer.getCustomerAddress()+"'");
			ConnectionUtility.closeConnection(null);
			return true;
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
			return false;
		}
	}
	
	public final static ArrayList<String> getAllCustomerIds() {
		ArrayList<String> customerIds = new ArrayList<>();
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			String cmd=String.format("select customerid from customer");
			ResultSet rs = st.executeQuery(cmd);
			while(rs.next()) {
				customerIds.add(rs.getString("customerid"));
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			e.printStackTrace();
		}
		return customerIds;
	}

}
