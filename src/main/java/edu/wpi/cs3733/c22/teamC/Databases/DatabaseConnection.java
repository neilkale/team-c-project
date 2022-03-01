/**
 * Programmers: Jack, Neil, Lauren Date: SUN, Jan 30 '22 Description: Will phase out the cdb db
 * connection portion of the databases. Added some methods to execute the query executions so that
 * our code can be cleaner.
 */
package edu.wpi.cs3733.c22.teamC.Databases;

import static edu.wpi.cs3733.c22.teamC.Capp.dbCreation;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// import org.apache.derby.jdbc.*;

public class DatabaseConnection {
  private Connection connection;
  private MongoDatabase mongoDatabase;

  private static ArrayList<String> tableNames;

  public boolean isClientDatabase() {
    return isClientDatabase;
  }

  public static ArrayList<String> getTableNames() {
    return tableNames;
  }

  public static void setTableNames() throws SQLException {
    ArrayList<String> list = new ArrayList<>();
    ResultSet rs =
        (new DatabaseConnection())
            .connection
            .getMetaData()
            .getTables(null, null, null, new String[] {"TABLE"});

    while (rs.next()) {
      String nextTable = (rs.getString("TABLE_NAME"));
      list.add(nextTable);
    }
    tableNames = list;
  }
  /*
  public boolean isMongulDB() {
    return isMongulDB;
  }

  public void setMongulDB(boolean b) {
    isMongulDB = b;
  }*/

  /** databaseType is false if embedded, true if client */
  private boolean isClientDatabase = false;

  // private boolean isMongulDB = false;

  private static DatabaseConnection dbcInstance = new DatabaseConnection();

  public static DatabaseConnection getInstance() {
    return dbcInstance;
  }

  private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
  private String driverCS = "org.apache.derby.jdbc.ClientDriver";

  private String db_url = "jdbc:derby:CDB;create=true";
  private String db_s_c_url = "jdbc:derby://localhost:1527/CDB;create=true";

  // private final String mongulConnection =
  //
  // "mongodb+srv://admin:dDbno11RbFVsXVv3@serverlessinstance0.zitm8.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

  /**
   * This url should be tested because I am unsure of the port type. I know that the current
   * embedded db has to create the initial CDB instance so could this implementation be used in such
   * a way where we declare the embedded db and then switch over tto the new client server db?
   */
  public DatabaseConnection() {
    try {
      mongoDatabase = new MongoDatabase();
    } catch (Exception e) {
      System.out.println("oops no mongo!");
    }
    // this.isMongulDB = false;
    startDbConnection();
  }

  public void startDbConnection() {
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(db_url);
      isClientDatabase = false;
      // isMongulDB = false;
      if (connection != null) {
        System.out.println("Connected to the Embedded DB");
      }

    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /*public void startMongo() {
    mongoEquipment = new MongoEquipment();
    mongoLocation = new MongoLocation();
  }*/

  /**
   * This will connect to the database once signed in and treat the database as a client server db.
   */
  public void startServerClientDbConnection() {
    try {
      Class.forName(driverCS);
      connection = DriverManager.getConnection(db_s_c_url, "admin", "admin");
      isClientDatabase = true;
      // isMongulDB = false;
      if (connection != null) {
        dbCreation();
        System.out.println("Connected to the Client DB");
      }

      // DatabaseCreation locTest = new DatabaseCreation();
      // locTest.
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * This will be used to decide when an employee logging in will have access to the admin embedded
   * db or the client server db.
   *
   * <p>public void setDatabaseType(Employee employee) { if (employee.get_access().equals("admin"))
   * { startDbConnection(); } else { startServerClientDbConnection(); } }
   */

  /** @return null if the connection is invalid. */
  public static Connection getConnection() {
    return dbcInstance.connection;
  }

  /** Standardizes executeQuery to method, so we don't have 100 query names */
  public ResultSet executeQuery(String query) throws SQLException {
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(query);
    return rs;
  }

  public void execute(String query) throws SQLException {
    Statement statement = connection.createStatement();
    statement.execute(query);
  }

  public void executeUpdate(String query) throws SQLException {
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

  public void close() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (mongoDatabase != null) {
      mongoDatabase.closeMongo();
    }
  }

  /*public MongoEquipment getMongoEquipment() {
    return mongoEquipment;
  }

  public MongoLocation getMongoLocation() {
    return mongoLocation;
  }*/
}
