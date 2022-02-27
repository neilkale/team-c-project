package edu.wpi.cs3733.c22.teamC.DatabaseTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseConnection;
import org.junit.jupiter.api.Test;

public class DBConnectionTest {

  @Test
  public void testServerClientDBConnection() {
    DatabaseConnection dbconnection = new DatabaseConnection();
    dbconnection.startServerClientDbConnection();
    boolean tf = dbconnection.isClientDatabase();
    assertEquals(true, tf);
  }
}
