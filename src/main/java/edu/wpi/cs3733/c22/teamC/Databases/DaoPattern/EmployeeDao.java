package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> getAllEmployees();
    public Employee getEmployee(String username);
    public void updateEmployeeAll(Employee employee);
    public void updateEmployeeUsername(String username);
    public void updateEmployeePassword(String password);
    public void deleteEmployee(Employee employee);
    // public int getIndex(Employee employee);
}
