package edu.wpi.cs3733.c22.teamC.SQLMethods;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeQuery extends Query<Employee> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public EmployeeQuery() {}

  @Override
  public Employee queryFactory(String[] inputs) {
    if (inputs.length != 7) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + getQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    return new Employee(
        inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
  }

  @Override
  public String getUID(Employee each) throws SQLException {
    return each.get_username();
  }

  public ArrayList<Employee> getAllNodeData() {
    Employee queryResult = null;
    ArrayList<Employee> allNodes = new ArrayList<Employee>();

    try {
      String query = "SELECT * FROM " + getQueryInput();
      ResultSet rs = dbConnection.executeQuery(query);

      while (rs.next()) {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String serviceType = rs.getString("serviceType");
        String access = rs.getString("access");
        String id = rs.getString("ID");

        queryResult =
            new Employee(username, password, firstName, lastName, serviceType, access, id);
        allNodes.add(queryResult);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allNodes;
  }

  public static ArrayList<String> getFullNameAll() {
    ArrayList<String> allNodes = new ArrayList<>();
    ArrayList<Employee> allEmployees = (new EmployeeQuery()).getAllNodeData();
    for (Employee each : allEmployees) {
      allNodes.add(each.get_firstName() + " " + each.get_lastName());
    }

    return allNodes;
  }

  public static Employee fullNameToEmployee(
      String fullName) { // This is a temporary implementation that does not account for multiple
    // employees of the same name

    ArrayList<Employee> allEmployees = (new EmployeeQuery()).getAllNodeData();
    for (Employee each : allEmployees) {
      if ((each.get_lastName() + "," + each.get_firstName()).equals(fullName)) return each;
    }
    return null;
  }

  public static String fullNameToUsername(String fullName) {
    return fullNameToEmployee(fullName).get_username();
  }

  @Override
  public void addNode(Employee employee) {
    try {
      String query =
          "INSERT INTO "
              + getQueryInput()
              + " VALUES "
              + "('"
              + employee.get_username()
              + "', '"
              + employee.get_password()
              + "', '"
              + employee.get_firstName()
              + "', '"
              + employee.get_lastName()
              + "', '"
              + employee.get_Service_Type()
              + "', '"
              + employee.get_access()
              + "', '"
              + employee.get_id()
              + "')";
      dbConnection.execute(query);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void removeNode(Employee employee) throws SQLException {
    String query =
        "DELETE FROM " + getQueryInput() + " WHERE username = '" + employee.get_username() + "'";
    dbConnection.execute(query);
  }

  @Override
  public void editNode(Employee employee) throws SQLException {
    String query =
        "UPDATE "
            + getQueryInput()
            + " SET "
            + "username = '"
            + employee.get_username()
            + "', password = '"
            + employee.get_password()
            + "', firstName = '"
            + employee.get_firstName()
            + "', lastName = '"
            + employee.get_lastName()
            + "', serviceType = '"
            + employee.get_Service_Type()
            + "', access = '"
            + employee.get_access()
            + "', id ="
            + employee.get_id()
            + "' WHERE "
            + "username = '"
            + employee.get_username()
            + "'";
    dbConnection.execute(query);
  }

  //  @Override
  //  public void writeCsv(String fileName) {
  //    // TODO: WRITE THIS

  @Override
  public String getQueryInput() {
    return "EMPLOYEEC";
  }

  @Override
  public Integer getNumRows() throws SQLException {
    String sql = "SELECT * FROM " + getQueryInput();
    ResultSet rs = dbConnection.executeQuery(sql);
    Integer rowCount = 0;
    while (rs.next()) {
      rowCount++;
    }
    return rowCount;
  }

  /**
   * Finds an employee corresponding to input username
   *
   * @return employee if exists, else null
   */
  public Employee findNodeByUsername(String target_user) {
    try {
      String sql = "SELECT * FROM " + getQueryInput() + " WHERE username = '" + target_user + "'";
      ResultSet rs = dbConnection.executeQuery(sql);
      while (rs.next()) {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String serviceType = rs.getString("serviceType");
        String access = rs.getString("access");
        String id = rs.getString("id");
        Employee queryResult =
            new Employee(username, password, firstName, lastName, serviceType, access, id);
        return queryResult;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Employee findNodeByID(String id) {
    ArrayList<Employee> allNodes = getAllNodeData();
    for (Employee each : allNodes) {
      if (each.get_id().equals(id)) {
        return each;
      }
    }
    return null;
  }
}