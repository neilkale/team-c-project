package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.requests.LanguageRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicineRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LanguageRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MedicineRequestQuery;

public class MedicineRequestDaoImpl extends DaoInterface<MedicineRequest> {

    public MedicineRequestDaoImpl() {
        nodeQuery = new MedicineRequestQuery();
        nodes = nodeQuery.getAllNodeData();
    }
}