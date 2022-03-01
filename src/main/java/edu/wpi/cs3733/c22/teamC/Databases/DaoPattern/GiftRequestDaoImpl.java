package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.GiftRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ITRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.GiftRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.ITRequestQuery;

public class GiftRequestDaoImpl extends DaoInterface<GiftRequest> {

    public GiftRequestDaoImpl() {
        nodeQuery = new GiftRequestQuery();
        nodes = nodeQuery.getAllNodeData();
    }
}