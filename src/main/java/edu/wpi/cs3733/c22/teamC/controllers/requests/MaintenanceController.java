package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MaintenanceRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MaintenanceRequestQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerUtil;
import edu.wpi.cs3733.c22.teamC.controllers.DatabaseUtil;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class MaintenanceController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXComboBox issueComboBox;
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML ImageView imageView;

  LocationQuery locationQuery = new LocationQuery();
  List<Location> locations;

  EmployeeQuery employeeQuery = new EmployeeQuery();
  List<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  private void initialize() {
    locations = DaoSingleton.getLocationDao().getAllNodes();
    employees = DaoSingleton.getEmployeeDao().getAllNodes();

    DatabaseUtil.getLongNames(locationComboBox, "PATI", "ELEV", "HALL");

    imageView.setImage(ImageLoader.loadImage("Maintenance"));

    issueComboBox.getItems().addAll("Lights", "Elevator", "Building damage", "HVAC");

    for (Employee e : employees) {
      if (e.get_service_type().toLowerCase().equals("maintenance")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }

    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");

    ControllerUtil.addAutoCompleteListener(issueComboBox);
    ControllerUtil.addAutoCompleteListener(locationComboBox);
    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
  }

  @FXML
  private void confirmButtonPressed() throws IOException, SQLException {
    if (comboBoxesFilled()) {

      String locationID = "";
      String chosenLocation = (String) locationComboBox.valueProperty().get();

      for (Location l : locations) {
        if (l.get_longName().equals(chosenLocation)) {
          locationID = l.get_nodeID();
          break;
        }
      }

      MaintenanceRequest request =
          new MaintenanceRequest(
              ServiceRequest.getNewestID(),
              locationID,
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "Issue Reported",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              issueComboBox.getSelectionModel().selectedItemProperty().getValue().toString());

      MaintenanceRequestQuery maintenenceQuery = new MaintenanceRequestQuery();
      maintenenceQuery.addNode(request);
      System.out.println(request.toString());

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);
    }
  }

  @FXML
  private void clearButtonPressed() throws IOException {
    locationComboBox.valueProperty().set(null);
    issueComboBox.valueProperty().set(null);
  }

  /**
   * Returns a boolean value for if the combo boxes have selected values in the
   * MaintenanceController
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
    if (issueComboBox.getSelectionModel().isEmpty()) {
      nodes.add(issueComboBox);
    }
    if (statusComboBox.getSelectionModel().isEmpty()) {
      nodes.add(statusComboBox);
    }
    if (!nodes.isEmpty()) {
      angryWiggle(nodes);
      return false;
    }
    return true;
  }
}
