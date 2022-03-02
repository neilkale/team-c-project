package edu.wpi.cs3733.c22.teamC.SQLMethods;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.Databases.MedicalEquipment;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicalEquipmentQuery extends Query<MedicalEquipment> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public MedicalEquipmentQuery() {
    try {
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

  public MedicalEquipment queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static MedicalEquipment staticQueryFactory(String[] inputs) {
    if (inputs.length != 6) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    MedicalEquipment toReturn =
        new MedicalEquipment(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
    return toReturn;
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
          "INSERT INTO "
              + getQueryInput()
              + " VALUES "
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
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "EQUIPMENTC";
  }
}
