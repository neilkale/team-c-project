package edu.wpi.cs3733.c22.teamC.controllers.sideView;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SideViewDashboardController extends AbstractController {
  @FXML AnchorPane anchorPane;

  @FXML PieChart xRayChart;
  @FXML PieChart pumpChart;
  @FXML PieChart bedChart;
  @FXML PieChart reclinerChart;

  private String[] floorNames = {"L2", "L1", "1", "2", "3"};

  private JFXButton previousButton;

  @FXML
  void initialize() throws IOException {
    SideViewTracker.loadDataFromDB();
    populateGraphsDefault();
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

  @FXML
  void seeMoreButtonPressed() throws IOException {
    // get controller of the order tracker page about to be loaded
    FXMLLoader loader = getLoader("SideView.fxml");
    Pane root = loader.load();
    controllerMediator.setDefaultPageCenter(root, "OrderTracker.fxml");
  }
}
