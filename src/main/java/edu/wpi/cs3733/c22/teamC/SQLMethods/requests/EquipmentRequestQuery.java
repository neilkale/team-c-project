package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.EquipmentRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipmentRequestQuery extends Query<EquipmentRequest> {

  private DatabaseConnection dbConnection = super.dbConnection;

  public EquipmentRequestQuery() {}

  @Override
  public String getUID(EquipmentRequest each) throws SQLException {
    return each.get_ticketID();
  }

  @Override
  public EquipmentRequest queryFactory(String[] inputs) {
    if (inputs.length != 8) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    EquipmentRequest toReturn =
        new EquipmentRequest(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
    return toReturn;
  }

  public ArrayList<EquipmentRequest> getAllNodeData() {
    EquipmentRequest queryResult = null;
    ArrayList<EquipmentRequest> allNodes = new ArrayList<EquipmentRequest>();

    try {
      String sql = "SELECT * FROM EquipmentRequestC";
      ResultSet rs = dbConnection.executeQuery(sql);

      while (rs.next()) {
        String ticketID = rs.getString("ticketID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String serviceType = rs.getString("serviceType");
        String assignment = rs.getString("assignment");
        String urgency = rs.getString("urgency");
        String equipmentID = rs.getString("equipmentID");
        String pickupLocationID = rs.getString("pickupLocationID");

        queryResult =
            new EquipmentRequest(
                ticketID,
                locationID,
                status,
                serviceType,
                assignment,
                urgency,
                equipmentID,
                pickupLocationID);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  public void addNode(EquipmentRequest object) {
    try {
      String query =
          "INSERT INTO EquipmentRequestC "
              + "VALUES "
              + "('"
              + object.get_ticketID()
              + "','"
              + object.get_locationID()
              + "','"
              + object.get_status()
              + "','"
              + object.get_serviceType()
              + "','"
              + object.get_urgency()
              + "','"
              + object.get_equipmentID()
              + "','"
              + object.get_pickupLocationID()
              + "','"
              + object.get_assignment()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(EquipmentRequest object) throws SQLException {
    String query =
        "DELETE FROM EquipmentRequestC WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(EquipmentRequest object) throws SQLException {
    String query =
        "UPDATE EquipmentRequestC SET "
            + "locationID = '"
            + object.get_locationID()
            + "', status = '"
            + object.get_status()
            + "', serviceType = '"
            + object.get_serviceType()
            + "', assignment = '"
            + object.get_assignment()
            + "', urgency = '"
            + object.get_urgency()
            + "', equipmentID = '"
            + object.get_equipmentID()
            + "', pickupLocationID = '"
            + object.get_pickupLocationID()
            + "' WHERE ticketID = '"
            + object.get_ticketID()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public String getQueryInput() {
    return "EquipmentRequestC";
  }

  @Override
  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM EquipmentRequestC";
    ResultSet rs = dbConnection.executeQuery(sql);
    int rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }
}
