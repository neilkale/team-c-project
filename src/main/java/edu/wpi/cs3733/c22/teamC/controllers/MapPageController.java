package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.Main;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MapPageController extends AbstractController {
  @FXML Pane mapPane;
  @FXML GridPane gridPane;
  @FXML JFXButton backButton;

  Pane mapEditorPane;

  @FXML
  void initialize() throws IOException {
    mapEditorPane =
        FXMLLoader.load(Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/MapEditor.fxml"));
  }

  @FXML
  public void backButtonPressed() throws IOException {
    setNewScene("DefaultPage.fxml", backButton);
  }

  @FXML
  private void mapEditorButtonPressed() {
    gridPane.add(mapEditorPane, 0, 1);
  }
}
