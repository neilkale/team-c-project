package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import java.util.List;

public class LocationDaoImpl extends DaoInterface<Location> {
  private List<Location> nodes;
  private LocationQuery nodeQuery;

  public LocationDaoImpl() {
    nodeQuery = new LocationQuery();
    nodes = nodeQuery.getAllNodeData();
  }

}
