package edu.wpi.cs3733.c22.teamC.Databases;
/** @author Aidan Burns 2/22/2022 This project does DatabaseInterface on the IntelliJ IDEA */
public interface DatabaseInterface {
  public String[] getFields();

  public String[] getValues();

  public String getUID();

  public String[] setValues(String[] values);

  public String getName();
}
