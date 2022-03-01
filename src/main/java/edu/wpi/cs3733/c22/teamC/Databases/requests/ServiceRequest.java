package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.*;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** this class is for the medical equipment service request data */
public abstract class ServiceRequest implements DatabaseInterface {
  private String _ticketID;
  private String _locationID;
  private String _serviceType;
  private String _status;
  private String _assignment; // this would really be an employee

  @Override
  public String getUID() {
    return _ticketID;
  }

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

    InternalTransportRequestDaoImpl a;
    a = DaoSingleton.getInternalTransportRequestDao();
    output.addAll(a.getAllNodes());

    ITRequestDaoImpl b;
    b = DaoSingleton.getItRequestDao();
    output.addAll(b.getAllNodes());

    GiftRequestDaoImpl c;
    c = DaoSingleton.getGiftRequestDao();
    output.addAll(c.getAllNodes());

    EquipmentRequestDaoImpl d;
    d = DaoSingleton.getEquipmentRequestDao();
    output.addAll(d.getAllNodes());

    LanguageRequestDaoImpl e;
    e = DaoSingleton.getLanguageRequestDao();
    output.addAll(e.getAllNodes());

    LaundryRequestDaoImpl f;
    f = DaoSingleton.getLaundryRequestDao();
    output.addAll(f.getAllNodes());

    MaintenanceRequestDaoImpl g;
    g = DaoSingleton.getMaintenanceRequestDao();
    output.addAll(g.getAllNodes());

    MedicineRequestDaoImpl h;
    h = DaoSingleton.getMedicineRequestDao();
    output.addAll(h.getAllNodes());

    ReligiousRequestDaoImpl i; // jklm
    i = DaoSingleton.getReligiousRequestDao();
    output.addAll(i.getAllNodes());

    SanitationRequestDaoImpl j;
    j = DaoSingleton.getSanitationRequestDao();
    output.addAll(j.getAllNodes());

    SecurityRequestDaoImpl k;
    k = DaoSingleton.getSecurityRequestDao();
    output.addAll(k.getAllNodes());

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
          "[ServiceRequest::setStatus]: Error in setting value "
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

  // return all of the current ticketIDs being used for submitted requests
  public static ArrayList<String> getAvailableTicketIDs() {
    ArrayList<ServiceRequest> total = getAllServiceRequests();
    ArrayList<String> ids = new ArrayList<String>();
    total.forEach(
        serviceRequest -> {
          ids.add(serviceRequest.get_ticketID());
        });
    return ids;
  }

  protected static ArrayList<String> getServiceRequestTables() {
    ArrayList<String> list = new ArrayList<>();
    for (String each : Query.getTableNames()) {
      if (each.toUpperCase().contains("REQUEST")) list.add(each);
    }
    return list;
  }

  public abstract Query getQueryInstance();
  // THIS METHOD FOR EACH SERVICE REQUEST MUST BE FORMATTED THE SAME
  // -> TITLE OF REQUEST FIRST; EACH FIELD HAS TITLE FOLLOWED BY COLON; SPACE AFTER FOLLOWED BY
  // FIELD VALUE
  public abstract String getRequestType();

  @Override
  public String[] setValues(String[] values) {
    List<String> a = new ArrayList<>();
    Method getter;
    String[] fields = getFields();
    for (int i = 0; i < getFields().length; i++) {
      try {
        getter = this.getClass().getMethod("set_" + fields[i]);
        a.add((String) getter.invoke(this, new Object[] {values[i]}));
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    String[] toReturn = new String[a.size()];
    for (int i = 0; i < a.size(); i++) {
      toReturn[i] = a.get(i);
    }
    return toReturn;
  }

  @Override
  public String[] getValues() {
    List<String> a = new ArrayList<>();
    Method getter;
    for (String s : getFields()) {
      try {
        getter = this.getClass().getMethod("get_" + s);
        a.add((String) getter.invoke(this, new Object[] {}));
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    String[] toReturn = new String[a.size()];
    for (int i = 0; i < a.size(); i++) {
      toReturn[i] = a.get(i);
    }
    return toReturn;
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
