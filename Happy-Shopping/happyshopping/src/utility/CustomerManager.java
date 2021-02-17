package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerManager {
	private Customer customer=null;
	private static boolean isNewInstance=true;
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private static CustomerManager customerManager=null;
	
	private CustomerManager(Customer customer) {
		this.customer=customer;
	}

	public final boolean checkCustomerExists() {
		System.out.println("Trying to fetch data using phone number"+this.customer.getCustomerPhoneNumber());
		if(this.customer.getCustomerPhoneNumber()!=null && this.customer.getCustomerPhoneNumber().length()!=0) {
			Customer c = CustomerDAO.getCustomerByCustomerPhoneNumber(this.customer.getCustomerPhoneNumber());
			if(c!=null) {
				return true;
			}
		}
		
		if(this.customer.getCustomerId()!=null && this.customer.getCustomerId().length()!=0) {
		System.out.println("Trying to fetch data using customer id"+this.customer.getCustomerId());
		Customer c = CustomerDAO.getCustomerByCustomerId(this.customer.getCustomerId());
		if(c!=null) {
			return true;
			}
		}
		return false;
	}
	

	public final boolean isNewInstance() {
		return isNewInstance;
	}
	
	public final Customer findCustomerByCustomerId(String customerId) {
		Customer customer = CustomerDAO.getCustomerByCustomerId(customerId);
		return customer;
	}
	
	
	public final Customer findCustomerbyCustomerPhoneNumber(String customerPhoneNumber) {
		Customer customer = CustomerDAO.getCustomerByCustomerPhoneNumber(customerPhoneNumber);
		return customer;
	}
	
	public final boolean updateCustomer() {
		boolean result = CustomerDAO.updateCustomer(customer);
		return result;
	}
	
	public final String generateCustomerID() {
		Random rand = new Random();
	    int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
	    String newCustomerID = "C"+String.valueOf(resRandom);
	    List<String> customerIds = CustomerDAO.getAllCustomerIds();
	    for(String customerId:customerIds) {
	    	if(customerId.equals(newCustomerID)) {
	    		newCustomerID=generateCustomerID();
	    	}
	    }
	    return newCustomerID;
}
	public final Customer register() {
		if(this.checkCustomerExists()) {
			System.out.println("Customer already exists");
			return getCustomer();
		}
		this.customer.setCustomerId(generateCustomerID());
		boolean result = CustomerDAO.insertCustomer(this.customer);
		System.out.println("Insertion result : "+result);
		return getCustomer();
	}
	
	//singleton
	public final static CustomerManager getInstance(Customer customer) {
		if(customerManager==null) {
			customerManager = new CustomerManager(customer);
		}
		isNewInstance=false;
		return customerManager;
	}
}
