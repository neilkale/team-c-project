package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.LanguageRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;

public class LanguageRequestDaoImpl extends DaoInterface<LanguageRequest> {

  public LanguageRequestDaoImpl() {
    nodeQuery = new LanguageRequestQuery();
    nodes = nodeQuery.getAllNodeData();
  }
}
