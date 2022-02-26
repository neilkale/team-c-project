package edu.wpi.cs3733.c22.teamC.Databases.requests.filters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.ArrayList;
import java.util.List;

/** this class represents the filtering of employees based on serviceType = transportation */
public class CriteriaTransportation implements Criteria {

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {
    List<Employee> transportationEmployees = new ArrayList<Employee>();

    for (Employee e : employees) {
      if (e.get_Service_Type().equalsIgnoreCase("transportation")) {
        transportationEmployees.add(e);
      }
    }
    return transportationEmployees;
  }
}