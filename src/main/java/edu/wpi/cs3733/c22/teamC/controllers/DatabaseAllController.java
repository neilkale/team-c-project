package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Capp;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
  @FXML private TableView tableView;
  @FXML private JFXComboBox comboBox;
  @FXML private JFXButton refreshButton;
  @FXML private JFXButton switchDBButton;
  @FXML private JFXButton btnToMongo;

  private String queryType;
  DatabaseConnection dbConnection;

  @FXML
  public void initialize() {
    dbConnection = DatabaseConnection.getInstance();
    switchDBButton.setText("Switch to Client-Server Database");
    if (dbConnection.isMongo()) {
      btnToMongo.setText("Switch to Embedded Database");
    } else {
      btnToMongo.setText("Switch to NoSQL");
    }
    queryType = (new LocationQuery()).getQueryInput();
    try {
      ObservableList<String> list =
          FXCollections.observableArrayList(DatabaseConnection.getTableNames());
      comboBox.getItems().setAll(list);

    } catch (Exception e) {
      e.printStackTrace();
    }

    LocationQuery locationQuery = new LocationQuery();
    setTable(locationQuery.getQueryInput());
    comboBox.setPromptText("Database Table");
    comboBox.setAccessibleText(locationQuery.getQueryInput());

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
    if (fd.getFiles().length != 0) readCSV(fd.getFiles()[0].getAbsolutePath());
    else System.out.println("File not selected");
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
    if (dbConnection.isMongo()) {
      dbConnection.startDbConnection();
      dbConnection.setMongo(false);
      btnToMongo.setText("Switch to NoSQL");
      System.out.println("Allegedly switched to embedded");
    } else if (!dbConnection.isMongo()) {
      System.out.println("Allegedly switched to mongo");
      dbConnection.setMongo(true);
      btnToMongo.setText("Switch to Embedded Database");
    }
    //  Capp.dbCreation();
  }

  private void setTable(String queryType) {
    // List<Location> locationList = locationQuery.getAllNodeData();
    dbConnection = DatabaseConnection.getInstance();
    tableView.getItems().clear();
    tableView.getColumns().clear();

    data = FXCollections.observableArrayList();

    if (!dbConnection.isMongo()) {
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
    } else {
      String query = "SELECT * FROM " + queryType.toUpperCase();
      List<DatabaseInterface> fromMongo =
          (List<DatabaseInterface>) dbConnection.getFromMongo(query);
      int i = 0;
      for (String s : dbConnection.fieldsFromMongo(queryType.toUpperCase())) {
        final int j = i;
        TableColumn col = new TableColumn(s);
        col.setSortable(true);
        col.setCellValueFactory(
            new Callback<
                TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
              public ObservableValue<String> call(
                  TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(j).toString());
              }
            });
        tableView.getColumns().add(col);
        i++;
      }

      for (DatabaseInterface d : fromMongo) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (String s : d.getValues()) {
          row.add(s);
        }
        data.add(row);
      }
      tableView.setItems(data);
    }
  }
}
