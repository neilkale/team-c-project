package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MaintenanceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaintenanceRequestQuery extends Query<MaintenanceRequest> {

  private DatabaseConnection dbConnection = super.dbConnection;
  private Scanner scanner = new Scanner(System.in);

  public MaintenanceRequestQuery() {}

  @Override
  public MaintenanceRequest queryFactory(String... inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    MaintenanceRequest toReturn =
        new MaintenanceRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
  }

  @Override
  public ArrayList<MaintenanceRequest> getAllNodeData() {
    MaintenanceRequest queryResult = null;
    ArrayList<MaintenanceRequest> allNodes = new ArrayList<MaintenanceRequest>();
    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);
      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String issueType = rs.getString("issueType");

        queryResult =
            new MaintenanceRequest(
                ticketID, locationID, status, serviceType, assignment, issueType);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public String getUID(MaintenanceRequest each) throws SQLException {
    return each.get_ticketID();
  }

  @Override
  public void addNode(MaintenanceRequest maintenanceRequest) {
    try {
      String query =
          "INSERT INTO "
              + getQueryInput()
              + " VALUES "
              + "('"
              + maintenanceRequest.get_ticketID()
              + "', '"
              + maintenanceRequest.get_locationID()
              + "', '"
              + maintenanceRequest.get_status()
              + "', '"
              + maintenanceRequest.get_serviceType()
              + "', '"
              + maintenanceRequest.get_assignment()
              + "', '"
              + maintenanceRequest.get_issueType()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(MaintenanceRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(MaintenanceRequest object) throws SQLException {
    String query =
        "UPDATE "
            + getQueryInput()
            + " SET "
            + "locationID = '"
            + object.get_locationID()
            + "', status = '"
            + object.get_status()
            + "', serviceType = '"
            + object.get_serviceType()
            + "', assignment = '"
            + object.get_assignment()
            + "', issueType = '"
            + object.get_issueType()
            + "' WHERE ticketID = '"
            + object.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM " + getQueryInput();
    ResultSet rs = dbConnection.executeQuery(sql);
    Integer rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }

  @Override
  public String getQueryInput() {
    return "MAINTENANCEREQUESTC";
  }
}
