package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
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

  /*
  _ticketID;
  private String _locationID;
  private String _serviceType;
  private String _status;
  private String _assignment
   */

  @Override
  public String[] getFields() {
    return new String[] {"ticketID", "locationID", "status", "serviceType", "assignment"};
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getLaundryRequestDao();
  }
}
