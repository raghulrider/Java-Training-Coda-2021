package com.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.actionerror.ActionError;
import com.actionerror.ActionErrors;
import com.actionerror.InvalidCustomerEntryAction;
import com.actionerror.InvalidCustomerRegisterAction;
import com.model.Customer;
import com.service.CustomerEntryService;



@PropertySource(value= {"classpath:application.properties"})
@Controller
public class CustomerEntryController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	CustomerEntryService customerEntryService;
	
	public CustomerEntryService getCustomerEntryService() {
		return customerEntryService;
	}
	public void setCustomerEntryService(CustomerEntryService customerEntryService) {
		this.customerEntryService = customerEntryService;
	}

	@RequestMapping(value="/customerentry", method = RequestMethod.POST)
	public ModelAndView handleCustomerEntry(HttpServletRequest request, HttpServletResponse response) {
		
		String customerPhoneNumber = request.getParameter("customerPhoneNumber");
		String customerId = request.getParameter("customerId");
		
		String newCustomer = request.getParameter("newCustomer");
		int gst = Integer.parseInt(environment.getRequiredProperty("gst"));
		int discount = Integer.parseInt(environment.getRequiredProperty("discount"));
		
		ActionErrors errors = new ActionErrors();
		Set<ActionError> errorSet = new HashSet<>();
		
		HttpSession session = request.getSession();
		
		session.setAttribute("gst", gst);
		session.setAttribute("discount", discount);
		ModelAndView view = new ModelAndView();
		
		if(customerPhoneNumber.length()==0 && customerId.length()==0 && newCustomer==null){
			ActionError error = new InvalidCustomerEntryAction("ID or Phone number is required");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			view.setViewName("customerentry");
			return view;
		}
		
		if(newCustomer!=null && newCustomer.equals("yes")) {
			view.setViewName("registercustomer");
			return view;
		}
		Customer customer = new Customer();
		if(customerPhoneNumber!=null && customerPhoneNumber.length()!=0) {
			if(customerEntryService.checkCustomerExists(customerPhoneNumber)) {
				customer = customerEntryService.getCustomerbyCustomerPhoneNumber(customerPhoneNumber);
				session.setAttribute("customer", customer);
				view.setViewName("billing");
				return view;
			}else {
				ActionError error = new InvalidCustomerEntryAction("Customer with P.NO "+customerPhoneNumber+" does not exists.");
				errorSet.add(error);
				errors.setErrors(errorSet);
				session.setAttribute("customerErrors", errors);
				view.setViewName("customerentry");
				return view;
			}
		}
		
		if(customerId!=null && customerId.length()!=0) {
			if(customerEntryService.checkCustomerExists(customerId)) {
				customer = customerEntryService.getCustomerByCustomerId(customerId);
				session.setAttribute("customer", customer);
				view.setViewName("billing");
				return view;
			}else {
				ActionError error = new InvalidCustomerEntryAction("Customer with ID "+customerId+" does not exists.");
				errorSet.add(error);
				errors.setErrors(errorSet);
				session.setAttribute("customerErrors", errors);
				view.setViewName("customerentry");
				return view;
			}
		}
		
		ActionError error = new InvalidCustomerEntryAction("Something went wrong");
		errorSet.add(error);
		errors.setErrors(errorSet);
		session.setAttribute("customerErrors", errors);
		view.setViewName("customerentry");
		return view;
	}
	
	@RequestMapping(value="/registercustomer", method = RequestMethod.POST)
	public ModelAndView registerCustomer(HttpServletRequest request, HttpServletResponse response) {
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
		
		ModelAndView view = new ModelAndView();
		
		if(customerName.length()==0) {
			ActionError error = new InvalidCustomerRegisterAction("Please enter name");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			view.setViewName("registercustomer");
			return view;
		}
		
		if(customerPhoneNumber.length()==0) {
			ActionError error = new InvalidCustomerRegisterAction("Phone number is mandatory");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			view.setViewName("registercustomer");
			return view;
		}
		
		if(customerAddress.length()==0) {
			ActionError error = new InvalidCustomerRegisterAction("Please enter address");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			view.setViewName("registercustomer");
			return view;
		}
		
		if(customerEntryService.checkCustomerExists(customerPhoneNumber)) {
			ActionError error = new InvalidCustomerRegisterAction("Customer already exists");
			errorSet.add(error);
			errors.setErrors(errorSet);
			session.setAttribute("customerErrors", errors);
			view.setViewName("registercustomer");
			return view;
		}else {
			boolean result = customerEntryService.register(customer);
			session.setAttribute("customer", customer);
			view.setViewName("billing");
			return view;
		}
	}
}
