package edu.wpi.cs3733.c22.teamC;

import edu.wpi.cs3733.c22.teamC.Databases.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.*;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Capp extends Application {
  private static final boolean REFRESHFROMCSV = true;

  @Override
  public void init() {
    // log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws SQLException {
    try {
      DatabaseConnection.getInstance().setStartup(true);
      dbCreation();
      DatabaseConnection.getInstance().setStartup(false);
      ImageLoader imageLoader = new ImageLoader();
      FXMLLoader loader = new FXMLLoader();
      Parent root =
          loader.load(Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/SignInPage.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setMinWidth(800);
      primaryStage.setMinHeight(660);
      primaryStage.setTitle("Team Cyan Cyclops App");
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void dbCreation() {
    try {
      DatabaseConnection dbc = DatabaseConnection.getInstance();
      ResultSet resultSet =
          dbc.getConnection().getMetaData().getTables(null, null, null, new String[] {"TABLE"});
      int i = 0;
      while (resultSet.next()) {
        System.out.println(resultSet.getString("TABLE_NAME") + " FOUND");
        i++;
      }
      // Creates the tables in the db
      DatabaseCreation.makeLocationTable();
      DatabaseCreation.makeEquipmentTable();
      DatabaseCreation.makeEquipmentRequestTable();
      DatabaseCreation.makeMaintenanceRequestTable();
      DatabaseCreation.makeSanitationTable();
      DatabaseCreation.makeITTable();
      DatabaseCreation.makeMapTable();
      DatabaseCreation.makeEmployeeTable();
      DatabaseCreation.makeGiftTable();
      DatabaseCreation.makeInternalTransportTable();
      DatabaseCreation.makeMedicineTable();
      DatabaseCreation.makeLaundryRequestTable();
      DatabaseCreation.makeReligionTable();
      DatabaseCreation.makeLanguageTable();
      DatabaseCreation.makeSecurityRequestTable();

      DatabaseCreation.readDatabasesFromCsv();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    // log.info("Shutting Down");
    dbSave();
    DatabaseConnection.getInstance().close();
  }

  // Implements persistent storage of databases when app is closed
  public static void dbSave() {
    LocationQuery locationQuery = new LocationQuery();
    locationQuery.writeCSV();

    MedicalEquipmentQuery medicalEquipmentQuery = new MedicalEquipmentQuery();
    medicalEquipmentQuery.writeCSV();

    SanitationRequestQuery sanitationRequestQuery = new SanitationRequestQuery();
    sanitationRequestQuery.writeCSV();

    MapQuery mapQuery = new MapQuery();
    mapQuery.writeCSV();

    EquipmentRequestQuery equipmentRequestQuery = new EquipmentRequestQuery();
    equipmentRequestQuery.writeCSV();

    ITRequestQuery itRequestQuery = new ITRequestQuery();
    itRequestQuery.writeCSV();

    MaintenanceRequestQuery maintenanceRequestQuery = new MaintenanceRequestQuery();
    maintenanceRequestQuery.writeCSV();

    Query e;
    e = new LaundryRequestQuery();
    e.writeCSV();

    e = new ReligiousRequestQuery();
    e.writeCSV();

    e = new EmployeeQuery();
    e.writeCSV();

    e = new GiftRequestQuery();
    e.writeCSV();

    e = new InternalTransportRequestQuery();
    e.writeCSV();

    e = new MedicineRequestQuery();
    e.writeCSV();
  }
}
