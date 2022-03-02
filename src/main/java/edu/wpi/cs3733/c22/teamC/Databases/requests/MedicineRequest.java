package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.MedicineRequestQuery;

/** this class is for the medicine service request data */
public class MedicineRequest extends ServiceRequest implements DatabaseInterface {

  private String _medicineType;
  private String _quantity;
  private String _urgency;

  public MedicineRequest(
      String ticketID,
      String locationID,
      String status,
      String serviceType,
      String assignment,
      String _medicineType,
      String _quantity,
      String _urgency) {
    super(ticketID, locationID, status, "med-distributor", assignment);
    this._medicineType = _medicineType;
    this._quantity = _quantity;
    this._urgency = _urgency;
  }

  public String get_medicineType() {
    return _medicineType;
  }

  public void set_medicineType(String _medicineType) {
    this._medicineType = _medicineType;
  }

  public String get_quantity() {
    return _quantity;
  }

  public void set_quantity(String _quantity) {
    this._quantity = _quantity;
  }

  public String get_urgency() {
    return _urgency;
  }

  public void set_language(String _urgency) {
    this._urgency = _urgency;
  }

  @Override
  public MedicineRequestQuery getQueryInstance() {
    return new MedicineRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Medicine Request";
  }

  public String toString() {
    return "Medicine Request\nID: "
        + get_ticketID()
        + "\nLocation: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nMedicine Type: "
        + _medicineType
        + "\nQuantity: "
        + _quantity
        + "\nUrgency: "
        + _urgency;
  }

  @Override
  public String[] getFields() {
    return new String[] {
      "ticketID", "status", "serviceType", "assignment", "medicineType", "quantity", "urgency"
    };
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getMedicineRequestDao();
  }
}
