package actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.Employee;
import utility.EmployeeManager;

public class GotoHomeAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String employeeid = (String) session.getAttribute("employeeId");
		System.out.println("Home requested - Employee ID : " + employeeid);

		if (employeeid != null && employeeid.length() != 0) {
			Employee employee = new Employee();
			employee.setEmployeeid(employeeid);
			EmployeeManager manager = EmployeeManager.getInstance(employee);
			if (!manager.isNewInstance()) {
				manager.setEmployee(employee);
			}

			if (manager.checkEmployeeExists()) {
				if (manager.checkIfEmployeeAlreadyLoggedIn()) {
					return "gotohome.success";
				}
			}
		}
		return "gotohome.failure";
	}
}
