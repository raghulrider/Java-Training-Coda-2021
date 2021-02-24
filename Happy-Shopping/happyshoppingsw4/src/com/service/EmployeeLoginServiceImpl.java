package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployeeDAO;
import com.model.Employee;

@Service
@Transactional
public class EmployeeLoginServiceImpl implements EmployeeLoginService{
	@Autowired
	private EmployeeDAO employeeDAO;
	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	@Override
	public boolean checkEmployeeExists(String employeeId) {
		List<String> employeeIds = employeeDAO.getAllEmployeeIds();
		if(employeeIds.contains(employeeId)) {
			return true;
		}
		return false;
	}
	@Override
	public boolean login(String employeeId, String password) {
		String dpassword = employeeDAO.getPassword(employeeId);
		if(dpassword.equals(password)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean checkIfEmployeeAlreadyLoggedIn(String employeeId) {
		int status = employeeDAO.getStatus(employeeId);
		if(status==1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setStatus(int status, String employeeId) {
		Employee employee = getEmployeeByEmployeeId(employeeId);
		employee.setStatus(status);
		boolean result = employeeDAO.update(employee);
		return result;
	}
	
	@Override
	public Employee getEmployeeByEmployeeId(String employeeId) {
		Employee employee = employeeDAO.getEmployeeByEmployeeId(employeeId);
		return employee;
	}
	
	@Override
	public boolean register(Employee employee) {
		boolean result = employeeDAO.insertEmployee(employee);
		return result;
	}
	
}
