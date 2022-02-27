package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.EquipmentRequestQuery;

import java.util.List;

public class EquipmentRequest extends ServiceRequest implements DatabaseInterface {

  private String _urgency;
  private String _equipmentID;
  private String _pickupLocationID;

  public EquipmentRequest(List<String> args){
    super(args.get(0), args.get(1), args.get(2), args.get(4), args.get(5));
    this._urgency = args.get(6);
    this._equipmentID = args.get(7);
    this._pickupLocationID = args.get(8);
  }

  public EquipmentRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String urgency,
      String equipmentID,
      String pickupLocationID) {
    super(ticketID, locationID, status, "equip-distributor", assignment);
    this._urgency = urgency;
    this._equipmentID = equipmentID;
    this._pickupLocationID = pickupLocationID;
  }

  public String get_urgency() {
    return _urgency;
  }

  public void set_urgency(String _urgency) {
    this._urgency = _urgency;
  }

  public String get_equipmentID() {
    return _equipmentID;
  }

  public void set_equipmentID(String _equipmentID) {
    this._equipmentID = _equipmentID;
  }

  public String get_pickupLocationID() {
    return _pickupLocationID;
  }

  public void set_pickupLocationID(String _pickupLocationID) {
    this._pickupLocationID = _pickupLocationID;
  }

  @Override
  public EquipmentRequestQuery getQueryInstance() {
    return new EquipmentRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Equipment Request";
  }

  @Override
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {
      in[0], in[1], in[2], in[3], in[4], "Urgency", "Equipment ID", "Pickup Location ID"
    };
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {
      in[0], in[1], in[2], in[3], in[4], this._urgency, this._equipmentID, this._pickupLocationID
    };
  }

  public String toString() {
    return "Equipment Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nUrgency: "
        + _urgency
        + "\nEquipment ID: "
        + _equipmentID
        + "\nPickup Location ID: "
        + _pickupLocationID;
  }

  @Override
  public String[] getValues() {
    return new String[] {
      "ID",
      "Location",
      "Status",
      "Service Type",
      "Assignment",
      "Urgency",
      "Equipment ID",
      "Pickup Location ID"
    };
  }

  @Override
  public String getName() {
    return null;
  }
}
