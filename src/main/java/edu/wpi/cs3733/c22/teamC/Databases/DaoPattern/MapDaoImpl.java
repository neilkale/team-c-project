package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.SQLMethods.MapQuery;

public class MapDaoImpl extends DaoInterface{
    public MapDaoImpl() {
        nodeQuery = new MapQuery();
        nodes = nodeQuery.getAllNodeData();
    }
}
