package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.PathFinding.PathFinder;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MapEditorController extends AbstractController { // todo implement the new filter
  @FXML Pane iconPane;
  @FXML Pane mapPane;
  @FXML ImageView mapView;

  @FXML Label requestTicketLabel;
  @FXML Label requestTypeLabel;
  @FXML Label requestStatusLabel;

  @FXML TabPane tabPane;
  @FXML Tab locationTab;
  @FXML Tab equipmentTab;
  @FXML Tab servicesTab;

  @FXML JFXComboBox floorComboBox;
  @FXML JFXComboBox typeComboBox;
  @FXML JFXTextField shortNameField;
  @FXML JFXTextField longNameField;

  @FXML JFXComboBox equipmentTypeComboBox;
  @FXML JFXComboBox equipmentStatusComboBox;
  @FXML JFXTextField nameField;

  @FXML JFXTreeTableView locationsTable;

  @FXML JFXButton addButton;
  @FXML JFXButton startButton;
  @FXML JFXButton endButton;

  @FXML JFXButton computeButton;

  @FXML JFXButton l2Button;
  @FXML JFXButton l1Button;
  @FXML JFXButton f1Button;
  @FXML JFXButton f2Button;
  @FXML JFXButton f3Button;

  @FXML JFXButton locationUpdateButton;
  @FXML JFXButton equipmentUpdateButton;

  @FXML GridPane controlPane;

  @FXML ScrollPane scrollPane;

  @FXML JFXCheckBox serviceReqsCheckbox;
  @FXML JFXCheckBox equipmentCheckBox;
  @FXML JFXCheckBox locationsCheckBox;

  private final int actualMapWidth = 875;
  private final int actualMapHeight = 786;

  private boolean dragStarted = false;

  private final double maxZoomFactor = 2.5;

  private boolean movingPoint = false;
  private boolean movingLocation = false;

  private String[] floorNames = {"L2", "L1", "1", "2", "3"};

  private MapNodeSelector selector = new MapNodeSelector();
  private MapNodeList list = new MapNodeList();
  private MapNavLine mapNavLine = new MapNavLine();

  private List<Location> path = null;

  @FXML
  private void initialize() {
    list.load();

    // load the first map
    mapView.setImage(ImageLoader.loadImage("L2"));
    MapState.setCurrentFloor("L2");

    // setup floornames combo box
    for (String f : floorNames) {
      floorComboBox.getItems().add(f);
    }

    // Fill in those fancy combo boxes
    for (MapNode n : list.nodes) {
      if (!typeComboBox.getItems().contains(n.getLocation().get_nodeType())) {
        typeComboBox.getItems().add(n.getLocation().get_nodeType());
      }
      for (MedicalEquipment e : n.equipment) {
        if (!equipmentTypeComboBox.getItems().contains(e.get_equipmentType()))
          equipmentTypeComboBox.getItems().add(e.get_equipmentType());

        if (!equipmentStatusComboBox.getItems().contains(e.get_status()))
          equipmentStatusComboBox.getItems().add(e.get_status());
      }
    }

    makeTreeTable();
    refresh();
  }

  private void refresh() {

    // Adjust button opacity according to which floor we are on
    String floor = MapState.getCurrentFloor();
    l2Button.setOpacity(.3);
    l1Button.setOpacity(.3);
    f1Button.setOpacity(.3);
    f2Button.setOpacity(.3);
    f3Button.setOpacity(.3);
    if (floor.equals("L2")) l2Button.setOpacity(.5);
    if (floor.equals("L1")) l1Button.setOpacity(.5);
    if (floor.equals("1")) f1Button.setOpacity(.5);
    if (floor.equals("2")) f2Button.setOpacity(.5);
    if (floor.equals("3")) f3Button.setOpacity(.5);

    // Make sure all images and panes are properly zoomed
    double zoomFactor = MapState.getZoomFactor();
    mapView.setFitWidth(actualMapWidth * zoomFactor);
    mapView.setFitHeight(actualMapHeight * zoomFactor);

    mapPane.setPrefWidth(actualMapWidth * zoomFactor);
    mapPane.setPrefHeight(actualMapHeight * zoomFactor);

    iconPane.setPrefWidth(actualMapWidth * zoomFactor);
    iconPane.setPrefHeight(actualMapHeight * zoomFactor);

    // Clear icons from screen
    iconPane.getChildren().clear();

    // display node icons

    for (MapNode n : list.nodes) {
      n.update();
      n.setOnMouseEntered(
          (MouseEvent evt) -> {
            if (!n.isSelected()) n.setOpacity(.8);
          });
      n.setOnMouseExited(
          (MouseEvent evt) -> {
            if (!n.isSelected()) n.setOpacity(.6);
          });
      iconPane.getChildren().add(n);
    }

    // display superscripts
    for (MapNode n : list.nodes) {
      Node node = n.getSuperscript();
      if (node != null && n.isVisible()) {
        iconPane.getChildren().add(node);
      }
    }

    // Astar implementation
    if (MapState.getStartIndex() != -1 && MapState.getStopIndex() != -1) {
      computeButton.setDisable(false);
    } else {
      computeButton.setDisable(true);
    }

    if (path != null) iconPane.getChildren().add(mapNavLine.getNavLine(path));

    // Reset selections
    movingPoint = false;
    movingLocation = false;

    updateOnClickMenuTabs();
  }

  // Makes the feature tree
  private void makeTreeTable() {
    TreeTableColumn<MapTableObject, String> treeTableColumn =
        new TreeTableColumn<>("Location Manager");

    boolean existingTable = locationsTable.getRoot() != null;

    treeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));

    treeTableColumn.setPrefWidth(200);

    locationsTable.getColumns().clear();
    locationsTable.getColumns().add(treeTableColumn);

    TreeItem<MapTableObject> treeNode = new TreeItem<>(new MapTableObject("Floors"));
    treeNode.setExpanded(true);

    // Makes a treenode for each floor and fills it with the locations
    for (int i = 0; i < floorNames.length; i++) {
      String floor = floorNames[i];
      TreeItem<MapTableObject> floorNode = new TreeItem<>(new MapTableObject(floor));
      // Keeps any expanded subtrees expanded
      if (existingTable) {
        floorNode.setExpanded(
            ((TreeItem) locationsTable.getRoot().getChildren().get(i)).isExpanded());
      }

      // For each floor, loops through and picks out the nodes that it needs in the subtree
      for (MapNode n : list.nodes) {
        if (n.getLocation().get_floor().equals(floor)) {
          TreeItem<MapTableObject> subNode =
              new TreeItem<>(
                  new MapTableObject(n.getLocation().get_longName(), n.getLocation().get_nodeID()));

          floorNode.getChildren().add(subNode);
        }
      }

      treeNode.getChildren().add(floorNode);
    }
    locationsTable.setRoot(treeNode);
  }

  @FXML
  private void addButtonPressed() {
    if (locationTab.isSelected()) {
      // Add a new location
      MapNode node = list.nodes.get(list.nodes.size() - 1);
      node.setNewPoint(false);
      node.update();
      makeTreeTable();
    } else if (equipmentTab.isSelected()) {
      // add a new equipment to the selected mapnode
      MapNode node = list.nodes.get(MapState.getIndexSelected());
      Random rand = new Random();
      MedicalEquipment e =
          new MedicalEquipment(
              Integer.toString(rand.nextInt(10000)),
              node.getLocation().get_nodeID(),
              "",
              "CLEAN",
              "XRAY",
              "NEW EQUIPMENT");
      node.addEquipment(e);
      updateFields();
    }
  }

  @FXML
  private void removeButtonPressed() {
    int index = MapState.getIndexSelected();

    if (locationTab.isSelected()) {
      // remove selected location
      if (index != -1) {
        list.nodes.remove(index);
        iconPane.getChildren().remove(index);
        MapState.setIndexSelected(-1);
        if (index == MapState.getStartIndex()) MapState.setStartIndex(-1);
        if (index == MapState.getStopIndex()) MapState.setStopIndex(-1);
      }
      hideOnClickMenu();
      makeTreeTable();
    }
    if (equipmentTab.isSelected()) {
      // remove selected equipment
      MapNode node = list.nodes.get(index);
      if (node.hasEquipment()) {
        node.removeEquipment();
      }
      updateFields();
    }
    if (servicesTab.isSelected()) {
      // remove selected serviceRequest
      MapNode node = list.nodes.get(index);
      if (node.hasServiceReq()) {
        node.removeServiceReq();
      }
      updateFields();
    }
  }

  @FXML
  private void moveButtonPressed() {
    hideOnClickMenu();
    if (locationTab.isSelected()) movingLocation = true;

    movingPoint = true;
  }

  @FXML
  private void updateButtonPressed() {
    int index = MapState.getIndexSelected();
    if (locationTab.isSelected()) {
      // Update the selected location
      if (typeComboBox.valueProperty().get() != null
          && floorComboBox.valueProperty().get() != null
          && !longNameField.getText().equals("Long Name")
          && !shortNameField.getText().equals("Short Name")) {
        list.nodes
            .get(index)
            .updateLocation(
                longNameField.getText(),
                shortNameField.getText(),
                (String) typeComboBox.valueProperty().get(),
                (String) floorComboBox.valueProperty().get());
      }

      makeTreeTable();
    }
    if (equipmentTab.isSelected()) {
      // Update the selected equipment
      if (equipmentTypeComboBox.valueProperty().get() != null
          && equipmentStatusComboBox.valueProperty().get() != null
          && !nameField.getText().equals("Name")
          && list.nodes.get(index).hasEquipment()) {
        MedicalEquipment e = list.nodes.get(index).getEquipment();
        e.set_status((String) equipmentStatusComboBox.valueProperty().get());
        e.set_equipmentType((String) equipmentTypeComboBox.valueProperty().get());
        e.set_name(nameField.getText());
        list.nodes.get(index).setEquipment(e);
      }
    }
    refresh();
  }

  @FXML
  void onScroll(ScrollEvent evt) {
    hideOnClickMenu();
    // Zoom in and out
    if (evt.getDeltaY() < 0) {
      zoomOutButtonPressed();
    } else {
      zoomInButtonPressed();
    }
    evt.consume();
  }

  @FXML
  private void zoomInButtonPressed() {
    if (MapState.getZoomFactor() < maxZoomFactor) {
      MapState.setZoomFactor(MapState.getZoomFactor() + .1);
      refresh();
    }
  }

  @FXML
  private void zoomOutButtonPressed() {
    if (MapState.getZoomFactor() > .1) {
      MapState.setZoomFactor(MapState.getZoomFactor() - .1);
      refresh();
    }
  }

  void moveNode(double x, double y) {
    movingPoint = false;

    int index = MapState.getIndexSelected();

    if (index == -1) {
      return;
    }
    // Move equipment from one node to another
    if (MapState.isEquipmentShown() && list.nodes.get(index).hasEquipment() && !movingLocation) {
      selector.moveEquipment(x, y);
    }
    // Move existing node
    else {
      list.nodes
          .get(index)
          .setLocationPos(x / MapState.getZoomFactor(), y / MapState.getZoomFactor());
      movingLocation = false;
    }
    refresh();
  }

  @FXML
  void handleTableClick(MouseEvent evt) {
    if (evt.getButton() != MouseButton.PRIMARY && evt.getButton() != MouseButton.SECONDARY) {
      return;
    }

    if (movingPoint) {

      int index = MapState.getIndexSelected();

      if (locationsTable.getSelectionModel().getSelectedItem() == null) {
        return;
      }

      if (index != -1) {
        if (MapState.isEquipmentShown() && list.nodes.get(index).hasEquipment()) {
          TreeItem<MapTableObject> selectedItem =
              (TreeItem<MapTableObject>) locationsTable.getSelectionModel().getSelectedItem();

          if (selectedItem.getValue().getId() != null) {
            selector.moveEquipment(selectedItem.getValue().getId());
            refresh();
          }
        }
      }
      movingPoint = false;
    } else if (evt.getButton() == MouseButton.SECONDARY) {
      TreeItem<MapTableObject> selectedItem =
          (TreeItem<MapTableObject>) locationsTable.getSelectionModel().getSelectedItem();
      if (selectedItem.getValue().getId() != null) {

        selector.selectNode(selectedItem.getValue().getId());
        placeOnClickMenu(0, evt.getY());

        refresh();
      }
    }
  }

  @FXML
  void handleClick(MouseEvent evt) {
    if (evt.getButton() == MouseButton.MIDDLE) scrollPane.setPannable(false);
    if (evt.getButton() != MouseButton.PRIMARY && evt.getButton() != MouseButton.SECONDARY) return;

    // Grab mouse coords
    double clickX = evt.getX();
    double clickY = evt.getY();

    if (dragStarted) {
      dragStarted = false;
      int index = MapState.getIndexSelected();
      if (index != -1) {
        list.nodes.get(index).update();
      }
    }

    if (movingPoint) {
      moveNode(clickX, clickY);
    }
    // Selecting a point
    else {
      selector.selectNearestNode(clickX, clickY);
    }

    if (evt.getButton() == MouseButton.SECONDARY) placeOnClickMenu(clickX, clickY);
    else hideOnClickMenu();

    refresh();
  }

  @FXML
  void handleDrag(MouseEvent evt) {
    // middle mouse drag pans screen
    if (evt.getButton() == MouseButton.MIDDLE) scrollPane.setPannable(true);

    // Filters out non-left clicks
    if (evt.getButton() != MouseButton.PRIMARY) return;

    double clickX = evt.getX();
    double clickY = evt.getY();

    selector.removeNewSelection();

    if (!dragStarted) {
      // If the drag hasn't started yet, select the node
      selector.selectNearestNode(clickX, clickY);

      if (MapState.getIndexSelected() != -1) {
        movingPoint = true;
      }
      dragStarted = true;
    }

    // keep image on the mouse as it drags
    else {
      int index = MapState.getIndexSelected();
      if (index != -1) {
        int radius = ((MapNode) iconPane.getChildren().get(index)).getRadius();
        ((MapNode) iconPane.getChildren().get(index)).setX(clickX - radius);
        ((MapNode) iconPane.getChildren().get(index)).setY(clickY - radius);
        iconPane.getChildren().get(index).setOpacity(.2);
      }
    }
  }

  @FXML
  private void l2ButtonPressed() {
    MapState.setCurrentFloor("L2");
    mapView.setImage(ImageLoader.loadImage("L2"));
    refresh();
    hideOnClickMenu();
  }

  @FXML
  private void l1ButtonPressed() {
    MapState.setCurrentFloor("L1");
    mapView.setImage(ImageLoader.loadImage("L1"));
    refresh();
    hideOnClickMenu();
  }

  @FXML
  private void f1ButtonPressed() {
    MapState.setCurrentFloor("1");
    mapView.setImage(ImageLoader.loadImage("1"));
    refresh();
    hideOnClickMenu();
  }

  @FXML
  private void f2ButtonPressed() {
    MapState.setCurrentFloor("2");
    mapView.setImage(ImageLoader.loadImage("2"));
    refresh();
    hideOnClickMenu();
  }

  @FXML
  private void f3ButtonPressed() {
    MapState.setCurrentFloor("3");
    mapView.setImage(ImageLoader.loadImage("3"));
    refresh();
    hideOnClickMenu();
  }

  // Moves the mobile button pane to the selected x and y coords
  private void placeOnClickMenu(double xCoord, double yCoord) {
    int index = MapState.getIndexSelected();
    if (index == -1) {
      hideOnClickMenu();
    }
    // If a point is selected
    else {
      if (list.nodes.get(MapState.getIndexSelected()).isNewPoint()) {
        startButton.setDisable(true);
        endButton.setDisable(true);
      } else {
        startButton.setDisable(false);
        endButton.setDisable(false);
      }
      MapNode node = list.nodes.get(index);
      tabPane.setVisible(true);
      tabPane.setLayoutX(xCoord);
      tabPane.setLayoutY(yCoord);
      updateOnClickMenuTabs();
      updateFields();
      if (node.isNewPoint()) {
        addButton.setDisable(false);
      } else {
        addButton.setDisable(true);
      }
      if (list.nodes.get(index).hasEquipment() && tabPane.getTabs().contains(equipmentTab))
        tabPane.getSelectionModel().select(equipmentTab);
      else if (list.nodes.get(index).hasServiceReq() && tabPane.getTabs().contains(servicesTab))
        tabPane.getSelectionModel().select(servicesTab);
      else if (tabPane.getTabs().contains(locationTab))
        tabPane.getSelectionModel().select(locationTab);
    }
  }

  // hides the mobile button pane
  private void hideOnClickMenu() {
    tabPane.setVisible(false);
  }

  // Resizes and adjusts the tabpane according to the current filters
  @FXML
  private void updateOnClickMenuTabs() {
    if (MapState.getIndexSelected() == -1) {
      hideOnClickMenu();
      return;
    }

    Tab previouslyOpenTab = tabPane.getSelectionModel().getSelectedItem();

    int paneWidth = 30;

    tabPane.getTabs().clear();
    if (MapState.isLocationsShown()) {
      tabPane.getTabs().add(locationTab);
      paneWidth += 58;
    }
    if (MapState.isEquipmentShown()) {
      tabPane.getTabs().add(equipmentTab);
      paneWidth += 70;
    }
    if (MapState.isServicesShown()) {
      tabPane.getTabs().add(servicesTab);
      paneWidth += 55;
    }
    if (paneWidth < 130) paneWidth = 130;

    tabPane.setPrefWidth(paneWidth);

    if (tabPane.getTabs().contains(previouslyOpenTab))
      tabPane.getSelectionModel().select(previouslyOpenTab);
    else tabPane.getSelectionModel().select(0);

    if (locationTab.isSelected()) tabPane.setPrefHeight(273);
    if (equipmentTab.isSelected()) tabPane.setPrefHeight(243);
    if (servicesTab.isSelected()) tabPane.setPrefHeight(213);
    if (tabPane.getHeight() + tabPane.getLayoutY() > iconPane.getHeight()) {
      tabPane.setLayoutY(iconPane.getHeight() - tabPane.getHeight());
    }
  }

  // updates the fields with the data from the selected mapnode
  private void updateFields() {
    MapNode node = list.nodes.get(MapState.getIndexSelected());

    longNameField.setText(node.getLocation().get_longName());
    shortNameField.setText(node.getLocation().get_shortName());
    typeComboBox.valueProperty().set(node.getLocation().get_nodeType());
    floorComboBox.valueProperty().set(node.getLocation().get_floor());

    if (node.hasEquipment()) {
      equipmentStatusComboBox.valueProperty().set(node.getEquipment().get_status());
      equipmentTypeComboBox.valueProperty().set(node.getEquipment().get_equipmentType());
      nameField.setText(node.getEquipment().get_name());
    } else {
      equipmentStatusComboBox.valueProperty().set(null);
      equipmentTypeComboBox.valueProperty().set(null);
      nameField.setText("");
    }

    if (node.hasServiceReq()) {
      requestTypeLabel.setText("Request type: " + node.getServiceReq().get_serviceType());
      requestTicketLabel.setText("Ticket # " + node.getServiceReq().get_ticketID());
      requestStatusLabel.setText("Status: " + node.getServiceReq().get_status());
    } else {
      requestTypeLabel.setText("NO REQUESTS");
      requestTicketLabel.setText("");
      requestStatusLabel.setText("");
    }
    refresh();
  }

  @FXML
  private void showServiceReqsButtonPressed(MouseEvent evt) {
    if (evt.getButton() == MouseButton.SECONDARY) { // Right click will hide the other fields
      MapState.setLocationsShown(false);
      locationsCheckBox.setSelected(false);
      MapState.setEquipmentShown(false);
      equipmentCheckBox.setSelected(false);
      serviceReqsCheckbox.setSelected(true);
    }
    MapState.setServicesShown(serviceReqsCheckbox.isSelected());
    refresh();
  }

  @FXML
  private void showEquipmentButtonPressed(MouseEvent evt) {
    if (evt.getButton() == MouseButton.SECONDARY) { // Right click will hide the other fields
      MapState.setLocationsShown(false);
      locationsCheckBox.setSelected(false);
      MapState.setServicesShown(false);
      serviceReqsCheckbox.setSelected(false);
      equipmentCheckBox.setSelected(true);
    }
    MapState.setEquipmentShown(equipmentCheckBox.isSelected());
    refresh();
  }

  @FXML
  private void showLocationsButtonPressed(MouseEvent evt) {
    if (evt.getButton() == MouseButton.SECONDARY) { // Right click will hide the other fields
      MapState.setEquipmentShown(false);
      equipmentCheckBox.setSelected(false);
      MapState.setServicesShown(false);
      serviceReqsCheckbox.setSelected(false);
      locationsCheckBox.setSelected(true);
    }
    MapState.setLocationsShown(locationsCheckBox.isSelected());
    refresh();
  }

  @FXML
  private void previousIndexButtonPressed() {
    int index = MapState.getIndexSelected();
    if (index != -1) {
      if (equipmentTab.isSelected()) list.nodes.get(index).incrementEquipmentIndex(-1);
      if (servicesTab.isSelected()) list.nodes.get(index).incrementServiceIndex(-1);
    }
    updateFields();
    refresh();
  }

  @FXML
  private void nextIndexButtonPressed() {
    int index = MapState.getIndexSelected();
    if (index != -1) {
      if (equipmentTab.isSelected()) list.nodes.get(index).incrementEquipmentIndex(1);
      if (servicesTab.isSelected()) list.nodes.get(index).incrementServiceIndex(1);
    }
    updateFields();
    refresh();
  }

  @FXML
  private void resetButtonPressed() {
    list.load();
    refresh();
  }

  @FXML
  private void saveButtonPressed() throws Exception {
    list.save();
  }

  @FXML
  private void cancelButtonPressed() {
    hideOnClickMenu();
  }

  @FXML
  private void startButtonPressed() {
    if (MapState.getStartIndex() != -1)
      list.nodes.get(MapState.getStartIndex()).setStartPoint(false);

    MapState.setStartIndex(MapState.getIndexSelected());
    if (MapState.getIndexSelected() != -1) {
      list.nodes.get(MapState.getIndexSelected()).setStartPoint(true);
    }
    path = null;
    refresh();
  }

  @FXML
  private void endButtonPressed() {
    if (MapState.getStopIndex() != -1) list.nodes.get(MapState.getStopIndex()).setStopPoint(false);

    MapState.setStopIndex(MapState.getIndexSelected());
    if (MapState.getIndexSelected() != -1) {
      list.nodes.get(MapState.getIndexSelected()).setStopPoint(true);
    }
    path = null;
    refresh();
  }

  @FXML
  private void computeButtonPressed() {
    PathFinder pf = new PathFinder();
    hideOnClickMenu();
    if (MapState.getStartIndex() != -1
        && MapState.getStopIndex() != -1
        && MapState.getStopIndex() != MapState.getStartIndex()) {
      path =
          pf.findPath(
              list.nodes.get(MapState.getStartIndex()).getLocation(),
              list.nodes.get(MapState.getStopIndex()).getLocation());
      System.out.println(path.size());
      refresh();
    } else {
      path = new ArrayList<Location>();
      refresh();
    }
  }
}
