package edu.wpi.cs3733.c22.teamC.PathFinding;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.LocationDaoImpl;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.MaintenanceRequestDaoImpl;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MaintenanceRequest;
import edu.wpi.cs3733.c22.teamC.PathFinding.AStarImpl.AStar;
import edu.wpi.cs3733.c22.teamC.PathFinding.AStarImpl.Node;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class PathFinder {

  private HashMap<String, Node> nodeMap = new HashMap<String, Node>();

  public PathFinder() {
    LocationDaoImpl e = DaoSingleton.getLocationDao();
    ArrayList<Location> locList = e.getAllNodes();
    nodeMap = locsToNodes(locList);
    blockMaintenance();
  }

  public List<Location> findPath(Location loc1, Location loc2) {
    // TODO: Account for missing nodes / disconnected locations
    AStar aStar = new AStar(nodeMap.get(loc1.get_nodeID()), nodeMap.get(loc2.get_nodeID()), this);
    List<Node> nodePath = aStar.findPath();
    List<Location> locationsPath = new ArrayList<>();

    LocationQuery locationQuery = new LocationQuery();
    for (Node node : nodePath) {
      locationsPath.add(locationQuery.findNodeByID(node.getNodeID()));
    }
    // can return List<Location>
    return locationsPath;
  }

  private static HashMap<String, Node> locsToNodes(List<Location> locationList) {
    HashMap<String, Node> nodeMap = new HashMap<String, Node>(); // Location_NodeID --> Node
    for (Location loc : locationList) {
      Node node =
          new Node(
              (int) Double.parseDouble(loc.get_xcoord()),
              (int) Double.parseDouble(loc.get_ycoord()),
              loc.get_zcoord(),
              loc.get_nodeID());
      nodeMap.put(loc.get_nodeID(), node);
    }
    // TODO: Add parent nodes by parsing towerEdges
    // TODO: Add an Edge class containing two locations and an Edge database and an EdgeQuery
    // TODO: class... restructure.

    // Reads the CSV to add edges to the nodes
    URL resource = PathFinder.class.getClassLoader().getResource("TowerEdges.csv");
    File fileIn;
    if (resource != null) fileIn = new File(resource.toString());
    else fileIn = new File("TowerEdges.csv");
    try {
      Scanner sc = new Scanner(fileIn);
      sc.useDelimiter(",|\\r"); // set to comma-delimited
      sc.nextLine(); // skip the header line

      LocationQuery locationQuery = new LocationQuery();
      while (sc.hasNextLine()) {
        String edgeID = sc.next();
        String startLocationID = sc.next();
        String endLocationID = sc.next();

        //        System.out.println(startLocationID);
        //        System.out.println(endLocationID);

        //        Location startLoc = locationQuery.findNodeByID(startLocationID);
        //        Location endLoc = locationQuery.findNodeByID(endLocationID);
        //        System.out.println(nodeMap.get(startLocationID).toString());
        //        System.out.println(nodeMap.get(endLocationID).toString());
        try {
          nodeMap.get(startLocationID).addAdjacent(endLocationID);
          nodeMap.get(endLocationID).addAdjacent(startLocationID);
        } catch (NullPointerException e) {
          System.out.println("Bad row in edge CSV.");
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not here!");
      e.printStackTrace();
    } catch (NoSuchElementException e) {
      // end of csv reached, continue
    }

    return nodeMap;
  }

  /** Blocks nodes with live maintenance requests */
  private void blockMaintenance() {
    MaintenanceRequestDaoImpl e = DaoSingleton.getMaintenanceRequestDao();
    ArrayList<MaintenanceRequest> allMaintenanceRequests = e.getAllNodes();
    for (MaintenanceRequest maintenanceRequest : allMaintenanceRequests) {
      String maintenanceLocationID = maintenanceRequest.get_locationID();
      nodeMap.get(maintenanceLocationID).setBlock(true);
    }
  }

  /** Blocks stairs for disabled guests */
  public void setDisabilityFriendly(boolean enabled) {
    nodeMap.forEach(
        (key, value) -> {
          if (key.toUpperCase().contains("STAI")) {
            value.setBlock(enabled);
          }
        });
  }

  public Node getNodeByID(String nodeID) {
    return nodeMap.get(nodeID);
  }

  public void refreshNodeMap() {
    LocationDaoImpl e = DaoSingleton.getLocationDao();
    ArrayList<Location> locList = e.getAllNodes();
    nodeMap = locsToNodes(locList);
    blockMaintenance();
  }
}
