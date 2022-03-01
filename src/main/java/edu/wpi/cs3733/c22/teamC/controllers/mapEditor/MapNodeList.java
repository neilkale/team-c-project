package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.Databases.requests.*;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.MedicalEquipmentQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.*;
import java.util.ArrayList;

public class MapNodeList {
  LocationQuery locationQuery = new LocationQuery();
  MedicalEquipmentQuery medicalEquipmentQuery = new MedicalEquipmentQuery();
  EquipmentRequestQuery equipmentRequestQuery = new EquipmentRequestQuery();
  GiftRequestQuery giftRequestQuery = new GiftRequestQuery();
  InternalTransportRequestQuery internalTransportRequestQuery = new InternalTransportRequestQuery();
  ITRequestQuery itRequestQuery = new ITRequestQuery();
  LaundryRequestQuery laundryRequestQuery = new LaundryRequestQuery();
  MaintenanceRequestQuery maintenanceRequestQuery = new MaintenanceRequestQuery();
  MedicineRequestQuery medicineRequestQuery = new MedicineRequestQuery();
  ReligiousRequestQuery religiousRequestQuery = new ReligiousRequestQuery();
  SanitationRequestQuery sanitationRequestQuery = new SanitationRequestQuery();

  static ArrayList<MapNode> nodes = new ArrayList<MapNode>();

  void build() {
    if (nodes.size() == 0) load();
  }

  void save() throws Exception {
    ArrayList<MedicalEquipment> equipment = new ArrayList<>();
    ArrayList<EquipmentRequest> eReq = new ArrayList<>();
    ArrayList<GiftRequest> gReq = new ArrayList<>();
    ArrayList<InternalTransportRequest> interReq = new ArrayList<>();
    ArrayList<ITRequest> itReq = new ArrayList<>();
    ArrayList<LanguageRequest> langReq = new ArrayList<>();
    ArrayList<LaundryRequest> launReq = new ArrayList<>();
    ArrayList<MaintenanceRequest> mainReq = new ArrayList<>();
    ArrayList<MedicineRequest> mediReq = new ArrayList<>();
    ArrayList<ReligiousRequest> reliReq = new ArrayList<>();
    ArrayList<SanitationRequest> sanReq = new ArrayList<>();
    ArrayList<Location> locs = new ArrayList<>();
    for (MapNode each : nodes) {
      locs.add(each.getLocation());
      equipment.addAll(each.equipment);
      ArrayList<ServiceRequest> working = each.requests;
      for (ServiceRequest curr : working) {
        switch (curr.getClass().toString()) {
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.EquipmentRequest":
            eReq.add((EquipmentRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.GiftRequest":
            gReq.add((GiftRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.InternalTransportRequest":
            interReq.add((InternalTransportRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.ITRequest":
            itReq.add((ITRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.LanguageRequest":
            langReq.add((LanguageRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.LaundryRequest":
            launReq.add((LaundryRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.MaintenanceRequest":
            mainReq.add((MaintenanceRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.MedicineRequest":
            mediReq.add((MedicineRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.ReligiousRequest":
            reliReq.add((ReligiousRequest) curr);
            break;
          case "class edu.wpi.cs3733.c22.teamC.Databases.requests.SanitationRequest":
            sanReq.add((SanitationRequest) curr);
            break;
        }
      }
    }
    (new MedicalEquipmentQuery()).compareAndChange(equipment, "equipmentID");
    (new EquipmentRequestQuery()).compareAndChange(eReq, "ticketID");
    (new GiftRequestQuery()).compareAndChange(gReq, "ticketID");
    (new InternalTransportRequestQuery()).compareAndChange(interReq, "ticketID");
    (new ITRequestQuery()).compareAndChange(itReq, "ticketID");
    (new LanguageRequestQuery()).compareAndChange(langReq, "ticketID");
    (new LaundryRequestQuery()).compareAndChange(launReq, "ticketID");
    (new MaintenanceRequestQuery()).compareAndChange(mainReq, "ticketID");
    (new MedicineRequestQuery()).compareAndChange(mediReq, "ticketID");
    (new ReligiousRequestQuery()).compareAndChange(reliReq, "ticketID");
    (new SanitationRequestQuery()).compareAndChange(sanReq, "ticketID");
    (new LocationQuery()).compareAndChange(locs, "nodeID");
    // (new Query()).compareAndChange();

  }

  void load() {
    nodes.clear();

    // put locations from the databases on the map
    ArrayList<Location> locations = locationQuery.getAllNodeData();
    locations.remove(0);

    for (Location l : locations) {
      l.set_xcoord(l.get_xcoord());
      l.set_ycoord(l.get_ycoord());
      MapNode node = new MapNode(l);
      nodes.add(node);
    }
    // put medical equipment on the map
    ArrayList<MedicalEquipment> equipment = medicalEquipmentQuery.getAllNodeData();

    for (MedicalEquipment e : equipment) {
      for (MapNode n : nodes) {
        if (n.checkLocation(e.get_locationID())) {
          n.addEquipment(e);
        }
      }
    }

    ArrayList<ServiceRequest> requests = new ArrayList<>();
    requests.addAll(equipmentRequestQuery.getAllNodeData());
    requests.addAll(giftRequestQuery.getAllNodeData());
    requests.addAll(internalTransportRequestQuery.getAllNodeData());
    requests.addAll(itRequestQuery.getAllNodeData());
    requests.addAll(laundryRequestQuery.getAllNodeData());
    requests.addAll(maintenanceRequestQuery.getAllNodeData());
    requests.addAll(medicineRequestQuery.getAllNodeData());
    requests.addAll(religiousRequestQuery.getAllNodeData());
    requests.addAll(sanitationRequestQuery.getAllNodeData());
    requests.addAll(new LanguageRequestQuery().getAllNodeData());
    for (ServiceRequest s : requests) {
      for (MapNode n : nodes) {
        if (n.checkLocation(s.get_locationID())) {
          n.addServiceReq(s);
        }
      }
    }
  }
}
