package edu.wpi.cs3733.c22.teamC.SQLMethods;
/** @author Aidan Burns 3/1/2022 This project does ResultSetParser on the IntelliJ IDEA */
import static edu.wpi.cs3733.c22.teamC.Databases.MongoDatabase.tableToQueryClass;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ResultSetParser {

  public static ArrayList<String> resultStringParser(ResultSet rs, String query)
      throws SQLException {
    String specificField = query.substring(query.indexOf(' ') + 1, query.indexOf("FROM") - 1);
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));

    ArrayList<String> toReturn = new ArrayList<>();
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    List<String> field = dbConnection.getFieldsFromTable(table);
    while (rs.next()) {
      toReturn.add(rs.getString(specificField));
    }

    return toReturn;
  }

  public static ArrayList<DatabaseInterface> resultDatabaseParser(ResultSet rs, String query) {
    ArrayList<DatabaseInterface> toReturn = new ArrayList<>();
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    String table;
    if (query.contains("WHERE")) {
      String actQuery = query.substring(query.indexOf(' ') + 1);
      actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
      actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
      table = actQuery.substring(0, actQuery.indexOf(' '));
    } else {
      table = query.substring(query.lastIndexOf(' ') + 1);
    }

    Class<? extends Query> queryClass;
    Method queryFactory;
    String className = tableToQueryClass(table);
    List<String> fields = dbConnection.getFieldsFromTable(table);
    try {
      queryClass = (Class<? extends Query>) Class.forName(className);
      queryFactory = queryClass.getMethod("staticQueryFactory", String[].class);

      while (rs.next()) {
        String[] args = new String[fields.size()];
        for (int i = 0; i < args.length; i++) {
          args[i] = rs.getString(fields.get(i));
        }

        toReturn.add((DatabaseInterface) queryFactory.invoke(null, new Object[] {args}));
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    System.out.println(toReturn);
    return toReturn;
  }
}
