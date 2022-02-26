package edu.wpi.cs3733.c22.teamC.Databases.requests.filters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.List;

/** interface for all filtering classes */
public interface Criteria {
  public List<Employee> meetCriteria(List<Employee> employeeList);
}
