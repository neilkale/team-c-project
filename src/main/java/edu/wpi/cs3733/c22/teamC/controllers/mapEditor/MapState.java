package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

public class MapState {
  private static double zoomFactor = 1;
  private static String currentFloor = "L2";
  private static int indexSelected = -1;

  // Bools for the filter
  private static boolean equipmentShown = true;
  private static boolean servicesShown = true;
  private static boolean locationsShown = true;

  private static int startIndex = -1;
  private static int stopIndex = -1;

  public static int getStartIndex() {
    return startIndex;
  }

  public static void setStartIndex(int startIndex) {
    MapState.startIndex = startIndex;
  }

  public static int getStopIndex() {
    return stopIndex;
  }

  public static void setStopIndex(int stopIndex) {
    MapState.stopIndex = stopIndex;
  }

  public static int getIndexSelected() {
    return indexSelected;
  }

  public static void setIndexSelected(int indexSelected) {
    MapState.indexSelected = indexSelected;
  }

  public static void setZoomFactor(double zoomFactor) {
    MapState.zoomFactor = zoomFactor;
  }

  public static void setCurrentFloor(String currentFloor) {
    MapState.currentFloor = currentFloor;
  }

  public static void setEquipmentShown(boolean equipmentShown) {
    MapState.equipmentShown = equipmentShown;
  }

  public static void setServicesShown(boolean servicesShown) {
    MapState.servicesShown = servicesShown;
  }

  public static void setLocationsShown(boolean locationsShown) {
    MapState.locationsShown = locationsShown;
  }

  public static double getZoomFactor() {
    return zoomFactor;
  }

  public static String getCurrentFloor() {
    return currentFloor;
  }

  public static boolean isEquipmentShown() {
    return equipmentShown;
  }

  public static boolean isServicesShown() {
    return servicesShown;
  }

  public static boolean isLocationsShown() {
    return locationsShown;
  }

  public static void toggleServicesShown() {
    servicesShown = !servicesShown;
  }

  public static void toggleLocationsShown() {
    locationsShown = !locationsShown;
  }

  public static void toggleEquipmentShown() {
    equipmentShown = !equipmentShown;
  }
}
