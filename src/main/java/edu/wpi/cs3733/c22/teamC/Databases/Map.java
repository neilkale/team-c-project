package edu.wpi.cs3733.c22.teamC.Databases;

public class Map implements DatabaseInterface {
  private String buildingName;
  private String floorName;
  private String imagePath;
  private String floorNumber;

  public String get_buildingName() {
    return buildingName;
  }

  public String get_floorName() {
    return floorName;
  }

  public String get_imagePath() {
    return imagePath;
  }

  public String get_floorNumber() {
    return floorNumber;
  }

  public void set_buildingName(String buildingName) {
    this.buildingName = buildingName;
  }

  public void set_floorName(String floorName) {
    this.floorName = floorName;
  }

  public void set_imagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public void set_floorNumber(String floorNumber) {
    this.floorNumber = floorNumber;
  }

  public Map(String buildingName, String floorName, String imagePath, String floorNumber) {
    this.buildingName = buildingName;
    this.floorName = floorName;
    this.imagePath = imagePath;
    this.floorNumber = floorNumber;
  }

  @Override
  public String[] getValues() {
    return new String[0];
  }

  @Override
  public String getName() {
    return null;
  }
}
