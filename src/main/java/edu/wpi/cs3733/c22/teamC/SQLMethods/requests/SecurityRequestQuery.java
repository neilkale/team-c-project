package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SecurityRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.SQLException;

public class SecurityRequestQuery extends Query<SecurityRequest> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public SecurityRequestQuery() {}

  public SecurityRequest queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static SecurityRequest staticQueryFactory(String[] inputs) {
    if (inputs.length != 8) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    SecurityRequest toReturn =
        new SecurityRequest(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
    return toReturn;
  }

  @Override
  public void addNode(SecurityRequest object) throws SQLException {
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
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "SECURITYREQUESTC";
  }
}
