package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MaintenanceRequestQuery;

/** this class is for the maintenance service request data */
public class MaintenanceRequest extends ServiceRequest implements DatabaseInterface {
  private String _issueType;

  public MaintenanceRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String _issueType) {
    super(ticketID, locationID, status, "maintenance", assignment);
    this._issueType = _issueType;
  }

  public String get_issueType() {
    return _issueType;
  }

  public void set_issueType(String _issueType) {
    this._issueType = _issueType;
  }

  @Override
  public MaintenanceRequestQuery getQueryInstance() {
    return new MaintenanceRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Maintenance Request";
  }

  @Override
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {in[0], in[1], in[2], in[3], in[4], "Issue Type"};
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {in[0], in[1], in[2], in[3], in[4], this._issueType};
  }

  public String toString() {
    return "Maintenance Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nIssue Type: "
        + _issueType;
  }

  @Override
  public String[] getValues() {
    return new String[] {"ID", "Location", "Status", "Service Type", "Assignment", "Issue Type"};
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }
}
