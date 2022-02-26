package edu.wpi.cs3733.c22.teamC.SQLMethods;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SecurityRequestQuery;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.filechooser.FileSystemView;

public abstract class Query<T> {
  public static final boolean VERBOSE = false;

  // protected String queryInput; // The string of the table the query would access

  protected DatabaseConnection dbConnection = DatabaseConnection.getInstance();

  public abstract T queryFactory(String[] inputs);

  public static Object factoryGeneric(
      String table,
      String[]
          inputs) { // TYPE IS TABLE, inputs are the inputs for the T queryFactory method for the
    // respective typeQuery

    switch (table.toUpperCase()) {
      case "EMPLOYEEC":
        return new EmployeeQuery().queryFactory(inputs);

      case "TOWERLOCATIONS":
        return new LocationQuery().queryFactory(inputs);

      case "EQUIPMENTREQUESTC":
        return new EquipmentRequestQuery().queryFactory(inputs);

      case "MAINTENANCEREQUESTC":
        return new MaintenanceRequestQuery().queryFactory(inputs);

      case "MAPS":
        return new MapQuery().queryFactory(inputs);

      case "SANITATIONREQUESTC":
        return new SanitationRequestQuery().queryFactory(inputs);

      case "ITREQUESTC":
        return new ITRequestQuery().queryFactory(inputs);

      case "RELIGIOUSREQUESTC":
        return new ReligiousRequestQuery().queryFactory(inputs);

      case "LANGUAGEREQUESTC":
        return new LanguageRequestQuery().queryFactory(inputs);

      case "GIFTREQUESTC":
        return new GiftRequestQuery().queryFactory(inputs);

      case "LAUNDRYREQUESTC":
        return new LaundryRequestQuery().queryFactory(inputs);

      case "INTERNALTRANSPORTREQUESTC":
        return new InternalTransportRequestQuery().queryFactory(inputs);

      case "MEDICINEREQUESTC":
        return new MedicineRequestQuery().queryFactory(inputs);

      case "SECURITYREQUESTC":
        return new SecurityRequestQuery().queryFactory(inputs);

      case "EQUIPMENTC":
        return new MedicalEquipmentQuery().queryFactory(inputs);
    }
    System.out.println(
        "[QUERY: factoryGeneric no such table " + table + " that factoryGeneric handles");
    return null;
  }

  public abstract void addNode(T object) throws SQLException;

  public boolean addNodeGeneric(String[] fields) throws SQLException {
    try {
      ResultSetMetaData rs =
          dbConnection.executeQuery("select * from" + getQueryInput()).getMetaData();
      int columns = rs.getColumnCount();
      if (fields.length != columns) {
        if (VERBOSE)
          System.out.println(
              "Improper amount of arguments entered for addNodeGeneric using queryType"
                  + getQueryInput());
        return false;
      }
      StringBuilder query = new StringBuilder();
      query.append("INSERT INTO ");
      query.append(getQueryInput());
      query.append(" VALUES ('");
      for (int i = 0; i < columns; i++) {
        query.append(fields[i]);
      }
      query.append("')");
      dbConnection.execute(query.toString());
    } catch (SQLException e) {
      if (VERBOSE) System.out.println(e);
      return false;
    }
    return true;
  }

  public abstract void removeNode(T object) throws SQLException;

  public abstract void editNode(T object) throws SQLException;

  public void modifyFromList(ArrayList<String> modificationTypes, ArrayList<T> nodes)
      throws Exception {
    if (modificationTypes.size() != nodes.size()) {
      System.out.println(
          "[modifyFromList:Query.java]: Error in arguments - input arguments modificationTypes and locations are not the same size - Throwing Exception");
      throw new Exception();
    }

    try {
      for (int i = 0; i < modificationTypes.size(); i++) {
        String modType = modificationTypes.get(i);
        T node = nodes.get(i);
        if (modType.toUpperCase().equals("REMOVE")) {
          removeNode(node);
        }
        if (modType.toUpperCase().equals("EDIT")) {
          editNode(node);
        }
        if (modType.toUpperCase().equals("ADD")) {}
        addNode(node);
      }
    } catch (SQLException e) {
      System.out.println(
          "[modifyFromList:Query.java]: Error occurred during query execution - Throwing SQLException");
      e.printStackTrace();
    }
  }

