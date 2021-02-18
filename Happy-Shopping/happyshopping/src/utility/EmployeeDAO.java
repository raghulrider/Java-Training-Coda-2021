package utility;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;


public class EmployeeDAO {
	public static final String getEmployeeName(String employeeId) {
		String employeeName=null;
		Employee employee = getEmployeeByEmployeeId(employeeId);
		if(employee!=null) {
			employeeName = employee.getName();
		}
		return employeeName;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select employeename from employee where employeeid='"+employeeId+"'");
//			if(rs.next()) {
//				employeeName = rs.getString("employeename");
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return employeeName;
	}
	
	public static final String getPassword(String employeeId) {
		String password=null;
		Employee employee = getEmployeeByEmployeeId(employeeId);
		if(employee!=null) {
			password = employee.getPassword();
		}
		return password;
//		String password=null;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select password from employee where employeeid='"+employeeId+"'");
//			if(rs.next()) {
//				password = rs.getString("password");
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return password;
	}
	
	public static final int getStatus(String employeeId) {
		int status=-1;
		Employee employee = getEmployeeByEmployeeId(employeeId);
		if(employee!=null) {
			status = employee.getStatus();
		}
		return status;
		}
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select status from employee where employeeid='"+employeeId+"'");
//			if(rs.next()) {
//				status = rs.getInt("status");
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return status;
//	}
	
	public static final boolean setPassword(String employeeId, String password) {
		Employee employee = getEmployeeByEmployeeId(employeeId);
		if(employee!=null) {
			employee.setPassword(password);
			try{
				Session session = HibernateUtility.getSession();
				session.saveOrUpdate(employee);
				HibernateUtility.closeSession(null);
				return true;
			}catch(Exception e) {
				HibernateUtility.closeSession(e);
				return false;
			}
		}
		return false;
//		boolean result=false;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			int val = st.executeUpdate("update employee set password='"+password+"' where employeeid='"+employeeId+"'");
//			if(val==1) {
//				result=true;
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return result;
	}
	
	public static final boolean setStatus(String employeeId, int status) {
		System.out.println("trying for hiber session to update status to : "+status);
		Employee employee = getEmployeeByEmployeeId(employeeId);
		
		//session = HibernateUtility.getSession();
		
		if(employee!=null) {
			System.out.println("Requested to set status to : "+status+" for id : "+employeeId);
			employee.setStatus(status);
			try{
				Session session = HibernateUtility.getSession();
				session.saveOrUpdate(employee);
				HibernateUtility.closeSession(null);
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				HibernateUtility.closeSession(e);
				return false;
			}
		}
		return false;
//		
//		boolean result=false;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			int val = st.executeUpdate("update employee set status="+status+" where employeeid='"+employeeId+"'");
//			if(val==1) {
//				result=true;
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return result;
	}
	
	public static final boolean setEmployeeName(String employeeName, String employeeId) {
		Employee employee = getEmployeeByEmployeeId(employeeId);
		if(employee!=null) {
			employee.setName(employeeName);
			try{
				Session session = HibernateUtility.getSession();
				session.saveOrUpdate(employee);
				HibernateUtility.closeSession(null);
				return true;
			}catch(Exception e) {
				HibernateUtility.closeSession(e);
				return false;
			}
		}
		return false;
//		
//		boolean result=false;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			int val = st.executeUpdate("update employee set employeename='"+employeeName+"' where employeeid='"+employeeId+"'");
//			if(val==1) {
//				result=true;
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return result;
	}
	
	public static final boolean insertEmployee(Employee employee) {
		if(employee!=null) {
			try{
				Session session = HibernateUtility.getSession();
				session.save(employee);
				HibernateUtility.closeSession(null);
				return true;
			}catch(Exception e) {
				HibernateUtility.closeSession(e);
				return false;
			}
		}
		return false;
//		boolean result=false;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			String cmd=String.format("insert into employee values ('%s', '%s', '%s', %d)", employeeId, employeeName, password, status);
//			int val = st.executeUpdate(cmd);
//			if(val==1) {
//				result=true;
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return result;
	}
	
	public static final Employee getEmployeeByEmployeeId(String employeeId) {

		Session session=HibernateUtility.getSession();
		Criteria criteria=session.createCriteria(Employee.class);
		criteria.add(Property.forName("employeeid").eq(employeeId));
		Employee employee=(Employee)criteria.uniqueResult();
		HibernateUtility.closeSession(null);
		return employee;
//		
//		Employee employee=null;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select * from employee where employeeid='"+employeeId+"'");
//		
//			
//			if(rs.next()) {
//				employeeId = rs.getString(1);
//				String employeeName = rs.getString(2);
//				String password = rs.getString(3);
//				int status = rs.getInt(4);
//				if(employeeId!=null && employeeName!=null){
//					employee= new Employee();
//					employee.setEmployeeid(employeeId);
//					employee.setName(employeeName);
//					employee.setPassword(password);
//					employee.setStatus(status);
//					}
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return employee;
	}
	
	public static final Employee getEmployeeByEmployeeName(String employeeName) {
		
		Session session=HibernateUtility.getSession();
		Criteria criteria=session.createCriteria(Employee.class);
		criteria.add(Property.forName("employeename").eq(employeeName));
		Employee employee=(Employee)criteria.uniqueResult();
		HibernateUtility.closeSession(null);
		return employee;

//		Employee employee=null;
//		Connection con = ConnectionUtility.createConnection();
//		try{
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select * from employee where employeename='"+employeeName+"'");
//		
//			
//			if(rs.next()) {
//				String employeeId = rs.getString(1);
//				employeeName = rs.getString(2);
//				String password = rs.getString(3);
//				int status = rs.getInt(4);
//				if(employeeId!=null && employeeName!=null){
//					employee= new Employee();
//					employee.setEmployeeid(employeeId);
//					employee.setName(employeeName);
//					employee.setPassword(password);
//					employee.setStatus(status);
//					}
//			}
//			ConnectionUtility.closeConnection(null);
//		}
//		catch(Exception e) {
//			ConnectionUtility.closeConnection(e);
//			System.out.println(e.getMessage());
//		}
//		return employee;
	}
}
