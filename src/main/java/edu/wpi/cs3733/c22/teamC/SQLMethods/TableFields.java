package edu.wpi.cs3733.c22.teamC.SQLMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author Aidan Burns 3/1/2022 This project does TableFields on the IntelliJ IDEA */
public class TableFields {
  private Map<String, List<String>> map;

  public TableFields() {
    map = new HashMap<>();
  }

  public void addToMap(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf('('));
    actQuery = actQuery.substring(actQuery.indexOf('('), actQuery.lastIndexOf(')'));
    String toIterate = actQuery;
    ArrayList<String> fields = new ArrayList<>();
    System.out.println(query);
    while (toIterate.contains(",")) {
      while (toIterate.charAt(0) != ' ') {
        toIterate = toIterate.substring(1);
      }
      fields.add(toIterate.substring(1, toIterate.indexOf("VAR") - 1));
      toIterate = toIterate.substring(toIterate.indexOf(',') + 1);
    }
    fields.add(toIterate.substring(1, toIterate.indexOf('V') - 1));
    map.put(table, fields);
  }

  public List<String> tableToFields(String table) {
    return map.get(table);
  }
}
