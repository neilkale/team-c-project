package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Capp;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class DatabaseAllController extends AbstractController {
  private ObservableList<ObservableList> data;
  @FXML private JFXButton backButton;
  @FXML private TableView tableView;
  @FXML private JFXComboBox comboBox;
  @FXML private JFXButton refreshButton;
  @FXML private JFXButton switchDBButton;
  @FXML private JFXButton btnToMongo;

  private String queryType;
  DatabaseConnection dbConnection;

  @FXML
  public void initialize() {
    switchDBButton.setText("Switch to Client-Server Database");

    queryType = (new LocationQuery()).getQueryInput();
    dbConnection = DatabaseConnection.getInstance();
    try {
      ObservableList<String> list = FXCollections.observableArrayList();
      ResultSet rs =
          dbConnection
              .getConnection()
              .getMetaData()
              .getTables(null, null, null, new String[] {"TABLE"});

      while (rs.next()) {
        String nextTable = (rs.getString("TABLE_NAME"));
        list.add(nextTable);
      }
      comboBox.getItems().setAll(list);

    } catch (Exception e) {
      e.printStackTrace();
    }

    LocationQuery locationQuery = new LocationQuery();
    setTable(locationQuery.getQueryInput());

    ControllerUtil.addAutoCompleteListener(comboBox);
  }

  @FXML
  private void writeButtonPressed() throws IOException {
    writeCSV();
  }

  @FXML
  private void readButtonPressed() throws IOException {
    FileDialog fd = new FileDialog(new JFrame());
    fd.setVisible(true);
    File[] f = fd.getFiles();
    if (f.length > 0) {
      // System.out.println(fd.getFiles()[0].getAbsolutePath());
    }
    readCSV(fd.getFiles()[0].getAbsolutePath());
  }

  public boolean readCSV(String fileIn) {
    return Query.readCSV(fileIn, queryType);
  }

  public boolean writeCSV() {
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
    String dateString = date.format(dateFormat);
    String resource = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    // System.out.println(resource.toString());
    String fileIn =
        resource.toString()
            + "/MANUALBACKUP-"
            + queryType
            + "-"
            + dateString
            + "-"
            + ".csv"; // fileName format is queryInput-Day-Month-Year-Seconds
    Query.writeCSV(fileIn, queryType);
    return true;
  }

  @FXML
  private void comboChanged() throws IOException {
    queryType = ((String) comboBox.getValue()).toUpperCase();
    // System.out.println(queryType);
    setTable(queryType);
  }

  @FXML
  private void refreshPressed() throws IOException {
    setTable(queryType);
  }

  @FXML
  private void switchDBButtonPressed() {
    //    Capp.dbSave();
    if (dbConnection.isClientDatabase()) {
      // This creates the switch in db
      dbConnection.startDbConnection();
      // This creates the new tables for the db
      Capp test = new Capp();
      test.dbCreation();

      switchDBButton.setText("Switch to Embedded Database");
    } else if (!dbConnection.isClientDatabase()) {
      dbConnection.startServerClientDbConnection();
      switchDBButton.setText("Switch to Client-Server Database");
    }
    //  Capp.dbCreation();
  }

  @FXML
  private void toMongo() {
    //    Capp.dbSave();
    if (dbConnection.isMongulDB()) {
      // This creates the switch in db
      dbConnection.startDbConnection();
      // This creates the new tables for the db
      Capp test = new Capp();
      test.dbCreation();

      btnToMongo.setText("Switch to Embedded Database");
    } else if (!dbConnection.isMongulDB()) {
      // dbConnection.start();
      dbConnection.setMongulDB(true);
      btnToMongo.setText("Switch to NoSQL");
    }
    //  Capp.dbCreation();
  }

  private void setTable(String queryType) {
    // List<Location> locationList = locationQuery.getAllNodeData();
    dbConnection = DatabaseConnection.getInstance();
    tableView.getItems().clear();
    tableView.getColumns().clear();

    data = FXCollections.observableArrayList();
    try {
      String query = "SELECT * FROM " + queryType.toUpperCase();
      ResultSet rs = dbConnection.executeQuery(query);
      int columnNum = rs.getMetaData().getColumnCount();

      for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        // We are using non property style for making dynamic table
        final int j = i;
        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(
            new Callback<
                TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
              public ObservableValue<String> call(
                  TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(j).toString());
              }
            });

        tableView.getColumns().addAll(col);
        // System.out.println("Column [" + i + "] ");
      }
      while (rs.next()) {
        // Iterate Row
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
          // Iterate Column
          row.add(rs.getString(i));
        }
        // System.out.println("Row [1] added " + row);
        data.add(row);
      }

      // FINALLY ADDED TO TableView
      tableView.setItems(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
