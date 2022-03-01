package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;

public class LocationDaoImpl extends DaoInterface<Location> {
  public LocationDaoImpl() {
    nodeQuery = new LocationQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
