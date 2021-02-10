package utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class EmployeeDAO {
	public static final String getEmployeeName(String employeeId) {
		String employeeName=null;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select employeename from employee where employeeid='"+employeeId+"'");
			if(rs.next()) {
				employeeName = rs.getString("employeename");
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return employeeName;
	}
	
	public static final String getPassword(String employeeId) {
		String password=null;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select password from employee where employeeid='"+employeeId+"'");
			if(rs.next()) {
				password = rs.getString("password");
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return password;
	}
	
	public static final int getStatus(String employeeId) {
		int status=-1;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select status from employee where employeeid='"+employeeId+"'");
			if(rs.next()) {
				status = rs.getInt("status");
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return status;
	}
	
	public static final boolean setPassword(String employeeId, String password) {
		boolean result=false;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			int val = st.executeUpdate("update employee set password='"+password+"' where employeeid='"+employeeId+"'");
			if(val==1) {
				result=true;
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public static final boolean setStatus(String employeeId, int status) {
		boolean result=false;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			int val = st.executeUpdate("update employee set status="+status+" where employeeid='"+employeeId+"'");
			if(val==1) {
				result=true;
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public static final boolean setEmployeeName(String employeeName, String employeeId) {
		boolean result=false;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			int val = st.executeUpdate("update employee set employeename='"+employeeName+"' where employeeid='"+employeeId+"'");
			if(val==1) {
				result=true;
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public static final boolean insertEmployee(String employeeId, String employeeName, String password, int status) {
		boolean result=false;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			String cmd=String.format("insert into employee values ('%s', '%s', '%s', %d)", employeeId, employeeName, password, status);
			int val = st.executeUpdate(cmd);
			if(val==1) {
				result=true;
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public static final Employee getEmployeeByEmployeeId(String employeeId) {
		Employee employee=null;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from employee where employeeid='"+employeeId+"'");
		
			
			if(rs.next()) {
				employeeId = rs.getString(1);
				String employeeName = rs.getString(2);
				String password = rs.getString(3);
				int status = rs.getInt(4);
				if(employeeId!=null && employeeName!=null){
					employee= new Employee();
					employee.setEmployeeid(employeeId);
					employee.setName(employeeName);
					employee.setPassword(password);
					employee.setStatus(status);
					}
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return employee;
	}
	
	public static final Employee getEmployeeByEmployeeName(String employeeName) {
		Employee employee=null;
		Connection con = ConnectionUtility.createConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from employee where employeename='"+employeeName+"'");
		
			
			if(rs.next()) {
				String employeeId = rs.getString(1);
				employeeName = rs.getString(2);
				String password = rs.getString(3);
				int status = rs.getInt(4);
				if(employeeId!=null && employeeName!=null){
					employee= new Employee();
					employee.setEmployeeid(employeeId);
					employee.setName(employeeName);
					employee.setPassword(password);
					employee.setStatus(status);
					}
			}
			ConnectionUtility.closeConnection(null);
		}
		catch(Exception e) {
			ConnectionUtility.closeConnection(e);
			System.out.println(e.getMessage());
		}
		return employee;
	}
}
