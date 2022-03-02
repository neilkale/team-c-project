package edu.wpi.cs3733.c22.teamC.controllers.sideView;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.MainFinal;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.mapEditor.MapState;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
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

  @FXML PieChart xRayChart;
  @FXML PieChart pumpChart;
  @FXML PieChart bedChart;
  @FXML PieChart reclinerChart;

  private String[] floorNames = {"L2", "L1", "1", "2", "3"};

  private JFXButton previousButton;

  @FXML
  void initialize() throws IOException {
    SideViewTracker.loadDataFromDB();

    MapState.setCurrentFloor("L2");

    l2Button.setGraphic(
        FXMLLoader.load(
            MainFinal.class.getResource(
                "/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("L1");

    l1Button.setGraphic(
        FXMLLoader.load(
            MainFinal.class.getResource(
                "/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("1");

    f1Button.setGraphic(
        FXMLLoader.load(
            MainFinal.class.getResource(
                "/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("2");

    f2Button.setGraphic(
        FXMLLoader.load(
            MainFinal.class.getResource(
                "/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

    MapState.setCurrentFloor("3");

    f3Button.setGraphic(
        FXMLLoader.load(
            MainFinal.class.getResource(
                "/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));
    populateGraphsDefault();
  }

  @FXML
  void f3ButtonPressed() throws IOException {
    switchToFloor("3", f3Button);
  }

  @FXML
  void f2ButtonPressed() throws IOException {
    switchToFloor("2", f2Button);
  }

  @FXML
  void f1ButtonPressed() throws IOException {

    switchToFloor("1", f1Button);
  }

  @FXML
  void l1ButtonPressed() throws IOException {
    switchToFloor("L1", l1Button);
  }

  @FXML
  void l2ButtonPressed() throws IOException {
    switchToFloor("L2", l2Button);
  }

  private void switchToFloor(String floor, JFXButton button) throws IOException {
    if (previousButton != null) {
      previousButton.setGraphic(
          FXMLLoader.load(
              MainFinal.class.getResource(
                  "/edu/wpi/cs3733.c22.teamC/Views/SideViewDeselected.fxml")));

      if (previousButton.equals(button)) {
        previousButton = null;
        populateGraphsDefault();
        return;
      }
    }
    MapState.setCurrentFloor(floor);
    populateGraphs(SideViewTracker.getEquipmentCount(floor));
    button.setGraphic(
        FXMLLoader.load(
            MainFinal.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SideViewSelected.fxml")));
    previousButton = button;
  }

  private void populateGraphsDefault() {
    Integer[] count = {0, 0, 0, 0, 0, 0, 0, 0};
    for (String floor : floorNames) {
      Integer[] floorEquipCount = SideViewTracker.getEquipmentCount(floor);
      for (int i = 0; i < count.length; i++) {
        count[i] += floorEquipCount[i];
      }
    }
    populateGraphs(count);
  }

  private void populateGraphs(Integer[] count) {

    xRayChart.getData().clear();
    pumpChart.getData().clear();
    bedChart.getData().clear();
    reclinerChart.getData().clear();

    xRayChart.getData().add(new PieChart.Data("Clean", count[0]));
    xRayChart.getData().add(new PieChart.Data("Dirty", count[4]));

    pumpChart.getData().add(new PieChart.Data("Clean", count[1]));
    pumpChart.getData().add(new PieChart.Data("Dirty", count[5]));

    bedChart.getData().add(new PieChart.Data("Clean", count[2]));
    bedChart.getData().add(new PieChart.Data("Dirty", count[6]));

    reclinerChart.getData().add(new PieChart.Data("Clean", count[3]));
    reclinerChart.getData().add(new PieChart.Data("Dirty", count[7]));
  }
}
