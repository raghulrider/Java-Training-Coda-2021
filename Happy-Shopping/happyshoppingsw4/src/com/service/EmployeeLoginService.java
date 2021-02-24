package com.service;

import com.model.Employee;

public interface EmployeeLoginService {
	public boolean checkEmployeeExists(String employeeId);
	public boolean login(String employeeId, String password);
	public boolean checkIfEmployeeAlreadyLoggedIn(String employeeId);
	public boolean setStatus(int i, String employeeId);
	public Employee getEmployeeByEmployeeId(String EmployeeId);
	public boolean register(Employee employee);
}
