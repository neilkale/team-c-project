package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.SQLMethods.MedicalEquipmentQuery;

public class EquipmentDaoImpl extends DaoInterface {

  public EquipmentDaoImpl() {
    nodeQuery = new MedicalEquipmentQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
