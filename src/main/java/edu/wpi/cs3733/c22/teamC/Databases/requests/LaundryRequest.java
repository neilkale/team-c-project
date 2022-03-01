package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LaundryRequestQuery;

public class LaundryRequest extends ServiceRequest implements DatabaseInterface {

  public LaundryRequest(
      String _ticketID,
      String _locationID,
      String _status,
      String _serviceType,
      String _assignment) {
    super(_ticketID, _locationID, _status, "laundry", _assignment);
  }

  @Override
  public LaundryRequestQuery getQueryInstance() {
    return new LaundryRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Laundry Request";
  }

  public String toString() {
    return "Laundry Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment();
  }

  @Override
  public String[] getFields() {
    return new String[] {"ID", "Location", "Status", "Service Type", "Assignment"};
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }
}
