package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SanitationRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SanitationRequestQuery extends Query<SanitationRequest> {

  private DatabaseConnection dbConnection = super.dbConnection;
  private Scanner scanner = new Scanner(System.in);

  public SanitationRequestQuery() {}

  @Override
  public String getUID(SanitationRequest each) throws SQLException {
    return each.get_ticketID();
  }

  public static SanitationRequest queryFactory(String[] inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    SanitationRequest toReturn =
        new SanitationRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
  }

  @Override
  public ArrayList<SanitationRequest> getAllNodeData() {
    SanitationRequest queryResult = null;
    ArrayList<SanitationRequest> allNodes = new ArrayList<>();

    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String messType = rs.getString("messType");

        queryResult =
            new SanitationRequest(ticketID, locationID, status, serviceType, assignment, messType);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public void addNode(SanitationRequest object) {
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
              + object.get_messType()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(SanitationRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(SanitationRequest object) throws SQLException {
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
            + "', messType = '"
            + object.get_messType()
            + "' WHERE ticketID = '"
            + object.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  public static String staticGetQueryInput() {
    return "SANITATIONREQUESTC";
  }

  @Override
  public String getQueryInput() {
    return staticGetQueryInput();
  }

  @Override
  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM SanitationRequestC";
    ResultSet rs = dbConnection.executeQuery(sql);
    Integer rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }
}
