package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class SlideExitMenuController extends AbstractController {
  @FXML private JFXButton logoutButton;

  @FXML
  private void logoutButtonPressed() throws IOException {
    controllerMediator.resetDefaultControllerList();
    setNewScene("SignInPage.fxml", logoutButton);
  }

  @FXML
  private void logoutExitButtonPressed() throws IOException {
    setNewScene("SignInPage.fxml", logoutButton);
    Platform.exit();
    System.exit(0);
  }
}
