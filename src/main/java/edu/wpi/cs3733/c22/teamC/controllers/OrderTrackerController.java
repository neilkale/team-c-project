package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.*;
import edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import edu.wpi.cs3733.c22.teamC.controllers.windowControllers.OrderController;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.swing.*;

// This class heavily relies on every service request having a proper toString method with correct
// string formatting
// This class also relies on the html structure of Order.fxml staying constant
// I will try to decouple iteration 4 - Jared
public class OrderTrackerController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private ListView listView;
  @FXML private JFXComboBox employeeComboBox;
  @FXML private JFXComboBox statusComboBox;
  @FXML private JFXComboBox idComboBox;
  @FXML private JFXComboBox locComboBox;
  @FXML private JFXComboBox typeComboBox;

  private static final int numOfComboBoxes = 5;
  private HashMap<String, ServiceRequest> requestHashMap = new HashMap<String, ServiceRequest>();
  private ArrayList<JFXComboBox> comboBoxes = new ArrayList<JFXComboBox>();
  private SRCriteria[] filterArray = new SRCriteria[numOfComboBoxes];

  // to clear/remove orders, must clear hashmap, orderControllerHashMap, and listView.items()

  @FXML
  public void initialize() {
    // make sure to add new comboboxes meant for filtering to this arraylist
    comboBoxes.addAll(
        FXCollections.observableArrayList(
            idComboBox, statusComboBox, locComboBox, typeComboBox, employeeComboBox));

    // populate combobox dropdowns
    statusComboBox.getItems().addAll("Blank", "Done", "Cancelled", "Waiting for");
    employeeComboBox.getItems().addAll((new EmployeeQuery()).getFullNameAll());
    typeComboBox
        .getItems()
        .addAll(
            "Equipment Request",
            "Gift Request",
            "Internal Transport Request",
            "IT Request",
            "Language Request",
            "Laundry Request",
            "Maintenance Request",
            "Medicine Request",
            "Religious Request",
            "Sanitation Request",
            "Security Request");
    idComboBox
        .getItems()
        .addAll(FXCollections.observableArrayList(ServiceRequest.getAvailableTicketIDs()));
    LocationQuery locationQuery = new LocationQuery();
    locationQuery
        .getAllNodeData()
        .forEach(
            node -> {
              locComboBox.getItems().add(node.get_longName());
            });

    // adding autocomplete listeners for comboboxes
    ControllerUtil.addAutoCompleteListener(statusComboBox);
    ControllerUtil.addAutoCompleteListener(employeeComboBox); // have to add after fill combobox
    ControllerUtil.addAutoCompleteListener(idComboBox);
    ControllerUtil.addAutoCompleteListener(locComboBox); // have to add after fill combobox
    ControllerUtil.addAutoCompleteListener(typeComboBox);

    // if no filters entered in comboboxes, show all orders; set float label color as well
    int count = 0;
    for (JFXComboBox comboBox : comboBoxes) {
      // set prompt text label color to #FFF if floating, else #4d4d4d
      comboBox
          .focusedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                if (oldValue && !comboBox.getEditor().getText().isBlank())
                  comboBox.setStyle("-fx-background-color: #FFF; -fx-prompt-text-fill: #FFF;");
                else
                  comboBox.setStyle("-fx-background-color: #FFF; -fx-prompt-text-fill: #4d4d4d;");
              });
      if (comboBox.getEditor().getText().isEmpty()) count++;
      if (count == comboBoxes.size()) {
        addRequestsToListView(ServiceRequest.getAllServiceRequests());
        count = 0;
      }
    }

    // add listener for each searchbox filter
    addComboBoxFilterListener(employeeComboBox, "assignment");
    addComboBoxFilterListener(statusComboBox, "status");
    addComboBoxFilterListener(idComboBox, "id");
    addComboBoxFilterListener(locComboBox, "location");
    addComboBoxFilterListener(typeComboBox, "type");

    // when you click out of an order you are editing, editing is disabled for that order
    listView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              listView
                  .getItems()
                  .forEach(
                      orderPane -> {
                        if (!orderPane.equals(newValue))
                          controllerMediator.setOrderFieldsEditable(
                              Integer.toString(orderPane.hashCode()), false);
                      });
            });
  }

  public JFXComboBox getEmployeeComboBox() {
    return employeeComboBox;
  }

  private void addRequestsToListView(List<ServiceRequest> requestsList) {
    // clear current request hashMap, orderController, and listView to allow for new categories of
    // requests
    listView.getItems().clear();
    requestHashMap.clear();
    controllerMediator.clearOrderController();

    // iterate through all requests
    requestsList.forEach(
        serviceRequest -> {
          try {
            // load new order pane which will store the fields for each request submitted
            FXMLLoader slideNavLoader = getLoader("Order.fxml");
            Pane orderPane = slideNavLoader.load();
            OrderController orderController = slideNavLoader.getController();

            String orderKey = Integer.toString(orderPane.hashCode());
            controllerMediator.addOrderController(orderKey, orderController);
            requestHashMap.put(orderKey, serviceRequest);

            // set title of order to request type
            controllerMediator.setOrderTitle(orderKey, serviceRequest.getRequestType());

            // get fieldName and fieldValue arrays and convert to ArrayLists
            ArrayList<String> fieldNamesArrayList =
                new ArrayList<String>(Arrays.asList(serviceRequest.getFieldNames()));
            ArrayList<String> fieldValuesArrayList =
                new ArrayList<String>(Arrays.asList(serviceRequest.getFieldValues()));

            fieldNamesArrayList.forEach(
                fieldName -> {
                  // if second string in array, must be ID of request, should not be editable
                  if (fieldNamesArrayList.indexOf(fieldName) == 0) {
                    Label idLabel = new Label(fieldName + ": " + fieldValuesArrayList.get(0));
                    controllerMediator.addNodeToOrder(orderKey, idLabel);
                  }

                  // for all other fields and values of request
                  else {
                    // add fieldName to a label
                    Label fieldNameLabel = new Label(fieldName);

                    // add fieldValue to a textField
                    TextField valueTextField;
                    // if field name is location id, lets take its fieldValue and convert the NODEID
                    // -> longName
                    if (fieldName.equalsIgnoreCase("location id")) {
                      LocationQuery locationQuery = new LocationQuery();
                      Location location =
                          locationQuery.findNodeByID(
                              fieldValuesArrayList.get(fieldNamesArrayList.indexOf(fieldName)));
                      valueTextField = new TextField(location.get_longName());
                    }
                    // else just use the corresponding raw fieldValue with same index as fieldName
                    else {
                      valueTextField =
                          new TextField(
                              fieldValuesArrayList.get(fieldNamesArrayList.indexOf(fieldName)));
                    }
                    valueTextField.setEditable(false); // set to not editable

                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(fieldNameLabel, valueTextField);
                    controllerMediator.addNodeToOrder(orderKey, vBox);
                  }
                });

            listView
                .getItems()
                .add(orderPane); // add orderPane of one request to listView for display
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  @FXML
  private void editButtonPressed() {
    // get currently selected order
    Object orderPane = listView.getSelectionModel().getSelectedItem();

    // if an order is actually selected, set the fields for that order to editable
    if (!Objects.isNull(orderPane)) {
      String hashKey = Integer.toString(orderPane.hashCode());
      controllerMediator.setOrderFieldsEditable(hashKey, true);
    } else ControllerUtil.popUpMessage("Error editing", "No order has been selected for editing");
  }

  @FXML
  private void deleteButtonPressed() {
    // get currently selected order pane
    Object orderPane = listView.getSelectionModel().getSelectedItem();

    // if there is an order pane actually selected
    if (!Objects.isNull(orderPane)) {
      // get the order pane's hashkey
      String orderKey = Integer.toString(orderPane.hashCode());

      // remove order pane from listView
      listView.getItems().remove(orderPane);

      // remove orderPane controller from hashmap
      controllerMediator.removeOrderController(orderKey);

      // retrieve the corresponding request instance that matches the request of this order pane
      ServiceRequest request = requestHashMap.get(orderKey);
      Query requestQuery = request.getQueryInstance(); // get query instance of the request

      try {
        requestQuery.removeNode(
            request); // call removeNode of query and pass in request corresponding to this order
        // pane
      } catch (SQLException e) {
        e.printStackTrace();
      }
      requestHashMap.remove(orderKey);

    } else ControllerUtil.popUpMessage("Error deleting", "No order has been selected for deletion");
  }

  @FXML
  private void saveButtonPressed() {
    // get currently selected order pane
    Object orderPane = listView.getSelectionModel().getSelectedItem();

    // if there is an order pane actually selected
    if (!Objects.isNull(orderPane)) {
      // get the order pane's hashkey
      String orderKey = Integer.toString(orderPane.hashCode());
      // retrieve the string fields as array list of the order pane;
      ArrayList<String> orderFieldsList = controllerMediator.getAllOrderFields(orderKey);
      orderFieldsList.forEach(
          field -> {
            if (field.isEmpty()) {
              ControllerUtil.popUpMessage("Error Saving", "Not all fields have been filled");
              return;
            }
            // else if the field is the location fieldValue, convert it back to NodeID for database
            // storage
            else if (orderFieldsList.indexOf(field)
                == controllerMediator.getLocationFieldIndex(orderKey)) {
              String nodeID = LocationQuery.longToNodeID(field);
              if (!nodeID.equals("")) orderFieldsList.set(orderFieldsList.indexOf(field), nodeID);
              else
                ControllerUtil.popUpMessage("Error Saving", "You have entered an invalid location");
            }
          });

      // retrieve the corresponding request instance that matches the request of this order pane
      ServiceRequest request = requestHashMap.get(orderKey);
      Query requestQuery = request.getQueryInstance(); // get query instance of the request

      // create new request holding edited fields using queryFactory method
      Object editedRequest = requestQuery.queryFactory(orderFieldsList.toArray(new String[0]));
      System.out.println(editedRequest.toString());
      try {
        requestQuery.editNode(
            editedRequest); // call editNode of query and pass in new edited request
      } catch (SQLException e) {
        e.printStackTrace();
      }

    } else ControllerUtil.popUpMessage("Error saving", "No order has been selected for saving");
  }

  @FXML
  private void refreshButtonPressed() {
    controllerMediator.setDefaultPageCenter("OrderTracker.fxml");
  }

  private void addComboBoxFilterListener(JFXComboBox comboBox, String criteriaType) {
    // listen for text change in combobox
    comboBox
        .getEditor()
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // criteria class stores new text entered, decide which class based on string input
              SRCriteria srCriteria;
              switch (criteriaType) {
                case "assignment":
                  srCriteria = new SRCriteriaAssignment(comboBox.getEditor().getText());
                  break;
                case "status":
                  srCriteria = new SRCriteriaStatus(comboBox.getEditor().getText());
                  break;
                case "id":
                  srCriteria = new SRCriteriaID(comboBox.getEditor().getText());
                  break;
                case "location":
                  srCriteria = new SRCriteriaLocationLong(comboBox.getEditor().getText());
                  break;
                case "type":
                  srCriteria = new SRCriteriaType(comboBox.getEditor().getText());
                  break;
                default:
                  srCriteria = new SRCriteriaLocation("");
                  break;
              }

              // assign criteria class to index of filterArray meant for the combobox
              // this cause array values to be overwritten, so that old criteria can be eliminated
              filterArray[comboBoxes.indexOf(comboBox)] = srCriteria;
              // get all service requests submitted currently
              ArrayList<ServiceRequest> allServiceRequests = ServiceRequest.getAllServiceRequests();
              // looseAndCriteria class filter service requests based on filterArray
              List<ServiceRequest> filteredServiceRequests =
                  new SRLooseAndCriteria(filterArray).meetCriteria(allServiceRequests);
              addRequestsToListView(filteredServiceRequests);
            });
  }
}
