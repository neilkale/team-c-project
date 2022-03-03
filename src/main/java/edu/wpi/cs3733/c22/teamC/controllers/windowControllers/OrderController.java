package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class OrderController {
  @FXML private FlowPane flowPane;
  @FXML private AnchorPane orderAnchorPane;
  @FXML private Label Banner;

  @FXML
  public void initialize() {
    AnchorPane.setTopAnchor(flowPane, 0.0);
    AnchorPane.setBottomAnchor(flowPane, 0.0);
    AnchorPane.setLeftAnchor(flowPane, 0.0);
    AnchorPane.setRightAnchor(flowPane, 0.0);

    flowPane.prefWidthProperty().bind(orderAnchorPane.widthProperty());
  }

  public FlowPane getFlowPane() {
    return flowPane;
  }

  public void addNode(Node node) {
    flowPane.getChildren().add(node);
  }

  public void setTitleLabelText(String titleText) {
    Banner.setText(titleText);
  }

  // set all textFields of this order pane to editable
  // this code is not ideal b/c of relying on Order.fxml's html structure staying constant, but
  // works for now
  public void setTextFieldsEditable(Boolean isEditable) {
    flowPane
        .getChildren()
        .forEach( // iterate through flowpane children, which we know are all VBoxes
            flowPaneChild -> {
              if (flowPaneChild instanceof VBox) {
                ((VBox) flowPaneChild)
                    .getChildren()
                    .forEach(
                        child -> {
                          if (child instanceof TextField)
                            ((TextField) child).setEditable(isEditable);
                        });
              }
            });
  }

  // get all textField values
  // this code is not ideal b/c of relying on Order.fxml's html structure staying constant, but
  // works for now
  public ArrayList<String> getAllFieldValues() {
    ArrayList<String> fieldValuesList = new ArrayList<String>();
    flowPane
        .getChildren()
        .forEach( // iterate through flowpane children, which we know are all VBoxes
            flowPaneChild -> {

              // id is direct child of flowpane and has id, add it to list
              if (flowPaneChild instanceof Label) {
                String fieldValue = ((Label) flowPaneChild).getText().split(":")[1].trim();
                fieldValuesList.add(fieldValue);
              } else if (flowPaneChild instanceof VBox) {
                // iterate through VBox children to find textfields with service request values
                ((VBox) flowPaneChild)
                    .getChildren()
                    .forEach(
                        vBoxChild -> {
                          if (vBoxChild instanceof TextField)
                            fieldValuesList.add(
                                ((TextField) vBoxChild).getText()); // add field values to list
                        });
              }
            });
    return fieldValuesList;
  }

  // search through field name labels of an order and find the index of location name; this index
  // also indicates
  // where the fieldValue for location longName is stored
  public int getLocationFieldIndex() {
    for (int i = 0; i < flowPane.getChildren().size(); i++) {
      // iterate through flowpane children, which we know are all VBoxes
      Node flowPaneChild = flowPane.getChildren().get(i);
      if (flowPaneChild instanceof VBox) {
        VBox vBox = (VBox) flowPaneChild;
        // iterate through VBox children to find label with location field name
        for (int j = 0; j < vBox.getChildren().size(); j++) {
          Node vBoxChild = vBox.getChildren().get(j);
          if (vBoxChild instanceof Label) {
            Label label = (Label) vBoxChild;
            if (label.getText().contains("location")) return i;
          }
        }
      }
    }
    return 0;
  }
}
