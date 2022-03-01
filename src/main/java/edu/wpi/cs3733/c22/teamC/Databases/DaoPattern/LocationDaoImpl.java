package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;

import java.util.List;

public class LocationDaoImpl implements DaoInterface<Location>{
    private List<Location> nodes;
    private LocationQuery nodeQuery;

    public LocationDaoImpl(){
        nodeQuery = new LocationQuery();
        nodes = nodeQuery.getAllNodeData();
    }

    @Override
    public List<Location> getAllNodes() {
        return nodes;
    }

    @Override
    public Location getNode(String uniqueID) {
        refreshNodeData();
        boolean done = false;
        int i;
        for(i =0; i < nodes.size() && !done; i++){
            if(nodes.get(i).get_nodeID().equalsIgnoreCase(uniqueID))
                done = true;
        }
        return nodes.get(i);
    }




    @Override
    public void updateNode(Location node) {
        refreshNodeData();

        int index = -1;
        boolean found = false;
        for(int i = 0; i < nodes.size() && found == false; i++){
            if(nodes.get(i).get_nodeID().equals(node.get_nodeID())){
                index = i;
                found = true;
            }
        }

        try{
            if(index  == -1)
            {
            nodeQuery.editNode(node); // updating Employee fields in EMBEDDED
            // updating Employee fields in OBJECT LIST
            Location node_update = nodes.get(index);
//            node_update.setValues(node.getValues());

//            employee_update.set_access(employee.get_access());
//            employee_update.set_id(employee.get_id());
//            employee_update.set_profilePicture(employee.get_profilePicture());
         }
        } catch(Exception e){
            e.printStackTrace();
        }

       // System.out.println("Employee: Index " + employees.indexOf(employee) + ", updated in the database");
    }

    @Override
    public void deleteNode(Location node) {
        refreshNodeData();
    }

    @Override
    public void addNode(Location node) {
        refreshNodeData();


    }


    @Override
    public void refreshNodeData() {
        nodes = nodeQuery.getAllNodeData();
    }
}
