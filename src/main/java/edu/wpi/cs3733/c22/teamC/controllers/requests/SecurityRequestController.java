package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SecurityRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SecurityRequestQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerUtil;
import edu.wpi.cs3733.c22.teamC.controllers.DatabaseUtil;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class SecurityRequestController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton clearButton;
  @FXML private JFXComboBox securityComboBox;
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXComboBox reportBreachComboBox;
  @FXML private JFXComboBox urgencyComboBox;
  @FXML ImageView imageView;

  private LocationQuery locationQuery = new LocationQuery();
  List<Location> locationList;

  EmployeeQuery employeeQuery = new EmployeeQuery();
  List<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  private void initialize() {

    locationList = locationQuery.getAllNodeData();
    employees = employeeQuery.getAllNodeData();

    imageView.setImage(ImageLoader.loadImage("Security"));

    DatabaseUtil.getLongNames(locationComboBox, "PATI", "ELEV", "HALL");

    securityComboBox
        .getItems()
        .addAll("Surveillance", "Security Guard", "Buddy System", "Security Support");
    for (Employee e : employees) {
      if (e.get_Service_Type().toLowerCase().equals("security")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }
    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");
    reportBreachComboBox.getItems().addAll("Breach", "No Breach");
    urgencyComboBox.getItems().addAll("Urgent", "Not Urgent");
    assignmentComboBox.getItems().addAll("SecurityMan");

    ControllerUtil.addAutoCompleteListener(securityComboBox);
    ControllerUtil.addAutoCompleteListener(locationComboBox);
    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
    ControllerUtil.addAutoCompleteListener(statusComboBox);
    ControllerUtil.addAutoCompleteListener(reportBreachComboBox);
    ControllerUtil.addAutoCompleteListener(urgencyComboBox);
  }

  @FXML
  private void confirmButtonPressed() throws IOException {
    if (comboBoxesFilled()) {

      String locationLong = "";
      String chosenLocation = (String) locationComboBox.valueProperty().get();

      for (Location l : locationList) {
        if (l.get_longName().equals(chosenLocation)) {
          locationLong = l.get_nodeID();
        }
      }

      SecurityRequest request =
          new SecurityRequest(
              ServiceRequest.getNewestID(),
              LocationQuery.longToNodeID(locationComboBox.getValue().toString()),
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "Security",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              reportBreachComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              securityComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              urgencyComboBox.getSelectionModel().selectedItemProperty().getValue().toString());

      System.out.println(request.toString());

      SecurityRequestQuery securityQuery = new SecurityRequestQuery();

      try {
        securityQuery.addNode(request);
      } catch (Exception e) {
        e.printStackTrace();
      }

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);

    } else {
      ControllerUtil.popUpMessage("Error with fields", "Not enough fields filled out");
    }
  }

  @FXML
  private void clearButtonPressed() {
    locationComboBox.valueProperty().set(null);
    securityComboBox.valueProperty().set(null);
    assignmentComboBox.valueProperty().set(null);
    statusComboBox.valueProperty().set(null);
    reportBreachComboBox.valueProperty().set(null);
    urgencyComboBox.valueProperty().set(null);
  }

  /**
   * Returns a boolean value for if the combo boxes have selected values in the
   * MedicineDeliveryController Class
   *
   * @return boolean value for if all the combo boxes have selections that are
   */
  private boolean comboBoxesFilled() {
    ArrayList<Node> nodes = new ArrayList<>();
    if (locationComboBox.getSelectionModel().isEmpty()) {
      nodes.add(locationComboBox);
    }
    if (assignmentComboBox.getSelectionModel().isEmpty()) {
      nodes.add(assignmentComboBox);
    }
    if (reportBreachComboBox.getSelectionModel().isEmpty()) {
      nodes.add(reportBreachComboBox);
    }
    if (statusComboBox.getSelectionModel().isEmpty()) {
      nodes.add(statusComboBox);
    }
    if (securityComboBox.getSelectionModel().isEmpty()) {
      nodes.add(securityComboBox);
    }
    if (urgencyComboBox.getSelectionModel().isEmpty()) {
      nodes.add(urgencyComboBox);
    }
    if (!nodes.isEmpty()) {
      angryWiggle(nodes);
      return false;
    }
    return true;
  }
}
