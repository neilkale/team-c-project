package edu.wpi.cs3733.c22.teamC.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.cs3733.c22.teamC.Databases.requests.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.*;
import java.sql.SQLException;
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
    List<EquipmentRequest> requestList;
    requestList = newEquipmentQuery.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(equipmentTest)) {
      assertEquals(requestList.get(requestList.size() - 1), equipmentTest);
    }
  }

  /**
   * Gift Test 1 Tests creating a request, query, and then adding that to the request list, and then
   * accessing that added node in the list of existing requests to see if it was saved.
   */
  @Test
  public void GiftQuery() {
    GiftRequest giftTest = new GiftRequest("123", "567", "active", "GiftRequest", "Jack", "Candy");
    GiftRequestQuery newGiftQuery = giftTest.getQueryInstance();
    newGiftQuery.addNode(giftTest);
    List<GiftRequest> requestList;
    requestList = newGiftQuery.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(giftTest)) {
      assertEquals(requestList.get(requestList.size() - 1), giftTest);
    }
  }

  /**
   * InternalTransport Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void InternalTransportQuery() {
    InternalTransportRequest internalTest =
        new InternalTransportRequest(
            "123", "567", "active", "InternalTransport", "Jack", "Salisbury", "Immediate");
    InternalTransportRequestQuery newInternalQuery = internalTest.getQueryInstance();
    newInternalQuery.addNode(internalTest);
    List<InternalTransportRequest> requestList;
    requestList = newInternalQuery.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(internalTest)) {
      assertEquals(requestList.get(requestList.size() - 1), internalTest);
    }
  }

  /**
   * IT Test 1 Tests creating a request, query, and then adding that to the request list, and then
   * accessing that added node in the list of existing requests to see if it was saved.
   */
  @Test
  public void ITRequestQuery() {
    ITRequest itTest = new ITRequest("123", "567", "active", "IT", "Jack", "Computer");
    ITRequestQuery newITQuery = itTest.getQueryInstance();
    newITQuery.addNode(itTest);
    List<ITRequest> requestList;
    requestList = newITQuery.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(itTest)) {
      assertEquals(requestList.get(requestList.size() - 1), itTest);
    }
  }

  /**
   * Language Request Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void languageTest() {
    LanguageRequest requestTest =
        new LanguageRequest("123", "567", "active", "Language", "Jack", "Italian");
    LanguageRequestQuery newQueryTest = requestTest.getQueryInstance();
    newQueryTest.addNode(requestTest);
    List<LanguageRequest> requestList;
    requestList = newQueryTest.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(requestTest)) {
      assertEquals(requestList.get(requestList.size() - 1), requestTest);
    }
  }

  /**
   * Laundry Request Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void LaundryQuery() {
    LaundryRequest requestTest = new LaundryRequest("123", "567", "active", "Laundry", "Jack");
    LaundryRequestQuery newQueryTest = requestTest.getQueryInstance();
    newQueryTest.addNode(requestTest);
    List<LaundryRequest> requestList;
    requestList = newQueryTest.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(requestTest)) {
      assertEquals(requestList.get(requestList.size() - 1), requestTest);
    }
  }

  /**
   * Maintenance Request Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void MaintenanceQuery() {
    MaintenanceRequest requestTest =
        new MaintenanceRequest("123", "567", "active", "Maintenance", "Jack", "Broken Bed");
    MaintenanceRequestQuery newQueryTest = requestTest.getQueryInstance();
    newQueryTest.addNode(requestTest);
    List<MaintenanceRequest> requestList;
    requestList = newQueryTest.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(requestTest)) {
      assertEquals(requestList.get(requestList.size() - 1), requestTest);
    }
  }

  /**
   * Medical Request Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void MedicalQuery() {
    MedicalEquipment requestTest =
        new MedicalEquipment("123", "567", "12:00", "active", "Bed", "Jack");
    MedicalEquipmentQuery newQueryTest = requestTest.getQueryInstance();
    newQueryTest.addNode(requestTest);
    List<MedicalEquipment> requestList;
    requestList = newQueryTest.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(requestTest)) {
      assertEquals(requestList.get(requestList.size() - 1), requestTest);
    }
  }
  /**
   * Medicine Request Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void MedicineQuery() {
    MedicineRequest requestTest =
        new MedicineRequest("123", "567", "Active", "Medicine", "Jack", "Advil", "x1", "immediate");
    MedicineRequestQuery newQueryTest = requestTest.getQueryInstance();
    newQueryTest.addNode(requestTest);
    List<MedicineRequest> requestList;
    requestList = newQueryTest.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(requestTest)) {
      assertEquals(requestList.get(requestList.size() - 1), requestTest);
    }
  }
  /**
   * Religious Request Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void ReligiousQuery() {
    ReligiousRequest requestTest =
        new ReligiousRequest("123", "567", "Active", "Religion", "Jack", "Catholic");
    ReligiousRequestQuery newQueryTest = requestTest.getQueryInstance();
    newQueryTest.addNode(requestTest);
    List<ReligiousRequest> requestList;
    requestList = newQueryTest.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(requestTest)) {
      assertEquals(requestList.get(requestList.size() - 1), requestTest);
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
    List<SanitationRequest> requestList;
    requestList = newSanitationQuery.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(sanitationTest)) {
      assertEquals(requestList.get(requestList.size() - 1), sanitationTest);
    }
  }

  /**
   * Security Request Test 1 Tests creating a request, query, and then adding that to the request
   * list, and then accessing that added node in the list of existing requests to see if it was
   * saved.
   */
  @Test
  public void SecurityQuery() throws SQLException {
    SecurityRequest requestTest =
        new SecurityRequest("123", "567", "Active", "Security", "Jack", "L1", "Armed", "Immediate");
    SecurityRequestQuery newQueryTest = requestTest.getQueryInstance();
    newQueryTest.addNode(requestTest);
    List<SecurityRequest> requestList;
    requestList = newQueryTest.getAllNodeData();

    if (requestList.get(requestList.size() - 1).equals(requestTest)) {
      assertEquals(requestList.get(requestList.size() - 1), requestTest);
    }
  }
}
