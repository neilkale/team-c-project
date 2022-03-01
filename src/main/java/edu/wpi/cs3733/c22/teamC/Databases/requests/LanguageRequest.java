package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;

/** this class is for the language service request data */
public class LanguageRequest extends ServiceRequest implements DatabaseInterface {

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

  @Override
  public String[] getFields() {
    return new String[] {"ID", "Location", "Status", "Service Type", "Assignment", "Language"};
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getLanguageRequestDao();
  }
}
