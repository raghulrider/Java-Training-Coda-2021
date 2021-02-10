package actions;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import actionerrors.ActionError;
import actionerrors.ActionErrors;
import actionerrors.RegisterFailureAction;
import utility.Employee;
import utility.EmployeeManager;

public class RegisterAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String employeeId = request.getParameter("employeeId");
		String employeeName = request.getParameter("employeeName");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		Set<ActionError> errorSet = new HashSet<>();
		if(employeeId.length()==0 || employeeId==null) {
			ActionError emptyIdError =  new RegisterFailureAction("Please enter Employee ID");
			errorSet.add(emptyIdError);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			return "register.failure";
		}
		if(employeeName.length()==0 || employeeName==null) {
			ActionError emptyIdError =  new RegisterFailureAction("Please enter Employee Name");
			errorSet.add(emptyIdError);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			return "register.failure";
		}
		
		if(password.length()==0 || password==null) {
			ActionError emptyPasswordError =  new RegisterFailureAction("Please enter password");
			errorSet.add(emptyPasswordError);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			return "register.failure";
		}
		
		Employee employee = new Employee();
		employee.setEmployeeid(employeeId);
		employee.setName(employeeName);
		employee.setPassword(password);
		
		EmployeeManager employeeManager = EmployeeManager.getInstance(employee);
		if(!employeeManager.isNewInstance()) {
			System.out.println("Not a new instance");
			employeeManager.setEmployee(employee);
		}
		
		boolean result = employeeManager.register();
		if(result) {
			session.setAttribute("employeeId", employeeId);
			return "register.success";
		}else {
			ActionError registrationFailed=  new RegisterFailureAction("Registration failed. "
					+ "Might be beacuse of the following reasons\n"
					+ "1. Employee already registered.\n"
					+ "2. Something's wrong with the server.");
			errorSet.add(registrationFailed);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			return "register.failure";
		}
	}
}
