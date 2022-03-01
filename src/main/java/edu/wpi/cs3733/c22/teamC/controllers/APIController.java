package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamB.ServiceRequestAPI.BServiceRequestAPI;
import edu.wpi.cs3733.c22.teamC.GiftServiceRequest;
import edu.wpi.cs3733.c22.teamD.Main;
import javafx.fxml.FXML;

public class APIController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton TeamBAPI;
  @FXML private JFXButton TeamCAPI;
  @FXML private JFXButton TeamDAPI;

  @FXML
  private void initialize() {}

  @FXML
  private void startTeamBAPI() {
    runBAPI();
  }

  @FXML
  private void startTeamCAPI() {
    runCAPI();
  }

  @FXML
  private void startTeamDAPI() {
    runDAPI();
  }

  public void runBAPI() {
    BServiceRequestAPI computerAPI = BServiceRequestAPI.getInstance();
    try {
      // Platform.setImplicitExit(false);
      computerAPI.run(0, 0, 1900, 1000, null, null, null);
    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }

  public void runCAPI() {
    GiftServiceRequest giftAPI = new GiftServiceRequest();
    try {
      // Platform.setImplicitExit(false);
      giftAPI.run(0, 0, 1900, 1000, null, null, null);

    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }

  public void runDAPI() {

    Main securityAPI = new Main();
    try {
      // Platform.setImplicitExit(false);
      securityAPI.run(0, 0, 1900, 1000, null, null, null);

    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }
}
