package utility;



public class EmployeeManager implements UserManager{
	private Employee employee=null;
	private boolean isNewInstance=true;
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	private static EmployeeManager employeeManager=null;
	
	private EmployeeManager(Employee employee) {
		this.employee=employee;
	}

	public final boolean checkEmployeeExists() {
		Employee e = EmployeeDAO.getEmployeeByEmployeeId(this.employee.getEmployeeid());
		if(e!=null) {
			return true;
		}
		return false;
	}
	
	public final boolean checkIfEmployeeAlreadyLoggedIn() {
		int status = EmployeeDAO.getStatus(this.employee.getEmployeeid());
		if(status==-1) {
			System.out.println("Something went wrong while fetching the status");
		}
		if(status==1) {
			return true;
		}
		return false;
	}

	public final boolean isNewInstance() {
		return this.isNewInstance;
	}
	
	public final Employee findEmployeeByEmployeeId(String employeeId) {
		Employee employee = EmployeeDAO.getEmployeeByEmployeeId(employeeId);
		return employee;
	}
	
	
	public final Employee findEmployeebyEmployeeName(String employeeName) {
		Employee employee = EmployeeDAO.getEmployeeByEmployeeName(employeeName);
		return employee;
	}
	
	
	@Override
	public boolean login() {
		String password = EmployeeDAO.getPassword(this.employee.getEmployeeid());
		if(this.employee.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean register() {
		if(this.checkEmployeeExists()) {
			System.out.println("Employee already exists");
			return false;
		}
		boolean result = EmployeeDAO.insertEmployee(this.employee.getEmployeeid(),
				this.employee.getName(), 
				this.employee.getPassword(), 
				this.employee.getStatus());
		System.out.println("Insertion result : "+result);
		return result;
	}
	
	//singleton
	public final static EmployeeManager getInstance(Employee employee) {
		if(employeeManager==null) {
			employeeManager = new EmployeeManager(employee);
			return employeeManager;
		}else {
			employeeManager.isNewInstance=false;
			return employeeManager;
		}
	}
	
	public final boolean updateEmployeeLoginStatus() {
		boolean result = EmployeeDAO.setStatus(this.employee.getEmployeeid(), this.employee.getStatus());
		return result;
	}
}
