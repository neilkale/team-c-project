package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.ArrayList;
import java.util.List;

public class SRCriteriaType implements SRCriteria {

  private String type;

  public SRCriteriaType(String type) {
    this.type = type;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> employeeList) {
    if (type.equals("")) return employeeList;
    ArrayList<ServiceRequest> toReturn = new ArrayList<ServiceRequest>();
    for (ServiceRequest each : employeeList) {
      if (each.get_serviceType().toUpperCase().contains(type.toUpperCase())) toReturn.add(each);
    }
    return toReturn;
  }
}
