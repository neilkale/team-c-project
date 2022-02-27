package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXAutoCompleteEvent;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class ControllerUtil {
  private ControllerUtil() {}

  // this method only works for JFXComboBox for now
  @FXML
  public static void addAutoCompleteListener(JFXComboBox comboBox) {
    JFXAutoCompletePopup autoCompletePopup = new JFXAutoCompletePopup<>();

    int MAX_CELL_SIZE = 10;
    comboBox.setVisibleRowCount(MAX_CELL_SIZE);
    // adds all combobox items to autocomplete dropdown
    autoCompletePopup.getSuggestions().addAll(comboBox.getItems());
    autoCompletePopup.setCellLimit(MAX_CELL_SIZE / 2);
    // SelectionHandler sets the value of the comboBox when selecting auto complete dropdown
    autoCompletePopup.setSelectionHandler(
        event -> {
          JFXAutoCompleteEvent jfxAutoCompleteEvent = (JFXAutoCompleteEvent) event;
          comboBox.setValue(((JFXAutoCompleteEvent<?>) event).getObject());
        });

    comboBox.setEditable(true);

    TextField editor = comboBox.getEditor(); // make combox a textfield editor

    editor.addEventHandler(
        KeyEvent.ANY,
        event -> {
          if (!event.getCode().isNavigationKey()) { // allow arrow navigation between characters
            // The filter method uses the Predicate to filter the Suggestions defined above
            // I choose to use the contains method while ignoring cases
            autoCompletePopup.filter( // filter out combobox items from typed characters
                item -> item.toString().toLowerCase().contains(editor.getText().toLowerCase()));
            // Hide the autocomplete popup if the filtered suggestions is empty or when the box's
            // original popup is open
            if (autoCompletePopup.getFilteredSuggestions().isEmpty()
                || event.getCode() == KeyCode.ENTER) {
              autoCompletePopup.hide();
            } else {
              autoCompletePopup.show(editor);
            }
          }
        });
  }

  // create popup message with ok button
  @FXML
  public static void popUpMessage(String title, String message) {
    popUpMessage(title, message, "Ok");
  }

  // use this method to create popup message if you want the button to have different text
  @FXML
  public static void popUpMessage(String title, String message, String acceptButton) {
    Dialog<String> dialog = new Dialog<String>();
    dialog.setTitle(title);
    ButtonType type = new ButtonType(acceptButton, ButtonBar.ButtonData.OK_DONE);
    dialog.setContentText(message);
    dialog.getDialogPane().getButtonTypes().add(type);
    dialog.showAndWait();
  }

  // create push notif with a title, message, and an anchorpane in which this push notif is anchored
  // to
  @FXML
  public static void addPushNotification(String title, String message, AnchorPane anchorPane) {
    JFXDialogLayout content = new JFXDialogLayout();
    content.setHeading(new Text(title));
    content.setBody(new Text(message));
    content.setMaxSize(1, 1);
    StackPane stackPane = new StackPane();
    stackPane.setMaxSize(1, 1);
    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.RIGHT);
    dialog.setMaxSize(1, 1);
    JFXButton button = new JFXButton("Close");
    button.setOnAction(
        (ActionEvent event) -> {
          dialog.close();
        });
    content.setActions(button);
    anchorPane.getChildren().add(stackPane);
    AnchorPane.setTopAnchor(stackPane, 0.0);
    AnchorPane.setRightAnchor(stackPane, 0.0);
    dialog.show();
    PauseTransition delay = new PauseTransition(Duration.seconds(5));
    delay.setOnFinished(event -> dialog.close());
    delay.play();
  }

  @FXML
  public static void addDrawerButtonHover(
      JFXButton button,
      JFXDrawer drawerToOpen,
      Pane drawerPane,
      ArrayList<JFXDrawer> drawersInStack,
      JFXDrawersStack drawerStack) {
    button
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue) {
                try {
                  drawerStack.toFront();
                  openDrawer(drawerToOpen, drawerPane, drawersInStack, drawerStack);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else {
                drawerStack.toggle(drawerToOpen, false);
              }
            });
  }

  @FXML // helper method for addOnButtonHoverListener()
  private static void openDrawer(
      JFXDrawer drawerToOpen,
      Pane drawerPane,
      ArrayList<JFXDrawer> drawersInStack,
      JFXDrawersStack drawerStack)
      throws IOException {
    for (JFXDrawer drawer : drawersInStack) {
      if (drawer.isOpened()) drawerStack.toggle(drawer, false);
    }

    drawerToOpen.setSidePane(drawerPane);
    drawerToOpen.setDefaultDrawerSize(drawerPane.getPrefWidth());
    drawerStack.toggle(drawerToOpen);

    drawerPane
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue) {
                drawerStack.toFront();
                drawerStack.toggle(drawerToOpen, true);
              } else {
                drawerStack.toggle(drawerToOpen, false);
                drawerStack.toBack();
              }
            });
  }
}
