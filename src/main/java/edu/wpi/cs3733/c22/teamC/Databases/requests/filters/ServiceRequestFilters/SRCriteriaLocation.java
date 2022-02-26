package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.ArrayList;
import java.util.List;

public class SRCriteriaLocation implements SRCriteria {

  private String locID;

  public SRCriteriaLocation(String locID) {
    this.locID = locID;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> employeeList) {
    if (locID.equals("")) return employeeList;
    ArrayList<ServiceRequest> toReturn = new ArrayList<ServiceRequest>();
    for (ServiceRequest each : employeeList) {
      if (each.get_locationID().toUpperCase().contains(locID.toUpperCase())) toReturn.add(each);
    }
    return toReturn;
  }
}
