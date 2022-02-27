package edu.wpi.cs3733.c22.teamC;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
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
  // ticketID
  // ticketID

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
    List<String> values = new ArrayList<>();

    List<String> fields = map.get(table);
    while (actQuery.contains(",")) {
      values.add(actQuery.substring(actQuery.indexOf('\'') + 1, actQuery.indexOf(',') - 1));
      actQuery = actQuery.substring(actQuery.indexOf(','));
      if (actQuery.charAt(0) == ',') {
        actQuery = actQuery.substring(1);
      }
    }
    values.add(actQuery.substring(2, actQuery.length() - 1));
    Document doc = new Document();
    for (int i = 0; i < fields.size(); i++) {
      doc.append(fields.get(i), values.get(i));
    }
    teamC_db.getCollection(table).insertOne(doc);
    return "INSERT";
  }

  private static String select(String query) {
    if (query.contains("*")){



    } else {

    }

    return "SELECT";
  }

  private static String delete(String query) {
    if (query.contains("WHERE")) {
      String actQuery = query.substring(query.indexOf(' ') + 1);
      actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
      String table = actQuery.substring(0, actQuery.indexOf(' '));
      String keyVal = actQuery.substring(actQuery.indexOf('\'') + 1, actQuery.length() - 1);
      MongoCollection collection = teamC_db.getCollection(table);
      teamC_db.getCollection(table).deleteOne(new Document(map.get(table).get(0), keyVal));
    } else {
      teamC_db.getCollection(query.substring(query.lastIndexOf(' '))).drop();
    }
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
    actQuery = actQuery.substring(actQuery.indexOf('('), actQuery.lastIndexOf(')'));
    String toIterate = actQuery;
    List<String> fields = new ArrayList<>();
    while (toIterate.contains(",")) {
      while (toIterate.charAt(0) != ' ') {
        toIterate = toIterate.substring(1);
      }
      fields.add(toIterate.substring(1, toIterate.indexOf('V') - 1));
      toIterate = toIterate.substring(toIterate.indexOf(',') + 1);
    }
    fields.add(toIterate.substring(1, toIterate.indexOf('V') - 1));
    map.put(table, fields);
    try {
      teamC_db.createCollection(table);
    } catch (Exception e) {
      teamC_db.getCollection(table);
    }

    return "CREATE";
  }

  private static String update(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));
    String toIterate = actQuery;
    List<String> values = new ArrayList<>();
    String from = actQuery.substring(actQuery.indexOf("WHERE"));
    from = from.substring(from.indexOf('\''));
    values.add(from);
    while (toIterate.contains(",")) {
      String value = toIterate.substring(toIterate.indexOf('\''), toIterate.indexOf(','));
      values.add(value);
      toIterate = toIterate.substring(toIterate.indexOf(',') + 1);
    }
    String lastValue = toIterate.substring(toIterate.indexOf('\''), toIterate.indexOf(" WHERE"));
    values.add(lastValue);
    List<String> fields = map.get(table);
    Document document = new Document();
    for (int i = 0; i < fields.size(); i++) {
      document.append(fields.get(i), values.get(i));
    }
    Document filterDoc = new Document(fields.get(0), values.get(0));
    System.out.println(table);
    System.out.println(fields.get(0));
    System.out.println(values.get(0));
    teamC_db.getCollection(table).deleteOne(filterDoc);
    teamC_db.getCollection(table).insertOne(document);
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