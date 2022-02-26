package edu.wpi.cs3733.c22.teamC.Databases.requests.filters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.ArrayList;
import java.util.List;

/** this class represents the filtering of employees based on access = admin */
public class CriteriaAdmin implements Criteria {

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {
    List<Employee> adminEmployees = new ArrayList<Employee>();

    for (Employee e : employees) {
      if (e.get_access().equalsIgnoreCase("admin")) {
        adminEmployees.add(e);
      }
    }
    return adminEmployees;
  }
}
