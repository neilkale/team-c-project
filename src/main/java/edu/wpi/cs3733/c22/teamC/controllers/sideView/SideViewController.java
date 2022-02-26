package edu.wpi.cs3733.c22.teamC.controllers.sideView;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.Main;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.mapEditor.MapState;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SideViewController extends AbstractController {
  @FXML AnchorPane anchorPane;

  @FXML GridPane towerGrid;

  @FXML JFXButton f3Button;
  @FXML JFXButton f2Button;
  @FXML JFXButton f1Button;
  @FXML JFXButton l1Button;
  @FXML JFXButton l2Button;

  private JFXButton previousButton;

  @FXML
  void initialize() throws IOException {
    EquipmentCountTracker.loadDataFromDB();

    GridPane grid;

    MapState.setCurrentFloor("L2");

    l2Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("L1");

    l1Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("1");

    f1Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("2");

    f2Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("3");

    f3Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    previousButton = f3Button;
  }

  @FXML
  void f3ButtonPressed() throws IOException {
    previousButton.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));
    MapState.setCurrentFloor("3");
    f3Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewSelected.fxml")));
    previousButton = f3Button;
  }

  @FXML
  void f2ButtonPressed() throws IOException {
    previousButton.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));
    MapState.setCurrentFloor("2");
    f2Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewSelected.fxml")));
    previousButton = f2Button;
  }

  @FXML
  void f1ButtonPressed() throws IOException {
    previousButton.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));
    MapState.setCurrentFloor("1");
    f1Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewSelected.fxml")));
    previousButton = f1Button;
  }

  @FXML
  void l1ButtonPressed() throws IOException {
    previousButton.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));
    MapState.setCurrentFloor("L1");
    l1Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewSelected.fxml")));
    previousButton = l1Button;
  }

  @FXML
  void l2ButtonPressed() throws IOException {
    previousButton.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));
    MapState.setCurrentFloor("L2");
    l2Button.setGraphic(
        FXMLLoader.load(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewSelected.fxml")));
    previousButton = l2Button;
  }
}
