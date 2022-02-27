package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.GiftRequestQuery;

public class GiftRequest extends ServiceRequest implements DatabaseInterface {

  private String _giftType;

  public GiftRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String _giftType) {
    super(ticketID, locationID, status, "gift-distributor", assignment);
    this._giftType = _giftType;
  }

  public String get_giftType() {
    return _giftType;
  }

  public void set_giftType(String _giftType) {
    this._giftType = _giftType;
  }

  @Override
  public GiftRequestQuery getQueryInstance() {
    return new GiftRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Gift Request";
  }

  @Override
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {in[0], in[1], in[2], in[3], in[4], "Gift Type"};
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {in[0], in[1], in[2], in[3], in[4], this._giftType};
  }

  public String toString() {
    return "Gift Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nGiftType: "
        + _giftType;
  }

  @Override
  public String[] getValues() {
    return new String[] {"ID", "Location", "Status", "Service Type", "Assignment", "GiftType"};
  }

  @Override
  public String getName() {
    return null;
  }
}
