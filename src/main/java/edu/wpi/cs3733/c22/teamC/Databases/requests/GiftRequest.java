package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
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
  public String[] getFields() {
    return new String[] {"ticketID", "status", "serviceType", "assignment", "giftType"};
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getGiftRequestDao();
  }
}
