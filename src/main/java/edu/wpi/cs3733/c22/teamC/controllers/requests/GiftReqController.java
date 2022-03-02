package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.*;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.GiftRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.GiftRequestQuery;
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

public class GiftReqController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private JFXButton clearButton;
  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXComboBox giftTypeComboBox;

  @FXML private ImageView imageView;

  LocationQuery locationQuery = new LocationQuery();
  List<Location> locations;
  EmployeeQuery employeeQuery = new EmployeeQuery();
  List<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  private void initialize() {

    imageView.setImage(ImageLoader.loadImage("Gifts"));

    LocationDaoImpl lDao = DaoSingleton.getLocationDao();
    EmployeeDaoImpl eDao = DaoSingleton.getEmployeeDao();
    locations = lDao.getAllNodes();
    employees = eDao.getAllNodes();

    DatabaseUtil.getLongNames(locationComboBox, "PATI");

    giftTypeComboBox.getItems().addAll("Flowers", "Balloons", "Stuffed Animal");

    for (Employee e : employees) {
      if (e.get_service_type().toLowerCase().equals("nurse")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }

    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");

    ControllerUtil.addAutoCompleteListener(giftTypeComboBox);
    ControllerUtil.addAutoCompleteListener(locationComboBox);
    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
  }

  @FXML
  private void submitButtonPressed() throws IOException {
    if (comboBoxesFilled()) {

      String chosenLocation = (String) locationComboBox.valueProperty().get();

      GiftRequest request =
          new GiftRequest(
              ServiceRequest.getNewestID(),
              LocationQuery.longToNodeID(chosenLocation),
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "GiftRequest",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              giftTypeComboBox.getSelectionModel().selectedItemProperty().getValue().toString());

      System.out.println(request.toString());

      GiftRequestQuery giftReqQuery = new GiftRequestQuery();
      GiftRequestDaoImpl gDao = DaoSingleton.getGiftRequestDao();
      gDao.addNode(request);

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);
    }
  }

  @FXML
  private void clearButtonPressed() throws IOException {
    locationComboBox.valueProperty().set(null);
    giftTypeComboBox.valueProperty().set(null);
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
    if (giftTypeComboBox.getSelectionModel().isEmpty()) {
      nodes.add(giftTypeComboBox);
    }
    if (statusComboBox.getSelectionModel().isEmpty()) {
      nodes.add(statusComboBox);
    }
    if (assignmentComboBox.getSelectionModel().isEmpty()) {
      nodes.add(assignmentComboBox);
    }
    if (!nodes.isEmpty()) {
      angryWiggle(nodes);
      return false;
    }
    return true;
  }
}
