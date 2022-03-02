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
              + "profilePicture VARCHAR(255), "
              + "phoneNumber VARCHAR(255), "
              + "email VARCHAR(255)"
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
              + "urgency VARCHAR(255))";
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
    String prefix = "";
    Query e = new LocationQuery();
    e.readCSV(prefix + "TowerLocationsC.csv");

    e = new EmployeeQuery();
    e.readCSV(prefix + "EmployeeC.csv");

    e = new LaundryRequestQuery();
    e.readCSV(prefix + "LaundryRequestC.csv");

    e = new ReligiousRequestQuery();
    e.readCSV(prefix + "ReligiousRequestC.csv");

    e = new MedicalEquipmentQuery();
    e.readCSV(prefix + "EquipmentC.csv");

    e = new EquipmentRequestQuery();
    e.readCSV(prefix + "EquipmentRequestC.csv");

    e = new MaintenanceRequestQuery();
    e.readCSV(prefix + "MaintenanceRequestC.csv");

    e = new SanitationRequestQuery();
    e.readCSV(prefix + "SanitationRequestC.csv");

    e = new ITRequestQuery();
    e.readCSV(prefix + "CSV_Files/ITRequestC.csv");

    e = new GiftRequestQuery();
    e.readCSV(prefix + "CSV_Files/GiftRequestC.csv");

    e = new InternalTransportRequestQuery();
    e.readCSV(prefix + "InternalTransportRequestC.csv");

    e = new LanguageRequestQuery();
    e.readCSV(prefix + "LanguageRequestC.csv");

    e = new SecurityRequestQuery();
    e.readCSV(prefix + "SecurityRequestC.csv");

    e = new MedicineRequestQuery();
    e.readCSV(prefix + "MedicineRequestC.csv");

    e = new MapQuery();
    e.readCSV(prefix + "Map.csv");
  }
}
