package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao{
    //list is working as a database
    private List<Employee> employees;
    private EmployeeQuery employeeQuery;

    public EmployeeDaoImpl(){
        employeeQuery = new EmployeeQuery();
        employees = employeeQuery.getAllNodeData();
    }

//    @Override
//    public int getIndex(Employee employee){
//        return employees.indexOf(employee);
//    }

    @Override
    public void deleteEmployee(Employee employee) {
        employees.remove(employees.indexOf(employee));
        System.out.println("Employee: Index " + employees.indexOf(employee) + ", deleted from database");
    }

    //retrieve list of employees from the database
    @Override
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @Override
    public Employee getEmployee(String username) {
        return employeeQuery.findNodeByUsername(username);
    }

    @Override
    public void updateEmployeeAll(Employee employee) {

        int index = -1;
        boolean found = false;

        for(int i = 0; i < employees.size() && found == false; i++){
            if(employees.get(i).get_username().equals(employee.get_username())){
                index = i;
                found = true;
            }
        }

        // updating Employee fields
        employees.get(index).set_username(employee.get_username());
        employees.get(index).set_password(employee.get_password());
        employees.get(index).set_firstName(employee.get_firstName());
        employees.get(index).set_lastName(employee.get_lastName());
        employees.get(index).set_serviceType(employee.get_service_type());
        employees.get(index).set_access(employee.get_access());
        employees.get(index).set_id(employee.get_id());
        employees.get(index).set_profilePicture(employee.get_profilePicture());
        System.out.println("Employee: Index " + employees.indexOf(employee) + ", updated in the database");
    }

}
