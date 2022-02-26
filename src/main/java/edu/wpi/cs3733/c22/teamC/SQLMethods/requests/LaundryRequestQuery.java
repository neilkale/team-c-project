package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;
/** @author Aidan Burns 2/15/2022 This project does LaundryRequestQuery on the IntelliJ IDEA */
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.LaundryRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
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
    if (inputs.length != 5) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    LaundryRequest toReturn =
        new LaundryRequest(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4]);
    return toReturn;
  }

  @Override
  public ArrayList<LaundryRequest> getAllNodeData() {
    LaundryRequest queryResult = null;
    ArrayList<LaundryRequest> allNodes = new ArrayList<>();

    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");

        queryResult = new LaundryRequest(ticketID, locationID, status, serviceType, assignment);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
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
    String query = "DELETE FROM LaundryRequestC WHERE ticketID = '" + object.get_ticketID() + "'";
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
    return "LaundryRequestC";
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
