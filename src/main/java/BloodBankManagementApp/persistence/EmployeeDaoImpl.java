package BloodBankManagementApp.persistence;

import BloodBankManagementApp.business.Employee;

import java.util.List;

public class EmployeeDaoImpl extends MySQLDao implements EmployeeDao{
    @Override
    public boolean addEmployee(Employee employee) {
        return false;
    }

    @Override
    public Employee getEmployeeByID(int employeeId) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }
}
