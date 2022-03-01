package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ITRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.ITRequestQuery;
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

public class ITReqController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXComboBox issueComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXComboBox locationComboBox;
  @FXML ImageView imageView;

  LocationQuery locationQuery = new LocationQuery();
  List<Location> locations;

  EmployeeQuery employeeQuery = new EmployeeQuery();
  List<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  private void initialize() {
    locations = locationQuery.getAllNodeData();
    employees = employeeQuery.getAllNodeData();

    imageView.setImage(ImageLoader.loadImage("IT"));

    DatabaseUtil.getLongNames(locationComboBox, "PATI", "ELEV", "HALL");

    issueComboBox.getItems().addAll("Internet Down", "Device Crash", "Electrical Fire");

    for (Employee e : employees) {
      if (e.get_Service_Type().toLowerCase().equals("it-agent")) {
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
  private void submitButtonPressed() throws IOException {
    if (comboBoxesFilled()) {

      String locationID = "";
      String chosenLocation = (String) locationComboBox.valueProperty().get();

      for (Location l : locations) {
        if (l.get_longName().equals(chosenLocation)) {
          locationID = l.get_nodeID();
        }
      }

      ITRequest request =
          new ITRequest(
              ServiceRequest.getNewestID(),
              LocationQuery.longToNodeID(locationComboBox.getValue().toString()),
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "IT",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              issueComboBox.getSelectionModel().selectedItemProperty().getValue().toString());

      System.out.println(request.toString());
      ITRequestQuery reqQuery = new ITRequestQuery();
      reqQuery.addNode(request);

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);

    } else {
      ControllerUtil.popUpMessage("Error with fields", "Not enough fields filled out");
    }
  }

  @FXML
  private void clearButtonPressed() throws IOException {
    issueComboBox.valueProperty().set(null);
    locationComboBox.valueProperty().set(null);
  }

  /**
   * Returns a boolean value for if the combo boxes have selected values in the ITReqController
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
