package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.LanguageRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SecurityRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SecurityRequestQuery;

public class SecurityRequestDaoImpl extends DaoInterface<SecurityRequest> {

    public SecurityRequestDaoImpl() {
        nodeQuery = new SecurityRequestQuery();
        nodes = nodeQuery.getAllNodeData();
    }
}