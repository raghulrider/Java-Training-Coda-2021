package com.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Employee;

@Repository
public class EmployeeDAOImpl extends EmployeeDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<String> getAllEmployeeIds() {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("select employeeid from employee");
		@SuppressWarnings("unchecked")
		List<String> employeeIds = query.list();
		return employeeIds;
	}
	@Override
	public String getPassword(String employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("select password from employee where employeeid=:id");
		query.setParameter("id", employeeId);
		String password = (String)query.uniqueResult();
		return password;
	}
	
	@Override
	public int getStatus(String employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("select status from employee where employeeid=:id");
		query.setParameter("id", employeeId);
		int status = (int)query.uniqueResult();
		return status;
	}
	
	@Override
	public boolean update(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(employee);
		return true;
	}
	
	@Override
	public Employee getEmployeeByEmployeeId(String employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.add(Property.forName("employeeid").eq(employeeId));
		Employee employee = (Employee)criteria.uniqueResult();
		return employee;
	}
	
	@Override
	public boolean insertEmployee(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		session.save(employee);
		return true;
	}
}
