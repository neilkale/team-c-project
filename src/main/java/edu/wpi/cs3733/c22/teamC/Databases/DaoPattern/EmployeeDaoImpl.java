package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;

public class EmployeeDaoImpl extends DaoInterface<Employee> {
  // list is working as a database

  public EmployeeDaoImpl() {
    nodeQuery = new EmployeeQuery();
    nodes = nodeQuery.getAllNodeData();

  }
}
