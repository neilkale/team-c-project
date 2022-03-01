package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.LocationDaoImpl;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LocationDataController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private TableView tableView;

  @FXML
  public void initialize() {
    LocationDaoImpl e = DaoSingleton.getLocationDao();
    List<Location> locationList = e.getAllNodes();

    TableColumn<Location, String> column1 = new TableColumn<>("Node ID");
    column1.setCellValueFactory(new PropertyValueFactory<>("_nodeID"));

    TableColumn<Location, String> column2 = new TableColumn<>("X Coordinate");
    column2.setCellValueFactory(new PropertyValueFactory<>("_xcoord"));

    TableColumn<Location, String> column3 = new TableColumn<>("Y Coordinate");
    column3.setCellValueFactory(new PropertyValueFactory<>("_ycoord"));

    TableColumn<Location, String> column4 = new TableColumn<>("Floor");
    column4.setCellValueFactory(new PropertyValueFactory<>("_floor"));

    TableColumn<Location, String> column5 = new TableColumn<>("Building");
    column5.setCellValueFactory(new PropertyValueFactory<>("_building"));

    TableColumn<Location, String> column6 = new TableColumn<>("Node Type");
    column6.setCellValueFactory(new PropertyValueFactory<>("_nodeType"));

    TableColumn<Location, String> column7 = new TableColumn<>("Long Name");
    column7.setCellValueFactory(new PropertyValueFactory<>("_longName"));

    TableColumn<Location, String> column8 = new TableColumn<>("Short Name");
    column8.setCellValueFactory(new PropertyValueFactory<>("_shortName"));

    tableView
        .getColumns()
        .addAll(column1, column2, column3, column4, column5, column6, column7, column8);
    for (int i = 1; i < locationList.size(); i++) {
      ObservableList<Location> data =
          FXCollections.observableArrayList(
              new Location(
                  locationList.get(i).get_nodeID(),
                  locationList.get(i).get_xcoord(),
                  locationList.get(i).get_ycoord(),
                  locationList.get(i).get_floor(),
                  locationList.get(i).get_building(),
                  locationList.get(i).get_nodeType(),
                  locationList.get(i).get_longName(),
                  locationList.get(i).get_shortName()));
      tableView.getItems().addAll(data);
    }
  }
}
