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

  @Override
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {
      in[0], in[1], in[2], in[3], in[4], "Report Breach", "Security Type", "Urgency"
    };
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {
      in[0], in[1], in[2], in[3], in[4], this._reportBreach, this._securityType, this._urgency
    };
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
