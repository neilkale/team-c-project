package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicineRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MedicineRequestQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerUtil;
import edu.wpi.cs3733.c22.teamC.controllers.DatabaseUtil;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MedicineDeliveryController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton clearButton;
  @FXML private JFXComboBox medicineComboBox;
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox quantityComboBox;
  @FXML private JFXComboBox urgencyComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private ImageView imageView;

  // ImageLoader imageLoader = new ImageLoader();
  private LocationQuery locationQuery = new LocationQuery();
  List<Location> locations;

  EmployeeQuery employeeQuery = new EmployeeQuery();
  List<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  private void initialize() {

    // langIcon.setImage(imageLoader.loadImage("Location"));
    locations = locationQuery.getAllNodeData();
    employees = employeeQuery.getAllNodeData();

    imageView.setImage(ImageLoader.loadImage("Meds"));

    DatabaseUtil.getLongNames(locationComboBox, "PATI");

    medicineComboBox.getItems().addAll("drug1", "drug2", "drug3", "drug4");
    for (Employee e : employees) {
      if (e.get_service_type().toLowerCase().equals("nurse")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }
    urgencyComboBox.getItems().addAll("Urgent", "Not Urgent");
    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");
    quantityComboBox.getItems().addAll("1", "100", "1000", "10k");

    ControllerUtil.addAutoCompleteListener(medicineComboBox);
    ControllerUtil.addAutoCompleteListener(locationComboBox);
    ControllerUtil.addAutoCompleteListener(quantityComboBox);
    ControllerUtil.addAutoCompleteListener(urgencyComboBox);
    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
    ControllerUtil.addAutoCompleteListener(statusComboBox);
  }

  @FXML
  private void confirmButtonPressed() throws IOException {
    if (comboBoxesFilled()) {

      MedicineRequest request =
          new MedicineRequest(
              ServiceRequest.getNewestID(),
              LocationQuery.longToNodeID(locationComboBox.getValue().toString()),
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "Medicine",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              (String) medicineComboBox.valueProperty().get().toString(),
              quantityComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              urgencyComboBox.getSelectionModel().selectedItemProperty().getValue().toString());

      System.out.println(request.toString());

      MedicineRequestQuery medQuery = new MedicineRequestQuery();

      try {
        medQuery.addNode(request);
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
    medicineComboBox.valueProperty().set(null);
    assignmentComboBox.valueProperty().set(null);
    statusComboBox.valueProperty().set(null);
    quantityComboBox.valueProperty().set(null);
    urgencyComboBox.valueProperty().set(null);
  }

  /**
   * Returns a boolean value for if the combo boxes have selected values in the
   * MedicineDeliveryController Class
   *
   * @return boolean value for if all the combo boxes have selections that are
   */
  private boolean comboBoxesFilled() {
    if (locationComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    if (medicineComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    if (assignmentComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    if (statusComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    if (quantityComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    if (urgencyComboBox.getSelectionModel().isEmpty()) {
      return false;
    }
    return true;
  }
}
