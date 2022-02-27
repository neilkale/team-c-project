package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.GiftRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GiftRequestQuery extends Query<GiftRequest> {

  private DatabaseConnection dbConnection = super.dbConnection;
  private Scanner scanner = new Scanner(System.in);

  public GiftRequestQuery() {}

  @Override
  public String getUID(GiftRequest each) throws SQLException {
    return each.get_ticketID();
  }


  public static GiftRequest queryFactory(String[] inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    GiftRequest toReturn =
        new GiftRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
  }

  @Override
  public ArrayList<GiftRequest> getAllNodeData() {
    GiftRequest queryResult = null;
    ArrayList<GiftRequest> allNodes = new ArrayList<GiftRequest>();
    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);
      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String giftType = rs.getString("giftType");

        queryResult =
            new GiftRequest(ticketID, locationID, status, serviceType, assignment, giftType);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public void addNode(GiftRequest giftRequest) {
    try {
      String query =
          "INSERT INTO "
              + getQueryInput()
              + " VALUES "
              + "('"
              + giftRequest.get_ticketID()
              + "', '"
              + giftRequest.get_locationID()
              + "', '"
              + giftRequest.get_status()
              + "', '"
              + giftRequest.get_serviceType()
              + "', '"
              + giftRequest.get_assignment()
              + "', '"
              + giftRequest.get_giftType()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(GiftRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(GiftRequest object) throws SQLException {
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
            + "', giftType = '"
            + object.get_giftType()
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
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "GIFTREQUESTC";
  }
}
