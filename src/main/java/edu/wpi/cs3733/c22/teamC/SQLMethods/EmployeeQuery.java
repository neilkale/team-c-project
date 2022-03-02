package edu.wpi.cs3733.c22.teamC.SQLMethods;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeQuery extends Query<Employee> {
  private DatabaseConnection dbConnection = super.dbConnection;

  public EmployeeQuery() {}

  public Employee queryFactory(String[] inputs) {
    return staticQueryFactory(inputs);
  }

  public static Employee staticQueryFactory(String[] inputs) {
    if (inputs.length != 10) {
      System.out.println(
          "[QueryFactory of QueryType]: "
              + staticGetQueryInput()
              + "has failed | Input arguments does not match the allotted arguments for the creation of the object - NULL has been returned");
      return null;
    }
    return new Employee(
        inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7],
        inputs[8], inputs[9]);
  }

  @Override
  public String getUID(Employee each) throws SQLException {
    return each.get_username();
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
              + employee.get_service_type()
              + "', '"
              + employee.get_access()
              + "', '"
              + employee.get_id()
              + "', '"
              + employee.get_profilePicture()
              + "', '"
              + employee.get_phoneNumber()
              + "', '"
              + employee.get_email()
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
            + employee.get_service_type()
            + "', access = '"
            + employee.get_access()
            + "', id ='"
            + employee.get_id()
            + "', profilePicture = '"
            + employee.get_profilePicture()
            + "', phoneNumber = '"
            + employee.get_phoneNumber()
            + "', email = '"
            + employee.get_email()
            + "' WHERE "
            + "username = '"
            + employee.get_username()
            + "'";
    dbConnection.execute(query);
  }

  @Override
  public String getQueryInput() {
    return staticGetQueryInput();
  }

  public static String staticGetQueryInput() {
    return "EMPLOYEEC";
  }

  /**
   * Finds an employee corresponding to input username
   *
   * @return employee if exists, else null
   */
  public Employee findNodeByUsername(String target_user) {
    List<Employee> employees = getAllNodeData();
    for (Employee e : employees) {
      System.out.println(e);
    }
    for (Employee e : employees) {
      if (e.get_username().equals(target_user)) {
        return e;
      }
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
