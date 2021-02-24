package com.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.actionerror.ActionError;
import com.actionerror.ActionErrors;
import com.actionerror.InvalidLoginActionError;
import com.actionerror.RegisterFailureAction;
import com.model.Employee;
import com.model.Item;
import com.service.EmployeeLoginService;
import com.service.ItemService;



@Controller
public class EmployeeEntryController {


	@Autowired
	EmployeeLoginService employeeLoginService;
	
	public EmployeeLoginService getEmployeeLoginService() {
		return employeeLoginService;
	}
	public void setEmployeeLoginService(EmployeeLoginService employeeLoginService) {
		this.employeeLoginService = employeeLoginService;
	}
	
	@Autowired
	ItemService itemService;
	public ItemService getItemService() {
		return itemService;
	}
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		System.out.println("Home method called");
		ModelAndView view = new ModelAndView();
		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("employee");

		if (employee != null) {
			if (employeeLoginService.checkEmployeeExists(employee.getEmployeeid())) {
				if (employeeLoginService.checkIfEmployeeAlreadyLoggedIn(employee.getEmployeeid())) {
					employeeLoginService.setStatus(1, employee.getEmployeeid());
					view.setViewName("shop");
					return view;
				}
			}
		}
		view.setViewName("login");
		return view;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		Employee employee= (Employee)session.getAttribute("employee");
		System.out.println("Logout requested : "+employee.getEmployeeid());
		employeeLoginService.setStatus(0,employee.getEmployeeid());
		session.invalidate();
		ModelAndView view = new ModelAndView();
		view.setViewName("login");
		return view;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("Login method called");
		String employeeId = request.getParameter("employeeId");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		Set<ActionError> errorSet = new HashSet<>();
		
		ModelAndView view = new ModelAndView();
		
		if(employeeId.length()==0 || employeeId==null) {
			ActionError emptyIdError =  new InvalidLoginActionError("Please enter Employee ID");
			errorSet.add(emptyIdError);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			view.setViewName("login");
			return view;
		}
		
		if(password.length()==0 || password==null) {
			ActionError emptyPasswordError =  new InvalidLoginActionError("Please enter password");
			errorSet.add(emptyPasswordError);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			view.setViewName("login");
			return view;
		}
		
		if(!employeeLoginService.checkEmployeeExists(employeeId)) {
			ActionError employeeNotExists =  new InvalidLoginActionError("Employee with ID "+employeeId+" does not exists. Please register or contact admin.");
			errorSet.add(employeeNotExists);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			view.setViewName("login");
			return view;
		}
		
		
		if(!employeeLoginService.login(employeeId, password)) {
			ActionError passwordWrong =  new InvalidLoginActionError("Password incorrect. If forgotten contact admin.");
			errorSet.add(passwordWrong);
			errors.setErrors(errorSet);
			session.setAttribute("loginErrors", errors);
			view.setViewName("login");
			return view;
		}
		
		if(employeeLoginService.checkIfEmployeeAlreadyLoggedIn(employeeId)) {
			List<Item> items = itemService.getAllItems();
			Employee employee = employeeLoginService.getEmployeeByEmployeeId(employeeId);
			session.setAttribute("employee", employee);
			session.setAttribute("items", items);
			System.out.println("Test ID : "+session.getAttribute("employeeId"));
			view.setViewName("shop");
			return view;
		}else {
			boolean result = employeeLoginService.setStatus(1, employeeId);
			List<Item> items = itemService.getAllItems();
			Employee employee = employeeLoginService.getEmployeeByEmployeeId(employeeId);
			session.setAttribute("employee", employee);
			session.setAttribute("items", items);
			view.setViewName("shop");
			return view;
		}
	}
	
	@RequestMapping(value="/gotologin", method = RequestMethod.GET)
	public ModelAndView gotoLogin() {
		System.out.println("Goto Login method called");
		ModelAndView view = new ModelAndView();
		view.setViewName("login");
		return view;
	}
	
	@RequestMapping(value="/gotoregister", method = RequestMethod.GET)
	public ModelAndView gotoRegister() {
		System.out.println("Goto Register method called");
		ModelAndView view = new ModelAndView();
		view.setViewName("register");
		return view;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("REgister method called");
		ModelAndView view = new ModelAndView();
		
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
			view.setViewName("register");
			return view;
		}
		
		if(employeeName.length()==0 || employeeName==null) {
			ActionError emptyIdError =  new RegisterFailureAction("Please enter Employee Name");
			errorSet.add(emptyIdError);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			view.setViewName("register");
			return view;
		}
		
		if(password.length()==0 || password==null) {
			ActionError emptyPasswordError =  new RegisterFailureAction("Please enter password");
			errorSet.add(emptyPasswordError);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			view.setViewName("register");
			return view;
		}
	
		
		if(employeeLoginService.checkEmployeeExists(employeeId)) {
			ActionError emptyIdError =  new RegisterFailureAction("Employee already exists");
			errorSet.add(emptyIdError);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			view.setViewName("register");
			return view;
		}
		
		Employee employee = new Employee();
		employee.setEmployeeid(employeeId);
		employee.setName(employeeName);
		employee.setPassword(password);
		
		boolean result = employeeLoginService.register(employee);
		if(result) {
			view.setViewName("login");
			return view;
		}else {
			ActionError registrationFailed=  new RegisterFailureAction("Registration failed. "
					+ "Might be beacuse of the following reasons\n"
					+ "1. Employee already registered.\n"
					+ "2. Something's wrong with the server.");
			errorSet.add(registrationFailed);
			errors.setErrors(errorSet);
			session.setAttribute("registrationErrors", errors);
			view.setViewName("register");
			return view;
		}
	}
}
