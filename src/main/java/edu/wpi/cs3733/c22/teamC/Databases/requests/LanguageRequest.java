package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;

/** this class is for the language service request data */
public class LanguageRequest extends ServiceRequest {

  private String _language;

  public LanguageRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String language) {
    super(ticketID, locationID, status, "interpreter", assignment);
    this._language = language;
  }

  public String get_language() {
    return _language;
  }

  public void set_language(String language) {
    this._language = language;
  }

  @Override
  public LanguageRequestQuery getQueryInstance() {
    return new LanguageRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Language Request";
  }

  @Override
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {in[0], in[1], in[2], in[3], in[4], "Language"};
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {in[0], in[1], in[2], in[3], in[4], this._language};
  }

  public String toString() {
    return "Language Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nLanguage: "
        + _language;
  }
}
