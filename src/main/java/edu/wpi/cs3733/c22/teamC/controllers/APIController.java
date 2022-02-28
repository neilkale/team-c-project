package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamB.ServiceRequestAPI.BServiceRequestAPI;
import edu.wpi.cs3733.c22.teamD.Main;
import javafx.fxml.FXML;

public class APIController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton TeamBAPI;
  @FXML private JFXButton TeamDAPI;

  @FXML
  private void initialize() {}

  @FXML
  private void startTeamBAPI() {
    BServiceRequestAPI computerAPI = BServiceRequestAPI.getInstance();
    try {
      computerAPI.run(0, 0, 1900, 1000, null, null, null);
    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }

  @FXML
  private void startTeamDAPI() {
    Main securityAPI = new Main();
    try {
      securityAPI.run(0, 0, 1900, 1000, null, null, null);

    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }
}
