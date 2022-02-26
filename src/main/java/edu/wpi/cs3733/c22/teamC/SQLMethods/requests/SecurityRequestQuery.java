package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SecurityRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SecurityRequestQuery extends Query<SecurityRequest> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public SecurityRequestQuery() {}

  @Override
  public SecurityRequest queryFactory(String[] inputs) {
    if (inputs.length != 8) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    SecurityRequest toReturn =
        new SecurityRequest(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
    return toReturn;
  }

  @Override
  public ArrayList<SecurityRequest> getAllNodeData() {
    SecurityRequest queryResult = null;
    ArrayList<SecurityRequest> allNodes = new ArrayList<>();

    try {
      String query = "SELECT * FROM SecurityRequestC";
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String reportBreach = rs.getString("reportBreach");
        String securityType = rs.getString("securityType");
        String urgency = rs.getString("urgency");

        queryResult =
            new SecurityRequest(
                ticketID,
                locationID,
                status,
                serviceType,
                assignment,
                reportBreach,
                securityType,
                urgency);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public void addNode(SecurityRequest object) throws SQLException {
    try {
      String query =
          "INSERT INTO SecurityRequestC VALUES "
              + "('"
              + object.get_ticketID()
              + "', '"
              + object.get_locationID()
              + "', '"
              + object.get_status()
              + "', '"
              + object.get_serviceType()
              + "','"
              + object.get_assignment()
              + "','"
              + object.get_reportBreach()
              + "','"
              + object.get_securityType()
              + "','"
              + object.get_urgency()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(SecurityRequest securityRequest) throws SQLException {
    String query =
        "DELETE FROM "
            + getQueryInput()
            + " WHERE ticketID = '"
            + securityRequest.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(SecurityRequest object) throws SQLException {
    String query =
        "UPDATE SecurityRequestC SET "
            + "locationID = '"
            + object.get_locationID()
            + "', status = '"
            + object.get_status()
            + "', serviceType = '"
            + object.get_serviceType()
            + "', assignment = '"
            + object.get_assignment()
            + "', reportBreach = '"
            + object.get_reportBreach()
            + "', securityType = '"
            + object.get_securityType()
            + "', urgency = '"
            + object.get_urgency()
            + "' WHERE ticketID = '"
            + object.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public String getUID(SecurityRequest each) throws SQLException {
    return each.get_ticketID();
  }

  @Override
  public String getQueryInput() {
    return "SecurityRequestC";
  }
}