package actions;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.Employee;
import utility.EmployeeManager;


public class LogoutAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String employeeId= (String) session.getAttribute("employeeId");
		System.out.println("Logout requested : "+employeeId);
		EmployeeManager manager = EmployeeManager.getInstance(null);
		Employee employee = manager.findEmployeeByEmployeeId(employeeId);
		System.out.println("Status before : "+employee.getStatus());
		employee.setStatus(0);
		manager.setEmployee(employee);
		boolean result = manager.updateEmployeeLoginStatus();
		System.out.println("Status after :"+manager.findEmployeeByEmployeeId(employeeId).getStatus());
		session.invalidate();
		if(result) {
			return "logout.success";
		}else {
			return "logout.failure";
		}
	}
}