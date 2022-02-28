package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.LoggedInUser;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class DashboardController {
  @FXML ProgressIndicator progressCircle;
  @FXML Label notificationLabel;
  @FXML Label qtyLabel;
  @FXML Label percentLabel;

  private Employee currentUser = LoggedInUser.getCurrentUser();

  public void initialize() {
    notificationLabel.setText(
        "You have ("
            + ServiceRequest.getAllServiceRequests(
                    currentUser.get_firstName() + " " + currentUser.get_lastName())
                .size()
            + ") pending requests");

    int total = ServiceRequest.getTotalAndComplete()[0];
    int complete = ServiceRequest.getTotalAndComplete()[1];

    float percentOfRequestsComplete = (total == 0 ? total : (float) complete / (float) total);

    progressCircle.setProgress(percentOfRequestsComplete);
    qtyLabel.setText(complete + "/" + total);
    percentLabel.setText(Math.round(percentOfRequestsComplete * 100) + "%");
  }
}
