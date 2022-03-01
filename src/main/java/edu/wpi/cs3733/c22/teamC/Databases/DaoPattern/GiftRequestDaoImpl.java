package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.GiftRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.GiftRequestQuery;

public class GiftRequestDaoImpl extends DaoInterface<GiftRequest> {

  public GiftRequestDaoImpl() {
    nodeQuery = new GiftRequestQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
