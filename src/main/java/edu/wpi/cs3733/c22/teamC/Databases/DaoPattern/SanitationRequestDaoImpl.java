package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.LanguageRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SanitationRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SanitationRequestQuery;

public class SanitationRequestDaoImpl extends DaoInterface<SanitationRequest> {

    public SanitationRequestDaoImpl() {
        nodeQuery = new SanitationRequestQuery();
        nodes = nodeQuery.getAllNodeData();
    }
}