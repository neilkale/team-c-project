package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicineRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineRequestQuery extends Query<MedicineRequest> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public MedicineRequestQuery() {}

  public MedicineRequest queryFactory(String[] inputs) {
    if (inputs.length != 8) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    MedicineRequest toReturn =
        new MedicineRequest(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
    return toReturn;
  }

  @Override
  public String getUID(MedicineRequest each) throws SQLException {
    return each.get_ticketID();
  }

  @Override
  public ArrayList<MedicineRequest> getAllNodeData() {
    MedicineRequest queryResult = null;
    ArrayList<MedicineRequest> allNodes = new ArrayList<>();

    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String medicineType = rs.getString("medicineType");
        String quantity = rs.getString("quantity");
        String urgency = rs.getString("urgency");

        queryResult =
            new MedicineRequest(
                ticketID,
                locationID,
                status,
                serviceType,
                assignment,
                medicineType,
                quantity,
                urgency);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  @Override
  public void addNode(MedicineRequest object) {
    try {
      String query =
          "INSERT INTO MedicineRequestC VALUES "
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
              + object.get_medicineType()
              + "', '"
              + object.get_quantity()
              + "', '"
              + object.get_urgency()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(MedicineRequest object) throws SQLException {
    String query = "DELETE FROM MedicineRequestC WHERE ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(MedicineRequest object) throws SQLException {
    String query =
        "UPDATE MedicineRequestC SET "
            + "locationID = '"
            + object.get_locationID()
            + "', status = '"
            + object.get_status()
            + "', serviceType = '"
            + object.get_serviceType()
            + "', assignment = '"
            + object.get_assignment()
            + "', medicineType = '"
            + object.get_medicineType()
            + "', quantity = '"
            + object.get_quantity()
            + "', urgency = '"
            + object.get_urgency()
            + "' WHERE ticketID = '"
            + object.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public String getQueryInput() {
    return "MedicineRequestC";
  }

  @Override
  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM MedicineRequestC";
    ResultSet rs = dbConnection.executeQuery(sql);
    Integer rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }
}