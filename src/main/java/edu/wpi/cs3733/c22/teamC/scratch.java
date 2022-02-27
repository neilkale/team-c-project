package edu.wpi.cs3733.c22.teamC;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import org.bson.Document;

public class scratch {
  static MongoClient mongoClient;
  static MongoDatabase teamC_db;
  static Map<String, List<String>> map;

  public static void main(String[] args) {

    String uri =
        "mongodb+srv://admin:dDbno11RbFVsXVv3@serverlessinstance0.zitm8.mongodb.net/teamC_DB?retryWrites=true&w=majority";

    mongoClient = MongoClients.create(uri);
    teamC_db = mongoClient.getDatabase("teamC_DB");
    map = new HashMap<>();

    try {
      for (String s : readQueries()) {
        if (s.length() != 0) {
          System.out.println(getAction(s) + s.substring(s.indexOf(' ')));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      mongoClient.close();
    }
  }

  private static String getAction(String query) {
    String toReturn = "";
    String firstWord = query.substring(0, query.indexOf(' '));

    switch (firstWord) {
      case "INSERT":
        toReturn = insert(query);
        break;
      case "CREATE":
        toReturn = createTable(query);
        break;
      case "SELECT":
        toReturn = select(query);
        break;
      case "DELETE":
        toReturn = delete(query);
        break;
      case "TRUNCATE":
        toReturn = truncate(query);
        break;
      case "UPDATE":
        toReturn = update(query);
        break;
      default:
        break;
    }
    return toReturn;
  }

  private static String insert(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));
    actQuery = actQuery.substring(actQuery.indexOf('(') + 1, actQuery.indexOf(')'));
    String toIterate = actQuery;
    List<String> values = new ArrayList<>();

    List<String> fields = map.get(table);
    while (actQuery.contains(",")) {
      values.add(actQuery.substring(0, actQuery.indexOf(',')));
      actQuery = actQuery.substring(actQuery.indexOf(',') + 1);
    }
    values.add(actQuery.substring(1, actQuery.length() - 1));

    Document doc = new Document();
    for (int i = 0; i < fields.size(); i++) {
      doc.append(fields.get(i), values.get(i));
    }
    teamC_db.getCollection(table).insertOne(doc);
    return "INSERT";
  }

  private static String select(String query) {
    String table = query.substring(query.lastIndexOf(' ') + 1);

    return "SELECT";
  }

  private static String delete(String query) {
    String actQuery = query.substring(query.indexOf(' '));
    actQuery = actQuery.substring(actQuery.indexOf(' '));
    return "DELETE";
  }

  private static String truncate(String query) {
    String actQuery = query;
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    teamC_db.getCollection(actQuery).drop();
    return "TRUNCATE";
  }

  private static String createTable(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf('('));
    actQuery = actQuery.substring(actQuery.indexOf('(') + 1, actQuery.lastIndexOf(')'));
    String toIterate = actQuery;
    List<String> values = new ArrayList<>();
    while (toIterate.contains(",")) {
      values.add(toIterate.substring(0, toIterate.indexOf('V') - 1));
      toIterate = toIterate.substring(toIterate.indexOf(',') + 1);
    }
    values.add(toIterate.substring(1, toIterate.indexOf('V') - 1));
    map.put(table, values);
    teamC_db.getCollection(table);

    return "CREATE";
  }

  private static String update(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    String table = actQuery.substring(0,actQuery.indexOf(' '));
    String toIterate = actQuery;
    List<String> values = new ArrayList<>();
    String from = actQuery.substring(actQuery.indexOf("WHERE"));
    from = from.substring(from.indexOf('\''));
    values.add(from);
    while (toIterate.contains(",")) {
      String value = toIterate.substring(toIterate.indexOf('\''),toIterate.indexOf(','));
      values.add(value);
      toIterate = toIterate.substring(toIterate.indexOf(',')+1);
    }
    String value = toIterate.substring(toIterate.indexOf('\''),toIterate.indexOf(" WHERE"));
    values.add(value);


    teamC_db.getCollection(t)
    return "UPDATE";
  }

  private static List<String> readQueries() {
    List<String> toReturn = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader("sampleQueries.txt"))) {
      String line;
      while ((line = br.readLine()) != null) {
        toReturn.add(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return toReturn;
  }
}
