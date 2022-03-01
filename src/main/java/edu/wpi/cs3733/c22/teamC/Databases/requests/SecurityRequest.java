package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SecurityRequestQuery;

public class SecurityRequest extends ServiceRequest {
  private String _reportBreach;
  private String _urgency;
  private String _securityType;

  public SecurityRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String reportBreach,
      String securityType,
      String urgency) {
    super(ticketID, locationID, status, "security", assignment);
    this._reportBreach = reportBreach;
    this._securityType = securityType;
    this._urgency = urgency;
  }

  public String get_urgency() {
    return _urgency;
  }

  public void set_urgency(String _urgency) {
    this._urgency = _urgency;
  }

  public String get_reportBreach() {
    return _reportBreach;
  }

  public void set_reportBreach(String _reportBreach) {
    this._reportBreach = _reportBreach;
  }

  public String get_securityType() {
    return _securityType;
  }

  public void set_securityType(String _securityType) {
    this._securityType = _securityType;
  }

  @Override
  public SecurityRequestQuery getQueryInstance() {
    return new SecurityRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Security Request";
  }

  public String toString() {
    return "Security Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nReport Breach: "
        + get_reportBreach()
        + "\nSecurity Type: "
        + get_securityType()
        + "\nUrgency: "
        + get_urgency();
  }

  /*
  String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String reportBreach,
      String securityType,
      String urgency
   */
  @Override
  public String[] getFields() {
    return new String[] {
      "ticketID", "status", "serviceType", "assignment", "reportBreach", "securityType", "urgency"
    };
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }
}

//  public SecurityRequest(
//          String ticketID,
//          String locationID,
//          String status,
//          String serviceType,
//          String assignment,
//          String reportBreach,
//          String securityType,
//          String urgency)
