package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

// THIS IS THE OBJECT FOR THE FEATURE TREE
public class MapTableObject {
  private String name;
  private String id = null;

  public MapTableObject(String name, String id) {
    this.name = name;
    this.id = id;
  }

  public MapTableObject(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String toString() {
    return id;
  }
}
