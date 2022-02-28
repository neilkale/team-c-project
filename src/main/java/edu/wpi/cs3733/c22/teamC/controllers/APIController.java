package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class APIController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton TeamBAPI;
  @FXML private JFXButton TeamDAPI;

  @FXML
  private void initialize() {}

  @FXML
  private void startTeamBAPI() {
    ComputerSR computerAPI = new ComputerSR();
    try {
      computerAPI.run(0, 0, 1900, 1000, null, null, null);
    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }

  @FXML
  private void startTeamDAPI() {
    InternalTransportationRequest securityAPI = new InternalTransportationRequest();

    try {
      securityAPI.run(0, 0, 1900, 1000, null, null, null);

    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }
}
