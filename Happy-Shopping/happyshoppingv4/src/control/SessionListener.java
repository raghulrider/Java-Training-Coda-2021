package control;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import utility.Employee;
import utility.EmployeeManager;

@WebListener()
public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("Session created");
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		System.out.println("Session destroyed");
		String employeeId = (String) session.getAttribute("employeeId");
		if (employeeId != null && employeeId.length() != 0) {
			EmployeeManager manager = EmployeeManager.getInstance(null);
			Employee employee = manager.findEmployeeByEmployeeId(employeeId);
			if (employee != null) {
				employee.setStatus(0);
				manager.setEmployee(employee);
				manager.updateEmployeeLoginStatus();
			}
		}
	}
}
