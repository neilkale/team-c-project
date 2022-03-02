package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.LanguageRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.SQLException;
import java.util.Scanner;

public class LanguageRequestQuery extends Query<LanguageRequest> {
  private DatabaseConnection dbConnection = super.dbConnection;
  private Scanner scanner = new Scanner(System.in);

  public LanguageRequestQuery() {}

  public LanguageRequest queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static LanguageRequest staticQueryFactory(String[] inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    LanguageRequest toReturn =
        new LanguageRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
  }

  @Override
  public void addNode(LanguageRequest object) {
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
              + object.get_language()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(LanguageRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(LanguageRequest object) throws SQLException {

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
            + "', language = '"
            + object.get_language()
            + "' WHERE ticketID = '"
            + object.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public String getUID(LanguageRequest each) throws SQLException {
    return each.get_ticketID();
  }

  @Override
  public String getQueryInput() {
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "LANGUAGEREQUESTC";
  }
}
