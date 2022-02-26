package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ITRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ITRequestQuery extends Query<ITRequest> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public ITRequestQuery() {}

  @Override
  public String getUID(ITRequest each) throws SQLException {
    return each.get_ticketID();
  }

  public ITRequest queryFactory(String[] inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    ITRequest toReturn =
        new ITRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
  }

  @Override
  public ArrayList<ITRequest> getAllNodeData() {
    ITRequest queryResult = null;
    ArrayList<ITRequest> allNodes = new ArrayList<>();

    try {
      String query = "SELECT * FROM ITRequestC";
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String issueType = rs.getString("issueType");

        queryResult =
            new ITRequest(ticketID, locationID, status, serviceType, assignment, issueType);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public void addNode(ITRequest object) {
    try {
      String query =
          "INSERT INTO ITRequestC VALUES "
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
              + object.get_issueType()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(ITRequest object) throws SQLException {
    String query = "DELETE FROM ITRequestC WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(ITRequest object) throws SQLException {
    String query =
        "UPDATE ITRequestC SET "
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
  public String getQueryInput() {
    return "ITRequestC";
  }

  @Override
  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM ITRequestC";
    ResultSet rs = dbConnection.executeQuery(sql);
    Integer rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }
}
