package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.ITRequestQuery;

/** this class is for the IT service request data */
public class ITRequest extends ServiceRequest implements DatabaseInterface {
  private String _issueType;

  public ITRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String _issueType) {
    super(ticketID, locationID, status, "interpreter", assignment);
    this._issueType = _issueType;
  }

  public String get_issueType() {
    return _issueType;
  }

  public void set_issueType(String _issueType) {
    this._issueType = _issueType;
  }

  @Override
  public ITRequestQuery getQueryInstance() {
    return new ITRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "IT Request";
  }

  public String toString() {
    return "IT Request\nID:  "
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
  public String[] getFields() {
    return new String[] {"ticketID", "status", "serviceType", "assignment", "issueType"};
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getItRequestDao();
  }
}
