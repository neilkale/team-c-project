package edu.wpi.cs3733.c22.teamC.Databases.requests;

import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.InternalTransportRequestQuery;

public class InternalTransportRequest extends ServiceRequest {
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
  public String[] getFieldNames() {
    String[] in = getGenericFieldNames();
    return new String[] {in[0], in[1], in[2], in[3], in[4], "Drop Off", "Urgency"};
  }

  @Override
  public String[] getFieldValues() {
    String[] in = getGenericFieldValues();
    return new String[] {in[0], in[1], in[2], in[3], in[4], this._dropOff, this._urgency};
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
}
