package edu.wpi.cs3733.c22.teamC.PathFinding.AStarImpl;

import edu.wpi.cs3733.c22.teamC.PathFinding.PathFinder;
import java.util.*;

/**
 * A Star Algorithm
 *
 * @author Marcelo Surriabre
 * @version 2.1, 2017-02-23
 */
public class AStar {
  private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
  private static int DEFAULT_DIAGONAL_COST = 14;
  private int hvCost;
  private int diagonalCost;
  //  private Node[][] searchArea;
  private PriorityQueue<String> openList;
  private Set<String> closedSet;
  private Node initialNode;
  private Node finalNode;
  private PathFinder pf = new PathFinder();

  public AStar(Node initialNode, Node finalNode, int hvCost, int diagonalCost) {
    this.hvCost = hvCost;
    this.diagonalCost = diagonalCost;
    setInitialNode(initialNode);
    setFinalNode(finalNode);
    //    this.searchArea = new Node[rows][cols];
    this.openList =
        new PriorityQueue<String>(
            new Comparator<String>() {
              @Override
              public int compare(String node0, String node1) {
                return Integer.compare(pf.getNodeByID(node0).getF(), pf.getNodeByID(node1).getF());
              }
            });
    //    setNodes();
    this.closedSet = new HashSet<>();
  }

