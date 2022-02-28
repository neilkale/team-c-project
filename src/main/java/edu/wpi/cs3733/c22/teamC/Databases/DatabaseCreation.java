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
          "CREATE TABLE TOWERLOCATIONSC( "
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
      // Creates the table with parameters EquipmentRequestC
      String query =
          "CREATE TABLE EQUIPMENTREQUESTC( "
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
      // Creates the table with parametersSecurityRequestC
      String query =
          "CREATE TABLE SECURITYREQUESTC( "
              + "ticketID VARCHAR (255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "assignment VARCHAR(255), "
              + "reportBreach VARCHAR(255), "
              + "securityType VARCHAR(255), "
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
      // Creates the table with parametersMaintenanceRequestC
      String query =
          "CREATE TABLE MAINTENANCEREQUESTC( "
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
          "CREATE TABLE MAPSC( "
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
          "CREATE TABLE SANITATIONREQUESTC( "
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
      // Creates the table with parametersITRequestC
      String query =
          "CREATE TABLE ITREQUESTC( "
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
          "CREATE TABLE EMPLOYEEC( "
              + "username VARCHAR(255), "
              + "password VARCHAR(255), "
              + "firstName VARCHAR(255), "
              + "lastName VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "access VARCHAR(255), "
              + "id VARCHAR(255), "
              + "profilePicture VARCHAR(255)"
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
      // Creates the table with parametersReligiousRequestC
      String query =
          "CREATE TABLE RELIGIOUSREQUESTC("
              + " ticketID VARCHAR(255), "
              + " locationID VARCHAR(255), "
              + " status VARCHAR(255), "
              + " serviceType VARCHAR(255), "
              + " assignment VARCHAR(255), "
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
      // Creates the table with parametersLanguageRequestC
      String query =
          "CREATE TABLE LANGUAGEREQUESTC("
              + " ticketID VARCHAR(255), "
              + " locationID VARCHAR(255), "
              + " status VARCHAR(255), "
              + " serviceType VARCHAR(255), "
              + " assignment VARCHAR(255), "
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
      // Creates the table with parametersGiftRequestC
      String query =
          "CREATE TABLE GIFTREQUESTC( "
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
      // Creates the table with parametersLaundryRequestC
      String query =
          "CREATE TABLE LAUNDRYREQUESTC( "
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
      // Creates the table with parametersInternalTransportRequestC
      String query =
          "CREATE TABLE INTERNALTRANSPORTREQUESTC( "
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
      // Creates the table with parametersMedicineRequestC
      String query =
          "CREATE TABLE MEDICINEREQUESTC( "
              + "ticketID VARCHAR(255), "
              + "locationID VARCHAR(255), "
              + "status VARCHAR(255), "
              + "serviceType VARCHAR(255), "
              + "assignment VARCHAR(255), "
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
      // Creates the table with parametersEquipmentC
      /*
      ID varchar

       */
      String query =
          "CREATE TABLE EQUIPMENTC( "
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
    locationQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/TowerLocationsC.csv");

    Query e = new EmployeeQuery();
    e.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EmployeeC.csv");

    e = new LaundryRequestQuery();
    e.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/LaundryRequestC.csv");

    e = new ReligiousRequestQuery();
    e.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/ReligiousRequestC.csv");

    MedicalEquipmentQuery medicalEquipmentQuery = new MedicalEquipmentQuery();
    medicalEquipmentQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentC.csv");

    EquipmentRequestQuery equipmentRequestQuery = new EquipmentRequestQuery();
    equipmentRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentRequestC.csv");

    MaintenanceRequestQuery maintenanceRequestQuery = new MaintenanceRequestQuery();
    maintenanceRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/MaintenanceRequestC.csv");

    SanitationRequestQuery sanitationRequestQuery = new SanitationRequestQuery();
    sanitationRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/SanitationRequestC.csv");

    ITRequestQuery itRequestQuery = new ITRequestQuery();
    itRequestQuery.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/ITRequestC.csv");

    GiftRequestQuery giftRequestQuery = new GiftRequestQuery();
    giftRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/GiftRequestC.csv");

    InternalTransportRequestQuery internalTransportRequestQuery =
        new InternalTransportRequestQuery();
    internalTransportRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/InternalTransportRequestC.csv");

    LanguageRequestQuery languageRequestQuery = new LanguageRequestQuery();
    languageRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/LanguageRequestC.csv");

    SecurityRequestQuery securityRequestQuery = new SecurityRequestQuery();
    securityRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/SecurityRequestC.csv");

    MedicineRequestQuery medicineRequestQuery = new MedicineRequestQuery();
    medicineRequestQuery.readCSV(
        "src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/MedicineRequestC.csv");

    MapQuery map = new MapQuery();
    map.readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/Map.csv");
  }
}
