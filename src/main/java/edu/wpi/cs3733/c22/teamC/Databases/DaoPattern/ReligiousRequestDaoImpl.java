package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ReligiousRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.ReligiousRequestQuery;

public class ReligiousRequestDaoImpl extends DaoInterface<ReligiousRequest> {

  public ReligiousRequestDaoImpl() {
    nodeQuery = new ReligiousRequestQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