  public AStar(Node initialNode, Node finalNode) {
    this(initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
  }

  //  private void setNodes() {
  //    for (int i = 0; i < searchArea.length; i++) {
  //      for (int j = 0; j < searchArea[0].length; j++) {
  //        Node node = new Node(i, j);
  //        node.calculateHeuristic(getFinalNode());
  //        this.searchArea[i][j] = node;
  //      }
  //    }
  //  }

  //  public void setBlocks(int[][] blocksArray) {
  //    for (int i = 0; i < blocksArray.length; i++) {
  //      int row = blocksArray[i][0];
  //      int col = blocksArray[i][1];
  //      setBlock(row, col);
  //    }
  //  }

  public List<Node> findPath() {
    openList.add(initialNode.getNodeID());
    while (!isEmpty(openList)) {
      Node currentNode = pf.getNodeByID(openList.poll());

      closedSet.add(currentNode.getNodeID());
      if (isFinalNode(currentNode)) {
        return getPath(currentNode);
      } else {
        addAdjacentNodes(currentNode);
      }
    }
    closedSet.clear(); // so nodes can be reused

    return new ArrayList<Node>();
  }

  private List<Node> getPath(Node currentNode) {
    List<Node> path = new ArrayList<Node>();
    path.add(currentNode);
    Node parent;
    while ((parent = currentNode.getParent()) != null) {
      path.add(0, parent);
      currentNode = parent;
    }
    return path;
  }

  /**
   * Fetches nodes from list of adjacents
   *
   * @param currentNode the node whose adjacents are being found
   */
  private void addAdjacentNodes(Node currentNode) {
    //    addAdjacentUpperRow(currentNode);
    //    addAdjacentMiddleRow(currentNode);
    //    addAdjacentLowerRow(currentNode);
    for (Node adjacentNode : currentNode.getAdjacents()) {
      checkNode(currentNode, adjacentNode, Node.getDistance(currentNode, adjacentNode));
    }
  }

  //  private void addAdjacentLowerRow(Node currentNode) {
  //    int row = currentNode.getRow();
  //    int col = currentNode.getCol();
  //    int lowerRow = row + 1;
  //    if (lowerRow < getSearchArea().length) {
  //      if (col - 1 >= 0) {
  //        checkNode(
  //            currentNode,
  //            col - 1,
  //            lowerRow,
  //            getDiagonalCost()); // Comment this line if diagonal movements are not allowed
  //      }
  //      if (col + 1 < getSearchArea()[0].length) {
  //        checkNode(
  //            currentNode,
  //            col + 1,
  //            lowerRow,
  //            getDiagonalCost()); // Comment this line if diagonal movements are not allowed
  //      }
  //      checkNode(currentNode, col, lowerRow, getHvCost());
  //    }
  //  }
  //
  //  private void addAdjacentMiddleRow(Node currentNode) {
  //    int row = currentNode.getRow();
  //    int col = currentNode.getCol();
  //    int middleRow = row;
  //    if (col - 1 >= 0) {
  //      checkNode(currentNode, col - 1, middleRow, getHvCost());
  //    }
  //    if (col + 1 < getSearchArea()[0].length) {
  //      checkNode(currentNode, col + 1, middleRow, getHvCost());
  //    }
  //  }
  //
  //  private void addAdjacentUpperRow(Node currentNode) {
  //    int row = currentNode.getRow();
  //    int col = currentNode.getCol();
  //    int upperRow = row - 1;
  //    if (upperRow >= 0) {
  //      if (col - 1 >= 0) {
  //        checkNode(
  //            currentNode,
  //            col - 1,
  //            upperRow,
  //            getDiagonalCost()); // Comment this if diagonal movements are not allowed
  //      }
  //      if (col + 1 < getSearchArea()[0].length) {
  //        checkNode(
  //            currentNode,
  //            col + 1,
  //            upperRow,
  //            getDiagonalCost()); // Comment this if diagonal movements are not allowed
  //      }
  //      checkNode(currentNode, col, upperRow, getHvCost());
  //    }
  //  }

  //  private void checkNode(Node currentNode, int col, int row, int cost) {
  //    Node adjacentNode = getSearchArea()[row][col];
  //    if (/*!adjacentNode.isBlock() &&*/ !getClosedSet().contains(adjacentNode)) {
  //      if (!getOpenList().contains(adjacentNode)) {
  //        adjacentNode.setNodeData(currentNode, cost);
  //        getOpenList().add(adjacentNode);
  //      } else {
  //        boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
  //        if (changed) {
  //          // Remove and Add the changed node, so that the PriorityQueue can sort again its
  //          // contents with the modified "finalCost" value of the modified node
  //          getOpenList().remove(adjacentNode);
  //          getOpenList().add(adjacentNode);
  //        }
  //      }
  //    }
  //  }

  private void checkNode(Node currentNode, Node adjacentNode, int cost) {
    if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode.getNodeID())) {
      if (!getOpenList().contains(adjacentNode.getNodeID())) {
        adjacentNode.setNodeData(currentNode, cost);
        getOpenList().add(adjacentNode.getNodeID());
      } else {
        boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
        if (changed) {
          // Remove and Add the changed node, so that the PriorityQueue can sort again its
          // contents with the modified "finalCost" value of the modified node
          getOpenList().remove(adjacentNode.getNodeID());
          getOpenList().add(adjacentNode.getNodeID());
        }
      }
    }
  }

  private boolean isFinalNode(Node currentNode) {
    return currentNode.getNodeID().equals(finalNode.getNodeID());
  }

  private boolean isEmpty(PriorityQueue<String> openList) {
    return openList.size() == 0;
  }

  //  private void setBlock(int row, int col) {
  //    this.searchArea[row][col].setBlock(true);
  //  }

  public Node getInitialNode() {
    return initialNode;
  }

  public void setInitialNode(Node initialNode) {
    this.initialNode = initialNode;
  }

  public Node getFinalNode() {
    return finalNode;
  }

  public void setFinalNode(Node finalNode) {
    this.finalNode = finalNode;
  }

  //  public Node[][] getSearchArea() {
  //    return searchArea;
  //  }
  //
  //  public void setSearchArea(Node[][] searchArea) {
  //    this.searchArea = searchArea;
  //  }

  public PriorityQueue<String> getOpenList() {
    return openList;
  }

  public void setOpenList(PriorityQueue<String> openList) {
    this.openList = openList;
  }

  public Set<String> getClosedSet() {
    return closedSet;
  }

  public void setClosedSet(Set<String> closedSet) {
    this.closedSet = closedSet;
  }

  public int getHvCost() {
    return hvCost;
  }

  public void setHvCost(int hvCost) {
    this.hvCost = hvCost;
  }

  private int getDiagonalCost() {
    return diagonalCost;
  }

  private void setDiagonalCost(int diagonalCost) {
    this.diagonalCost = diagonalCost;
  }
}
