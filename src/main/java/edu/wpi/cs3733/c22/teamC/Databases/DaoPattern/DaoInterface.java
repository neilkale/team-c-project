package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.util.List;

public abstract class DaoInterface<T> {
  protected List<T> nodes;
  protected Query<T> nodeQuery;

  public List<T> getAllNodes() {
    return nodes;
  }

  public T getNode(String uniqueID) {
    refreshNodeData();
    boolean done = false;
    int i;
    for (i = 0; i < nodes.size() && !done; i++) {
      if (((DatabaseInterface) (nodes.get(i))).getUID().equalsIgnoreCase(uniqueID)) done = true;
    }
    return nodes.get(i);
  }

  public void updateNode(T node) {
    refreshNodeData();

    int index = -1;
    boolean found = false;
    for (int i = 0; i < nodes.size() && found == false; i++) {
      if (((DatabaseInterface) (nodes.get(i)))
          .getUID()
          .equals(((DatabaseInterface) node).getUID())) {
        index = i;
        found = true;
      }
    }

    try {
      if (index == -1) {
        nodeQuery.editNode(node); // updating Employee fields in EMBEDDED
        // updating Employee fields in OBJECT LIST
        T node_update = nodes.get(index);
        ((DatabaseInterface) node_update).setValues(((DatabaseInterface) node).getValues());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("Employee: Index " + nodes.indexOf(node) + ", updated in the database");
  }

  public void deleteNode(T node) {
    refreshNodeData();
    try {
      nodeQuery.removeNode(node); // removes employee from EMBEDDED
      nodes.remove(nodes.indexOf(node)); // removes employee from OBJECT LIST
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(
        "Node: " + node.toString() + " | Index:" + nodes.indexOf(node) + ", deleted from database");
  }

  public void addNode(T node) {
    refreshNodeData();
    try {
      nodeQuery.addNode(node); // adds employee to EMBEDDED
      nodes.add(node); // adds employee to OBJECT LIST
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Node " + node.toString() + " added to database");
  }

  public void refreshNodeData() {
    nodes = nodeQuery.getAllNodeData();
  }
}
