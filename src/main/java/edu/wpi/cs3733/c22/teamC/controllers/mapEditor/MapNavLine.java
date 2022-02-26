package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

import edu.wpi.cs3733.c22.teamC.Databases.Location;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MapNavLine {
  public Pane getNavLine(List<Location> locations) {
    Pane returnPane = new Pane();
    returnPane.setPrefWidth(875 * MapState.getZoomFactor());
    returnPane.setPrefHeight(786 * MapState.getZoomFactor());
    if (locations.size() <= 1) return returnPane;
    Location lPrevious = null;
    for (Location l : locations) {
      if (lPrevious == null) {
        lPrevious = l;
      } else {
        if (l.get_floor().equals(MapState.getCurrentFloor())
            && lPrevious.get_floor().equals(MapState.getCurrentFloor())) {
          double startX = Double.parseDouble(l.get_xcoord()) * MapState.getZoomFactor();
          double startY = Double.parseDouble(l.get_ycoord()) * MapState.getZoomFactor();
          double endX = Double.parseDouble(lPrevious.get_xcoord()) * MapState.getZoomFactor();
          double endY = Double.parseDouble(lPrevious.get_ycoord()) * MapState.getZoomFactor();
          returnPane.getChildren().add(new Line(startX, startY, endX, endY));
        }
      }
      lPrevious = l;
    }
    return returnPane;
  }
}
