package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.LocationDaoImpl;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.ArrayList;
import java.util.List;

public class SRCriteriaLocationLong implements SRCriteria {

  private String locLongName;

  public SRCriteriaLocationLong(String locLongName) {
    this.locLongName = locLongName;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> employeeList) {
    if (locLongName.equals("")) return employeeList;
    ArrayList<ServiceRequest> toReturn = new ArrayList<ServiceRequest>();
    ArrayList<String> locationIDs = new ArrayList<>();
    LocationDaoImpl e = DaoSingleton.getLocationDao();
    for (Location eachLoc : e.getAllNodes()) {
      if (eachLoc.get_longName().toUpperCase().contains(locLongName.toUpperCase()))
        locationIDs.add(eachLoc.get_nodeID());
    }
    for (ServiceRequest each : employeeList) {
      boolean found = false;
      for (int j = 0; (j < locationIDs.size() && !found); j++) {
        if (locationIDs.get(j).equals(each.get_locationID())) toReturn.add(each);
      }
    }
    return toReturn;
  }
}
