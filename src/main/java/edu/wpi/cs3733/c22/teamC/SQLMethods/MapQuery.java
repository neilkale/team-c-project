package edu.wpi.cs3733.c22.teamC.SQLMethods;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.Map;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapQuery extends Query<Map> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public MapQuery() {}

  public Map queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static Map staticQueryFactory(String[] inputs) {
    if (inputs.length != 4) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    Map toReturn = new Map(inputs[0], inputs[1], inputs[2], inputs[3]);
    return toReturn;
  }

  @Override
  public ArrayList<Map> getAllNodeData() {
    return null;
  }

  @Override
  public void addNode(Map object) {
    try {
      String query =
          "INSERT INTO "
              + getQueryInput()
              + " VALUES "
              + "('"
              + object.get_buildingName()
              + "', '"
              + object.get_floorName()
              + "', '"
              + object.get_imagePath()
              + "', '"
              + object.get_floorNumber()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(Map object) throws SQLException {}

  @Override
  public void editNode(Map object) throws SQLException {}

  @Override
  public String getUID(Map each) throws SQLException {
    return each.get_imagePath();
  }

  @Override
  public String getQueryInput() {
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "MAPSC";
  }

  @Override
  public Integer getNumRows() throws SQLException {
    return dbConnection.executeQuery("SELECT * FROM " + getQueryInput()).size();
  }
}
