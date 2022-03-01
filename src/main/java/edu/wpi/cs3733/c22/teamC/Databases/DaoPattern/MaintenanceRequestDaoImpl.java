package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MaintenanceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MaintenanceRequestQuery;

public class MaintenanceRequestDaoImpl extends DaoInterface<MaintenanceRequest> {

    public MaintenanceRequestDaoImpl() {
        nodeQuery = new MaintenanceRequestQuery();
        nodes = nodeQuery.getAllNodeData();
    }
}