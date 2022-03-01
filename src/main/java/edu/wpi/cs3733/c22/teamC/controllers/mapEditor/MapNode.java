package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.MedicalEquipment;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MapNode extends ImageView {
  private final int locationIconRadius = 8;
  private final int itemIconRadius = 12;
  private final int startStopIconRadius = 15;
  private boolean newPoint;
  private boolean selected = false;
  private int radius;
  private int equipmentIndex = 100;
  private int serviceIndex = 100;
  private boolean startPoint = false;
  private boolean stopPoint = false;

  private Location location;
  ArrayList<MedicalEquipment> equipment = new ArrayList<>();
  ArrayList<ServiceRequest> requests = new ArrayList<>();

  public MapNode(Location location) {
    this.location = location;
  }

  public MapNode(double clickX, double clickY) { // THIS CONSTRUCTOR EXCLUSIVELY HANDLES NEW POINTS
    Random rand = new Random();
    this.location =
        new Location(
            LocationQuery.createUniqueNodeID("HALL", MapState.getCurrentFloor()),
            Double.toString(clickX / MapState.getZoomFactor()),
            Double.toString(clickY / MapState.getZoomFactor()),
            MapState.getCurrentFloor(),
            "Tower",
            "",
            "",
            "");
    newPoint = true;
    selected = true;
  }

  void incrementEquipmentIndex(int increment) {
    this.equipmentIndex += increment;
  }

  void incrementServiceIndex(int increment) {
    this.serviceIndex += increment;
  }

  Node getSuperscript() { // MAKES A JAVAFX NODE THAT IS THE SUPERSCRIPT FOR THE MAPNODE
    Node superScript = null;
    double xCoord = super.getX() + 2 * radius - 2;
    double yCoord = super.getY() + 2;
    if (MapState.isEquipmentShown() && equipment.size() > 1) { // Superscript for the equipment icon
      if (hasServiceReq() && MapState.isServicesShown()) {
        Circle circle = new Circle(xCoord, yCoord, 5);
        circle.setFill(Color.web("0xde8d28"));
        superScript = circle;
      } else {
        boolean clean = false;
        boolean dirty = false;
        for (MedicalEquipment e : equipment) {
          if (e.get_status().equals("DIRTY")) dirty = true;
          else clean = true;
        }
        if (getEquipment().get_status().equals("DIRTY")) {
          if (clean) {
            Circle circle = new Circle(xCoord, yCoord, 5);
            circle.setFill(Color.web("0x05aa02"));
            superScript = circle;
          }
        } else {
          if (dirty) {
            Circle circle = new Circle(xCoord, yCoord, 5);
            circle.setFill(Color.web("0xbb0100"));
            superScript = circle;
          }
        }
      }
    }
    if (superScript
        != null) { // Changes the opacity so that the superscript matches the icon in opacity
      superScript.setOpacity(super.getOpacity());
    }
    return superScript;
  }

  void setSelected(boolean selected) {
    this.selected = selected;
  }

  boolean isNewPoint() {
    return newPoint;
  }

  void setNewPoint(boolean newPoint) {
    this.newPoint = newPoint;
  }

  int getRadius() {
    return radius;
  }

  void setLocationXPos(double xCoord) {
    super.setX(xCoord * MapState.getZoomFactor() - radius);
    location.set_xcoord(Double.toString(xCoord));
  }

  void setLocationYPos(double yCoord) {
    super.setY(yCoord * MapState.getZoomFactor() - radius);
    location.set_ycoord(Double.toString(yCoord));
  }

  double getDistance(double xCoord, double yCoord) {
    double xDist = (xCoord - (getX() + radius));
    double yDist = (yCoord - (getY() + radius));
    return Math.sqrt(xDist * xDist + yDist * yDist);
  }

  void setLocationPos(double xCoord, double yCoord) { // takes actual coordinates (not zoomed)
    setLocationXPos(xCoord);
    setLocationYPos(yCoord);
  }

  void updateImage() { // Changes the image according to the nature of the MapNode
    if (startPoint) {
      super.setImage(ImageLoader.loadImage("Start"));
      radius = startStopIconRadius;
    } else if (stopPoint) {
      super.setImage(ImageLoader.loadImage("End"));
      radius = startStopIconRadius;
    } else if (MapState.isEquipmentShown() && hasEquipment()) {
      MedicalEquipment e = equipment.get(equipmentIndex % equipment.size());
      if (e.get_status().equals("DIRTY"))
        super.setImage(ImageLoader.loadImage(e.get_equipmentType() + "DIRTY"));
      else super.setImage(ImageLoader.loadImage(e.get_equipmentType()));
      radius = itemIconRadius;
    } else if (MapState.isServicesShown() && hasServiceReq()) {
      super.setImage(ImageLoader.loadImage("Service Request"));
      radius = itemIconRadius;
    } else if (MapState.isLocationsShown()) {
      if (isNewPoint()) super.setImage(ImageLoader.loadImage("New"));
      else super.setImage(ImageLoader.loadImage("Location"));
      radius = locationIconRadius;
    } else super.setVisible(false);
  }

  void update() {
    // Makes this mapnode visible only if it is on the right floor
    if (location.get_floor().equals(MapState.getCurrentFloor())) {
      super.setVisible(true);
    } else {
      super.setVisible(false);
    }

    // Changes the icon image
    updateImage();

    // Changes the opacity to show if it is selected or not
    if (selected) {
      super.setOpacity(1);
    } else {
      super.setOpacity(.6);
    }

    // Adjusts the image according to the zoom factor
    setLocationPos(
        Double.parseDouble(location.get_xcoord()), Double.parseDouble(location.get_ycoord()));

    // Sets the icon size
    super.setFitHeight(radius * 2);
    super.setFitWidth(radius * 2);
  }

  // Updates the contained location object
  void updateLocation(
      String locationLongName,
      String locationShortName,
      String locationType,
      String locationFloor) {
    Random rand = new Random();
    location.set_nodeID(LocationQuery.createUniqueNodeID(locationType, locationFloor));
    location.set_longName(locationLongName);
    location.set_shortName(locationShortName);
    location.set_nodeType(locationType);
    location.set_floor(locationFloor);

    for (MedicalEquipment e : equipment) {
      e.set_locationID(location.get_nodeID());
    }

    for (ServiceRequest s : requests) {
      s.set_locationID(location.get_nodeID());
    }
  }

  // returns true if the given ID matches the location of this node
  boolean checkLocation(String nodeID) {
    return nodeID.equals(location.get_nodeID());
  }

  void addEquipment(MedicalEquipment e) {
    e.set_locationID(location.get_nodeID());
    equipment.add(e);
  }

  void addServiceReq(ServiceRequest s) {
    s.set_locationID(location.get_nodeID());
    requests.add(s);
  }

  MedicalEquipment getEquipment() {
    try {
      return equipment.get(equipmentIndex % equipment.size());
    } catch (Exception e) {
      return null;
    }
  }

  ServiceRequest getServiceReq() {
    try {
      return requests.get(serviceIndex % equipment.size());
    } catch (Exception e) {
      return null;
    }
  }

  void removeEquipment() {
    equipment.remove(equipmentIndex % equipment.size());
    update();
  }

  void removeServiceReq() {
    requests.remove(serviceIndex % equipment.size());
    update();
  }

  boolean hasEquipment() {
    return (equipment.size() > 0);
  }

  boolean hasServiceReq() {
    return (requests.size() > 0);
  }

  void setEquipment(MedicalEquipment e) {
    equipment.set(equipmentIndex % equipment.size(), e);
  }

  Location getLocation() {
    return location;
  }

  public boolean isStartPoint() {
    return startPoint;
  }

  public void setStartPoint(boolean startPoint) {
    this.startPoint = startPoint;
  }

  public boolean isStopPoint() {
    return stopPoint;
  }

  public void setStopPoint(boolean stopPoint) {
    this.stopPoint = stopPoint;
  }

  public void playEnlargeAnimation() {
    ScaleTransition scaleTransition = new ScaleTransition();
    scaleTransition.setNode(this);

    scaleTransition.setByY(.5);
    scaleTransition.setByX(.5);
    scaleTransition.setDuration(Duration.millis(100));
    scaleTransition.setCycleCount(2);
    scaleTransition.setAutoReverse(true);

    scaleTransition.play();
  }

  public void playWiggleAnimation() {
    RotateTransition rotateTransition = new RotateTransition();
    rotateTransition.setNode(this);

    rotateTransition.setByAngle(30);
    rotateTransition.setDuration(Duration.millis(100));
    rotateTransition.setCycleCount(2);
    rotateTransition.setAutoReverse(true);

    rotateTransition.play();
  }
}
