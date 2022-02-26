package edu.wpi.cs3733.c22.teamC.SQLMethods;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.MongoDB.MongoLocation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationQuery extends Query<Location> {
  private DatabaseConnection dbConnection = super.dbConnection;
  private MongoLocation mongoLocation;

  public LocationQuery() {
    dbConnection.getMongoLocation();
    try {
      mongoLocation = new MongoLocation();
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
      ResultSet rs = queryLoc.dbConnection.executeQuery(query);
      if (rs.next()) {
        toReturn = rs.getString("NODEID");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return toReturn;
  }

  public Location queryFactory(String[] inputs) {
    if (inputs.length != 8) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    Location toReturn =
        new Location(
            inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
    return toReturn;
  }

  public ArrayList<Location> getAllNodeData() {
    Location queryResult = null;
    ArrayList<Location> allNodes = new ArrayList<Location>();

    if (!dbConnection.isMongulDB()) {
      try {
        String query = "SELECT * FROM " + getQueryInput();
        ResultSet rs = dbConnection.executeQuery(query);

        while (rs.next()) {
          String nodeID = rs.getString("nodeID");
          String xcoord = rs.getString("xcoord");
          String ycoord = rs.getString("ycoord");
          String floor = rs.getString("floor");
          String building = rs.getString("building");
          String nodeType = rs.getString("nodeType");
          String longName = rs.getString("longName");
          String shortName = rs.getString("shortName");

          queryResult =
              new Location(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName);
          allNodes.add(queryResult);
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {

      allNodes = dbInterfaceToLocation(mongoLocation.getAllNodeData());
    }
    return allNodes;
  }

  private ArrayList<Location> dbInterfaceToLocation(ArrayList<DatabaseInterface> input) {
    ArrayList<Location> toReturn = new ArrayList<>();
    for (DatabaseInterface d : input) {
      toReturn.add((Location) d);
    }
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
    mongoLocation.addItem(location);
  }

  @Override
  public void removeNode(Location location) throws SQLException {

    String query =
        "DELETE FROM " + getQueryInput() + " WHERE nodeID = '" + location.get_nodeID() + "'";
    dbConnection.execute(query);
    mongoLocation.removeItem(location);
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
    mongoLocation.changeItem(location);
  }

  @Override
  public String getQueryInput() {
    return "TOWERLOCATIONS";
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

  public Location findNodeByID(String target_ID) {
    try {
      String sql = "SELECT * FROM " + getQueryInput() + " WHERE nodeID = '" + target_ID + "'";
      ResultSet rs = dbConnection.executeQuery(sql);
      while (rs.next()) {
        String nodeID = rs.getString("nodeID");
        String xcoord = rs.getString("xcoord");
        String ycoord = rs.getString("ycoord");
        String floor = rs.getString("floor");
        String building = rs.getString("building");
        String nodeType = rs.getString("nodeType");
        String longName = rs.getString("longName");
        String shortName = rs.getString("shortName");
        Location queryResult =
            new Location(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName);
        return queryResult;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
