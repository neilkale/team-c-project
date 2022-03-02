/**
 * Programmers: Jack, Neil, Lauren Date: SUN, Jan 30 '22 Description: Will phase out the cdb db
 * connection portion of the databases. Added some methods to execute the query executions so that
 * our code can be cleaner.
 */
package edu.wpi.cs3733.c22.teamC.Databases;

import edu.wpi.cs3733.c22.teamC.SQLMethods.ResultSetParser;
import edu.wpi.cs3733.c22.teamC.SQLMethods.TableFields;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
  private Connection connection;
  private MongoDatabase mongoDatabase;
  private boolean justStartup;
  private boolean isMongo;
  private boolean canMongo;
  private List<String> startupInsert;
  private static ArrayList<String> tableNames;

  private TableFields tableFields;

  public boolean isClientDatabase() {
    return isClientDatabase;
  }

  public void setStartup(boolean b) {
    justStartup = b;
  }

  public boolean isJustStartup() {
    return justStartup;
  }

  public void disableMongo(String cause) {
    System.out.println("MONGOISBEINGDISABLED:  " + cause);
    mongoDatabase.closeMongo();
    mongoDatabase = null;
    isMongo = false;
    canMongo = false;
  }

  public boolean canMongo() {

    return canMongo && (mongoDatabase != null);
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

  /** databaseType is false if embedded, true if client */
  private boolean isClientDatabase = false;

  private static DatabaseConnection dbcInstance = new DatabaseConnection();

  public static DatabaseConnection getInstance() {
    return dbcInstance;
  }

  private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
  private String driverCS = "org.apache.derby.jdbc.ClientDriver";

  private String db_url = "jdbc:derby:CDB;create=true";
  private String db_s_c_url = "jdbc:derby://localhost:1527/CDB;create=true";

  /**
   * This url should be tested because I am unsure of the port type. I know that the current
   * embedded db has to create the initial CDB instance so could this implementation be used in such
   * a way where we declare the embedded db and then switch over tto the new client server db?
   */
  private DatabaseConnection() {
    setStartup(true);
    tableFields = new TableFields();
    startupInsert = new ArrayList<>();
    try {
      mongoDatabase = new MongoDatabase();
      canMongo = true;
      setMongo(false);
    } catch (Exception e) {
      System.out.println("oops no mongo!");
      disableMongo("Failed on Startup");
    }
    startDbConnection();
  }

  public void startDbConnection() {
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(db_url);
      isClientDatabase = false;
      setMongo(false);
      if (connection != null) {
        System.out.println("Connected to the Embedded DB");
      }

    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * This will connect to the database once signed in and treat the database as a client server db.
   */
  public void startServerClientDbConnection() {
    try {
      Class.forName(driverCS);
      connection = DriverManager.getConnection(db_s_c_url);
      isClientDatabase = true;
      setMongo(false);
      if (connection != null) {
        startDbConnection();
        System.out.println("Connected to the Client DB");
      }

    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void setMongo(boolean b) {
    isMongo = b && canMongo();
  }

  public boolean isMongo() {
    return canMongo() && isMongo && (mongoDatabase != null);
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
  public ArrayList<? extends Object> executeQuery(String query) throws SQLException {
    System.out.println(query);
    if (isMongo()) {
      if (query.contains("*")) {
        ArrayList<DatabaseInterface> toReturn =
            (ArrayList<DatabaseInterface>) mongoDatabase.select(query);
        return toReturn;
      } else {
        ArrayList<String> toReturn = (ArrayList<String>) mongoDatabase.select(query);
        return toReturn;
      }
    } else {
      if (query.contains("*")) {
        ArrayList<DatabaseInterface> toReturn =
            ResultSetParser.resultDatabaseParser(sqlExecuteQuery(query), query);
        return toReturn;
      } else {
        ArrayList<String> toReturn =
            ResultSetParser.resultStringParser(sqlExecuteQuery(query), query);
        return toReturn;
      }
    }
  }

  private ResultSet sqlExecuteQuery(String query) throws SQLException {
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(query);
    return rs;
  }

  public void execute(String query) throws SQLException {
    System.out.println(query);
    // If Database can handle Mongo

    if (justStartup) {
      if (query.contains("CREATE TABLE")) {
        tableFields.addToMap(query);
      }
      try {
        if (canMongo()) {
          // If Mongo is instanced, and is just starting up
          if (query.substring(0, query.indexOf(' ')).equals("INSERT")) {
            // This just waits to add everything when doing batch writes to mono
            startupInsert.add(query);
          } else {
            if (startupInsert.size() > 0) {
              mongoDatabase.batchInsert(startupInsert);
            }
            startupInsert = new ArrayList<>();
            mongoDatabase.getAction(query);
            Statement statement = connection.createStatement();
            if (query.contains("UPDATE")) {
              statement.executeUpdate(query);
            } else {
              statement.execute(query);
            }
          }
        }
      } catch (SQLException e) {

      } catch (Exception f) {
        disableMongo("Mongo Failed on initilize");
      }

    } else if (canMongo()) {
      mongoDatabase.insert(query);
    }

    if (!canMongo()) {
      Statement statement = connection.createStatement();
      if (query.contains("UPDATE")) {
        statement.executeUpdate(query);
      } else {
        statement.execute(query);
      }
    }
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

  public List<String> getFieldsFromTable(String table) {
    return tableFields.tableToFields(table);
  }
}
