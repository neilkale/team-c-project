package edu.wpi.cs3733.c22.teamC.controllers.sideView;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MedicalEquipmentQuery;
import edu.wpi.cs3733.c22.teamC.controllers.mapEditor.MapState;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SideViewDeselectedController {
  MedicalEquipmentQuery medicalEquipmentQuery = new MedicalEquipmentQuery();
  LocationQuery locationQuery = new LocationQuery();

  @FXML Label cleanLabel;
  @FXML Label dirtyLabel;
  @FXML Label floorLabel;

  private ArrayList<MedicalEquipment> equipment;
  private ArrayList<Location> locations;

  @FXML
  void initialize() {

    switch (MapState.getCurrentFloor()) {
      case "L2":
        floorLabel.setText("  Lower Level 2  ");
        break;
      case "L1":
        floorLabel.setText("  Lower Level 1  ");
        break;
      case "1":
        floorLabel.setText("Floor 1");
        break;
      case "2":
        floorLabel.setText("Floor 2");
        break;
      case "3":
        floorLabel.setText("Floor 3");
        break;
    }
    cleanLabel.setText(
        Integer.toString(EquipmentCountTracker.getCleanEquipCount(MapState.getCurrentFloor())));
    dirtyLabel.setText(
        Integer.toString(EquipmentCountTracker.getDirtyEquipCount(MapState.getCurrentFloor())));
  }
}
