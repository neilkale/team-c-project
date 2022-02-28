package edu.wpi.cs3733.c22.teamC;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import org.bson.Document;

/** @author Aidan Burns 2/21/2022 File is a playground for creating MongoDB */
public class AidansMessAround {
  private static String[] locFields =
      new String[] {
        "nodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName"
      };

  static MongoClient mongoClient;
  static MongoDatabase teamC_db;
  static Map<String, List<String>> map;

  public static void main(String[] args) {
    String uri =
        "mongodb+srv://admin:dDbno11RbFVsXVv3@serverlessinstance0.zitm8.mongodb.net/teamC_DB?retryWrites=true&w=majority";

    mongoClient = MongoClients.create(uri);
    teamC_db = mongoClient.getDatabase("teamC_DB");
    map = new HashMap<>();
    /*
    try {
      System.out.println(locFields[-1]);
      // Replace the uri string with your MongoDB deployment's connection string
      // changed uri to include the name of the database we are using instead of myFirstDatabase


      try {
        MongoDatabase teamC_db = mongoClient.getDatabase("teamC_DB");
        try {
        } catch (MongoException me) {
          System.err.println("An error occurred: " + me);
        }
      }

      MongoClient mongoClient = MongoClients.create(uri);

      MongoDatabase database = mongoClient.getDatabase("teamC_DB");

      // not sure if this is actually how we're supposed to get this command to run
      Runtime r = Runtime.getRuntime();
      Process p = null;
      // this command line imports everything from the TowerLocationsC.csv file into the teamC_DB
      // it creates a "document" for each line in the csv and adds it to the collection "locations"
      // which is basically the equivalent of a table
      // headerline indicates the header column names in the csv

      // database.runCommand(command);

      try {
        MongoCollection<Document> equipmentCollection = database.getCollection("equipment");
        List<Document> equipment = createEquipmentDoc();
        List<Document> equipmentFiter = createEquipmentFilter();
        if (equipmentCollection.countDocuments() == 0) {
          equipmentCollection.insertMany(equipment);
        }
        for (int i = 0; i < equipment.size(); i++) {
          Document filter = equipmentFiter.get(i);

          boolean a = false;
          for (Document d : equipmentCollection.find()) {
            a = true;
          }

          if (!a) {
            equipmentCollection.insertOne(equipment.get(i));
          }
        }

        MongoCollection<Document> locationCollection = database.getCollection("locations");
        List<Document> locations = createLocationDoc();
        List<Document> locationFilter = createLocationFilter();

        if (locationCollection.countDocuments() == 0) { // if mongoDB is empty, it populates it
          locationCollection.insertMany(locations);
        } else if (locationCollection.countDocuments() > locations.size()) {
          // if there are more things in mongoDB, than in CSV

          for (Document doc : locationCollection.find()) {}
        }
        for (int i = 0; i < locations.size(); i++) {
          Document filter = locationFilter.get(i);
          Document locationDoc = locations.get(i);
          boolean a = false;
          for (Document d : locationCollection.find(filter)) {
            if (!compareLocationDocs(locationDoc, d)) {
              locationCollection.deleteOne(d);
              locationCollection.insertOne(locationDoc);
            }
            a = true;
          }

          if (!a) {
            locationCollection.insertOne(locationDoc);
          }
        }

        System.out.println("Reading csv into Database");

      } catch (Exception e) {
        e.printStackTrace();
      }

      // database.createCollection("test");
      // String command = "mongoimport --db users --collection contacts --type csv --file
      // resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EmployeeC.csv";

    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getCause());
    }

     */

    for (String s : readQueries()) {
      if (s.length() != 0) {
        System.out.println(getAction(s) + s.substring(s.indexOf(' ')));
      }
    }

    if (mongoClient != null) {
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
        /*case "SELECT":
            toReturn = select(query);
            break;
        case "DELETE":
            toReturn = delete(query);
            break;
        case "TRUNCATE":
            toReturn = truncate(query);
            break;*/
      default:
        break;
    }
    return toReturn;
  }

