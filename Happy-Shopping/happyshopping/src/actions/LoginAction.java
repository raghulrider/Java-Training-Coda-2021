package actions;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import actionerrors.ActionError;
import actionerrors.ActionErrors;
import actionerrors.InvalidLoginActionError;
import utility.Employee;
import utility.EmployeeManager;

public class LoginAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String employeeId = request.getParameter("employeeId");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		ActionErrors errors = new ActionErrors();
		Set<ActionError> errorSet = new HashSet<>();
		
		if(employeeId.length()==0 || employeeId==null) {
			ActionError emptyIdError =  new InvalidLoginActionError("Please enter Employee ID");
			errorSet.add(emptyIdError);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			return "login.failure";
		}
		
		if(password.length()==0 || password==null) {
			ActionError emptyPasswordError =  new InvalidLoginActionError("Please enter password");
			errorSet.add(emptyPasswordError);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			return "login.failure";
		}
		
		Employee employee = new Employee();
		employee.setEmployeeid(employeeId);
		employee.setPassword(password);
		
		EmployeeManager employeeManager = EmployeeManager.getInstance(employee);
		if(!employeeManager.isNewInstance()) {
			employeeManager.setEmployee(employee);
		}
		
		if(!employeeManager.checkEmployeeExists()) {
			ActionError employeeNotExists =  new InvalidLoginActionError("Employee with ID "+employeeId+" does not exists. Please register or contact admin.");
			errorSet.add(employeeNotExists);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			return "login.failure";
		}
		
		if(!employeeManager.login()) {
			ActionError passwordWrong =  new InvalidLoginActionError("Password incorrect. If forgotten contact admin.");
			errorSet.add(passwordWrong);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			return "login.failure";
		}
		
		if(employeeManager.checkIfEmployeeAlreadyLoggedIn()) {
			session.setAttribute("employeeId", employeeId);
			System.out.println("Test ID : "+session.getAttribute("employeeId"));
			return "login.alreadyLoggedIn";
		}else {
			System.out.println("Employee status before login : "+employee.getStatus());
			employee.setStatus(1);
			employeeManager.setEmployee(employee);
			boolean result=employeeManager.updateEmployeeLoginStatus();
			System.out.println("Login status update : "+result);
			System.out.println("Employee status after login : "+employee.getStatus());
			session.setAttribute("employeeId", employeeId);
			System.out.println("Logged in employee ID : "+session.getAttribute("employeeId"));
			return "login.success";
		}
	}
}
