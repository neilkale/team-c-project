package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.LoggedInUser;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class RequestDashboardController extends AbstractController {
  @FXML private ProgressIndicator progressCircle;
  @FXML private Label notificationLabel;
  @FXML private Label qtyLabel;
  @FXML private Label percentLabel;

  private Employee currentUser = LoggedInUser.getCurrentUser();

  public void initialize() {
    notificationLabel.setText(
        "You have ("
            + ServiceRequest.getAllServiceRequests(
                    currentUser.get_firstName() + " " + currentUser.get_lastName())
                .size()
            + ") pending requests");

    // below are calculations and display of request statistics
    int total = ServiceRequest.getTotalAndComplete()[0];
    int complete = ServiceRequest.getTotalAndComplete()[1];

    float percentOfRequestsComplete = (total == 0 ? total : (float) complete / (float) total);

    progressCircle.setProgress(percentOfRequestsComplete);
    qtyLabel.setText(complete + "/" + total);
    percentLabel.setText(Math.round(percentOfRequestsComplete * 100) + "%");
  }

  @FXML
  private void trackOrdersButtonPressed() throws IOException {
    // get logged in user
    Employee currentUser = LoggedInUser.getCurrentUser();

    // get controller of the order tracker page about to be loaded
    FXMLLoader loader = getLoader("OrderTracker.fxml");
    Pane root = loader.load();
    controllerMediator.setOrderTrackerController(loader.getController());
    controllerMediator.setDefaultPageCenter(root, "OrderTracker.fxml");

    // preload filter in employee combobox in order tracker
    controllerMediator.setOrderTrackerControllerAssigned(
        currentUser.get_firstName() + " " + currentUser.get_lastName());
  }
}