  private static String insert(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' ') - 1);
    actQuery = actQuery.substring(actQuery.indexOf('(') + 1, actQuery.indexOf(')'));
    String toIterate = actQuery;
    List<String> values = new ArrayList<>();
    List<String> fields = map.get(table);
    while (actQuery.contains(",")) {
      values.add(actQuery.substring(0, actQuery.indexOf(',')));
      actQuery = actQuery.substring(actQuery.indexOf(',') + 1);
    }
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
    String actQuery = query.substring(query.indexOf(' '));
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    teamC_db.getCollection(actQuery).deleteMany(new Document());
    return "TRUNCATE";
  }

  private static String createTable(String query) {
    String actQuery = query.substring(query.indexOf(' ') + 1);
    actQuery = actQuery.substring(actQuery.indexOf(' ') + 1);
    String table = actQuery.substring(0, actQuery.indexOf(' '));
    actQuery = actQuery.substring(actQuery.indexOf('(') + 1, actQuery.lastIndexOf(')'));
    String toIterate = actQuery;
    List<String> values = new ArrayList<>();
    while (toIterate.contains(",")) {
      values.add(toIterate.substring(0, toIterate.indexOf('V') - 1));
      toIterate = toIterate.substring(toIterate.indexOf(',') + 1);
    }
    values.add(toIterate.substring(1, toIterate.indexOf('V') - 1));
    map.put(table, values);

    return "CREATE";
  }

  private static List<Location> getAllItemsFromMongo(String collectionName) {
    MongoCollection<Document> collection = teamC_db.getCollection(collectionName);
    List<Location> locations = new ArrayList<>();
    for (Document d : collection.find()) {
      Location location =
          new Location(
              (String) d.get(locFields[0]),
              (String) d.get(locFields[1]),
              (String) d.get(locFields[2]),
              (String) d.get(locFields[3]),
              (String) d.get(locFields[4]),
              (String) d.get(locFields[5]),
              (String) d.get(locFields[6]),
              (String) d.get(locFields[7]));
      locations.add(location);
    }
    return locations;
  }

  private static boolean compareLocationDocs(Document loc1, Document loc2) {
    boolean toReturn = false;
    for (String f : locFields) {
      if (loc1.get(f) != loc2.get(f)) {
        toReturn = true;
      }
    }

    return toReturn;
  }

  private static List<Document> createLocationDoc() {
    List<List<String>> csvList =
        readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/TowerLocationsC.csv");
    List<Document> toReturn = new ArrayList<>();
    for (List l : csvList) {
      List<String> entries = (List<String>) l;
      Document itemInList = new Document();
      for (int i = 0; i < locFields.length; i++) {
        itemInList.append(locFields[i], entries.get(i));
      }
      toReturn.add(itemInList);
    }
    return toReturn;
  }

  private static List<Document> createLocationFilter() {
    List<List<String>> csvList =
        readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/TowerLocationsC.csv");
    List<Document> toReturn = new ArrayList<>();
    for (List l : csvList) {
      List<String> entries = (List<String>) l;
      Document itemInList = new Document("nodeID", entries.get(0));
      toReturn.add(itemInList);
    }
    return toReturn;
  }

  private static List<Document> createEquipmentDoc() {
    List<List<String>> csvList =
        readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentC.csv");
    List<Document> toReturn = new ArrayList<>();
    for (List l : csvList) {
      List<String> entries = (List<String>) l;
      Document itemInList = new Document();
      itemInList.append("equipmentID", entries.get(0));
      itemInList.append("locationID", entries.get(1));
      itemInList.append("lastKnownTime", entries.get(2));
      itemInList.append("status", entries.get(3));
      itemInList.append("equipmentType", entries.get(4));
      itemInList.append("name", entries.get(5));
      toReturn.add(itemInList);
    }
    return toReturn;
  }

  private static List<Document> createEquipmentFilter() {
    List<List<String>> csvList =
        readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentC.csv");
    List<Document> toReturn = new ArrayList<>();
    for (List l : csvList) {
      List<String> entries = (List<String>) l;
      Document itemInList = new Document("equipmentID", entries.get(0));
      toReturn.add(itemInList);
    }
    return toReturn;
  }

  private static List<List<String>> readCSV(String filename) {
    List<List<String>> toReturn = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        toReturn.add(Arrays.asList(values));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return toReturn;
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
