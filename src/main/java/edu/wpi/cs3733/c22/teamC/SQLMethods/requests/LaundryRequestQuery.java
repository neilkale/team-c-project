package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;
/** @author Aidan Burns 2/15/2022 This project does LaundryRequestQuery on the IntelliJ IDEA */
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.LaundryRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.SQLException;
import java.util.*;

public class LaundryRequestQuery extends Query<LaundryRequest> {

  private DatabaseConnection dbConnection = super.dbConnection;
  private Scanner scanner = new Scanner(System.in);

  public LaundryRequestQuery() {}

  @Override
  public String getUID(LaundryRequest each) throws SQLException {
    return each.get_ticketID();
  }

  public LaundryRequest queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static LaundryRequest staticQueryFactory(String[] inputs) {
    if (inputs.length != 5) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    LaundryRequest toReturn =
        new LaundryRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4]);
    return toReturn;
  }

  public void addNode(LaundryRequest object) {
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
              + "')";
      System.out.println(query);
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(LaundryRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  public void editNode(LaundryRequest object) throws SQLException {
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
    return "LAUNDRYREQUESTC";
  }
}
