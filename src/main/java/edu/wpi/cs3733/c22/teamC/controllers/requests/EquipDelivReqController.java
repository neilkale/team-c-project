package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.EquipmentRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.EquipmentRequestQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerUtil;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class EquipDelivReqController extends AbstractController {

  @FXML private JFXButton backButton;

  @FXML private JFXComboBox locationComboBox;
  @FXML private JFXComboBox urgencyComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox assignmentComboBox;
  @FXML private JFXComboBox equipmentComboBox;

  @FXML private ImageView equipmentIcon;

  private LinkedList<Label> selectLabelsList = new LinkedList<Label>();

  LocationQuery locationQuery = new LocationQuery();
  private List<Location> locations;

  private EmployeeQuery employeeQuery = new EmployeeQuery();
  private ArrayList<Employee> employees;

  private HashMap<String, String> employeeNames = new HashMap<>();

  @FXML
  public void initialize() throws SQLException {

    equipmentIcon.setImage(ImageLoader.loadImage("EquipDelivery"));

    locations = locationQuery.getAllNodeData();
    employees = employeeQuery.getAllNodeData();

    String nodeTypeDesired = "PATI";

    for (Location l : locations) {
      if (l.get_nodeType().equals(nodeTypeDesired)) {
        locationComboBox.getItems().add(l.get_longName());
      }
    }
    equipmentComboBox.getItems().add("X-Ray");
    equipmentComboBox.getItems().add("Pump");
    equipmentComboBox.getItems().add("Bed");
    equipmentComboBox.getItems().add("Recliner");

    for (Employee e : employees) {
      if (e.get_Service_Type().toLowerCase().equals("nurse")) {
        String name = e.get_firstName() + " " + e.get_lastName().charAt(0);
        String fullName = e.get_firstName() + " " + e.get_lastName();
        employeeNames.put(name, fullName);
        assignmentComboBox.getItems().add(name);
      }
    }

    urgencyComboBox.getItems().addAll("Urgent", "Not Urgent");

    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for Equipment");

    ControllerUtil.addAutoCompleteListener(assignmentComboBox);
    ControllerUtil.addAutoCompleteListener(locationComboBox);
  }

  @FXML
  private void confirmButtonPressed() throws IOException, SQLException {
    if (comboBoxesFilled()) {
      EquipmentRequestQuery allNodeValues = new EquipmentRequestQuery();
      List<EquipmentRequest> serviceRequestList = allNodeValues.getAllNodeData();

      String nodeIDSelected = "";

      for (Location l : locations) {
        if (l.get_longName().equals(locationComboBox.valueProperty().get()))
          nodeIDSelected = l.get_nodeID();
      }
      String equipType = "";
      switch ((String) equipmentComboBox.getSelectionModel().getSelectedItem()) {
        case "X-Ray":
          equipType = "XRAY";
          break;
        case "Pump":
          equipType = "PUMP";
          break;
        case "Recliner":
          equipType = "RECL";
          break;
        case "Bed":
          equipType = "BEDS";
          break;
      }

      EquipmentRequest request =
          new EquipmentRequest(
              ServiceRequest.getNewestID(),
              LocationQuery.longToNodeID(
                  locationComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              statusComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              "equip_distributor",
              employeeNames.get(
                  assignmentComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()),
              urgencyComboBox.getSelectionModel().selectedItemProperty().getValue().toString(),
              equipType,
              LocationQuery.longToNodeID(
                  locationComboBox
                      .getSelectionModel()
                      .selectedItemProperty()
                      .getValue()
                      .toString()));
      System.out.println(request.get_assignment());
      allNodeValues.addNode(request);

      String[] toString = request.toString().split("\n", 2);
      controllerMediator.anchorPushNotification(toString[0], toString[1]);
    }
  }

  /**
   * Returns a boolean value for if the combo boxes have selected values in the
   * EquipDelivReqController
   *
   * @return boolean value for if all the combo boxes have selections that are
   */
  private boolean comboBoxesFilled() {
    ArrayList<Node> nodes = new ArrayList<>();

    if (equipmentComboBox.getSelectionModel().isEmpty()) {
      nodes.add(equipmentComboBox);
    }
    if (locationComboBox.getSelectionModel().isEmpty()) {
      nodes.add(locationComboBox);
    }
    if (urgencyComboBox.getSelectionModel().isEmpty()) {
      nodes.add(urgencyComboBox);
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

  @FXML
  private void equipmentComboBoxSelected() {
    if (equipmentComboBox.getSelectionModel().getSelectedItem() != null) {
      switch ((String) equipmentComboBox.getSelectionModel().getSelectedItem()) {
        case "X-Ray":
          equipmentIcon.setImage(ImageLoader.loadImage("XRAYBLUE"));
          break;
        case "Pump":
          equipmentIcon.setImage(ImageLoader.loadImage("PUMPBLUE"));
          break;
        case "Recliner":
          equipmentIcon.setImage(ImageLoader.loadImage("RECLBLUE"));
          break;
        case "Bed":
          equipmentIcon.setImage(ImageLoader.loadImage("BEDSBLUE"));
          break;
      }
    } else {
      equipmentIcon.setImage(ImageLoader.loadImage("EquipDelivery"));
    }
  }

  @FXML
  private void clearButtonPressed() {

    urgencyComboBox.valueProperty().set(null);
    locationComboBox.valueProperty().set(null);
    statusComboBox.valueProperty().set(null);
    assignmentComboBox.valueProperty().set(null);
    equipmentComboBox.valueProperty().set(null);
  }
}