  //  public boolean compareAndChange(ArrayList<T> list, String UID) throws Exception {
  //    try {
  //      ArrayList<T> listDatabaseNow = getAllNodeData();
  //      for (T each : listDatabaseNow) {
  //
  //        boolean found = false;
  //        for (T eacher : list) {
  //          if (!listDatabaseNow.contains(eacher)) {
  //            if (!found) {
  //              //            System.out.println("Each :" + getUID(each));
  //              //            System.out.println("Eacher :" + getUID(eacher));
  //              if (getUID(each).equals(getUID(eacher))) {
  //                System.out.println("FOUND  " + getUID(each));
  //                found = true;
  //                removeNode(each);
  //                addNode(eacher);
  //              }
  //            }
  //
  //            if (!found) {
  //              removeNode(each);
  //            }
  //          } else {
  //          }
  //        }
  //      }
  //
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //      throw e;
  //    }
  //
  //    return false;
  //  }

  public boolean compareAndChange(ArrayList<T> list, String UID) throws Exception {
    try {
      ArrayList<T> listDataBaseNow = getAllNodeData();
      for (T mod : list) {
        String modUID = getUID(mod);
        if (!listDataBaseNow.contains(mod)) {
          for (T now : listDataBaseNow) {
            if (modUID.equals(getUID(now))) {
              DatabaseConnection.getConnection()
                  .createStatement()
                  .executeUpdate(
                      "DELETE  FROM "
                          + getQueryInput()
                          + " WHERE "
                          + UID
                          + " = '"
                          + getUID(now)
                          + "'");
            }
          }
          addNode(mod);
        }
      }
      for (T now : listDataBaseNow) {
        boolean found = false;
        String nowUID = getUID(now);
        for (T mod : list) {
          if (nowUID.equals(getUID(mod))) {
            //            System.out.println("REMOVING UID:" + nowUID);
            found = true;
          }
        }
        if (!found) {

          System.out.println("REMOVING UID:" + nowUID);
          DatabaseConnection.getConnection()
              .createStatement()
              .executeUpdate(
                  "DELETE  FROM " + getQueryInput() + " WHERE " + UID + " = '" + nowUID + "'");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

    return false;
  }
  //  public boolean compareAndChange(ArrayList<T> list, String UID) throws Exception {
  //    try {
  //      ArrayList<T> listDatabaseNow = getAllNodeData();
  //      for (T each : list) {
  //        String nodeUID = getUID(each);
  //        if (!listDatabaseNow.contains(each)) {
  //
  //          ResultSet rs =
  //              dbConnection.executeQuery(
  //                  "SELECT * FROM " + getQueryInput() + " WHERE " + UID + " = '" + nodeUID +
  // "'");
  //          boolean found = false;
  //          if (rs.next()) {
  //            found = true;
  //            System.out.println("___:" + rs.getString(UID));
  //            DatabaseConnection.getConnection()
  //                .createStatement()
  //                .executeUpdate(
  //                    "DELETE  FROM " + getQueryInput() + " WHERE " + UID + " = '" + nodeUID +
  // "'");
  //          }
  //
  //          addNode(each);
  //        }
  //      }
  //
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //      throw e;
  //    }
  //
  //    return false;
  //  }

  public abstract String getUID(T each) throws SQLException;

  public ArrayList<T> getAllNodeData() {
    T queryResult = null;
    ArrayList<T> allNodes = new ArrayList<>();

    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);
      int columns = rs.getMetaData().getColumnCount();
      while (rs.next()) {
        String[] arguments = new String[columns];
        for (int i = 1; i <= columns; i++) {
          arguments[i] = rs.getString(rs.getMetaData().getColumnName(i));
        }

        queryResult = queryFactory(arguments);
        allNodes.add(queryResult);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  public abstract String
      getQueryInput(); // Returns the table name so that I can query it (For example in locations
  // this would be "TowerLocationsC")

  public void readCsv(String fileName) {
    readCSV(fileName);
  }

  public boolean readCSV(String fileIn) {
    return readCSV(fileIn, getQueryInput());
  }

  public static boolean readCSV(String fileIn, String queryType) {
    try {
      Connection connection = DatabaseConnection.getConnection();
      System.out.println(fileIn);

      String readFile = fileIn;
      URL resource = Query.class.getClassLoader().getResource(fileIn);
      if (resource != null) readFile = resource.toString().replace("%20", " ");

      File in = new File(fileIn); // goetting the file
      if (in != null) {
        Scanner s = new Scanner(in);
        ResultSet inputLine =
            DatabaseConnection.getInstance()
                .executeQuery(
                    "SELECT * FROM "
                        + queryType); // Taking a query of the table so that I can find out the
        // amount of columns
        int columns =
            inputLine
                .getMetaData()
                .getColumnCount(); // Taking the amount of columns from the query metadata
        if (VERBOSE) System.out.println("Columns:" + columns);
        // ArrayList<String> columnNames = new ArrayList<>();
        if (VERBOSE)
          for (int i = 1; i <= columns; i++) { // adding columnNames to the arraylist
            System.out.println("Column:" + i);
            // columnNames.add(inputLine.getMetaData().getColumnName(i));
          }
        String[] lineIn; //
        DatabaseConnection.getInstance()
            .execute("DELETE FROM " + queryType); // Performing a full wipe into recreation
        if (VERBOSE) System.out.println("Entering : " + s.hasNext());
        while (s.hasNext()) {
          String innie = s.nextLine();
          StringTokenizer st =
              new StringTokenizer(
                  innie,
                  "[\"]+,[\"]+"); // using string tokenizer with delimiter of regular expression
          // meaning any amount of " followed by a comma followed by any amount of " so  | " : {x} |
          // , : {1} | " : {x} |  where x represents a arbitrary number
          // example """"""",""""""" would be considered one delimiter but ,"""", would be
          // considered 2 different delimiters of ,"""" and ,
          if (VERBOSE) System.out.println("LineIn:" + innie);
          StringBuilder resultive = new StringBuilder();
          for (int e = 0; e < columns; e++) {
            String elementNow = st.nextToken();
            if (VERBOSE) System.out.println(elementNow);
            resultive.append(
                "'"
                    + elementNow
                    + "'"); // appending every valueof the delmited string to the final query
            if (e != columns - 1) {
              resultive.append(",");
            }
          }
          String query =
              "INSERT INTO "
                  + queryType
                  + " VALUES ("
                  + resultive.toString()
                  + ")"; // creating the query
          if (VERBOSE) System.out.println(query);
          DatabaseConnection.getInstance().execute(query); // executing it
        }
        s.close();
        if (VERBOSE) System.out.println("Read CSV successful: " + queryType);
        return true;
      }
      if (VERBOSE)
        System.out.println(
            "Read CSV unsuccessful: [file was not found or is unwritable to] " + queryType);

    } catch (Exception e) {
      if (VERBOSE) System.out.println("Read CSV unsuccessful: " + queryType);
      if (VERBOSE) e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean writeCSV() {
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
    String dateString = date.format(dateFormat);
    //    URL resource =
    //        getClass()
    //            .getClassLoader()
    //
    // .getResource("./resources/main/edu/wpi/cs3733.c22.teamC/CSV_Files/CSVAUTOBACKUP/");
    String resource = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/";
    System.out.println(resource.toString());
    String fileIn =
        resource.toString()
            + "/"
            + getQueryInput()
            + "-"
            + dateString
            + "-"
            + ".csv"; // fileName format is queryInput-Day-Month-Year-Seconds
    // if (VERBOSE) System.out.println("Write CSV successful: " + getQueryInput());

    return writeCSV(fileIn);
  }

  public boolean writeCSV(String fileIn) {
    return (writeCSV(fileIn, getQueryInput()));
  }

  public static boolean writeCSV(String fileIn, String queryType) {
    try {
      Connection connection = DatabaseConnection.getConnection();
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      File in = createFile(fileIn); // creates the file using createfile
      if (in != null && in.canWrite()) {
        FileWriter fw = new FileWriter(in);
        ResultSet inputLine =
            DatabaseConnection.getInstance().executeQuery("SELECT * FROM " + queryType);
        int columns =
            inputLine
                .getMetaData()
                .getColumnCount(); // same as readcsv - getting column amount of querytype using
        // metadata
        while (inputLine.next()) { // for every table row
          StringBuilder line = new StringBuilder();
          for (int i = 1; i <= columns; i++) { // for every table entry
            line.append(inputLine.getString(i)); // append them to a certain line
            if (i
                != (columns)) { // add commas to the end of them ONLY IF THEY ARE NOT THE LAST ENTRY
              line.append(","); // ^
            }
          }
          line.append("\n"); // end the line with \n
          fw.write(line.toString()); // write the line to the document
        }
        fw.close(); // closes the writer here
      } else {
        return false;
      }
    } catch (Exception e) {
      if (VERBOSE) System.out.println("Write CSV unsuccessful: " + queryType);
      e.printStackTrace();
      return false;
    }
    if (VERBOSE) System.out.println("Write CSV successful: " + queryType);
    return true;
  }

  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM " + getQueryInput();
    ResultSet rs = dbConnection.executeQuery(sql);
    int rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }

  public void clearTable() {
    try {
      String sql = "TRUNCATE TABLE " + getQueryInput();
      dbConnection.execute(sql);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static File createFile(String fileName) {
    try {
      File myObj = new File(fileName);
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File " + fileName + " already exists.");
      }
      return myObj;
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return null;
  }
}
