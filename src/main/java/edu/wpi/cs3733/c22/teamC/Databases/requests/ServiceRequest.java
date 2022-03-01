package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LaundryRequestQuery;
import java.sql.ResultSet;
import java.util.ArrayList;

/** this class is for the medical equipment service request data */
public abstract class ServiceRequest {
  private String _ticketID;
  private String _locationID;
  private String _serviceType;
  private String _status;
  private String _assignment; // this would really be an employee

  public ServiceRequest(
      String ticketID,
      String locationID,
      String status /*, String _assignment*/,
      String serviceType,
      String assignment) {
    this._ticketID = ticketID;
    this._locationID = locationID;
    this._status = status;
    this._serviceType = serviceType;
    this._assignment = assignment;
  }

  public ServiceRequest(String ticketID) {
    this._ticketID = ticketID;
    this._locationID = null;
    this._status = null;
    this._assignment = null;
  }

  public static ArrayList<ServiceRequest> getAllServiceRequests() {
    ArrayList<ServiceRequest> output = new ArrayList<>();

    output.addAll((new InternalTransportRequestQuery()).getAllNodeData());
    output.addAll((new ITRequestQuery()).getAllNodeData());
    output.addAll((new GiftRequestQuery()).getAllNodeData());
    output.addAll((new EquipmentRequestQuery()).getAllNodeData());
    output.addAll((new LanguageRequestQuery()).getAllNodeData());
    output.addAll((new LaundryRequestQuery()).getAllNodeData());
    output.addAll((new MaintenanceRequestQuery()).getAllNodeData());
    output.addAll((new MedicineRequestQuery()).getAllNodeData());
    output.addAll((new ReligiousRequestQuery()).getAllNodeData());
    output.addAll((new SanitationRequestQuery()).getAllNodeData());
    output.addAll((new SecurityRequestQuery()).getAllNodeData());

    return output;
  }

  public static ArrayList<ServiceRequest> getAllOfStatus(String statusIn) {
    ArrayList<ServiceRequest> allNodes = new ArrayList<>();
    for (ServiceRequest each : getAllServiceRequests()) {
      if (each.get_status().toUpperCase().contains(statusIn.toUpperCase())) allNodes.add(each);
    }
    return allNodes;
  }
  // DONE
  // WAITING FOR
  // CANCELLED
  // BLANK
  public ArrayList<ServiceRequest> allOfBlank() {
    return getAllOfStatus("BLANK");
  }

  public ArrayList<ServiceRequest> allOfDone() {
    return getAllOfStatus("DONE");
  }

  public ArrayList<ServiceRequest> allOfCancelled() {
    return getAllOfStatus("CANCELLED");
  }

  public ArrayList<ServiceRequest> allOfWaiting() {
    return getAllOfStatus("WAITING FOR");
  }

  public static ArrayList<ServiceRequest> getAllServiceRequests(String assignment) {
    ArrayList<ServiceRequest> input = getAllServiceRequests();
    ArrayList<ServiceRequest> output = new ArrayList<>();
    for (ServiceRequest each : input) {
      if (each.get_assignment()
          .toLowerCase()
          .contains(
              assignment
                  .toLowerCase())) { // If the service request contains the worker/assignment given
        // then
        // push it to the output
        output.add(each);
      }
    }
    return output;
  }

  public String get_ticketID() {
    return _ticketID;
  }

  public void set_ticketID(String _ticketID) {
    this._ticketID = _ticketID;
  }

  public String get_locationID() {
    return _locationID;
  }

  public void set_locationID(String _locationID) {
    this._locationID = _locationID;
  }

  //  public String get_serviceType() {
  //    return _serviceType;
  //  }
  //
  //  public void set_serviceType(String _serviceType) {
  //    this._serviceType = _serviceType;
  //  }

  public String get_status() {
    return _status;
  }

  public void set_status(String _status) {
    this._status = _status;
  }

  public String get_assignment() {
    return _assignment;
  }

  public void set_assignment(String _assignment) {
    this._assignment = _assignment;
  }

  public String get_serviceType() {
    return _serviceType;
  }

