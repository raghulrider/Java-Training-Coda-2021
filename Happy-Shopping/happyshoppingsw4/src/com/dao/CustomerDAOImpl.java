package com.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Customer;

@Repository
public class CustomerDAOImpl extends CustomerDAO{
	
	@Autowired
	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<String> getAllCustomerIds() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select customerid from customer");
		@SuppressWarnings("unchecked")
		List<String> customerids = query.list();
		return customerids;
	}
	
	@Override
	public Customer getCustomerByCustomerId(String customerId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.add(Property.forName("customerid").eq(customerId));
		Customer customer = (Customer) criteria.uniqueResult();
		return customer;
	}
	
	@Override
	public Customer getCustomerByCustomerPhoneNumber(String customerPhoneNumber) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.add(Property.forName("customerphonenumber").eq(customerPhoneNumber));
		Customer customer = (Customer) criteria.uniqueResult();
		return customer;
	}
	
	@Override
	public boolean createCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.save(customer);
		return true;
	}
	@Override
	public boolean updateCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);
		return true;
	}
}
