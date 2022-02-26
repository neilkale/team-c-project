package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.ArrayList;
import java.util.List;

public class SRCriteriaID implements SRCriteria {

  private String ID;

  public SRCriteriaID(String ID) {
    this.ID = ID;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> employeeList) {
    if (ID.equals("")) return employeeList;
    ArrayList<ServiceRequest> toReturn = new ArrayList<ServiceRequest>();
    for (ServiceRequest each : employeeList) {
      if (each.get_ticketID().equals(ID)) toReturn.add(each);
    }
    return toReturn;
  }
}
