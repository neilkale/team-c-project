package edu.wpi.cs3733.c22.teamC.Databases;

import edu.wpi.cs3733.c22.teamC.SQLMethods.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.*;
import java.sql.SQLException;

public class DatabaseCreation {
  public static void makeLocationTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE TowerLocationsC( "
              + "nodeID VARCHAR(255), "
              + "xcoord VARCHAR(255), "
              + "ycoord VARCHAR(255), "
              + "floor VARCHAR(255), "
              + "building VARCHAR(255), "
              + "nodeType VARCHAR(255), "
              + "longName VARCHAR(255), "
              + "shortName VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "TOWERLOCATIONS already exists," does nothing
      if (e.getSQLState().equals("X0Y32")) {
        return; // do nothing
      }
      throw e;
    }
  }

  public static void makeEquipmentRequestTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE EquipmentRequestC( "
              + "ticketID VARCHAR(255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR (255), "
              + "assignment VARCHAR(255), "
              + "urgency VARCHAR(255), "
              + "equipmentID VARCHAR(255), "
              + "pickupLocationID VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeSecurityRequestTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE SecurityRequestC( "
              + "ticketID VARCHAR (255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR(255),"
              + "assignment VARCHAR(255), "
              + "reportBreach VARCHAR(255), "
              + "securityType VARCHAR(255),"
              + "urgency VARCHAR(255))";

      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeMaintenanceRequestTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE MaintenanceRequestC( "
              + "ticketID VARCHAR(255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "assignment VARCHAR(255), "
              + "issueType VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeMapTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE Maps( "
              + "buildingName VARCHAR(255), "
              + "floorName VARCHAR(255), "
              + "imagePath VARCHAR(255), "
              + "floorNumber VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeSanitationTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE SanitationRequestC( "
              + " ticketID VARCHAR(255), "
              + " locationID VARCHAR(255), "
              + " status VARCHAR(255), "
              + " serviceType VARCHAR(255), "
              + " assignment VARCHAR(255), "
              + " messType VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeITTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE ITRequestC( "
              + "ticketID VARCHAR(255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "assignment VARCHAR(255), "
              + "issueType VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeEmployeeTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE EmployeeC( "
              + "username VARCHAR(255), "
              + "password VARCHAR(255), "
              + "firstName VARCHAR(255), "
              + "lastName VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "access VARCHAR(255),"
              + "id VARCHAR(255)"
              + ")";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeReligionTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE ReligiousRequestC("
              + " ticketID VARCHAR(255), "
              + " locationID VARCHAR(255), "
              + " status VARCHAR(255), "
              + " serviceType VARCHAR(255), "
              + " assignment VARCHAR(255),"
              + " religion VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeLanguageTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE LanguageRequestC("
              + " ticketID VARCHAR(255), "
              + " locationID VARCHAR(255), "
              + " status VARCHAR(255), "
              + " serviceType VARCHAR(255), "
              + " assignment VARCHAR(255),"
              + " language VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeGiftTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE GiftRequestC( "
              + " ticketID VARCHAR(255), "
              + " locationID VARCHAR(255), "
              + " status VARCHAR(255), "
              + " serviceType VARCHAR(255), "
              + " assignment VARCHAR(255), "
              + " giftType VARCHAR(255))";

      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeLaundryRequestTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE LaundryRequestC( "
              + " ticketID VARCHAR(255), "
              + " locationID VARCHAR(255), "
              + " status VARCHAR(255), "
              + " serviceType VARCHAR(255), "
              + " assignment VARCHAR(255))";

      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeInternalTransportTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE InternalTransportRequestC( "
              + "ticketID VARCHAR(255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "assignment VARCHAR(255), "
              + "dropOff VARCHAR(255), "
              + "urgency VARCHAR(255)) ";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeMedicineTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      String query =
          "CREATE TABLE MedicineRequestC( "
              + "ticketID VARCHAR(255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "assignment VARCHAR(255),"
              + "medicineType VARCHAR(255), "
              + "quantity VARCHAR(255), "
              + "urgency VARCHAR(255)) ";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing\
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void makeEquipmentTable() throws SQLException {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    try {
      // Creates the table with parameters
      /*
      ID varchar

       */
      String query =
          "CREATE TABLE EquipmentC( "
              + "equipmentID VARCHAR(255), "
              + "locationID VARCHAR(255), "
              + "lastKnownTime VARCHAR(255), "
              + "status VARCHAR(255), "
              + "equipmentType VARCHAR(255), "
              + "name VARCHAR(255))";
      dbConnection.execute(query);
    } catch (SQLException e) {
      // if "ServiceRequestC already exists," does nothing
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      throw e;
    }
  }

  public static void readDatabasesFromCsv() {
    LocationQuery locationQuery = new LocationQuery();
    locationQuery.clearTable();
    locationQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/TowerLocationsC.csv");

    Query e = new EmployeeQuery();
    e.clearTable();
    e.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EmployeeC.csv");

    e = new LaundryRequestQuery();
    e.clearTable();
    e.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/LaundryRequestC.csv");

    e = new ReligiousRequestQuery();
    e.clearTable();
    e.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/ReligiousRequestC.csv");

    MedicalEquipmentQuery medicalEquipmentQuery = new MedicalEquipmentQuery();
    medicalEquipmentQuery.clearTable();
    medicalEquipmentQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentC.csv");

    EquipmentRequestQuery equipmentRequestQuery = new EquipmentRequestQuery();
    equipmentRequestQuery.clearTable();
    equipmentRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentRequestC.csv");

    MaintenanceRequestQuery maintenanceRequestQuery = new MaintenanceRequestQuery();
    maintenanceRequestQuery.clearTable();
    maintenanceRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/MaintenanceRequestC.csv");

    SanitationRequestQuery sanitationRequestQuery = new SanitationRequestQuery();
    sanitationRequestQuery.clearTable();
    sanitationRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/SanitationRequestC.csv");

    ITRequestQuery itRequestQuery = new ITRequestQuery();
    itRequestQuery.clearTable();
    itRequestQuery.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/ITRequestC.csv");

    GiftRequestQuery giftRequestQuery = new GiftRequestQuery();
    giftRequestQuery.clearTable();
    giftRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/GiftRequestC.csv");

    InternalTransportRequestQuery internalTransportRequestQuery =
        new InternalTransportRequestQuery();
    internalTransportRequestQuery.clearTable();
    internalTransportRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/InternalTransportRequestC.csv");

    LanguageRequestQuery languageRequestQuery = new LanguageRequestQuery();
    languageRequestQuery.clearTable();
    languageRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/LanguageRequestC.csv");

    SecurityRequestQuery securityRequestQuery = new SecurityRequestQuery();
    securityRequestQuery.clearTable();
    securityRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/SecurityRequestC.csv");

    MedicineRequestQuery medicineRequestQuery = new MedicineRequestQuery();
    medicineRequestQuery.clearTable();
    medicineRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/MedicineRequestC.csv");

    MapQuery map = new MapQuery();
    map.clearTable();
    map.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/Map.csv");
  }
}
