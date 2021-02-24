package com.dao;

import java.util.List;

import com.model.Employee;

public abstract class EmployeeDAO {
	public abstract List<String> getAllEmployeeIds();

	public abstract String getPassword(String employeeId);

	public abstract int getStatus(String employeeId);

	public abstract boolean update(Employee employee);

	public abstract Employee getEmployeeByEmployeeId(String employeeId);

	public abstract boolean insertEmployee(Employee employee); 
}
