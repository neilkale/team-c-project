package edu.wpi.cs3733.c22.teamC.DatabaseTests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import org.junit.jupiter.api.Test;

public class DBConnectionTest {

  @Test
  public void testServerClientDBConnection() {
    DatabaseConnection dbconnection = new DatabaseConnection();
    dbconnection.startServerClientDbConnection();
  }
}
