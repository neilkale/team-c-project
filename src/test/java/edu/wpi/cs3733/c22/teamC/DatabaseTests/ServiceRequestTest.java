package edu.wpi.cs3733.c22.teamC.DatabaseTests;

import edu.wpi.cs3733.c22.teamC.Databases.requests.SanitationRequest;
import org.junit.jupiter.api.Test;

public class ServiceRequestTest {

  @Test
  public void SanitationTest() {
    SanitationRequest sanitationTest =
        new SanitationRequest("123", "567", "active", "cleanup", "Jack", "spill");
  }
}
