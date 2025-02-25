package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.Employee;

import java.util.List;

public interface EmployeeDao {
    public boolean addEmployee(Employee employee);
    public Employee getEmployeeByID(int employeeId);
    public List<Employee> getAllEmployees();


}
