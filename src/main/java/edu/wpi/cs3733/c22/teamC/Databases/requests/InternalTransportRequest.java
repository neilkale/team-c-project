package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoInterface;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DatabaseInterface;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.InternalTransportRequestQuery;

public class InternalTransportRequest extends ServiceRequest implements DatabaseInterface {
  private String _dropOff;
  private String _urgency;

  public InternalTransportRequest(
      String _ticketID,
      String _locationID,
      String _status,
      String _serviceType,
      String _assignment,
      String _dropOff,
      String _urgency) {
    super(_ticketID, _locationID, _status, "transportation", _assignment);
    this._dropOff = _dropOff;
    this._urgency = _urgency;
  }

  public String get_dropOff() {
    return _dropOff;
  }

  public void set_dropOff(String _dropOff) {
    this._dropOff = _dropOff;
  }

  public String get_urgency() {
    return _urgency;
  }

  public void set_urgency(String _urgency) {
    this._urgency = _urgency;
  }

  @Override
  public InternalTransportRequestQuery getQueryInstance() {
    return new InternalTransportRequestQuery();
  }

  @Override
  public String getRequestType() {
    return "Internal Transport Request";
  }

  @Override
  public String toString() {
    return "Internal Transport Request\nID: "
        + get_ticketID()
        + "\nPick-up Location: "
        + get_locationID()
        + "\nStatus: "
        + get_status()
        + "\nService Type: "
        + get_serviceType()
        + "\nAssignment: "
        + get_assignment()
        + "\nDrop off: "
        + _dropOff
        + "\nUrgency: "
        + _urgency;
  }

  @Override
  public String[] getFields() {
    return new String[] {"ticketID", "status", "serviceType", "assignment", "dropOff", "urgency"};
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public DaoInterface getDao() {
    return DaoSingleton.getInternalTransportRequestDao();
  }
}
