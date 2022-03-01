package edu.wpi.cs3733.c22.teamC.Databases;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Location implements DatabaseInterface {

  private String _nodeID;
  private String _xcoord;
  private String _ycoord;
  private String _floor;
  private String _building;
  private String _nodeType;
  private String _longName;
  private String _shortName;

  public String get_nodeID() {
    return _nodeID;
  }

  public String get_xcoord() {
    return _xcoord;
  }

  public String get_ycoord() {
    return _ycoord;
  }

  public String get_floor() {
    return _floor;
  }

  public Integer get_zcoord() {
    switch (_floor) {
      case "L2":
        {
          return 100;
        }
      case "L1":
        {
          return 200;
        }
      case "1":
        {
          return 300;
        }
      case "2":
        {
          return 400;
        }
      case "3":
        {
          return 500;
        }
      default:
        {
          return 000;
        }
    }
  }

  public String get_building() {
    return _building;
  }

  public String get_nodeType() {
    return _nodeType;
  }

  public String get_longName() {
    return _longName;
  }

  public String get_shortName() {
    return _shortName;
  }

  public void set_nodeID(String _nodeID) {
    this._nodeID = _nodeID;
  }

  public void set_xcoord(String _xcoord) {
    this._xcoord = _xcoord;
  }

  public void set_ycoord(String _ycoord) {
    this._ycoord = _ycoord;
  }

  public void set_floor(String _floor) {
    this._floor = _floor;
  }

  public void set_building(String _building) {
    this._building = _building;
  }

  public void set_nodeType(String _nodeType) {
    this._nodeType = _nodeType;
  }

  public void set_longName(String _longName) {
    this._longName = _longName;
  }

  public void set_shortName(String _shortName) {
    this._shortName = _shortName;
  }

  public double getDistance(Location other) {
    return getDistance(this, other);
  }

  public static double getDistance(Location one, Location two) {
    double x = Math.pow((Double.parseDouble(one._xcoord) - Double.parseDouble(two._xcoord)), 2);
    double y = Math.pow((Double.parseDouble(one._ycoord) - Double.parseDouble(two._ycoord)), 2);
    return Math.pow((x + y), .5);
  }

  public Location(
      String nodeID,
      String xcoord,
      String ycoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this._nodeID = nodeID;
    this._xcoord = xcoord;
    this._ycoord = ycoord;
    this._floor = floor;
    this._building = building;
    this._nodeType = nodeType;
    this._longName = longName;
    this._shortName = shortName;
  }

  protected Location(
      String equipmentID,
      String lastKnownX,
      String lastKnownY,
      String lastKnownFloor,
      String lastKnownBuilding,
      String EquipmentType,
      String name) {
    this._nodeID = equipmentID;
    this._xcoord = lastKnownX;
    this._ycoord = lastKnownY;
    this._floor = lastKnownFloor;
    this._building = lastKnownBuilding;
    this._nodeType = EquipmentType;
    this._shortName = name;
    this._longName = null;
  }

  public Location(String nodeID) {
    this._nodeID = nodeID;
    this._xcoord = null;
    this._ycoord = null;
    this._floor = null;
    this._building = null;
    this._nodeType = null;
    this._longName = null;
    this._shortName = null;
  }

  public String getName() {
    return "Location";
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
  public String getUID() {
    return _nodeID;
  }

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
  public String[] getFields() {
    return new String[] {
      "nodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName"
    };
  }

  // For the purpose of the project we will be phasing out the hash map idea for the location of
  // towers.
  //  public int hashCode() {
  //    int result = _nodeID.hashCode();
  //    result = result + _xcoord.hashCode();
  //    result = result + _ycoord.hashCode();
  //    result = result + _floor.hashCode();
  //    result = result + _building.hashCode();
  //    result = result + _nodeType.hashCode();
  //    result = result + _longName.hashCode();
  //    result = result + _shortName.hashCode();
  //    return result;
  //  }
}
