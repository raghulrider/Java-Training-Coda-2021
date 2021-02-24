package com.service;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CustomerDAO;
import com.model.Customer;

@Service
@Transactional
public class CustomerEntryServiceImpl implements CustomerEntryService{
	@Autowired
	CustomerDAO customerDAO;
	
	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}
	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
	
	
	@Override
	public boolean checkCustomerExists(String customerCred) {
		Customer customer = getCustomerByCustomerId(customerCred);
		if(customer!=null) {
			return true;
		}
		customer = getCustomerbyCustomerPhoneNumber(customerCred);
		if(customer!=null) {
			return true;
		}
		return false;
	}
	@Override
	public Customer getCustomerByCustomerId(String customerId) {
		Customer customer = customerDAO.getCustomerByCustomerId(customerId);
		return customer;
	}
	
	
	@Override
	public Customer getCustomerbyCustomerPhoneNumber(String customerPhoneNumber) {
		Customer customer = customerDAO.getCustomerByCustomerPhoneNumber(customerPhoneNumber);
		return customer;
	}
	
	@Override
	public String generateCustomerID() {
		Random rand = new Random();
	    int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
	    String newCustomerID = "C"+String.valueOf(resRandom);
	    List<String> customerIds = customerDAO.getAllCustomerIds();
	    for(String customerId:customerIds) {
	    	if(customerId.equals(newCustomerID)) {
	    		newCustomerID=generateCustomerID();
	    	}
	    }
	    return newCustomerID;
	}
	
	@Override
	public boolean register(Customer customer) {
		customer.setCustomerId(generateCustomerID());
		boolean result = customerDAO.createCustomer(customer);
		return result;
	}
	
	@Override
	public boolean updateCustomer(Customer customer) {
		boolean result = customerDAO.updateCustomer(customer);
		return result;
	}
}

