package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.LanguageRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.LaundryRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LaundryRequestQuery;

public class LaundryRequestDaoImpl extends DaoInterface<LaundryRequest> {

    public LaundryRequestDaoImpl() {
        nodeQuery = new LaundryRequestQuery();
        nodes = nodeQuery.getAllNodeData();
    }
}