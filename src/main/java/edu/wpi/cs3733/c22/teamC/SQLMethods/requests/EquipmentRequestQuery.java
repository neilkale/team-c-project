package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.requests.EquipmentRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.SQLException;

public class EquipmentRequestQuery extends Query<EquipmentRequest> {

  private DatabaseConnection dbConnection = super.dbConnection;

  public EquipmentRequestQuery() {}

  @Override
  public String getUID(EquipmentRequest each) throws SQLException {
    return each.get_ticketID();
  }

  public EquipmentRequest queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static EquipmentRequest staticQueryFactory(String[] inputs) {
    if (inputs.length != 8) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    EquipmentRequest toReturn =
        new EquipmentRequest(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
    return toReturn;
  }

  public void addNode(EquipmentRequest object) {
    try {
      String query =
          "INSERT INTO "
              + getQueryInput()
              + " "
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
              + object.get_assignment()
              + "','"
              + object.get_urgency()
              + "','"
              + object.get_equipmentID()
              + "','"
              + object.get_pickupLocationID()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(EquipmentRequest object) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE " + "ticketID = '" + object.get_ticketID() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(EquipmentRequest object) throws SQLException {
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
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "EQUIPMENTREQUESTC";
  }
}
