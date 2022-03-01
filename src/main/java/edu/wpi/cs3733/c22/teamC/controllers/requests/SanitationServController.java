package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SanitationRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SanitationRequestQuery;
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

public class SanitationServController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton clearButton;
  @FXML private JFXComboBox messTypeComboBox;
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXCheckBox biohazardCheckBox;
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

    imageView.setImage(ImageLoader.loadImage("Sanitation"));

    DatabaseUtil.getLongNames(locationComboBox, "PATI", "ELEV", "HALL");

    messTypeComboBox.getItems().addAll("Spill", "Disinfect", "Room Cleaning");

    for (Employee e : employees) {
      if (e.get_service_type().toLowerCase().equals("sanitation")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }

    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");

    ControllerUtil.addAutoCompleteListener(messTypeComboBox);
    ControllerUtil.addAutoCompleteListener(locationComboBox);
    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
    ControllerUtil.addAutoCompleteListener(statusComboBox);
  }

  @FXML
  private void submitButtonPressed() throws IOException {
    if (comboBoxesFilled()) {

      String locationLong = "";
      String chosenLocation = (String) locationComboBox.valueProperty().get();

      for (Location l : locations) {
        if (l.get_longName().equals(chosenLocation)) {
          locationLong = l.get_nodeID();
        }
      }

      SanitationRequest request =
          new SanitationRequest(
              ServiceRequest.getNewestID(),
              LocationQuery.longToNodeID(locationComboBox.getValue().toString()),
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "Sanitation",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              messTypeComboBox.getSelectionModel().selectedItemProperty().getValue().toString());

      System.out.println(request.toString());
      SanitationRequestQuery sanReqQuery = new SanitationRequestQuery();
      sanReqQuery.addNode(request);

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);
    }
  }

  @FXML
  private void clearButtonPressed() throws IOException {
    locationComboBox.valueProperty().set(null);
    messTypeComboBox.valueProperty().set(null);
    statusComboBox.valueProperty().set(null);
    assignmentComboBox.valueProperty().set(null);
  }
  /**
   * Returns a boolean value for if the combo boxes have selected values in the
   * SanitationServController
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
    if (messTypeComboBox.getSelectionModel().isEmpty()) {
      nodes.add(messTypeComboBox);
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
