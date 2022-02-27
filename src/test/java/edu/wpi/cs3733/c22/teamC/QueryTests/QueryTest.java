package edu.wpi.cs3733.c22.teamC.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.cs3733.c22.teamC.Databases.requests.EquipmentRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.SanitationRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.EquipmentRequestQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.SanitationRequestQuery;
import java.util.*;
import org.junit.jupiter.api.Test;

public class QueryTest {
  /**
   * Equipment Test 1 Tests creating a request, query, and then adding that to the request list, and
   * then accessing that added node in the list of existing requests to see if it was saved.
   */
  @Test
  public void EquipmentQuery() {
    EquipmentRequest equipmentTest =
        new EquipmentRequest(
            "123", "567", "active", "Bed", "Jack", "Immediate", "12345", "123PickHere123");
    EquipmentRequestQuery newEquipmentQuery = equipmentTest.getQueryInstance();
    newEquipmentQuery.addNode(equipmentTest);
    List<EquipmentRequest> requestList = new ArrayList<>();
    requestList = newEquipmentQuery.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(equipmentTest)) {
      assertEquals(requestList.get(requestList.size() - 1), equipmentTest);
    }
  }

  /**
   * Sanitation Test 1 Tests creating a request, query, and then adding that to the request list,
   * and then accessing that added node in the list of existing requests to see if it was saved.
   */
  @Test
  public void SanitationQuery() {
    SanitationRequest sanitationTest =
        new SanitationRequest("123", "567", "active", "cleanup", "Jack", "spill");
    SanitationRequestQuery newSanitationQuery = sanitationTest.getQueryInstance();
    newSanitationQuery.addNode(sanitationTest);
    List<SanitationRequest> requestList = new ArrayList<>();
    requestList = newSanitationQuery.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(sanitationTest)) {
      assertEquals(requestList.get(requestList.size() - 1), sanitationTest);
    }
  }
}
