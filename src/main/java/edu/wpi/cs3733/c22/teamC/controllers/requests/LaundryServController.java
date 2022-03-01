package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.*;
import edu.wpi.cs3733.c22.teamC.Databases.requests.LaundryRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.LaundryRequestQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerUtil;
import edu.wpi.cs3733.c22.teamC.controllers.DatabaseUtil;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class LaundryServController extends AbstractController {
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private ImageView imageView;

  private LocationQuery locationQuery = new LocationQuery();
  List<Location> locations;

  EmployeeQuery employeeQuery = new EmployeeQuery();
  List<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  private void initialize() {
    locations = locationQuery.getAllNodeData();
    employees = employeeQuery.getAllNodeData();

    imageView.setImage(ImageLoader.loadImage("Laundry"));

    DatabaseUtil.getLongNames(locationComboBox, "PATI");

    for (Employee e : employees) {
      if (e.get_service_type().toLowerCase().equals("laundry")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }

    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");

    ControllerUtil.addAutoCompleteListener(locationComboBox);
    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
  }

  @FXML
  void submitButtonPressed() throws IOException {
    if (comboBoxesFilled()) {

      String locationLong = "";
      String chosenLocation = (String) locationComboBox.valueProperty().get();

      for (Location l : locations) {
        if (l.get_longName().equals(chosenLocation)) {
          locationLong = l.get_nodeID();
        }
      }

      LaundryRequest request =
          new LaundryRequest(
              ServiceRequest.getNewestID(),
              LocationQuery.longToNodeID(locationComboBox.getValue().toString()),
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "Laundry",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()));

      System.out.println(request.toString());
      LaundryRequestQuery sanReqQuery = new LaundryRequestQuery();
      sanReqQuery.addNode(request);

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);

    } else {
      ControllerUtil.popUpMessage("Error with fields", "Not enough fields filled out");
    }
  }

  @FXML
  private void cancelButtonPressed() throws IOException {
    locationComboBox.valueProperty().set(null);
    assignmentComboBox.valueProperty().set(null);
    statusComboBox.valueProperty().set(null);
  }

  /**
   * Returns a boolean value for if the combo boxes have selected values in the
   * SanitationServController
   *
   * @return boolean value for if all the combo boxes have selections that are
   */
  private boolean comboBoxesFilled() {
    if (locationComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    if (statusComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    if (assignmentComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    return true;
  }
}
