package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SanitationRequestQuery;

/** this class is for the sanitation service request data */
public class SanitationRequest extends ServiceRequest {
  private String _messType;

  public SanitationRequest(
      String _ticketID,
      String _locationID,
      String _status,
      String _serviceType,
      String _assignment,
      String _messType) {
    super(_ticketID, _locationID, _status, "sanitation", _assignment);
    this._messType = _messType;
  }

  public String get_messType() {
    return _messType;
  }

  public void set_messType(String _messType) {
    this._messType = _messType;
  }

  @Override
  public SanitationRequestQuery getQueryInstance() {
    return new SanitationRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Sanitation Request";
  }

  public String toString() {
    return "Sanitation Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nMess Type: "
        + _messType;
  }

  /*
     String ticketID,
     String locationID,
     String status,
     String serviceType,
     String assignment,
     String messType
  */

  @Override
  public String[] getFields() {
    return new String[] {
      "ticketID", "locationID", "status", "serviceType", "assignment", "messType"
    };
  }

  @Override
  public String getName() {
    return null;
  }
}
