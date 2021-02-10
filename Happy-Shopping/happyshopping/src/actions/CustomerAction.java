package actions;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import actionerrors.ActionError;
import actionerrors.ActionErrors;
import actionerrors.InvalidCustomerEntryAction;
import utility.Customer;
import utility.CustomerManager;

public class CustomerAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String customerPhoneNumber = request.getParameter("customerPhoneNumber");
		String customerId = request.getParameter("customerId");
		String newCustomer = request.getParameter("newCustomer");
		ActionErrors errors = new ActionErrors();
		Set<ActionError> errorSet = new HashSet<>();
		HttpSession session = request.getSession();
		if(customerPhoneNumber.length()==0 && customerId.length()==0 && newCustomer==null){
			ActionError error = new InvalidCustomerEntryAction("ID or Phone number is required");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			return "customer.failure";
		}
		if(newCustomer!=null && newCustomer.equals("yes")) {
			return "customer.register";
		}
		Customer customer = new Customer();
		customer.setCustomerPhoneNumber(customerPhoneNumber);
		CustomerManager customerManager = CustomerManager.getInstance(customer);
		if(!customerManager.isNewInstance()) {
			customerManager.setCustomer(customer);
		}
		if(customerPhoneNumber!=null && customerPhoneNumber.length()!=0) {
			if(customerManager.checkCustomerExists()) {
				customer = customerManager.findCustomerbyCustomerPhoneNumber(customerPhoneNumber);
				session.setAttribute("customerId", customer.getCustomerId());
				return "customer.success";
			}else {
				ActionError error = new InvalidCustomerEntryAction("Customer with P.NO "+customerPhoneNumber+" does not exists.");
				errorSet.add(error);
				errors.setErrors(errorSet);
				session.setAttribute("customerErrors", errors);
				return "customer.failure";
			}
		}
		customer.setCustomerId(customerId);
		customerManager.setCustomer(customer);
		if(customerId!=null && customerId.length()!=0) {
			if(customerManager.checkCustomerExists()) {
				customer = customerManager.findCustomerByCustomerId(customerId);
				session.setAttribute("customerId", customer.getCustomerId());
				return "customer.success";
			}else {
				ActionError error = new InvalidCustomerEntryAction("Customer with ID "+customerId+" does not exists.");
				errorSet.add(error);
				errors.setErrors(errorSet);
				session.setAttribute("customerErrors", errors);
				return "customer.failure";
			}
		}
		ActionError error = new InvalidCustomerEntryAction("Something went wrong");
		errorSet.add(error);
		errors.setErrors(errorSet);
		session.setAttribute("customerErrors", errors);
		return "customer.failure";
		
		
		
//		System.out.println(customerId);
//		System.out.println(customerPhoneNumber);
//		if(!customerManager.checkCustomerExists()) {
//			System.out.println("U trying to fool me dude?");
//			return "customer.createnew";
//		}
//		if(customerPhoneNumber!=null) {
//			System.out.println("Final data got using phone number");
//			customer = customerManager.findCustomerbyCustomerPhoneNumber(customerPhoneNumber);
//		}
//		if(customerId!=null) {
//			System.out.println("Final data got using customer id");
//			customer = customerManager.findCustomerByCustomerId(customerId);
//		}
//		if(customer.getCustomerId().length()<5) {
//			return "customer.createnew";
//		}
//		session.setAttribute("customerId", customer.getCustomerId());
//		return "customer.success";
	}

}
