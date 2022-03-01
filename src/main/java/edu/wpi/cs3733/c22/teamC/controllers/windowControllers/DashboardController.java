package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.LoggedInUser;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class DashboardController extends AbstractController {
  @FXML private ProgressIndicator progressCircle;
  @FXML private Label notificationLabel;
  @FXML private Label qtyLabel;
  @FXML private Label percentLabel;
  @FXML private Label idLabel;
  @FXML private Label typeLabel;
  @FXML private TextField firstTextField;
  @FXML private TextField lastTextField;
  @FXML private TextField usernameTextField;
  @FXML private TextField passwordTextField;
  @FXML private PasswordField passwordField;
  @FXML private JFXButton saveButton;
  @FXML private JFXButton editButton;
  @FXML private CheckBox showPassCheckBox;

  private Employee currentUser = LoggedInUser.getCurrentUser();

  public void initialize() {
    notificationLabel.setText(
        "You have ("
            + ServiceRequest.getAllServiceRequests(
                    currentUser.get_firstName() + " " + currentUser.get_lastName())
                .size()
            + ") pending requests");

    setUserInfo();

    passwordField.setVisible(true);
    passwordTextField.setVisible(false);

    // listens for checkbox tick; if ticked, we show password
    showPassCheckBox
        .selectedProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if (showPassCheckBox.isSelected()) {
                passwordTextField.setText(passwordField.getText());
                passwordTextField.setVisible(true);
                passwordField.setVisible(false);
              } else {
                passwordTextField.setVisible(false);
                passwordField.setVisible(true);
              }
            });

    // below are calculations and display of request statistics
    int total = ServiceRequest.getTotalAndComplete()[0];
    int complete = ServiceRequest.getTotalAndComplete()[1];

    float percentOfRequestsComplete = (total == 0 ? total : (float) complete / (float) total);

    progressCircle.setProgress(percentOfRequestsComplete);
    qtyLabel.setText(complete + "/" + total);
    percentLabel.setText(Math.round(percentOfRequestsComplete * 100) + "%");
  }

  @FXML
  private void editButtonPressed() {
    if (editButton.getText().equalsIgnoreCase("edit")) {
      editButton.setText("Cancel");
      setEditable(true);
    } else {
      editButton.setText("Edit");
      setEditable(false);
      setUserInfo();
    }
  }

  @FXML
  private void saveButtonPressed() throws SQLException {
    setEditable(false);

    currentUser.set_firstName(firstTextField.getText());
    currentUser.set_lastName(lastTextField.getText());
    currentUser.set_username(usernameTextField.getText());

    if (passwordField.isVisible()) currentUser.set_password(passwordField.getText());
    else currentUser.set_password(passwordTextField.getText());

    (new EmployeeQuery()).editNode(currentUser);
    controllerMediator.setNameLabel(firstTextField.getText() + " " + lastTextField.getText());
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

  private void setUserInfo() {
    idLabel.setText("ID: " + currentUser.get_id());
    typeLabel.setText("Service Type: " + currentUser.get_serviceType());
    firstTextField.setText(currentUser.get_firstName());
    lastTextField.setText(currentUser.get_lastName());
    usernameTextField.setText(currentUser.get_username());
    passwordField.setText(currentUser.get_password());
  }

  private void setEditable(Boolean isEditable) {
    if (isEditable) {
      firstTextField.setEditable(true);
      lastTextField.setEditable(true);
      usernameTextField.setEditable(true);
      passwordField.setEditable(true);
    } else {
      firstTextField.setEditable(false);
      lastTextField.setEditable(false);
      usernameTextField.setEditable(false);
      passwordField.setEditable(false);
    }
  }
}
