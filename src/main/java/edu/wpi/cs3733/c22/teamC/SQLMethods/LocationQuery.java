package edu.wpi.cs3733.c22.teamC.SQLMethods;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationQuery extends Query<Location> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public LocationQuery() {

    try {
      // mongoLocation = new MongoLocation();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[LocationQuery]: Something went wrong with connecting to the mongoDB");
    }
  }

  public static String createUniqueNodeID(String nodeType, String floor) {
    StringBuilder returnable = new StringBuilder();
    ArrayList<Location> locations = (new LocationQuery()).getAllNodeData();
    int highestNumber = 0;
    for (Location each : locations) {
      String scopedID = each.get_nodeID();
      if (scopedID.substring(1, 5).contains(nodeType)) {
        if (highestNumber < (Integer.parseInt(scopedID.substring(5, 8))))
          highestNumber = Integer.parseInt(scopedID.substring(5, 8));
      }
    }
    returnable.append('c');
    returnable.append(nodeType.toUpperCase());
    String nodeNumber = Integer.toString(highestNumber);
    while (nodeNumber.length() < 3) nodeNumber = "0" + nodeNumber;
    returnable.append(nodeNumber);
    returnable.append(floor.toUpperCase());
    return returnable.toString();
  }

  @Override
  public String getUID(Location each) throws SQLException {
    return each.get_nodeID();
  }

  public static String longToNodeID(String longName) {

    LocationQuery queryLoc = new LocationQuery();
    String query =
        "SELECT NODEID FROM " + queryLoc.getQueryInput() + " WHERE longName = '" + longName + "'";
    String toReturn = "";
    try {
      toReturn = DatabaseConnection.getInstance().executeQuery(query).get(0).toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return toReturn;
  }

  public Location queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static Location staticQueryFactory(String[] inputs) {
    if (inputs.length != 8) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    Location toReturn =
        new Location(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
    return toReturn;
  }

  @Override
  public void addNode(Location location) {
    try {
      String query =
          "INSERT INTO "
              + getQueryInput()
              + " VALUES "
              + "('"
              + location.get_nodeID()
              + "', '"
              + location.get_xcoord()
              + "', '"
              + location.get_ycoord()
              + "', '"
              + location.get_floor()
              + "', '"
              + location.get_building()
              + "', '"
              + location.get_nodeType()
              + "', '"
              + location.get_longName()
              + "', '"
              + location.get_shortName()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
    // mongoLocation.addItem(location);
  }

  @Override
  public void removeNode(Location location) throws SQLException {

    String query =
        "DELETE FROM " + getQueryInput() + " WHERE nodeID = '" + location.get_nodeID() + "'";
    dbConnection.execute(query);
    // mongoLocation.removeItem(location);
  }

  @Override
  public void editNode(Location location) throws SQLException {
    String query =
        "UPDATE "
            + getQueryInput()
            + " SET "
            + "nodeID = '"
            + location.get_nodeID()
            + "', xcoord = '"
            + location.get_xcoord()
            + "', ycoord = '"
            + location.get_ycoord()
            + "', floor = '"
            + location.get_floor()
            + "', building = '"
            + location.get_building()
            + "', nodeType = '"
            + location.get_nodeType()
            + "', longName = '"
            + location.get_longName()
            + "', shortName = '"
            + location.get_shortName()
            + "' WHERE "
            + "nodeID = '"
            + location.get_nodeID()
            + "'";
    dbConnection.execute(query);
    // mongoLocation.changeItem(location);
  }

  @Override
  public String getQueryInput() {
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "TOWERLOCATIONSC";
  }

  public Location findNodeByID(String target_ID) {
    try {
      String sql = "SELECT * FROM " + getQueryInput() + " WHERE nodeID = '" + target_ID + "'";
      return (Location) dbConnection.executeQuery(sql).get(0);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
