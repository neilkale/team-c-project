package edu.wpi.cs3733.c22.teamC.SQLMethods.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicalEquipmentQuery extends Query<MedicalEquipment> {
  private DatabaseConnection dbConnection = super.dbConnection;
  // private MongoEquipment mongoEquipment;

  public MedicalEquipmentQuery() {
    try {
      // mongoEquipment = new MongoEquipment();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(
          "[MedicalEquipmentQuery]: Something went wrong with connecting to the mongoDB");
    }
  }

  @Override
  public String getUID(MedicalEquipment each) throws SQLException {
    return each.get_equipmentID();
  }

  @Override
  public MedicalEquipment queryFactory(String[] inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    MedicalEquipment toReturn =
        new MedicalEquipment(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
  }

  @Override
  public ArrayList<MedicalEquipment> getAllNodeData() {

    MedicalEquipment queryResult = null;
    ArrayList<MedicalEquipment> allNodes = new ArrayList<MedicalEquipment>();

    try {
      String query = "SELECT * FROM EquipmentC";
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String equipmentID = rs.getString("equipmentID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String equipmentType = rs.getString("equipmentType");
        String name = rs.getString("name");
        String lastKnownTime = rs.getString("lastKnownTime");

        queryResult =
            new MedicalEquipment(
                equipmentID, locationID, lastKnownTime, status, equipmentType, name);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return allNodes;
  }

  private ArrayList<MedicalEquipment> dbInterfaceToEquipment(ArrayList<DatabaseInterface> input) {
    ArrayList<MedicalEquipment> toReturn = new ArrayList<>();
    for (DatabaseInterface d : input) {
      toReturn.add((MedicalEquipment) d);
    }
    return toReturn;
  }

  @Override
  public void addNode(MedicalEquipment object) {
    // mongoEquipment.addItem(object);
    try {
      String query =
          "INSERT INTO EquipmentC VALUES "
              + "('"
              + object.get_equipmentID()
              + "', '"
              + object.get_locationID()
              + "', '"
              + object.get_lastKnownTime()
              + "', '"
              + object.get_status()
              + "', '"
              + object.get_equipmentType()
              + "', '"
              + object.get_name()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(MedicalEquipment object) throws SQLException {}

  @Override
  public void editNode(MedicalEquipment object) throws SQLException {}

  @Override
  public String getQueryInput() {
    return "EquipmentC";
  }

  @Override
  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM EquipmentC";
    ResultSet rs = dbConnection.executeQuery(sql);
    Integer rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }
}
