package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.fxml.FXML;

public class SlideInfoMenuController extends AbstractController {
  @FXML private JFXButton logoutButton;

  @FXML
  private void aboutButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("AboutPage.fxml");
  }

  @FXML
  private void covidButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("CovidPage.fxml");
  }

  @FXML
  private void helpButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("HelpPage.fxml");
  }

  @FXML
  private void creditsButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("CreditsPage.fxml");
  }
}
