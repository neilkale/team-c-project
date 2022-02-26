package edu.wpi.cs3733.c22.teamC.Databases.requests.filters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.ArrayList;
import java.util.List;

/** this class represents the filtering of employees based on serviceType = maintenance */
public class CriteriaMaintenance implements Criteria {

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {
    List<Employee> maintenanceEmployees = new ArrayList<Employee>();

    for (Employee e : employees) {
      if (e.get_Service_Type().equalsIgnoreCase("maintenance")) {
        maintenanceEmployees.add(e);
      }
    }
    return maintenanceEmployees;
  }
}