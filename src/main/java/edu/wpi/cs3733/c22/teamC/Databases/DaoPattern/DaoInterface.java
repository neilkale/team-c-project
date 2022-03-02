package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.util.ArrayList;

public abstract class DaoInterface<T> {
  protected ArrayList<T> nodes;
  protected Query<T> nodeQuery;

  public ArrayList<T> getAllNodes() {
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
      System.out.println("Username1: " + ((DatabaseInterface) nodes.get(i)).getUID());
      System.out.println("Username2: " + ((DatabaseInterface) node).getUID());
      if (((DatabaseInterface) (nodes.get(i)))
          .getUID()
          .equals(((DatabaseInterface) node).getUID())) {
        index = i;
        found = true;
        System.out.println("Boolean found: " + found);
      }
    }

    if (found) {
      try {
        System.out.println("ENTERED FOUND");
        nodeQuery.editNode(node); // updating Employee fields in EMBEDDED
        // updating Employee fields in OBJECT LIST
        T node_update = nodes.get(index);
        ((DatabaseInterface) node_update).setValues(((DatabaseInterface) node).getValues());
        System.out.println("Node Updated : " + node_update);
      } catch (Exception e) {
        e.printStackTrace();
      }
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
