package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

public class MapNodeSelector {

  private MapNodeList list = new MapNodeList();

  public MapNodeSelector() {}

  void removeNewSelection() {
    if (list.nodes.size() == 0) return;

    // check if the selected index is a new point
    if (list.nodes.get(list.nodes.size() - 1).isNewPoint()) {
      list.nodes.remove(list.nodes.size() - 1);
      MapState.setIndexSelected(-1);
    }
  }

  // selects a node by nodeID
  void selectNode(String nodeID) {
    MapState.setIndexSelected(-1);
    for (int n = 0; n < list.nodes.size(); n++) {
      MapNode node = list.nodes.get(n);
      if (node.checkLocation(nodeID)) {
        node.setSelected(true);
        MapState.setIndexSelected(n);
      } else node.setSelected(false);
    }
  }

  // selects the nearest node to xCoord and yCoord (Note, the xCoord and yCoord must be inside the
  // radius of the node)
  void selectNearestNode(double xCoord, double yCoord) {

    int index = -1;

    if (list.nodes.size() == 0) { // Add a new node if no nodes exist
      MapNode n = new MapNode(xCoord, yCoord);
      n.setSelected(true);
      list.nodes.add(n);
      return;
    }

    removeNewSelection();

    MapNode node = list.nodes.get(0);
    double minDist = node.getDistance(xCoord, yCoord);

    for (int n = 0; n < list.nodes.size(); n++) {
      node = list.nodes.get(n);
      node.setSelected(false);

      double dist = node.getDistance(xCoord, yCoord);

      double maxDist = node.getRadius();

      if (dist <= maxDist && dist <= minDist && list.nodes.get(n).isVisible()) {
        index = n;
        minDist = dist;
      }
    }
    // make a new node
    if (index == -1) {
      MapNode n = new MapNode(xCoord, yCoord);
      n.setSelected(true);
      list.nodes.add(n);
      MapState.setIndexSelected(list.nodes.size() - 1);
    }
    // Select existing node
    else {
      list.nodes.get(index).setSelected(true);
      list.nodes.get(index).update();
      MapState.setIndexSelected(index);
    }
  }

  // Tries to move the selected equipment to another node (THIS USES SNAPPING)
  void moveEquipment(double xCoord, double yCoord) {

    int previousNodeIndex = MapState.getIndexSelected();

    if (previousNodeIndex == -1) {
      return;
    }

    int index = -1;
    MapNode node = list.nodes.get(0);

    double minDist = node.getDistance(xCoord, yCoord);

    for (int n = 0; n < list.nodes.size(); n++) {
      node = list.nodes.get(n);

      double dist = node.getDistance(xCoord, yCoord);

      double maxDist = node.getRadius() * 1.5;

      list.nodes.get(n).setSelected(false);

      if (dist <= maxDist && dist <= minDist && list.nodes.get(n).isVisible()) {
        index = n;
        minDist = dist;
      }
    }

    if (index == -1) { // Nothing clicked
      list.nodes.get(previousNodeIndex).playWiggleAnimation();
      MapState.setIndexSelected(-1);
    } else { // Location clicked
      list.nodes.get(index).addEquipment(list.nodes.get(previousNodeIndex).getEquipment());
      list.nodes.get(index).playEnlargeAnimation();
      list.nodes.get(previousNodeIndex).removeEquipment();
      MapState.setIndexSelected(index);
    }
  }

  // Moves the selected equipment to the node with the given ID
  void moveEquipment(String newLocationID) {

    int previousNodeIndex = MapState.getIndexSelected();

    if (previousNodeIndex == -1) {
      return;
    }

    int index = -1;

    for (int n = 0; n < list.nodes.size(); n++) {
      MapNode node = list.nodes.get(n);

      node.setSelected(false);

      if (node.checkLocation(newLocationID)) {
        index = n;
      }
    }

    if (index == -1) { // Nothing clicked
      MapState.setIndexSelected(-1);
      return;
    } else { // Location clicked
      MapState.setIndexSelected(index);
      list.nodes.get(index).addEquipment(list.nodes.get(previousNodeIndex).getEquipment());
      list.nodes.get(index).playEnlargeAnimation();
      list.nodes.get(previousNodeIndex).removeEquipment();
      list.nodes.get(index).setSelected(true);
    }
  }
}
