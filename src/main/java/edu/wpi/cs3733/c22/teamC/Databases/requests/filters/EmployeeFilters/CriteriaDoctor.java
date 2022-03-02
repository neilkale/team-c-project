package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.EmployeeFilters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.ArrayList;
import java.util.List;

/** this class represents the filtering of employees based on serviceType = doctor */
public class CriteriaDoctor implements Criteria {

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {
    List<Employee> doctors = new ArrayList<Employee>();

    for (Employee e : employees) {
      if (e.get_service_type().equalsIgnoreCase("doctor")) {
        doctors.add(e);
      }
    }
    return doctors;
  }
}
