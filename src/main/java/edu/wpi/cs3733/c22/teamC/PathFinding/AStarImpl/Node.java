package edu.wpi.cs3733.c22.teamC.PathFinding.AStarImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Node Class
 *
 * @author Marcelo Surriabre
 * @version 2.0, 2018-02-23
 */
public class Node {

  private int g;
  private int f;
  private int h;
  private int row;
  private int col;
  private int height;
  private boolean isBlock;
  private Node parent;
  private List<String> adjacents;
  private String nodeID;

  public Node(int row, int col, int height, String nodeID) {
    super();
    this.row = row;
    this.col = col;
    this.height = height;
    this.nodeID = nodeID;
    this.isBlock = false;
    adjacents = new ArrayList<String>();
  }

  //  public void calculateHeuristic(Node finalNode) {
  //    this.h = Math.abs(finalNode.getRow() - getRow()) + Math.abs(finalNode.getCol() - getCol());
  //  }

  public void setNodeData(Node currentNode, int cost) {
    int gCost = currentNode.getG() + cost;
    setParent(currentNode);
    setG(gCost);
    calculateFinalCost();
  }

  public boolean checkBetterPath(Node currentNode, int cost) {
    int gCost = currentNode.getG() + cost;
    if (gCost < getG()) {
      setNodeData(currentNode, cost);
      return true;
    }
    return false;
  }

  private void calculateFinalCost() {
    int finalCost = getG() + getH();
    setF(finalCost);
  }

  @Override
  public boolean equals(Object arg0) {
    Node other = (Node) arg0;
    return this.getRow() == other.getRow() && this.getCol() == other.getCol();
  }

  @Override
  public String toString() {
    return "Node [row=" + row + ", col=" + col + "]";
  }

  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h = h;
  }

  public int getG() {
    return g;
  }

  public void setG(int g) {
    this.g = g;
  }

  public int getF() {
    return f;
  }

  public void setF(int f) {
    this.f = f;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public boolean isBlock() {
    return isBlock;
  }

  public void setBlock(boolean isBlock) {
    this.isBlock = isBlock;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public List<String> getAdjacents() {
    return adjacents;
  }

  public void addAdjacent(String nodeLocationID) {
    adjacents.add(nodeLocationID);
  }

  public static int getDistance(Node n1, Node n2) {
    return (int)
        Math.sqrt(
            Math.pow(n1.row - n2.row, 2)
                + Math.pow(n1.col - n2.col, 2)
                + Math.pow(n1.height - n2.height, 2));
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }
}
