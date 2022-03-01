package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import java.util.List;

public class EmployeeDaoImpl extends DaoInterface<Employee> {
  // list is working as a database
  private List<Employee> nodes;
  private EmployeeQuery nodeQuery;

  public EmployeeDaoImpl() {
    nodeQuery = new EmployeeQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
