package edu.wpi.cs3733.c22.teamC.Databases.MongoDB;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/** @author Aidan Burns 2/22/2022 This project does MongoInterface on the IntelliJ IDEA */
public interface MongoInterface {
  void addItem(DatabaseInterface d);

  DatabaseInterface removeItem(DatabaseInterface d);

  void changeItem(DatabaseInterface d);

  List<Document> createDocs();

  List<Document> createFilter();

  ArrayList<DatabaseInterface> getAllNodeData();
}
