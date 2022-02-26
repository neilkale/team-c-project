package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LaundryRequestQuery;

public class LaundryRequest extends ServiceRequest {

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

  @Override
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {in[0], in[1], in[2], in[3], in[4]};
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {in[0], in[1], in[2], in[3], in[4]};
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
}
