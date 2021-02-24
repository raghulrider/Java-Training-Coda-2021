package com.service;

import com.model.Customer;

public interface CustomerEntryService {
	public boolean checkCustomerExists(String customerId);
	public Customer getCustomerByCustomerId(String customerId);
	public Customer getCustomerbyCustomerPhoneNumber(String customerPhoneNumber);
	public boolean updateCustomer(Customer customer);
	public String generateCustomerID();
	public  boolean register(Customer customer);
}
