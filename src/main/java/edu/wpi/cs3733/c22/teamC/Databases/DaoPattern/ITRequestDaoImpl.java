package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ITRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.InternalTransportRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.ITRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.InternalTransportRequestQuery;

public class ITRequestDaoImpl extends DaoInterface<ITRequest>{

    public ITRequestDaoImpl(){
        nodeQuery = new ITRequestQuery();
        nodes = nodeQuery.getAllNodeData();
    }

}