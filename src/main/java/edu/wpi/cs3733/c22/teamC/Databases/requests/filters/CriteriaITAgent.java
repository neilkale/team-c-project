package edu.wpi.cs3733.c22.teamC.Databases.requests.filters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.ArrayList;
import java.util.List;

/** this class represents the filtering of employees based on serviceType = it-agent */
public class CriteriaITAgent implements Criteria {

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {
    List<Employee> itAgentEmployees = new ArrayList<Employee>();

    for (Employee e : employees) {
      if (e.get_Service_Type().equalsIgnoreCase("it-agent")) {
        itAgentEmployees.add(e);
      }
    }
    return itAgentEmployees;
  }
}