package com.dao;

import java.util.List;

import com.model.Customer;

public abstract class CustomerDAO {

	public abstract List<String> getAllCustomerIds();

	public abstract Customer getCustomerByCustomerId(String customerId);

	public abstract Customer getCustomerByCustomerPhoneNumber(String customerPhoneNumber);

	public abstract boolean createCustomer(Customer customer);

	public abstract boolean updateCustomer(Customer customer);

}
