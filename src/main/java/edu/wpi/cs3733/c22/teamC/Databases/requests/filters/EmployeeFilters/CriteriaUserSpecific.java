package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.EmployeeFilters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.ArrayList;
import java.util.List;

public class CriteriaUserSpecific implements Criteria {
  private String username;

  public CriteriaUserSpecific(String username) {
    this.username = username;
  }

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {
    List<Employee> userEmployees = new ArrayList<Employee>();

    for (Employee e : employees) {
      if (e.get_username().equalsIgnoreCase(username)) {
        userEmployees.add(e);
      }
    }
    return userEmployees;
  }
}
