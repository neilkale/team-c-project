package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import java.util.List;

public class EmployeeDaoImpl implements DaoInterface<Employee> {
  // list is working as a database
  private List<Employee> employees;
  private EmployeeQuery employeeQuery;

  public EmployeeDaoImpl() {
    employeeQuery = new EmployeeQuery();
    employees = employeeQuery.getAllNodeData();
  }

  // retrieve list of employees from the database
  @Override
  public List<Employee> getAllNodes() {
    return employees;
  }

  @Override
  public Employee getNode(String username) {
    refreshNodeData(); // refreshes employee list to make sure it matches the EMBEDDED db
    return employeeQuery.findNodeByUsername(username);
  }

  @Override
  public void addNode(Employee employee) {
    refreshNodeData();
    try {
      employeeQuery.addNode(employee); // adds employee to EMBEDDED
      employees.add(employee); // adds employee to OBJECT LIST
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Employee added to database");
  }

  @Override
  public void deleteNode(Employee employee) {
    refreshNodeData();
    try {
      employeeQuery.removeNode(employee); // removes employee from EMBEDDED
      employees.remove(employees.indexOf(employee)); // removes employee from OBJECT LIST
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(
        "Employee: Index " + employees.indexOf(employee) + ", deleted from database");
  }

  @Override
  public void updateNode(Employee employee) {
    refreshNodeData();

    int index = -1;
    boolean found = false;
    for (int i = 0; i < employees.size() && found == false; i++) {
      if (employees.get(i).get_username().equals(employee.get_username())) {
        index = i;
        found = true;
      }
    }

    try {
      employeeQuery.editNode(employee); // updating Employee fields in EMBEDDED
      // updating Employee fields in OBJECT LIST
      Employee employee_update = employees.get(index);
      employee_update.set_username(employee.get_username());
      employee_update.set_password(employee.get_password());
      employee_update.set_firstName(employee.get_firstName());
      employee_update.set_lastName(employee.get_lastName());
      employee_update.set_serviceType(employee.get_service_type());
      employee_update.set_access(employee.get_access());
      employee_update.set_id(employee.get_id());
      employee_update.set_profilePicture(employee.get_profilePicture());
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(
        "Employee: Index " + employees.indexOf(employee) + ", updated in the database");
  }

  @Override
  public void refreshNodeData() {
    employees = employeeQuery.getAllNodeData();
  }
}
