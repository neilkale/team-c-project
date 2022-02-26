package edu.wpi.cs3733.c22.teamC.Databases.MongoDB;
/** @author Aidan Burns 2/22/2022 This project does MongoLocation on the IntelliJ IDEA */
import com.mongodb.*;
import com.mongodb.client.*;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import org.bson.Document;

public class MongoLocation implements MongoInterface {
  private static String[] locFields =
      new String[] {
        "nodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName"
      };

  private MongoDatabase mongoDatabase;
  private MongoCollection<Document> LocationCollection;

  public MongoLocation() {
    ConnectionString connectionString =
        new ConnectionString(
            "mongodb+srv://admin:dDbno11RbFVsXVv3@serverlessinstance0.zitm8.mongodb.net/teamC_DB?retryWrites=true&w=majority");
    MongoClientSettings settings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    mongoDatabase = mongoClient.getDatabase("teamC_DB");

    LocationCollection = mongoDatabase.getCollection("locations");
    LocationCollection.countDocuments();
    List<Document> equipment = createDocs();
    List<Document> equipmentFiter = createFilter();
    if (LocationCollection.countDocuments() == 0) {
      LocationCollection.insertMany(equipment);
    }
    for (int i = 0; i < equipment.size(); i++) {
      Document filter = equipmentFiter.get(i);

      boolean a = false;
      for (Document d : LocationCollection.find(filter)) {
        a = true;
      }

      if (!a) {
        LocationCollection.insertOne(equipment.get(i));
      }
    }
  }

  @Override
  public void addItem(DatabaseInterface d) {
    Location m = (Location) d;
    String[] values = d.getValues();
    Document insertDoc = new Document();
    boolean isAlreadyContained = false;

    for (Document doc : LocationCollection.find(new Document(locFields[0], values[0]))) {
      isAlreadyContained = true;
    }

    if (isAlreadyContained) {
      for (int i = 0; i < values.length; i++) {
        insertDoc.append(locFields[i], values[i]);
      }
      LocationCollection.updateOne(new Document(locFields[0], values[0]), insertDoc);
      return;
    }

    for (int i = 0; i < values.length; i++) {
      insertDoc.append(locFields[i], values[i]);
    }
    LocationCollection.insertOne(insertDoc);
  }

  @Override
  public DatabaseInterface removeItem(DatabaseInterface d) {
    LocationCollection.deleteOne(new Document(d.getValues()[0], d.getValues()[0]));
    return d;
  }

  @Override
  public void changeItem(DatabaseInterface d) {
    removeItem(d);
    addItem(d);
  }

  /** @return A list of documents, used to insert into MongoDB */
  @Override
  public List<Document> createDocs() {
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

  @Override
  public List<Document> createFilter() {
    List<List<String>> csvList =
        readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/TowerLocationsC.csv");
    List<Document> toReturn = new ArrayList<>();
    for (List l : csvList) {
      List<String> entries = (List<String>) l;
      Document itemInList = new Document(locFields[0], entries.get(0));
      toReturn.add(itemInList);
    }
    return toReturn;
  }

  @Override
  public ArrayList<DatabaseInterface> getAllNodeData() {
    ArrayList<DatabaseInterface> toReturn = new ArrayList<>();
    FindIterable<Document> a = LocationCollection.find();
    for (Document d : a) {
      toReturn.add(
          new Location(
              (String) d.get(locFields[0]),
              (String) d.get(locFields[1]),
              (String) d.get(locFields[2]),
              (String) d.get(locFields[3]),
              (String) d.get(locFields[4]),
              (String) d.get(locFields[5]),
              (String) d.get(locFields[6]),
              (String) d.get(locFields[7])));
    }
    return toReturn;
  }

  /**
   * Reads a csv that you tell it to
   *
   * @param filename the name
   * @return a List of lines that are represented by Strings
   */
  private List<List<String>> readCSV(String filename) {
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
}
