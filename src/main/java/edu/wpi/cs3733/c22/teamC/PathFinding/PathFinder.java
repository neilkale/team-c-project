package edu.wpi.cs3733.c22.teamC.PathFinding;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.PathFinding.AStarImpl.AStar;
import edu.wpi.cs3733.c22.teamC.PathFinding.AStarImpl.Node;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PathFinder {

  private HashMap<String, Node> nodeMap = new HashMap<String, Node>();

  public PathFinder() {
    LocationQuery locationQuery = new LocationQuery();
    ArrayList<Location> locList = locationQuery.getAllNodeData();
    nodeMap = locsToNodes(locList);
  }

  public List<Location> findPath(Location loc1, Location loc2) {
    // TODO: Account for missing nodes / disconnected locations
    AStar aStar = new AStar(nodeMap.get(loc1.get_nodeID()), nodeMap.get(loc2.get_nodeID()));
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
              Integer.parseInt(loc.get_xcoord()),
              Integer.parseInt(loc.get_ycoord()),
              loc.get_nodeID());
      nodeMap.put(loc.get_nodeID(), node);
    }
    // TODO: Add parent nodes by parsing towerEdges
    // TODO: Add an Edge class containing two locations and an Edge database and an EdgeQuery
    // TODO: class... restructure.

    // Reads the CSV to add edges to the nodes
    File fileIn =
        new File("./src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/TowerEdges.csv");
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
        nodeMap.get(startLocationID).addAdjacent(nodeMap.get(endLocationID));
        nodeMap.get(endLocationID).addAdjacent(nodeMap.get(startLocationID));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchElementException | NullPointerException e) {
      // end of csv reached, continue
    }
    return nodeMap;
  }

  public void setBlock(String locationID, boolean isBlock) {
    nodeMap.get(locationID).setBlock(isBlock);
  }

  public Node getNodeByID(String nodeID) {
    return nodeMap.get(nodeID);
  }
}