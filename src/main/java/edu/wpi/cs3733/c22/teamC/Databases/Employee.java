package edu.wpi.cs3733.c22.teamC.Databases;

public class Employee implements DatabaseInterface {

  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String serviceType;
  private String access;
  private String id;

  public String get_username() {
    return username;
  }

  public String get_password() {
    return password;
  }

  public String get_firstName() {
    return firstName;
  }

  public String get_lastName() {
    return lastName;
  }

  public String get_Service_Type() {
    return serviceType;
  }

  public String get_access() {
    return access;
  }

  public String get_id() {
    return id;
  }

  public void set_username(String username) {
    this.username = username;
  }

  public void set_password(String password) {
    this.password = password;
  }

  public void set_firstName(String firstName) {
    this.firstName = firstName;
  }

  public void set_lastName(String lastName) {
    this.lastName = lastName;
  }

  public void set_serviceType(String serviceType) {
    this.serviceType = serviceType;
  }

  public void set_access(String access) {
    this.access = access;
  }

  public void set_id(String id) {
    this.id = id;
  }

  public Employee(
      String username,
      String password,
      String firstName,
      String lastName,
      String serviceType,
      String access,
      String id) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.serviceType = serviceType;
    this.access = access;
    this.id = id;
  }

  @Override
  public String[] getValues() {
    return new String[] {
      "username", "password", "firstName", "lastName", "serviceType", "access", "id"
    };
  }

  @Override
  public String getName() {
    return null;
  }
}
