package edu.wpi.cs3733.c22.teamC.controllers.requests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MedicalEquipmentQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MedEquipDataController extends AbstractController {
  @FXML private JFXButton backButton;
  @FXML private TableView tableView;

  @FXML
  public void initialize() throws SQLException {
    MedicalEquipmentQuery allEquipmentValues = new MedicalEquipmentQuery();
    List<MedicalEquipment> equipmentList = allEquipmentValues.getAllNodeData();
    System.out.println(equipmentList);

    TableColumn<MedicalEquipment, String> column1 = new TableColumn<>("Equipment ID");
    column1.setCellValueFactory(new PropertyValueFactory<>("_equipmentID"));

    TableColumn<MedicalEquipment, String> column2 = new TableColumn<>("Last Known X Coordinate");
    column2.setCellValueFactory(new PropertyValueFactory<>("_lastKnownX"));

    TableColumn<MedicalEquipment, String> column3 = new TableColumn<>("Last Known Y Coordinate");
    column3.setCellValueFactory(new PropertyValueFactory<>("_lastKnownY"));

    TableColumn<MedicalEquipment, String> column4 = new TableColumn<>("Last Known Floor");
    column4.setCellValueFactory(new PropertyValueFactory<>("_lastKnownFloor"));

    TableColumn<MedicalEquipment, String> column5 = new TableColumn<>("Last Known Building");
    column5.setCellValueFactory(new PropertyValueFactory<>("_lastKnownBuilding"));

    TableColumn<MedicalEquipment, String> column6 = new TableColumn<>("Last Known Time");
    column6.setCellValueFactory(new PropertyValueFactory<>("_lastKnownTime"));

    TableColumn<MedicalEquipment, String> column7 = new TableColumn<>("Status");
    column7.setCellValueFactory(new PropertyValueFactory<>("_status"));

    TableColumn<MedicalEquipment, String> column8 = new TableColumn<>("Equipment Type");
    column8.setCellValueFactory(new PropertyValueFactory<>("_equipmentType"));

    TableColumn<MedicalEquipment, String> column9 = new TableColumn<>("Name");
    column9.setCellValueFactory(new PropertyValueFactory<>("_name"));

    tableView
        .getColumns()
        .addAll(column1, column2, column3, column4, column5, column6, column7, column8, column9);

    for (int i = 1; i < equipmentList.size(); i++) {
      ObservableList<MedicalEquipment> data =
          FXCollections.observableArrayList(
              new MedicalEquipment(
                  equipmentList.get(i).get_equipmentID(),
                  equipmentList.get(i).get_locationID(),
                  equipmentList.get(i).get_lastKnownTime(),
                  equipmentList.get(i).get_status(),
                  equipmentList.get(i).get_equipmentType(),
                  equipmentList.get(i).get_name()));
      tableView.getItems().addAll(data);
    }
  }
}
