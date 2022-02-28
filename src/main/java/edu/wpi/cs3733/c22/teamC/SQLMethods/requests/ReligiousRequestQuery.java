package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ReligiousRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReligiousRequestQuery extends Query<ReligiousRequest> {
  private DatabaseConnection dbConnection = super.dbConnection;
  private Scanner scanner = new Scanner(System.in);

  public ReligiousRequestQuery() {}

  @Override
  public String getUID(ReligiousRequest each) throws SQLException {
    return each.get_ticketID();
  }

  public ReligiousRequest queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static ReligiousRequest staticQueryFactory(String[] inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    ReligiousRequest toReturn =
        new ReligiousRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
  }

  @Override
  public ArrayList<ReligiousRequest> getAllNodeData() {
    ReligiousRequest queryResult = null;
    ArrayList<ReligiousRequest> allNodes = new ArrayList<ReligiousRequest>();
    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);
      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String religion = rs.getString("religion");

        queryResult =
            new ReligiousRequest(ticketID, locationID, status, serviceType, assignment, religion);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public void addNode(ReligiousRequest object) {
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
              + object.get_religion()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(ReligiousRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(ReligiousRequest object) throws SQLException {

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
            + "', religion = '"
            + object.get_religion()
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
    return "ReligiousRequestC";
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
