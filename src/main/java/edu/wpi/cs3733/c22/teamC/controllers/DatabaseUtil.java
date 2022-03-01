package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import java.util.*;
import javafx.fxml.FXML;

public final class DatabaseUtil {
  private DatabaseUtil() {}

  @FXML
  public static void getLongNames(JFXComboBox comboBox, String... nodeTypesGiven) {
    ArrayList<String> nodeList = new ArrayList<>(Arrays.asList(nodeTypesGiven));
    LocationQuery locationQuery = new LocationQuery();

    List<Location> locations = locationQuery.getAllNodeData();
    ArrayList<String> nodeTypes = new ArrayList<>();
    nodeTypes.addAll(nodeList);

    for (Location l : locations) {
      for (String type : nodeTypes) {
        if (l.get_nodeType().equals(type)) {
          comboBox.getItems().add(l.get_longName());
        }
      }
    }
  }
}
