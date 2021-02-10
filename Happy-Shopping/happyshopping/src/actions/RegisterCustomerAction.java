package actions;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import actionerrors.ActionError;
import actionerrors.ActionErrors;
import actionerrors.InvalidCustomerRegisterAction;
import utility.Customer;
import utility.CustomerManager;

public class RegisterCustomerAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String customerName = request.getParameter("ncustomerName");
		String customerPhoneNumber = request.getParameter("ncustomerPhoneNumber");
		String customerAddress = request.getParameter("ncustomerAddress");
		
		Customer customer = new Customer();
		customer.setCustomerName(customerName);
		customer.setCustomerPhoneNumber(customerPhoneNumber);
		customer.setCustomerAddress(customerAddress);
		ActionErrors errors =new ActionErrors(); 
		Set<ActionError> errorSet = new HashSet<>();
		HttpSession session = request.getSession();
		CustomerManager customerManager = CustomerManager.getInstance(customer);
		if(!customerManager.isNewInstance()) {
			customerManager.setCustomer(customer);
		}
		if(customerPhoneNumber.length()==0) {
			ActionError error = new InvalidCustomerRegisterAction("Phone number is mandatory");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			return "registercustomer.failure";
		}
		
		if(customerManager.checkCustomerExists()) {
			ActionError error = new InvalidCustomerRegisterAction("Customer already exists");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			return "registercustomer.alreadyExists";
		}else {
			customer = customerManager.register();
			session.setAttribute("customerId", customer.getCustomerId());
			return "registercustomer.success";
		}
	}

}
