package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.InternalTransportRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.InternalTransportRequestQuery;
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

public class InternalTransportController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXComboBox pickUpLocationComboBox;
  @FXML private JFXComboBox dropOffLocationComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXComboBox urgencyComboBox;
  @FXML private ImageView imageView;

  LocationQuery locationQuery = new LocationQuery();
  List<Location> locations;
  EmployeeQuery employeeQuery = new EmployeeQuery();
  List<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  private void initialize() {
    locations = locationQuery.getAllNodeData();
    employees = employeeQuery.getAllNodeData();

    imageView.setImage(ImageLoader.loadImage("Transport"));

    DatabaseUtil.getLongNames(pickUpLocationComboBox, "PATI", "ELEV", "HALL");
    DatabaseUtil.getLongNames(dropOffLocationComboBox, "PATI", "ELEV", "HALL");

    for (Employee e : employees) {
      if (e.get_service_type().toLowerCase().equals("transportation")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }

    urgencyComboBox.getItems().addAll("Urgent", "Not Urgent");
    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");

    ControllerUtil.addAutoCompleteListener(pickUpLocationComboBox);
    ControllerUtil.addAutoCompleteListener(dropOffLocationComboBox);
    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
  }

  @FXML
  private void submitButtonPressed() throws IOException {
    if (comboBoxesFilled()) {

      String locationID = "";
      String chosenLocation = (String) pickUpLocationComboBox.valueProperty().get();

      for (Location l : locations) {
        if (l.get_longName().equals(chosenLocation)) {
          locationID = l.get_nodeID();
        }
      }

      InternalTransportRequest request =
          new InternalTransportRequest(
              ServiceRequest.getNewestID(),
              locationID,
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "Internal Transport",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              LocationQuery.longToNodeID(
                  dropOffLocationComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              urgencyComboBox.getSelectionModel().selectedItemProperty().getValue().toString());

      System.out.println(request.toString());
      InternalTransportRequestQuery InternalTransportReqQuery = new InternalTransportRequestQuery();
      InternalTransportReqQuery.addNode(request);

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);
    }
  }

  @FXML
  private void clearButtonPressed() throws IOException {
    statusComboBox.valueProperty().set(null);
    assignmentComboBox.valueProperty().set(null);
    pickUpLocationComboBox.valueProperty().set(null);
    dropOffLocationComboBox.valueProperty().set(null);
    urgencyComboBox.valueProperty().set(null);
  }
  /**
   * Returns a boolean value for if the combo boxes have selected values in the
   * SanitationServController
   *
   * @return boolean value for if all the combo boxes have selections that are
   */
  private boolean comboBoxesFilled() {
    ArrayList<Node> nodes = new ArrayList<>();
    if (statusComboBox.getSelectionModel().isEmpty()) {
      nodes.add(statusComboBox);
    }
    if (assignmentComboBox.getSelectionModel().isEmpty()) {
      nodes.add(assignmentComboBox);
    }
    if (pickUpLocationComboBox.getSelectionModel().isEmpty()) {
      nodes.add(pickUpLocationComboBox);
    }
    if (dropOffLocationComboBox.getSelectionModel().isEmpty()) {
      nodes.add(dropOffLocationComboBox);
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
