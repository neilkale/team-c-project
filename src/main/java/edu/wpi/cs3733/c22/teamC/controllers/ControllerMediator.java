package edu.wpi.cs3733.c22.teamC.controllers;

import edu.wpi.cs3733.c22.teamC.controllers.windowControllers.OrderController;
import edu.wpi.cs3733.c22.teamC.controllers.windowControllers.TabPaneController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.Label;

// this singleton mediator will be used for all incoming and outgoing controller communication
// now controllers don't call each other directly, leading to low coupling and centralized
// communication btw controllers
// all methods below will be called by other controllers to let mediator know to send messages to
// its field controllers
public class ControllerMediator {
  // create an object of SingleObject
  private static ControllerMediator instance = new ControllerMediator();
  private TabPaneController tabPaneController;
  private HashMap<String, DefaultController> defaultControllerMap =
      new HashMap<String, DefaultController>();
  private HashMap<String, OrderController> orderControllerMap =
      new HashMap<String, OrderController>();

  // make the constructor private so that this class cannot be
  // instantiated
  private ControllerMediator() {}

  // Get the only object available
  public static ControllerMediator getInstance() {
    return instance;
  }

  public void setTabPaneController(TabPaneController tabPaneController) {
    this.tabPaneController = tabPaneController;
  }

  // takes in the hashcode key of a Tab and a Default Controller instance
  public void addDefaultController(String tabKey, DefaultController defaultController) {
    this.defaultControllerMap.put(tabKey, defaultController);
  }

  public void removeDefaultController(String key) {
    this.defaultControllerMap.remove(key);
  }

  public void resetDefaultControllerList() {
    this.defaultControllerMap.clear();
  }

  // gets the default controller from the hashmap by using the hashcode of the currently selected
  // tab
  private DefaultController getDefaultController() throws IOException {
    String key =
        Integer.toString(
            this.tabPaneController.getTabPane().getSelectionModel().getSelectedItem().hashCode());
    if (defaultControllerMap.containsKey(key)) return defaultControllerMap.get(key);
    return null;
  }

  // takes in an OrderPane hashcode key and an Order Controller object
  public void addOrderController(String orderKey, OrderController orderController) {
    orderControllerMap.put(orderKey, orderController);
  }

  // takes in order pane key and removes corresponding order controller object
  public void removeOrderController(String orderKey) {
    orderControllerMap.remove(orderKey);
  }

  public void clearOrderController() {
    orderControllerMap.clear();
  }

  private OrderController getOrderController(String orderKey) {
    return orderControllerMap.get(orderKey);
  }

  public void addNodeToOrder(String orderKey, Node node) {
    getOrderController(orderKey).addNode(node);
  }

  // makes all textfields of an order pane editable
  public void setOrderFieldsEditable(String orderKey, Boolean isEditable) {
    getOrderController(orderKey).setTextFieldsEditable(isEditable);
  }

  // returns array list of all string fields from the textfields of an order pane
  public ArrayList<String> getAllOrderFields(String orderKey) {
    return getOrderController(orderKey).getAllFieldValues();
  }

  public void setOrderTitle(String orderKey, String titleText) {
    getOrderController(orderKey).setTitleLabelText(titleText);
  }

  // sets the center of DefaultPage's borderpane; this method preserves the side menu to the left of
  // DefaultPage
  public void setDefaultPageCenter(String fxmlFileName) {
    try {
      this.getDefaultController().setCenter(fxmlFileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // adds nodes to the anchor pane of tabPaneController
  public void addAnchorPaneChildren(Node node) {
    this.tabPaneController.getAnchorPane().getChildren().add(node);
  }

  // sets the name of the currently selected tab
  public void setTabName(String tabName) {
    this.tabPaneController
        .getTabPane()
        .getSelectionModel()
        .getSelectedItem()
        .setGraphic(new Label(tabName));
  }

  public void anchorPushNotification(String title, String message) {
    ControllerUtil.addPushNotification(title, message, this.tabPaneController.getAnchorPane());
  }

  // universal back button implementation
  public void backButtonPressed() throws IOException {
    this.getDefaultController().backButtonPressed();
  }
}
