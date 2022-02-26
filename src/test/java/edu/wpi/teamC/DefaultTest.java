/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.teamC;

import org.junit.jupiter.api.Test;

public class DefaultTest {

  @Test
  public void test() {}

  public void testLocationObject() {
    /* Location loc = new Location("a", "b", "c", "d", "e", "f", "g", "h");
    assertTrue(loc.get_building().equals("e"));*/
  }

  @Test
  public void testLocationToNode() {
    /*PathFinder pf = new PathFinder();
    LocationQuery locationQuery = new LocationQuery();
    List<Location> locationList = locationQuery.getAllNodeData();
    Location start = locationList.get(4);
    Location end = locationList.get(0);
    //    PathFinder.getInstance().setBlock("FHALL03301", true);
    List<Location> path = pf.findPath(start, end);

    if (path.isEmpty()) {
      System.out.println(
          "No path exists from " + start.get_longName() + " to " + end.get_longName());
    } else {
      System.out.println("Path from " + start.get_longName() + " to " + end.get_longName() + ":");
      for (Location location : path) {
        System.out.println(
            location.get_longName()
                + " "
                + location.get_nodeID()
                + " "
                + location.get_xcoord()
                + " "
                + location.get_ycoord()
                + " "
                + location.get_floor());
      }
    }

    start = locationList.get(3);
    end = locationList.get(0);
    //    PathFinder.getInstance().setBlock("FHALL03301", true);
    path = pf.findPath(start, end);

    if (path.isEmpty()) {
      System.out.println(
          "No path exists from " + start.get_longName() + " to " + end.get_longName());
    } else {
      System.out.println("Path from " + start.get_longName() + " to " + end.get_longName() + ":");
      for (Location location : path) {
        System.out.println(
            location.get_longName()
                + " "
                + location.get_nodeID()
                + " "
                + location.get_xcoord()
                + " "
                + location.get_ycoord()
                + " "
                + location.get_floor());
      }
    }*/
  }

  /*@Test
  public void testServiceRequestDatabase() throws SQLException {

    DatabaseConnection connection = new DatabaseConnection();
    ServiceRequestQuery allNodeValues = new ServiceRequestQuery(connection);
    List<ServiceRequest> serviceRequestList = allNodeValues.getAllServiceRequestData();

    ServiceRequest test = new ServiceRequest("a", "b", "c", "d", "e");

    int origLength = allNodeValues.getLength();

    try {
      allNodeValues.addNode(test);
    } catch (SQLException e) {
    }

    int finalLength = allNodeValues.getLength();

    assertTrue(origLength + 1 == finalLength);
  }*/
}
