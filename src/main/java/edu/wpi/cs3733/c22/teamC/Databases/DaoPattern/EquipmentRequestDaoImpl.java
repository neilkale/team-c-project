package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.EquipmentRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.EquipmentRequestQuery;

public class EquipmentRequestDaoImpl extends DaoInterface<EquipmentRequest> {

  public EquipmentRequestDaoImpl() {
    nodeQuery = new EquipmentRequestQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
