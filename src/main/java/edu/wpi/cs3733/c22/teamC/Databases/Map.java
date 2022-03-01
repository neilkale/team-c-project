package edu.wpi.cs3733.c22.teamC.Databases;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Map implements DatabaseInterface {
  private String buildingName;
  private String floorName;
  private String imagePath;
  private String floorNumber;

  public String get_buildingName() {
    return buildingName;
  }

  public String get_floorName() {
    return floorName;
  }

  public String get_imagePath() {
    return imagePath;
  }

  public String get_floorNumber() {
    return floorNumber;
  }

  public void set_buildingName(String buildingName) {
    this.buildingName = buildingName;
  }

  public void set_floorName(String floorName) {
    this.floorName = floorName;
  }

  public void set_imagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public void set_floorNumber(String floorNumber) {
    this.floorNumber = floorNumber;
  }

  public Map(String buildingName, String floorName, String imagePath, String floorNumber) {
    this.buildingName = buildingName;
    this.floorName = floorName;
    this.imagePath = imagePath;
    this.floorNumber = floorNumber;
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
    return imagePath;
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
  public String[] getFields() {
    return new String[0];
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getMapDao();
  }
}
