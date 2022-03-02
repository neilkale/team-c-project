package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class DashboardController extends AbstractController {
  @FXML FlowPane flowPane;
  private Pane profilePage;
  private Pane requestPage;
  private Pane sideViewPage;
  private Pane serviceRequestPage;

  @FXML
  void initialize() throws IOException {
    profilePage = (Pane) loadFxml("ProfilePage.fxml");
    requestPage = (Pane) loadFxml("RequestDashboard.fxml");
    sideViewPage = (Pane) loadFxml("SideViewDashboard.fxml");
    serviceRequestPage = (Pane) loadFxml("ServiceRequest.fxml");
    serviceRequestPage.setMaxHeight(400);
    serviceRequestPage.setMaxWidth(300);

    flowPane.getChildren().add(profilePage);
    flowPane.getChildren().add(requestPage);
    flowPane.getChildren().add(serviceRequestPage);
    flowPane.getChildren().add(sideViewPage);
  }
}
