package edu.wpi.cs3733.c22.teamC.Databases;
/** @author Aidan Burns 2/28/2022 This project does MongoDatabase on the IntelliJ IDEA */
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import edu.wpi.cs3733.c22.teamC.SQLMethods.Query;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map;
import org.bson.Document;

public class MongoDatabase {
  private static MongoClient mongoClient;
  private static com.mongodb.client.MongoDatabase teamC_db;
  private static Map<String, ArrayList<String>> map;
  private static String uri =
      "mongodb+srv://admin:dDbno11RbFVsXVv3@serverlessinstance0.zitm8.mongodb.net/teamC_DB?retryWrites=true&w=majority";
  private DatabaseConnection databaseConnection;

  public MongoDatabase() {
    mongoClient = MongoClients.create(uri);
    teamC_db = mongoClient.getDatabase("teamC_DB");
    map = new HashMap<>();
    databaseConnection = DatabaseConnection.getInstance();
  }

  public void closeMongo() {
    mongoClient.close();
  }

  public void batchInsert(List<String> queries) {
    List<Document> docList = new ArrayList<>();
    Document doc;
    String actQuery = queries.get(0).substring(queries.get(0).indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));
    ArrayList<String> values;
    for (String query : queries) {
      actQuery = query.substring(query.indexOf(' ') + 1);
      actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
      actQuery = actQuery.substring(actQuery.indexOf('(') + 1, actQuery.indexOf(')'));
      values = new ArrayList<>();

      ArrayList<String> fields = map.get(table);
      while (actQuery.contains(",")) {
        values.add(actQuery.substring(actQuery.indexOf('\'') + 1, actQuery.indexOf(',') - 1));
        actQuery = actQuery.substring(actQuery.indexOf(','));
        if (actQuery.charAt(0) == ',') {
          actQuery = actQuery.substring(1);
        }
      }
      values.add(actQuery.substring(1, actQuery.length() - 1));
      doc = new Document();
      for (int i = 0; i < fields.size(); i++) {
        doc.append(fields.get(i), values.get(i));
      }
      docList.add(doc);
    }

