package edu.wpi.cs3733.c22.teamC.Databases.MongoDB;
/** @author Aidan Burns 2/22/2022 This project does MongoEquipment on the IntelliJ IDEA */
import com.mongodb.*;
import com.mongodb.client.*;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import org.bson.Document;

public class MongoEquipment implements MongoInterface {
  private static final String[] equipmentFields =
      new String[] {
        "equipmentID", "locationID", "lastKnownTime", "status", "equipmentType", "name"
      };
  private final String uri =
      "mongodb+srv://admin:dDbno11RbFVsXVv3@serverlessinstance0.zitm8.mongodb.net/teamC_DB?retryWrites=true&w=majority";
  private MongoDatabase mongoDatabase;
  private MongoCollection<Document> equipmentCollection;

  public MongoEquipment() {
    /*ConnectionString connectionString =
        new ConnectionString(
            "mongodb+srv://admin:dDbno11RbFVsXVv3@serverlessinstance0.zitm8.mongodb.net/teamC_DB?retryWrites=true&w=majority");
    MongoClientSettings settings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    mongoDatabase = mongoClient.getDatabase("teamC_DB");

    equipmentCollection = mongoDatabase.getCollection("equipment");
    List<Document> equipment = createDocs();
    List<Document> equipmentFiter = createFilter();
    if (equipmentCollection.countDocuments() == 0) {
      equipmentCollection.insertMany(equipment);
    }
    for (int i = 0; i < equipment.size(); i++) {
      Document filter = equipmentFiter.get(i);

      boolean a = false;
      for (Document d : equipmentCollection.find(filter)) {
        a = true;
      }

      if (!a) {
        equipmentCollection.insertOne(equipment.get(i));
      }
    }*/
  }

  @Override
  public void addItem(DatabaseInterface d) {
    MedicalEquipment m = (MedicalEquipment) d;
    String[] values = d.getValues();
    Document insertDoc = new Document();
    boolean isAlreadyContained = false;

    for (Document doc : equipmentCollection.find(new Document(equipmentFields[0], values[0]))) {
      isAlreadyContained = true;
    }

    if (isAlreadyContained) {
      for (int i = 0; i < values.length; i++) {
        insertDoc.append(equipmentFields[i], values[i]);
      }
      equipmentCollection.updateOne(new Document(equipmentFields[0], values[0]), insertDoc);
      return;
    }

    for (int i = 0; i < values.length; i++) {
      insertDoc.append(equipmentFields[i], values[i]);
    }
    equipmentCollection.insertOne(insertDoc);
  }

  @Override
  public DatabaseInterface removeItem(DatabaseInterface d) {
    equipmentCollection.deleteOne(new Document(d.getValues()[0], d.getValues()[0]));
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
        readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentC.csv");
    List<Document> toReturn = new ArrayList<>();
    for (List l : csvList) {
      List<String> entries = (List<String>) l;
      Document itemInList = new Document();
      for (int i = 0; i < equipmentFields.length; i++) {
        itemInList.append(equipmentFields[i], entries.get(i));
      }
      toReturn.add(itemInList);
    }
    return toReturn;
  }

  @Override
  public List<Document> createFilter() {
    List<List<String>> csvList =
        readCSV("src/main/resources/edu/wpi/cs3733.c22.teamC/CSV_Files/EquipmentC.csv");
    List<Document> toReturn = new ArrayList<>();
    for (List l : csvList) {
      List<String> entries = (List<String>) l;
      Document itemInList = new Document(equipmentFields[0], entries.get(0));
      toReturn.add(itemInList);
    }
    return toReturn;
  }

  @Override
  public ArrayList<DatabaseInterface> getAllNodeData() {
    ArrayList<DatabaseInterface> toReturn = new ArrayList<>();
    FindIterable<Document> a = equipmentCollection.find();
    for (Document d : a) {
      toReturn.add(
          new MedicalEquipment(
              (String) d.get(equipmentFields[0]),
              (String) d.get(equipmentFields[1]),
              (String) d.get(equipmentFields[2]),
              (String) d.get(equipmentFields[3]),
              (String) d.get(equipmentFields[4]),
              (String) d.get(equipmentFields[5])));
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
