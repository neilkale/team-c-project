package edu.wpi.cs3733.c22.teamC.Databases;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.SQLMethods.MedicalEquipmentQuery;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** this class is for the medical equipment data */
public class MedicalEquipment implements DatabaseInterface {

  private String _equipmentID;
  private String _locationID;
  private String _lastKnownTime;
  private String _status;
  private String _equipmentType;
  private String _name;

  public String get_equipmentID() {
    return _equipmentID;
  }

  public void set_equipmentID(String _equipmentID) {
    this._equipmentID = _equipmentID;
  }

  public String get_locationID() {
    return _locationID;
  }

  public void set_locationID(String _locationID) {
    this._locationID = _locationID;
  }

  public String get_lastKnownTime() {
    return _lastKnownTime;
  }

  public void set_lastKnownTime(String _lastKnownTime) {
    this._lastKnownTime = _lastKnownTime;
  }

  public String get_status() {
    return _status;
  }

  public void set_status(String _status) {
    this._status = _status;
  }

  public String get_equipmentType() {
    return _equipmentType;
  }

  public void set_equipmentType(String _equipmentType) {
    this._equipmentType = _equipmentType;
  }

  public String get_name() {
    return _name;
  }

  public void set_name(String _name) {
    this._name = _name;
  }

  public MedicalEquipment(
      String _equipmentID,
      String _locationID,
      String _lastKnownTime,
      String _status,
      String _equipmentType,
      String _name) {
    this._equipmentID = _equipmentID;
    this._locationID = _locationID;
    this._lastKnownTime = _lastKnownTime;
    this._status = _status;
    this._equipmentType = _equipmentType;
    this._name = _name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MedicalEquipment that = (MedicalEquipment) o;
    return get_equipmentID().equals(that.get_equipmentID())
        && get_locationID().equals(that.get_locationID())
        && get_lastKnownTime().equals(that.get_lastKnownTime())
        && get_status().equals(that.get_status())
        && get_equipmentType().equals(that.get_equipmentType())
        && get_name().equals(that.get_name());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        get_equipmentID(),
        get_locationID(),
        get_lastKnownTime(),
        get_status(),
        get_equipmentType(),
        get_name());
  }

  public static String[] getarguments() {
    return new String[] {
      "equipmentID", "locationID", "lastKnownTime", "status", "equipmentType", "name"
    };
  }

  @Override
  public String[] getValues() {
    List<String> a = new ArrayList<>();
    Method getter;
    for (String s : getarguments()) {
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
  public String getUID() {
    return _equipmentID;
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

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getEquipmentDao();
  }

  public MedicalEquipmentQuery getQueryInstance() {
    return new MedicalEquipmentQuery();
  }

  @Override
  public String[] getFields() {
    return new String[] {
      get_equipmentID(),
      get_locationID(),
      get_lastKnownTime(),
      get_status(),
      get_equipmentType(),
      get_name()
    };
  }
}
