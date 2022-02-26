package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.ArrayList;
import java.util.List;

public class SRCriteriaAssignment implements SRCriteria {

  private String assignment;

  public SRCriteriaAssignment(String assignment) {
    this.assignment = assignment;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> employeeList) {
    if (assignment.equals("")) return employeeList;
    ArrayList<ServiceRequest> toReturn = new ArrayList<ServiceRequest>();
    for (ServiceRequest each : employeeList) {
      if (each.get_assignment().toUpperCase().contains(assignment.toUpperCase()))
        toReturn.add(each);
    }
    return toReturn;
  }
}
