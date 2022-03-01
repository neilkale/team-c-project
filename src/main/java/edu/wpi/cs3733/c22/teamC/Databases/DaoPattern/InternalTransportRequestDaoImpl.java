package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.InternalTransportRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.InternalTransportRequestQuery;

public class InternalTransportRequestDaoImpl extends DaoInterface<InternalTransportRequest> {

  public InternalTransportRequestDaoImpl() {
    nodeQuery = new InternalTransportRequestQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
