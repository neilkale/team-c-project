package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.InternalTransportRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class InternalTransportRequestQuery extends Query<InternalTransportRequest> {
  private DatabaseConnection dbConnection = super.dbConnection;
  private Scanner scanner = new Scanner(System.in);

  public InternalTransportRequestQuery() {}

  @Override
  public String getUID(InternalTransportRequest each) throws SQLException {
    return each.get_ticketID();
  }

  public static InternalTransportRequest queryFactory(String[] inputs) {
    if (inputs.length != 7) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    InternalTransportRequest toReturn =
        new InternalTransportRequest(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
    return toReturn;
  }

  @Override
  public ArrayList<InternalTransportRequest> getAllNodeData() {
    InternalTransportRequest queryResult = null;
    ArrayList<InternalTransportRequest> allNodes = new ArrayList<>();

    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String dropOff = rs.getString("dropOff");
        String urgency = rs.getString("urgency");

        queryResult =
            new InternalTransportRequest(
                ticketID, locationID, status, serviceType, assignment, dropOff, urgency);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public void addNode(InternalTransportRequest object) {
    try {
      String query =
          "INSERT INTO "
              + getQueryInput()
              + " VALUES "
              + "('"
              + object.get_ticketID()
              + "', '"
              + object.get_locationID()
              + "', '"
              + object.get_status()
              + "', '"
              + object.get_serviceType()
              + "', '"
              + object.get_assignment()
              + "', '"
              + object.get_dropOff()
              + "', '"
              + object.get_urgency()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(InternalTransportRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(InternalTransportRequest object) throws SQLException {
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
            + "', dropOff = '"
            + object.get_dropOff()
            + "', urgency = '"
            + object.get_urgency()
            + "' WHERE ticketID = '"
            + object.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public String getQueryInput() {
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "INTERNALTRANSPORTREQUESTC";
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
}
