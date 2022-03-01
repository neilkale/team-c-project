package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

import java.util.List;

public interface DaoInterface<T> {
  public List<T> getAllNodes();

  public T getNode(String uniqueID);

  public void updateNode(T node);

  public void deleteNode(T node);

  public void addNode(T node);

  public void refreshNodeData();
}
