package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.ArrayList;
import java.util.List;

public class SRCriteriaStatus implements SRCriteria {

  private String status;

  public SRCriteriaStatus(String status) {
    this.status = status;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> employeeList) {
    if (status.equals("")) return employeeList;
    ArrayList<ServiceRequest> toReturn = new ArrayList<ServiceRequest>();
    for (ServiceRequest each : employeeList) {
      if (each.get_status().toUpperCase().contains(status.toUpperCase())) toReturn.add(each);
    }
    return toReturn;
  }
}
