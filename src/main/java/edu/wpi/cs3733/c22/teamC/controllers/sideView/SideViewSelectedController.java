package edu.wpi.cs3733.c22.teamC.controllers.sideView;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MedicalEquipmentQuery;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import edu.wpi.cs3733.c22.teamC.controllers.mapEditor.MapState;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SideViewSelectedController {
  MedicalEquipmentQuery medicalEquipmentQuery = new MedicalEquipmentQuery();
  LocationQuery locationQuery = new LocationQuery();

  @FXML Label cleanXrayLabel;
  @FXML Label cleanPumpLabel;
  @FXML Label cleanBedLabel;
  @FXML Label cleanReclinerLabel;

  @FXML Label dirtyXrayLabel;
  @FXML Label dirtyPumpLabel;
  @FXML Label dirtyBedLabel;
  @FXML Label dirtyReclinerLabel;

  @FXML ImageView cleanPumpIcon;
  @FXML ImageView cleanXrayIcon;
  @FXML ImageView cleanReclinerIcon;
  @FXML ImageView cleanBedIcon;

  @FXML ImageView dirtyPumpIcon;
  @FXML ImageView dirtyXrayIcon;
  @FXML ImageView dirtyReclinerIcon;
  @FXML ImageView dirtyBedIcon;

  @FXML Label floorLabel;

  private ArrayList<MedicalEquipment> equipment;
  private ArrayList<Location> locations;

  @FXML
  void initialize() {
    cleanReclinerIcon.setImage(ImageLoader.loadImage("RECL"));
    cleanBedIcon.setImage(ImageLoader.loadImage("BEDS"));
    cleanXrayIcon.setImage(ImageLoader.loadImage("XRAY"));
    cleanPumpIcon.setImage(ImageLoader.loadImage("PUMP"));

    dirtyReclinerIcon.setImage(ImageLoader.loadImage("RECLDIRTY"));
    dirtyBedIcon.setImage(ImageLoader.loadImage("BEDSDIRTY"));
    dirtyXrayIcon.setImage(ImageLoader.loadImage("XRAYDIRTY"));
    dirtyPumpIcon.setImage(ImageLoader.loadImage("PUMPDIRTY"));

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
    Integer[] equipCount = EquipmentCountTracker.getEquipmentCount(MapState.getCurrentFloor());
    cleanXrayLabel.setText(Integer.toString(equipCount[0]));
    cleanPumpLabel.setText(Integer.toString(equipCount[1]));
    cleanBedLabel.setText(Integer.toString(equipCount[2]));
    cleanReclinerLabel.setText(Integer.toString(equipCount[3]));
    dirtyXrayLabel.setText(Integer.toString(equipCount[4]));
    dirtyPumpLabel.setText(Integer.toString(equipCount[5]));
    dirtyBedLabel.setText(Integer.toString(equipCount[6]));
    dirtyReclinerLabel.setText(Integer.toString(equipCount[7]));
  }
}
