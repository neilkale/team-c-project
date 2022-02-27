package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class DashboardController {
  @FXML ProgressIndicator progressCircle;
  @FXML Label notificationLabel;

  public void initialize() {
    notificationLabel.setText(
        "You have ("
            + ServiceRequest.getAllServiceRequests("Jared Chan").size()
            + ") pending requests");

    float percentOfRequestsComplete =
        (ServiceRequest.getTotalAndComplete()[0] == 0
            ? ServiceRequest.getTotalAndComplete()[0]
            : (float) ServiceRequest.getTotalAndComplete()[1]
                / (float) ServiceRequest.getTotalAndComplete()[0]);

    progressCircle.setProgress(percentOfRequestsComplete);
  }
}
