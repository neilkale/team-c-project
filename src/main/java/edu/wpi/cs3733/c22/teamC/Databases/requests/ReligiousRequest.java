package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.ReligiousRequestQuery;

public class ReligiousRequest extends ServiceRequest implements DatabaseInterface {

  private String _religion;

  public ReligiousRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String religion) {
    super(ticketID, locationID, status, "religion", assignment);
    this._religion = religion;
  }

  public String get_religion() {
    return _religion;
  }

  public void set_religion(String religion) {
    this._religion = religion;
  }

  @Override
  public ReligiousRequestQuery getQueryInstance() {
    return new ReligiousRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Religious Request";
  }

  @Override
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {in[0], in[1], in[2], in[3], in[4], "Religion"};
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {in[0], in[1], in[2], in[3], in[4], this._religion};
  }

  public String toString() {
    return "Religious Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nReligion: "
        + _religion;
  }

  @Override
  public String[] getValues() {
    return new String[] {
      "ticketID", "locationID", "status", "serviceType", "assignment", "religion",
    };
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }
}