    teamC_db.getCollection(table).insertMany(docList);
  }

  public String getAction(String query) {
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
        select(query);
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

  public String insert(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));
    actQuery = actQuery.substring(actQuery.indexOf('(') + 1, actQuery.indexOf(')'));
    ArrayList<String> values = new ArrayList<>();

    ArrayList<String> fields = map.get(table);
    while (actQuery.contains(",")) {
      values.add(actQuery.substring(actQuery.indexOf('\'') + 1, actQuery.indexOf(',') - 1));
      actQuery = actQuery.substring(actQuery.indexOf(','));
      if (actQuery.charAt(0) == ',') {
        actQuery = actQuery.substring(1);
      }
    }
    values.add(actQuery.substring(1, actQuery.length() - 1));
    Document doc = new Document();
    for (int i = 0; i < fields.size(); i++) {
      doc.append(fields.get(i), values.get(i));
    }
    teamC_db.getCollection(table).insertOne(doc);

    return "INSERT";
  }

  public List<? extends Object> select(String query) {
    if (query.contains("*")) {
      // Returns List of databaseInterface
      return selectAllObjectFromQuery(query);
    } else {
      // Returns List of String
      return selectColumnFromQuery(query);
    }
  }

  public String delete(String query) {
    if (query.contains("WHERE")) {
      String actQuery = query.substring(query.indexOf(' ') + 1);
      actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
      String table = actQuery.substring(0, actQuery.indexOf(' '));
      String keyVal = actQuery.substring(actQuery.indexOf('\'') + 1, actQuery.length() - 1);

      try {
        teamC_db.getCollection(table).deleteOne(new Document(map.get(table).get(0), keyVal));
      } catch (Exception e) {
        databaseConnection.disableMongo("Mongo Failed to Delete " + query);
      }
    } else {
      try {
        teamC_db.getCollection(query.substring(query.lastIndexOf(' ') + 1)).drop();
      } catch (Exception e) {
        databaseConnection.disableMongo("Mongo Failed to Delete " + query);
      }
    }
    return "DELETE";
  }

  public String truncate(String query) {
    String actQuery = query;
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);

    try {
      teamC_db.getCollection(actQuery).drop();
    } catch (Exception e) {
      databaseConnection.disableMongo("Mongo Failed to truncate " + query);
    }
    return "TRUNCATE";
  }

  public String createTable(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf('('));
    actQuery = actQuery.substring(actQuery.indexOf('('), actQuery.lastIndexOf(')'));
    String toIterate = actQuery;
    ArrayList<String> fields = new ArrayList<>();
    while (toIterate.contains(",")) {
      while (toIterate.charAt(0) != ' ') {
        toIterate = toIterate.substring(1);
      }
      fields.add(toIterate.substring(1, toIterate.indexOf("VAR") - 1));
      toIterate = toIterate.substring(toIterate.indexOf(',') + 1);
    }
    fields.add(toIterate.substring(1, toIterate.indexOf('V') - 1));
    map.put(table, fields);

    try {
      teamC_db.getCollection(table);
    } catch (Exception e) {
      teamC_db.createCollection(table);
    }

    return "CREATE";
  }

  public String update(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));
    String toIterate = actQuery;
    ArrayList<String> values = new ArrayList<>();
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
    ArrayList<String> fields = map.get(table);
    Document document = new Document();
    for (int i = 0; i < fields.size(); i++) {
      document.append(fields.get(i), values.get(i));
    }
    Document filterDoc = new Document(fields.get(0), values.get(0));

    teamC_db.getCollection(table).deleteOne(filterDoc);
    teamC_db.getCollection(table).insertOne(document);

    return "UPDATE";
  }

  public ArrayList<DatabaseInterface> selectAllObjectFromQuery(String query) {
    String table;
    if (query.contains("WHERE")) {
      String actQuery = query.substring(query.indexOf(' ') + 1);
      actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
      table = actQuery.substring(0, actQuery.indexOf(' '));
      System.out.println(table);
    } else {
      table = query.substring(query.lastIndexOf(' ') + 1);
    }
    MongoCollection<Document> collection;

    collection = teamC_db.getCollection(table);

    ArrayList<DatabaseInterface> toReturn = new ArrayList<>();
    ArrayList<String> fields = map.get(table);
    Class<? extends Query> queryClass;
    Method queryFactory;

    try {
      String className = tableToQueryClass(table);
      queryClass = (Class<? extends Query>) Class.forName(className);

      queryFactory = queryClass.getMethod("staticQueryFactory", String[].class);
      FindIterable<Document> toIterate;

      if (query.contains("WHERE")) {
        Document filter =
            new Document(
                fields.get(0), query.substring(query.indexOf('\'') + 1, query.length() - 1));

        toIterate = collection.find(filter);

      } else {
        toIterate = collection.find();
      }

      for (Document d : toIterate) {

        String[] args = new String[d.size() - 1];
        for (int i = 0; i < fields.size(); i++) {
          args[i] = (String) d.get(fields.get(i));
        }

        toReturn.add((DatabaseInterface) (queryFactory.invoke(null, new Object[] {args})));
      }
    } catch (ClassNotFoundException e) {
      System.out.println("ClassNotFound");
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      System.out.println("IllegalArgumentException");
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      System.out.println("NoSuchMethod");
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      System.out.println("InvocationTarget");
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      System.out.println("IllegalAccess");
      e.printStackTrace();
    }
    return toReturn;
  }

  public ArrayList<String> selectColumnFromQuery(String query) {
    String specificField = query.substring(query.indexOf(' ') + 1, query.indexOf("FROM") - 1);
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));

    MongoCollection<Document> collection;

    collection = teamC_db.getCollection(table);

    ArrayList<String> toReturn = new ArrayList<>();

    FindIterable<Document> toIterate;
    toIterate = collection.find();

    for (Document d : toIterate) {
      toReturn.add((String) d.get(specificField));
    }

    return toReturn;
  }

  public static String tableToQueryClass(String tableName) {
    String toCheck = tableName.toUpperCase(Locale.ROOT);
    String toReturn = "";
    switch (toCheck) {
      case "TOWERLOCATIONSC":
        toReturn = "Location";
        break;
      case "SECURITYREQUESTC":
        toReturn = "requests.SecurityRequest";
        break;
      case "LANGUAGEREQUESTC":
        toReturn = "requests.LanguageRequest";
        break;
        // edu.wpi.cs3733.c22.teamC.SQLMethods.requests.ReligiousRequestQuery
      case "RELIGIOUSREQUESTC":
        toReturn = "requests.ReligiousRequest";
        break;
      case "LAUNDRYREQUESTC":
        toReturn = "requests.LaundryRequest";
        break;
      case "MEDICINEREQUESTC":
        toReturn = "requests.MedicineRequest";
        break;
      case "INTERNALTRANSPORTREQUESTC":
        toReturn = "requests.InternalTransportRequest";
        break;
      case "GIFTREQUESTC":
        toReturn = "requests.GiftRequest";
        break;
      case "EMPLOYEEC":
        toReturn = "Employee";
        break;
      case "MAPSC":
        toReturn = "Map";
        break;
      case "ITREQUESTC":
        toReturn = "requests.ITRequest";
        break;
      case "SANITATIONREQUESTC":
        toReturn = "requests.SanitationRequest";
        break;
      case "MAINTENANCEREQUESTC":
        toReturn = "requests.MaintenanceRequest";
        break;
      case "EQUIPMENTREQUESTC":
        toReturn = "requests.EquipmentRequest";
        break;
      case "EQUIPMENTC":
        toReturn = "MedicalEquipment";
        break;
    }
    return "edu.wpi.cs3733.c22.teamC.SQLMethods." + toReturn + "Query";
  }

  public List<String> tableToFields(String table) {
    return map.get(table);
  }
}
