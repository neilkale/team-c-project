package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.GoldenGandaberundas.Main;
import edu.wpi.cs3733.c22.teamB.ServiceRequestAPI.BServiceRequestAPI;
import edu.wpi.cs3733.c22.teamC.GiftServiceRequest;
import edu.wpi.fire_engine_foenix.Fapp;
import edu.wpi.teame.TeamESecurityServiceRequest;
import javafx.fxml.FXML;

public class APIController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton TeamBAPI;
  @FXML private JFXButton TeamCAPI;
  @FXML private JFXButton TeamDAPI;
  @FXML private JFXButton TeamEAPI;
  @FXML private JFXButton TeamFAPI;
  @FXML private JFXButton TeamGAPI;

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
  private void startTeamEAPI() {
    runEAPI();
  }

  @FXML
  private void startTeamFAPI() {
    runFAPI();
  }

  @FXML
  private void startTeamGAPI() {
    runGAPI();
  }

  public void runBAPI() {
    BServiceRequestAPI computerAPI = BServiceRequestAPI.getInstance();
    try {
      // Platform.setImplicitExit(false);
      computerAPI.run(0, 0, 800, 600, null, null, null);
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

  public void runEAPI() {
    TeamESecurityServiceRequest securityAPI = TeamESecurityServiceRequest.getInstance();
    try {
      // Platform.setImplicitExit(false);
      securityAPI.run(0, 0, 1900, 1000, null, null);

    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }

  public void runFAPI() {

    Fapp medicineAPI = new Fapp();
    try {
      // Platform.setImplicitExit(false);
      medicineAPI.run(0, 0, 1900, 1000, null, null, null);

    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }

  public void runGAPI() {

    Main workflowAPI = new Main();
    try {
      // Platform.setImplicitExit(false);
      workflowAPI.run(0, 0, 1900, 1000, null, 2);

    } catch (Exception e) {
      System.out.println("Failed to run API");
      e.printStackTrace();
    }
  }
}
