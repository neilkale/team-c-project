package edu.wpi.cs3733.c22.teamC.controllers.sideView;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MedicalEquipmentQuery;
import java.util.ArrayList;
import java.util.HashMap;

public class SideViewTracker {
  private static HashMap<String, Integer[]> equipmentCount = new HashMap<String, Integer[]>();
  private static LocationQuery locationQuery = new LocationQuery();
  private static MedicalEquipmentQuery medicalEquipmentQuery = new MedicalEquipmentQuery();

  static void loadDataFromDB() {
    ArrayList<Location> locations = locationQuery.getAllNodeData();
    ArrayList<MedicalEquipment> equipment = medicalEquipmentQuery.getAllNodeData();

    Integer[] blankArray = {0, 0, 0, 0, 0, 0, 0, 0};
    equipmentCount.put("L2", blankArray.clone());
    equipmentCount.put("L1", blankArray.clone());
    equipmentCount.put("1", blankArray.clone());
    equipmentCount.put("2", blankArray.clone());
    equipmentCount.put("3", blankArray.clone());

    for (MedicalEquipment e : equipment) {
      for (Location l : locations) {
        if (e.get_locationID().equals(l.get_nodeID())) {
          if (e.get_status().equals("DIRTY")) {
            switch (e.get_equipmentType()) {
              case "XRAY":
                equipmentCount.get(l.get_floor())[4]++;
                break;
              case "PUMP":
                equipmentCount.get(l.get_floor())[5]++;
                break;
              case "BEDS":
                equipmentCount.get(l.get_floor())[6]++;
                break;
              case "RECL":
                equipmentCount.get(l.get_floor())[7]++;
                break;
            }
          } else {
            switch (e.get_equipmentType()) {
              case "XRAY":
                equipmentCount.get(l.get_floor())[0]++;
                break;
              case "PUMP":
                equipmentCount.get(l.get_floor())[1]++;
                break;
              case "BEDS":
                equipmentCount.get(l.get_floor())[2]++;
                break;
              case "RECL":
                equipmentCount.get(l.get_floor())[3]++;
                break;
            }
          }
        }
      }
    }
  }

  static int getCleanEquipCount(String floor) {
    Integer[] equip = equipmentCount.get(floor);
    return equip[0] + equip[1] + equip[2] + equip[3];
  }

  static int getDirtyEquipCount(String floor) {
    Integer[] equip = equipmentCount.get(floor);
    return equip[4] + equip[5] + equip[6] + equip[7];
  }

  static Integer[] getEquipmentCount(String floor) {
    return equipmentCount.get(floor);
  }
}