  public static String getNewestID() {
    int largest = 0;
    try {
      ArrayList<String> list = getServiceRequestTables();
      for (String table : list) {
        String query = "SELECT TICKETID FROM " + table;
        ResultSet rsTable = DatabaseConnection.getInstance().executeQuery(query);
        while (rsTable.next()) {
          if ((rsTable.getString("TICKETID")).matches("[0-9]+")) {
            int got = Integer.parseInt(rsTable.getString("TICKETID"));

            if (got > largest) {
              largest = got;
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return Integer.toString(largest + 1);
  }

  public boolean setStatus(
      String statusIn) { // sets the status of the service request - ASSUMES A UNIQUE TICKET ID!!!!!
    ArrayList<String> tables = new ArrayList<>();
    try {
      tables = getServiceRequestTables();

      boolean found = false;
      for (int i = 0; (i < tables.size()) || (!found); i++) {
        String query = "SELECT * FROM " + tables.get(i) + " WHERE TICKETID = " + this._ticketID;
        ResultSet request = DatabaseConnection.getInstance().executeQuery(query);
        if (request.next()) {
          found = true;
          DatabaseConnection.getInstance()
              .execute(
                  "UPDATE "
                      + tables.get(i)
                      + " SET STATUS = "
                      + statusIn
                      + " WHERE TICKETID = "
                      + _ticketID);
        }
      }

      // for
      this._status = statusIn;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(
          "[ServiceRequst::setStatus]: Error in setting value "
              + statusIn
              + " given [ServiceRequest]:"
              + this.toString());
      return false;
    }

    return true;
  }

  public String getStatusDB() { // gets the status of the service request - ASSUMES A UNIQUE TICKET
    // ID!!!!!
    ArrayList<String> tables = new ArrayList<>();
    try {

      tables = getServiceRequestTables();

      for (int i = 0; (i < tables.size()); i++) {
        String query = "SELECT * FROM " + tables.get(i) + " WHERE TICKETID = " + this._ticketID;
        ResultSet request = DatabaseConnection.getInstance().executeQuery(query);
        if (request.next()) {
          return request.getString("TICKETID");
        }
      }

      // for
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(
          "[ServiceRequest::getStatus]: Error in getting value "
              + " given [ServiceRequest]:"
              + this.toString());
      return null;
    }

    return null;
  }

  /**
   * @return An array of two integer, Index 0 being the total amount of service requests, Index 1
   *     being the total amount of complete service requests
   */
  public static int[] getTotalAndComplete() {
    ArrayList<ServiceRequest> total = getAllServiceRequests();
    int completed = 0;
    for (ServiceRequest each : total) {
      if (each.get_status().equalsIgnoreCase("done")
          || each.get_status().equalsIgnoreCase("complete")
          || each.get_status().equalsIgnoreCase("finished")) {
        completed++;
      }
    }
    return new int[] {total.size(), completed};
  }

  protected static ArrayList<String> getServiceRequestTables() {
    ArrayList<String> list = new ArrayList<>();
    for (String each : DatabaseConnection.getTableNames()) {
      if (each.toUpperCase().contains("REQUEST")) list.add(each);
    }
    return list;
  }

  public abstract Query getQueryInstance();
  // THIS METHOD FOR EACH SERVICE REQUEST MUST BE FORMATTED THE SAME
  // -> TITLE OF REQUEST FIRST; EACH FIELD HAS TITLE FOLLOWED BY COLON; SPACE AFTER FOLLOWED BY
  // FIELD VALUE
  public abstract String getRequestType();

  public abstract String[] getFieldNames();

  public abstract String[] getFieldValues();

  public String[] getGenericFieldNames() {
    return new String[] {"Ticket ID", "Location ID", "Status", "Service Type", "Assignment"};
  }
  //    this._ticketID = ticketID;
  //    this._locationID = locationID;
  //    this._status = status;
  //    this._serviceType = serviceType;
  //    this._assignment = assignment;
  public String[] getGenericFieldValues() {
    return new String[] {
      this._ticketID, this._locationID, this._status, this._serviceType, this._assignment
    };
  }

  @Override
  public String toString() {
    return "ServiceRequest{"
        + "_ticketID='"
        + _ticketID
        + '\''
        + ", locationID='"
        + _locationID
        + '\''
        + ", _status='"
        + _status
        + '\''
        + '}';
  }
}
