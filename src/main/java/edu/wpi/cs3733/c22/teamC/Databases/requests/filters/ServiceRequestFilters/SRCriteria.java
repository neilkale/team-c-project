package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.List;

public interface SRCriteria {
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> employeeList);
}
