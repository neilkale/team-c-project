package edu.wpi.cs3733.c22.teamC.Databases;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Employee implements DatabaseInterface {

  private String _username;
  private String _password;
  private String _firstName;
  private String _lastName;
  private String _serviceType;
  private String _access;
  private String _id;
  private String _profilePicture;

  public String get_username() {
    return _username;
  }

  public String get_password() {
    return _password;
  }

  public String get_firstName() {
    return _firstName;
  }

  public String get_lastName() {
    return _lastName;
  }

  public String get_Service_Type() {
    return _serviceType;
  }

  public String get_service_type() {
    return _serviceType;
  }

  public String get_access() {
    return _access;
  }

  public String get_id() {
    return _id;
  }

  public void set_username(String username) {
    this._username = username;
  }

  public void set_password(String password) {
    this._password = password;
  }

  public void set_firstName(String firstName) {
    this._firstName = firstName;
  }

  public void set_lastName(String lastName) {
    this._lastName = lastName;
  }

  public void set_serviceType(String serviceType) {
    this._serviceType = serviceType;
  }

  public void set_access(String access) {
    this._access = access;
  }

  public void set_id(String id) {
    this._id = id;
  }

  public Employee(
      String username,
      String password,
      String firstName,
      String lastName,
      String serviceType,
      String access,
      String id,
      String profilePicture) {
    this._username = username;
    this._password = password;
    this._firstName = firstName;
    this._lastName = lastName;
    this._serviceType = serviceType;
    this._access = access;
    this._id = id;
    this._profilePicture = profilePicture;
  }

  public String get_profilePicture() {
    return _profilePicture;
  }

  public void set_profilePicture(String profilePicture) {
    this._profilePicture = profilePicture;
  }

  @Override
  public String[] getFields() {
    return new String[] {
      "username",
      "password",
      "firstName",
      "lastName",
      "serviceType",
      "access",
      "id",
      "profilePicture"
    };
  }

  @Override
  public String[] getValues() {
    List<String> a = new ArrayList<>();
    Method getter;
    for (String s : getFields()) {
      try {
        getter = this.getClass().getMethod("get_" + s);
        a.add((String) getter.invoke(this, new Object[] {}));
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    String[] toReturn = new String[a.size()];
    for (int i = 0; i < a.size(); i++) {
      toReturn[i] = a.get(i);
    }
    return toReturn;
  }

  @Override
  public String[] setValues(String[] values) {
    List<String> a = new ArrayList<>();
    Method setter;
    String[] fields = getFields();
    for (int i = 0; i < getFields().length; i++) {
      try {
        setter = this.getClass().getMethod("set_" + fields[i]);
        a.add((String) setter.invoke(this, new Object[] {values[i]}));
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    for (String s : getFields()) {}

    String[] toReturn = new String[a.size()];
    for (int i = 0; i < a.size(); i++) {
      toReturn[i] = a.get(i);
    }
    return toReturn;
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }
}
